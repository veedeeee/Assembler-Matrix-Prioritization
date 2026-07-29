* Japanese description is in below

# Matrix Assembler Prioritization

An add-on for [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2) and [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider) that adds a priority setting to the Matrix Assembler.

## Dependencies

### Required
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

### Optional (loaded transitively via ExtendedAE)
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)

## Feature: Matrix Assembler Priority

### ExtendedAE behavior
The Matrix Assembler from ExtendedAE does not have a priority menu. There is no way to control which Matrix Assembler is preferred when multiple assemblers can produce the same output.

### This MOD's behavior
Adds a priority UI to the Matrix Assembler — the same UI used by the ME Storage Bus. Higher number means higher priority. Default priority is 0.

When multiple Matrix Assemblers (or a Matrix Assembler and a Pattern Provider) can craft the same item, the one with the higher priority is used first. If the highest-priority provider cannot fully satisfy the request due to insufficient materials, the system falls back to lower-priority providers.

#### Example scenarios
- Put priority 1000 on your Matrix Assemblers to always prefer them over other providers.
- Use Certus Quartz Essence (high priority) first, then fall back to the Crusher + Reaction Chamber loop (lower priority) for the rest.

---

# Matrix Assembler Prioritization（日本語）

[Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2) と [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider) のアドオン MOD です。組立マトリックスに優先度設定を追加します。

## 前提 MOD

### 必須
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

### 任意（ExtendedAE 経由で間接的に必要）
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)

## 機能: 組立マトリックスの優先度設定

### ExtendedAE の挙動
ExtendedAE の組立マトリックスには優先度メニューがありません。同じ出力を複数の組立マトリックスが生産できる場合、どちらが優先されるかを制御する手段がありません。

### この MOD の挙動
組立マトリックスに ME ストレージバスと同じ優先度 UI を追加します。数字が大きいほど優先度が高く、デフォルトは 0 です。

複数の組立マトリックス（またはパターンプロバイダーとの組み合わせ）が同じアイテムをクラフトできる場合、優先度の高い方が先に使用されます。優先度の高いプロバイダーの素材が不足している場合は、低優先度のプロバイダーにフォールバックします。

#### 想定シナリオ
- 組立マトリックスに優先度 1000 を設定して、他のプロバイダーより常に優先させる。
- ケルタスクォーツ: まずエッセンス（高優先度）を使い、足りない分は粉砕機＋リアクションチャンバーのループ（低優先度）で補う。
