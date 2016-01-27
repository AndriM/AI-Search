import java.util.Collection;

public class Node {
	
	private State state;
	private Collection<State> childStates;
	private State parentState;
	private String action;
	
	public Node(State state, State parentState, String action) {
		this.state = state;
		this.parentState = parentState;
		this.action = action;
	}
	
	Collection<String> getActions() {
		//TODO: Recursive method to gather all the actions from current node to root
		return null;
	}
}
