# Release Test Scenarios

This document defines release-readiness test scenarios for **AE2: Improved Crafting Prioritization**.

## Scope and Axes

- Primary matrix axis: `AE2 official loader/version support`.
- `Applied Energistics 2` is required in every scenario.
- `ExtendedAE` is only included where the target loader/version line supports it.

## Dependency Mod Requirements

The following dependency mods are **mandatory** in every test scenario. They are not optional and
must not be treated as smoke-check-only.

| Mod | Role | Loader-match rule |
| --- | --- | --- |
| GuideME | AE2 guide overlay | MUST use the build targeting the same ModLoader as the scenario |
| Jade | In-world tooltip overlay | MUST use the build targeting the same ModLoader as the scenario |
| JEI | Recipe viewer integration | MUST use the build targeting the same ModLoader as the scenario |

Each dependency mod artifact MUST target the same ModLoader as the scenario being tested. Using a
Fabric build of JEI/Jade/GuideME in a NeoForge or Forge scenario (or vice-versa) is not
permitted — even if the game launches without an immediate crash.

### Pinned Modrinth version coordinates — NeoForge 1.21.1

These coordinates are confirmed via Modrinth API (`loaders=["neoforge"]`, `game_versions=["1.21.1"]`):

| Mod | Modrinth coordinate | Version number | Confirmed filename |
| --- | --- | --- | --- |
| JEI | `maven.modrinth:jei:sc43sMLj` | `19.42.0.385` | `jei-1.21.1-neoforge-19.42.0.385.jar` |
| Jade | `maven.modrinth:jade:15.10.5+neoforge` | `15.10.5+neoforge` | `Jade-1.21.1-NeoForge-15.10.5.jar` |
| GuideME | `maven.modrinth:guideme:rduAfwb7` | `21.1.17` | `guideme-21.1.17.jar` |

> **Note — JEI coordinate format**: `version_number` `19.42.0.385` exists for both NeoForge and
> Fabric on Modrinth. The Modrinth Maven coordinate MUST use the version `id` (`sc43sMLj`) to
> guarantee NeoForge artifact resolution.

### Forge 1.20.1 / Fabric 1.20.1

Coordinates for Forge and Fabric loaders will be determined when those scenarios are prepared.
The same loader-match requirement applies; do not reuse NeoForge IDs for other loaders.

## AE2 Requirement Snapshot

Current official AE2 downloads expose these relevant lines:

- `1.21.1`: NeoForge
- `1.20.1`: Forge / Fabric

The previous `1.21`-based matrix is aborted because it does not match the official AE2 requirement set.

## Scenario Matrix

| Scenario ID | Minecraft | Loader | AE2 | ExtendedAE profile | Required checks |
| --- | --- | --- | --- | --- | --- |
| A-NF-211 | 1.21.1 | NeoForge | installed | off | F1, F2, F3 |
| B-NF-211 | 1.21.1 | NeoForge | installed | on | F1, F2, F3 |
| A-FG-201 | 1.20.1 | Forge | installed | off | F1, F2, F3 |
| B-FG-201 | 1.20.1 | Forge | installed | on | F1, F2, F3 |
| A-FB-201 | 1.20.1 | Fabric | installed | off | F1, F3 |

## Common Setup

1. Start a new test world with AE2 installed and available for the target loader/version line.
2. Build a minimal ME network with autocrafting.
3. Prepare at least two providers that can craft the same output:
   - one high-priority provider
   - one lower-priority provider
4. For ExtendedAE-enabled scenarios, add an Assembler Matrix setup with valid patterns.

## Functional Checks

### F1. AE2 Pattern Provider Priority

Steps:
1. Open provider priority UI and assign distinct priorities (example: 100 and 10).
2. Request an item both providers can craft.
3. Observe provider usage order.

Expected:
- Higher priority provider is selected first.
- If top provider cannot satisfy full amount, lower priority provider is used for remainder.
- Priority screen title shows crafting-priority wording.

Failure signals:
- Equal/undefined selection despite different priorities with both providers capable.
- Planner stops instead of continuing to lower-priority provider.
- Priority UI cannot open from provider screen.

### F2. ExtendedAE Assembler Matrix Priority Behavior

Steps:
1. Open Assembler Matrix UI and open priority screen.
2. Set matrix priority higher than another provider for same output.
3. Request craft with enough inputs for both providers.
4. Repeat with top-priority matrix lacking enough inputs.

Expected:
- Higher-priority matrix/provider is consumed first.
- Planning continues to lower-priority candidates when top choice cannot fully satisfy.
- No crafting-planner collapse to a single effective candidate.

Failure signals:
- Priority setting does not affect provider order.
- Partial insufficiency on top provider causes planning failure without fallback.

### F3. Assembler Matrix UI/Open-Path Stability

Steps:
1. Open Assembler Matrix screen repeatedly.
2. Verify the priority button is visible and clickable.
3. Open/close the priority screen multiple times.

Expected:
- No crash when opening Assembler Matrix or priority screen.
- `openPriority` widget is present and stable in the intended top-right area.
- No overlap that prevents interaction with required controls.

Failure signals:
- `Screen is missing required widget: openPriority`.
- open-screen handling errors, GUI freeze, or immediate client crash.
- Priority button disappears, is inverted, or is not interactable.

## Exit Criteria

- All applicable scenarios from the official AE2 matrix pass.
- No critical crash in client logs during scenario execution.
- Priority behavior is consistent with provider-level priority semantics across loaders.
