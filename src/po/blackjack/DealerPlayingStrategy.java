package po.blackjack;

/**
 * Dealer strategy class - hits on anything under 17 (soft) and stands on 17 (soft) or above
 * 
 * @author Patrick O'Reilly
 */
public class DealerPlayingStrategy implements PlayingStrategy {
	public Decision makeDecision(Hand hand) {
		int value = hand.getCardsSoftSum();
		
		if (value < 17) {
			return Decision.HIT;
		} else {
			return Decision.STAND;
		}
	}
	
	public String toString() {
		return "S17";
	}
}