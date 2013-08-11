package normchan.mj.game;

import java.util.EventListener;

public interface GameStateListener extends EventListener {
	void gameStateChanged(GameStateEvent event);
}
