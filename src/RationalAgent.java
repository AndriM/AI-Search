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
		int x = 0; int y = 0;
		
		Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
		for(String percept : percepts) {
			Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
			if(perceptNameMatcher.matches()) {
				String perceptName = perceptNameMatcher.group(1);
				if(perceptName.equals("AT")) {
					Matcher m = Pattern.compile("\\(\\s*AT\\s+(DIRT|OBSTACLE)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if(m.matches()){
						Position pos = new Position(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
						if(m.group(1).equals("DIRT"))
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
					if(m.matches()) {
						addWallsToObstacles(obstacles, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
						x = Integer.parseInt(m.group(1));
						y = Integer.parseInt(m.group(2));
					}
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
		printWorld(world, dirt, homePosition, x, y);
		actionStack = search.getActionSequence(initial);
		System.out.println(actionStack);
	}

	@Override
	public String nextAction(Collection<String> percepts) {
		return actionStack.pop();
	}
	
	private void addWallsToObstacles(HashMap<Position, Position> obstacles, int x, int y) {
		for(int i = 1; i <= x; i++) {
			Position upperWallCell = new Position(i, 0);
			Position lowerWallCell = new Position(i, y + 1);
			obstacles.put(upperWallCell, upperWallCell);
			obstacles.put(lowerWallCell, lowerWallCell);
		}
		
		for(int i = 1; i <= y; i++) {
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
	
	private void printWorld(World world, List<Position> dirt, Position homePos, int width, int length) {
		char map[][] = new char[length + 2][width + 2];
		
		for(int y = 0; y < map.length; y++) {
			for(int x = 0; x < map[y].length; x++) {
				Position currPos = new Position(x, y);
				if(world.isPositionObstacle(currPos))
					map[currPos.y][currPos.x] = 'X';
				else
					map[currPos.y][currPos.x] = ' ';
			}
		}
		
		map[homePos.y][homePos.x] = 'H';
		
		for(Position d : dirt)
			map[d.y][d.x] = 'D';
		
		for(int y = map.length - 1; y >= 0; y--) {
			for(int x = map[y].length - 1; x >= 0; x--)
				System.out.print(map[y][x]);
			System.out.println();
		}
	}
}
