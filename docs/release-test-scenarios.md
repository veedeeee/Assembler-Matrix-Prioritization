# Release Test Scenarios

This document defines release-readiness test scenarios for **Assembler Matrix Prioritization**.

## Scope and Axes

- Primary matrix axis: `AE2 official loader/version support`.
- `ExtendedAE` is **required** in every test scenario. Scenarios without ExtendedAE are out of scope.
- `Applied Energistics 2` is loaded transitively via ExtendedAE.
- F1 (AE2 Pattern Provider Priority) is native AE2 behavior and is **out of scope** for this MOD.
- All scenarios in this document assume ExtendedAE is installed and active.
- **Fabric is out of scope**: ExtendedAE does not implement Assembler Matrix on Fabric. No Fabric scenarios are included.

## Dependency Mod Requirements

The following dependency mods are **mandatory** in every test scenario. They are not optional and
must not be treated as smoke-check-only.

| Mod | Role | Loader-match rule |
| --- | --- | --- |
| ExtendedAE | Required MOD dependency (provides Assembler Matrix) | MUST use the build targeting the same ModLoader as the scenario |
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

### Fabric 1.21.1

Coordinates to be determined.

## AE2 Requirement Snapshot

Current official AE2 downloads expose these relevant lines:

- `1.21.1`: NeoForge
- `1.20.1`: Forge / ~~Fabric~~ (Fabric is out of scope for this MOD)

The previous `1.21`-based matrix is aborted because it does not match the official AE2 requirement set.

## Scenario Matrix

| Scenario ID | Minecraft | Loader | Required checks |
| --- | --- | --- | --- |
| B-NF-211 | 1.21.1 | NeoForge | F2, F3, T-CROSSTYPE-1, T-CROSSTYPE-2 |
| B-FG-201 | 1.20.1 | Forge | F2, F3, T-CROSSTYPE-1, T-CROSSTYPE-2 |

## Common Setup

1. Install ExtendedAE (required) along with AE2 (loaded transitively) for the target loader/version line.
2. Start a new test world with both mods installed and available.
3. Build a minimal ME network with autocrafting.
4. Prepare at least two providers that can craft the same output:
   - one high-priority Assembler Matrix
   - one lower-priority Pattern Provider
5. Add an Assembler Matrix setup with valid patterns.

## Functional Checks

### F1. AE2 Pattern Provider Priority

> **Out of scope for this MOD.** F1 is native AE2 behavior and is not a required check in any
> release scenario for Assembler Matrix Prioritization.

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

## Cross-Type Priority Test Scenarios

These scenarios validate cross-type priority selection and fallback when a Matrix Assembler
and a Pattern Provider can both produce the same output.

Applies to: B-NF-211, B-FG-201

### T-CROSSTYPE-1: Priority Selection — Matrix Assembler vs Pattern Provider

Setup:
- Matrix Assembler (priority = 100): produces Iron Ingot
- Pattern Provider (priority = 0, default): Crafting Pattern — 9x Iron Nuggets → Iron Ingot
- ME network contains inputs for both providers

Steps:
1. Order 1x Iron Ingot.
2. Confirm Matrix Assembler was used (higher priority).
3. Remove Matrix Assembler inputs from the network.
4. Order 1x Iron Ingot again.
5. Confirm Pattern Provider was used as fallback.

Expected:
- Step 2: Matrix Assembler (higher priority) is selected.
- Step 5: Pattern Provider (lower priority) is used as fallback when Assembler cannot satisfy.

Failure signals:
- Pattern Provider selected when Matrix Assembler can satisfy (priority ignored).
- Crafting fails when Assembler inputs are absent (no fallback to Pattern Provider).

### T-CROSSTYPE-2: Partial Fallback — Matrix Assembler Insufficient Stock

Setup:
- Matrix Assembler (priority = 100): produces Iron Ingot; inputs for only 1 unit available.
- Pattern Provider (priority = 0): Crafting Pattern — 9x Iron Nuggets → Iron Ingot; 18+ Iron Nuggets in stock.

Steps:
1. Order 3x Iron Ingot.
2. Inspect the crafting plan: confirm Matrix Assembler is assigned 1 unit and Pattern Provider is assigned 2 units.
3. Start crafting and confirm 3x Iron Ingot produced.

Expected:
- Crafting plan splits: Matrix Assembler handles 1x, Pattern Provider handles 2x.
- Combined result: 3x Iron Ingot completed.

Failure signals:
- Crafting plan assigns all 3 to Matrix Assembler and fails (no fallback split).
- Crafting plan assigns all 3 to Pattern Provider (priority ignored).
- Planning error or planner collapse when both providers are available but Assembler is insufficient alone.

## Exit Criteria

- Both scenarios pass: B-NF-211, B-FG-201.
- No critical crash in client logs during scenario execution.
- Priority behavior is consistent with provider-level priority semantics across loaders.
- T-CROSSTYPE-1 and T-CROSSTYPE-2 pass on B-NF-211, B-FG-201.