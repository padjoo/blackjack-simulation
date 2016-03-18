package po.blackjack;

/**
 * Gambler class (for lack of a better name)
 * 
 * @author Patrick O'Reilly
 */
public class Gambler extends Player {
	protected int chips;
	protected int chipTarget;
	protected int bet;
	
	public Gambler(String name, int chips, int chipTarget, int bet, PlayingStrategy playingStrategy) {
		super(name, playingStrategy);
		
		this.chips = chips;
		this.chipTarget = chipTarget;
		this.bet = bet;
	}
	
	/**
	 * Finished playing if no more chips or has met target
	 * 
	 * @return finished
	 */
	public boolean finishedPlaying(boolean clearHandIfFinished) {
		boolean finished = (this.chips <= 0) || (this.chips >= this.chipTarget);
		
		if (clearHandIfFinished && finished) {
			hand = null;
		}
		
		return finished;
	}
	
	public boolean finishedPlaying() {
		return finishedPlaying(false);
	}
	
	public void resetHand() {
		super.resetHand(getBet());
	}
	
	/**
	 * Get bet for next hand. Makes sure player doesn't bet more than they have
	 * 
	 * @return bet
	 */
	public int getBet() {
		return (this.chips >= this.bet) ? this.bet : this.chips;
	}
	
	public int getChips() {
		return chips;
	}
	
	/**
	 * Deduct bet from player
	 * 
	 * @return bet
	 */
	public int deductBet() {
		int bet = hand.getBet();
		
		// Deduct from chip count and return bet
		this.chips -= bet;
		
		return bet;
	}
	
	/**
	 * Collect win chips
	 * 
	 * @return bet
	 */
	public void collectWinnings(int chips) {
		// Add winnigs
		this.chips += chips;
	}
	
	public String getInitialInfo() {
		String info = String.format("%s: Initial Chips: %d, Target: %s, Strategy: %s", name, chips, chipTarget, playingStrategy);
		
		return info;
	}
	
	public String getInfo() {
		String info = String.format("%s: %s, Chips: %d, Target: %s, Finished: %s", name, (null == hand) ? "No Hand" : hand.getInfo(), chips, chipTarget,
			finishedPlaying() ? "Yes" : "No");
		
		return info;
	}
}