package normchan.mj.game.strategy;

import normchan.mj.game.Player;

public abstract class AbstractPlayerStrategy implements PlayerStrategy {

	protected final Player player;

	public AbstractPlayerStrategy(Player player) {
		super();
		this.player = player;
	}

}