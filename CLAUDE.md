# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

This is `API_monstres`, the Spring Boot monster service of the Gatcha game, checked out as a git submodule of the `GatchaApi` root repo — see the root repo's CLAUDE.md for the cross-service architecture, ports, and how the stack is run (root `docker-compose.yaml` only). Build with `./mvnw clean package`; test dependencies exist but there is no `src/test` directory yet. Note the lowercase base package (`com.imt.api_monstres`) and capitalized `Repository` folder, inconsistent with the other Java services.

This repo has no `.env`/`.env.example` of its own. Any new environment variable this service needs must be added to the root repo's `.env`/`.env.exemple` and wired into this service's `environment:` block in the root `docker-compose.yaml` — there is nowhere else the running stack would pick it up from.

## Git workflow (required)

For any piece of work beyond a trivial one-line fix: create a dedicated branch (`feat/...`, `fix/...`, `perf/...`) from the integration branch (`master` here — the `dev` branch that used to serve this role is stale, doesn't even carry this file, and should not be branched from), commit in atomic steps with conventional-commit messages in French (`feat:`/`fix:`/`perf:`/`docs:` plus a body explaining the why), then merge back with `--no-ff`. Never commit sizeable work directly on the integration branch.
