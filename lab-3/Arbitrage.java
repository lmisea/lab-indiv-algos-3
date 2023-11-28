/*
 * Laboratorio 2 del Laboratorio de Algoritmos y Estructuras de Datos III.
 * Autor: Luis Isea (19-10175).
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class Arbitrage {
	public static DigraphWithCost<String> createCurrencyGraph(String nombreArchivo) {
		DigraphWithCost<String> graph = new DigraphWithCost<String>();
		// Se lee el archivo 'tasas.txt' y se procesa cada línea.
		try {
			FileReader file = new FileReader(nombreArchivo);
			BufferedReader reader = new BufferedReader(file);
			// Leemos la primera linea del archivo.
			String line = reader.readLine();
			// Leemos el archivo linea por linea.
			while (line != null) {
				// La línea se separa en un arreglo de Strings.
				String[] lineArray = line.split(" ");
				// Se agrega cada moneda como un vértice al grafo.
				graph.addVertex(lineArray[0]);
				graph.addVertex(lineArray[1]);
				// Se agrega la arista entre las monedas al grafo.
				// Aquí se le asigna el costo a la arista, es decir, el valor de la tasa de
				// cambio.
				graph.addEdge(lineArray[0], lineArray[1], Double.parseDouble(lineArray[2]));
				// Hacemos una regla de tres para obtener el costo de la arista inversa.
				double costoInverso = 1 / Double.parseDouble(lineArray[2]);
				// Se agrega la arista inversa entre las monedas al grafo.
				graph.addEdge(lineArray[1], lineArray[0], costoInverso);
				// Leemos la siguiente linea.
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Error al leer el archivo: " + e);
		}
		return graph;
	}

	public static void main(String[] args) {
		// Se crea el grafo de monedas.
		DigraphWithCost<String> graph = createCurrencyGraph("tasas.txt");
		System.out.println("Grafo de monedas:");
		System.out.println(graph);
	}
}
