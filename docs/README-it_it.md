# AE Improved Crafting Prioritization
Questa è una mod aggiuntiva per AE2.
Questa mod migliora il comportamento di priorità del crafting a livello di provider per Interfaccia per modelli ME.

## Mod dipendenti
### Richiesto
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Opzionale
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## Funzione 1: Migliorare la risoluzione degli ingredienti
### Comportamento di AE2
- Puoi registrare più ricette per lo stesso output, ad esempio `8x Oak Planks -> Chest` e `8x Cherry Planks -> Chest`.
- Se imposti priorità di crafting 1 sul Interfaccia per modelli ME per la ricetta Oak Planks e 2 per la ricetta Cherry Planks, il tuo sistema di autocrafting controlla prima **Cherry Planks** e poi Oak Planks.

| Quantità in stock<br>di Oak Plank | Quantità in stock<br>di Cherry Plank | Ordinato | Lavoro di crafting |
| --- | --- | --- | --- |
|  8 |  0 | 1x Chest | Funziona con 8x Oak Planks |
|  0 | 24 | 3x Chest | Funziona con 24x Cherry Planks |
|  8 | 16 | 3x Chest | :no_entry: **Ingredienti mancanti!**<br>Servono altri 8x Cherry Planks |

### Comportamento di questa mod
Questa mod risolve questa situazione. Potrai ordinare 3 Chests con 8x Oak Planks e 16x Cherry Planks.
#### Scenario del creatore della mod
- Ordinare molti lingotti di ferro o Osmium Ingots in **Mekanism**, avendo anche **Mystical Agriculture**.
- Imposterei una priorità più alta per il Pattern di creazione con Essence e più bassa per il Pattern di fusione con dusts.

## Funzione 2: Matrix Assembler ha la priorità
### Comportamento di ExtendedAE
Il Matrix Assembler di ExtendedAE non ha un menu di priorità.
### Comportamento di questa mod
Ora lo ha.
#### Scenario del creatore della mod
Adoro Matrix Assembler e ci inserisco molte ricette. Non mi interessano i dettagli; imposto priorità 1000 per quelle.
- La stessa cosa che ho scritto sopra dovrebbe funzionare bene: Essence vs smelting dusts.
- Funziona anche per Quarzo Certus. Usa prima Essence, poi esegui il ciclo con Crusher e Camera di reazione per il resto.

