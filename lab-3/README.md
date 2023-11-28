# Laboratorio 3: Arbitrage

**Autor:** _Luis Isea_.
**Carnet:** _19-10175_.

## Descripción

### Planteamiento del problema

## Estructura de archivos

- **Arbitrage.java**: Este archivo contiene la clase principal del programa, `Arbitrage`, que se encarga de leer el archivo _tasas.txt_, crear las localidades y calcular la cantidad de repartidores necesarios para cubrir la demanda.
- **DigraphWithCost.java**: Archivo con la interfaz `Digraph`, que representa un dígrafo. La interfaz es una modificación de la interfaz `Graph` del primer proyecto del curso, ya que se agregaron un par de métodos adicionales, como `getVertex` y `containsEdge`.
- **tasas.txt**: Archivo con las vías de bajo tráfico de la ciudad de Caracas. Cada línea del archivo contiene un par de nombres de comercios separados por coma. Si un par de comercios a, b aparece en este archivo, significa que hay una vía de bajo tráfico desde a hasta b. Estas vías son de un solo sentido.

  Ejemplo tasas.txt

  ```txt
  USD GBP 0.7
  GBP FRF 9.5
  FRF USD 0.16
  ```

## Compilación y ejecución

Para ejecutar el programa, es necesario contar con un archivo _tasas.txt_ en el mismo directorio que el laboratorio. Es importante que el archivo tenga el formato especificado en la sección anterior.

Para compilar el programa, se debe ejecutar el siguiente comando:

```bash
javac Arbitrage.java
```

Para ejecutar el programa, se debe ejecutar el siguiente comando:

```bash
java Arbitrage
```

La salida esperada del programa con el archivo _Caracas.txt_ del repositorio es la siguiente:

```txt
30
```

## Detalles de la implementación

## Complejidad del algoritmo

Dibujemos una tabla con las complejidades de cada método de la clase `Arbitrage`:

| Método              | Complejidad     |
| ------------------- | --------------- |
| createCurrencyGraph | O(\|V\|\*\|E\|) |

Donde `|V|` es la cantidad de monedas diferentes que hay, y `|E|` es la cantidad de tasas de cambio que hay.

**Nota:** Más detalles sobre la complejidad de cada método se encuentran en los comentarios del código de la clase `Arbitrage`.
