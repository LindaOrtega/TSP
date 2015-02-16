import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

public class TSP_GUI extends JFrame implements ActionListener{
	
	private JLabel tspLabel;
	private JLabel intro;
	private JLabel intro2;
	private CityMapPanel cityMap;
	private static JTextArea cityInfo;
	private JLabel oLabel; // Optimal Button instructions
	private JLabel oLabel2;
	private JLabel nLabel; // Nearest Neighbor Button instructions
	private JLabel nLabel2;
	private JButton oButton; // Optimal Button
	private JButton nButton; // Nearest Button
	private JTextField oField;
	private JTextField nField;

	private static Vertex[] cities;
	private static ArrayList<Edge> edges;
	private TSP_Algorithm algo;
	
	private final int FRAME_W = 1200;
	private final int FRAME_H = 850;
	private final static int PANEL_W = 790;
	private final static int PANEL_H = 790;

	// Standard x-y spacing between elements
	private final static int XSPACE = 10;
	private final static int YSPACE = 5;

	// Final variables for the menu items (i.e. everything except panel)
	private final int MENU_XPOS = 810;
	private final int MENU_H = 20;
	private final int MENU_W = 300;

	// Vertex's weight and height
	private final static int VW = 10;
	private final static int VH = 10;
	
	// Constructor takes an integer N that represents number of cities
	public TSP_GUI(int n) throws UnderflowException {
		super("Traveling Superheros");
		setLayout(null);
		setSize(FRAME_W, FRAME_H);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		cities = new Vertex[n];
		edges = new ArrayList<Edge>(n-1);
		
		getCities(n);
		getEdges(cities);
		
		algo = new TSP_Algorithm(cities, edges);
		
		// Instantiates graphics
		tspLabel = new JLabel("Traveling Superheros");
		float fontSize = 20;
		tspLabel.setFont(tspLabel.getFont().deriveFont(fontSize));
		
		intro = new JLabel("Batman and Flash need to visit every city only once.");
		intro2 = new JLabel("Who will finish first?");
		
		cityMap = new CityMapPanel();

		cityInfo = new JTextArea (3, 7);
		cityInfo.setEditable(false);
		
		oLabel = new JLabel("Batman needs all the help he can get.");
		oLabel2 = new JLabel("Help Batman find the optimal path:");
		nLabel = new JLabel ("Flash will rely on his speed.");
		nLabel2 = new JLabel("Help Flash find the 'Nearest Neighbor Path':");

		oButton = new JButton ("Optimal Path");
		nButton = new JButton ("Nearest Path");

		oField = new JTextField("Batman's distance is ... ");
		nField = new JTextField("Flash's distance is ... ");
		oField.setEditable(false);
		nField.setEditable(false);

		// Plots the graphics.
		tspLabel.setBounds(MENU_XPOS, YSPACE, 
			MENU_W, MENU_H+10);
		intro.setBounds(MENU_XPOS, MENU_H + 3*YSPACE, 
			MENU_W, MENU_H);
		intro2.setBounds(MENU_XPOS, 2*MENU_H + 3*YSPACE, 
			MENU_W, MENU_H);

		oLabel.setBounds(MENU_XPOS, 4*MENU_H + 4*YSPACE, 
			MENU_W, MENU_H);
		oLabel2.setBounds(MENU_XPOS, 5*MENU_H + 5*YSPACE, 
			MENU_W, MENU_H);
		oButton.setBounds(MENU_XPOS, 6*MENU_H + 6*YSPACE, 
			MENU_W/2, MENU_H);

		nLabel.setBounds(MENU_XPOS, 8*MENU_H + 7*YSPACE, 
			MENU_W, MENU_H);
		nLabel2.setBounds(MENU_XPOS, 9*MENU_H + 8*YSPACE, 
			MENU_W, MENU_H);
		nButton.setBounds(MENU_XPOS, 10*MENU_H + 9*YSPACE, 
			MENU_W/2, MENU_H);

		oField.setBounds(MENU_XPOS, 12*MENU_H + 10*YSPACE, 
			MENU_W, MENU_H);
		nField.setBounds(MENU_XPOS, 13*MENU_H + 11*YSPACE, 
			MENU_W, MENU_H);
		
		cityInfo.setBounds(MENU_XPOS, 15*MENU_H + 12*YSPACE, 
			MENU_W, MENU_H*10);

		// Adds action listeners to the buttons
		oButton.addActionListener(this);
		nButton.addActionListener(this);

		// Adds the graphics to the frame
		add(tspLabel);
		add(intro);
		add(intro2);
		add(cityMap);
		add(cityInfo);
		add(oLabel);
		add(oLabel2);
		add(nLabel);
		add(nLabel2);
		add(oButton);
		add(nButton);
		add(oField);
		add(nField);
	}
	
	// Generates N Vertices representing N cities
	public void getCities(int n){
		Vertex v;
		double VX;
		double VY;
		String cityName;
		
		int i = 0;

		while (i < n){
			VX = (Math.random()+1)*(Math.random()+1)*300;
			VY = (Math.random()+1)*(Math.random()+1)*300;
			
			if (i == 0){
				cityName = "Star City";
				v = new Vertex (cityName, VX, VY);
				cities[i] = v;
			}
			else if (i == 1){
				cityName = "Gotham";
				v = new Vertex (cityName, VX, VY);
				cities[i] = v;
			}
			else if (i == 2){
				cityName = "Metropolis";
				v = new Vertex (cityName, VX, VY);
				cities[i] = v;
			}
			else if (i == 3){
				cityName = "Central City";
				v = new Vertex (cityName, VX, VY);
				cities[i] = v;
			}
			else {
				cityName = "City " + (i+1);
				v = new Vertex (cityName, VX, VY);
				cities[i] = v;
			}
			i++;
		}
	}

	// Creates list of edges connecting every vertex with 
	// every other vertex
	public void getEdges(Vertex[] list){
		for (Vertex v1 : list)
			for (Vertex v2 : list){
				edges.add(new Edge(v1, v2));
			}
	}

	// If button pushed a path is calculated using
	// TSP algorithm and distance displayed on screen.
	public void actionPerformed(ActionEvent ae){
		if (ae.getSource() == oButton) {
			Path oPath = algo.getOptimalPath();
			
			cityMap.drawOptimalPath(oPath);
			oField.setText("Optimal Distance = " + oPath.getDist());
		}

		if (ae.getSource() == nButton){

				Path nPath = algo.getNearestPath();
				cityMap.drawNearPath(nPath);
				nField.setText("Estimated Shortest Path = " + nPath.getDist());
			
		}
	}
	
	// Creates panel for drawing all city vertices and their edges 
	// (or connections)
	private static class CityMapPanel extends JPanel {
		
		public CityMapPanel (){
			setBounds(0, 0, PANEL_W, PANEL_H);
			setBackground(Color.white);
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			// Draws an edge between every Vertex.
			for (Edge e : edges){
				
				double V1x = getX_coord(e.getV1());
				double V1y = getY_coord(e.getV1());
				double V2x = getX_coord(e.getV2());
				double V2y = getY_coord(e.getV2());
			
				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.LIGHT_GRAY);

				Line2D.Double edge = new Line2D.Double(V1x+VW/2, V1y+VH/2, 
						V2x+VW/2, V2y+VH/2);	
				
				g2.draw(edge);
			}
			
			g2.setColor(Color.black);
			String cityIntro = "City Name" + "\t" + "x-coord" + "\t" + "y-coord";
			cityInfo.append(cityIntro + "\n");
			
			// Draws vertices.
			for (Vertex v : cities){
				
				double vx = getX_coord(v);
				double vy = getY_coord(v);
				
				Ellipse2D.Double vShape = new Ellipse2D.Double(vx, vy, 
						VW, VH);
				g2.fill(vShape);
				g2.draw(vShape);
				
				g2.drawString(v.getData(), (int) vx+VW, (int) vy);
				g2.drawString(("(" + (int) v.getX() + ", " + (int) v.getY() + ")"), 
						(int) vx+VW, (int) vy + VH+1);
				
				cityInfo.append(v.getData() + "\t" + (int) v.getX() + "\t" + (int) v.getY() + "\n");
				
			}
		}

		// Draws the optimal path
		public void drawOptimalPath(Path p) {
			Graphics2D g = (Graphics2D) getGraphics();
			
			// Draws an edges.
			for (Edge e : p.getEdges()){
				double V1x = getX_coord(e.getV1());
				double V1y = getY_coord(e.getV1());
				double V2x = getX_coord(e.getV2());
				double V2y = getY_coord(e.getV2());
							
				g.setStroke(new BasicStroke(2));
				g.setColor(Color.blue);

				Line2D.Double edge = new Line2D.Double(V1x+VW/2, V1y+VH/2, 
						V2x+VW/2, V2y+VH/2);	
										
				g.draw(edge);
			}
						
			// Paints nodes in shortest path yellow.
			for (Vertex v : p.getPath()){
				double vx = getX_coord(v);
				double vy = getY_coord(v);
				
				g.setColor(Color.blue);

				Ellipse2D.Double vShape = new Ellipse2D.Double(vx, 
					vy, VW, VH);

				g.fill(vShape);
				g.draw(vShape);
				
				g.setColor(Color.black);
				g.drawString(v.getData(), (int) vx+VW, (int) vy);
				g.drawString(("(" + (int) v.getX() + ", " + (int) v.getY() + ")"), 
						(int) vx+VW, (int) vy + VH+1);
			}	
		}

		// Draws the Nearest Neighbor Path
		public void drawNearPath(Path p){
			Graphics2D g = (Graphics2D) getGraphics();

			// Draws path's edges
			for (Edge e : p.getEdges()){
				double V1x = getX_coord(e.getV1());
				double V1y = getY_coord(e.getV1());
				double V2x = getX_coord(e.getV2());
				double V2y = getY_coord(e.getV2());
									
				g.setColor(Color.red);
				g.setStroke(new BasicStroke(2));
				Line2D.Double edge = new Line2D.Double(V1x+VW/2, V1y+VH/2, 
						V2x+VW/2, V2y+VH/2);	
										
				g.draw(edge);
			}
			
			// Paints nodes in nearest neighbor path red.
			for (Vertex v : p.getPath()){
				double vx = getX_coord(v);
				double vy = getY_coord(v);
				
				g.setColor(Color.red);

				Ellipse2D.Double vShape = new Ellipse2D.Double(vx, 
					vy, VW, VH);

				g.fill(vShape);
				g.draw(vShape);
				
				g.setColor(Color.black);
				g.drawString(v.getData(), (int) vx+VW, (int) vy);
				g.drawString(("(" + (int) v.getX() + ", " + (int) v.getY() + ")"), 
						(int) vx+VW, (int) vy + VH+1);
			}
			
			
		}
		
		// Standardizes x-y coordinates of all graphical elements taking into 
		// account the panel (CityMapPanel) size
		private double getX_coord(Vertex v){
			double vx = v.getX()/2;
			
			if (vx > this.getWidth())
				vx = vx - (vx - getWidth())/2;
			
			else if (vx == getWidth())
				vx = this.getWidth()-VW;			
			
			return vx;
		}
		
		private double getY_coord(Vertex v) {
			double vy = v.getY()/2;

			if (vy > this.getHeight())
				vy = vy - (vy - getHeight())/2;
			
			else if (vy == 0)
				vy = VH+1;
			
			return this.getHeight() - vy;
		}
	}
}
