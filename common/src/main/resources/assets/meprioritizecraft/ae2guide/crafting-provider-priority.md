---
navigation:
  title: Crafting Provider Priority
  icon: ae2:pattern_provider
  parent: index.md
---

# Crafting Provider Priority

AE2: Improved Crafting Prioritization adds AE2-style provider priority behavior for crafting providers.

It covers:
- AE2 Pattern Providers
- ExtendedAE Assembler Matrix providers

## What this changes

- Priority applies to each pattern provider, not to the crafting job itself.
- Higher numbers are preferred first when multiple providers can make the same output.
- The default priority is `0`.
- Providers without a custom setting behave as priority `0`.
- If priorities are equal, Applied Energistics 2 keeps using its normal selection logic.
- If the top-priority provider cannot fully satisfy the request, the planner continues to lower-priority providers and combines them in priority order.

## Example behavior

If provider A has priority `100` and provider B has priority `10`, provider A is consumed first.  
When provider A runs out of required inputs, the planner keeps using provider B for the remaining amount instead of failing or selecting only one effective candidate.
