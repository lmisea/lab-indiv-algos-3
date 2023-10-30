# Laboratorio 1

El objetivo de este laboratorio es implementar un programa para calcular el grado de separación entre dos personas en una red social.
Se proporciona un archivo con las amistades de las personas en la red social (_input.txt_) y se espera como entrada del programa los nombres de las dos personas a las que se les quiere calcular el grado de separación.
Se considera que el grado de separación de una persona consigo misma es 0, y el grado de separación entre dos personas que no son amigas es -1.

## Estructura de archivos

- **DegreesOfSeparation.java**: Archivo principal del programa. Este archivo contiene el método main y se encarga de leer los datos de entrada y llamar a los métodos necesarios para calcular el grado de separación entre dos personas.
- **input.txt**: Archivo con las amistades de las personas en la red social. Este archivo contiene una lista de amistades, donde cada línea tiene el formato `persona1 persona2`, lo que indica que `persona1` y `persona2` son amigas entre sí. Nótese que la amistad es una relación simétrica, por lo que si `persona1` es amiga de `persona2`, entonces `persona2` es amiga de `persona1`.
- **UndirectedGraph.java**: Archivo con la interfaz `UndirectedGraph`, que representa un grafo no dirigido. La interfaz es una modificación de la interfaz `Graph` del primer proyecto del curso, ya que ahora el grafo es no dirigido.
- **AdjacencyListUndirectedGraph.java**: Archivo con la clase `AdjacencyListUndirectedGraph`, que implementa la interfaz `UndirectedGraph` utilizando listas de adyacencia. Esta clase es una modificación de la clase `AdjacencyListGraph` del primer proyecto del curso, ya que ahora el grafo es no dirigido.

## Detalles de la implementación

Como la relación de amistad es simétrica, se puede modelar como un grafo no dirigido. Esto llevó a modificar la interfaz `Graph` y la clase `AdjacencyListGraph` del primer proyecto del curso, para que ahora representen un grafo no dirigido.
Esta modificación reduce el espacio requerido en memoria del primer proyecto, ya que ahora no se necesitan dos HashMaps para representar el grafo, sino que basta con uno solo.
