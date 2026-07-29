# AE2: Improved Crafting Prioritization
这是一个用于 AE2 的附加模组。
该模组改进了 ME样板供应器 的提供器级合成优先级行为。

## 依赖模组
### 必需
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### 可选
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## 功能 1：改进材料解析
### AE2 的行为
- 你可以为同一产物注册多个配方，例如 `8x 橡木木板 -> 箱子` 和 `8x 樱花木板 -> 箱子`。
- 如果你在 Oak Planks 配方的 ME样板供应器 上设置合成优先级为 1，在 Cherry Planks 配方上设置为 2，那么自动合成系统会先检查 **Cherry Planks**，再检查 Oak Planks。

| 橡木木板<br>库存数量 | 樱花木板<br>库存数量 | 订购 | 合成执行 |
| --- | --- | --- | --- |
|  8 |  0 | 1x 箱子 | 可使用 8x 橡木木板 合成 |
|  0 | 24 | 3x 箱子 | 可使用 24x 樱花木板 合成 |
|  8 | 16 | 3x 箱子 | :no_entry: **材料不足！**<br>还需要额外 8x 樱花木板 |

### 本模组的行为
该模组可解决此情况。你可以在只有 8x 橡木木板 和 16x 樱花木板 时订购 3 个 箱子。
#### 模组作者的场景
- 在 **Mekanism** 中批量订购铁锭或 Osmium Ingots，同时也安装了 **Mystical Agriculture**。
- 我会给使用 Essence 的合成样板更高优先级，给使用 dusts 的冶炼样板更低优先级。

## 功能 2：装配矩阵 支持优先级
### ExtendedAE 的行为
ExtendedAE 的 装配矩阵 没有优先级菜单。
### 本模组的行为
现在它有了。
#### 模组作者的场景
我非常喜欢 装配矩阵，并且往里面放了很多配方。我不在意细节；我会给这些配方设置 1000 的优先级。
- 上面写到的情况应当可以正常工作：Essence 与 smelting dusts 的选择。
- 对赛特斯石英也有效。先使用 Essence，然后对剩余部分运行 Crusher 和反应舱循环。

