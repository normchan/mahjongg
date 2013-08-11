package normchan.mj.game.model;

import java.util.Arrays;
import java.util.List;


public class WindTile extends HonorTile {
	private static final int WIND_START_VALUE = 2000;
	private static final int WIND_MULTIPLIER = 100;
	private static final List<Wind> WIND_ORDER = Arrays.asList(Wind.values());

	private final Wind wind;

	public WindTile(Wind wind) {
		super();
		this.wind = wind;
	}

	public Wind getWind() {
		return wind;
	}
	
	public int getValue() {
		return WIND_START_VALUE + WIND_ORDER.indexOf(wind) * WIND_MULTIPLIER;
	}
	
	public String toString() {
		return wind.toString()+" ("+getValue()+")";
	}
}
