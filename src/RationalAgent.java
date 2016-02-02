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
		
		world = new World(homePosition, obstacles, x, y);
		State initial = new State(homePosition, orientation, false, dirt, world);
		printWorld(world, dirt, homePosition);
		actionStack = search.getActionSequence(initial);
		System.out.println(actionStack);
	}

	@Override
	public String nextAction(Collection<String> percepts) {
		if(actionStack != null && !actionStack.isEmpty())
			return actionStack.pop();
		return "TURN_OFF";
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
	
	private void printWorld(World world, List<Position> dirt, Position homePos) {
		char map[][] = new char[world.yMax + 2][world.xMax + 2];
		
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
		
		for(Position d : dirt){
			if(map[d.y][d.x] != 'X' && map[d.y][d.x] != 'H')
				map[d.y][d.x] = 'D';
			else
				map[d.y][d.x] = 'F';
		}
		
		for(int y = map.length - 1; y >= 0; y--) {
			for(int x = map[y].length - 1; x >= 0; x--)
				System.out.print(map[y][x]);
			System.out.println();
		}
	}
}
