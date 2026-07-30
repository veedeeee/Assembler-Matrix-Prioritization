# MOD Publishing Checklist

Checklist for publishing Assembler Matrix Prioritization to CurseForge and Modrinth.

## Pre-publish (Both Platforms)

- [ ] Build artifacts are ready
  - [ ] `neoforge/build/libs/assembler-matrix-prioritization-neoforge-*.jar`
  - [ ] `forge/build/libs/assembler-matrix-prioritization-forge-*.jar`
- [ ] All release tests pass (see `release-test-scenarios.md`)
  - [ ] B-NF-211 PASS
  - [ ] B-FG-201 PASS
- [ ] MOD icon ready (256×256px, PNG, square)
- [ ] Changelog written

---

## CurseForge

### Account & Project Setup (first time only)

- [ ] Create/log in to CurseForge account at <https://authors.curseforge.com/>
- [ ] Create new project: <https://authors.curseforge.com/#/projects/create/choose-game>
  - [ ] Game: Minecraft
  - [ ] Class: Mods
  - [ ] Name: `Assembler Matrix Prioritization`
  - [ ] Summary: (short description)
  - [ ] Description: (English, sufficiently detailed)
  - [ ] License: select appropriate license
  - [ ] Main category: select appropriate category
  - [ ] Logo image: upload 256×256px icon
- [ ] Note down the **Project ID** (needed for Gradle automation)

### File Upload

- [ ] Navigate to project → Files tab → Upload File
- [ ] Upload NeoForge jar
  - [ ] File: `assembler-matrix-prioritization-neoforge-*.jar`
  - [ ] Display Name: e.g. `Assembler Matrix Prioritization vX.Y.Z (NeoForge 1.21.1)`
  - [ ] Release Type: `Release` / `Beta` / `Alpha`
  - [ ] Supported Versions: `1.21.1` + `NeoForge`
  - [ ] Changelog: paste changelog
  - [ ] Required Dependencies: `Applied Energistics 2`, `ExtendedAE`
- [ ] Upload Forge jar
  - [ ] File: `assembler-matrix-prioritization-forge-*.jar`
  - [ ] Display Name: e.g. `Assembler Matrix Prioritization vX.Y.Z (Forge 1.20.1)`
  - [ ] Release Type: `Release` / `Beta` / `Alpha`
  - [ ] Supported Versions: `1.20.1` + `Forge`
  - [ ] Changelog: paste changelog
  - [ ] Required Dependencies: `Applied Energistics 2`, `ExtendedAE`
- [ ] Confirm status changes to `Under Review`

### Post-approval

- [ ] Verify public URL: `https://www.curseforge.com/minecraft/mc-mods/<slug>`
- [ ] (Optional) Set up Gradle automation: `net.darkhax.curseforgegradle` plugin with API token + Project ID

---

## Modrinth

### Account & Project Setup (first time only)

- [ ] Create/log in to Modrinth account at <https://modrinth.com/>
- [ ] Create new project from dashboard
  - [ ] Name: `Assembler Matrix Prioritization`
  - [ ] Summary: (short description)
  - [ ] Description: (detailed)
  - [ ] License: select appropriate license
  - [ ] Categories: select appropriate categories
  - [ ] Icon: upload 256×256px icon
- [ ] Note down the **Project ID / slug** (needed for Gradle automation)

### Version Upload

- [ ] Navigate to project → Versions → Create version
- [ ] Upload NeoForge jar
  - [ ] File: `assembler-matrix-prioritization-neoforge-*.jar`
  - [ ] Version number: `vX.Y.Z`
  - [ ] Release channel: `Release` / `Beta` / `Alpha`
  - [ ] Game versions: `1.21.1`
  - [ ] Loaders: `NeoForge`
  - [ ] Changelog: paste changelog
  - [ ] Dependencies (required): `Applied Energistics 2`, `ExtendedAE`
- [ ] Upload Forge jar
  - [ ] File: `assembler-matrix-prioritization-forge-*.jar`
  - [ ] Version number: `vX.Y.Z`
  - [ ] Release channel: `Release` / `Beta` / `Alpha`
  - [ ] Game versions: `1.20.1`
  - [ ] Loaders: `Forge`
  - [ ] Changelog: paste changelog
  - [ ] Dependencies (required): `Applied Energistics 2`, `ExtendedAE`
- [ ] Confirm status changes to `processing` → `approved`

### Post-approval

- [ ] Verify public URL: `https://modrinth.com/mod/<slug>`
- [ ] (Optional) Set up Gradle automation: `com.modrinth.minotaur` plugin with PAT (`CREATE_VERSION` scope) + Project ID

---

## Automation Option (Both Platforms at Once)

- [ ] Set up `mc-publish` GitHub Action for simultaneous CurseForge + Modrinth release
  - Repository: <https://github.com/Kir-Antipov/mc-publish>
  - Requires: CurseForge API token, Modrinth PAT, both project IDs

---

## Notes

- Review time SLA is not officially stated for either platform; allow several days for first submission.
- First release requires project setup + first file upload. Subsequent releases only need file upload.
- Projects must have at least one `Release`-type file to sync to the CurseForge App.
