import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	
	public State(State s) {
		this.orientation = s.orientation;
		this.position = s.position;
		this.turned_on = s.turned_on;
		this.dirt = s.dirt;
		this.world = s.world;
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
		List<String> actions = new ArrayList<>();
		if(!this.turned_on)
			actions.add("TURN_ON");
		else if(this.turned_on && this.position.equals(world.homePosition) && this.dirt.isEmpty())
			actions.add("TURN_OFF");
		else if(dirt.contains(this.position))
			actions.add("SUCK");
		else {
			if(!world.isPositionObstacle(position.changePositionInDirection(orientation)))
				actions.add("GO");
			if(!world.isPositionObstacle(position.changePositionInDirection(orientation.left())))
				actions.add("TURN_LEFT");
			if(!world.isPositionObstacle(position.changePositionInDirection(orientation.right())))
				actions.add("TURN_RIGHT");
		}
		return actions;
	}
	
	public State nextState(String action) {
		State newState = new State(this);
		if(action.equals("GO"))
			newState.position = newState.position.changePositionInDirection(orientation);
		else if(action.equals("TURN_LEFT"))
			newState.orientation = newState.orientation.left();
		else if(action.equals("TURN_RIGHT"))
			newState.orientation = newState.orientation.right();
		else if(action.equals("SUCK"))
			newState.dirt = getNextStateDirt();
		else if(action.equals("TURN_ON"))
			newState.turned_on = true;
		else if(action.equals("TURN_OFF"))
			newState.turned_on = false;
		
		return newState;
	}
	
	private Collection<Position> getNextStateDirt() {
		List<Position> newDirt = new ArrayList<>();
		for(Position dirt : this.dirt) {
			if(!this.position.equals(dirt))
				newDirt.add(dirt);
		}
		return newDirt;
	}
}
