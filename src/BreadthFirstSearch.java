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
		return null;
	}
}
