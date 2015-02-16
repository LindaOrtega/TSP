
public class TSP_Tester {
	
	// Takes one command line argument. An integer
	public static void main(String[] args) throws UnderflowException {
		int numCities = Integer.parseInt(args[0]);
		
			TSP_GUI d = new TSP_GUI(numCities);
			d.setVisible(true);
	}
}
