package game;

import javax.swing.JOptionPane;

public class Main
{
	static Character player1 = null;
	static Character player2 = null;
	static Game game = new Game(player1, player2);

	public static void main(String[] args)
	{
		boolean done = false; // Boolean for while loop below

		/*
		 * Choices for game include: Human vs. Human, Human vs. Computer, Computer
		 * vs. Computer or the user can quit.
		 */
		String[] choices = { "HvH", "HvC", "CvC", "Quit" };

		/*
		 * Choices of AI the user game play against or have compete against each
		 * other include: With Alpha-Beta Pruning, Without Alpha-Beta Pruning,
		 * Temporal Different Neural Network Heuristic Function Naive
		 * Bayes/Nearest Neighbor/Decision Tree
		 */
		String[] aiChoices = { "Basic Game Heuristic",
				"Nearest Neighbor Heuristic",
				"Temporal Difference Neural Network", "Alpha-Beta Pruning?" };

		/*
		 * Player choose either be either X or O
		 */

		String[] playerVariable = { "X", "O" };

		while (!done)
		{
			int choice = JOptionPane.showOptionDialog(null, "Options",
				"Pick an Option", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
				
			switch (choice)
			{
				// Human vs. Human
				case 0:
					int playerVariables = JOptionPane.showOptionDialog(null,
							"X or O", "Choose X or O",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, playerVariable,
							playerVariable[0]);
	
					switch (playerVariables)
					{
						case 0:
							game.player1 = 'X';
							game.player2 = 'O';
		
							game.resetGame(); // Start new game
							game.printState(); // Print the blank board
							playGame(game.player1, game.player2, null, null);
							break;
		
						case 1:
							game.player1 = 'O';
							game.player2 = 'X';
		
							game.resetGame(); // Start new game
							game.printState(); // Print the blank board
							playGame(game.player1, game.player2, null, null);
							break;
		
						default:
							System.exit(0);
							break;
					}
	
					break;
	
				// Human vs. Computer
				case 1:
					int aiOption = JOptionPane.showOptionDialog(null,
							"AI Opponent", "Choose an AI Opponent",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, aiChoices,
							aiChoices[0]);
	
					switch (aiOption)
					{
						// Heuristic created from exercise 5.9 in book
						case 0:
							game.player1 = 'X';
							HeuristicAI heuristicAI = new HeuristicAI('O'); //Heuristic AI
							game.resetGame();
							game.printState();
							playGame(game.player1, heuristicAI.player, null, heuristicAI);
							break;
		
						//Nearest Neighbor Heuristic
						case 1:
							game.player1 = 'X';
							NearestNeighborAI nearestNeighborAI = new NearestNeighborAI('O'); //Nearest Neighbor AI
							game.resetGame();
							game.printState();
							playGame(game.player1, nearestNeighborAI.player, null, nearestNeighborAI);
							break;
		
						// temporal Difference Neural Network Heuristic
						case 2:
							game.player1 = 'X';
							TemporalDifferenceAI temporalDifferenceAI = new TemporalDifferenceAI('O');
							game.resetGame();
							game.printState();
							playGame(game.player1, temporalDifferenceAI.player, null, temporalDifferenceAI);
							break;
		
						// Implement Alpha-Beta Pruning to heuristic function
						case 3:
							game.player1 = 'X';
							
							break;
		
						default:
							System.exit(0);
					}
					break;
	
				// Computer vs. Computer
				case 2:
					// Choose two AIs to compete against each other.
					int aiOption1 = JOptionPane.showOptionDialog(null,
							"AI Opponent", "Choose an AI Opponent",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, aiChoices,
							aiChoices[0]);
					
					int aiOption2 = JOptionPane.showOptionDialog(null,
							"AI Opponent", "Choose an AI Opponent",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, aiChoices,
							aiChoices[0]);
					
					if(aiOption1 == 0 && aiOption2 == 0){
						HeuristicAI heuristicAI1 = new HeuristicAI('X'); //Heuristic AI
						HeuristicAI heuristicAI2 = new HeuristicAI('O'); //Heuristic AI
						game.resetGame();
						game.printState();
						playGame(heuristicAI1.player, heuristicAI2.player, heuristicAI1, heuristicAI2);
					}else if(aiOption1 == 0 && aiOption2 == 1){
						HeuristicAI heuristicAI = new HeuristicAI('X'); //Heuristic AI
						NearestNeighborAI nearestNeighborAI = new NearestNeighborAI('O');
						game.resetGame();
						game.printState();
						playGame(heuristicAI.player, nearestNeighborAI.player, heuristicAI, nearestNeighborAI);
					}else if(aiOption1 == 0 && aiOption2 == 2){
						HeuristicAI heuristicAI = new HeuristicAI('X'); //Heuristic AI
						TemporalDifferenceAI temporalDifferenceAI = new TemporalDifferenceAI('O');
						game.resetGame();
						game.printState();
						playGame(heuristicAI.player, temporalDifferenceAI.player, heuristicAI, temporalDifferenceAI);
					}else if(aiOption1 == )
					
					break;
	
				// Quit
				case 3:
	
					done = true;
					break;
				default:
					System.exit(0);
			}
		}
	}
	
	public static void playGame(Character person1, Character person2, AI ai1, AI ai2)
	{
		int count = 0;
		boolean done = false;
		Character player1 = person1;
		Character player2 = person2;

		int p1x = 0;
		int p1y = 0;
		
		int p2x = 0;
		int p2y = 0;
		
		String[] xOptions = { "A", "B", "C", "D" };
		String[] yOptions = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12" };

		while (!done)
		{
			if (count % 2 == 0)
			{
				//if player1 is AI ai.move
				if(ai1 != null) {
					ai1.move(game);
					count++;
					if(game.done) {
						done = true;
					}
				}
				//else 
				else {
					// Play the game until a winner is declared
					p1x = JOptionPane.showOptionDialog(null, "Player X",
							"Input a Row value", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, xOptions,
							xOptions[0]);

					p1y = JOptionPane.showOptionDialog(null, "Player X",
							"Input a Column value", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, yOptions,
							yOptions[0]);

					//Check if move is legal
					if(count == 0){
						game.move(player1, p1x, p1y);
						count++;
					}
					else if(LegalMoves.Moves(game.board)[p1x][p1y] == true)
					{
						//Only increment turn if move is legal, else repeat turn
						count++;
						//If move is legal, check if it's a winning move.
						if (game.move(player1, p1x, p1y) == true)
						{
							done = true;
						}
					}
					else
					{
						System.out.println("Not a legal move.");
					}
				}
			}
			else
			{
				//if player2 is AI ai.move
				//if player1 is AI ai.move
				if(ai2 != null) {
					ai2.move(game);
					count++;
					if(game.done) {
						done = true;
					}
				}
				//else 
				else {
					p2x = JOptionPane.showOptionDialog(null, "Player O",
							"Input a Row value", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, xOptions,
							xOptions[0]);

					p2y = JOptionPane.showOptionDialog(null, "Player O",
							"Input a Column value", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, yOptions,
							yOptions[0]);

					//Check if move is legal
					if(LegalMoves.Moves(game.board)[p2x][p2y] == true)
					{
						//Only increment turn if move is legal, else repeat turn
						count++;
						//If move is legal, check if it's a winning move.
						if (game.move(player2, p2x, p2y) == true)
						{
							done = true;
						}
					}
					else
					{
						System.out.println("Not a legal move.");
					}
				}
			}
			game.printState();
		}
	}
}
