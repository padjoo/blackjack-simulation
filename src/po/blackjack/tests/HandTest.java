package po.blackjack.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import po.blackjack.*;

public class HandTest {

	@Test
	public void testHasBlackjack() {
		assertTrue("1,10 should be BJ", createHand(new int[] {1, 10}).hasBlackjack());
		assertTrue("1,11 should be BJ", createHand(new int[] {1, 11}).hasBlackjack());
		assertTrue("1,12 should be BJ", createHand(new int[] {1, 12}).hasBlackjack());
		
		assertTrue("1,13 should be BJ", createHand(new int[] {1, 13}).hasBlackjack());
		assertTrue("10,1 should be BJ", createHand(new int[] {10, 1}).hasBlackjack());
		assertTrue("11,1 should be BJ", createHand(new int[] {11, 1}).hasBlackjack());
		assertTrue("12,1 should be BJ", createHand(new int[] {12, 1}).hasBlackjack());
		assertTrue("13,1 should be BJ", createHand(new int[] {13, 1}).hasBlackjack());
		
		assertFalse("1,1 should NOT be BJ", createHand(new int[] {1, 1}).hasBlackjack());
		assertFalse("1,9 should NOT be BJ", createHand(new int[] {1, 9}).hasBlackjack());
		assertFalse("9,1 should NOT be BJ", createHand(new int[] {9, 1}).hasBlackjack());
		assertFalse("10,10 should NOT be BJ", createHand(new int[] {10, 10}).hasBlackjack());
	}

	@Test
	public void testCompareHands() {
		HandResult result;
		
		// Player wins
		result = compareHandWithDealer(
			new int[] {1, 11}, // Player
			new int[] {10, 5, 7} // Dealer
		);
		assertEquals("BJ should beat bust", HandResult.WIN, result);
		
		result = compareHandWithDealer(
			new int[] {1, 11}, // Player
			new int[] {10, 5, 6} // Dealer
		);
		assertEquals("BJ should beat 21", HandResult.WIN, result);
		
		result = compareHandWithDealer(
			new int[] {10, 8}, // Player
			new int[] {10, 7} // Dealer
		);
		assertEquals("18 should beat 17", HandResult.WIN, result);
		
		result = compareHandWithDealer(
			new int[] {1, 8}, // Player
			new int[] {10, 8} // Dealer
		);
		assertEquals("S19 should beat 18", HandResult.WIN, result);
		
		result = compareHandWithDealer(
			new int[] {1, 5, 4}, // Player
			new int[] {5, 5, 5, 4} // Dealer
		);
		assertEquals("S20 should beat 19", HandResult.WIN, result);
		
		result = compareHandWithDealer(
			new int[] {2, 2}, // Player
			new int[] {5, 5, 5, 7} // Dealer
		);
		assertEquals("4 should beat bust", HandResult.WIN, result);
		
		// Dealer wins
		result = compareHandWithDealer(
			new int[] {10, 5, 7}, // Player
			new int[] {1, 11} // Dealer
		);
		assertEquals("BJ should beat bust", HandResult.LOSE, result);
		
		result = compareHandWithDealer(
			new int[] {10, 5, 6}, // Player
			new int[] {1, 11} // Dealer
		);
		assertEquals("BJ should beat 21", HandResult.LOSE, result);
		
		result = compareHandWithDealer(
			new int[] {10, 7}, // Player
			new int[] {10, 8} // Dealer
		);
		assertEquals("18 should beat 17", HandResult.LOSE, result);
		
		result = compareHandWithDealer(
			new int[] {10, 8}, // Player
			new int[] {1, 8} // Dealer
		);
		assertEquals("S19 should beat 18", HandResult.LOSE, result);
		
		result = compareHandWithDealer(
			new int[] {5, 5, 5, 4}, // Player
			new int[] {1, 5, 4} // Dealer
		);
		assertEquals("S20 should beat 19", HandResult.LOSE, result);
		
		result = compareHandWithDealer(
			new int[] {5, 5, 5, 7}, // Player
			new int[] {5, 5, 5, 7} // Dealer
		);
		assertEquals("Dealer bust should beat player bust", HandResult.LOSE, result);
		
		result = compareHandWithDealer(
			new int[] {5, 5, 5, 7}, // Player
			new int[] {2, 2} // Dealer
		);
		assertEquals("Dealer 2 should beat player bust", HandResult.LOSE, result);
		
		// Push
		result = compareHandWithDealer(
			new int[] {10, 5, 4}, // Player
			new int[] {10, 9} // Dealer
		);
		assertEquals("19 v 19 should push", HandResult.PUSH, result);
		
		result = compareHandWithDealer(
			new int[] {10, 1}, // Player
			new int[] {1, 11} // Dealer
		);
		assertEquals("BJ vs BJ should push", HandResult.PUSH, result);
		
		result = compareHandWithDealer(
			new int[] {1, 6}, // Player
			new int[] {10, 7} // Dealer
		);
		assertEquals("S17 v H17 should push", HandResult.PUSH, result);
	}
	
	private HandResult compareHandWithDealer(int[] playerCardRanks, int[] dealerCardRanks) {
		Hand playerHand = createHand(playerCardRanks, 0);
		Hand dealerHand = createHand(dealerCardRanks, 0);
		
		return playerHand.compareWithDealerHand(dealerHand);
	}
	
	/**
	 * Create a list of cards using ranks (suit is irrelavant for BJ)
	 * @param cardRanks
	 * @return card list
	 */
	private CardList createCardsFromRanksArray(int[] cardRanks) {
		CardList cards = new CardList();
		
		for (int rank : cardRanks) {
			cards.add(new Card(CardSuit.HEARTS, rank));
		}
		
		return cards;
	}
	
	/**
	 * Create hand from ranks array and chips count 
	 */
	private Hand createHand(int[] cardRanks, int chips) {
		Hand hand = new Hand(chips);
		
		for (Card card : createCardsFromRanksArray(cardRanks)) {
			hand.addCard(card);
		}
		
		return hand;
	}
	
	private Hand createHand(int[] cardRanks) {
		return createHand(cardRanks, 0);
	}
}
