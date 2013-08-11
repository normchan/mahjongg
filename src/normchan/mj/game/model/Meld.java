package normchan.mj.game.model;

import java.util.List;


public abstract class Meld {
	protected final List<Tile> tiles;

	public Meld(List<Tile> tiles) {
		super();
		this.tiles = tiles;
	}
	
	public int getTileCount() {
		return tiles.size();
	}

	public List<Tile> getTiles() {
		return tiles;
	}
	
	public String toString() {
		return tiles.toString();
	}
}
