import java.util.Collection;

public class Node {
	
	private State state;
	private Node parentNode;
	private String action;
	
	public Node(State state, State parentState, String action) {
		this.state = state;
		this.parentNode = parentNode;
		this.action = action;
	}
	
	Collection<String> getActions() {
		//TODO: Recursive method to gather all the actions from current node to root
		return null;
	}
}
