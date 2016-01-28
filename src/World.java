import java.util.HashMap;

public class World {
	public final Position homePosition;
	private final HashMap<Position, Position> obstacles;
	
	public World(Position homePosition, HashMap<Position, Position> obstacles) {
		this.homePosition = homePosition;
		this.obstacles = obstacles;
	}
	
	public boolean isPositionObstacle(Position pos) {
		return obstacles.containsKey(pos);
	}
}
