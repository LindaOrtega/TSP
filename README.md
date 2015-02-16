Name: Linda Ortega Cordoves
UNI: lo2258
Assignment: Data Structures Homework 6, The Traveling Salesperson

# of folders included: 1

FOLDER Traveling_Salesperson includes the following files:
- BinaryHeap.java
- Edge.java
- FactorialTree.java
- Path.java
- TSP_Algorithm.java
- TSP_GUI.java
- TSP_Tester.java
- UnderflowException.java
- Vertex.java

Run and compile the file "TSP_Tester.java" with an integer as a command line argument
representing the number of cities. 

--------------------------------------------------------------------------------------
TSP_GUI.java :	

Creates a GUI with a JPanel for displaying the cities, their name, and
their x-y coordinate. The random number generator gave cities close coordinates, making 
it hard to read the name and x-y coordinate. To address this, I inlcuded a JTextArea
that displays the name of all the ciites and their coordinates. The GUI also
creates an array of cities depending on the index user passed in command line.

To calculate the optimal path, click "Optimal Path" button. Total distance will display
in first JTextField.

To calculate the Nearest Neighbor Path, click "Nearest Path" button. Total distance 
will display in second JTextField.

--------------------------------------------------------------------------------------
TSP_Algorithm.java

Calculates optimal path using an array of cities and a FactorialTree object.
The Factorial Tree's Node includes a 'parent' and array of 'children' fields.
Based on N, size of cities, the Factorial tree class creates a null root node
with N children to represent the number of possibilities the salesperson 
has at the beggining. Each of those children then has N-1 children and so forth.
While recursively building the tree, each leaf node is stored in a list. Recursively 
calling the parent of each leaf node until reach a null pointer (i.e. the root) will
create a list of all the possible paths. An iteration through this list of paths
comparing each path's distance will result in the optimalPath (i.e the path with
the smallest distance).

Calculates nearest path using a BinaryHeap. All edges in connected graph inserted
into a BinaryHeap. The first vertex of the edge that is returned when 
deleteMin() performed on heap will be the starting point. This ensures that the 
the starting vertex is the vertex with the smallest distance edge coming out
of it. A HashMap with Vertex as key and BinaryHeap of Edges (all edges coming 
out of that particular vertex) is also created. Essentially, this allows 
for calculating the smallest edge coming out of a vertex and therefore determing
which vertex should be visited next (as long as said vertex has not been visited 
before). Once a vertex is visited, the field 'visited' becomes true and it is 
added to an ArrayList<Vertex> that will become a Path.

In the two cases (Optimal and Nearest) an ArrayList<Vertex> was calculated and then
the origin city was added to create a circuit (i.e TSP returns home).
