# Copilot Instructions — Assembler Matrix Prioritization

## Project Overview
- This MOD adds a priority UI to the **Matrix Assembler** (ExtendedAE), using the same UI as
  the ME Pattern Provider. Higher numbers mean higher priority (default: 0).
- Repository: `veedeeee/Assembler-Matrix-Prioritization`
- Agent-ops infrastructure is in a separate repository (`TeamSOS_Multi-agent`), accessed
  via `/add-dir`. Read that repo's `copilot-instructions.md` for agent-workflow rules.

## Mod Features
- The MOD adds a priority UI to the **Matrix Assembler** (ExtendedAE). This allows setting
  per-assembler crafting priority, the same way ME Storage Bus priority works.
- Higher number means higher priority. The default priority is 0.
- When multiple providers (Matrix Assemblers or Pattern Providers) can produce the same item,
  the one with the highest priority is used first.
- If the highest-priority provider cannot satisfy the full request alone (insufficient
  materials), the system falls back to lower-priority providers to fill the remainder.

### MOD Goal

| Goal | Description | Status |
|------|-------------|--------|
| **Goal 2** | Matrix Assembler (ExtendedAE) priority UI — set crafting priority per Assembler Matrix | ✅ Implemented |

### Example — Matrix Assembler vs Pattern Provider
- Matrix Assembler [M]: produces Iron Ingot, priority = 100
- Pattern Provider [P]: `9x Iron Nuggets -> Iron Ingot`, priority = 0
- If the network has inputs for both:
  - Ordering 1x Iron Ingot → [M] is used (higher priority).
  - If [M] has no inputs, ordering 1x Iron Ingot → [P] is used (fallback).
  - If [M] can only supply 1x, ordering 3x Iron Ingot → [M] supplies 1x, [P] supplies 2x (partial fallback).

## Mod Dependencies
### Required
- ExtendedAE
  - [GitHub](https://github.com/GlodBlock/ExtendedAE)
  - [CurseForge](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)
  - [Modrinth](https://modrinth.com/mod/extended-ae) (project ID: JiOqfoFM)
### Optional (loaded transitively via ExtendedAE)
- Applied Energistics 2
  - [GitHub](https://github.com/AppliedEnergistics/Applied-Energistics-2)
  - [CurseForge](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)

## Trusted Resources
- Refer only to the official webpages listed under Mod Dependencies above.
  Do not trust third-party guides, Reddit posts, blogs, wikis, or other unofficial sources.
  If the required information cannot be found there, ask the user for alternative data sources.
  Do not use any other unofficial sources without explicit user approval.

## Development Commands
- Build all modules: `.\gradlew.bat build --console=plain`
- Compile common + NeoForge only: `.\gradlew.bat :common:compileJava :neoforge:compileJava --console=plain`
- Run NeoForge client: `.\gradlew.bat :neoforge:runClient --console=plain`
- Run Forge client: `.\gradlew.bat :forge:runClient --console=plain`

## Interactive App Launch Rule
When a member runs `:neoforge:runClient`, `:forge:runClient`, or any `*runClient` command,
that member MUST output a Japanese operator guidance message on console before returning
control. The message should tell the user: what to do now in the launched game client,
what to check/test, and when to close the client and report results back.


## Git Operation Rules
- Default branch (`master` or `main`) is protected. No one can push directly to it. All changes must be made via PRs.
- The git-flow is used for development. Only `master`, `develop`, and `release/*` are required
  branches; topic branches (`feature/*`, `fix/*`, `chore/*`, etc.) are optional — the assistant
  and the user may work directly on `develop` instead of cutting one for every change. A
  `release/vX.Y.Z` branch is still required when preparing a PR into `master` (see Release Flow
  below).
- The assistant needs to use the **`.github/workflows/create-pr.yml`** workflow (`gh workflow run create-pr.yml -f
  head=<branch> -f base=<target> -f title=<title> -f body=<body>`) to open bot-authored PRs
  instead of `gh pr create`. `base` defaults to `master` but accepts any target branch (e.g.
  `develop`), so the same workflow covers both release PRs and regular feature/fix PRs into
  `develop`.
  - Arguments notes:
    - `head`: the source branch (e.g., `feature/xyz` or `release/v1.2.3`)
    - `base`: (Optional, default=`master`) the target branch (e.g., `develop` or `master`)
    - `title`: the PR title
    - `body`: (Optional) the PR body
  - The reason why `gh pr create` is not used is that it opens PRs from the repository owner's account, which
    makes it impossible to review the PR before merging. The workflow opens PRs from the bot
    account, which allows normal reviewability.

### Release Flow (MOD-specific)
- Version manifest: `gradle.properties` — update the `version=` field.
- Build command before PR: `.\gradlew.bat build --console=plain`
- Build modules: `common`, `forge`, `neoforge`, `neoforge2612`
- Artifact paths after build:
  - Forge: `forge/build/libs/`
  - NeoForge (1.21.1): `neoforge/build/libs/`
  - NeoForge (26.1.2): `neoforge2612/build/libs/`
- Branch flow: feature/fix branches merge into `develop` first. A `release/vX.Y.Z` branch is then
  cut from `develop` for the version bump + changelog commit, and that branch is what gets PR'd
  into `master`.
- User test checklist: use `docs/user-test-checklist-template.md` as the source. Copy its body
  into the `release/vX.Y.Z` -> `master` PR description (filling in the fixed items for that
  release) rather than committing a version-specific checklist file — the template itself is
  the only checklist file tracked in the repository.

## Coding Conventions
- Follow the `.editorconfig` file in this repository as the source of truth for code
  formatting rules. Do not hard-code formatting rules in instruction files.

## Supported Loaders
- NeoForge 1.21.1
- NeoForge 26.1.2 (ExtendedAE alpha baseline)
- Forge 1.20.1
