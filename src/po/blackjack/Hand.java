package po.blackjack;

/**
 * Player hand class containing the cards and chips that were bet
 * 
 * @author Patrick O'Reilly
 */
public class Hand {
	protected CardList cards;
	protected int chips;
	protected HandResult result;
	
	public Hand(int chips) {
		cards = new CardList();
		
		this.reset(chips);
	}
	
	public Hand() {
		this(0);
	}

	public void reset(int chips) {
		cards.clear();
		result = HandResult.UNKNOWN;
		
		this.chips = chips;
	}
	
	/**
	 * Return the lowest sum of the hand
	 */
	public int getCardsHardSum() {
		int sum = 0;
		
		for (Card card : this.cards) {
			sum += card.getHardValue();
		}
		
		return sum;
	}
	
	/**
	 * Return the highest sum of the hand (returns hard sum if soft hand means bust)
	 */
	public int getCardsSoftSum() {
		int sum = 0;
		
		for (Card card : this.cards) {
			sum += card.getSoftValue();
		}
		
		// If soft value means bust, return hard sum instead
		if (sum > 21) {
			return getCardsHardSum();
		} else {
			return sum;
		}
	}
	
	public void placeBet(int chips) {
		this.chips = chips;
	}
	
	public int getBet() {
		return this.chips;
	}
	
	public boolean isBust() {
		return this.getCardsSoftSum() > 21;
	}

	public boolean hasBlackjack() {
		if (2 == cards.size()) {
			Card card1 = cards.get(0);
			Card card2 = cards.get(1);
			
			return
				// Ace and 10,J,Q,K
				(1 == card1.getHardValue() && 10 == card2.getHardValue()) ||
				(1 == card2.getHardValue() && 10 == card1.getHardValue())
			;
		} else {
			return false;
		}
	}

	/**
	 * Compares hand with dealer hand and returns the result - Win, Lose, Push
	 * 
	 * @param dealerHand
	 * @return result
	 */
	public HandResult compareWithDealerHand(Hand dealerHand) {
		// If player is bust he loses either way
		if (isBust()) {
			result = HandResult.LOSE;
			
		// If player isn't bust and dealer is player wins
		} else if (dealerHand.isBust()) {
			result = HandResult.WIN;
			
		// If player has black jack he wins unless dealer has black jack too in case it's a push
		} else if (hasBlackjack()) {
			result = dealerHand.hasBlackjack() ? HandResult.PUSH : HandResult.WIN;
			
		// If dealer has BJ and player doesn't he loses
		} else if (dealerHand.hasBlackjack()) {
			result = HandResult.LOSE;
			
		// Otherwise, compare the soft (highest) sum
		} else {
			int playerSum = getCardsSoftSum();
			int dealerSum = dealerHand.getCardsSoftSum();
			
			// Player win's if higher value than dealer
			if (playerSum > dealerSum) {
				result = HandResult.WIN;
				
			// Player loses if lower value than dealer
			} else if (playerSum < dealerSum) {
				result = HandResult.LOSE;
				
			// Otherwise it's a push
			} else {
				result = HandResult.PUSH;
			}
		}
		
		return result;
	}

	public void addCard(Card card) {
		cards.add(card);
	}
	
	public CardList getCards() {
		return cards;
	}
	
	public String getInfo(boolean isPlayer) {
		String cardList = "";
		for (Card card : cards) {
			cardList += card.toString();
			cardList += ',';
		}
		
		String format = "Cards: %s Sum: %d%s";
		if  (isPlayer) {
			format += ", Bet: %d, Result: %s";
		}
		
		String status = "";
		if (isBust()) {
			status = " (Bust)";
		} else if (hasBlackjack()) {
			status = " (Blackjack!)";
		}
		
		String info = String.format(format, cardList, getCardsSoftSum(), status, chips, result);
		
		return info;
	}
	
	public String getInfo() {
		return getInfo(true);
	}
	
	public HandResult getResult() {
		return result;
	}
}