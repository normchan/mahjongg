package normchan.mj.game.model;

import java.util.Arrays;
import java.util.List;


public class SimpleTile extends Tile {
	private static final int SIMPLE_START_VALUE = 3000;
	private static final int SUIT_MULTIPLIER = 100;
	private static final List<Suit> SUIT_ORDER = Arrays.asList(Suit.values());

	private final Suit suit;
	private final int ordinal;
	
	public SimpleTile(Suit suit, int ordinal) {
		super();
		this.suit = suit;
		this.ordinal = ordinal;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getOrdinal() {
		return ordinal;
	}
	
	public int getValue() {
		return SIMPLE_START_VALUE + SUIT_ORDER.indexOf(suit) * SUIT_MULTIPLIER + ordinal;
	}
	
	public String toString() {
		return suit+" "+ordinal+" ("+getValue()+")";
	}
}
