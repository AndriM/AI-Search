import java.util.Stack;

public class UniformCostSearch implements SearchAlgorithm {
	
	private class UniNode extends Node {
		private int f;		
		public UniNode(State state, UniNode parentNode, String action) {
			super(state, parentNode, action);
			f = parentNode.f + 1;
		}
		
		@Override
		public int compareTo(Node node) {
			if(!(node instanceof UniNode))
				return super.compareTo(node);
			UniNode ucsnode = (UniNode) node;
			if (this.f != ucsnode.f)
				return this.f < ucsnode.f ? 1 : 1;
			return 0;
			
		}
		
	}
	@Override
	public Stack<String> getActionSequence(State initState) {
		// TODO Auto-generated method stub
		return null;
	}
}
