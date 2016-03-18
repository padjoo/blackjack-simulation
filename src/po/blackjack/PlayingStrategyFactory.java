package po.blackjack;

/**
 * Conservative strategy - Stands on 10 or above
 * 
 * @author Patrick O'Reilly
 */
public class PlayingStrategyFactory {
	public static PlayingStrategy factory(String strategy) throws IllegalArgumentException {
		switch (strategy) {
			case "a":
				return new AggressivePlayingStrategy();
				
			case "c":
				return new ConservativePlayingStrategy();
				
			default:
				throw new IllegalArgumentException("Strategy can only be 'c' or 'a'");
		}
	}
}