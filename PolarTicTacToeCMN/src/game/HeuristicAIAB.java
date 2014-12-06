
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
	public class HeuristicAIAB implements AI {
		
		public Character player;
		private int movex;
		private int movey;

		/**
		 * 
		 */
		public HeuristicAIAB(Character playerChoice) {
			player = playerChoice;
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
				lookAhead(game.board, player, depth, game);//call recursive lookahead function
				//lookahead will set movex and movey to best values
			}
			boolean win = game.move(player, movex, movey);//make move
			return win;//return if we wone the game
		}
		/**
		 * recursively checks ahead and returns best heuristic value
		 * sets movex and movey corrosponding to the best move found
		 * @param board
		 * @param thisPlayer
		 * @param depth
		 * @return
		 */
		private Integer lookAhead(Board board, Character thisPlayer, int depth, Game game, int parentValue, int grandparentValue) {
			boolean[][] moves = LegalMoves.Moves(board);//all available moves
			Integer[][] values = new Integer[4][12];//stores heuristic values for each available move
			int numberLegalMoves = 0;
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					if(moves[i][j]==true) {//if we can move there...
						numberLegalMoves++;
						Board candidate = Board.clone(board);//copy the board to pass forward
						candidate.theBoard[i][j] = thisPlayer;//make move on copy
						//if we don't need to search any farther
						if(depth <= 1 || WinCheck.check(i, j, candidate) == true) {
							if(WinCheck.check(i, j, candidate)==true) {
								//if we can win
								if(candidate.theBoard[i][j] == player) {
									values[i][j] = 10000;
									return values[i][j];//just return the winning move
								}
								//if our opponent won
								else {
									values[i][j] = -10000;
								}
							}
							//if we need to evaluate the current board
							else {
								values[i][j] = Heuristic(board, i, j);//call heuristic and save returned value
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
			if(numberLegalMoves == 0) {
				return 0;
			}
			//if us (max player)
			if(thisPlayer == player) {
				int maxvalue = -999999999;//maximum heuristic value of returned move
				for(int i=0; i<4; i++) {
					for(int j=0; j<12; j++) {
						if(values[i][j] != null && values[i][j] > maxvalue) {
							maxvalue = values[i][j];
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
						if(values[i][j] != null && values[i][j] < minvalue) {
							minvalue = values[i][j];
							movex = i;
							movey = j;
						}
					}
				}
				//break ties randomly
				int[] tiesx = new int[48];//will hold x coordinate for ties
				int[] tiesy = new int[48];//will hold y coordinate for ties
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
				
				
				
				
				// ERRORS OCCUR AT THIS LINE OF CODE
				// ERRORS OCCUR AT THIS LINE OF CODE
				// ERRORS OCCUR AT THIS LINE OF CODE
				// ERRORS OCCUR AT THIS LINE OF CODE
				int randomChoice = generator.nextInt(count);//pick a random best move
				// ERRORS OCCUR AT THIS LINE OF CODE
				// ERRORS OCCUR AT THIS LINE OF CODE
				// ERRORS OCCUR AT THIS LINE OF CODE
				// ERRORS OCCUR AT THIS LINE OF CODE
				
				
				
				
				
				
				//make move
				movex = tiesx[randomChoice];
				movey = tiesy[randomChoice];
				return minvalue;//assume opponent will choose the best move for them
			}
			
		}
