/*
 * Interface de un grafo no dirigido. Esta es una modificación de la interfaz
 * Graph.java del primer proyecto del curso.
 */

import java.util.List;

interface UndirectedGraph<T> {
	boolean addVertex(T vertex);

	boolean addEdge(T ver1, T ver2);

	boolean removeEdge(T ver1, T ver2);

	boolean contains(T vertex);

	List<T> getAdjacentVertices(T vertex);

	List<T> getAllVertices();

	boolean removeVertex(T vertex);

	int size();

	int getVertexId(T vertex);
}
