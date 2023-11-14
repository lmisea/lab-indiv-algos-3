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
- **Digraph.java**: Archivo con la interfaz `UndirectedGraph`, que representa un grafo no dirigido. La interfaz es una modificación de la interfaz `Graph` del primer proyecto del curso, ya que ahora el grafo es no dirigido.
- **AdjacencyListDigraph.java**: Archivo con la clase `AdjacencyListUndirectedGraph`, que implementa la interfaz `UndirectedGraph` utilizando listas de adyacencia. Esta clase es una modificación de la clase `AdjacencyListGraph` del primer proyecto del curso, ya que ahora el grafo es no dirigido.
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

## Complejidad del algoritmo

Dibujemos una tabla con las complejidades de cada método de la clase `NextToYou`:

| Método | Complejidad |
| ------ | ----------- |
