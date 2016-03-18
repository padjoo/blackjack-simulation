package po.blackjack;

/**
 * Playing strategy interface
 * 
 * @author Patrick O'Reilly
 */
public interface PlayingStrategy {
	/**
	 * Returns the player decision using the details of the hand supplied
	 * 
	 * @param hand
	 * @return decision
	 */
	public Decision makeDecision(Hand hand);
}