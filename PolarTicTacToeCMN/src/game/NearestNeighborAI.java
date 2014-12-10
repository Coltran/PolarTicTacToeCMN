/**
* @author Coltran Hophan-Nichols, Nick Burgard, Michael Lehmann
* This class Implements the Nearest Neighbor AI
*/
package game;

import java.util.Random;

public class NearestNeighborAI implements AI{
	
	public Character player;//not used currently
	private Character opponent;
	private Board[][] examples;//holds all of our examples to compare against
	Character result[];//holds the results for all the example games (x,o or d)
	int numberExamples;//number of example games to run can be chosen by user
	int similarity[];//holds the similarity to each example at the same turn level
	private int movex;//best move in x direction at given time
	private int movey;//best move in y direction at given time

	//constructor 
	public NearestNeighborAI(Character playerChoice, int inNumberExamples) {
		player = playerChoice;//set who we're playing at
		examples = new Board[inNumberExamples][49];//48 is maximum possible moves in a game + 1 checking for tie
		numberExamples = inNumberExamples;
		similarity = new int[inNumberExamples];
		result = new Character[inNumberExamples];
		//determine opponent 
		opponent = 'X';
		if(playerChoice == 'X') {
			opponent = 'O';
		}
		//train the classifier
		learn();
	}
	//makes the next move on the provided game
	public boolean move(Game game) {
		movex = 0;//best move in x direction (there should always be a valid move when this is called)
		movey = 0;//best move in y direction ''
		if(game.moveNumber == 0) {//just pick a random move if we're going first
			Random generator = new Random(System.nanoTime());
			movex = generator.nextInt(4);
			movey = generator.nextInt(12);
		}
		else{
			int ply = 2;//number of plys to search
			int depth = ply*2;//moves to look ahead = ply * 2
			lookAhead(game.board, player, depth, game);//call recursive lookahead function
			//lookahead will set movex and movey to best values
		}
		boolean win = game.move(player, movex, movey);//make move
		return win;//return if we won the game
	}
	/**
	 * recursively checks ahead and returns best heuristic value
	 * sets movex and movey corrosponding to the best move found
	 * @param board
	 * @param thisPlayer
	 * @param depth
	 * @return
	 */
	private Integer lookAhead(Board board, Character thisPlayer, int depth, Game game) {
		boolean[][] moves = LegalMoves.Moves(board);//all available moves
		Integer[][] values = new Integer[4][12];//stores heuristic values for each available move
		int numberLegalMoves = 0;//counts number of available moves
		for(int i=0; i<4; i++) {
			for(int j=0; j<12; j++) {
				if(moves[i][j]==true) {//if we can move there...
					numberLegalMoves++;//then it's an available move
					Board candidate = Board.clone(board);//copy the board to pass forward
					candidate.theBoard[i][j] = thisPlayer;//make move on copy
					//if we don't need to search any farther
					if(depth <= 1 || WinCheck.check(i, j, candidate) == true) {
						if(WinCheck.check(i, j, candidate)==true) {
							//if we can win
							if(candidate.theBoard[i][j] == player) {
								movex = i;
								movey = j;
								values[i][j] = 1;
								return values[i][j];//just return the winning move
							}
							//if our opponent won
							else {
								values[i][j] = -1;
							}
						}
						//if we need to evaluate the current board
						else {
							values[i][j] = predict(board);//classify and save returned value
						}
					}
					//if we need to look farther ahead
					else {
						//determine next player
						Character otherPlayer = 'X';
						if(thisPlayer == 'X') {
							otherPlayer = 'O';
						}
						//recursive function call
						values[i][j] = lookAhead(candidate, otherPlayer, depth-1, game);
					}
				}
				//if we can't move there
				else {
					values[i][j] = null;
				}
			}
		}
		//if no move available and we reached here it's a tie
		if(numberLegalMoves == 0) {
			return 0;//return tie value
		}
		//if us (max player)
		if(thisPlayer == player) {
			int maxvalue = -999999999;//maximum heuristic value of returned move
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					if(Main.nearestVerbose && values[i][j] != null) {
						System.out.format("depth = %d x = %d y = %d Value = %d\n", depth, i, j, values[i][j]);
					}
					//if we found a new max value
					if(values[i][j] != null && values[i][j] > maxvalue) {
						maxvalue = values[i][j];//set it as the max so far
					}
				}
			}
			//break ties randomly
			int[] tiesx = new int[48];//will hold x coordinate for ties
			int[] tiesy = new int[48];//will hold y coordinate for ties
			int count = 0;//tracks number of ties
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					if(values[i][j] != null && values[i][j] == maxvalue) {//if we found one of the best moves
						tiesx[count] = i;//add x value to list
						tiesy[count] = j;//add coorosponding y value to list
						count++;//increment count
					}
				}
			}
			Random generator = new Random(System.nanoTime());
			int randomChoice = generator.nextInt(count);//pick a random best move
			//make move
			movex = tiesx[randomChoice];
			movey = tiesy[randomChoice];
			return maxvalue;//we will choose best move for us
		}
		//otherwise it's our opponent (min player)
		else {
			int minvalue = 999999999;//minimum heuristic value of returned move
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					if(Main.nearestVerbose && values[i][j] != null) {
						System.out.format("depth = %d x = %d y = %d Value = %d\n", depth, i, j, values[i][j]);
					}
					//if we found a new min value
					if(values[i][j] != null && values[i][j] < minvalue) {
						minvalue = values[i][j];//set it as min so far
						movex = i;
						movey = j;
					}
				}
			}
			//break ties randomly
			int[] tiesx = new int[49];//will hold x coordinate for ties
			int[] tiesy = new int[49];//will hold y coordinate for ties
			int count = 0;//tracks number of ties
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					if(values[i][j] != null && values[i][j] == minvalue) {//if we found one of the best moves
						tiesx[count] = i;//add x value to list
						tiesy[count] = j;//add coorosponding y value to list
						count++;//increment count
					}
				}
			}
			Random generator = new Random(System.nanoTime());
			int randomChoice = generator.nextInt(count);//pick a random best move
			//make move
			movex = tiesx[randomChoice];
			movey = tiesy[randomChoice];
			return minvalue;//assume opponent will choose the best move for them
		}
		
	}
	
	//returns a prediction (x,o or d) for which player will win (or a draw)
	public int predict(Board board) {
		int moveNumber = 0;
		//count number of moves made so far
		for(int x=0; x<4; x++) {
			for(int y=0; y<12; y++) {
				if(board.theBoard[x][y] != 0) {
					moveNumber++;//for each move made increment moveNumber
				}
			}
		}
		//for each example (we will look only at the corresponding move number from each example)
		for(int example=0; example<numberExamples; example++) {
			similarity[example] = 0;//set to 0 initially
			for(int x=0; x<4; x++) {
				for(int y=0; y<12; y++) {
					if(examples[example][moveNumber] != null && board.theBoard[x][y] == examples[example][moveNumber].theBoard[x][y]) {
						similarity[example]++;//increment similarity for each board position that matches
					}
				}
			}
		}
		int maxSimilarity = 0;//maximum similarity value found so far
		int maxLocation = 0;//location of most similar so far
		//for each example tested
		for(int example=0; example<numberExamples; example++) {
			//if we find the largest similarity so far
			if(similarity[example]>maxSimilarity) {
				maxSimilarity = similarity[example];//set maxSimilarity to new max
				maxLocation = example;//set max location to location of new max
			}
		}
		//if we think we'll win
		if(result[maxLocation] == player) {
			return 1;
		}
		//if we think we'll loose
		else if(result[maxLocation] == opponent) {
			return -1;
		}
		//if we think we'l tie
		else {
			return 0;
		}
	}
	//play lots of games, saving every board state and classifying each game as win or loss
	private void learn() {
		//loop once for each desired example
		for(int i=0; i<numberExamples; i++) {
			Game trainingGame = new Game('X','O');//make a game
			//Heuristic based AIs to play the game
			HeuristicAI playerX = new HeuristicAI('X');
			HeuristicAI playerO = new HeuristicAI('O');
			int turnNumber = 0;//tracks turn for saving intermediate game boards
			//until game is done
			while(trainingGame.done == false) {
				playerX.move(trainingGame);//first player moves
				examples[i][turnNumber] = Board.clone(trainingGame.board);//save game board
				turnNumber++;//next turn
				if(trainingGame.done == true && trainingGame.winningPlayer != null && trainingGame.winningPlayer == 'X') {
					result[i] = 'x';//set result to x win if x won
				}
				//if the game is not done
				else if(trainingGame.done == false) {
					playerO.move(trainingGame);//seccond player moves
					examples[i][turnNumber] = Board.clone(trainingGame.board);//save game board
					turnNumber++;//next turn
					if(trainingGame.done == true && trainingGame.winningPlayer!= null && trainingGame.winningPlayer == 'Y') {
						result[i] = 'y';//set result to x win if x won
					}
				}
				if(trainingGame.done && trainingGame.winningPlayer == null) {
					result[i] = 'd';//set result to draw if game is over and nobody won
				}
			}
		}
	}

}
