/**
 * 
 */
package game;

import java.util.Arrays;

/**
 * @author
 * This class Implements the resolution based win checker
 */
public class WinCheck {
	
	int x, y;
	Board board;
	

	/**
	 * 
	 */
	public WinCheck(int xLoc, int yLoc, Board boardIn) {
		x = xLoc;
		y = yLoc;
		board = boardIn;
		
	}

	/**
	 * calculates and returns an integer heuristic value for a provided board state
	 * 2 in a row combinations get a value of 4 
	 * 3 in a row combinations get a value of 9+2*4=17
	 * @param board
	 * @return value
	 */
	private boolean UseRules() {

		Character[][] win = new Character[7][4];
		Character player = board.theBoard[x][y];
		
		//all values in of x are player (entire column is player)
		for (int i = 0; i < 4; i++)
		{
			player = Unify(player, board.theBoard[i][y]);
			if (player != null)
				break;
			win[0][i] = board.theBoard[i][y];
		}
		
		//all values of x+1,y+1 and x-1,y-1 are player (within bounds)
		for (int i = x; i >= 0; i--)
		{
			win[1][i] = board.theBoard[x-i][y-i];
		}
		for (int i = 1; i <= (3 - x); i++)
		{
			win[1][i] = board.theBoard[x+i][y+i];
		}
		
		//all values of x-1,y+1 and x+1,y-1 are player (within bounds)
		for (int i = x; i >= 0; i--)
		{
			win[2][i] = board.theBoard[x-i][y+i];
		}
		for (int i = 1; i <= (3 - x); i++)
		{
			win[2][i] = board.theBoard[x+i][y-i];
		}
		
		//y-3,y-2,y-1 are player
		for (int i = 0; i < 4; i++)
		{
			win[3][i] = board.theBoard[x][y-i];
		}
		
		//y-2,y-1,y+1 are player
		for (int i = -1; i < 3; i++)
		{
			win[4][i] = board.theBoard[x][y-i];
		}
		
		//y-1,y+1,y+2 are player
		for (int i = -2; i < 2; i++)
		{
			win[5][i] = board.theBoard[x][y-i];
		}
		
		//y+1,y+2,y+3 are player
		for (int i = -3; i < 1; i++)
		{
			win[6][i] = board.theBoard[x][y-i];
		}
		
		Character[] checkVal = new Character[]{player,player,player,player};
		return Unify(win, checkVal, 0);
	}
	
//	private Character Unify(Character player, Character win)
//	{
//		if (player == win)
//			return null;
//		else
//			return player;
//	}
	
	private boolean Unify(Character[][] winCheck, Character[] winValue, int i)
	{
		 boolean unified = Arrays.equals(winCheck[i],winValue);
		 if (!unified)
		 {
			 unified = Unify(winCheck, winValue, i+1);
		 }
		 return unified;
	}
	

}