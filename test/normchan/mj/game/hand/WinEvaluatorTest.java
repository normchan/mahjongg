package normchan.mj.game.hand;

import normchan.mj.game.hand.Hand;
import normchan.mj.game.hand.WinEvaluator;
import normchan.mj.game.model.Dragon;
import normchan.mj.game.model.DragonTile;
import normchan.mj.game.model.SimpleTile;
import normchan.mj.game.model.Suit;

import org.junit.Assert;
import org.junit.Test;

public class WinEvaluatorTest {

	@Test
	public void testWinningHand1() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.BAMBOO, 5));

		for (int i = 0; i < 2; i++) 
			hand.addTile(new SimpleTile(Suit.CHARACTER, 2));

		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 5));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testWinningHand2() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.BAMBOO, 5));

		for (int i = 0; i < 2; i++) 
			hand.addTile(new SimpleTile(Suit.CHARACTER, 7));

		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 5));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testWinningHand3() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.BAMBOO, 5));

		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.CHARACTER, 7));

		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 5));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testWinningHand4() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.BAMBOO, 5));

		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 5));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testWinningHand5() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.BAMBOO, 5));

		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 5));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testWinningHand6() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.BAMBOO, 5));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testWinningHand7() {
		Hand hand = new Hand();
		
		hand.addTile(new SimpleTile(Suit.CHARACTER, 2));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 3));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 3));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testWinningHand8() {
		Hand hand = new Hand();
		
		hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 2));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 3));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 4));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 4));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 5));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 6));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 7));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 7));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 7));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 8));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 9));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testWinningHand9() {
		Hand hand = new Hand();
		
		hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 2));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 3));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 4));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 4));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 5));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 5));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 5));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 6));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 7));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 8));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 9));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testNonWinningHand1() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.BAMBOO, 5));

		for (int i = 0; i < 2; i++) 
			hand.addTile(new SimpleTile(Suit.CHARACTER, 2));

		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 9));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));

		Assert.assertFalse(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testNonWinningHand2() {
		Hand hand = new Hand();
		
		hand.addTile(new SimpleTile(Suit.CHARACTER, 1));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 2));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 3));

		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.BAMBOO, 5));

		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 9));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));

		Assert.assertFalse(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testNonWinningHand3() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		for (int i = 0; i < 3; i++) 
			hand.addTile(new SimpleTile(Suit.BAMBOO, 5));

		for (int i = 0; i < 2; i++) 
			hand.addTile(new SimpleTile(Suit.CHARACTER, 7));

		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 5));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));

		Assert.assertFalse(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testNonWinningHand4() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 5));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 9));

		Assert.assertFalse(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testNonWinningHand5() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 5));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 9));

		Assert.assertFalse(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}


	@Test
	public void testNonWinningHand6() {
		Hand hand = new Hand();
		
		for (int i = 0; i < 3; i++)
			hand.addTile(new DragonTile(Dragon.WHITE));
		
		hand.addTile(new SimpleTile(Suit.CIRCLE, 6));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 5));
		hand.addTile(new SimpleTile(Suit.CIRCLE, 7));

		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));

		Assert.assertFalse(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testNonWinningHand7() {
		Hand hand = new Hand();
		
		hand.addTile(new SimpleTile(Suit.CHARACTER, 2));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 3));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 3));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));

		Assert.assertFalse(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testNonWinningHand8() {
		Hand hand = new Hand();
		
		hand.addTile(new SimpleTile(Suit.CHARACTER, 2));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 3));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 3));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 4));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 5));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 6));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 7));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));
		hand.addTile(new SimpleTile(Suit.CHARACTER, 8));

		Assert.assertFalse(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}

	@Test
	public void testNonWinningHand9() {
		Hand hand = new Hand();
		
		hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 1));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 2));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 3));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 4));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 4));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 5));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 5));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 6));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 7));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 7));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 8));
		hand.addTile(new SimpleTile(Suit.BAMBOO, 9));

		Assert.assertTrue(new WinEvaluator(hand.getHiddenTiles()).isWinningHand());
	}
}
