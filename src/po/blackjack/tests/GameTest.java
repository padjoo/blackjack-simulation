package po.blackjack.tests;

import po.blackjack.*;
import po.blackjack.tests.mock.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Game tests
 * 
 * @author Patrick O'Reilly
 */
public class GameTest implements Logger {
	@Test
	public void testRun() {
		Gambler player;
		Dealer dealer;
		GamblerList players;
		
		// Inputs
		
		// Use mock show to remove randomness
		MockShoe shoe = new MockShoe();
		
		// P1=BJ, P2=Bust, D=21		
		shoe.setCards(new int[] {
		// P1, P2, D
		   1,  13, 10, 
		   11,  2,  6, 
		       10, 10,
		       
		   1,   5, 10, 
		   9,   5, 10, 
		        9,
		       
	       5,      10, 
		   2,       4, 
		   3,
		   1,       3,
		});
		
		// Create game
		Game game = new Game();
		
		// Send logs to this class for debugging
		game.setLogger(this);
		
		dealer = game.getDealer();
		
		players = new GamblerList();
		players.add(new Gambler("Player 1", 100, 124, 10, new ConservativePlayingStrategy()));
		players.add(new Gambler("Player 2", 30, 100, 20, new AggressivePlayingStrategy()));
		
		// Players
		for (Gambler p : players) {
			game.addPlayer(p);
		}

		// Set shoe
		game.setShoe(shoe);
		
		// Round 1
		game.play(1);
		
		player = players.get(0);
		assertEquals("Player 1 should have 21", 21, player.getHand().getCardsSoftSum());
		assertTrue("Player 1 should have BJ", player.getHand().hasBlackjack());
		assertEquals("Player 1 should win", HandResult.WIN, player.getHand().getResult());
		assertEquals("Player 1 should have 15 more chips", 115, player.getChips());
		assertFalse("Player 1 should not be finished", player.finishedPlaying());
		
		player = players.get(1);
		assertEquals("Player 2 should have 22", 22, player.getHand().getCardsSoftSum());
		assertTrue("Player 2 should be bust", player.getHand().isBust());
		assertEquals("Player 2 should lose", HandResult.LOSE, player.getHand().getResult());
		assertEquals("Player 2 should have 20 chips less", 10, player.getChips());
		assertFalse("Player 2 should not be finished", player.finishedPlaying());
		
		assertEquals("Dealer should have 26", 26, dealer.getHand().getCardsSoftSum());
		assertTrue("Dealer should be bust", dealer.getHand().isBust());
		
		// Round 2
		game.play(1);
		
		player = players.get(0);
		assertEquals("Player 1 should have 20", 20, player.getHand().getCardsSoftSum());
		assertEquals("Player 1 should push", HandResult.PUSH, player.getHand().getResult());
		assertEquals("Player 1 should have same amount of chips", 115, player.getChips());
		assertFalse("Player 1 should not be finished", player.finishedPlaying());
		
		player = players.get(1);
		assertEquals("Player 2 should have 19", 19, player.getHand().getCardsSoftSum());
		assertEquals("Player 2 should lose", HandResult.LOSE, player.getHand().getResult());
		assertEquals("Player 2 bet should only be 10", 10, player.getHand().getBet());
		assertEquals("Player 2 should have 10 chips less", 0, player.getChips());
		assertTrue("Player 2 should be finished", player.finishedPlaying());
		
		assertEquals("Dealer should have 20", 20, dealer.getHand().getCardsSoftSum());

		// Round 3
		game.play(1);
		
		player = players.get(0);
		assertEquals("Player 1 should have 21", 21, player.getHand().getCardsSoftSum());
		assertFalse("Player 1 should not have BJ", player.getHand().hasBlackjack());
		assertEquals("Player 1 should win", HandResult.WIN, player.getHand().getResult());
		assertEquals("Player 1 should have 10 more chips", 125, player.getChips());
		assertTrue("Player 1 should be finished", player.finishedPlaying());
		
		player = players.get(1);
		assertNull("Player 2 should not have a hand", player.getHand());
		assertTrue("Player 2 should be finished", player.finishedPlaying());
		
		assertEquals("Dealer should have 17", 17, dealer.getHand().getCardsSoftSum());
		
		// Round 4
		game.play(1);
		
		player = players.get(0);
		assertTrue("Player 1 should be finished", player.finishedPlaying());

		player = players.get(1);
		assertNull("Player 2 should not have a hand", player.getHand());
		assertTrue("Player 2 should be finished", player.finishedPlaying());
	}
	
	public void log(String event, String message) {
		//System.out.println(message);
	}
}