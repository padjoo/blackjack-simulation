package po.blackjack;

/**
 * Aggressive strategy - Stands on 18 or above.
 * 
 * @author Patrick O'Reilly
 */
public class AggressivePlayingStrategy implements PlayingStrategy {
	public Decision makeDecision(Hand hand) {
		int value = hand.getCardsSoftSum();
		
		if (value < 18) {
			return Decision.HIT;
		} else {
			return Decision.STAND;
		}
	}
	
	public String toString() {
		return "Aggressive";
	}
}