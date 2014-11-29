/**
 * 
 */
package game;

/**
 * @author
 * This class Implements the resolution based win checker
 */
public class WinCheck {
	
	private String player;
	int x, y;

	/**
	 * 
	 */
	public WinCheck(String playerChoice, int xLoc, int yLoc) {
		player = playerChoice;
		x = xLoc;
		y = yLoc;
		
	}

	/**
	 * calculates and returns an integer heuristic value for a provided board state
	 * 2 in a row combinations get a value of 4 
	 * 3 in a row combinations get a value of 9+2*4=17
	 * @param board
	 * @return value
	 */
	private int Heuristic(Board board) {


		return 0;
	}

}
