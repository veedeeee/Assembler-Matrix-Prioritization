# AE Improved Crafting Prioritization
Bu, AE2 için bir eklenti modudur.
Bu mod, ME Şablon Sağlayıcı için sağlayıcı seviyesindeki crafting önceliği davranışını iyileştirir.

## Bağımlı modlar
### Gerekli
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### İsteğe bağlı
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## İşlev 1: Malzeme çözümlemeyi iyileştirme
### AE2 davranışı
- Aynı çıktı için birden fazla tarif kaydedebilirsiniz; örneğin `8x Oak Planks -> Chest` ve `8x Cherry Planks -> Chest`.
- Oak Planks tarifi için ME Şablon Sağlayıcı üzerinde crafting önceliğini 1, Cherry Planks tarifi için 2 yaparsanız, otomatik crafting sisteminiz önce **Cherry Planks** tarifini, sonra Oak Planks tarifini kontrol eder.

| Depodaki miktar<br>Oak Plank | Depodaki miktar<br>Cherry Plank | Sipariş | Crafting işlemi |
| --- | --- | --- | --- |
|  8 |  0 | 1x Chest | 8x Oak Planks ile çalışır |
|  0 | 24 | 3x Chest | 24x Cherry Planks ile çalışır |
|  8 | 16 | 3x Chest | :no_entry: **Eksik malzeme!**<br>8x daha Cherry Planks gerekiyor |

### Bu modun davranışı
Bu mod bu durumu çözer. 8x Oak Planks ve 16x Cherry Planks ile 3 Chests sipariş edebilirsiniz.
#### Mod geliştiricisinin senaryosu
- **Mekanism** içinde çok sayıda Demir Külçesi veya Osmium Ingots sipariş etmek ve aynı zamanda **Mystical Agriculture** kullanmak.
- Essence kullanan Üretim Şablonu için daha yüksek, dusts kullanan Eritme Şablonu için daha düşük öncelik verirdim.

## İşlev 2: Birleştirici Matris öncelik alır
### ExtendedAE davranışı
ExtendedAE'deki Birleştirici Matris'da öncelik menüsü yoktur.
### Bu modun davranışı
Artık var.
#### Mod geliştiricisinin senaryosu
Birleştirici Matris'ı çok seviyorum ve içine birçok tarif koyuyorum. Ayrıntılar umurumda değil; bunlara 1000 öncelik veriyorum.
- Yukarıda yazdığım aynı şey iyi çalışmalı: Essence ve smelting dusts karşılaştırması.
- Certus Kuvarsı için de çalışır. Önce Essence kullanın, sonra kalanlar için Crusher ve Reaksiyon Odası döngüsünü çalıştırın.

