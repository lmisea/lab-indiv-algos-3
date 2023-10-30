/*
 * Laboratorio 1 del Laboratorio de Algoritmos y Estructuras de Datos III.
 * Autor: Luis Isea (19-10175).
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListUndirectedGraph<T> implements UndirectedGraph<T> {
	/*
	 * Creamos un HashMap que mapea cada vértice a una lista de sus vertices
	 * adyacentes. Solo es necesario un HashMap porque el grafo es no dirigido.
	 */
	private HashMap<T, List<T>> adjacencyList = new HashMap<T, List<T>>();
	private HashMap<T, Integer> idMap = new HashMap<T, Integer>();

	/*
	 * Recibe un vértice y lo agrega al grafo. Retorna true si el vértice es
	 * agregado con éxito.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean addVertex(T vertex) {
		// Si el vértice ya existe en el grafo, no se agrega y se retorna false.
		if (contains(vertex)) {
			return false;
		}
		// Si el vértice es null, no se agrega y se retorna false.
		if (vertex == null) {
			return false;
		}
		// Se agrega el vértice al mapa de ids.
		idMap.put(vertex, this.size());
		// Se agrega el vértice al grafo.
		adjacencyList.put(vertex, new LinkedList<T>());
		return true;
	}

	/*
	 * Recibe dos vértices 'ver1' y 'ver2' y agrega al grafo la arista (ver1, ver2).
	 * Retorna true si la arista es agregada con éxito.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean addEdge(T ver1, T ver2) {
		// Si alguno de los vértices no existe en el grafo, no se agrega la arista y
		// se retorna false.
		if (!contains(ver1) || !contains(ver2)) {
			return false;
		}
		// Si la arista ya existe en el grafo, no se agrega la arista y se retorna
		// false.
		if (adjacencyList.get(ver1).contains(ver2)) {
			return false;
		}
		// Se añade la arista al grafo.
		adjacencyList.get(ver1).add(ver2);
		adjacencyList.get(ver2).add(ver1);
		return true;
	}

	/*
	 * Recibe dos vértices 'ver1' y 'ver2' y elimina del grafo la arista (ver1,
	 * ver2).
	 * Retorna true si la arista es eliminada con éxito.
	 * Retorna false en caso contrario.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	public boolean removeEdge(T ver1, T ver2) {
		// Si alguno de los vértices no existe en el grafo, no se elimina la arista y
		// se retorna false.
		if (!contains(ver1) || !contains(ver2)) {
			return false;
		}
		// Si la arista no existe en el grafo, no se elimina la arista y se retorna
		// false.
		if (!adjacencyList.get(ver1).contains(ver2)) {
			return false;
		}
		// Se elimina la arista del grafo.
		adjacencyList.get(ver1).remove(ver2);
		adjacencyList.get(ver2).remove(ver1);
		return true;
	}

	/*
	 * Recibe un vértice y retorna true si el vértice pertenece a V.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean contains(T vertex) {
		return adjacencyList.containsKey(vertex);
	}

	/*
	 * Recibe un vértice v y retorna la lista de vertices adyacentes a v.
	 * Es decir, retorna la lista de todos los vertices u tales que (v, u) ∈ E.
	 * Si ocurre algún error, retorna la referencia null
	 * Complejidad: O(1)
	 */
	public List<T> getAdjacentVertices(T vertex) {
		// Si el vértice no existe en el grafo, se retorna null.
		if (!contains(vertex)) {
			return null;
		}
		return adjacencyList.get(vertex);
	}

	/*
	 * Retorna la lista de todos vértices del grafo.
	 * Es decir, todos los elementos de V.
	 * Complejidad: O(1).
	 */
	public List<T> getAllVertices() {
		return adjacencyList.keySet().stream().toList();
	}

	/*
	 * Recibe un vértice y lo elimina del grafo. Retorna true si el vértice es
	 * eliminado con éxito. Retorna false en caso contrario.
	 * Complejidad: O(n*m). Siendo n la cantidad de vértices y m la cantidad
	 * promedio de arcos por vértice.
	 */
	public boolean removeVertex(T vertex) {
		// Si el vértice no existe en el grafo, no se elimina y se retorna false.
		if (!contains(vertex)) {
			return false;
		}
		// Se eliminan todas las aristas que contienen al vértice.
		for (T adjacentVertex : adjacencyList.get(vertex)) {
			adjacencyList.get(adjacentVertex).remove(vertex);
		}
		// Se elimina el vértice del grafo.
		adjacencyList.remove(vertex);
		return true;
	}

	/*
	 * Retorna la cantidad de vértices que contiene el grafo. Es decir, la
	 * cardinalidad del conjunto de vértices |V|.
	 * Complejidad: O(1).
	 */
	public int size() {
		return adjacencyList.size();
	}

	/*
	 * Retorna el id de un vértice.
	 * Complejidad: O(1).
	 */
	public int getVertexId(T vertex) {
		return idMap.get(vertex);
	}

	/*
	 * Retorna el mapa de ids.
	 * Complejidad: O(1).
	 */
	public HashMap<T, Integer> getIdMap() {
		return idMap;
	}

	/*
	 * Retorna una representación en String del grafo.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	@Override
	public String toString() {
		String result = "";
		for (T vertex : adjacencyList.keySet()) {
			result += "[" + vertex + "] -> " + adjacencyList.get(vertex) + "\n";
		}
		return result;
	}
}
