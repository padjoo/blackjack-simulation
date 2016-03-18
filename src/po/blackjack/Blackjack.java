package po.blackjack;

import java.io.*;

/**
 * Main entry class for Blackjack simulation.
 * 
 * Based on the following requirements:
 * - A simulation run will always have one dealer and at least two players.
 * - Each player in the game has a finite amount of money. When that money is exhausted, they can no longer participate in that run of the
 *   game simulation.
 * - A player will also stop playing in that simulation run when they reach a certain amount of winnings.
 * - If there are no more players left (either they all ran out of money or they all hit their money total or a mix of both) then the game
 *   simulation run is over
 * - We expect there to be some main entry point to kick off a game simulation. When running a simulation, what is happening in the game
 *   should be conveyed by printing to the console. It should be obvious, by reading the console output, what is happening in the game
 *   simulation.
 * - The dealer needs to abide by standard House Rules; hitting on anything under 17 and standing on anything 17 or above (S17)
 * - The non-dealer players should be setup to follow a strategy that is configured at time of player creation. Two possible strategies are:
 *   Conservative: Standing on anything where the total is greater than 10; hitting on anything where the total is ten or less (S11)
 *   Aggressive: Hitting on anything under 18 (S18)
 * - You do not need to support a multi deck shoe. Just a single deck will suffice. If the deck runs out it needs to be reshuffled.
 * - Rules: https://en.wikipedia.org/wiki/Blackjack#Rules_of_play_at_casinos
 * 
 * @author Patrick O'Reilly
 * 
 * @todo add min/max bets
 * @todo add "cut card" to avoid shuffling mid round
 * @todo optimize more for resource usage
 * @todo optimize card lists
 * @todo add more unit tests
 * @todo allow for more inputs
 * @todo what if deck run out of cards in first round?
 * @todo add input validation and error checking
 */
public class Blackjack implements Logger {
	BufferedReader reader;
	
	/**
	 * Main entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Blackjack();
	}
	
	/**
	 * Application runs on construction
	 */
	public Blackjack() {
		log("application_start", "Blackjack Simulator");

		// Create game
		Game game = new Game();
		
		// Send logs to this class for output
		game.setLogger(this);

		/*
		 * Inputs
		 * - no of players
		 * 		chips, target, strategy
		 * 		
		 */
		int noOfPlayers = promptInt("Number of players", 2, 1, 5);
		int maxHands = promptInt("Max hands", 100, 1, 9999);
		
		// Players
		for (int i = 1; i <= noOfPlayers; i++) {
			String name = String.format("Player %d", i);
			
			int chips = promptInt(String.format("%s: chips", name), 100, 0, 99999);
			int target = promptInt(String.format("%s: target chips", name), 200, 0, 99999);
			int bet = promptInt(String.format("%s: bet", name), 10, 0, 99999);
			
			String strategy = promptStrategy(name);
			
			// Add player
			game.addPlayer(new Gambler(name, chips, target, bet, PlayingStrategyFactory.factory(strategy)));
		}
		
		//game.addPlayer(new Gambler("Player 1", 100, 200, 15, PlayingStrategyFactory.factory("c")));
		//game.addPlayer(new Gambler("Player 2", 200, 500, 25, PlayingStrategyFactory.factory("a")));

		// 1 deck
		game.addDecksToShoeAndShuffle(1);
		
		// Play simulation
		game.play(maxHands);
	}
	
	public void log(String event, String message) {
		// Add space between rounds
		if ("round_start" == event) {
			echo("");
		}
		
		echo(message);
	}
	
	private void echo(String line) {
		System.out.println(line);
	}
	
	
	private BufferedReader getReader() {
		if (null == reader) {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}
		
		return reader;
	}
	
	private String promptString(String prompt) {
		System.out.print(prompt + ": ");
		String input;
	    
		try {
	    	input = getReader().readLine();
	    } catch (IOException e) {
	    	input = "";
	    	// Ignore errors
	        //e.printStackTrace();
	    }
		
	    return input;
	}
	
	private int promptInt(String prompt, int defaultValue, int min, int max) {
		String input;
		int value;
		boolean valid;
		
		do {
			input = promptString(String.format("%s (%d-%d) (default: %d)", prompt, min, max, defaultValue));
			
			if (input == null || input.isEmpty()) {
				value = defaultValue;
			} else {
				try {
			    	value = Integer.parseInt(input);
			    } catch (NumberFormatException e) {
			        //e.printStackTrace();
			    	value = -1;
			    }
			}
			
			// Validate
			valid = (min <= value && value <= max);
			
			if (!valid) {
				echo(String.format("Value needs to be between %d and %d", min, max));
			}
		} while (!valid);
		
	    return value;
	}

	private String promptStrategy(String name) {
		String prompt = String.format("%s: strategy (c=conservatie, a=aggressive) (default: c)", name);
		String defaultValue = "c";
		String value;
		boolean valid;
		
		do {
			value = promptString(prompt);

			if (value == null || value.isEmpty()) {
				value = defaultValue;
			}
			
			// Validate
			valid = (value.equals("c") || value.equals("a"));
			
			if (!valid) {
				echo("Value needs to be 'c' or 'a'");
			}
		} while (!valid);
		
	    return value;
	}	
}