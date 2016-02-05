import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class BreadthFirstSearch implements SearchAlgorithm {
	
	private Node root;
	HashMap<State, State> explored;
	LinkedList<Node> frontier;
	
	@Override
	public Stack<String> getActionSequence(State initState) {
		frontier = new LinkedList<>();
		explored = new HashMap<>();
		this.root = new Node(initState, null, null);
		if(root.state.isGoal()) return root.getSolution();
		this.frontier.add(root);
		int numberOfExpansions = 0;
		int maximumSizeOfFrontier = 1;
		while(true) {
			if(frontier.isEmpty()) return failure();
			maximumSizeOfFrontier = (frontier.size() > maximumSizeOfFrontier) ? frontier.size() : maximumSizeOfFrontier;
			Node currentNode = frontier.pop();
			explored.put(currentNode.state, currentNode.state);
			numberOfExpansions++;
			for(String action : currentNode.state.legalActions()) {
				Node childNode = new Node(currentNode.state.nextState(action), currentNode, action);
				if(!explored.containsKey(childNode.state) && !frontier.contains(childNode)) {
					if(childNode.state.isGoal()) { 
						System.out.println("NE: " + numberOfExpansions + " MS: " + maximumSizeOfFrontier);
						return childNode.getSolution();
					}
					frontier.add(childNode);
				}
			}
		}
	}
	
	private Stack<String> failure() {
		System.out.println("No solution found!");
		return null;
	}
}
