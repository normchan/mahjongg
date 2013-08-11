package normchan.mj.game.model;

import java.util.List;


public class Pong extends Meld {
	private Pong(List<Tile> tiles) {
		super(tiles);
	}

	public static Pong makeInstance(List<Tile> tiles) throws Exception {
		if (tiles == null) throw makeException();
		
		if (tiles.size() != 3) throw makeException();
		
		Tile first = tiles.get(0);
		for (int i = 1; i < tiles.size(); i++) {
			if (!first.equals(tiles.get(i)))
				throw makeException();
		}
		
		return new Pong(tiles);
	}
	
	private static Exception makeException() {
		return new Exception("Pong must have three identical tiles");
	}
	
	public String toString() {
		return "Pong--"+tiles;
	}
}
