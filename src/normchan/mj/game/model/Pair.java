package normchan.mj.game.model;

import java.util.List;


public class Pair {
	private final List<Tile> tiles;

	public Pair(List<Tile> tiles) throws Exception {
		super();
		
		if (tiles == null) throw makeException();
		
		if (tiles.size() != 2) throw makeException();
		
		if (!tiles.get(0).equals(tiles.get(1)))
			throw makeException();
		
		this.tiles = tiles;
	}

	private Exception makeException() {
		return new Exception("Pair must have two identical tiles");
	}

	public List<Tile> getTiles() {
		return tiles;
	}
}
