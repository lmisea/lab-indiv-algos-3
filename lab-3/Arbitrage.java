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
		// Se verifica si ocurre un arbitraje.
		ocurreArbitraje(graph);
	}

	public static void ocurreArbitraje(DigraphWithCost<String> grafo) {
		List<String> cicloInicial = new LinkedList<>();
		System.out.println(recorrerCiclosRec(grafo, cicloInicial));
	}

	public static String recorrerCiclosRec(DigraphWithCost<String> grafo, List<String> cicloParcial) {
		// Verificamos si después de agregar la última moneda, volvemos a la moneda
		// inicial.
		if (cicloParcial.size() > 1 && cicloParcial.get(cicloParcial.size() - 1) == cicloParcial.get(0)) {
			// Verificamos si al terminar el ciclo, tenemos más de 1 unidad de la moneda
			// inicial. Es decir, si ocurre un arbitraje.
			if (cantidadMonedaInicial(grafo, cicloParcial) > 1.001) {
				// Si es así, entonces retornamos que ocurre un arbitraje.
				System.out.println("Solución parcial: " + cicloParcial);
				return "DINERO FÁCIL DESDE TU CASA";
			} else {
				System.out.println("Solución parcial: " + cicloParcial);
			}
		}
		if (cicloParcial.size() > grafo.size()) {
			return "";
		}
		// Si aún no hemos llegado al final del ciclo, entonces seguimos buscando
		for (HashMap<String, Double> monedaMap : monedasValidas(grafo, cicloParcial)) {
			// Agregamos la siguiente moneda al ciclo parcial.
			String moneda = monedaMap.keySet().iterator().next();
			cicloParcial.add(moneda);
			// Llamamos recursivamente.
			if (recorrerCiclosRec(grafo, cicloParcial) == "DINERO FÁCIL DESDE TU CASA") {
				return "DINERO FÁCIL DESDE TU CASA";
			}
			// Eliminamos la ultima moneda agregada al ciclo parcial.
			// Esto es para que se pueda agregar otra moneda en su lugar.
			cicloParcial.remove(cicloParcial.size() - 1);
		}
		return "TODO GUAY DEL PARAGUAY";
	}

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

	public static Double cantidadMonedaInicial(DigraphWithCost<String> grafo, List<String> cicloParcial) {
		// Tomamos la primera moneda del ciclo parcial.
		String monedaInicial = cicloParcial.get(0);
		// Tomamos el costo de la arista entre la primera moneda y la segunda moneda.
		Double cantidad = grafo.getCost(monedaInicial, cicloParcial.get(1));
		// Iteramos sobre las demás monedas del ciclo parcial.
		for (int i = 1; i < cicloParcial.size() - 1; i++) {
			// Tomamos la moneda actual.
			String monedaActual = cicloParcial.get(i);
			// Tomamos la siguiente moneda.
			String monedaSiguiente = cicloParcial.get(i + 1);
			// Tomamos el costo de la arista entre la moneda actual y la siguiente moneda.
			double costo = grafo.getCost(monedaActual, monedaSiguiente);
			// Multiplicamos la cantidad de moneda inicial por el costo de la arista.
			cantidad *= costo;
		}
		System.err.println("Cantidad de moneda inicial: " + cantidad);
		// Retornamos la cantidad de moneda inicial.
		return cantidad;
	}
}
