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
		
		private int heuristic() {
			int h = state.dirt.size() * state.getActionCost("SUCK");
			Position[] dirts = this.state.getStateDirt();
			Position currentPos = state.position;
			int startIndex = 0;
			int closestIndex = 0;
			while(startIndex < dirts.length) {
				Position closestDirt = dirts[startIndex];
				int distance = currentPos.manhattanDistance(closestDirt);
				for(int i = startIndex + 1; i < dirts.length; i++) {
					int newDistance = currentPos.manhattanDistance(dirts[i]);
					if(newDistance < distance) {
						distance = newDistance;
						closestDirt = dirts[i];
						closestIndex = i;
					}
				}
				h += distance * state.getActionCost("GO");
				currentPos = closestDirt;
				dirts[closestIndex] = dirts[startIndex];
				dirts[startIndex] = closestDirt;
				startIndex++;
			}
			return h + currentPos.manhattanDistance(state.world.homePosition) * state.getActionCost("GO");
		}
	}

	@Override
	public Stack<String> getActionSequence(State initState) {
		HashMap<State, State> explored = new HashMap<>();
		HashMap<State, AStarNode> isInFrontier = new HashMap<>();
		PriorityQueue<AStarNode> frontier = new PriorityQueue<>();
		AStarNode init = new AStarNode(initState, null, null);
		frontier.add(init);
		isInFrontier.put(init.state, init);
		int numberOfExpansions = 0;
		while(true) {
			System.out.println("NE: " + numberOfExpansions + ", MF: " + frontier.size());
			if(frontier.isEmpty()) return failure();
			AStarNode node = frontier.poll();
			if(node.state.isGoal()) return node.getSolution();
			explored.put(node.state, node.state);
			for(String action : node.state.legalActions()) {
				AStarNode childNode = new AStarNode(node.state.nextState(action), node, action);
				AStarNode oldNode = isInFrontier.get(childNode.state);
				if(!explored.containsKey(childNode.state) && oldNode == null) {
					frontier.add(childNode);
					isInFrontier.put(childNode.state, childNode);
				}
				else if(oldNode != null) {
					int cmp = childNode.compareTo(oldNode);
					if(cmp < 0) {
						frontier.remove(oldNode);
						frontier.add(childNode);
					}
				}
			}
		}
	}
	
	private Stack<String> failure() {
		System.out.println("No solution found!");
		return null;
	}

}
