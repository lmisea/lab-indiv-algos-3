/*
 * Laboratorio 2 del Laboratorio de Algoritmos y Estructuras de Datos III.
 * Autor: Luis Isea (19-10175).
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Arbitrage {
	public static void main(String[] args) {
		// Se crea el grafo de monedas.
		DigraphWithCost<String> graph = createCurrencyGraph("tasas.txt");

		// Se verifica si ocurre un arbitraje.
		ocurreArbitraje(graph, 1.001);
	}

	/*
	 * Método que crea un grafo de monedas a partir de un archivo de texto.
	 * Donde cada moneda es un vértice y cada tasa de cambio es una arista.
	 * Cada arista tiene un costo que representa el valor de la tasa de cambio.
	 * Por eso se termina con un dígrafo. Porque no es la misma tasa de cambio
	 * pasar de una moneda A a una moneda B, que pasar de una moneda B a una
	 * moneda A.
	 *
	 * Cada línea del archivo de texto debe tener el siguiente formato:
	 * `moneda1 moneda2 tasa_de_cambio`
	 *
	 * Complejidad: O(|V|*|E|), donde |V| es el número de monedas y |E| es el
	 * número de tasas de cambio.
	 *
	 * Ya que cada línea representa una arista del grafo, y por cada línea se
	 * realiza una operación de costo O(|V|) (agregar la arista al grafo).
	 */
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
				// Verificamos si hay una arista inversa entre las monedas.
				if (graph.containsEdge(lineArray[1], lineArray[0])) {
					// Si es así, entonces no se agrega la arista inversa.
					line = reader.readLine();
					continue;
				}
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

	/*
	 * Método que verifica si ocurre un arbitraje en un grafo de monedas.
	 * Un arbitraje ocurre cuando se puede hacer una secuencia de intercambios
	 * de monedas, de tal forma que se obtenga una mayor cantidad de la moneda
	 * inicial.
	 *
	 * Complejidad: O(|V|^(|V|+1)), donde |V| es el número de monedas.
	 * Ya que se llama a recorrerCiclosRec, que tiene complejidad O(|V|^(|V|+1)).
	 */
	public static void ocurreArbitraje(DigraphWithCost<String> grafo, Double minGanancia) {
		List<String> cicloInicial = new LinkedList<>();
		System.out.println(recorrerCiclosRec(grafo, cicloInicial, minGanancia));
	}

	/*
	 * Método que recorre todos los ciclos de un grafo de monedas.
	 * Para ello, se hace una búsqueda en profundidad (DFS) sobre el grafo.
	 * Cada vez que se llega a un vértice, se agrega al ciclo parcial.
	 * Cuando se llega a un vértice que ya está en el ciclo parcial, se verifica
	 * si se ha obtenido una mayor cantidad de la moneda inicial.
	 * Si es así, entonces se retorna que ocurre un arbitraje.
	 * Si no es así, entonces se sigue buscando.
	 *
	 * Complejidad: Encuentra todos los ciclos de hasta |V| + 1 monedas.
	 * Y como cada ciclo puede empezar en |V| vértices, y puede tener |V| - 1
	 * monedas para la segunda moneda, |V| - 1 para la tercera y así sucesivamente,
	 * hasta |V| - 1 para la última moneda. Entonces, la complejidad es:
	 * O(|V| * |V|+1 * |V|+1 * ... * |V|+1) = O(|V|^(|V|+1)).
	 */
	public static String recorrerCiclosRec(DigraphWithCost<String> grafo, List<String> cicloParcial,
			Double minGanancia) {
		// Verificamos si después de agregar la última moneda, volvemos a la moneda
		// inicial.
		if (cicloParcial.size() > 1 && cicloParcial.get(0).equals(cicloParcial.get(cicloParcial.size() - 1))) {
			// Verificamos si al terminar el ciclo, tenemos más de 1 unidad de la moneda
			// inicial. Es decir, si ocurre un arbitraje.
			if (cantidadMonedaInicial(grafo, cicloParcial) > minGanancia) {
				// Si es así, entonces retornamos que ocurre un arbitraje.
				return "DINERO FÁCIL DESDE TU CASA";
			}
			return "";
		}
		// Si aún no hemos llegado al final del ciclo, entonces seguimos buscando
		for (HashMap<String, Double> monedaMap : monedasValidas(grafo, cicloParcial)) {
			// Establecemos que la longitud máxima del ciclo sea |V| + 1
			if (cicloParcial.size() > grafo.size()) {
				return "";
			}
			// Agregamos la siguiente moneda al ciclo parcial.
			String moneda = monedaMap.keySet().iterator().next();
			cicloParcial.add(moneda);
			// Llamamos recursivamente.
			if (recorrerCiclosRec(grafo, cicloParcial, minGanancia) == "DINERO FÁCIL DESDE TU CASA") {
				return "DINERO FÁCIL DESDE TU CASA";
			}
			// Eliminamos la ultima moneda agregada al ciclo parcial.
			// Esto es para que se pueda agregar otra moneda en su lugar.
			cicloParcial.remove(cicloParcial.size() - 1);
		}
		return "TODO GUAY DEL PARAGUAY";
	}

	/*
	 * Método que retorna las monedas válidas para agregar al ciclo parcial.
	 * Es decir, las monedas que son adyacentes a la última moneda del ciclo
	 * parcial.
	 * O todas las monedas del grafo, si el ciclo parcial está vacío.
	 * Complejidad: O(|V|) donde |V| es el número de monedas.
	 */
	public static List<HashMap<String, Double>> monedasValidas(DigraphWithCost<String> grafo,
			List<String> cicloParcial) {
		// Verificamos si cicloParcial no tiene elementos.
		if (cicloParcial.size() == 0) {
			// Si es así, retornamos todos los vértices del grafo, para que
			// se creen soluciones parciales que empiecen con todas las carta mostro.
			List<HashMap<String, Double>> lista = new LinkedList<>();
			for (String vertex : grafo.getAllVertices()) {
				HashMap<String, Double> mapa = new HashMap<>();
				mapa.put(vertex, 1.0);
				lista.add(mapa);
			}
			return lista;
		}
		// En caso contrario, tomamos el ultimo elemento de cicloParcial.
		String ultima = cicloParcial.get(cicloParcial.size() - 1);
		// Y retornamos los vecinos de la ultima carta de cicloParcial, es decir,
		// las cartas mostro que comparten exactamente una característica con
		// la ultima carta de cicloParcial.
		return grafo.getOutwardEdges(ultima);
	}

	/*
	 * Método que calcula la cantidad de moneda inicial que se obtiene al
	 * recorrer un ciclo parcial.
	 * Complejidad: O(|V|), donde |V| es el número de monedas.
	 * Ya que el ciclo más largo que se puede formar es de |V| + 1 monedas.
	 */
	public static Double cantidadMonedaInicial(DigraphWithCost<String> grafo, List<String> ciclo) {
		// Tomamos la primera moneda del ciclo parcial.
		String monedaInicial = ciclo.get(0);
		// Tomamos el costo de la arista entre la primera moneda y la segunda moneda.
		Double cantidad = grafo.getCost(monedaInicial, ciclo.get(1));
		// Iteramos sobre las demás monedas del ciclo parcial.
		for (int i = 1; i < ciclo.size() - 1; i++) {
			// Tomamos la moneda actual.
			String monedaActual = ciclo.get(i);
			// Tomamos la siguiente moneda.
			String monedaSiguiente = ciclo.get(i + 1);
			// Tomamos el costo de la arista entre la moneda actual y la siguiente moneda.
			double costo = grafo.getCost(monedaActual, monedaSiguiente);
			// Multiplicamos la cantidad de moneda inicial por el costo de la arista.
			cantidad *= costo;
		}
		// Retornamos la cantidad de moneda inicial.
		return cantidad;
	}
}
