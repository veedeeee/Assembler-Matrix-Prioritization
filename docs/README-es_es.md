# AE Improved Crafting Prioritization
Este es un mod complementario para AE2.
Este mod mejora el comportamiento de prioridad de crafteo a nivel de proveedor para Proveedor de Patrones ME.

## Mods de dependencia
### Requerido
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
### Opcional
- [ExtendedAE](https://www.curseforge.com/minecraft/mc-mods/ex-pattern-provider)

## Función 1: Mejorar la resolución de ingredientes
### Comportamiento de AE2
- Puedes registrar varias recetas para la misma salida, por ejemplo `8x Tablones de roble -> Cofre` y `8x Tablones de cerezo -> Cofre`.
- Si configuras prioridad de crafteo 1 en el Proveedor de Patrones ME para la receta de Tablones de roble y 2 para la de Tablones de cerezo, tu sistema de autocrafteo revisa **Tablones de cerezo primero** y luego Tablones de roble.

| Cantidad almacenada<br>de Tablones de roble | Cantidad almacenada<br>de Tablones de cerezo | Pedido | Trabajo de crafteo |
| --- | --- | --- | --- |
|  8 |  0 | 1x Cofre | Funciona con 8x Tablones de roble |
|  0 | 24 | 3x Cofre | Funciona con 24x Tablones de cerezo |
|  8 | 16 | 3x Cofre | :no_entry: **¡Faltan ingredientes!**<br>Se necesitan 8x más Tablones de cerezo |

### Comportamiento de este mod
Este mod resuelve esta situación. Podrás pedir 3 Cofre con 8x Tablones de roble y 16x Tablones de cerezo.
#### Escenario del creador del mod
- Pedir muchos lingotes de hierro u Osmium Ingots en **Mekanism** y además tener **Mystical Agriculture**.
- Yo pondría mayor prioridad al Patrón de crafteo con Essence y menor prioridad al Patrón de fundición con dusts.

## Función 2: Matriz ensambladora tiene prioridad
### Comportamiento de ExtendedAE
El Matriz ensambladora de ExtendedAE no tiene menú de prioridad.
### Comportamiento de este mod
Ahora sí lo tiene.
#### Escenario del creador del mod
Me encanta Matriz ensambladora y pongo muchas recetas allí. No me importan los detalles; pongo prioridad 1000 para esas recetas.
- Lo mismo que escribí arriba debería funcionar bien: Essence frente a smelting dusts.
- También funciona para Cuarzo Certus. Usa Essence primero y luego ejecuta el ciclo con Crusher y Cámara de reacción para el resto.

