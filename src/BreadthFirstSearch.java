import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class BreadthFirstSearch implements SearchAlgorithm {
	
	private Node root;
	private State goal;
	HashMap<State, State> explored;
	LinkedList<Node> frontier;
	
	@Override
	public Stack<String> getActionSequence(State initState, State goalState) {
		this.root = new Node(initState, null, null);
		this.goal = goalState;
		explored = new HashMap<>();
		frontier = new LinkedList<>();
		Stack<String> tempStackForReturn = new Stack<>();
		tempStackForReturn.push("TURN_ON");
		tempStackForReturn.push("TURN_OFF");
		return tempStackForReturn;
	}
}
