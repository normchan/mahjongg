package normchan.mj.game.model;

import java.util.Arrays;
import java.util.List;


public class DragonTile extends HonorTile {
	private static final int DRAGON_START_VALUE = 1000;
	private static final int DRAGON_MULTIPLIER = 100;
	private static final List<Dragon> DRAGON_ORDER = Arrays.asList(Dragon.values());

	private final Dragon dragon;

	public DragonTile(Dragon dragon) {
		super();
		this.dragon = dragon;
	}

	public Dragon getDragon() {
		return dragon;
	}
	
	public int getValue() {
		return DRAGON_START_VALUE + DRAGON_ORDER.indexOf(dragon) * DRAGON_MULTIPLIER;
	}
	
	public String toString() {
		return dragon.toString()+" ("+getValue()+")";
	}
}
