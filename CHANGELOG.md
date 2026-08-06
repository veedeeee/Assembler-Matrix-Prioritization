v1.0.4
- ADD: Added an additional NeoForge target for Minecraft `26.1.2` (`neoforge2612` module, NeoForge `26.1.2.94`) while keeping existing `1.21.1` NeoForge support.
- MODIFY: Expanded CI/release automation to build and publish the new `26.1.2` NeoForge artifact in parallel with existing Forge/NeoForge outputs.
- NOTE: ExtendedAE for `26.1.2` is currently tracked via alpha baseline (`26.1.2-neoforge`) for local runtime validation.

v1.0.3
- FIX: Crush on Forge/1.20.1 because this mod is requesting `JAVA_18` even Minecraft 1.20.1 runs on `JAVA_17`

v1.0.2
- MODIFY: Change the target of the version of Forge to `47.4.10` from `47.4.22`.
- MODIFY: Refer the ExtendedAE as Required Dependency.
- Only documentation/configuration change. No code level change.

v1.0.1
- Actually this is the first version. v1.0.0? what's that?
