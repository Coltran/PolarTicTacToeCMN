/**
 * 
 */
package game;

/**
 * @author
 * This class Implements the Heuristic based AI to play Polar Tic Tac Toe
 */
public class HeuristicAI {
	
	private String player;

	/**
	 * 
	 */
	public HeuristicAI(String playerChoice) {
		player = playerChoice;
	}
	public void move(Game game) {
		int movex = 0;//best move in x direction (there should always be a valid move when this is called
		int movey = 0;//best move in y direction ''
		int maxvalue = -999999999;//maximum heuristic value of returned move
		int[][] values = new int[4][12];//array that holds heuristic value for all possible moves
		boolean moves[][] = LegalMoves.Moves(game.board);
		for(int i=0; i<4; i++) {
			for(int j=0; j<12; j++) {
				if(moves[i][j]) {
					Board candidate = Board.clone(game.board);
					candidate.theBoard[i][j] = player;
					values[i][j] = Heuristic(candidate);
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
		game.move(player, movex, movey);
	}
	/**
	 * calculates and returns an integer heuristic value for a provided board state
	 * 2 in a row combinations get a value of 4 
	 * 3 in a row combinations get a value of 9+2*4=17
	 * @param board
	 * @return value
	 */
	private int Heuristic(Board board) {
		int value2 = 4;
		int value3 = 9;
		int value = 0;
		//check for 2/3 in a row in x (i) direction
		for(int j=0; j<12; j++) {
			//2 in a row
			for(int i=0; i<3; i++) {
				if(board.theBoard[i][j]==player && board.theBoard[i+1][j]==player) {
					value += value2;
				}
			}
			//3 in a row
			for(int i=0; i<2; i++) {
				if(board.theBoard[i][j]==player && board.theBoard[i+1][j]==player && board.theBoard[i+1][j]==player) {
					value += value3;
				}
			}
		}
		//check for 2/3 in a row in y (j) direction
		//2 in a row
		for(int i=0; i<3; i++) {
			for(int j=0; j<11; j++) {
				if(board.theBoard[i][j]==player && board.theBoard[i][j+1]==player) {
					value += value2;
				}
			}
			//2 in a row wrap-around
			if(board.theBoard[i][0]==player && board.theBoard[i][11]==player) {
				value += value2; 
			}
			//3 in a row
			for(int j=0; j<10; j++) {
				if(board.theBoard[i][j]==player && board.theBoard[i][j+1]==player && board.theBoard[i][j+2]==player){
					value += value3;
				}
			}
			//3 in a row wrap-around
			if(board.theBoard[i][0]==player && board.theBoard[i][1]==player && board.theBoard[i][11]==player) {
				value += value3;
			}
			if(board.theBoard[i][0]==player && board.theBoard[i][11]==player && board.theBoard[i][10]==player) {
				value += value3;
			}
		}
		//check for 2/3 in a row diagonally
		//2 in a row
		for(int i=0; i<3; i++) {
			//wrap around case left-to-right
			if(board.theBoard[i][0]==player && board.theBoard[i+1][11]==player) {
				value += value2; 
			}
			//wrap around case right-to-left
			if(board.theBoard[i][11]==player && board.theBoard[i+1][0]==player) {
				value += value2; 
			}
			//left to right
			for(int j=0; j<11; j++) {
				if(board.theBoard[i][j]==player && board.theBoard[i+1][j+1]==player) {
					value += value2;
				}
			}
			//right to left
			for(int j=1; j<12; j++) {
				if(board.theBoard[i][j]==player && board.theBoard[i+1][j-1]==player) {
					value += value2;
				}
			}
		}
		//3 in a row
		for(int i=0; i<2; i++) {
			//wrap around case left-to-right
			if(board.theBoard[i][0]==player && board.theBoard[i+1][11]==player && board.theBoard[i+2][10]==player) {
				value += value3; 
			}
			if(board.theBoard[i][1]==player && board.theBoard[i+1][0]==player && board.theBoard[i+2][11]==player) {
				value += value3; 
			}
			//wrap around case right-to-left
			if(board.theBoard[i][11]==player && board.theBoard[i+1][0]==player && board.theBoard[i+2][1]==player) {
				value += value3; 
			}
			if(board.theBoard[i][10]==player && board.theBoard[i+1][11]==player && board.theBoard[i+2][0]==player) {
				value += value3; 
			}
			//left to right
			for(int j=0; j<10; j++) {
				if(board.theBoard[i][j]==player && board.theBoard[i+1][j+1]==player && board.theBoard[i+2][j+2]==player) {
					value += value3;
				}
			}
			//right to left
			for(int j=2; j<12; j++) {
				if(board.theBoard[i][j]==player && board.theBoard[i+1][j-1]==player && board.theBoard[i+2][j-2]==player) {
					value += value3;
				}
			}
		}
		return value;
	}

}
