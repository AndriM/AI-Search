import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class BreadthFirstSearch implements SearchAlgorithm {
	
	@Override
	public Stack<String> getActionSequence(State initState) {
		HashMap<State, State> explored = new HashMap<>();
		LinkedList<Node> frontier = new LinkedList<>();
		Node root = new Node(initState, null, null);
		if(root.state.isGoal()) return root.getSolution();
		frontier.add(root);
		int numberOfExpansions = 0;
		int maximumSizeOfFrontier = 1;
		while(!frontier.isEmpty()) {
			maximumSizeOfFrontier = (frontier.size() > maximumSizeOfFrontier) ? frontier.size() : maximumSizeOfFrontier;
			Node currentNode = frontier.pop();
			explored.put(currentNode.state, currentNode.state);
			numberOfExpansions++;
			for(String action : currentNode.state.legalActions()) {
				Node childNode = new Node(currentNode.state.nextState(action), currentNode, action);
				if(!explored.containsKey(childNode.state) && !frontier.contains(childNode)) {
					if(childNode.state.isGoal()) { 
						System.out.println("Number of Expansions: " + numberOfExpansions + " Maximum Frontier: " + maximumSizeOfFrontier);
						return childNode.getSolution();
					}
					frontier.add(childNode);
				}
			}
		}
		return failure();
	}
	
	/*
	 * returns the null stack and prints out failure to the standard output stream
	 */
	private Stack<String> failure() {
		System.out.println("No solution found!");
		return null;
	}
}
