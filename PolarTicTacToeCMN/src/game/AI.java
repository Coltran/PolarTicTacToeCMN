/**
* @author Coltran Hophan-Nichols, Nick Burgard, Michael Lehmann
*
*/
package game;
//interface for all our AIs to impliment
public interface AI {

	/**
	 * allows any AI to be called on to make a move on a given game at any state
	 */
	public boolean move(Game game);

}
