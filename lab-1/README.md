# Laboratorio 1

**Autor:** _Luis Isea_.
**Carnet:** _19-10175_.

El objetivo de este laboratorio es implementar un programa para calcular el grado de separación entre dos personas en una red social.

Se proporciona un archivo con las amistades de las personas en la red social (_input.txt_) y se espera como entrada del programa los nombres de las dos personas a las que se les quiere calcular el grado de separación. Y como salida, el programa debe entregar el grado de separación entre las dos personas como un número entero.

Se considera que el grado de separación de una persona consigo misma es 0, y el grado de separación entre dos personas que no son amigas es -1.

## Estructura de archivos

- **DegreesOfSeparation.java**: Archivo principal del programa. Este archivo contiene el método main y se encarga de leer los datos de entrada y llamar a los métodos necesarios para calcular el grado de separación entre dos personas.
- **input.txt**: Archivo con las amistades de las personas en la red social. Este archivo contiene una lista de amistades, donde cada línea tiene el formato `persona1 persona2`, lo que indica que `persona1` y `persona2` son amigas entre sí. Nótese que la amistad es una relación simétrica, por lo que si `persona1` es amiga de `persona2`, entonces `persona2` es amiga de `persona1`.
- **UndirectedGraph.java**: Archivo con la interfaz `UndirectedGraph`, que representa un grafo no dirigido. La interfaz es una modificación de la interfaz `Graph` del primer proyecto del curso, ya que ahora el grafo es no dirigido.
- **AdjacencyListUndirectedGraph.java**: Archivo con la clase `AdjacencyListUndirectedGraph`, que implementa la interfaz `UndirectedGraph` utilizando listas de adyacencia. Esta clase es una modificación de la clase `AdjacencyListGraph` del primer proyecto del curso, ya que ahora el grafo es no dirigido.

## Compilación y ejecución

Para ejecutar el programa, es necesario contar con un archivo _input.txt_ en el mismo directorio que el archivo _DegreesOfSeparation.java_. El archivo _input.txt_ debe tener el formato descrito en la sección anterior.

Un ejemplo de compilación y ejecución del programa es el siguiente (usando el archivo _input.txt_ del repositorio):

```
javac DegreesOfSeparation.java
java DegreesOfSeparation Carlos Ana
```

Y el resultado de la ejecución es el siguiente:

```
2
```

## Detalles de la implementación

Como la relación de amistad es simétrica, se puede modelar como un grafo no dirigido. Esto llevó a modificar la interfaz `Graph` y la clase `AdjacencyListGraph` del primer proyecto del curso, para que ahora representen un grafo no dirigido.

Por eso, la interfaz y la clase se renombraron a `UndirectedGraph` y `AdjacencyListUndirectedGraph`, respectivamente. Además, se eliminó uno de los dos HashMaps que se utilizaban para representar las listas de predecesores y sucesores de cada vértice, ya que ahora la relación es simétrica y no es necesario tener dos listas.

Sin embargo, se terminó agregando un HashMap adicional para mapear cada vértice a su id en el grafo. Esto porque se volvió fundamental poder asignar y consultar el id de un vértice en el grafo para la lógica del programa.

Y es que precisamente, se decidió implementar una **búsqueda en anchura (BFS)** en lugar de una búsqueda en profundidad (DFS) para calcular el grado de separación entre dos personas. Esto por dos motivos:

- El primer camino encontrado por la búsqueda en anchura es el camino más corto entre esos dos usuarios, ya que en caso de exista otro camino además del encontrado, este será de igual o mayor longitud. Así que no es necesario seguir buscando caminos. En cambio, la búsqueda en profundidad no garantiza encontrar de primeras el camino más corto, por lo que se podría terminar buscando caminos innecesariamente.
- Una búsqueda por profundidad en un grafo de amistades de una red social es simplemente ineficiente, ya que cada persona podría tener cientos o miles de amigos, que a su vez podrían tener cientos o miles, y así sucesivamente. Por lo que la búsqueda en profundidad podría terminar recorriendo una gran cantidad de vértices y aristas, y por lo tanto, tardar mucho tiempo en encontrar el camino entre dos personas. Esto sumado a que no garantiza encontrar el camino más corto, hace que la búsqueda en profundidad no sea una buena opción para este problema.

Así pues, solo quedaba el desafío de implementar la búsqueda en anchura. Para esto, se utilizó una cola, un arreglo de booleanos que representaba los vértices visitados y un arreglo que representaba el grado de separación de cada vértice con respecto al vértice inicial de la búsqueda.

El arreglo de los vértices visitados se utilizó para evitar visitar un vértice más de una vez, ya que solo se encolaban los vértices cuyo id en el arreglo de vertices visitados era false (por esto se agregó el otro HashMap a la lista de adyacencia).

Por su parte el arreglo de grados de separación se utilizó para almacenar el grado de separación de cada vértice con respecto al vértice inicial de la búsqueda. Esto permitió que, al momento de encontrar el vértice objetivo, se pudiera retornar 1 más el grado de separación de este vértice adyacente al objetivo con respecto al vértice inicial de la búsqueda.

## Complejidad del algoritmo

La complejidad del algoritmo es **O(V + E)**, donde V es la cantidad de personas y E es la cantidad de amistades. Ya que el algoritmo recorre todos los vértices y aristas del grafo, y en cada iteración del ciclo principal del algoritmo, se encolan y desencolan vértices, lo que es O(1) en cada caso.
