package po.blackjack;

/**
 * Conservative strategy - Stands on 11 or above
 * 
 * @author Patrick O'Reilly
 */
public class ConservativePlayingStrategy implements PlayingStrategy {
	public Decision makeDecision(Hand hand) {
		int value = hand.getCardsSoftSum();
		
		if (value < 11) {
			return Decision.HIT;
		} else {
			return Decision.STAND;
		}
	}
	
	public String toString() {
		return "Conservative";
	}
}