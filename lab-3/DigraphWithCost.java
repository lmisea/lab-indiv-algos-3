/*
 * Laboratorio 3 del Laboratorio de Algoritmos y Estructuras de Datos III.
 * Autor: Luis Isea (19-10175).
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DigraphWithCost<T> {
	/*
	 * Creamos un HashMap que mapea cada vértice a una lista de sus vertices
	 * adyacentes. Solo es necesario un HashMap porque el grafo es no dirigido.
	 */
	private HashMap<T, List<HashMap<T, Double>>> adjListIn = new HashMap<T, List<HashMap<T, Double>>>();
	private HashMap<T, List<HashMap<T, Double>>> adjListOut = new HashMap<T, List<HashMap<T, Double>>>();

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
		// Se agrega el vértice al grafo.
		adjListOut.put(vertex, new LinkedList<HashMap<T, Double>>());
		adjListIn.put(vertex, new LinkedList<HashMap<T, Double>>());
		return true;
	}

	/*
	 * Recibe dos vértices 'ver1' y 'ver2' y agrega al grafo la arista (ver1, ver2).
	 * También recibe un costo 'cost' que representa el costo de la arista.
	 * Retorna true si la arista es agregada con éxito.
	 * Retorna false en caso contrario.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 * Ya que se debe verificar que el arco no exista en el grafo.
	 * Y para ello se debe recorrer la lista de adyacencia de 'from'.
	 */
	public boolean addEdge(T ver1, T ver2, Double cost) {
		// Si alguno de los vértices no existe en el grafo, no se agrega la arista y
		// se retorna false.
		if (!contains(ver1) || !contains(ver2)) {
			return false;
		}
		// Si la arista ya existe en el grafo, se actualiza el costo de la arista.
		if (containsEdge(ver1, ver2)) {
			removeEdge(ver1, ver2);
		}
		// Se añade la arista al grafo.
		adjListOut.get(ver1).add(new HashMap<T, Double>(Map.of(ver2, cost)));
		adjListIn.get(ver2).add(new HashMap<T, Double>(Map.of(ver1, cost)));
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
		if (!containsEdge(ver1, ver2)) {
			return false;
		}
		// Se elimina 'ver2' de la lista de sucesores de 'ver1'.
		for (HashMap<T, Double> edge : adjListOut.get(ver1)) {
			if (edge.containsKey(ver2)) {
				adjListOut.get(ver1).remove(edge);
				break;
			}
		}
		// Se elimina 'ver1' de la lista de predecesores de 'ver2'.
		for (HashMap<T, Double> edge : adjListIn.get(ver2)) {
			if (edge.containsKey(ver1)) {
				adjListIn.get(ver2).remove(edge);
				break;
			}
		}
		return true;
	}

	public boolean containsEdge(T ver1, T ver2) {
		// Si alguno de los vértices no existe en el grafo, no se elimina la arista y
		// se retorna false.
		if (!contains(ver1) || !contains(ver2)) {
			return false;
		}
		// Si la arista no existe en el grafo, no se elimina la arista y se retorna
		// false.
		for (HashMap<T, Double> edge : adjListOut.get(ver1)) {
			if (edge.containsKey(ver2)) {
				return true;
			}
		}
		return false;
	}

	public Double getCost(T ver1, T ver2) {
		// Si alguno de los vértices no existe en el grafo, no se elimina la arista y
		// se retorna false.
		if (!contains(ver1) || !contains(ver2)) {
			return null;
		}
		// Se retorna el costo de la arista, viendo en la lista de
		// sucesores de 'ver1' el costo de la arista (ver1, ver2).
		for (HashMap<T, Double> edge : adjListOut.get(ver1)) {
			if (edge.containsKey(ver2)) {
				return edge.get(ver2);
			}
		}
		return null;
	}

	/*
	 * Recibe un vértice y retorna true si el vértice pertenece a V.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean contains(T vertex) {
		return adjListOut.containsKey(vertex);
	}

	/*
	 * Recibe un vértice v y retorna la lista de predecedores de v. Es decir,
	 * retorna la lista de todos los u ∈ V tales que (u, v) ∈ E.
	 * Si ocurre algún error, retorna la referencia null
	 * Complejidad: O(n), siendo n la cantidad de vertices.
	 * Esto ya que para copiar la lista, se debe recorrer cada elemento de
	 * la lista una vez.
	 */
	public List<HashMap<T, Double>> getInwardEdges(T to) {
		// Si el vértice no existe en el grafo, se retorna null.
		if (!contains(to)) {
			return null;
		}
		// Creamos una lista de predecesores de 'to', para no retornar la referencia
		// directa a la lista de predecesores de 'to'.
		List<HashMap<T, Double>> inwardEdges = adjListIn.get(to);
		return inwardEdges;
	}

	/*
	 * Recibe un vértice v y retorna la lista de sucesores de v. Es decir, retorna
	 * la lista de todos los u ∈ V tales que (v, u) ∈ E.
	 * Si ocurre algún error, retorna la referencia null.
	 * Complejidad: O(n), siendo n la cantidad de vertices.
	 * Esto ya que para copiar la lista, se debe recorrer cada elemento de
	 * la lista una vez.
	 */
	public List<HashMap<T, Double>> getOutwardEdges(T from) {
		// Si el vértice no existe en el grafo, se retorna null.
		if (!contains(from)) {
			return null;
		}
		// Creamos una lista de sucesores de 'from', para no retornar la referencia
		// directa a la lista de sucesores de 'from'.
		List<HashMap<T, Double>> outwardEdges = adjListOut.get(from);
		return outwardEdges;
	}

	/*
	 * Retorna la lista de todos vértices del grafo.
	 * Es decir, todos los elementos de V.
	 * Complejidad: O(1).
	 */
	public List<T> getAllVertices() {
		return adjListOut.keySet().stream().toList();
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
		for (HashMap<T, Double> edge : adjListOut.get(vertex)) {
			adjListIn.get(edge).remove(vertex);
		}
		for (HashMap<T, Double> edge : adjListIn.get(vertex)) {
			adjListOut.get(edge).remove(vertex);
		}
		adjListOut.remove(vertex);
		adjListIn.remove(vertex);
		return true;
	}

	/*
	 * Retorna la cantidad de vértices que contiene el grafo. Es decir, la
	 * cardinalidad del conjunto de vértices |V|.
	 * Complejidad: O(1).
	 */
	public int size() {
		return adjListOut.size();
	}

	/*
	 * Retorna una representación en String del grafo.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	@Override
	public String toString() {
		String result = "";
		for (T vertex : adjListOut.keySet()) {
			result += "[" + vertex + "] -> " + adjListOut.get(vertex) + "\n";
		}
		return result;
	}
}
