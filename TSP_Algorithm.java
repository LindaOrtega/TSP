import java.util.ArrayList;
import java.util.HashMap;

public class TSP_Algorithm {
	private ArrayList<Path> pathsList; // List of all possible paths
	private Path optimalPath; // Optimal path
	
	private Path nearestPath; // Nearest Neighbor Path
	
	private Vertex[] vList; // List of vertices
	private ArrayList<Edge> eList; //List of all edges 
	
	private HashMap<Vertex, BinaryHeap<Edge>> vMapE; // Map of Vertex with Binary heap of all edges connected to it
	private int numVisited; // Tracks # Vertex visited in Nearest Neighbor algorithm 
	
	private FactorialTree<Vertex> tree;
	private BinaryHeap<Edge> heap; 

	// Factorial will depend on length of vList array
	public TSP_Algorithm(Vertex[] vertices, ArrayList<Edge> edges) throws UnderflowException{
		vList = vertices;
		eList = edges;
		
		tree = new FactorialTree<Vertex>(vList); 
		heap = new BinaryHeap<Edge>(eList.toArray(new Edge[eList.size()]));
		
		vMapE = new HashMap<Vertex, BinaryHeap<Edge>>();
		createVmapE();
		numVisited = 0;
		
		calcOptimalPath();
		calcNearestPath();
	}

	private void createVmapE() {
		for (Vertex v1 : vList){
			
			BinaryHeap<Edge> vHeapE = new BinaryHeap<Edge>();
			
			for (Vertex v2 : vList){
				vHeapE.insert(new Edge(v1, v2));
			}
			
			vMapE.put(v1, vHeapE);
		}
	}

	// n is number of vertices in vList so it's vList.length - 1
	private void calcOptimalPath(){
		ArrayList<ArrayList<Vertex>> treeList = tree.getAllCombinations();
		pathsList = new ArrayList<Path>(treeList.size());

		// Adds all possible circuit path to a list of paths
		for (ArrayList<Vertex> p : treeList) {
			pathsList.add(new Path (p));
		}
			
		// Base case path
		optimalPath = pathsList.get(0);
		
		for (Path p : pathsList){
			if (p.getDist() < optimalPath.getDist()) {
				optimalPath = p;
			}
		}
	}
	
	public Path getOptimalPath(){
		return optimalPath;
	}
	
	public double getOptimalDist(){
		return optimalPath.getDist();
	}

	// Calculated Nearest Neighbor Path
	// Smallest Edge calculated using min-Binary Heap
	private void calcNearestPath() throws UnderflowException {
		Edge eShort;
		
		// Error checking for when the shortest edge is the edge connecting
		// the vertex to itself. 
		if (vList.length != 1){
			eShort = heap.deleteMin();
			
			while(!heap.isEmpty() && eShort.getWeight() == 0)
				eShort = heap.deleteMin();
		}
		else
			eShort = heap.deleteMin();
		
		ArrayList<Vertex> nList = new ArrayList<Vertex>(vList.length+1);
		Vertex v1 = eShort.getV1();
		
		v1.visited = true;
		numVisited++;
		nList.add(v1);
		
		calcNearestPath(nList, v1);
	}

	private void calcNearestPath(ArrayList<Vertex> nList, Vertex origin) throws UnderflowException {
		
		Vertex nextVert;
		
		for (nextVert = origin; numVisited < vList.length; ){
			
			BinaryHeap<Edge> vHeap = vMapE.get(nextVert);
			Edge e = vHeap.deleteMin();
			
			nextVert = e.getV2();
			
			// Makes sure that second vertex in shortest attached has not 
			// been visited.
			while (nextVert.visited){
				if (!vHeap.isEmpty()){
					e = vHeap.deleteMin();
					nextVert = e.getV2();
				}
				else
					throw new UnderflowException();
			}
			
			nextVert.visited = true;
			numVisited++;
			
			nList.add(nextVert);
		}
		
		// Adds origin once again to create cycle.
		nList.add(origin);
				
		nearestPath = new Path(nList);
	}
	
	public Path getNearestPath(){
		return nearestPath;
	}
	
	public double getNearestPathDist(){
		return nearestPath.getDist();
	}
}
