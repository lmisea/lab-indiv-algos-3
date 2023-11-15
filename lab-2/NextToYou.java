/*
 * Laboratorio 2 del Laboratorio de Algoritmos y Estructuras de Datos III.
 * Autor: Luis Isea (19-10175).
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class NextToYou {
	public static void main(String[] args) {
		// Creamos un grafo en base al archivo "Caracas.txt".
		AdjacencyListDigraph<String> grafo = createCityGraph("Caracas.txt");

		// Calculamos las localidades de la ciudad.
		HashMap<Integer, List<String>> localidades = determinarLocalidades(grafo);

		// Una vez tenemos las localidades, podemos calcular el número de
		// repartidores que necesitamos.
		int numRepartidores = calcularNumRepartidores(localidades);

		// Imprimimos la cantidad de repartidores que NextToYou debe
		// contratar.
		System.out.println(numRepartidores);
	}

	/*
	 * Método que crea el dígrafo de la ciudad, dónde los vertices son
	 * comercios aliados a NextToYou y la arista (a, b) representa que hay
	 * una vía de poco tráfico que lleva desde `a` hasta `b`.
	 */
	public static AdjacencyListDigraph<String> createCityGraph(String nombreArchivo) {
		// Creamos un grafo vacío.
		AdjacencyListDigraph<String> grafo = new AdjacencyListDigraph<String>();
		// Leemos el archivo.
		try {
			FileReader file = new FileReader(nombreArchivo);
			BufferedReader reader = new BufferedReader(file);
			// Leemos la primera linea del archivo.
			String line = reader.readLine();
			// Leemos el archivo linea por linea.
			while (line != null) {
				// Separamos la linea en dos partes que están separadas por una coma.
				String[] parts = line.split(",");
				// Le quitamos el espacio en blanco de la primera posición del segundo
				// vértice.
				parts[1] = parts[1].substring(1);
				// Agregamos el primer vértice.
				grafo.add(parts[0]);
				// Agregamos el segundo vértice.
				grafo.add(parts[1]);
				// Agregamos la arista.
				grafo.connect(parts[0], parts[1]);
				// Leemos la siguiente linea.
				line = reader.readLine();
			}
			// Cerramos el archivo.
			reader.close();
		} catch (Exception e) {
			System.out.println("Error al leer el archivo " + nombreArchivo);
		}
		// Retornamos el grafo.
		return grafo;
	}

	/*
	 * Método que dado un grafo de la ciudad, determina las localidades de la
	 * ciudad. Una localidad es un conjunto de comercios por los que se puede
	 * viajar entre ellos sin pasar por una vía de alto tráfico. Es decir, si
	 * tenemos un grafo G = (V, E), una localidad es un subconjunto de V tal que
	 * para todo par de vértices u, v en la localidad, existe un camino (en el
	 * grafo) entre ellos. Ya que dos comercios están conectados en el grafo si y
	 * solo si no hay una vía de alto tráfico entre ellos.
	 *
	 * Es decir, tenemos que encontrar las componentes fuertemente conexas del
	 * grafo.
	 * Y luego crear un HashMap que mapee cada componente fuertemente conexa a
	 * los vértices que la componen.
	 */
	public static HashMap<Integer, List<String>> determinarLocalidades(AdjacencyListDigraph<String> grafo) {
		// Las componentes fuertemente conexas son las localidades, así que
		// calculamos las componentes fuertemente conexas del grafo.
		int[] componentes = stronglyConnectedComponents(grafo);
		// Creamos un hashmap para guardar las localidades.
		HashMap<Integer, List<String>> localidades = new HashMap<Integer, List<String>>();
		for (int i = 0; i < componentes.length; i++) {
			// Si el componente no está en el hashmap, lo agregamos.
			if (!localidades.containsKey(componentes[i])) {
				localidades.put(componentes[i], new LinkedList<String>());
			}
			// Agregamos el vértice al componente.
			localidades.get(componentes[i]).add(grafo.getVertex(i));
		}
		return localidades;
	}

	/*
	 * Método que implementa el algoritmo de Roy-Warshall para hallar la matriz
	 * de alcance de un grafo.
	 *
	 * Una matriz de alcance es una matriz booleana de nxn tal que la coordenada
	 * (i, j) es verdadera si y solo si existe un camino de alcance entre el
	 * vértice i y el vértice j en el dígrafo.
	 */
	public static boolean[][] calcularMatrizDeAlcance(AdjacencyListDigraph<String> grafo) {
		int n = grafo.size();
		boolean[][] matrizDeAlcance = new boolean[n][n];
		// Inicializamos la matriz de alcance.
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (i == j || grafo.containsEdge(grafo.getVertex(i), grafo.getVertex(j)))
					matrizDeAlcance[i][j] = true;
		// Buscamos los caminos de alcance k entre los vértices.
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				if (i == k || !matrizDeAlcance[i][k])
					continue;
				for (int j = 0; j < n; j++) {
					matrizDeAlcance[i][j] = matrizDeAlcance[i][j] || matrizDeAlcance[k][j];
				}
			}
		}
		// Retornamos la matriz de alcance.
		return matrizDeAlcance;
	}

	/*
	 * Método que implementa un algoritmo para hallar las componentes
	 * fuertemente conexas de un grafo.
	 *
	 * Las componentes fuertemente conexas de un grafo son los conjuntos de
	 * vértices tal que para todo par de vértices u, v en la componente, existe
	 * un camino de u a v y de v a u.
	 *
	 * El algoritmo devuelve un arreglo de tamaño n tal que la componente
	 * fuertemente conexa del vértice i es el valor del arreglo en la posición
	 * i. Y dos vértices están en la misma componente fuertemente conexa si y
	 * solo si tienen el mismo valor en el arreglo.
	 */
	public static int[] stronglyConnectedComponents(AdjacencyListDigraph<String> grafo) {
		// Calculamos la matriz de alcance del grafo.
		boolean[][] matrizDeAlcance = calcularMatrizDeAlcance(grafo);
		// Calculamos las componentes fuertemente conexas del grafo.
		int n = grafo.size();
		int[] componentes = new int[n];
		// Inicializamos el arreglo de componentes.
		for (int i = 0; i < n; i++)
			componentes[i] = -1;
		// Buscamos las componentes fuertemente conexas.
		for (int i = 0; i < n; i++) {
			// Si el vértice ya tiene una componente asignada, continuamos.
			if (componentes[i] != -1)
				continue;
			// Si no, asignamos una nueva componente.
			componentes[i] = i;
			// Buscamos los vértices que están en la misma componente que el
			// vértice actual.
			for (int j = 0; j < n; j++) {
				if (matrizDeAlcance[i][j] && matrizDeAlcance[j][i])
					componentes[j] = i;
			}
		}
		return componentes;
	}

	/*
	 * Método que dado un hashmap de localidades de una ciudad, calcula el número
	 * de repartidores que NextToYou debe contratar para cubrir la ciudad.
	 *
	 * Para localidades pequeñas de 2 o menos comercios, se necesitan diez
	 * repartidores.
	 * Para localidades medianas de 3 a 5 comercios, se necesitan veinte
	 * repartidores.
	 * Para localidades grandes de 6 o más comercios, se necesitan treinta
	 * repartidores.
	 */
	public static int calcularNumRepartidores(HashMap<Integer, List<String>> localidades) {
		int numRepartidores = 0;
		// Se recorre cada localidad para conocer su tamaño.
		for (List<String> localidad : localidades.values()) {
			// Si la localidad tiene un tamaño de 2 o menos, se necesitan
			// diez repartidores para cubrir la localidad.
			if (localidad.size() < 3) {
				numRepartidores += 10;
			}
			// Si la localidad tiene un tamaño entre 3 y 5, se necesitan
			// veinte repartidores para cubrir la localidad.
			else if (localidad.size() < 6) {
				numRepartidores += 20;
			}
			// Si la localidad tiene un tamaño de 6 o más, se necesitan
			// treinta repartidores para cubrir la localidad.
			else {
				numRepartidores += 30;
			}
		}
		return numRepartidores;
	}
}
