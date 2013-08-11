package normchan.mj.game.strategy;

import normchan.mj.game.Discard;
import normchan.mj.game.Player;
import normchan.mj.game.GameState.ClaimType;
import normchan.mj.game.model.Meld;
import normchan.mj.game.model.Tile;

public class SimpleAutoPlayerStrategy extends AbstractPlayerStrategy {
	public SimpleAutoPlayerStrategy(Player player) {
		super(player);
	}

	@Override
	public ClaimType discardNotification(Discard discard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tile chooseDiscard() {
		return player.getHand().discardRandomTile();
	}

	@Override
	public Meld acceptDiscard(ClaimType type, Tile discard) {
		return null;
	}

	@Override
	public boolean winPossible() {
		return false;
	}

	@Override
	public boolean kongPossible(Tile tile) {
		return false;
	}
}
