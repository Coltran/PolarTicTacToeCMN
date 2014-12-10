/**
* @author Coltran Hophan-Nichols, Nick Burgard, Michael Lehmann
*
*/
package game;

class Board {

	public Character[][] theBoard;

	/**
	 * 
	 */
	public Board() {
		theBoard = new Character[4][12];
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
