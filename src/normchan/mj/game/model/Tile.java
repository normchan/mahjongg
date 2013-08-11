package normchan.mj.game.model;

public abstract class Tile implements Comparable<Tile> {
	public abstract int getValue();
	
	public boolean equals(Tile tile) {
		return compareTo(tile) == 0;
	}
	
	public int compareTo(Tile tile) {
		return getValue() - tile.getValue();
	}
}
