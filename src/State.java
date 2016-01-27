import java.util.Collection;

public class State {
	private Orientation orientation;
	private Position position;
	private boolean turned_on;
	private Collection<Position> dirt;
	private World world;

	public State(Position position, Orientation orientation, boolean turned_on, Collection<Position> dirt, World world) {
		this.position = position;
		this.orientation = orientation;
		this.turned_on = turned_on;
		this.world = world;
		this.dirt = dirt;
	}

	public String toString() {
		return "State{position: " + position + ", orientation: " + orientation + ", on:" + turned_on + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof State)) return false;
		State state = (State) obj;
		return this.position.equals(state.position)
				&& this.orientation.equals(state.orientation) && this.turned_on == state.turned_on;
	}
	
	@Override
	public int hashCode() {
		int hash = position.hashCode();
		if(orientation == Orientation.NORTH) hash *=  9907;
		else if(orientation == Orientation.EAST) hash *= 5189;
		else if(orientation == Orientation.SOUTH) hash *= 127;
		else if(orientation == Orientation.WEST) hash *= 109;
		hash *= turned_on == true ? 7121 : 1;
		return hash;
	}
	
	public boolean isGoal() {
		return (!turned_on && position.equals(world.homePosition) && dirt.isEmpty());
	}
	
	public Collection<String> legalActions() {
		return null;
	}
	
	public State nextState(String action) {
		return null;
	}
}
