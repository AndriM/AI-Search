import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class UniformCostSearch implements SearchAlgorithm {



	private class UniNode extends Node {
		private int f;		
		public UniNode(State state, UniNode parentNode, String action) {
			super(state, parentNode, action);
			if(parentNode != null) {
				f = parentNode.f + 1;
			}
			else{
				f = 0;
			}
		}
		
		@Override
		public int compareTo(Node node) {
			if(!(node instanceof UniNode))
				return super.compareTo(node);
			UniNode ucsnode = (UniNode) node;
			if (this.f != ucsnode.f)
				return this.f < ucsnode.f ? -1 : 1;
			return 0;
			
		}
		
	}
	@Override
	public Stack<String> getActionSequence(State initState) {
		PriorityQueue<UniNode> frontier = new PriorityQueue<>();
		HashMap<State, UniNode> explored = new HashMap<>();
		UniNode root = new UniNode(initState, null, null);
		frontier.add(root);
		while(!frontier.isEmpty()){
			UniNode curr_node = frontier.poll();
			State curr_state = curr_node.state;
			if(curr_node.state.isGoal()){
				return curr_node.getSolution();
			}
			if(explored.containsKey(curr_state)) {
				UniNode explored_node = explored.get(curr_state);
				int cmp = curr_node.compareTo(explored_node);
				if (cmp >= 0) {
					continue;
				}
			}
			explored.put(curr_state, curr_node);
			for(String action : curr_node.state.legalActions()) {
					UniNode childNode = new UniNode(curr_node.state.nextState(action), curr_node, action);
					frontier.add(childNode);
			}
		}
		return failure();
	}

	private Stack<String> failure() {
		System.out.println("No solution found!");
		return null;
	}
}
