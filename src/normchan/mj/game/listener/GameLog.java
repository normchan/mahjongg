package normchan.mj.game.listener;

import normchan.mj.game.Discard;
import normchan.mj.game.GameStateEvent;
import normchan.mj.game.GameStateListener;

public class GameLog implements GameStateListener {

	@Override
	public void gameStateChanged(GameStateEvent event) {
		switch (event.getType()) {
		case INITIAL_HAND:
			System.out.println(event.getPlayer().toString());
			break;
		case TILE_DRAWN:
			System.out.println(event.getPlayer().getDisplayName()+" draws "+event.getSource());
			break;
		case TILE_DISCARDED:
			System.out.println(event.getPlayer().getDisplayName()+" discards "+((Discard)event.getSource()).getTile()+"\n"+event.getPlayer());
			break;
		case FLOWER_DISPLAYED:
			System.out.println(event.getPlayer().getDisplayName()+" displays flower "+event.getSource());
		}
	}

}
