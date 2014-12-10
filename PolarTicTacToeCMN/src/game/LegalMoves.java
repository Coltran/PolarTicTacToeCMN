/**
* @author Coltran Hophan-Nichols, Nick Burgard, Michael Lehmann
* This class Implements the Legal Moves checker
*/
package game;

public class LegalMoves {

	/**
	 * Returns all legal moves that can be made for a given game state
	 */
	public static boolean[][] Moves(Board board) {
		//array that holds all legal spaces to move to
		boolean[][] moves = new boolean[4][12];
		//for each row in the board
		for(int i=0; i<4; i++) {
			//and for each column in the board
			for(int j=0; j<12; j++) {
				//if someone has made a move in this space check all surrounding spaces
				//add surrounding space if it is free
				if(board.theBoard[i][j] != 0)
				{
					moves[i][j] = false; //can't move to already taken space
					if(i>0 && j>0 && board.theBoard[i-1][j-1]==0) {
						moves[i-1][j-1] = true;
					}
					if(i>0 && j<11 && board.theBoard[i-1][j+1]==0) {
						moves[i-1][j+1] = true;
					}
					if(i>0 && board.theBoard[i-1][j]==0) {
						moves[i-1][j] = true;
					}
					if(i<3 && j>0 && board.theBoard[i+1][j-1]==0) {
						moves[i+1][j-1] = true;
					}
					if(i<3 && j<11 && board.theBoard[i+1][j+1]==0) {
						moves[i+1][j+1] = true;
					}
					if(i<3 && board.theBoard[i+1][j]==0) {
						moves[i+1][j] = true;
					}
					if(j>0 && board.theBoard[i][j-1]==0) {
						moves[i][j-1] = true;
					}
					if(j<11 && board.theBoard[i][j+1]==0) {
						moves[i][j+1] = true;
					}
					//wrap around cases
					if(j==0 && i>0 && board.theBoard[i-1][11]==0) {
						moves[i-1][11] = true;
					}
					if(j==0 && i<3 && board.theBoard[i+1][11]==0) {
						moves[i+1][11] = true;
					}
					if(j==0 && board.theBoard[i][11]==0) {
						moves[i][11] = true;
					}
					if(j==11 && i>0 && board.theBoard[i-1][0]==0) {
						moves[i-1][0] = true;
					}
					if(j==11 && i<3 && board.theBoard[i+1][0]==0) {
						moves[i+1][0] = true;
					}
					if(j==11 && board.theBoard[i][0]==0) {
						moves[i][0] = true;
					}
				}
			}
		}
		return moves;
	}

}
