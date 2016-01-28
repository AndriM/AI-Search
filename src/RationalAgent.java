import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RationalAgent implements Agent {
	private SearchAlgorithm search;
	private World world;
	private Stack<String> actionStack;
	
	public RationalAgent(SearchAlgorithm searchAlgorithm) {
		this.search = searchAlgorithm;
	}
	
	@Override
	public void init(Collection<String> percepts) {
		List<Position> dirt = new ArrayList<>();
		HashMap<Position, Position> obstacles = new HashMap<>();
		Position homePosition = null;
		Orientation orientation = null;
		
		Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
		for(String percept : percepts) {
			Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
			if(perceptNameMatcher.matches()) {
				String perceptName = perceptNameMatcher.group(1);
				if(perceptName.equals("AT")) {
					Matcher m = Pattern.compile("\\(\\s*AT\\s+(DIRT|OBSTACLE)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if(m.matches()){
						Position pos = new Position(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
						if(m.group(0).equals("DIRT"))
							dirt.add(pos);
						else
							obstacles.put(pos, pos);
					}
				}
				else if(perceptName.equals("HOME")) {
					Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if(m.matches())
						homePosition = new Position(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
				}
				else if(perceptName.equals("SIZE")) {
					Matcher m = Pattern.compile("\\(\\s*SIZE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if(m.matches())
						addWallsToObstacles(obstacles, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
				}
				else if(perceptName.equals("ORIENTATION")) {
					Matcher m = Pattern.compile("\\(\\s*ORIENTATION\\s+(NORTH|EAST|SOUTH|WEST)\\s*\\)").matcher(percept);
					if(m.matches())
						orientation = getOrientation(m.group(1));
				}
				else
					System.out.println("other percept:" + percept);
			}
			else
				System.err.println("strange percept that does not match pattern: " + percept);
		}
		
		world = new World(homePosition, obstacles);
		State initial = new State(homePosition, orientation, false, dirt, world);
		State goal = new State(homePosition, orientation, false, new ArrayList<>(), world);
		actionStack = search.getActionSequence(initial, goal);
	}

	@Override
	public String nextAction(Collection<String> percepts) {
		return "TURN_OFF";
	}
	
	private void addWallsToObstacles(HashMap<Position, Position> obstacles, int x, int y) {
		for(int i = 1; i < x + 2; i++) {
			Position upperWallCell = new Position(i, 0);
			Position lowerWallCell = new Position(i, y + 1);
			obstacles.put(upperWallCell, upperWallCell);
			obstacles.put(lowerWallCell, lowerWallCell);
		}
		
		for(int i = 1; i < y + 2; i++) {
			Position leftWallCell = new Position(0, i);
			Position rightWallCell = new Position(x + 1, i);
			obstacles.put(leftWallCell, leftWallCell);
			obstacles.put(rightWallCell, rightWallCell);
		}
	}
	
	private Orientation getOrientation(String stringOrientation) {
		if(stringOrientation.equals("NORTH"))
			return Orientation.NORTH;
		else if(stringOrientation.equals("EAST"))
			return Orientation.EAST;
		else if(stringOrientation.equals("SOUTH"))
			return Orientation.SOUTH;
		else if(stringOrientation.equals("WEST"))
			return Orientation.WEST;
		return null;
	}
}
