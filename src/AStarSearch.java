import java.util.Stack;

public class AStarSearch implements SearchAlgorithm{
	
	private class AStarNode extends Node{
		private int f;
		private int g;

		public AStarNode(State state, Node parentNode, String action) {
			super(state, parentNode, action);
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
	}

	@Override
	public Stack<String> getActionSequence(State initState) {
		AStarNode node = new AStarNode(null, null, null);
		return null;
	}

}
