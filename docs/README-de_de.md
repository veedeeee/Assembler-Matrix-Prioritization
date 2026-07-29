# AE2: Improved Crafting Prioritization
Dies ist ein Add-on-Mod für AE2.
Dieser Mod verbessert das Prioritätsverhalten beim Crafting auf Provider-Ebene für den ME Schablonen-Provider.

## Abhängige Mods
### Erforderlich
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Optional
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## Funktion 1: Auflösung von Zutaten verbessern
### AE2-Verhalten
- Du kannst mehrere Rezepte für dasselbe Ergebnis registrieren, z. B. `8x Eichenholzbretter -> Truhe` und `8x Kirschholzbretter -> Truhe`.
- Wenn du beim Eichenholzbretter-Rezept am ME Schablonen-Provider die Crafting-Priorität 1 und beim Kirschholzbretter-Rezept 2 setzt, prüft dein Autocrafting-System zuerst **Kirschholzbretter**, dann Eichenholzbretter.

| Vorrat<br>Eichenholzbretter | Vorrat<br>Kirschholzbretter | Bestellung | Crafting-Ergebnis |
| --- | --- | --- | --- |
|  8 |  0 | 1x Truhe | Funktioniert mit 8x Eichenholzbretter |
|  0 | 24 | 3x Truhe | Funktioniert mit 24x Kirschholzbretter |
|  8 | 16 | 3x Truhe | :no_entry: **Zutaten fehlen!**<br>Benötigt 8x mehr Kirschholzbretter |

### Verhalten dieses Mods
Dieser Mod löst diese Situation. Du kannst 3 Truhe mit 8x Eichenholzbretter und 16x Kirschholzbretter bestellen.
#### Szenario des Mod-Erstellers
- Viele Eisenbarren oder Osmium Ingots in **Mekanism** bestellen und zusätzlich **Mystical Agriculture** nutzen.
- Ich würde für das Herstellungsmuster mit Essence eine höhere Priorität setzen und für das Schmelzmuster mit Dusts eine niedrigere.

## Funktion 2: Matrix Assembler erhält Priorität
### ExtendedAE-Verhalten
Der Matrix Assembler aus ExtendedAE hat kein Prioritätsmenü.
### Verhalten dieses Mods
Jetzt hat er eins.
#### Szenario des Mod-Erstellers
Ich mag den Matrix Assembler sehr und habe viele Rezepte darin. Details sind mir egal; ich setze dafür Priorität 1000.
- Dasselbe wie oben beschrieben sollte gut funktionieren: Essence vs. Schmelz-Dusts.
- Funktioniert auch für Certus-Quarz. Zuerst Essence verwenden, dann den Rest mit Crusher und Reaktionskammer im Loop verarbeiten.
