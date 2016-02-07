import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class State {
	public Orientation orientation;
	public Position position;
	public boolean turned_on;
	public Collection<Position> dirt;
	public World world;

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
				&& this.orientation.equals(state.orientation) && this.turned_on == state.turned_on
				&& this.dirt.equals(state.dirt)
				&& this.world == state.world;
	}
	
	@Override
	public int hashCode() {
		//TODO: hashCode needs improvement!
		int hash = position.hashCode();
		if(orientation == Orientation.NORTH) hash *=  9907;
		else if(orientation == Orientation.EAST) hash *= 5189;
		else if(orientation == Orientation.SOUTH) hash *= 127;
		else if(orientation == Orientation.WEST) hash *= 109;
		hash *= turned_on == true ? 7121 : 1;
		
		for(Position d : dirt)
			hash *= d.hashCode();
		
		return hash;
	}
	
	public boolean isGoal() {
		return (!turned_on && position.equals(world.homePosition) && dirt.isEmpty());
	}
	
	/**
	 * returns all actions that legal to take in this state
	 */
	public Collection<String> legalActions() {
		List<String> actions = new ArrayList<>();
		if(!turned_on) // The only sensible action to take if the robot is not turned on is to TURN_ON
			actions.add("TURN_ON");
		else if(turned_on && position.equals(world.homePosition) && dirt.isEmpty()) // Only TURN_OFF if you are one step away from goal
			actions.add("TURN_OFF");
		else if(dirt.contains(position)) // Only include SUCK if you are at a dirty tile
			actions.add("SUCK");
		else {
			boolean isObstacleFront = world.isPositionObstacle(position.changePositionInDirection(orientation));
			boolean isObstacleLeft = world.isPositionObstacle(position.changePositionInDirection(orientation.left()));
			boolean isObstacleRight = world.isPositionObstacle(position.changePositionInDirection(orientation.right()));
			if(!isObstacleFront)
				actions.add("GO");
			if(!isObstacleLeft || isObstacleRight) //Only TURN_LEFT if there is no obstacle left or if there is obstacle at the right
				actions.add("TURN_LEFT");
			if(!isObstacleRight || isObstacleLeft)
				actions.add("TURN_RIGHT");
		}
		return actions;
	}
	
	/**
	 * returns the successor state for a given action
	 */
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
	
	/**
	 * returns a new collection of the dirts excluding the dirt that
	 * is in the current position 
	 */
	private Collection<Position> getNextStateDirt() {
		List<Position> newDirt = new ArrayList<>();
		for(Position dirt : this.dirt) {
			if(!this.position.equals(dirt))
				newDirt.add(dirt);
		}
		return newDirt;
	}
	
	/**
	 * returns all the dirt in a array
	 */
	public Position[] getStateDirt() {
		Position[] array = new Position[dirt.size()];
		int i = 0;
		for(Position d : dirt)
			array[i++] = d;
		return array;
	}
	
	/**
	 * Returns the cost of a given action in the state 
	 */
	public int getActionCost(String action) {
		//We always return 1 since all action costs are equal except when it causes a punishment
		//We have eliminated the chance that robot can perform an actions that cause
		//punishment cost so we return 1
		//This function should be implemented further if we want to have actions that do not have the same cost
		return 1;
	}
	
	/*
	 * returns the minimum travel cost between two points
	 */
	public int travelCost(Position pos1, Position pos2){
		int minimumTurnCost = pos1.x != pos2.x || pos1.y != pos2.y ? getActionCost("TURN_LEFT") : 0;
		int distance = ((Math.max(pos1.x, pos2.x) - Math.min(pos1.x, pos2.x)) + (Math.max(pos1.y, pos2.y) - Math.min(pos1.y, pos2.y))) * getActionCost("GO");
		return distance + minimumTurnCost;
	}
}
