/**
 * 
 */
package game;

/**
 * @author Coltran
 *
 */
public class NearestNeighborAI implements AI {
	
	public Character player;//not used currently
	private Board[][] examples;//holds all of our examples to compare against
	Character result[];//holds the results for all the example games (x,o or d)
	int numberExamples;//number of example games to run can be chosen by user
	int similarity[];//holds the similarity to each example at the same turn level

	/**
	 * 
	 */
	//constructor 
	public NearestNeighborAI(Character playerChoice, int inNumberExamples) {
		player = playerChoice;
		examples = new Board[inNumberExamples][48];//48 is maximum possible moves in a game
		numberExamples = inNumberExamples;
		similarity = new int[inNumberExamples];
		result = new Character[inNumberExamples];
		learn();
	}
	//returns a prediction (x,o or d) for which player will win (or a draw)
	public Character predict(Board board) {
		int moveNumber = 0;
		//count number of moves made so far
		for(int x=0; x<4; x++) {
			for(int y=0; y<12; y++) {
				if(board.theBoard[x][y] != null) {
					moveNumber++;//for each move made increment moveNumber
				}
			}
		}
		//for each example (we will look only at the corresponding move number from each example)
		for(int example=0; example<numberExamples; example++) {
			similarity[example] = 0;//set to 0 initially
			for(int x=0; x<4; x++) {
				for(int y=0; y<12; y++) {
					if(board.theBoard[x][y] == examples[example][moveNumber].theBoard[x][y]) {
						similarity[example]++;//increment similarity for each board position that matches
					}
				}
			}
		}
		int maxSimilarity = 0;//maximum similarity value found so far
		int maxLocation = 0;//location of most similar so far
		//for each example tested
		for(int example=0; example<numberExamples; example++) {
			//if we find the largest similarity so far
			if(similarity[example]>maxSimilarity) {
				maxSimilarity = similarity[example];//set maxSimilarity to new max
				maxLocation = example;//set max location to location of new max
			}
		}
		return result[maxLocation];//return result of most similar (nearest) example
	}
	//play lots of games, saving every board state and classifying each game as win or loss
	private void learn() {
		//loop once for each desired example
		for(int i=0; i<numberExamples; i++) {
			Game trainingGame = new Game('x','o');//make a game
			//Heuristic based AIs to play the game
			HeuristicAI playerX = new HeuristicAI('x');
			HeuristicAI playerO = new HeuristicAI('o');
			int turnNumber = 0;//tracks turn for saving intermediate game boards
			//until game is done
			while(trainingGame.done == false) {
				playerX.move(trainingGame);//first player moves
				examples[i][turnNumber] = Board.clone(trainingGame.board);//save game board
				turnNumber++;//next turn
				if(trainingGame.winningPlayer == 'x') {
					result[i] = 'x';//set result to x win if x won
				}
				//if the game is not done
				else if(trainingGame.done == false) {
					playerO.move(trainingGame);//second player moves
					examples[i][turnNumber] = Board.clone(trainingGame.board);//save game board
					turnNumber++;//next turn
					if(trainingGame.winningPlayer == 'y') {
						result[i] = 'y';//set result to y win if y won
					}
				}
				if(trainingGame.done && trainingGame.winningPlayer == null) {
					result[i] = 'd';//set result to draw if game is over and nobody won
				}
			}
		}
	}
	@Override
	public boolean move(Game game) {
		// TODO Auto-generated method stub
		return false;
	}
}
