package po.blackjack;

/**
 * Player decisions. Only incorporated HIT and STAND
 * 
 * @author Patrick O'Reilly
 */
public enum Decision {
	HIT("H"),
	STAND("S");
	
    private final String label;

    /**
     * @param label
     */
    private Decision(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}