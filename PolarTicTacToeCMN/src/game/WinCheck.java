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
	
	/**
	 * Uses for loops to create arrays that match the rules shown in comments
	 * @param x, y, board
	 * @return result of Unify function
	 */
	public static boolean check(int x, int y, Board board) {

		Character[][] win = new Character[7][4];
		Character player = board.theBoard[x][y];
		
		//all values in of x are player (entire column is player)
		for (int i = 0; i < 4; i++)
		{
			win[0][i] = board.theBoard[i][y];
		}
		
		//all values of x+1,y+1 and x-1,y-1 are player (within bounds)
		for (int i = x; i >= (x-3); i--)
		{
			if (i > y)
				win[1][i] = board.theBoard[x-i][12-(i-y)];
			else if ((y-i) > 11)
				win[1][i] = board.theBoard[x-i][(y-i)-12];
			else
				win[1][i] = board.theBoard[x-i][y-i];
		}
//		for (int i = 1; i <= (3 - x); i++)
//		{
//			
//			win[1][i] = board.theBoard[x+i][y+i];
//		}
		
		//all values of x-1,y+1 and x+1,y-1 are player (within bounds)
		for (int i = x; i >= (x-3); i--)
		{
			if ((y+i) > 11)
				win[2][i] = board.theBoard[x-i][(y+i)-12];
			else if (y < -i)
				win[2][i] = board.theBoard[x-i][12+(y+i)];
			else
				win[2][i] = board.theBoard[x-i][y+i];
		}
//		for (int i = 1; i <= (3 - x); i++)
//		{
//			win[2][i] = board.theBoard[x+i][y-i];
//		}
		
		//y-3,y-2,y-1 are player
		for (int i = 0; i < 4; i++)
		{
			if (y-i < 0)
				win[3][i] = board.theBoard[x][12-(y-i)];
			else
				win[3][i] = board.theBoard[x][y-i];
		}
		
		//y-2,y-1,y+1 are player
		for (int i = -1; i < 3; i++)
		{
			if (y-i < 0)
				win[4][i] = board.theBoard[x][12-(y-i)];
			else if (y-i > 11)
				win[4][i] = board.theBoard[x][(y-i)-12];
			else
				win[4][i] = board.theBoard[x][y-i];
		}
		
		//y-1,y+1,y+2 are player
		for (int i = -2; i < 2; i++)
		{
			if (y-i < 0)
				win[5][i] = board.theBoard[x][12-(y-i)];
			else if (y-i > 11)
				win[5][i] = board.theBoard[x][(y-i)-12];
			else
				win[5][i] = board.theBoard[x][y-i];
		}
		
		//y+1,y+2,y+3 are player
		for (int i = -3; i < 1; i++)
		{
			if (y-i > 11)
				win[6][i] = board.theBoard[x][(y-i)-12];
			else
				win[6][i] = board.theBoard[x][y-i];
		}
		
		Character[] checkVal = new Character[]{player,player,player,player};
		return Unify(win, checkVal, 0);
	}
	
	/**
	 * Uses unification to check rule arrays off winning array
	 * @param winCheck, winValue, i
	 * @return unified
	 */
	private static boolean Unify(Character[][] winCheck, Character[] winValue, int i)
	{
		 boolean unified = Arrays.equals(winCheck[i],winValue);
		 if (!unified)
		 {
			 unified = Unify(winCheck, winValue, i+1);
		 }
		 return unified;
	}
	
//	private Character Unify(Character player, Character win)
//	{
//		if (player == win)
//			return null;
//		else
//			return player;
//	}
}