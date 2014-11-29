/**
 * 
 */
package game;

/**
 * @author Coltran
 *
 */
public class NearestNeighborAI {
	
	private Character player;
	private Board[][] examples;
	boolean result[];
	int numberExamples;
	int similarity[];

	/**
	 * 
	 */
	public NearestNeighborAI(Character playerChoice, int inNumberExamples) {
		player = playerChoice;
		examples = new Board[inNumberExamples][48];
		numberExamples = inNumberExamples;
		similarity = new int[inNumberExamples];
		result = new boolean[inNumberExamples];
		learn();
	}
	//true means win false means loose
	public boolean predict(Board board) {
		int moveNumber = 0;
		for(int x=0; x<4; x++) {
			for(int y=0; y<12; y++) {
				if(board.theBoard[x][y] != null) {
					moveNumber++;
				}
			}
		}
		for(int example=0; example<numberExamples; example++) {
			for(int x=0; x<4; x++) {
				for(int y=0; y<12; y++) {
					if(board.theBoard[x][y] == examples[example][moveNumber].theBoard[x][y]) {
						similarity[example]++;
					}
				}
			}
		}
		int maxSimilarity = 0;
		int maxLocation = 0;
		for(int example=0; example<numberExamples; example++) {
			if(similarity[example]>maxSimilarity) {
				maxSimilarity = similarity[example];
				maxLocation = example;
			}
		}
		return result[maxLocation];
	}
	
	private void learn() {
		//play lots of games, saving every board state and classifying each game as win or loss
		for(int i=0; i<numberExamples; i++) {
			Game trainingGame = new Game('x','o');
			HeuristicAI playerX = new HeuristicAI('x');
			HeuristicAI playerO = new HeuristicAI('o');
			if(player == 'x'){
				//until game is done
				while(trainingGame.done == false) {
					playerX.move(trainingGame);
					if(trainingGame.winningPlayer == 'x') {
						result[i] = true;
					}
					else if(trainingGame.done == false) {
						playerO.move(trainingGame);
						if(trainingGame.winningPlayer == 'y') {
							result[i] = false;
						}
					}
				}
			}
		}
	}

}
