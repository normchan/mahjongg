package normchan.mj.game.strategy;

import java.util.List;

import normchan.mj.game.Discard;
import normchan.mj.game.GameState.ClaimType;
import normchan.mj.game.Player;
import normchan.mj.game.model.Meld;
import normchan.mj.game.model.Tile;

public class TestPlayerStrategy extends AbstractPlayerStrategy {
	private ClaimType type;
	private List<Integer> tileValues;

	public TestPlayerStrategy(Player player) {
		super(player);
	}
	
	public void setClaimType(ClaimType type) {
		this.type = type;
	}

	@Override
	public ClaimType discardNotification(Discard discard) {
		return type;
	}

	@Override
	public Tile chooseDiscard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setTilesForMeld(List<Integer> tiles) {
		this.tileValues = tiles;
	}

	@Override
	public Meld acceptDiscard(ClaimType type, Tile discard) {
		if (type == ClaimType.CHOW) {
			return player.getHand().addChow(tileValues, discard);
		} else {
			return player.getHand().addMeld(type, discard);
		}
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
