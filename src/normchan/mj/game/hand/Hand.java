package normchan.mj.game.hand;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import normchan.mj.game.Discard;
import normchan.mj.game.GameState.ClaimType;
import normchan.mj.game.model.Chow;
import normchan.mj.game.model.FlowerTile;
import normchan.mj.game.model.Kong;
import normchan.mj.game.model.Meld;
import normchan.mj.game.model.Pair;
import normchan.mj.game.model.Pong;
import normchan.mj.game.model.SimpleTile;
import normchan.mj.game.model.Tile;
import normchan.mj.game.strategy.PlayerStrategy;
import normchan.mj.game.util.SortedList;

public class Hand {
	private Log log = LogFactory.getLog(this.getClass());

	private boolean winner;
	private int hiddenTileCount = 0;
	private SortedMap<Integer, TileNode> hiddenTiles = new TreeMap<Integer, TileNode>();
	private List<FlowerTile> flowers = new ArrayList<FlowerTile>();
	private List<Meld> melds = new ArrayList<Meld>();
	private Pair eyes;
	
	static class TileNode extends ArrayList<Tile> {
	}
	
	public boolean canClaim(ClaimType type, Discard discard) {
		switch(type) {
		case PONG:
			return canPong(discard.getTile());
		case KONG:
			return canKong(discard.getTile());
		case CHOW:
			return canChow(discard.getTile());
		case WIN:
			return canWin(discard.getTile());
		default:
			throw new RuntimeException("Unknown claim type for Hand.canClaim.");
		}
	}
	
	private boolean canKong(Tile tile) {
		return hasEnoughTiles(tile, 3);
	}
	
	public boolean processKongs(PlayerStrategy playerStrategy) {
		for (TileNode node : hiddenTiles.values()) {
			if (node.size() >= 4 && playerStrategy.kongPossible(node.get(0)))
				return false;
		}
		
		return true;
	}
	
	private boolean canPong(Tile tile) {
		return hasEnoughTiles(tile, 2);
	}
	
	private boolean hasEnoughTiles(Tile tile, int number) {
		TileNode node = hiddenTiles.get(tile.getValue());
		return node != null && node.size() >= number;
	}
	
	private boolean canChow(Tile discard) {
		log.debug("Hand checking if chow is valid");
		if (hiddenTiles.get(discard.getValue()-2) != null &&
				hiddenTiles.get(discard.getValue()-1) != null)
				return true;
		
		log.debug("failed first check");
		if (hiddenTiles.get(discard.getValue()-1) != null &&
				hiddenTiles.get(discard.getValue()+1) != null)
				return true;
		
		log.debug("failed second check");
		if (hiddenTiles.get(discard.getValue()+1) != null &&
				hiddenTiles.get(discard.getValue()+2) != null)
				return true;
		
		log.debug("failed third check");
		return false;
	}
	
	public boolean canWin(Tile discard) {
		if (discard != null)
			addTile(discard);
		boolean winningHand = new WinEvaluator(hiddenTiles).isWinningHand();
		if (discard != null)
			removeTile(discard.getValue());
		
		return winningHand;
	}

	public Meld addChow(List<Integer> values, Tile discard) {
		List<SimpleTile> tiles = new ArrayList<SimpleTile>();
		tiles.add((SimpleTile)discard);
		tiles.add((SimpleTile)removeTile(values.get(0)));
		tiles.add((SimpleTile)removeTile(values.get(1)));
		Collections.sort(tiles);
		
		Meld chow = null;
		try {
			chow = Chow.makeInstance(tiles);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		melds.add(chow);
		return chow;
	}
	
	public Meld addMeld(ClaimType type, Tile discard) { 
		return addMeld(type, discard, false);
	}
	
	public Meld addMeld(ClaimType type, Tile discard, boolean fromHand) {
		switch (type) {
		case PONG:
		case KONG:
			List<Tile> tiles = new ArrayList<Tile>();
			tiles.add(fromHand ? removeTile(discard.getValue()) : discard);
			tiles.add(removeTile(discard.getValue()));
			tiles.add(removeTile(discard.getValue()));
			if (type == ClaimType.KONG) {
				tiles.add(removeTile(discard.getValue()));
			}

			Meld meld = null;
			try {
				if (type == ClaimType.KONG) {
					meld = Kong.makeInstance(tiles);
					
				} else {
					meld = Pong.makeInstance(tiles);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

			melds.add(meld);
			return meld;

		default:
			throw new RuntimeException("Unsupported meld type for Hand.addMeld.");
		}
	}
	
	private Tile getTileWithValue(int value) {
		TileNode node = hiddenTiles.get(value);
		if (node == null) return null;
		return node.get(0);
	}
	
	private Tile removeTile(int value) {
		Tile tile = null;
		TileNode node = hiddenTiles.get(value);
		if (node != null) {
			tile = node.get(0);
			if (node.size() == 1) {
				hiddenTiles.remove(tile.getValue());
			} else {
				node.remove(tile);
			}
			hiddenTileCount--;
		}
		
		return tile;
	}

	public void addTile(Tile tile) {
		if (tile instanceof FlowerTile) {
			flowers.add((FlowerTile)tile);
		} else {
			TileNode node = hiddenTiles.get(tile.getValue());
			if (node == null) {
				node = new TileNode();
				hiddenTiles.put(tile.getValue(), node);
			}
			node.add(tile);
			hiddenTileCount++;
		}
	}
	
	public Tile discardRandomTile() {
		int index = 0, stop = new Random(Calendar.getInstance().getTimeInMillis()).nextInt(getHiddenTileCount());
		for (TileNode node : hiddenTiles.values()) {
			for (Tile tile : node) {
				if (index == stop) 
					return removeTile(tile.getValue());
				index++;
			}
		}
		
		return null;
	}

	public Tile discardTile(int value) {
		return removeTile(value);
	}

	public int getTotalTileCount() {
		int count = getHiddenTileCount() + flowers.size();
		for (Meld meld : melds) {
			count += meld.getTileCount();
		}
		return count; 
	}
	
	public int getHiddenTileCount() {
		return hiddenTileCount;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Tiles ("+getHiddenTileCount()+"): ");
		for (TileNode node : hiddenTiles.values()) {
			for (Tile tile : node) {
				buffer.append("\n  "+tile);
			}
		}
		buffer.append("\nFlowerTiles ("+flowers.size()+"): "+flowers+"\nMelds: "+melds);
		
		return buffer.toString();
	}

	SortedMap<Integer, TileNode> getHiddenTiles() {
		return hiddenTiles;
	}

	public List<Meld> getMelds() {
		return melds;
	}
}
