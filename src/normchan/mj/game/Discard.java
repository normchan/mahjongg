package normchan.mj.game;

import normchan.mj.game.model.Tile;

public class Discard {
	private final Player player;
	private final Tile tile;
	
	public Discard(Player player, Tile tile) {
		super();
		this.player = player;
		this.tile = tile;
	}

	public Player getPlayer() {
		return player;
	}

	public Tile getTile() {
		return tile;
	}
}
