/*
 * Laboratorio 1 del Laboratorio de Algoritmos y Estructuras de Datos III.
 * Autor: Luis Isea (19-10175).
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class DegreesOfSeparation {
	/*
	 * Con este método se procesan las amistades de los usuarios extraídas del
	 * archivo 'input.txt' y se crea el grafo no dirigido que las representa.
	 */
	public static AdjacencyListUndirectedGraph<String> processInput() throws FileNotFoundException {
		AdjacencyListUndirectedGraph<String> graph = new AdjacencyListUndirectedGraph<String>();
		// Se lee el archivo 'input.txt' y se procesa cada línea.
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			// Se separa la línea en dos nombres de usuarios.
			String[] users = line.split(" ");
			// Se agrega cada usuario al grafo.
			graph.addVertex(users[0]);
			graph.addVertex(users[1]);
			// No se agrega la amistad entre un usuario y él mismo.
			if (users[0].equals(users[1])) {
				continue;
			}
			// Se agrega la amistad entre los usuarios al grafo.
			graph.addEdge(users[0], users[1]);
		}
		sc.close();
		return graph;
	}

	/*
	 * Con este método se realiza una búsqueda en anchura para encontrar el grado de
	 * separación entre dos usuarios.
	 * Decidí separar este método del método 'getDegreeOfSeparation' para poder
	 * modularizar el código y hacerlo más legible.
	 */
	public static int friendshipBFS(AdjacencyListUndirectedGraph<String> graph,
			String user1, String user2) {
		// Se crea una cola para realizar la búsqueda en anchura.
		Queue<String> queue = new LinkedList<String>();
		// Se crea un arreglo para almacenar los usuarios visitados.
		boolean[] visited = new boolean[graph.size()];
		// Se crea un arreglo para almacenar el grado de separación de cada usuario.
		int[] degreeOfSeparation = new int[graph.size()];
		// Se inicializa el arreglo de usuarios visitados.
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		// Se inicializa el arreglo de grados de separación.
		for (int i = 0; i < degreeOfSeparation.length; i++) {
			degreeOfSeparation[i] = -1;
		}
		// Se agrega el usuario inicial a la cola y se marca como visitado.
		queue.add(user1);
		visited[graph.getVertexId(user1)] = true;
		degreeOfSeparation[graph.getVertexId(user1)] = 0;
		// Se realiza la búsqueda en anchura.
		while (!queue.isEmpty()) {
			// Se obtiene el usuario actual.
			String currentUser = queue.poll();
			// Se obtienen los usuarios adyacentes al usuario actual.
			List<String> adjacentUsers = graph.getAdjacentVertices(currentUser);
			// Se recorren los usuarios adyacentes.
			for (String adjacentUser : adjacentUsers) {
				if (adjacentUser.equals(user2)) {
					// Si el usuario adyacente es el usuario buscado, se retorna el grado de
					// separación entre los usuarios.
					return degreeOfSeparation[graph.getVertexId(currentUser)] + 1;
				}
				// Si el usuario adyacente no ha sido visitado, se agrega a la cola y se
				// marca como visitado.
				else if (!visited[graph.getVertexId(adjacentUser)]) {
					queue.add(adjacentUser);
					visited[graph.getVertexId(adjacentUser)] = true;
					// Se actualiza el grado de separación del usuario adyacente.
					degreeOfSeparation[graph
							.getVertexId(adjacentUser)] = degreeOfSeparation[graph.getVertexId(currentUser)] + 1;
				}
			}
		}
		// Si al finalizar el recorrido de los usuarios adyacentes no se ha encontrado
		// el usuario buscado, se retorna -1.
		return -1;
	}

	/*
	 * Con este método se obtiene el grado de separación entre dos usuarios.
	 */
	public static int getDegreeOfSeparation(AdjacencyListUndirectedGraph<String> graph,
			String user1, String user2) {
		// Primero se verifica que ambos usuarios estén en el grafo.
		if (!graph.contains(user1) || !graph.contains(user2)) {
			return -1;
		}
		// Si se desea saber el grado de separación entre un usuario y él mismo, se
		// retorna 0.
		else if (user1.equals(user2)) {
			return 0;
		}
		// Realizamos una búsqueda en anchura para encontrar el grado de separación
		return friendshipBFS(graph, user1, user2);
	}

	public static void main(String[] args) {
		AdjacencyListUndirectedGraph<String> friendshipGraph = new AdjacencyListUndirectedGraph<String>();
		try {
			friendshipGraph = processInput();
		} catch (FileNotFoundException e) {
			System.out.println("El archivo 'input.txt' no existe en el directorio actual.");
		}
		// Si no se pasan dos usuarios como argumentos, se imprime un mensaje de error.
		if (args.length != 2) {
			System.out.println("Se deben pasar dos usuarios como argumentos.");
			return;
		}
		// Imprimimos el grado de separación entre los usuarios pasados como argumentos.
		System.out.println(getDegreeOfSeparation(friendshipGraph, args[0], args[1]));
	}
}
