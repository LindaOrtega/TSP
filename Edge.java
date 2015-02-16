
public class Edge implements Comparable<Edge> {
	private Vertex v1;
	private Vertex v2;
	private double weight;
	
	public Edge (Vertex vert1, Vertex vert2){
		v1 = vert1;
		v2 = vert2;
		calculateWeight();
	}
	
	public Edge (Vertex vert1, Vertex vert2, double dist){
		v1 = vert1;
		v2 = vert2;
		weight = dist;
	}

	private void calculateWeight() {
		
		weight = (v1.getX()-v2.getX())*(v1.getX()-v2.getX());
		weight += (v1.getY()-v2.getY())*(v1.getY()-v2.getY());
		weight = Math.sqrt(weight);
	}
	
	public Vertex getV1(){
		return v1;
	}
	
	public Vertex getV2(){
		return v2;
	}
	
	public double getWeight(){
		return weight;
	}
	
	public String toString(){
		return ("Edge: " + v1 + " " + v2);
	}
	
	public int compareTo(Edge v) {
		if (this.weight < v.weight)
			return -1;
		else if (this.weight > v.weight)
			return 1;
		else
			return 0;
	}
}
