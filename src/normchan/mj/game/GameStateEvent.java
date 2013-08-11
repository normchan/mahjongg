package normchan.mj.game;

import java.util.EventObject;

public class GameStateEvent extends EventObject {
	public enum Type { INITIAL_HAND, TILE_DRAWN, TILE_DISCARDED, WINNING_HAND, KONG_DISPLAYED, PONG_DISPLAYED, CHOW_DISPLAYED, FLOWER_DISPLAYED };
	private final Type type;
	private final Player player;

	public GameStateEvent(Type type, Player player, Object arg0) {
		super(arg0);
		this.type = type;
		this.player = player;
	}

	public Type getType() {
		return type;
	}

	public Player getPlayer() {
		return player;
	}

}
