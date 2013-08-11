package normchan.mj.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import normchan.mj.game.model.Dragon;
import normchan.mj.game.model.DragonTile;
import normchan.mj.game.model.FlowerTile;
import normchan.mj.game.model.Kong;
import normchan.mj.game.model.Meld;
import normchan.mj.game.model.SimpleTile;
import normchan.mj.game.model.Suit;
import normchan.mj.game.model.Tile;
import normchan.mj.game.model.Wind;
import normchan.mj.game.model.WindTile;

public class GameState {
	private final List<Tile> drawPile = new ArrayList<Tile>();
	private final List<Tile> discardPile = new ArrayList<Tile>();
	private Player dealer;
	private Player winner;
	private Wind currentWind;
	private Player currentPlayer;
	private Discard currentDiscard;
	private MeldClaim currentClaim;
	private Set<Player> readyPlayers = new HashSet<Player>();
	
	public enum ClaimType { WIN, KONG, PONG, CHOW };
	final private List<ClaimType> claimTypeList = Arrays.asList(ClaimType.values());
	
	class MeldClaim implements Comparable<MeldClaim> {
		final ClaimType type;
		final Player player;
		final int distance;
		
		public MeldClaim(ClaimType type, Player player) {
			super();
			this.type = type;
			this.player = player;
			this.distance = player.getPosition() - currentDiscard.getPlayer().getPosition() + 
				(player.getPosition() < currentDiscard.getPlayer().getPosition() ? Wind.values().length : 0);
		}

		public void process() {
			discardPile.remove(currentDiscard.getTile());
			Meld meld = player.acceptDiscard(type, currentDiscard.getTile());
			if (meld != null) {
				// If meld is not null, then no one has declared a win
				if (type == ClaimType.KONG) {
					notifyMgr.notifyListeners(new GameStateEvent(GameStateEvent.Type.KONG_DISPLAYED, player, meld));
				} else if (type == ClaimType.PONG) {
					notifyMgr.notifyListeners(new GameStateEvent(GameStateEvent.Type.PONG_DISPLAYED, player, meld));
				} else if (type == ClaimType.CHOW) {
					notifyMgr.notifyListeners(new GameStateEvent(GameStateEvent.Type.CHOW_DISPLAYED, player, meld));
				}
			}
			GameState.this.currentDiscard = null;
			GameState.this.currentClaim = null;
			GameState.this.currentPlayer = player;
		}
		
		private boolean sameClaimPriorityAs(MeldClaim mc) {
			if (this.type == mc.type)
				return true;
			
			if (this.type == ClaimType.KONG && mc.type == ClaimType.PONG)
				return true;
			
			if (this.type == ClaimType.PONG && mc.type == ClaimType.KONG)
				return true;
			
			return false;
		}

		@Override
		public int compareTo(MeldClaim mc) {
			if (sameClaimPriorityAs(mc) && this.player.equals(mc.player))
				return 0;
			else if (sameClaimPriorityAs(mc)) {
				return this.distance - mc.distance;
			} else {
				return claimTypeList.indexOf(this.type) - claimTypeList.indexOf(mc.type);
			}
		}
	}
	
	private class NotificationManager {
		List<GameStateListener> listeners = new ArrayList<GameStateListener>();
		
		void addListener(GameStateListener listener) {
			listeners.add(listener);
		}
		
		void removeListener(GameStateListener listener) {
			listeners.remove(listener);
		}
		
		void notifyListeners(GameStateEvent event) {
			for (GameStateListener listener : listeners) {
				listener.gameStateChanged(event);
			}
		}
	}
	private NotificationManager notifyMgr = new NotificationManager();
	
	public void addListener(GameStateListener listener) {
		notifyMgr.addListener(listener);
	}
	
	public void removeListener(GameStateListener listener) {
		notifyMgr.removeListener(listener);
	}
	
	public void gameStateUpdate(GameStateEvent event) {
		notifyMgr.notifyListeners(event);
	}
	
	public void init() {
		for (Suit suit : Suit.values()) {
			for (int i = 1; i <= 9; i++) {
				for (int j = 0; j < 4; j++) {
					// Four simple tiles per value/suit combination
					drawPile.add(new SimpleTile(suit, i));
				}
			}
		}
		
		for (Wind wind : Wind.values()) {
			for (int j = 0; j < 4; j++) {
				// Four tiles per wind
				drawPile.add(new WindTile(wind));
			}
		}
		
		for (Dragon dragon : Dragon.values()) {
			for (int j = 0; j < 4; j++) {
				// Four tiles per dragon
				drawPile.add(new DragonTile(dragon));
			}
		}
		
		for (FlowerTile.Type type : FlowerTile.Type.values()) {
			for (int i = 1; i <= 4; i++) {
				// Four flower tiles per type
				drawPile.add(new FlowerTile(type, i));
			}
		}
	}
	
	public void startRound(List<Player> players) {
		int index = 0;
		if (dealer != null) {
			index = players.indexOf(dealer)+1;
			if (index >= players.size()) {
				index = 0;
			}
		}
		
		this.currentWind = Wind.values()[index];
		this.dealer = players.get(index);
		this.currentPlayer = dealer;
		int pos = players.size()-index;
		for (int i = 0; i < players.size(); i++) {
			if (pos >= players.size())
				pos = 0;
			players.get(i).startRound(pos+1);
			pos++;
		}
		
		drawPile.addAll(discardPile);
		discardPile.clear();
		readyPlayers.clear();
		winner = null;
	}
	
	public void shuffleTiles() {
		Collections.shuffle(drawPile, new Random(Calendar.getInstance().getTimeInMillis()));
	}
	
	public void handleDiscard(Discard discard) {
		this.currentDiscard = discard;
		discardPile.add(discard.getTile());
		notifyMgr.notifyListeners(new GameStateEvent(GameStateEvent.Type.TILE_DISCARDED, discard.getPlayer(), discard));
	}
	
	public Tile drawTile() {
		if (drawPile.isEmpty())
			throw new OutOfTilesException();
		return drawPile.remove(0);
	}
	
	public Tile drawReplacementTile() {
		if (drawPile.isEmpty())
			throw new OutOfTilesException();
		return drawPile.remove(drawPile.size()-1);
	}
	
	public void claimDiscard(Player player, ClaimType type) {
		MeldClaim newClaim = new MeldClaim(type, player);
		if (currentClaim == null || newClaim.compareTo(currentClaim) < 0) {
			this.currentClaim = newClaim;
		}
	}
	
	public void declareKong(Player player, Kong kong) {
		// Player declares kong based on drawn tiles, not from discard
		notifyMgr.notifyListeners(new GameStateEvent(GameStateEvent.Type.KONG_DISPLAYED, player, kong));
	}
	
	public void declareWin(Player player) {
		this.winner = player;
		notifyMgr.notifyListeners(new GameStateEvent(GameStateEvent.Type.WINNING_HAND, player, player.getHand()));
	}
	
	public boolean roundComplete() {
		return winner != null || drawPile.size() <= 7;
	}
	
	public Player getDealer() {
		return dealer;
	}

	public Player getWinner() {
		return winner;
	}

	public Wind getCurrentWind() {
		return currentWind;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public MeldClaim getCurrentClaim() {
		return currentClaim;
	}

	public Set<Player> getReadyPlayers() {
		return readyPlayers;
	}

}