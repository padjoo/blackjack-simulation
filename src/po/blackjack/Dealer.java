package po.blackjack;

/**
 * Dealer class.
 * 
 * @author Patrick O'Reilly
 */
public class Dealer extends Player {
	public Dealer() {
		super("Dealer", new DealerPlayingStrategy());
	}
	
	public void resetHand() {
		super.resetHand(0);
	}
	
	public String getInfo() {
		String info = String.format("%s: %s", name, (null == hand) ? "No hand" : hand.getInfo(false));
		
		return info;
	}
}