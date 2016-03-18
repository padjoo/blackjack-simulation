package po.blackjack;

/**
 * Class representing a card deck with 52 cards
 * 
 * @author Patrick O'Reilly
 */
public class Deck {
	protected CardList cards; 

	/**
	 * Constructor creates a full deck of cards
	 */
	public Deck() {
		cards = new CardList();
		
		// Loop through each suit
		for (CardSuit suit : CardSuit.values()) {
			// Loop through each card rank
			for (int i = 1; i <= 13; i++) {
				// Create card
				cards.add(new Card(suit, i));
			}
		}
	}
	
	public CardList getCards() {
		return cards;
	}
}
