import java.util.HashMap;
import java.util.Stack;

public class DepthFirstSearch implements SearchAlgorithm {
	
	private Node root;
	private HashMap<State, State> explored;
	Stack<Node> frontier;
	
	@Override
	public Stack<String> getActionSequence(State initState) {
		frontier = new Stack<>();
		explored = new HashMap<>();
		this.root = new Node(initState, null, null);
		if(root.state.isGoal()) return root.getSolution();
		this.frontier.push(root);
		while(true) {
			if(frontier.isEmpty()) failure();
			Node currentNode = frontier.pop();
			explored.put(currentNode.state, currentNode.state);
			for(String action : currentNode.state.legalActions()) {
				Node childNode = new Node(currentNode.state.nextState(action), currentNode, action);
				if(!explored.containsKey(childNode.state) && !frontier.contains(childNode)) {
					if(childNode.state.isGoal()) 
						return childNode.getSolution();
					frontier.push(childNode);
				}
			}
		}
	}
	
	private Stack<String> failure() {
		System.out.println("No solution found!");
		return null;
	}
}
