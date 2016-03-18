package po.blackjack;

import java.util.Collections;

/**
 * Shoe class that holds all the cards and keeps track of discarded cards.
 * 
 * @author Patrick O'Reilly
 */
public class Shoe {
	protected CardList cards;
	protected CardList discardedCards;
	protected Logger logger;
	
	public Shoe() {
		cards = new CardList();
		discardedCards = new CardList();
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	/**
	 * Create and add x decks to the shoe
	 * 
	 * @param noOfDecks
	 */
	public void addDecks(int noOfDecks) {
		for (int i = 0; i < noOfDecks; i++) {
			Deck deck = new Deck();
			
			/* for (Card card : deck.getCards()) {
				this.cards.add(card);
			} */
			
			this.cards.addAll(deck.getCards());
		}
	}
	
	/**
	 * Pull 1 card from the shoe
	 * 
	 * @return card
	 */
	public Card pullCard() {
		// If no cards left to pull, shuffle discarded pile and return to shoe
		if (cards.isEmpty()) {
			returnDiscardedCardsAndShuffle();
		}
		
		// Get the next card
		int index = cards.size() - 1;
		
		Card card = cards.get(index);
		
		// Remove it from the shoe
		cards.remove(index);
		
		return card;
	}
	
	/**
	 * Throw cards on the discard pile
	 * 
	 * @param cards
	 */
	public void discardCards(CardList cards) {
		discardedCards.addAll(cards);
	}
	
	public int getCardsLeftCount() {
		return cards.size();
	}
	
	public int getCardsDiscardedCount() {
		return discardedCards.size();
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
		
		if (null != logger) {
			logger.log("shoe_shuffled", "Cards re-shuffled");
		}
	}
	
	/**
	 * When the shoe is empty, this method returns all the discarded cards and re-shuffles them
	 */
	protected void returnDiscardedCardsAndShuffle() {
		// Add discarded cards back
		cards.addAll(discardedCards);
		// Clear discarded pile
		discardedCards.clear();
		// Re-shuffle
		shuffle();
	}
}
