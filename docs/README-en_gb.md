# AE2: Improved Crafting Prioritization
This is an add-on mod for AE2.
This improves provider-level crafting priority behavior for ME Pattern Provider.

## Dependencies MODs
### Required
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Optional
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## Function 1: Improve ingredients resolving
### AE2 behavior
- You can register multiple recipes for the same output, such as `8x Oak Planks -> Chest` and `8x Cherry Planks -> Chest`.
- If you set crafting priority 1 on the ME Pattern Provider for the Oak Planks recipe and 2 for the Cherry Planks recipe, your autocrafting system checks **Cherry Planks first**, then Oak Planks.

| Stocked qty<br>of Oak Plank | Stocked qty<br>of Cherry Plank | Ordered | Crafting Work |
| --- | --- | --- | --- |
|  8 |  0 | 1x Chest | Works with 8x Oak Planks |
|  0 | 24 | 3x Chest | Works with 24x Cherry Planks |
|  8 | 16 | 3x Chest | :no_entry: **Missing ingredients!**<br>Needs 8x more Cherry Planks |

### This MOD's behavior
This mod solves this situation. You can order 3 Chests with 8x Oak Planks and 16x Cherry Planks.
#### MOD Creator's scenario
- Ordering many Iron Ingots or Osmium Ingots in **Mekanism**, while also having **Mystical Agriculture**.
- I would set higher priority for the Crafting Pattern with Essence, and lower priority for the Smelting Pattern with dusts.

## Function 2: Matrix Assembler gets priority
### ExtendedAE behavior
The Matrix Assembler from ExtendedAE does not have a priority menu.
### This MOD's behavior
Now it has one.
#### MOD Creator's scenario
I really like Matrix Assembler and put many recipes into it. I do not care about details; I set priority 1000 for those.
- The same thing I wrote above should work fine: Essence vs. smelting dusts.
- Also works for Certus Quartz. Use Essence first, then run the loop with Crusher and Reaction Chamber for the rest.

