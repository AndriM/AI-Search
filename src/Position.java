public class Position {
	
	public int x;
	public int y;
	
	public Position(int x, int y) {
		this.x = x; this.y = y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Position)) return false;
		Position pos = (Position) obj;
		return this.x == pos.x && this.y == pos.y;
	}
	
	@Override
	public int hashCode() {
		return (this.x * 2963)^(this.y * 2969);
	}
	
	public Position changePositionInDirection(Orientation orientation) {
		Position newPos = new Position(this.x, this.y);
		if(orientation == orientation.NORTH)
			newPos.y++;
		else if(orientation == orientation.EAST)
			newPos.x++;
		else if(orientation == orientation.SOUTH)
			newPos.y--;
		else if(orientation == orientation.WEST)
			newPos.x++;
		return newPos;
	}

}
