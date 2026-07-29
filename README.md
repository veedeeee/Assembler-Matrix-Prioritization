* Japanese desciption is in below
* Other languages are in [docs/](docs/) directory. Machine translated.

# AE2: Improved Crafting Prioritization
This improves provider-level crafting priority behavior for ME Pattern Provider.

## Dependencies MODs
### Required
- [https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2](Applied Energistics 2)
### Optional
- [https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider](ExtendedAE)

## Function 1: Improve ingredients resolving
### AE2 behavior
- You can register multiple recipes for same output. Like `8x Oak Planks -> Chest` and `8x Cherry Planks -> Chest`.
- If you put 1 as crafting priority on the ME Pattern Provider for the Oak Planks recipe, and you put 2 for the Cherry Planks recipe, your autocrafting system sees **Cherry Planks first**, then Oak Planks.

| Stocked qty<br>of Oak Plank | Stocked qty<br>of Cherry Plank | Ordered | Crafting Work |
| --- | --- | --- | --- |
|  8 |  0 | 1x Chest | Works with 8x Oak Planks |
|  0 | 24 | 3x Chest | Works with 24x Cherry Planks |
|  8 | 16 | 3x Chest | :no_entry: **Missing ingredients!**<br>Needs 8x more Cherry Planks |

### This MOD's behavior
This mod solves this situation. You will be able to order 3 Chests with 8x Oak Planks and 16x Cherry Planks.
#### MOD Creator's scenario
- Ordering a bunch of Iron Ingots or Osumium Ingots on **Mekanism**. Also having **Mystical Agriculture**.
- I would put higher priority for the Crafting Pattern with Essence, and lower priority for the Smelting Pattern with dusts.

## Function 2: Matrix Assembler is having priority
### ExtendedAE behavior
The Matrix Assembler from ExtendedAE doesn't have prioritize menu.
### This MOD's behavior
Now, it is having.
#### MOD Creator's scenario
Yes I love Matrix Assembler and I put many recipes into it. I don't care details. Put 1000 priority for those.
- Same thing I wrote in avobe should work fine. Essence vs smelting Dusts.
- Also works for Certus Quartz. Use Essence first. Then run the loop with Crusher and Reaction Chamber for the rest.

---

# AE Improved Crafting Prioritization
これは AE2 向けのアドオン MOD です。
MEパターンプロバイダー に対する、プロバイダー単位のクラフト優先度の挙動を改善します。

## 前提MOD
### 必須
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### 任意
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## 機能 1: 素材解決の改善
### AE2 の挙動
- 同じ出力に対して複数レシピを登録できます。例: `8x オークの板材 -> チェスト` と `8x サクラの板材 -> チェスト`。
- オークの板材レシピのMEパターンプロバイダーにクラフト優先度 1、サクラの板材レシピに 2 を設定すると、オートクラフトは **サクラの板材を先に** 確認します。その後にオークの板材を見ます。

| オークの板材<br>の在庫数 | サクラの板材<br>の在庫数 | 注文 | クラフト結果 |
| --- | --- | --- | --- |
|  8 |  0 | 1x チェスト | 8x オークの板材 でクラフト可能 |
|  0 | 24 | 3x チェスト | 24x サクラの板材 でクラフト可能 |
|  8 | 16 | 3x チェスト | :no_entry: **素材不足!**<br>サクラの板材 がさらに 8x 必要 |

### この MOD の挙動
この MOD はこの状況を解決します。8x オークの板材 と 16x サクラの板材 を使った 3 個の チェスト 発注が可能になります。
#### MOD 制作者の想定シナリオ
- **Mystical Agriculture**導入環境で、鉄インゴットやオスミウムインゴットを大量注文する場合
- エッセンスを使うクラフトパターンを高優先度に、ダストを使う精錬パターンを低優先度に設定しておけば、可能な限りエッセンスでのクラフトをする

## 機能 2: 組立マトリックス の優先度対応
### ExtendedAE の挙動
ExtendedAE の 組立マトリックス には優先度メニューがありません。
### この MOD の挙動
この MOD で優先度を設定できるようになります。
#### MOD 制作者の想定シナリオ
組立マトリックスにとにかくレシピを突っ込んで、優先度は適当に1000とかにしておく。
- 上で書いたエッセンスと精錬の使い分けも問題なく動作します。
- ケルタスクォーツでも有効。まずある限りのエッセンスを使い、残りは粉砕機とリアクションチャンバーのループで増殖させる。
