import java.util.Stack;

public class Node implements Comparable<Node> {
	
	public final State state;
	private Node parentNode;
	private String action;
	
	public Node(State state, Node parentNode, String action) {
		this.state = state;
		this.parentNode = parentNode;
		this.action = action;
	}
	
	public Stack<String> getSolution() {
		Stack<String> actionStack = new Stack<>();
		return getSolutionHelper(this, actionStack);
	}
	
	private Stack<String> getSolutionHelper(Node node, Stack<String> actionStack) {
		if(node.action != null){
			actionStack.add(node.action);
			actionStack = getSolutionHelper(node.parentNode, actionStack);
		}
		return actionStack;
	}

	@Override
	public int compareTo(Node arg0) {
		return action.compareTo(arg0.action);
	}
}
