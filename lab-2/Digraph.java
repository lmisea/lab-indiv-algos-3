import java.util.List;

interface Digraph<T> {
	boolean add(T vertex);

	boolean connect(T from, T to);

	boolean disconnect(T from, T to);

	boolean contains(T vertex);

	List<T> getInwardEdges(T to);

	List<T> getOutwardEdges(T from);

	boolean containsEdge(T from, T to);

	List<T> getVerticesConnectedTo(T vertex);

	List<T> getAllVertices();

	boolean remove(T vertex);

	int size();
}
