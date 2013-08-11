package normchan.mj.game;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import normchan.mj.game.GameState.ClaimType;
import normchan.mj.game.model.Meld;
import normchan.mj.game.model.SimpleTile;
import normchan.mj.game.model.Suit;
import normchan.mj.game.model.Tile;
import normchan.mj.game.strategy.SimpleAutoPlayerStrategy;
import normchan.mj.game.strategy.TestPlayerStrategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class PlayerTest {
	private Log log = LogFactory.getLog(this.getClass());

	@Test
	public void testGameStateChanged() {
		GameState state = new GameState();
		Player north = new Player(state);
		north.startRound(4);
		north.setStrategy(new SimpleAutoPlayerStrategy(north));
		Player east = new Player(state);
		east.startRound(1);
		TestPlayerStrategy strategy = new TestPlayerStrategy(east);
		east.setStrategy(strategy);
		strategy.setClaimType(ClaimType.CHOW);
		Integer[] tileArray = {new Integer(3207), new Integer(3208)};
		List<Integer> tiles = Arrays.asList(tileArray);
		strategy.setTilesForMeld(tiles);
		east.getHand().addTile(new SimpleTile(Suit.BAMBOO, 2));
		east.getHand().addTile(new SimpleTile(Suit.BAMBOO, 4));
		east.getHand().addTile(new SimpleTile(Suit.BAMBOO, 7));
		east.getHand().addTile(new SimpleTile(Suit.BAMBOO, 8));
		east.getHand().addTile(new SimpleTile(Suit.BAMBOO, 8));
		east.getHand().addTile(new SimpleTile(Suit.BAMBOO, 9));
		Discard discard = new Discard(north, new SimpleTile(Suit.BAMBOO, 9));
		state.handleDiscard(discard);
		Assert.assertNotNull(state.getCurrentClaim());
		state.getCurrentClaim().process();
		List<Meld> melds = east.getHand().getMelds();
		Assert.assertFalse(melds.isEmpty());
		List<Tile> meldTiles = melds.get(0).getTiles();
		log.debug(meldTiles);
		Assert.assertEquals(meldTiles.get(0).getValue(), 3207);
		Assert.assertEquals(meldTiles.get(1).getValue(), 3208);
		Assert.assertEquals(meldTiles.get(2).getValue(), 3209);
	}

}
