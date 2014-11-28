/**
 * 
 */
package game;

/**
 * @author 
 * 
 */
public class LegalMoves {

	/**
	 * Returns all legal moves that can be made for a given game state
	 */
	public boolean[][] Moves(Board board, String player) {
		//array that holds all legal spaces to move to
		boolean[][] moves = new boolean[4][12];
		//for each row in the board
		for(int i=0; i<4; i++) {
			//and for each column in the board
			for(int j=0; j<12; j++) {
				//if someone has made a move in this space check all surrounding spaces
				//add surrounding space if it is free
				if(board.theBoard[i][j] != null) {
					moves[i][j] = false; //can't move to already taken space
					if(i>0 && j>0 && board.theBoard[i-1][j-1]==null) {
						moves[i-1][j-1] = true;
					}
					else if(i>0 && j<11 && board.theBoard[i-1][j+1]==null) {
						moves[i-1][j+1] = true;
					}
					else if(i>0 && board.theBoard[i-1][j]==null) {
						moves[i-1][j] = true;
					}
					else if(i<3 && j>0 && board.theBoard[i+1][j-1]==null) {
						moves[i+1][j-1] = true;
					}
					else if(i<3 && j<11 && board.theBoard[i+1][j+1]==null) {
						moves[i+1][j+1] = true;
					}
					else if(i<3 && board.theBoard[i+1][j]==null) {
						moves[i+1][j] = true;
					}
					else if(j>0 && board.theBoard[i][j-1]==null) {
						moves[i][j-1] = true;
					}
					else if(j<11 && board.theBoard[i][j+1]==null) {
						moves[i][j+1] = true;
					}
					//wrap around cases
					else if(j==0 && i>0 && board.theBoard[i-1][11]==null) {
						moves[i-1][11] = true;
					}
					else if(j==0 && i<3 && board.theBoard[i+1][11]==null) {
						moves[i+1][11] = true;
					}
					else if(j==0 && board.theBoard[i][11]==null) {
						moves[i][11] = true;
					}
					else if(j==11 && i>0 && board.theBoard[i-1][0]==null) {
						moves[i-1][0] = true;
					}
					else if(j==11 && i<3 && board.theBoard[i+1][0]==null) {
						moves[i+1][0] = true;
					}
					else if(j==11 && board.theBoard[i][0]==null) {
						moves[i][0] = true;
					}
				}
			}
		}
		return moves;
	}

}
