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

Para resolver este problema, se modeló un grafo dirigido con costos, donde cada nodo representa una moneda y cada arista representa una tasa de cambio. Para esto, se modificó la implementación del grafo dirigido con listas de adyacencia del primer proyecto para que ahora cada nodo tenga dos listas de HashMaps, una para los nodos sucesores y otra para los nodos predecesores
donde cada HashMap en estas listas representa una arista y tiene como key el nodo destino y como value el costo de la arista.

Por cada tasa de cambio en el archivo _tasas.txt_, se crean dos aristas, una en cada dirección, para que el grafo sea bidireccional. Y se construye el costo de la arista inversa usando la regla de tres. Pero el programa también soporta que se ingrese la tasa inversa de una tasa de cambio ya existente, y en ese caso solo se actualiza el costo de la arista inversa. Esto hace que el programa soporte tasas unidireccionales y bidireccionales al mismo tiempo.

Para determinar si se puede hacer arbitrage, se recorre el grafo usando DFS, recorriendo todos los ciclos posibles de hasta `|V|+1` nodos. Cuando se encuentra un ciclo, se calcula la cantidad de moneda inicial que se obtiene al recorrer el ciclo, y si esta cantidad es mayor a **1.001**, entonces el programa imprime `DINERO FÁCIL DESDE TU CASA` y termina. Si no se encuentra ningún ciclo que cumpla con esta condición, entonces el programa imprime `NO SE PUEDE HACER DINERO FÁCIL DESDE TU CASA` y termina.

Se decidió poner `1.001` como el máximo valor de ganancia antes de que se considere que se puede hacer arbitraje. Es decir, que si se obtiene una ganancia de `1.001` o menos, entonces no se considera que se puede hacer arbitraje. Esto se debe a que ocurren errores de redondeo al hacer las operaciones con los costos de las aristas, y por lo tanto, no se puede asegurar que se pueda hacer arbitraje con una ganancia de `1.001` o menos.

De todas formas, si se desea cambiar este valor, se puede modificar el parámetro _maxGananciaDespreciable_ en la llamada al método `ocurreArbitraje` en el método `main` de la clase `Arbitrage` y así decidir a partir de qué valor se considera que se puede hacer arbitraje.

Este programa tiene una complejidad de **O(\|V\|<sup>\|V\|+1</sup>)**, donde `|V|` es la cantidad de monedas diferentes que hay. Esto se debe a que hay `|V|` nodos posibles para empezar el recorrido, y cada nodo siguiente tiene `|V|-1` nodos siguientes posibles, sin importar la posición en la que se encuentre en el recorrido.

Así se tiene que la cantidad de ciclos posibles es `|V|*(|V|-1)*(|V|-1)*...*(|V|-1)`, es decir, `|V|` multiplicado por `|V|` veces `|V|-1`. Esto es igual a **O(|V|<sup>\|V\|+1</sup>)**.

## Complejidad del algoritmo

Dibujemos una tabla con las complejidades de cada método de la clase `Arbitrage`:

| Método                | Complejidad                |
| --------------------- | -------------------------- |
| createCurrencyGraph   | O(\|V\|\*\|E\|)            |
| ocurreArbitraje       | O(\|V\|<sup>\|V\|+1</sup>) |
| recorrerCiclosRec     | O(\|V\|<sup>\|V\|+1</sup>) |
| monedasValidas        | O(\|V\|)                   |
| cantidadMonedaInicial | O(\|V\|)                   |

Donde `|V|` es la cantidad de monedas diferentes que hay, y `|E|` es la cantidad de tasas de cambio que hay.

**Nota:** Más detalles sobre la complejidad de cada método se encuentran en los comentarios del código de la clase `Arbitrage`.
