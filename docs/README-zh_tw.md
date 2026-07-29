# AE2: Improved Crafting Prioritization
這是 AE2 的附加模組。
此模組可改善 ME 樣板供應器 的提供者層級合成優先級行為。

## 相依模組
### 必要
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### 選用
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## 功能 1：改善材料解析
### AE2 的行為
- 你可以為相同輸出註冊多個配方，例如 `8x 橡木木板 -> 箱子` 與 `8x 櫻花木板 -> 箱子`。
- 若你在 Oak Planks 配方的 ME 樣板供應器 上設定合成優先級為 1，Cherry Planks 配方設定為 2，則自動合成系統會先檢查 **Cherry Planks**，再檢查 Oak Planks。

| 橡木木板<br>庫存數量 | 櫻花木板<br>庫存數量 | 訂單 | 合成執行 |
| --- | --- | --- | --- |
|  8 |  0 | 1x 箱子 | 可用 8x 橡木木板 合成 |
|  0 | 24 | 3x 箱子 | 可用 24x 櫻花木板 合成 |
|  8 | 16 | 3x 箱子 | :no_entry: **材料不足！**<br>還需要額外 8x 櫻花木板 |

### 此模組的行為
此模組可解決這種情況。你可以在只有 8x 橡木木板 與 16x 櫻花木板 時訂購 3 個 箱子。
#### 模組作者的情境
- 在 **Mekanism** 中大量訂購鐵錠或 Osmium Ingots，同時也安裝了 **Mystical Agriculture**。
- 我會將使用 Essence 的合成樣板設為較高優先級，使用 dusts 的熔煉樣板設為較低優先級。

## 功能 2：Matrix Assembler 具備優先級
### ExtendedAE 的行為
ExtendedAE 的 Matrix Assembler 沒有優先級選單。
### 此模組的行為
現在它有了。
#### 模組作者的情境
我非常喜歡 Matrix Assembler，並放入許多配方。我不在乎細節；我會為這些配方設定 1000 的優先級。
- 上面寫的情況應可正常運作：Essence 與 smelting dusts 的取捨。
- 對賽特斯石英也有效。先使用 Essence，再對剩餘部分執行 Crusher 與反應槽的循環。

