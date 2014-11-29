/**
 * 
 */
package game;

/**
 * @author Coltran
 *
 */
class Board {

	public char[][] theBoard;

	/**
	 * 
	 */
	public Board() {
		theBoard = new char[4][12];
	}
	
	public static Board clone(Board source) {
		Board destination = new Board();
		for(int i=0; i<4; i++) {
			for(int j=0; j<12; j++) {
				destination.theBoard[i][j] = source.theBoard[i][j];
			}
		}
		return destination;
	}

}
