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

}
