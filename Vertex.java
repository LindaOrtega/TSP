public class Vertex implements Comparable<Vertex> {
	private double x_coordinate;
	private double y_coordinate;
	private String data;
	
	public boolean visited; // If Vertex visited in Nearest Neighbor problem, visited = true;
	
	// Creates a vertex with an x-y coordinate and data.
	public Vertex(String d, double x, double y){
		data = d;
		x_coordinate = x;
		y_coordinate = y;
		visited = false;
	}
	
	public double getX(){
		return x_coordinate;
	}
	
	public double getY(){
		return y_coordinate;
	}
	
	public String getData(){
		return data;
	}
	
	public String toString(){
		String msg = data;
		return msg;
	}
	
	public int compareTo(Vertex v) {
		if (this.x_coordinate < v.x_coordinate) // 'this' to the left of v
			return -1;
		else if (this.x_coordinate > v.x_coordinate) // 'this' to right of v
			return 1;
		else
			return 0; // 'this' and v are in same x-position
	}
}
