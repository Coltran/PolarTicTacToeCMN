/**
 * 
 */
package game;

/**
 * @author Coltran
 * This class Implements the Heuristic based AI to play Polar Tic Tac Toe
 * Instantiate with HeuristicAI name = new HeuristicAI(playerChoice) 
 * where playerChoice is 'x' or 'o'
 * To have the AI make a move: name.move(game) 
 * where game is a Game
 */
public class HeuristicAI {
	
	public Character player;

	/**
	 * 
	 */
	public HeuristicAI(Character playerChoice) {
		player = playerChoice;
	}
	public boolean move(Game game) {
		int movex = 0;//best move in x direction (there should always be a valid move when this is called
		int movey = 0;//best move in y direction ''
		int maxvalue = -999999999;//maximum heuristic value of returned move
		int[][] values = new int[4][12];//array that holds heuristic value for all possible moves
		boolean moves[][];
		//on first move, all the board is legal
		if(game.moveNumber == 0) {
			moves = new boolean[4][12];
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					moves[i][j] = true;
				}
			}
		}
		//otherwise get legal moves
		else {
			moves = LegalMoves.Moves(game.board);
		}
		for(int i=0; i<4; i++) {
			for(int j=0; j<12; j++) {
				if(moves[i][j]) {
					Board candidate = Board.clone(game.board);
					candidate.theBoard[i][j] = player;
					values[i][j] = Heuristic(candidate, i, j);
				}
			}
		}
		for(int i=0; i<4; i++) {
			for(int j=0; j<12; j++) {
				if(values[i][j]>maxvalue) {
					maxvalue = values[i][j];
					movex = i;
					movey = j;
				}
			}
		}
		boolean win = game.move(player, movex, movey);
		return win;
	}
	/**
	 * calculates and returns an integer heuristic value for a provided board state
	 * 2 in a row combinations get a value of 4 
	 * 3 in a row combinations get a value of 9+2*4=17
	 * @param board
	 * @return value
	 */
	private int Heuristic(Board board, int movex, int movey) {
		int value2 = 4;
		int value3 = 9;
		int value = 0;
		Character opponent;
		if(player == 'x') {
			opponent = 'o';
		}
		else {
			opponent = 'x';
		}
		//check for win
		if(WinCheck.check(movex, movey, board)) {
			//if we wone
			if(board.theBoard[movex][movey] == player) {
				return 10000;
			}
			//if our opponent wone
			else {
				return -10000;
			}
		}
		else {
			//check for 2/3 in a row in x (i) direction
			for(int j=0; j<12; j++) {
				//2 in a row
				for(int i=0; i<3; i++) {
					//player
					if(board.theBoard[i][j]==player && board.theBoard[i+1][j]==player) {
						value += value2;
					}
					//opponent
					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j]==opponent) {
						value -= value2;
					}
				}
				//3 in a row
				for(int i=0; i<2; i++) {
					//player
					if(board.theBoard[i][j]==player && board.theBoard[i+1][j]==player && board.theBoard[i+1][j]==player) {
						value += value3;
					}
					//opponent
					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j]==opponent && board.theBoard[i+1][j]==opponent) {
						value -= value3;
					}
				}
			}
			//check for 2/3 in a row in y (j) direction
			//2 in a row
			for(int i=0; i<3; i++) {
				for(int j=0; j<11; j++) {
					//player
					if(board.theBoard[i][j]==player && board.theBoard[i][j+1]==player) {
						value += value2;
					}
					//opponent
					if(board.theBoard[i][j]==opponent && board.theBoard[i][j+1]==opponent) {
						value -= value2;
					}
				}
				//2 in a row wrap-around
				//player
				if(board.theBoard[i][0]==player && board.theBoard[i][11]==player) {
					value += value2; 
				}
				//opponent
				if(board.theBoard[i][0]==opponent && board.theBoard[i][11]==opponent) {
					value -= value2; 
				}
				//3 in a row
				for(int j=0; j<10; j++) {
					//player
					if(board.theBoard[i][j]==player && board.theBoard[i][j+1]==player && board.theBoard[i][j+2]==player){
						value += value3;
					}
					//opponent
					if(board.theBoard[i][j]==opponent && board.theBoard[i][j+1]==opponent && board.theBoard[i][j+2]==opponent){
						value -= value3;
					}
				}
				//3 in a row wrap-around
				//player
				if(board.theBoard[i][0]==player && board.theBoard[i][1]==player && board.theBoard[i][11]==player) {
					value += value3;
				}
				//opponent
				if(board.theBoard[i][0]==opponent && board.theBoard[i][1]==opponent && board.theBoard[i][11]==opponent) {
					value -= value3;
				}
				//player
				if(board.theBoard[i][0]==player && board.theBoard[i][11]==player && board.theBoard[i][10]==player) {
					value += value3;
				}
				//opponent
				if(board.theBoard[i][0]==opponent && board.theBoard[i][11]==opponent && board.theBoard[i][10]==opponent) {
					value -= value3;
				}
			}
			//check for 2/3 in a row diagonally
			//2 in a row
			for(int i=0; i<3; i++) {
				//wrap around case left-to-right
				//player
				if(board.theBoard[i][0]==player && board.theBoard[i+1][11]==player) {
					value += value2; 
				}
				//opponent
				if(board.theBoard[i][0]==opponent && board.theBoard[i+1][11]==opponent) {
					value -= value2; 
				}
				//wrap around case right-to-left
				//player
				if(board.theBoard[i][11]==player && board.theBoard[i+1][0]==player) {
					value += value2; 
				}
				//opponent
				if(board.theBoard[i][11]==opponent && board.theBoard[i+1][0]==opponent) {
					value -= value2; 
				}
				//left to right
				for(int j=0; j<11; j++) {
					//player
					if(board.theBoard[i][j]==player && board.theBoard[i+1][j+1]==player) {
						value += value2;
					}
					//opponent
					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j+1]==opponent) {
						value -= value2;
					}
				}
				//right to left
				for(int j=1; j<12; j++) {
					//player
					if(board.theBoard[i][j]==player && board.theBoard[i+1][j-1]==player) {
						value += value2;
					}
					//opponent
					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j-1]==opponent) {
						value -= value2;
					}
				}
			}
			//3 in a row
			for(int i=0; i<2; i++) {
				//wrap around case left-to-right
				//player
				if(board.theBoard[i][0]==player && board.theBoard[i+1][11]==player && board.theBoard[i+2][10]==player) {
					value += value3; 
				}
				//opponent
				if(board.theBoard[i][0]==opponent && board.theBoard[i+1][11]==opponent && board.theBoard[i+2][10]==opponent) {
					value -= value3; 
				}
				//player
				if(board.theBoard[i][1]==player && board.theBoard[i+1][0]==player && board.theBoard[i+2][11]==player) {
					value += value3; 
				}
				//opponent
				if(board.theBoard[i][1]==opponent && board.theBoard[i+1][0]==opponent && board.theBoard[i+2][11]==opponent) {
					value -= value3; 
				}
				//wrap around case right-to-left
				//player
				if(board.theBoard[i][11]==player && board.theBoard[i+1][0]==player && board.theBoard[i+2][1]==player) {
					value += value3; 
				}
				//opponent
				if(board.theBoard[i][11]==opponent && board.theBoard[i+1][0]==opponent && board.theBoard[i+2][1]==opponent) {
					value -= value3; 
				}
				//player
				if(board.theBoard[i][10]==player && board.theBoard[i+1][11]==player && board.theBoard[i+2][0]==player) {
					value += value3; 
				}
				//opponent
				if(board.theBoard[i][10]==opponent && board.theBoard[i+1][11]==opponent && board.theBoard[i+2][0]==opponent) {
					value -= value3; 
				}
				//left to right
				for(int j=0; j<10; j++) {
					//player
					if(board.theBoard[i][j]==player && board.theBoard[i+1][j+1]==player && board.theBoard[i+2][j+2]==player) {
						value += value3;
					}
					//opponent
					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j+1]==opponent && board.theBoard[i+2][j+2]==opponent) {
						value -= value3;
					}
				}
				//right to left
				for(int j=2; j<12; j++) {
					//player
					if(board.theBoard[i][j]==player && board.theBoard[i+1][j-1]==player && board.theBoard[i+2][j-2]==player) {
						value += value3;
					}
					//opponent
					if(board.theBoard[i][j]==opponent && board.theBoard[i+1][j-1]==opponent && board.theBoard[i+2][j-2]==opponent) {
						value -= value3;
					}
				}
			}
			return value;
		}
	}

}
