# Distribuidora de Ferretería

Sistema de gestión para una distribuidora de artículos de ferretería.
TP Integrador de Desarrollo de Sistemas Orientado a Objetos — Java + Swing + MySQL, en capas con MVC y DAO.

## Entidades

| Entidad | Qué guarda | CRUD |
|---|---|---|
| **Producto** (Artículo) | código, nombre, stock, precio | alta, listado, modificación, baja |
| **Cliente** | nombre y apellido, dirección, CUIT, teléfono | alta, listado, modificación, baja |
| **Venta** (Pedido) | combina un Cliente con uno o más Producto: fecha, estado y renglones (cantidad y precio del día) | registrar (descuenta stock), listar y ver detalle, marcar como entregada, anular (devuelve stock); solo un pedido pendiente se puede entregar o anular |

Extra: **impresión del comprobante de la venta** con la API de impresión de Java (`java.awt.print`), sin librerías externas. En Windows se puede elegir la impresora "Microsoft Print to PDF" para guardarlo como PDF.

## Cómo levantarlo

1. Necesitás **JDK 21 o superior**, **NetBeans** y **MySQL**.
2. Ejecutá `script_bd.sql` en MySQL Workbench: crea la base `distribuidora` y sus 4 tablas.
3. En `src/gestion/distribuidora/dao/ConexionBD.java` poné tu puerto, usuario y contraseña de MySQL (no la subas al repo).
4. Abrí el proyecto en NetBeans (File → Open Project) y ejecutalo con **F6**. Se abre el menú principal.

Las librerías (`mysql-connector-j` y `flatlaf`) ya están en `lib/` y configuradas con ruta relativa.

## Cómo está armado

```
gestion.distribuidora
├── Main                    arranca: aplica el estilo (FlatLaf) y abre el menú
├── modelo                  entidades y reglas del negocio (validar(), StockInsuficienteException)
│   └── casosdeuso          una clase por caso de uso, con ejecutar(dao, ...)
├── dao                     DAO<T, ID> genérico, un DAO por entidad, implementaciones JDBC y DaoException
├── controlador             conecta las ventanas con los casos de uso
├── vista                   ventanas Swing y modelos de tabla (AbstractTableModel)
└── reportes                comprobante de venta para imprimir (ImpresionException)
```

Recorrido de una acción: **vista → controlador → caso de uso → modelo (validar) → DAO → MySQL**.
El modelo no usa Swing, la vista no tiene reglas del negocio y el controlador nunca usa el DAO directo: se lo pasa al caso de uso.

## Diagramas

En `docs/diagramas/` (imagen PNG + fuente PlantUML editable):
- `casos-de-uso.png`: diagrama de casos de uso general.
- `secuencia-registrar-venta.png`: diagrama de secuencia de "Registrar venta".
