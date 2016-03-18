package po.blackjack;

/**
 * Base class for Dealer and Gambler.
 * 
 * @author Patrick O'Reilly
 */
public class Player {
	protected String name;
	protected PlayingStrategy playingStrategy;
	protected Hand hand;
	
	public Player(String name, PlayingStrategy playingStrategy) {
		this.name = name;
		this.playingStrategy = playingStrategy;
	}
	
	/**
	 * Reset player hand.
	 */
	public void resetHand(int chips) {
		if (null == hand) {
			hand = new Hand(chips);	
		} else {
			hand.reset(chips);
		}
	}
	
	public void resetHand() {
		resetHand(0);
	}
	
	/**
	 * Reset player hand.
	 * 
	 * @todo re-use the same object instead of creating new one each time
	 */
	public void addCard(Card card) {
		hand.addCard(card);
	}
	
	public Hand getHand() {
		return hand;
	}
	
	/**
	 * Decide on the next action for player
	 * 
	 * @return decision
	 */
	public Decision nextDecision() {
		return playingStrategy.makeDecision(hand);
	}
	
	public String toString() {
		return name;
	}
}