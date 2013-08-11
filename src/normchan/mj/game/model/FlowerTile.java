package normchan.mj.game.model;

import java.util.Arrays;
import java.util.List;


public class FlowerTile extends Tile {
	private static final int FLOWER_START_VALUE = 0;
	private static final int TYPE_MULTIPLIER = 100;
	private static final List<Type> TYPE_ORDER = Arrays.asList(Type.values());

	public enum Type { PLANT, SEASON };
	private final Type type;
	private final int ordinal;
	
	public FlowerTile(Type type, int ordinal) {
		super();
		this.type = type;
		this.ordinal = ordinal;
	}

	public Type getType() {
		return type;
	}

	public int getOrdinal() {
		return ordinal;
	}
	
	public int getValue() {
		return FLOWER_START_VALUE + TYPE_ORDER.indexOf(type) * TYPE_MULTIPLIER + ordinal;
	}
	
	public String toString() {
		return type+" "+ordinal+" ("+getValue()+")";
	}
}
