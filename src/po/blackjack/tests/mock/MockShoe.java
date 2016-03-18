package po.blackjack.tests.mock;

import po.blackjack.*;

/**
 * Mock shoe for testing so that the cards can be manually set instead of making them random
 * 
 * @author Patrick O'Reilly
 */
public class MockShoe extends Shoe {
	@Override
	public void shuffle() {
		// Don't shuffle
	}
	
	public void setCards(int[] cardRanks) {
		cards.clear();
		cards.addAll(createCardsFromRanksArray(cardRanks));
		
		//System.out.println(cards);
	}
	
	/**
	 * Create a list of cards using ranks (suit is irrelavant for BJ)
	 * @param cardRanks
	 * @return card list
	 */
	private CardList createCardsFromRanksArray(int[] cardRanks) {
		CardList cards = new CardList();
		
		// Do in reverse order since shoe pulls from the end of the list
		for (int i = cardRanks.length - 1; i >= 0; i--) {
			cards.add(new Card(CardSuit.HEARTS, cardRanks[i]));
		}
		
		/* for (int rank : cardRanks) {
			cards.add(new Card(CardSuit.HEARTS, rank));
		} */
		
		return cards;
	}
}