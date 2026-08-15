# User Test Checklist Template

This is a reusable template for release-readiness user testing. It is **not** meant to be
copied into a version-specific file and committed to the repository — instead:

1. Copy the checklist body below into the release PR description (the PR created for
   `release/vX.Y.Z` -> `master`), filling in the "Fixed in this release" section with the
   actual bugs/changes being validated.
2. Have the user run through the checklist and record results directly in the PR body/comments.
3. Once the release PR is merged, the filled-in checklist lives in the PR history — no
   separate file needs to be kept in the repository.

---

## User Test Checklist — vX.Y.Z

### Fixed in this release
- (list the bugs/changes being validated in this release)

Target loaders: Forge 1.20.1 / NeoForge 1.21.1 / NeoForge 26.1.2 (verify on all three unless a
change is loader-specific)

### Prerequisites

- [ ] Installed ExtendedAE, AE2 (transitive), JEI, Jade, GuideME matching the target loader/version
- [ ] Built at least one Assembler Matrix (multi-pattern capable)
- [ ] Prepared a Pattern Provider (lower priority) that can produce the same output

### 1. Basic functionality

- [ ] Assembler Matrix priority UI opens without crash/freeze
- [ ] A priority value (e.g. 100) can be entered and saved
- [ ] Ordering an item that both the (higher-priority) Assembler Matrix and (lower-priority)
      Pattern Provider can produce → **Assembler Matrix is used**
- [ ] With the Assembler Matrix's inputs emptied, ordering the same item → **falls back to the
      Pattern Provider**
- [ ] Ordering more than the Assembler Matrix can supply alone → **the shortfall is filled by
      the Pattern Provider (partial fallback)**

### 2. Behavior on exit/restart

- [ ] Set a priority value (e.g. 200) on the Assembler Matrix
- [ ] Quit the game (leave the world or close the client)
- [ ] Relaunch and re-enter the same world
- [ ] Priority UI still shows the value that was set (200) — **not reset**
- [ ] Previously registered patterns are still present
- [ ] Input/output inventory contents are unchanged

### 3. Result summary

| Environment | 1. Basic functionality | 2. Exit/restart behavior | Notes |
| --- | --- | --- | --- |
| Forge 1.20.1 | OK / NG | OK / NG | |
| NeoForge 1.21.1 | OK / NG | OK / NG | |
| NeoForge 26.1.2 | OK / NG | OK / NG | |

If any item is NG, attach the relevant environment's `logs/latest.log`.
