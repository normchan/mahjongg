package normchan.mj.game.strategy;

import normchan.mj.game.Discard;
import normchan.mj.game.GameState;
import normchan.mj.game.GameState.ClaimType;
import normchan.mj.game.model.Meld;
import normchan.mj.game.model.Tile;

public interface PlayerStrategy {
	ClaimType discardNotification(Discard discard);
	Tile chooseDiscard();
	Meld acceptDiscard(GameState.ClaimType type, Tile discard);
	boolean winPossible();
	boolean kongPossible(Tile tile);
}
