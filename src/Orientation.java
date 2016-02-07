public enum Orientation {
	NORTH(0), 
	EAST(1), 
	SOUTH(2), 
	WEST(3);
	
	private final int value;
	
	private Orientation(int value) {
		this.value = value;
	}
	
	/**
	 * returns the orientation on your left
	 */
	public Orientation left() {
		if(this.value == 0)
			return Orientation.WEST;
		else if(this.value == 1)
			return Orientation.NORTH;
		else if(this.value == 2)
			return Orientation.EAST;
		
		return Orientation.SOUTH;
	}
	
	/**
	 * returns the orientation on your right
	 */
	public Orientation right() {
		if(this.value == 0)
			return Orientation.EAST;
		else if(this.value == 1)
			return Orientation.SOUTH;
		else if(this.value == 2)
			return Orientation.WEST;
		
		return Orientation.NORTH;
	}
};
