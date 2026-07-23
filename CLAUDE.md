# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

This is `API_monstres`, the Spring Boot monster service of the Gatcha game, checked out as a git submodule of the `GatchaApi` root repo — see the root repo's CLAUDE.md for the cross-service architecture, ports, and how the stack is run (root `docker-compose.yaml` only).

This repo has no `.env`/`.env.example` of its own. Any new environment variable this service needs must be added to the root repo's `.env`/`.env.exemple` and wired into this service's `environment:` block in the root `docker-compose.yaml` — there is nowhere else the running stack would pick it up from.

## Commands

```bash
./mvnw clean package     # build
./mvnw test               # run tests — test deps (JUnit/Mockito) are declared but there is no src/test directory yet, nothing to run
./mvnw spring-boot:run     # run locally against application-local.yml (mongo on localhost:27021, see below)
```

No linting/formatting tooling is configured for this service (unlike `API_invocations`, which has spotless/checkstyle).

## Git workflow (required)

For any piece of work beyond a trivial one-line fix: create a dedicated branch (`feat/...`, `fix/...`, `perf/...`) from the integration branch (`master` here — the `dev` branch that used to serve this role is stale, doesn't even carry this file, and should not be branched from), commit in atomic steps with conventional-commit messages in French (`feat:`/`fix:`/`perf:`/`docs:` plus a body explaining the why), then merge back with `--no-ff`. Never commit sizeable work directly on the integration branch.

## Architecture

Standard controller → service → repository → dao layering, two parallel resources (Monster, Skill) each with their own controller/service/repository/dao/mapper. **Naming is inconsistent with the other three Java services**: base package is lowercase `com.imt.api_monstres` (not `com.imt.Api_monstres`), and the persistence layer sits under a capitalized `Repository/` folder (`Repository/dao`, `Repository/dto`) rather than `repository/`. Don't assume identical casing when navigating by analogy from `API_joueur`/`API_invocations`/`API_authentification`.

- **`Repository/`** is a thin custom layer, *not* Spring Data repositories directly exposed to services: `MonsterRepository`/`SkillRepository` (plain `@Repository` classes) wrap `MonsterMongoDao`/`SkillMongoDao` (the actual `MongoRepository<..., String>` interfaces under `Repository/dao`). Services depend on the wrapper, never the Dao directly.
- **IDs are app-generated UUIDs**, not Mongo ObjectIds: both `MonsterService.createMonster` and `SkillService.createSkill` do `UUID.randomUUID().toString()` and store it as `@MongoId` on the entity — Mongo never auto-generates the id.
- **Monster creation cascades into Skill creation**: `MonsterService.createMonster` first calls `SkillService.createSkill` once per skill in the request body (each gets its own new UUID and a `monsterId` foreign key), then saves the monster itself with the resulting `skillIds` list. `deleteMonster` is the mirror: it looks up all skills by `monsterId` and deletes them individually before deleting the monster. There's no Mongo transaction wrapping either cascade — a failure partway through leaves orphaned skills/monsters.
- **`MonsterMongoDto.playerId` is effectively dead**: `MonsterHttpDto` (the create request) has no `playerId` field, and `MonsterMapper.toMongoEntity` always persists `playerId = null`. Nothing in this service ever sets it. That means `GET /api/monsters/getByPlayerId/{playerId}` will never return results as currently wired — ownership of a monster actually lives in `API_joueur`'s inventory (see root repo's cross-service architecture), not here. Don't assume this endpoint works without checking whether something downstream (e.g. `API_invocations`) has started PATCHing it — as of now, nothing does.
- **`SkillController#updateSkill` is commented out** even though `SkillService.updateSkill` is fully implemented — the service method has no live caller.
- **Auth is enforced per-request, not via Spring Security**: `AuthInterceptor` (`config/AuthInterceptor.java`) is a plain `HandlerInterceptor` registered in `WebConfig` on `/api/monsters/**` (swagger/OpenAPI paths excluded). It forwards the `Authorization` header to `API_authentification`'s `POST /user/verify-token` via a plain `RestTemplate` (host/port from `gatcha.auth-api.host`/`port`, env `AUTH_API_HOST`/`AUTH_API_PORT`). Behavior is fail-closed but distinguishes failure modes: missing token → 401, auth service says invalid (4xx) → 401, auth service itself 5xx → 401 ("fail-safe: treat as invalid rather than block"), auth service unreachable → 500. The verified username is stashed on the request as attribute `"username"` but nothing currently reads it back out.
- **Validation errors** are the only exception type with a global handler: `GlobalExceptionHandler` catches `HandlerMethodValidationException` (from `@Valid`/Bean Validation) and returns a structured `{"errors": [{"statusCode": ..., "message": ...}]}` body. Everything else (`RuntimeException`/`IllegalArgumentException` thrown for "not found" cases in the services) is unhandled and will surface as Spring Boot's default 500 error page — there is no `@ExceptionHandler` for domain-not-found errors.
- **Rank and Elementary are fixed enums**, not free text: `Rank` (COMMON/RARE/EPIC/LEGENDARY, each carrying the drop rate used by `API_invocations`' gacha roll — 50/30/15/5%) and `Elementary` (FIRE/WATER/WIND only — no other elements exist yet). `Stat`/`Ratio` back a skill's damage-scaling formula (`Ratio` = which `Stat` the skill scales off of + what percent).
- Local dev DB config (`application-local.yml`, used by `./mvnw spring-boot:run`) points at `localhost:27021` (`monsters_db`) — that's `mongo-monsters`'s host-exposed port when the root docker-compose stack is up, distinct from `application.yml`'s in-container default of port `27017` / database `Api_monstres`.
