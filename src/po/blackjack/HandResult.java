package po.blackjack;

/**
 * Possible results for a player hand
 * 
 * @author Patrick O'Reilly
 */
public enum HandResult {
	UNKNOWN("Unknown"),
	WIN("Win"),
	LOSE("Lose"),
	PUSH("Push");
	
    private final String label;

    /**
     * @param label
     */
    private HandResult(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}