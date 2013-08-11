package normchan.mj.game;

import java.util.LinkedList;

import normchan.mj.game.listener.GameLog;
import normchan.mj.game.model.Wind;
import normchan.mj.game.strategy.HumanPlayerStrategy;
import normchan.mj.game.strategy.PlayerStrategy;
import normchan.mj.game.strategy.SimpleAutoPlayerStrategy;

public class MJGame {
	private LinkedList<Player> players = new LinkedList<Player>();
	private GameState state = new GameState();
	
	private void init() {
		Player next = null;
		for (Wind wind : Wind.values()) {
			players.addFirst(new Player(state));
			state.addListener(players.getFirst());
			PlayerStrategy strategy = null;
//			if (wind == Wind.SOUTH)
				strategy = new HumanPlayerStrategy(players.getFirst());
//			else 
//				strategy = new SimpleAutoPlayerStrategy(players.getFirst());
			players.getFirst().setStrategy(strategy);
			players.getFirst().setNextPlayer(next);
			next = players.getFirst();
		}
		players.getLast().setNextPlayer(players.getFirst());

		state.init();
		state.addListener(new GameLog());
	}
	
	public void play() {
		init();
		while (!anyPlayerBankrupt()) {
			startRound();
			while (!state.roundComplete()) {
				try {
				state.handleDiscard(new Discard(state.getCurrentPlayer(), state.getCurrentPlayer().discard()));
				if (state.getCurrentClaim() != null) {
					state.getCurrentClaim().process();
				} else {
					state.setCurrentPlayer(state.getCurrentPlayer().getNextPlayer());
					state.getCurrentPlayer().drawTile();
				}
				state.getCurrentPlayer().checkHand();
				} catch (OutOfTilesException e) {
					System.out.println("Ran out of tiles!");
				}
			}
			settlePayouts();
//			System.exit(0);
		}
	}
	
	private boolean anyPlayerBankrupt() {
		for (Player player : players) {
			if (player.isBankrupt())
				return true;
		}
		return false;
	}
	
	private void startRound() {
		state.startRound(players);
		state.shuffleTiles();
		state.getDealer().drawInitialHand();
		state.getDealer().replaceInitialFlowers();
		Player player = state.getDealer();
		for (Wind wind : Wind.values()) {
			state.gameStateUpdate(new GameStateEvent(GameStateEvent.Type.INITIAL_HAND, player, player.getHand()));
			player = player.getNextPlayer();
		}
		state.getDealer().checkHand();
	}
	
	private void settlePayouts() {
		int payout = 1;
		for (Player player : players) {
			if (player != state.getWinner()) {
				player.payLoss(payout);
			}
		}
		state.getWinner().collectWin(payout * 3);
	}
	
	public static void main(String[] args) {
		MJGame game = new MJGame();
		game.play();
	}
}
