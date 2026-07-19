# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

This is `API_monstres`, the Spring Boot monster service of the Gatcha game, checked out as a git submodule of the `GatchaApi` root repo — see the root repo's CLAUDE.md for the cross-service architecture, ports, and how the stack is run (root `docker-compose.yaml` only). Build with `./mvnw clean package`; test dependencies exist but there is no `src/test` directory yet. Note the lowercase base package (`com.imt.api_monstres`) and capitalized `Repository` folder, inconsistent with the other Java services.

## Git workflow (required)

For any piece of work beyond a trivial one-line fix: create a dedicated branch (`feat/...`, `fix/...`, `perf/...`) from the integration branch (`dev` here), commit in atomic steps with conventional-commit messages in French (`feat:`/`fix:`/`perf:`/`docs:` plus a body explaining the why), then merge back with `--no-ff`. Never commit sizeable work directly on the integration branch or `master`.
