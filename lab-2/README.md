# Laboratorio 2: App de Delivery

**Autor:** _Luis Isea_.
**Carnet:** _19-10175_.

## Descripción

Recientemente se anunció el próximo lanzamiento de _NextToYou_ una nueva aplicación que promete revolucionar el mercado de las apps de delivery. _NextToYou_ tiene un concepto simple pero efectivo: prometen enfocarse en la rapidez. Prometen un tiempo de entrega menor a 30 minutos o te devuelven el dinero.

_NextToYou_ piensa lanzar su piloto inicialmente en la ciudad de Caracas. Para ello, han hecho un estudio de los comercios disponibles y las vías la ciudad. A partir de este estudio, se concluyó que lo mejor sería excluir aquellas vías que suelen tener mucho tráfico.

La app solo necesita dos cosas para lanzar su prueba piloto. Lo primero es determinar las localidades de la ciudad de Caracas. Esto se hará con base a los comercios socios de _NextToYou_. Una localidad viene dada por un conjunto de comercios entre los cuales se puede transitar sin pasar por vías de mucho tráfico. A cada usuario se le asignará la localidad que le quede más cerca. Nótese que con este esquema es posible tener localidades que incluyan un solo comercio.

Lo segundo es determinar el n ́umero de repartidores que hará falta contratar. Se estima que se necesitarán 10 repartidores para localidades peque ̃nas (2 o menos comercios), 20 para localidades medianas (5 o menos comercios) y 30 para localidades grandes (de 6 comercios en adelante).

### Planteamiento del problema

Se proveerá un archivo `Caracas.txt` que contendrá los comercios socios de _NextToYou_ y especificará qué comercios están conectados por vías de bajo tráfico. Hay que resaltar que es una relación asimétrica, es decir, si hay una vía de bajo tráfico desde a hasta b, no necesariamente hay una vía de bajo tráfico desde b hasta a.

Se desea calcular las localidades e imprimir por la salida estándar la cantidad
de repartidores necesaria para cubrir la demanda según las estimaciones

## Estructura de archivos

- **NextToYou.java**: Este archivo contiene la clase principal del programa, `NextToYou`, que se encarga de leer el archivo _Caracas.txt_, crear las localidades y calcular la cantidad de repartidores necesarios para cubrir la demanda.
- **Digraph.java**: Archivo con la interfaz `Digraph`, que representa un dígrafo. La interfaz es una modificación de la interfaz `Graph` del primer proyecto del curso, ya que se agregaron un par de métodos adicionales, como `getVertex` y `containsEdge`.
- **AdjacencyListDigraph.java**: Archivo con la clase `AdjacencyListDigraph`, que implementa la interfaz `Digraph` utilizando listas de adyacencia. Esta clase es una modificación de la clase `AdjacencyListGraph` del primer proyecto del curso, ya que se agregaron un par de métodos adicionales y un HashMap adicional para mapear cada vértice a su id en el grafo.
- **Caracas.txt**: Archivo con las vías de bajo tráfico de la ciudad de Caracas. Cada línea del archivo contiene un par de nombres de comercios separados por coma. Si un par de comercios a, b aparece en este archivo, significa que hay una vía de bajo tráfico desde a hasta b. Estas vías son de un solo sentido.

  Ejemplo Caracas.txt

  ```txt
  Sucy’s Cookies, Kagari Sushi
  Kagari Sushi, Arepas Amanda
  Arepas Amanda, Sucy’s Cookies
  MacDonas, Farmanada
  Farmanada, MacDonas
  ```

## Compilación y ejecución

Para ejecutar el programa, es necesario contar con un archivo _Caracas.txt_ en el mismo directorio que el laboratorio. Es importante que el archivo tenga el formato especificado en la sección anterior.

Para compilar el programa, se debe ejecutar el siguiente comando:

```bash
javac NextToYou.java
```

Para ejecutar el programa, se debe ejecutar el siguiente comando:

```bash
java NextToYou
```

La salida esperada del programa con el archivo _Caracas.txt_ del repositorio es la siguiente:

```txt
30
```

## Detalles de la implementación

La dificultad de este problema es encontrar las localidades de la ciudad. Ya que realmente eso significa encontrar las componentes fuertemente conexas del grafo. Ya que una localidad es un conjunto de comercios entre los cuales se puede transitar sin pasar por vías de mucho tráfico. Y esto es equivalente a decir que es un conjunto de nodos entre los cuales, para cada par de nodos, existe un camino entre ellos.

Para esto utilizamos el algoritmo Roy-Warshall, que nos permite encontrar la matriz de alcance del grafo. Que básicamente es una matriz en dónde se especifica si un nodo es alcanzable desde otro nodo. Es decir, si existe un camino entre ellos.
Y luego, con esta matriz de alcance, podemos encontrar las componentes fuertemente conexas del grafo, utilizando el algoritmo descrito en el libro de texto de Meza y Ortega.

Este algoritmo crea un arreglo de enteros, que representa la componente fuertemente conexa a la que pertenece cada nodo. Es decir, dos nodos pertenecen a la misma componente fuertemente conexa si y solo si tienen el mismo valor en el arreglo de enteros.

Esto representó un pequeño desafío, ya que se optó por utilizar la lista de adyacencia creada para el primer proyecto del curso. Y esta lista de adyacencia no tiene una forma de obtener el nodo asociado a un índice. Por lo que se optó por adoptar la misma estrategia que se utilizó en el primer laboratorio, que es crear un HashMap de ids, que asocia cada id con su respectivo nodo.

Con esto, se puede obtener el nodo asociado a un índice en la lista de adyacencia, y luego obtener el valor de la componente fuertemente conexa de ese nodo.

Una vez obtenido el arreglo de enteros de las componentes fuertemente conexas, se procede a crear un HashMap de componentes fuertemente conexas, que asocia cada componente fuertemente conexa con un conjunto de nodos. Esto se hace recorriendo el arreglo de enteros y agregando cada nodo a la componente fuertemente conexa a la que pertenece.

Esto permite obtener las localidades de la ciudad, ya que cada componente fuertemente conexa es una localidad. Y de esa forma se puede calcular el tamaño de cada localidad, y determinar cuántos repartidores se necesitan para cada localidad, y así determinar cuántos repartidores se necesitan en total.

## Complejidad del algoritmo

Dibujemos una tabla con las complejidades de cada método de la clase `NextToYou`:

| Método                      | Complejidad          |
| --------------------------- | -------------------- |
| createCityGraph             | O(\|V\|\*\|E\|)      |
| determinarLocalidades       | O(\|V\|<sup>3</sup>) |
| calcularMatrizDeAlcance     | O(\|V\|<sup>3</sup>) |
| stronglyConnectedComponents | O(\|V\|<sup>3</sup>) |
| calcularNumRepartidores     | O(\|V\|)             |

Donde `|V|` es la cantidad de comercios en la ciudad, y `|E|` es la cantidad de vías de bajo tráfico, que conectan a los comercios.

**Nota:** Más detalles sobre la complejidad de cada método se encuentran en los comentarios del código de la clase `NextToYou`.
