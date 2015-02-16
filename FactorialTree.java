import java.util.ArrayList;

public class FactorialTree<AnyType> {
	private int numPos; // Number of possibilities
	private Node<AnyType> root;
	private ArrayList<Node<AnyType>> leafList;

	public FactorialTree(AnyType[] list) { 
		numPos = list.length;
		
		leafList = new ArrayList<Node<AnyType>>();
		
		root = new Node<AnyType> (null, null, numPos);
		buildTree(root, list, numPos);
	}

	// Number of possibilities become number of children of a node.
	private void buildTree(Node<AnyType> parent, AnyType[] childList, int numChild){
		// If it's a leaf node
		if (numChild == 1){
			Node<AnyType> leaf = new Node<AnyType>(parent, childList[0], numChild);
			parent.addChildNode(leaf);
			leafList.add(leaf);
			return;
		}
		
		// If root.
		else if (numChild == numPos){
			
			for (int i = 0; i < childList.length; i++){
				
				Node<AnyType> childNode = new Node<AnyType>(parent, childList[i], numChild);
				parent.addChildNode(childNode);
			
				buildTree(childNode, remove(childList, i), numChild-1);
			}
			return;
		}

		// Recursively populates parent Node's children.
		else { 
			
			for (int i = 0; i < childList.length; i++){
				
				Node<AnyType> childNode = new Node<AnyType>(parent, childList[i], numChild);
				parent.addChildNode(childNode);
					
				buildTree(childNode, remove(childList, i), numChild-1);
			}
		}
	}
	
	public ArrayList<ArrayList<AnyType>> getAllCombinations(){

		ArrayList<ArrayList<AnyType>> comboList = new ArrayList<ArrayList<AnyType>>(leafList.size());
		
		for (int i = 0; i < leafList.size(); i++){
			
			Node<AnyType> leaf = leafList.get(i);
			
			ArrayList<AnyType> nodesList = getCombination(new ArrayList<AnyType>(), leaf);
			// Makes path a circuit that goes back to starting vertex
			nodesList.add(leaf.data);
			
			comboList.add(nodesList);
		}
		
		return comboList;
	}
	
	private ArrayList<AnyType> getCombination(ArrayList<AnyType> list, Node<AnyType> node) {
		
		if (node.data == null) {
			return list;
		}
		else {
			AnyType nodeData = node.data;
			list.add(nodeData);
			
			getCombination(list, node.parent);
			
			return list;
		}
	}

	private AnyType[] remove(AnyType[] arr, int location){
		
		ArrayList<AnyType> newList = new ArrayList<AnyType>(arr.length);
		AnyType toRemove = arr[location];
		
		for (AnyType t : arr)
			if (!toRemove.equals(t))
				newList.add(t);
		
		AnyType[] newArr = newList.toArray( (AnyType[]) new Comparable[newList.size()] );
				
		return newArr;
	}
		
	private static class Node<AnyType> {
		AnyType data;
		Node<AnyType> parent;
		ArrayList<Node<AnyType>> children;

		Node(Node<AnyType> parent, AnyType data, int numChild) {
			this.parent = parent;
			this.data = data;
			
			if (numChild != 0) 
				children = new ArrayList<Node<AnyType>>(numChild);
			else
				children = null;
		}

		void addChildNode(Node<AnyType> n){
			children.add(n);
		} 
		
		public String toString(){
			return ("" + data);
		}
	}
}
