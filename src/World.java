import java.util.HashMap;

public class World {
	public final Position homePosition;
	public final HashMap<Position, Position> obstacles;
	public final int xMax;
	public final int yMax;
	
	public World(Position homePosition, HashMap<Position, Position> obstacles, int xMax, int yMax) {
		this.homePosition = homePosition;
		this.obstacles = obstacles;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	public boolean isPositionObstacle(Position pos) {
		return !isPositionInWorld(pos) || obstacles.containsKey(pos);
	}
	
	private boolean isPositionInWorld(Position pos) {
		return pos.x > 0 && pos.x <= this.xMax && pos.y > 0 && pos.y <= this.yMax;  
	}
}
