package normchan.mj.game;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import normchan.mj.game.GameState.ClaimType;
import normchan.mj.game.hand.Hand;
import normchan.mj.game.model.FlowerTile;
import normchan.mj.game.model.Kong;
import normchan.mj.game.model.Meld;
import normchan.mj.game.model.SimpleTile;
import normchan.mj.game.model.Suit;
import normchan.mj.game.model.Tile;
import normchan.mj.game.model.Wind;
import normchan.mj.game.strategy.PlayerStrategy;

public class Player implements GameStateListener {
	private Log log = LogFactory.getLog(this.getClass());

	private final GameState gameState;
	private PlayerStrategy strategy;
	private Hand hand;
	private boolean dealer;
	private int chipBalance = 100;
	private Wind wind;
	private int position;
	private Player next;
	
	public Player(GameState gameState) {
		this.gameState = gameState;
		gameState.addListener(this);
	}
	
	public boolean isBankrupt() {
		return chipBalance <= 0;
	}
	
	public Player getNextPlayer() {
		return next;
	}
	
	public void setNextPlayer(Player next) {
		this.next = next;
	}
	
	public void startRound(int position) {
		this.dealer = (position == 1);
		this.position = position;
		this.wind = Wind.values()[position-1];
		
		this.hand = new Hand();
	}
	
	public void drawInitialHand() {
		if (wind == Wind.SOUTH) {
			if (hand.getTotalTileCount() == 0) {
				hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 2));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 3));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 4));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 5));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 6));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 7));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 7));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 8));
				hand.addTile(new SimpleTile(Suit.BAMBOO, 9));
			}
			next.drawInitialHand();
			return;
		}
		
		if (hand.getTotalTileCount() < 12) {
			drawTile(4);
		} else if (hand.getTotalTileCount() < 14) {
			// Only the dealer will get to draw 14th tile
			drawTile(1);
		}
		
		if (hand.getTotalTileCount() < 14) {
			next.drawInitialHand();
		}
	}
	
	public void replaceInitialFlowers() {
		int missing = 13 + (dealer?1:0) - hand.getHiddenTileCount();
		if (missing > 0) {
			drawTile(missing, true);
		}
		
		missing = 13 + (dealer?1:0) - hand.getHiddenTileCount();
		if (missing == 0) {
			gameState.getReadyPlayers().add(this);
		}
		
		if (gameState.getReadyPlayers().size() < 4) {
			next.replaceInitialFlowers();
		}
	}
	
	public void checkHand() {
		boolean processed = false;
		// Repeat until all kongs are taken care of
		while (!processed) {
			if (hand.canWin(null))
				strategy.winPossible();
			processed = hand.processKongs(strategy);
		}
	}
	
	public Tile drawTile() {
		return drawTile(false);
	}
	
	private Tile drawTile(boolean replacement) {
		if (wind == Wind.SOUTH) {
			Tile tile = new SimpleTile(Suit.BAMBOO, 7);
			hand.addTile(tile);
			return tile;
		}
		
		Tile tile = drawTile(1, replacement);
		gameState.gameStateUpdate(new GameStateEvent(GameStateEvent.Type.TILE_DRAWN, this, tile));
		
		while (tile instanceof FlowerTile) {
			gameState.gameStateUpdate(new GameStateEvent(GameStateEvent.Type.FLOWER_DISPLAYED, this, tile));
			tile = drawTile(1, true);
			gameState.gameStateUpdate(new GameStateEvent(GameStateEvent.Type.TILE_DRAWN, this, tile));
		}
		
		return tile;
	}
	
	private Tile drawTile(int count) {
		return drawTile(count, false);
	}
	
	private Tile drawTile(int count, boolean replacement) {
		Tile tile = null;
		for (int i=0; i < count; i++) {
			if (replacement) {
				tile = gameState.drawReplacementTile();
			} else {
				tile = gameState.drawTile();
			}
			hand.addTile(tile);
		}
		
		return tile;
	}
	
	public Tile discard() {
		Tile discard = strategy.chooseDiscard();
		
		return discard;
	}
	
	public Meld acceptDiscard(ClaimType type, Tile discard) {
		if (type == ClaimType.WIN) {
			gameState.declareWin(this);
			return null;
		} else { 
			return strategy.acceptDiscard(type, discard);
		}
	}
	
	public void declareKong(Tile tile) {
		Meld meld = hand.addMeld(ClaimType.KONG, tile);
		gameState.declareKong(this, (Kong)meld);
		drawTile(true); // draw replacement tile for kong
	}
	
	public void declareWin() {
		gameState.declareWin(this);
	}
	
	public void gameStateChanged(GameStateEvent event) {
		log.debug(getDisplayName()+" gameStateChanged called");
		if (event.getType() == GameStateEvent.Type.TILE_DISCARDED) {
			ClaimType claimType = strategy.discardNotification((Discard)event.getSource());
			log.debug("Calling discardNotification on player strategy returned "+claimType);
			if (claimType != null && canClaim(claimType, (Discard)event.getSource())) {
				gameState.claimDiscard(this, claimType);
			}
		}
	}
	
	private boolean canClaim(ClaimType type, Discard discard) {
		if (type == ClaimType.CHOW) {
			log.debug("Checking if player can claim Chow");
			if (!(discard.getTile() instanceof SimpleTile))
				return false;
			
			int discardPos =  discard.getPlayer().getPosition();
			log.debug("discard pos: "+discardPos+" current pos: "+position);
			if (position == 1) {
				if (discardPos != 4) {
					log.debug("failed first check");
					return false;
				}
			} else if (discardPos != position - 1) {
				log.debug("failed second check");
				return false;
			}
		}
		return hand.canClaim(type, discard);
	}
	
	public void payLoss(int amount) {
		chipBalance -= amount;
		System.out.println(getDisplayName()+" current balance: "+chipBalance);
	}
	
	public void collectWin(int amount) {
		chipBalance += amount;
		System.out.println(getDisplayName()+" current balance: "+chipBalance);
	}

	public void setStrategy(PlayerStrategy strategy) {
		this.strategy = strategy;
	}

	public int getPosition() {
		return position;
	}

	public Hand getHand() {
		return hand;
	}
	
	public String getDisplayName() {
		return "Player "+wind+(dealer?" (dealer)":"");
	}

	public String toString() {
		return getDisplayName()+": "+hand+"\n";
	}
}
