# AE2: Improved Crafting Prioritization
Acesta este un mod add-on pentru AE2.
Acest mod îmbunătățește comportamentul priorității de crafting la nivel de provider pentru Furnizor Model ME.

## Moduri de dependență
### Necesar
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Opțional
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## Funcția 1: Îmbunătățirea rezolvării ingredientelor
### Comportamentul AE2
- Poți înregistra mai multe rețete pentru același rezultat, de exemplu `8x Oak Planks -> Chest` și `8x Cherry Planks -> Chest`.
- Dacă setezi prioritatea de crafting 1 pe Furnizor Model ME pentru rețeta Oak Planks și 2 pentru rețeta Cherry Planks, sistemul tău de autocrafting verifică mai întâi **Cherry Planks**, apoi Oak Planks.

| Cantitate stocată<br>de Oak Plank | Cantitate stocată<br>de Cherry Plank | Comandat | Execuție crafting |
| --- | --- | --- | --- |
|  8 |  0 | 1x Chest | Funcționează cu 8x Oak Planks |
|  0 | 24 | 3x Chest | Funcționează cu 24x Cherry Planks |
|  8 | 16 | 3x Chest | :no_entry: **Ingrediente lipsă!**<br>Mai sunt necesare 8x Cherry Planks |

### Comportamentul acestui mod
Acest mod rezolvă această situație. Vei putea comanda 3 Chests cu 8x Oak Planks și 16x Cherry Planks.
#### Scenariul creatorului modului
- Comandarea unui număr mare de lingouri de fier sau Osmium Ingots în **Mekanism**, având și **Mystical Agriculture**.
- Aș seta prioritate mai mare pentru Tipar de crafting cu Essence și prioritate mai mică pentru Tipar de topire cu dusts.

## Funcția 2: Matrix Assembler primește prioritate
### Comportamentul ExtendedAE
Matrix Assembler din ExtendedAE nu are meniu de prioritate.
### Comportamentul acestui mod
Acum are.
#### Scenariul creatorului modului
Îmi place foarte mult Matrix Assembler și pun multe rețete în el. Nu mă interesează detaliile; setez prioritate 1000 pentru acestea.
- Același lucru pe care l-am scris mai sus ar trebui să funcționeze bine: Essence vs. smelting dusts.
- Funcționează și pentru Cuarț Certus. Folosește mai întâi Essence, apoi rulează bucla cu Crusher și Cameră de reacție pentru restul.

