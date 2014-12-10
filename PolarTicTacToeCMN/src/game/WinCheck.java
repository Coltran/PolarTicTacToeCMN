/**
* @author Coltran Hophan-Nichols, Nick Burgard, Michael Lehmann
* This class Implements the WinCheck method to check for a win 
* using resolution with unification
*/
package game;

import java.util.*;

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
	public static boolean check(int x, int y, Board board)
	{
		Character player = board.theBoard[x][y];
		List<Character> winList = new ArrayList<Character>();
		
		//all values of x are player (entire column is player)
		for (int i = 0; i < 4; i++)
		{
			winList.add(board.theBoard[i][y]);
		}
		if (unify(winList, player))
			return true;
		
		winList.clear();
		//all values of x+1,y+1 and x-1,y-1 are player (within bounds)
		for (int i = x; i >= (x-3); i--)
		{
			if (i > y)
				winList.add(board.theBoard[x-i][12-(i-y)]);
			else if ((y-i) > 11)
				winList.add(board.theBoard[x-i][(y-i)-12]);
			else
				winList.add(board.theBoard[x-i][y-i]);
		}
		if (unify(winList, player))
			return true;

		winList.clear();
		//all values of x-1,y+1 and x+1,y-1 are player (within bounds)
		for (int i = x; i >= (x-3); i--)
		{
			if ((y+i) > 11)
				winList.add(board.theBoard[x-i][(y+i)-12]);
			else if (y < -i)
				winList.add(board.theBoard[x-i][12+(y+i)]);
			else
				winList.add(board.theBoard[x-i][y+i]);
		}
		if (unify(winList, player))
			return true;

		winList.clear();
		//y-3,y-2,y-1 are player
		for (int i = 0; i < 4; i++)
		{
			if (y-i < 0)
				winList.add(board.theBoard[x][12+(y-i)]);
			else
				winList.add(board.theBoard[x][y-i]);
		}
		if (unify(winList, player))
			return true;

		winList.clear();
		//y-2,y-1,y+1 are player
		for (int i = -1; i < 3; i++)
		{
			if (y-i < 0)
				winList.add(board.theBoard[x][12+(y-i)]);
			else if (y-i > 11)
				winList.add(board.theBoard[x][(y-i)-12]);
			else
				winList.add(board.theBoard[x][y-i]);
		}
		if (unify(winList, player))
			return true;

		winList.clear();
		//y-1,y+1,y+2 are player
		for (int i = -2; i < 2; i++)
		{
			if (y-i < 0)
				winList.add(board.theBoard[x][12+(y-i)]);
			else if (y-i > 11)
				winList.add(board.theBoard[x][(y-i)-12]);
			else
				winList.add(board.theBoard[x][y-i]);
		}
		if (unify(winList, player))
			return true;

		winList.clear();
		//y+1,y+2,y+3 are player
		for (int i = -3; i < 1; i++)
		{
			if (y-i > 11)
				winList.add(board.theBoard[x][(y-i)-12]);
			else
				winList.add(board.theBoard[x][y-i]);
		}
		if (unify(winList, player))
			return true;
		return false;
	}
	
	/**
	 * Uses unification to check rule arrays off winning array
	 * @param winCheck, winValue, i
	 * @return unified
	 */
	private static boolean unify(List<Character> winList, Character player)
	{
		if (Main.winVerbose)
		{
			//need to find a value for blank line prints, right now prints nothing which doesn't show what we want
			for (int i = 0; i < winList.size(); i++)
			{
				if (winList.get(i) == 0)
				{
					System.out.format("%c", '/');
				}
				System.out.format("%c", winList.get(i));
			}
			System.out.println();
		}
		//results are unified, win found
		if(winList.isEmpty())
		{
			if (Main.winVerbose)
			{
				System.out.println("Unification shows a win");
			}
			return true;
		}
		//value is unified, recursivly call unify on updated theta
		if(winList.get(0) == player)
		{
			winList.remove(0);
			return unify(winList, player);
		}
		if (Main.winVerbose)
		{
			System.out.println("No more values to unify");
		}
		//never retunred true, so false is returned
		return false;
	}
}
