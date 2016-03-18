package po.blackjack;

/**
 * The main game class responsible for running the simulation.
 * 
 * @author Patrick O'Reilly
 * @see https://en.wikipedia.org/wiki/Blackjack#Rules_of_play_at_casinos
 */
public class Game {
	protected GamblerList players;
	protected Dealer dealer;
	protected Shoe shoe;
	protected int chips;
	protected Logger logger;
	
	public Game() {
		this.players = new GamblerList();
		
		// Table +/-
		this.chips = 0;
		
		// Set dealer
		this.dealer = new Dealer();
	}
	
	public void addPlayer(Gambler player) {
		this.players.add(player);
	}

	public void addDecksToShoeAndShuffle(int noOfDecks) {
		if (null == shoe) {
			setShoe(new Shoe());
		}
		
		shoe.addDecks(noOfDecks);
		shoe.shuffle();
	}
	
	public void setShoe(Shoe shoe) {
		this.shoe = shoe;
		
		// Pass along logger
		shoe.setLogger(logger);
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;

		// Pass along logger
		if (null != shoe) {
			shoe.setLogger(logger);
		}
	}
		
	public Dealer getDealer() {
		return dealer;
	}
	
	public void play(int maxRounds) {
		logger.log("play_start", String.format("Game starting with %d players, playing a max of %d rounds", players.size(), maxRounds));
		
		// Display initial player info
		for (Gambler player : players) {
			logger.log("player",  player.getInitialInfo());
		}
		
		int round = 1;
		while (round <= maxRounds && playersStillPlaying()) {
			logger.log("round_start", String.format("Round %d: Cards left: %d, Cards discarded: %d, Table +/-: %d", round,
				shoe.getCardsLeftCount(), shoe.getCardsDiscardedCount(), chips));
			
			// Reset hands and add first card
			for (Gambler player : players) {
				if (!player.finishedPlaying(true)) {
					player.resetHand();
					player.addCard(shoe.pullCard());
				}
			}
			dealer.resetHand();
			dealer.addCard(shoe.pullCard());
			
			// Second card
			for (Gambler player : players) {
				if (!player.finishedPlaying()) {
					player.addCard(shoe.pullCard());
				}
			}
			dealer.addCard(shoe.pullCard());
			
			// Play hands
			for (Gambler player : players) {
				if (!player.finishedPlaying()) {
					while (player.nextDecision() == Decision.HIT) {
						player.addCard(shoe.pullCard());
					}
				}
			}
			// Dealer hand
			while (dealer.nextDecision() == Decision.HIT) {
				dealer.addCard(shoe.pullCard());
			}
			
			logger.log("dealer",  dealer.getInfo());
			
			// Collect each hand
			Hand dealerHand = dealer.getHand();
			for (Gambler player : players) {
				if (!player.finishedPlaying()) {
					// Check win, lose, push
					Hand playerHand = player.getHand();
					HandResult result = playerHand.compareWithDealerHand(dealerHand);
					
					// House pays player
					if (result == HandResult.WIN) {
						int payOut = playerHand.getBet();
						
						// Black jack pays 3:2
						if (playerHand.hasBlackjack()) {
							payOut *= 1.5;
						}
						
						player.collectWinnings(payOut);
						
						this.chips -= payOut;
						
					// House gets bet
					} else if (result == HandResult.LOSE) {
						this.chips += player.deductBet();
					}
					// If push, nothing to do
					
					// Discard player hand
					shoe.discardCards(playerHand.getCards());
				}
				
				logger.log("player",  player.getInfo());
			}
			// Discard dealer hand
			shoe.discardCards(dealerHand.getCards());
			
			round++;
		}
		
		logger.log("play_end", "Game ended");
	}
	
	/**
	 * Check if players are still playing
	 * 
	 * @return true if players left
	 */
	protected boolean playersStillPlaying() {
		for (Gambler player : this.players) {
			if (!player.finishedPlaying()) {
				return true;
			}
		}
		
		return false;
	}
}
