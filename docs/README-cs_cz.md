# AE Improved Crafting Prioritization
Toto je doplňkový mod pro AE2.
Tento mod zlepšuje chování priority craftění na úrovni provideru pro Poskytovatel ME gesta.

## Závislé mody
### Povinné
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Volitelné
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## Funkce 1: Zlepšení vyhodnocení ingrediencí
### Chování AE2
- Můžete zaregistrovat více receptů pro stejný výstup, například `8x Oak Planks -> Chest` a `8x Cherry Planks -> Chest`.
- Pokud nastavíte prioritu craftění 1 na Poskytovatel ME gestau pro recept Oak Planks a 2 pro recept Cherry Planks, váš autocrafting systém kontroluje nejdříve **Cherry Planks**, potom Oak Planks.

| Množství na skladě<br>Oak Plank | Množství na skladě<br>Cherry Plank | Objednáno | Průběh craftění |
| --- | --- | --- | --- |
|  8 |  0 | 1x Chest | Funguje s 8x Oak Planks |
|  0 | 24 | 3x Chest | Funguje s 24x Cherry Planks |
|  8 | 16 | 3x Chest | :no_entry: **Chybí ingredience!**<br>Je potřeba o 8x více Cherry Planks |

### Chování tohoto modu
Tento mod tuto situaci řeší. Budete moci objednat 3 Chests s 8x Oak Planks a 16x Cherry Planks.
#### Scénář autora modu
- Objednávání velkého množství Železných ingotů nebo Osmium Ingots v **Mekanism** a zároveň používání **Mystical Agriculture**.
- Nastavil bych vyšší prioritu pro Crafting vzor s Essence a nižší prioritu pro Tavicí vzor s dusts.

## Funkce 2: Matrix Assembler má prioritu
### Chování ExtendedAE
Matrix Assembler z ExtendedAE nemá menu priority.
### Chování tohoto modu
Teď ho má.
#### Scénář autora modu
Matrix Assembler mám rád a dávám do něj mnoho receptů. Detaily neřeším; pro tyto recepty nastavím prioritu 1000.
- Stejná věc, kterou jsem napsal výše, by měla fungovat dobře: Essence vs. smelting dusts.
- Funguje to také pro Certus křemen. Nejprve použijte Essence a pak spusťte smyčku s Crusher a Reakční komora pro zbytek.

