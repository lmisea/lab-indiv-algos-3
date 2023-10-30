/*
 * Laboratorio 1 del Laboratorio de Algoritmos y Estructuras de Datos III.
 * Autor: Luis Isea (19-10175).
 */

import java.io.File;
import java.io.FileNotFoundException;
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
		return 0;
	}

	public static void main(String[] args) {
		AdjacencyListUndirectedGraph<String> friendshipGraph = new AdjacencyListUndirectedGraph<String>();
		try {
			friendshipGraph = processInput();
		} catch (FileNotFoundException e) {
			System.out.println("El archivo 'input.txt' no existe en el directorio actual.");
		}
		System.out.println(friendshipGraph);
	}
}
