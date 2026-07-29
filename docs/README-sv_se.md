# AE2: Improved Crafting Prioritization
Detta är en tilläggsmod för AE2.
Den här modden förbättrar beteendet för crafting-prioritet på provider-nivå för Mönsterleverantör för ME.

## Beroende mods
### Krävs
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Valfritt
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## Funktion 1: Förbättra lösning av ingredienser
### AE2-beteende
- Du kan registrera flera recept för samma output, till exempel `8x Oak Planks -> Chest` och `8x Cherry Planks -> Chest`.
- Om du sätter crafting-prioritet 1 på Mönsterleverantör för ME för Oak Planks-receptet och 2 för Cherry Planks-receptet, kontrollerar ditt autocrafting-system **Cherry Planks först**, sedan Oak Planks.

| Lagrad mängd<br>Oak Plank | Lagrad mängd<br>Cherry Plank | Beställt | Crafting-arbete |
| --- | --- | --- | --- |
|  8 |  0 | 1x Chest | Fungerar med 8x Oak Planks |
|  0 | 24 | 3x Chest | Fungerar med 24x Cherry Planks |
|  8 | 16 | 3x Chest | :no_entry: **Saknade ingredienser!**<br>Behöver 8x fler Cherry Planks |

### Den här moddens beteende
Den här modden löser den här situationen. Du kan beställa 3 Chests med 8x Oak Planks och 16x Cherry Planks.
#### Moddskaparens scenario
- Beställa många järntackor eller Osmium Ingots i **Mekanism**, samtidigt som **Mystical Agriculture** också används.
- Jag skulle sätta högre prioritet för Hantverksmönster med Essence och lägre prioritet för Smältmönster med dusts.

## Funktion 2: Matrix Assembler får prioritet
### ExtendedAE-beteende
Matrix Assembler från ExtendedAE har ingen prioritetsmeny.
### Den här moddens beteende
Nu har den det.
#### Moddskaparens scenario
Jag gillar Matrix Assembler mycket och lägger in många recept i den. Jag bryr mig inte om detaljer; jag sätter prioritet 1000 för dessa.
- Samma sak som jag skrev ovan bör fungera bra: Essence vs. smelting dusts.
- Fungerar också för Certus-kvarts. Använd Essence först, kör sedan loopen med Crusher och Reaktionskammare för resten.

