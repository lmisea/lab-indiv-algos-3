# Laboratorio 3: Arbitrage

**Autor:** _Luis Isea_.
**Carnet:** _19-10175_.

## Descripción

Este laboratorio consiste en implementar un programa que determine si se puede hacer arbitrage con un conjunto de tasas de cambio.
Arbitrage entre monedas es la posibilidad de obtener ganancias a partir de la diferencia de tasas de cambio entre dos monedas.

Por ejemplo, si 1 dólar estadounidense equivale a 0.7 libras esterlinas, y 1 libra esterlina equivale a 9.5 francos franceses, entonces 1 dólar estadounidense pasado a libras esterlinas y luego a francos franceses equivale a 0.7 \* 9.5 = 6.65 francos franceses. Como 1 franco francés equivale a 0.16 dólares estadounidenses, entonces 6.65 francos franceses equivale a 6.65 \* 0.16 = 1.064 dólares estadounidenses. Esto quiere decir que si se tiene 1 dólar estadounidense, se puede convertir a 1.064 dólares estadounidenses usando arbitrage.

### Planteamiento del problema

Se desea implementar un programa que determine si se puede hacer arbitrage con un conjunto de tasas de cambio. Estas tasas de cambio se encuentran en un archivo _tasas.txt_ en el mismo directorio que el programa.

## Estructura de archivos

- **Arbitrage.java**: Este archivo contiene la clase principal del programa, `Arbitrage`, que se encarga de leer el archivo _tasas.txt_, añade las monedas y las tasas de cambio al grafo, y luego determina si se puede hacer arbitrage o no.
- **DigraphWithCost.java**: Archivo con la implementación de un grafo dirigido con costos. Para esto se modificó la lista de adyacencia usada en el primer proyecto para que ahora cada nodo tenga una lista de HashMaps, donde cada HashMap representa una arista y tiene como llave el nodo destino y como valor el costo de la arista.
- **tasas.txt**: Archivo con las distintas monedas y su correspondiente tasa de cambio. Cada línea del archivo contiene un par de monedas y su tasa de cambio. El formato de cada línea es el siguiente: `<moneda1> <moneda2> <tasa de cambio>`. Por ejemplo, la línea `USD GBP 0.7` indica que 1 dólar estadounidense equivale a 0.7 libras esterlinas.

  Ejemplo _tasas.txt_:

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

La salida esperada del programa con el archivo _tasas.txt_ del repositorio es la siguiente:

```txt
DINERO FÁCIL DESDE TU CASA
```

Esto indica que sí se puede hacer arbitrage con las tasas de cambio del archivo _tasas.txt_.

## Detalles de la implementación

Para resolver este problema, se modeló el conjunto de tasas de cambio como un grafo dirigido con costos, donde cada nodo representa una moneda y cada arista representa una tasa de cambio. Para esto, se modificó la implementación del grafo dirigido con listas de adyacencia del primer proyecto para que ahora cada nodo tenga una lista de HashMaps, donde cada HashMap representa una arista y tiene como llave el nodo destino y como valor el costo de la arista.

## Complejidad del algoritmo

Dibujemos una tabla con las complejidades de cada método de la clase `Arbitrage`:

| Método              | Complejidad     |
| ------------------- | --------------- |
| createCurrencyGraph | O(\|V\|\*\|E\|) |

Donde `|V|` es la cantidad de monedas diferentes que hay, y `|E|` es la cantidad de tasas de cambio que hay.

**Nota:** Más detalles sobre la complejidad de cada método se encuentran en los comentarios del código de la clase `Arbitrage`.
