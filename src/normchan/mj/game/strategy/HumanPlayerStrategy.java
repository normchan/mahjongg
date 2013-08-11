package normchan.mj.game.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import normchan.mj.game.Discard;
import normchan.mj.game.GameState.ClaimType;
import normchan.mj.game.Player;
import normchan.mj.game.model.Meld;
import normchan.mj.game.model.Tile;

public class HumanPlayerStrategy extends AbstractPlayerStrategy {
	public HumanPlayerStrategy(Player player) {
		super(player);
	}

	@Override
	public ClaimType discardNotification(Discard discard) {
		if (discard.getPlayer() == player)
			return null;
		
		System.out.println(discard.getPlayer().getDisplayName()+" discards "+discard.getTile()+".  "+player+"Make claim? [N, W, K, P, C]: ");
		try {
			int response = System.in.read();
			switch (response) {
			case 'W':
				return ClaimType.WIN;
			case 'K':
				return ClaimType.KONG;
			case 'P':
				return ClaimType.PONG;
			case 'C':
				return ClaimType.CHOW;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tile chooseDiscard() {
		return player.getHand().discardTile(retrieveIntValue(player+"Enter value of tile to discard: "));
	}

	@Override
	public boolean winPossible() {
		String response = retrieveValue(player+"Do you wish to declare a winning hand? [Y/N]:");
		if (response.equals("Y")) {
			player.declareWin();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean kongPossible(Tile tile) {
		String response = retrieveValue(player+"Do you wish to declare a kong of "+tile+"? [Y/N]:");
		if (response.equals("Y")) {
			player.declareKong(tile);
			return true;
		}
		
		return false;
	}

	@Override
	public Meld acceptDiscard(ClaimType type, Tile discard) {
		List<Integer> hiddenTiles = new ArrayList<Integer>();
		if (type == ClaimType.CHOW) {
			hiddenTiles.add(retrieveIntValue(player+"Enter value of first tile in meld: "));
			hiddenTiles.add(retrieveIntValue(player+"Enter value of second tile in meld: "));
			return player.getHand().addChow(hiddenTiles, discard);
		} else {
			return player.getHand().addMeld(type, discard);
		}
	}
	
	private int retrieveIntValue(String message) {
		return new Integer(retrieveValue(message)).intValue();
	}
	
	private String retrieveValue(String message) {
		System.out.println(message);
		try {
			StringBuffer buffer = new StringBuffer();
			int response = 0;
			while (response != '\n') {
				response = System.in.read();
				if (response != '\n')
					buffer.append((char)response);
			}
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
