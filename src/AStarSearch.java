import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStarSearch implements SearchAlgorithm{
	
	private class AStarNode extends Node{
		private int f;
		private int g;

		public AStarNode(State state, AStarNode parentNode, String action) {
			super(state, (AStarNode)parentNode, action);
			g = parentNode != null ? parentNode.g + this.state.getActionCost(action) : 0;
			f = g + heuristic();
		}
		
		@Override
		public int compareTo(Node node) {
			if(!(node instanceof AStarNode))
				return super.compareTo(node);
			AStarNode aNode = (AStarNode) node;
			if(this.f != aNode.f)
				return this.f < aNode.f ? -1 : 1;
			return 0;
		}
		
		/**
		 * returns the cost from the given position to the dirt farthest away + the cost from the farthest dirt to the home position
		 * + the cost of sucking all the dirt
		 */
		private int heuristic() {
			int h = state.dirt.size() * state.getActionCost("SUCK");
			h += !state.turned_on && !state.dirt.isEmpty() ? state.getActionCost("TURN_ON") : 0;
			h += state.turned_on ? state.getActionCost("TURN_OFF") : 0;
			Position[] dirts = this.state.getStateDirt();
			Position currentPos = this.state.position;
			if(dirts.length != 0) {
				Position dirt = dirts[0];
				int distance = this.state.travelCost(this.state.position, dirt);
				for(int i = 1; i < dirts.length; i++) {
					int newDistance = this.state.travelCost(this.state.position, dirts[i]);
					if(newDistance > distance) {
						distance = newDistance;
						dirt = dirts[i];
					}
				}
				currentPos = dirt;
				h += distance;
			}
			return h + this.state.travelCost(currentPos, this.state.world.homePosition);
		}
	}
	
	@Override
	public Stack<String> getActionSequence(State initState) {
		PriorityQueue<AStarNode> frontier = new PriorityQueue<>();
		HashMap<State, AStarNode> explored = new HashMap<>();
		AStarNode root = new AStarNode(initState, null, null);
		frontier.add(root);
		int maxFrontier = 0;
		int numberOfExpansions = 0;
		while(!frontier.isEmpty()){
			maxFrontier = frontier.size() > maxFrontier ? frontier.size() : maxFrontier;
			AStarNode curr_node = frontier.poll();
			State curr_state = curr_node.state;
			if(curr_node.state.isGoal()){
				System.out.println("Number of Expansions: " + numberOfExpansions + ", Max Frontier: " + maxFrontier);
				return curr_node.getSolution();
			}
			if(!explored.containsKey(curr_state)) {
				explored.put(curr_state, curr_node);
				for(String action : curr_node.state.legalActions()) {
						AStarNode childNode = new AStarNode(curr_node.state.nextState(action), curr_node, action);
						frontier.add(childNode);
				}
				numberOfExpansions++;
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
