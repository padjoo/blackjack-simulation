package po.blackjack;

/**
 * Class to represent a single card with suite and rank.
 * 
 * @author Patrick O'Reilly
 */
public class Card {
	protected CardSuit suite;
	protected int rank;
	
	public Card(CardSuit suite, int rank) {
		this.suite = suite;
		this.rank = rank;
	}
	
	/**
	 * Return the lowest value of the card i.e. 1 for aces
	 */
	public int getHardValue() {
		if (this.rank >= 10) {
			return 10;
		} else {
			return this.rank;
		}
	}
	
	/**
	 * Return the highest value of the card i.e. 11 for aces
	 */
	public int getSoftValue() {
		if (1 == this.rank) {
			return 11;
		} else {
			return this.getHardValue();
		}
	}
	
	/**
	 * Create two character string of card suit and rank
	 */
	public String toString() {
		String label;
		
		switch (this.rank) {
			case 1:
				label = "A";
				break;
				
			case 11:
				label = "J";
				break;
			
			case 12:
				label = "Q";
				break;
				
			case 13:
				label = "K";
				break;
				
			default:
				label = String.valueOf(this.rank); // String.format("%02d", this.rank);
				break;
		}

		return label + this.suite;
	}
}