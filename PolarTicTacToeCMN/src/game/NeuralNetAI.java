/**
 * 
 */
package game;

import java.util.Random;

/**
 * @author Coltran
 * This class Implements the Heuristic based AI to play Polar Tic Tac Toe
 * Instantiate with HeuristicAI name = new HeuristicAI(playerChoice) 
 * where playerChoice is 'X' or 'O'
 * To have the AI make a move: name.move(game) 
 * where game is a Game
 */
public class NeuralNetAI implements AI {
	
	private Character player;//not used currently
	private Character opponent;
	private Board[][] examples;//holds all of our examples to compare against
	Character result[];//holds the results for all the example games (x,o or d)
	int numberExamples;//number of example games to run can be chosen by user
	int similarity[];//holds the similarity to each example at the same turn level
	private int movex;
	private int movey;

	/**
	 * 
	 */
	public NeuralNetAI(Character playerChoice, int inNumberExamples) {
		player = playerChoice;
		examples = new Board[inNumberExamples][48];//48 is maximum possible moves in a game
		numberExamples = inNumberExamples;
		similarity = new int[inNumberExamples];
		result = new Character[inNumberExamples];
		opponent = 'X';
		if(playerChoice == 'X') {
			opponent = 'O';
		}
		learn();
	}
	
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
			//lookAhead(game.board, player, depth);//call recursive lookahead function
			//lookahead will set movex and movey to best values
		}
		boolean win = game.move(player, movex, movey);//make move
		return win;//return if we wone the game
	}
	
	
	//play lots of games, saving every board state and classifying each game as win or loss
	private void learn() {
		//loop once for each desired example
		for(int i=0; i<numberExamples; i++) {
			Game trainingGame = new Game('x','o');//make a game
			//Heuristic based AIs to play the game
			HeuristicAI playerX = new HeuristicAI('x');
			HeuristicAI playerO = new HeuristicAI('o');
			int turnNumber = 0;//tracks turn for saving intermediate game boards
			//until game is done
			while(trainingGame.done == false) {
				playerX.move(trainingGame);//first player moves
				examples[i][turnNumber] = Board.clone(trainingGame.board);//save game board
				turnNumber++;//next turn
				if(trainingGame.winningPlayer == 'x') {
					result[i] = 'x';//set result to x win if x won
				}
				//if the game is not done
				else if(trainingGame.done == false) {
					playerO.move(trainingGame);//second player moves
					examples[i][turnNumber] = Board.clone(trainingGame.board);//save game board
					turnNumber++;//next turn
					if(trainingGame.winningPlayer == 'y') {
						result[i] = 'y';//set result to x win if x won
					}
				}
				if(trainingGame.done && trainingGame.winningPlayer == null) {
					result[i] = 'd';//set result to draw if game is over and nobody wone
				}
			}
		}
	}
}
//	/**
//	 * recursively checks ahead and returns best heuristic value
//	 * sets movex and movey corrosponding to the best move found
//	 * @param board
//	 * @param thisPlayer
//	 * @param depth
//	 * @return
//	 */
//	private Integer lookAhead(Board board, Character thisPlayer, int depth) {
//		boolean[][] moves = LegalMoves.Moves(board);//all available moves
//		Integer[][] values = new Integer[4][12];//stores heuristic values for each available move
//		for(int i=0; i<4; i++) {
//			for(int j=0; j<12; j++) {
//				if(moves[i][j]==true) {//if we can move there...
//					Board candidate = Board.clone(board);//copy the board to pass forward
//					candidate.theBoard[i][j] = thisPlayer;//make move on copy
//					//if we don't need to search any farther
//					if(depth <= 1 || WinCheck.check(i, j, candidate) == true) {
//						if(WinCheck.check(i, j, candidate)==true) {
//							//if we can win
//							if(candidate.theBoard[i][j] == player) {
//								values[i][j] = 10000;
//								return values[i][j];//just return the winning move
//							}
//							//if our opponent won
//							else {
//								values[i][j] = -10000;
//							}
//						}
//						//if we need to evaluate the current board
//						else {
//							values[i][j] = Heuristic(board, i, j);//call heuristic and save returned value
//						}
//					}
//					//if we need to look farther ahead
//					else {
//						//determine next player
//						Character otherPlayer = 'X';
//						if(thisPlayer == 'X') {
//							otherPlayer = 'O';
//						}
//						//recursive function call
//						values[i][j] = lookAhead(candidate, otherPlayer, depth-1);
//					}
//				}
//				//if we can't move there
//				else {
//					values[i][j] = null;
//				}
//			}
//		}
//		//if us (max player)
//		if(thisPlayer == player) {
//			int maxvalue = -999999999;//maximum heuristic value of returned move
//			for(int i=0; i<4; i++) {
//				for(int j=0; j<12; j++) {
//					if(values[i][j] != null && values[i][j] > maxvalue) {
//						maxvalue = values[i][j];
//					}
//				}
//			}
//			//break ties randomly
//			int[] tiesx = new int[48];//will hold x coordinate for ties
//			int[] tiesy = new int[48];//will hold y coordinate for ties
//			int count = 0;//tracks number of ties
//			for(int i=0; i<4; i++) {
//				for(int j=0; j<12; j++) {
//					if(values[i][j] != null && values[i][j] == maxvalue) {//if we found one of the best moves
//						tiesx[count] = i;//add x value to list
//						tiesy[count] = j;//add coorosponding y value to list
//						count++;//increment count
//					}
//				}
//			}
//			Random generator = new Random(System.nanoTime());
//			int randomChoice = generator.nextInt(count);//pick a random best move
//			//make move
//			movex = tiesx[randomChoice];
//			movey = tiesy[randomChoice];
//			return maxvalue;//we will choose best move for us
//		}
//		//otherwise it's our opponent (min player)
//		else {
//			int minvalue = 999999999;//minimum heuristic value of returned move
//			for(int i=0; i<4; i++) {
//				for(int j=0; j<12; j++) {
//					if(values[i][j] != null && values[i][j] < minvalue) {
//						minvalue = values[i][j];
//						movex = i;
//						movey = j;
//					}
//				}
//			}
//			//break ties randomly
//			int[] tiesx = new int[48];//will hold x coordinate for ties
//			int[] tiesy = new int[48];//will hold y coordinate for ties
//			int count = 0;//tracks number of ties
//			for(int i=0; i<4; i++) {
//				for(int j=0; j<12; j++) {
//					if(values[i][j] != null && values[i][j] == minvalue) {//if we found one of the best moves
//						tiesx[count] = i;//add x value to list
//						tiesy[count] = j;//add coorosponding y value to list
//						count++;//increment count
//					}
//				}
//			}
//			Random generator = new Random(System.nanoTime());
//			int randomChoice = generator.nextInt(count);//pick a random best move
//			//make move
//			movex = tiesx[randomChoice];
//			movey = tiesy[randomChoice];
//			return minvalue;//assume opponent will choose the best move for them
//		}
//		
//	}
//	
//	/**
//	 * calculates and returns an integer heuristic value for a provided board state
//	 * 2 in a row combinations get a value of 4 
//	 * 3 in a row combinations get a value of 9+2*4=17
//	 * @param board
//	 * @return value
//	 */
//	private int Heuristic(Board board, int movex, int movey) {
//		int value2 = 4;//value of 2 in a row
//		int value3 = 9;//value of 3 in a row is this + 2*value2
//		int value = 0;
//		Character opponent;
//		if(player == 'X') {
//			opponent = 'O';
//		}
//		else {
//			opponent = 'X';
//		}
//			//check for 2/3 in a row in x (i) direction
//			for(int j=0; j<12; j++) {
//				//2 in a row
//				for(int i=0; i<3; i++) {
//					//player
//					if(board.theBoard[i][j]==player && board.theBoard[i+1][j]==player) {
//						value += value2;
//					}
//					//opponent
//					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j]==opponent) {
//						value -= value2;
//					}
//				}
//				//3 in a row
//				for(int i=0; i<2; i++) {
//					//player
//					if(board.theBoard[i][j]==player && board.theBoard[i+1][j]==player && board.theBoard[i+1][j]==player) {
//						value += value3;
//					}
//					//opponent
//					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j]==opponent && board.theBoard[i+1][j]==opponent) {
//						value -= value3;
//					}
//				}
//			}
//			//check for 2/3 in a row in y (j) direction
//			//2 in a row
//			for(int i=0; i<3; i++) {
//				for(int j=0; j<11; j++) {
//					//player
//					if(board.theBoard[i][j]==player && board.theBoard[i][j+1]==player) {
//						value += value2;
//					}
//					//opponent
//					if(board.theBoard[i][j]==opponent && board.theBoard[i][j+1]==opponent) {
//						value -= value2;
//					}
//				}
//				//2 in a row wrap-around
//				//player
//				if(board.theBoard[i][0]==player && board.theBoard[i][11]==player) {
//					value += value2; 
//				}
//				//opponent
//				if(board.theBoard[i][0]==opponent && board.theBoard[i][11]==opponent) {
//					value -= value2; 
//				}
//				//3 in a row
//				for(int j=0; j<10; j++) {
//					//player
//					if(board.theBoard[i][j]==player && board.theBoard[i][j+1]==player && board.theBoard[i][j+2]==player){
//						value += value3;
//					}
//					//opponent
//					if(board.theBoard[i][j]==opponent && board.theBoard[i][j+1]==opponent && board.theBoard[i][j+2]==opponent){
//						value -= value3;
//					}
//				}
//				//3 in a row wrap-around
//				//player
//				if(board.theBoard[i][0]==player && board.theBoard[i][1]==player && board.theBoard[i][11]==player) {
//					value += value3;
//				}
//				//opponent
//				if(board.theBoard[i][0]==opponent && board.theBoard[i][1]==opponent && board.theBoard[i][11]==opponent) {
//					value -= value3;
//				}
//				//player
//				if(board.theBoard[i][0]==player && board.theBoard[i][11]==player && board.theBoard[i][10]==player) {
//					value += value3;
//				}
//				//opponent
//				if(board.theBoard[i][0]==opponent && board.theBoard[i][11]==opponent && board.theBoard[i][10]==opponent) {
//					value -= value3;
//				}
//			}
//			//check for 2/3 in a row diagonally
//			//2 in a row
//			for(int i=0; i<3; i++) {
//				//wrap around case left-to-right
//				//player
//				if(board.theBoard[i][0]==player && board.theBoard[i+1][11]==player) {
//					value += value2; 
//				}
//				//opponent
//				if(board.theBoard[i][0]==opponent && board.theBoard[i+1][11]==opponent) {
//					value -= value2; 
//				}
//				//wrap around case right-to-left
//				//player
//				if(board.theBoard[i][11]==player && board.theBoard[i+1][0]==player) {
//					value += value2; 
//				}
//				//opponent
//				if(board.theBoard[i][11]==opponent && board.theBoard[i+1][0]==opponent) {
//					value -= value2; 
//				}
//				//left to right
//				for(int j=0; j<11; j++) {
//					//player
//					if(board.theBoard[i][j]==player && board.theBoard[i+1][j+1]==player) {
//						value += value2;
//					}
//					//opponent
//					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j+1]==opponent) {
//						value -= value2;
//					}
//				}
//				//right to left
//				for(int j=1; j<12; j++) {
//					//player
//					if(board.theBoard[i][j]==player && board.theBoard[i+1][j-1]==player) {
//						value += value2;
//					}
//					//opponent
//					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j-1]==opponent) {
//						value -= value2;
//					}
//				}
//			}
//			//3 in a row
//			for(int i=0; i<2; i++) {
//				//wrap around case left-to-right
//				//player
//				if(board.theBoard[i][0]==player && board.theBoard[i+1][11]==player && board.theBoard[i+2][10]==player) {
//					value += value3; 
//				}
//				//opponent
//				if(board.theBoard[i][0]==opponent && board.theBoard[i+1][11]==opponent && board.theBoard[i+2][10]==opponent) {
//					value -= value3; 
//				}
//				//player
//				if(board.theBoard[i][1]==player && board.theBoard[i+1][0]==player && board.theBoard[i+2][11]==player) {
//					value += value3; 
//				}
//				//opponent
//				if(board.theBoard[i][1]==opponent && board.theBoard[i+1][0]==opponent && board.theBoard[i+2][11]==opponent) {
//					value -= value3; 
//				}
//				//wrap around case right-to-left
//				//player
//				if(board.theBoard[i][11]==player && board.theBoard[i+1][0]==player && board.theBoard[i+2][1]==player) {
//					value += value3; 
//				}
//				//opponent
//				if(board.theBoard[i][11]==opponent && board.theBoard[i+1][0]==opponent && board.theBoard[i+2][1]==opponent) {
//					value -= value3; 
//				}
//				//player
//				if(board.theBoard[i][10]==player && board.theBoard[i+1][11]==player && board.theBoard[i+2][0]==player) {
//					value += value3; 
//				}
//				//opponent
//				if(board.theBoard[i][10]==opponent && board.theBoard[i+1][11]==opponent && board.theBoard[i+2][0]==opponent) {
//					value -= value3; 
//				}
//				//left to right
//				for(int j=0; j<10; j++) {
//					//player
//					if(board.theBoard[i][j]==player && board.theBoard[i+1][j+1]==player && board.theBoard[i+2][j+2]==player) {
//						value += value3;
//					}
//					//opponent
//					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j+1]==opponent && board.theBoard[i+2][j+2]==opponent) {
//						value -= value3;
//					}
//				}
//				//right to left
//				for(int j=2; j<12; j++) {
//					//player
//					if(board.theBoard[i][j]==player && board.theBoard[i+1][j-1]==player && board.theBoard[i+2][j-2]==player) {
//						value += value3;
//					}
//					//opponent
//					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j-1]==opponent && board.theBoard[i+2][j-2]==opponent) {
//						value -= value3;
//					}
//				}
//			}
//			return value;
//	}
