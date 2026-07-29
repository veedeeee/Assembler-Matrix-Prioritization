# AE2: Improved Crafting Prioritization
Ez egy kiegészítő mod az AE2-höz.
Ez a mod javítja a szolgáltató szintű crafting prioritási működést a ME Pattern Provider esetén.

## Függő modok
### Kötelező
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Opcionális
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## 1. funkció: Az összetevő-feloldás javítása
### AE2 viselkedése
- Ugyanahhoz a kimenethez több receptet is regisztrálhatsz, például `8x Oak Planks -> Chest` és `8x Cherry Planks -> Chest`.
- Ha az Oak Planks recepthez 1-es crafting prioritást, a Cherry Planks recepthez pedig 2-est állítasz a ME Pattern Provideren, az automata crafting rendszer először a **Cherry Planks** receptet nézi, majd az Oak Planks receptet.

| Raktáron lévő<br>Oak Plank mennyiség | Raktáron lévő<br>Cherry Plank mennyiség | Rendelés | Crafting eredmény |
| --- | --- | --- | --- |
|  8 |  0 | 1x Chest | Működik 8x Oak Planks használatával |
|  0 | 24 | 3x Chest | Működik 24x Cherry Planks használatával |
|  8 | 16 | 3x Chest | :no_entry: **Hiányzó összetevők!**<br>Még 8x Cherry Planks szükséges |

### A mod viselkedése
Ez a mod megoldja ezt a helyzetet. 8x Oak Planks és 16x Cherry Planks mellett is tudsz 3 Chests rendelni.
#### A mod készítőjének példája
- Sok vasrudak vagy Osmium Ingots rendelése **Mekanism** alatt, miközben **Mystical Agriculture** is telepítve van.
- Az Essence-t használó Készítési minta magasabb prioritást kapna, a dusts-t használó Olvasztási minta alacsonyabbat.

## 2. funkció: A Matrix Assembler prioritást kap
### ExtendedAE viselkedése
Az ExtendedAE Matrix Assemblerében nincs prioritási menü.
### A mod viselkedése
Most már van.
#### A mod készítőjének példája
Nagyon szeretem a Matrix Assemblert, és sok receptet teszek bele. A részletek nem számítanak; ezekre 1000-es prioritást adok.
- Ugyanaz, amit fent írtam, jól kell működjön: Essence vs. smelting dusts.
- Certus kvarc esetén is működik. Először Essence-t használj, majd a maradékhoz futtasd a loopot Crusherrel és Reakciókamrával.

