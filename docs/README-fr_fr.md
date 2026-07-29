# AE2: Improved Crafting Prioritization
Ceci est un mod complémentaire pour AE2.
Ce mod améliore le comportement de priorité d'artisanat au niveau du fournisseur pour le Fournisseur de pattern ME.

## Mods de dépendance
### Requis
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Optionnel
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## Fonction 1 : Améliorer la résolution des ingrédients
### Comportement d'AE2
- Vous pouvez enregistrer plusieurs recettes pour la même sortie, par exemple `8x Planches de chêne -> Coffre` et `8x Planches de cerisier -> Coffre`.
- Si vous définissez la priorité d'artisanat à 1 sur le Fournisseur de pattern ME pour la recette Planches de chêne et à 2 pour la recette Planches de cerisier, votre système d'autocraft vérifie **Planches de cerisier d'abord**, puis Planches de chêne.

| Quantité stockée<br>d'Planches de chêne | Quantité stockée<br>de Planches de cerisier | Commande | Travail d'artisanat |
| --- | --- | --- | --- |
|  8 |  0 | 1x Coffre | Fonctionne avec 8x Planches de chêne |
|  0 | 24 | 3x Coffre | Fonctionne avec 24x Planches de cerisier |
|  8 | 16 | 3x Coffre | :no_entry: **Ingrédients manquants !**<br>Nécessite 8x Planches de cerisier de plus |

### Comportement de ce mod
Ce mod résout cette situation. Vous pourrez commander 3 Coffre avec 8x Planches de chêne et 16x Planches de cerisier.
#### Scénario du créateur du mod
- Commander beaucoup de lingots de fer ou d'Osmium Ingots dans **Mekanism**, tout en ayant aussi **Mystical Agriculture**.
- Je mettrais une priorité plus élevée pour le pattern d'artisanat avec Essence, et une priorité plus basse pour le pattern de fusion avec dusts.

## Fonction 2 : Matrix Assembler obtient une priorité
### Comportement d'ExtendedAE
Le Matrix Assembler d'ExtendedAE n'a pas de menu de priorité.
### Comportement de ce mod
Maintenant, il en a un.
#### Scénario du créateur du mod
J'adore Matrix Assembler et j'y mets beaucoup de recettes. Je ne me soucie pas des détails ; je mets une priorité de 1000 pour celles-ci.
- La même chose que j'ai écrite ci-dessus devrait bien fonctionner : Essence vs smelting dusts.
- Cela fonctionne aussi pour Quartz de Certus. Utilisez d'abord Essence, puis lancez la boucle avec Crusher et Chambre de réaction pour le reste.

