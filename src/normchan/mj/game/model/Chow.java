package normchan.mj.game.model;

import java.util.ArrayList;
import java.util.List;


public class Chow extends Meld {
	private Chow(List<Tile> tiles) {
		super(tiles);
	}

	public static Chow makeInstance(List<SimpleTile> simpleTiles) throws Exception {
		if (simpleTiles == null) throw makeException();
		
		if (simpleTiles.size() != 3) throw makeException();
		
		for (int i = 0; i < simpleTiles.size()-1; i++) {
			if (simpleTiles.get(i).compareTo(simpleTiles.get(i+1)) != -1)
				throw makeException();
		}
		
		List<Tile> tiles = new ArrayList<Tile>();
		tiles.addAll(simpleTiles);
		return new Chow(tiles);
	}
	
	private static Exception makeException() {
		return new Exception("Chow must have three sequential simple tiles of the same suit");
	}
	
	public String toString() {
		return "Chow--"+tiles;
	}
}
