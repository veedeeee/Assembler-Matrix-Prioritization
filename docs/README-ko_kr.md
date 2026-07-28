# AE Improved Crafting Prioritization
이 모드는 AE2용 애드온 모드입니다.
이 모드는 패턴 공급기의 공급자 단위 제작 우선순위 동작을 개선합니다.

## 의존 모드
### 필수
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### 선택
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## 기능 1: 재료 해결 방식 개선
### AE2 동작
- 같은 결과물에 대해 여러 레시피를 등록할 수 있습니다. 예: `8x 참나무 판자 -> 상자`, `8x 벚나무 판자 -> 상자`.
- Oak Planks 레시피의 패턴 공급기에 제작 우선순위 1, Cherry Planks 레시피에 2를 설정하면 자동 제작 시스템은 **Cherry Planks를 먼저** 확인한 뒤 Oak Planks를 확인합니다.

| 보관 수량<br>참나무 판자 | 보관 수량<br>벚나무 판자 | 주문 | 제작 처리 |
| --- | --- | --- | --- |
|  8 |  0 | 1x 상자 | 8x 참나무 판자로 제작 가능 |
|  0 | 24 | 3x 상자 | 24x 벚나무 판자로 제작 가능 |
|  8 | 16 | 3x 상자 | :no_entry: **재료 부족!**<br>벚나무 판자가 8x 더 필요함 |

### 이 모드의 동작
이 모드는 이 상황을 해결합니다. 8x 참나무 판자와 16x 벚나무 판자만으로도 3개의 상자를 주문할 수 있습니다.
#### 모드 제작자의 시나리오
- **Mekanism**에서 철 주괴 또는 Osmium Ingots를 대량 주문하고, 동시에 **Mystical Agriculture**도 사용하는 상황.
- Essence를 쓰는 제작 패턴에 더 높은 우선순위를, dusts를 쓰는 제련 패턴에 더 낮은 우선순위를 주겠습니다.

## 기능 2: 조합 매트릭스 우선순위 지원
### ExtendedAE 동작
ExtendedAE의 조합 매트릭스에는 우선순위 메뉴가 없습니다.
### 이 모드의 동작
이제 우선순위 메뉴가 생깁니다.
#### 모드 제작자의 시나리오
저는 조합 매트릭스를 정말 좋아해서 많은 레시피를 넣어 둡니다. 세부 사항은 신경 쓰지 않고, 이런 레시피에는 우선순위 1000을 줍니다.
- 위에서 쓴 것과 동일하게 잘 동작해야 합니다: Essence 대 smelting dusts.
- 서투스 석영에도 동작합니다. 먼저 Essence를 사용하고, 나머지는 Crusher와 반응 챔버 루프로 처리하세요.

