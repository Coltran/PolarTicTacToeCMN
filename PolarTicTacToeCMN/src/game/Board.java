/**
* @author Coltran Hophan-Nichols, Nick Burgard, Michael Lehmann
*
*/
package game;
//this is used for the game board as well as by AIs when looking ahead
class Board {
	
	public Character[][] theBoard;//the board

	/**
	 * 
	 */
	public Board() {
		theBoard = new Character[4][12];//initialize to size of game board
	}
	//takes in any board and returns an independent but identical copy of that board
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
