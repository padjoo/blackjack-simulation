package po.blackjack;

/**
 * Card suits
 * 
 * @author Patrick O'Reilly
 */
public enum CardSuit {
	SPADES("S"),
	HEARTS("H"),
	DIAMONDS("D"),
	CLUBS("C");
	
    private final String label;

    /**
     * @param label
     */
    private CardSuit(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}