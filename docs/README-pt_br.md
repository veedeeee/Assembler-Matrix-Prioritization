# AE2: Improved Crafting Prioritization
Este é um mod complementar para AE2.
Este mod melhora o comportamento de prioridade de crafting no nível do provedor para o Provedor de Padrões.

## Mods de dependência
### Obrigatório
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Opcional
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## Função 1: Melhorar a resolução de ingredientes
### Comportamento do AE2
- Você pode registrar várias receitas para a mesma saída, como `8x Tábuas de Carvalho -> Baú` e `8x Tábuas de Cerejeira -> Baú`.
- Se você definir prioridade de crafting 1 no Provedor de Padrões para a receita de Tábuas de Carvalho e 2 para a de Tábuas de Cerejeira, seu sistema de autocrafting verifica **Tábuas de Cerejeira primeiro** e depois Tábuas de Carvalho.

| Quantidade em estoque<br>de Tábuas de Carvalho | Quantidade em estoque<br>de Tábuas de Cerejeira | Pedido | Trabalho de crafting |
| --- | --- | --- | --- |
|  8 |  0 | 1x Baú | Funciona com 8x Tábuas de Carvalho |
|  0 | 24 | 3x Baú | Funciona com 24x Tábuas de Cerejeira |
|  8 | 16 | 3x Baú | :no_entry: **Ingredientes faltando!**<br>Precisa de mais 8x Tábuas de Cerejeira |

### Comportamento deste mod
Este mod resolve essa situação. Você poderá pedir 3 Baú com 8x Tábuas de Carvalho e 16x Tábuas de Cerejeira.
#### Cenário do criador do mod
- Pedir muitas barras de ferro ou Osmium Ingots no **Mekanism**, além de ter **Mystical Agriculture**.
- Eu colocaria prioridade mais alta para o Padrão de crafting com Essence e prioridade mais baixa para o Padrão de fundição com dusts.

## Função 2: Matriz de Montagem recebe prioridade
### Comportamento do ExtendedAE
O Matriz de Montagem do ExtendedAE não tem menu de prioridade.
### Comportamento deste mod
Agora ele tem.
#### Cenário do criador do mod
Eu adoro Matriz de Montagem e coloco muitas receitas nele. Não me importo com detalhes; coloco prioridade 1000 para essas receitas.
- A mesma coisa que escrevi acima deve funcionar bem: Essence vs smelting dusts.
- Também funciona para Quartzo Certus. Use Essence primeiro, depois execute o ciclo com Crusher e Câmara de Reação para o restante.

