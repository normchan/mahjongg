package normchan.mj.game.hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import normchan.mj.game.hand.Hand.TileNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WinEvaluator {
	private Log log = LogFactory.getLog(this.getClass());
	
	private SortedMap<Integer, TileNode> tiles;
	private List<TileGroup> groups = new ArrayList<TileGroup>();
	private boolean eyesFound = false;

	public WinEvaluator(SortedMap<Integer, TileNode> tiles) {
		super();
		this.tiles = tiles;
		init();
	}
	
	public boolean isWinningHand() {
		log.debug("Checking if current hand qualifies for win..."); 
		for (TileGroup group : groups) {
			if (group.tileCount < 2) {
				log.debug("Group has fewer than 2 tiles, not a win."); 
				return false;
			}
			
			if (group.tileCount == 2) {
				log.debug("Group has 2 tiles."); 
				if (eyesFound) {
					// Only one pair of eyes per winning hand
					log.debug("Hand already has a pair of eyes, not a win."); 
					return false;
				} else if (group.startValue != group.endValue) { 
					// Group has 2 tiles, but not a pair
					log.debug("Group has only two tiles and not a pair, not a win."); 
					return false;
				} else {
					log.debug("Group has a pair of eyes."); 
					eyesFound = true;
				}
			} else if (group.tileCount == 3) {
				log.debug("Group has 3 tiles."); 
				if (group.startValue != group.endValue && group.endValue != group.startValue + 2) {
					// Not a pong or chow
					log.debug("Neither a pong or a chow, not a win."); 
					return false;
				}
			} else {
				// Recursive call on tile group to determine if it has valid sets
				log.debug("Group has more than 3 tiles."); 
				if (!group.isValidForWin())
					return false;
				
				eyesFound = group.hasEyes;
			}
			
		}

		if (eyesFound) {
			log.debug("Found winning hand.");
		} else {
			log.debug("All groups are valid, but no eyes found.");
		}
		return eyesFound;
	}
	
	private class Counter {
		int count = 0;
		
		public Counter(int count) {
			this.count = count;
		}

		public int decrement(int val) {
			this.count -= val;
			return count;
		}
		
		public int getCount() {
			return count;
		}
	}

	private class TileGroup implements Comparable<TileGroup> {
		int startValue;
		int endValue;
		int tileCount = 0;
		boolean hasEyes = false;
		
		@Override
		public int compareTo(TileGroup g) {
			return tileCount - g.tileCount;
		}
		
		boolean isValidForWin() {
			Map<Integer, Counter> map = new HashMap<Integer, Counter>();
			
			for (int i = startValue; i <= endValue; i++) {
				map.put(i, new Counter(tiles.get(i).size()));
			}
			
			EvaluatorResult result = isValidForWin(startValue, endValue, map, eyesFound);
			this.hasEyes = result.hasEyes;
			return result.valid;
		}
		
		EvaluatorResult isValidForWin(int start, int end, Map<Integer, Counter> map, boolean eyesFound) {
			log.debug("Checking group validity. start: "+start+" end: "+end+" eyes found: "+eyesFound);
			EvaluatorResult result = new GroupEvaluator(start, end, map).removePongFirst(eyesFound);
			if (result.valid) {
				return result;
			}

			result = new GroupEvaluator(start, end, map).removeChowFirst(eyesFound);
			if (result.valid) {
				return result;
			}

			if (!eyesFound) {
				result = new GroupEvaluator(start, end, map).removeEyesFirst();
				if (result.valid) {
					return result;
				}
			}
			
			return new EvaluatorResult(false); 
		}
		
		Map<Integer, Counter> cloneMap(Map<Integer, Counter> input) {
			Map<Integer, Counter> map = new HashMap<Integer, Counter>();
			
			for (Integer i : input.keySet()) {
				map.put(i, new Counter(input.get(i).getCount()));
			}
			
			return map;
		}
		
		private class EvaluatorResult {
			final boolean valid;
			final boolean hasEyes;

			EvaluatorResult(boolean valid) {
				super();
				this.valid = valid;
				this.hasEyes = false;
			}

			EvaluatorResult(boolean valid, boolean hasEyes) {
				super();
				this.valid = valid;
				this.hasEyes = hasEyes;
			}
		}
	
		private class GroupEvaluator {
			int start;
			int end;
			Map<Integer, Counter> map;
			
			GroupEvaluator(int start, int end, Map<Integer, Counter> map) {
				this.start = start;
				this.end = end;
				this.map = cloneMap(map);
			}
			
			EvaluatorResult removePongFirst(boolean eyesFound) {
				log.trace("Removing pong with start "+start+" end "+end);
				Counter counter = map.get(start);
				if (counter.getCount() >= 3) {
					log.debug("Three or more of the first tile in the group.");
					if (counter.decrement(3) == 0) {
						map.remove(start);
						start++;
					}
					
					if (start > end) {
						// All tiles processed
						log.debug("Current subset of tiles are valid.");
						return new EvaluatorResult(true); 
					} else {
						// Recurse on remaining tiles and return result
						return isValidForWin(start, end, map, eyesFound);
					}
				} 
	
				log.trace("Returning false");
				return new EvaluatorResult(false); 
			}
			
			EvaluatorResult removeChowFirst(boolean eyesFound) {
				log.trace("Removing chow with start "+start+" end "+end);
				boolean hasEyes = false;
				Counter counter = map.get(start);
				if (end - start >= 2) {
					log.debug("Found sequence in the group.");
					int newStart = start;
//					log.trace("Newstart value: "+newStart);
					for (int i = start; i < start + 3; i++) {
						counter = map.get(i);
						if (counter.decrement(1) == 0) {
							log.debug("No tiles at value "+i);
							map.remove(i);
							if (newStart == i)
								newStart++;
//							log.trace("Newstart value: "+newStart);
						}
					}
					
					for (int i = newStart+1; i < start + 3; i++) {
						counter = map.get(i);
						if (counter == null) {
							if (newStart != -1) {
								// original group is now split into two due to gap in sequence
								EvaluatorResult result = isValidForWin(newStart, i-1, map, eyesFound);
								if (!result.valid)
									return result;
									
								hasEyes = result.hasEyes;
								newStart = -1;
							}
						} else {
							if (newStart == -1)
								newStart = i;
						}
					}
					
					if (newStart == -1) {
						return new EvaluatorResult(true, hasEyes);
					} else if (newStart > end) {
						// All tiles processed
						log.debug("Current subset of tiles are valid.");
						return new EvaluatorResult(true); 
					} else {
						// Recurse on remaining tiles and return result
						EvaluatorResult result = isValidForWin(newStart, end, map, eyesFound);
						return new EvaluatorResult(result.valid, result.hasEyes || hasEyes);
					}
				} 

				log.trace("Returning false");
				return new EvaluatorResult(false); 
			}
			
			EvaluatorResult removeEyesFirst() {
				log.trace("Removing eyes with start "+start+" end "+end);
				Counter counter = map.get(start);
				if (counter.getCount() == 2) {
					log.debug("Found pair of eyes.");
					if (counter.decrement(2) == 0) {
						map.remove(start);
						start++;
					}
					
					if (start > end) {
						// All tiles processed
						log.debug("Current subset of tiles are valid.");
						return new EvaluatorResult(true, true); 
					} else {
						// Recurse on remaining tiles and return result
						EvaluatorResult result = isValidForWin(start, end, map, true);
						if (result.valid)
							return new EvaluatorResult(true, true);
					}
				}

				log.trace("Returning false");
				return new EvaluatorResult(false); 
			}
		}
	}
	
	private void init() {
		log.debug("Starting Init...");
		TileGroup currentGroup = null;
		int previousKey = -1;
		for (Integer i : tiles.keySet()) {
			log.debug("Processing key "+i);
			if (i - previousKey != 1) {
				// Gap between this and previous key, so set start value of new tile group
				if (currentGroup != null) {
					log.debug("Setting end value of previous group (size "+currentGroup.tileCount+") to "+previousKey);
					currentGroup.endValue = previousKey;
				}
				log.debug("Creating new group starting at "+i);
				currentGroup = new TileGroup();
				groups.add(currentGroup);
				currentGroup.startValue = i;
			}
			currentGroup.tileCount += tiles.get(i).size();
			previousKey = i;
		}
		log.debug("Setting end value of final group (size "+currentGroup.tileCount+") to "+previousKey);
		currentGroup.endValue = previousKey;
		Collections.sort(groups);
	}
}
