package normchan.mj.game.model;

import java.util.List;


public class Kong extends Meld {
	private Kong(List<Tile> tiles) {
		super(tiles);
	}

	public static Kong makeInstance(List<Tile> tiles) throws Exception {
		if (tiles == null) throw makeException();
		
		if (tiles.size() != 4) throw makeException();
		
		Tile first = tiles.get(0);
		for (int i = 1; i < tiles.size(); i++) {
			if (!first.equals(tiles.get(i)))
				throw makeException();
		}
		
		return new Kong(tiles);
	}
	
	private static Exception makeException() {
		return new Exception("Kong must have four identical tiles");
	}
	
	public String toString() {
		return "Kong--"+tiles;
	}
}
