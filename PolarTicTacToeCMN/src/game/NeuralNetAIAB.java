/**
* @author Coltran Hophan-Nichols, Nick Burgard, Michael Lehmann
* This class Implements the Neural Net AI with temperal-difference
*/
package game;

import java.util.Random;

public class NeuralNetAIAB implements AI{
	
	public Character player;//not used currently
	int numberExamples;//number of example games to run can be chosen by user
	private int movex;
	private int movey;
	double[][] hiddenWeights;
	double[] outputWeights;
	double[] inputNodes;
	double[] hiddenNodes;
	double outputNode;
	int[][] hiddenFreq;
	int[] outputFreq;
	
	/**
	 * 
	 */
	//constructor 
	public NeuralNetAIAB(Character playerChoice, int inNumberExamples) {
		player = playerChoice;
		numberExamples = inNumberExamples;
		//build neural net construct
		inputNodes = new double[50];
		hiddenWeights = new double[30][50];
		hiddenFreq = new int[30][50];
			//using 30 hidden nodes and 50 input nodes, we have 50 weights for each of the hidden nodes
		hiddenNodes = new double[30];
		outputWeights = new double[30]; // we have one weight coming into our ourput node for each hidden node
		outputFreq = new int[30];
		outputNode = 0;
		learn();
	}
	
	public boolean move(Game game) {
		movex = 0;//best move in x direction (there should always be a valid move when this is called)
		movey = 0;//best move in y direction ''
		if(game.moveNumber == 0) {//just pick a random move if we're going first
			Random generator = new Random(System.nanoTime());
			movex = generator.nextInt(4);
			movey = generator.nextInt(12);
		}
		else{
			int ply = 2;//number of plys to search
			int depth = ply*2;//moves to look ahead = ply * 2
			lookAhead(game.board, player, depth, game, -9999999999999999999.0, 999999999999999999.0);//call recursive lookahead function
			//lookahead will set movex and movey to best values
		}
		boolean win = game.move(player, movex, movey);//make move
		return win;//return if we won the game
	}
	
	//Training move function that allows for player input, unlike play move function
	public boolean trainingMove(Game game, Character movePlayer) {
		movex = 0;//best move in x direction (there should always be a valid move when this is called)
		movey = 0;//best move in y direction ''
		if(game.moveNumber == 0) {//just pick a random move if we're going first
			Random generator = new Random(System.nanoTime());
			movex = generator.nextInt(4);
			movey = generator.nextInt(12);
		}
		else{
			int ply = 2;//number of plys to search
			int depth = ply*2;//moves to look ahead = ply * 2
			lookAhead(game.board, movePlayer, depth, game, -9999999999999999999.0, 999999999999999999.0);//call recursive lookahead function
			//lookahead will set movex and movey to best values
		}
		boolean win = game.move(movePlayer, movex, movey);//make move
		return win;//return if we won the game
	}

	/**
	 * recursively checks ahead and returns best value
	 * sets movex and movey corrosponding to the best move found
	 * @param board
	 * @param thisPlayer
	 * @param depth
	 * @return
	 */
	private Double lookAhead(Board board, Character thisPlayer, int depth, Game game, double alpha, double beta) {
		boolean[][] moves = LegalMoves.Moves(board);//all available moves
		Double[][] values = new Double[4][12];//stores heuristic values for each available move
		int numberLegalMoves = 0;
		for(int i=0; i<4; i++) {
			for(int j=0; j<12; j++) {
				if(moves[i][j]==true) {//if we can move there...
					numberLegalMoves++;
					Board candidate = Board.clone(board);//copy the board to pass forward
					candidate.theBoard[i][j] = thisPlayer;//make move on copy
					//if we don't need to search any farther
					if(depth <= 1 || WinCheck.check(i, j, candidate) == true) {
						if(WinCheck.check(i, j, candidate)==true) {
							//if we can win
							if(candidate.theBoard[i][j] == player) {
								movex = i;
								movey = j;
								values[i][j] = 1.0;
								return values[i][j];//just return the winning move
							}
							//if our opponent won
							else {
								values[i][j] = -1.0;
							}
						}
						//if we need to evaluate the current board
						else {
							values[i][j] = evaluate(board);//call heuristic and save returned value
						}
					}
					//if we need to look farther ahead
					else {
						//determine next player
						Character otherPlayer = 'X';
						if(thisPlayer == 'X') {
							otherPlayer = 'O';
						}
						double n = evaluate(candidate);
						double alpha1 = alpha;
						double beta1 = beta;
						//if next player is max player
						if(thisPlayer != player) {
							//update alpha if necessary
							if(n > alpha) {
								alpha1 = n;
							}
							if(alpha1 >= beta) {
								if(Main.abVerbose) {
									System.out.format("min node: alpha = %d beta = %d Pruning\n", alpha, beta);
								}
								values[i][j] = n;//prune
							}
						}
						//if next player is min player
						else if(thisPlayer == player) {
							//update beta if necessary
							if(n < beta) {
								beta1 = n;
							}
							if(beta1 <= alpha) {
								if(Main.abVerbose) {
									System.out.format("max node: alpha = %d beta = %d Pruning\n", alpha, beta);
								}
								values[i][j] = n;//prune
							}
						}
						//if we didn't prune
						if(!((thisPlayer != player && alpha1 >= beta1) || (thisPlayer == player && beta1 <= alpha1))) {
							//recursive function call
							values[i][j] = lookAhead(candidate, otherPlayer, depth-1, game, alpha1, beta1);
						}
					}
				}
				//if we can't move there
				else {
					values[i][j] = null;
				}
			}
		}
		if(numberLegalMoves == 0) {
			return 0.0;
		}
		//if us (max player)
		if(thisPlayer == player) {
			//comparison breaks when using DOUBLE_MAX or any type of scientific notation (5.0E50)
			//for some reason Java does not like running a numerical comparison when one number is written like that
			//so just used a very small number here
			Double maxvalue = -500000000000000000000000000000000000000000.0;//-99999999999999.0;//maximum heuristic value of returned move
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					if(values[i][j] != null && values[i][j] > maxvalue) {
						maxvalue = values[i][j];
					}
				}
			}
			//break ties randomly
			int[] tiesx = new int[48];//will hold x coordinate for ties
			int[] tiesy = new int[48];//will hold y coordinate for ties
			int count = 0;//tracks number of ties
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					if(values[i][j] != null && values[i][j] == maxvalue) {//if we found one of the best moves
						tiesx[count] = i;//add x value to list
						tiesy[count] = j;//add corresponding y value to list
						count++;//increment count
					}
				}
			}
			Random generator = new Random(System.nanoTime());
			int randomChoice = generator.nextInt(count);//pick a random best move
			//make move
			movex = tiesx[randomChoice];
			movey = tiesy[randomChoice];
			return maxvalue;//we will choose best move for us
		}
		//otherwise it's our opponent (min player)
		else {
			//comparison breaks when using DOUBLE_MAX or any type of scientific notation (5.0E50)
			//for some reason Java does not like running a numerical comparison when one number is written like that
			//so just used a very large number here
			Double minvalue = 500000000000000000000000000000000000000000.0;//99999999999999.0;//minimum heuristic value of returned move
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					if(values[i][j] != null && values[i][j] < minvalue) {
						minvalue = values[i][j];
						movex = i;
						movey = j;
					}
				}
			}
			//break ties randomly
			int[] tiesx = new int[49];//will hold x coordinate for ties
			int[] tiesy = new int[49];//will hold y coordinate for ties
			int count = 0;//tracks number of ties
			for(int i=0; i<4; i++) {
				for(int j=0; j<12; j++) {
					if(values[i][j] != null && values[i][j] == minvalue) {//if we found one of the best moves
						tiesx[count] = i;//add x value to list
						tiesy[count] = j;//add coorosponding y value to list
						count++;//increment count
					}
				}
			}
			Random generator = new Random(System.nanoTime());
			int randomChoice = generator.nextInt(count);//pick a random best move
			//make move
			movex = tiesx[randomChoice];
			movey = tiesy[randomChoice];
			return minvalue;//assume opponent will choose the best move for them
		}
		
	}
	
	//returns an evaluation of current state using neural net.
	public Double evaluate(Board board) {
		//initialize first 48 input nods to values of board locations, last 2 to number moves made by x and by y
		//for each board space, 0 represents open, 1 represents we have moved there, -1 represents opponent has moved there
		int playerMoves=0, oppenentMoves=0;
		for(int i=0; i<48; i++)
		{
			if(board.theBoard[i/12][i%12] == null)
			{
				inputNodes[i] = 0;
			}
			else if(board.theBoard[i/12][i%12] == player)
			{
				inputNodes[i] = 1;
				playerMoves++;
			}
			else
			{
				inputNodes[i] = -1;
				oppenentMoves++;
			}
		}
		inputNodes[48] = playerMoves;
		inputNodes[49] = oppenentMoves;

		//for each hidden node, multiply each weight for that node by the value in the corresponding inputNode
			//sum these 50 results and divide by 50 this will give you the value for that hidden node
		for (int i=0; i<1500; i++)
		{
			int x = i/50;
			int y = i%50;
			hiddenNodes[x] += hiddenWeights[x][y] * inputNodes[y];
			if(y == 49)
			{
				hiddenNodes[x] = hiddenNodes[x]/50.0;
			}
		}
		
		//now repeat this process for the output node, summing over the 30 (output weights * corresponding hiddenNode)
			//divide by 30 and this will give you the value for the input game state, return this value.
		for (int i=0; i<30; i++)
		{
			outputNode += outputWeights[i] * hiddenNodes[i];
		}
		outputNode /= 30.0;

		return outputNode;
	}
	
	//play lots of games, training neural net on each game. 
	private void learn() {
		//initialize hiddenWeights and and outputWeights to random number between -3 and 3 excluding 0
		for(int j = 0; j < 30; j++){
			for(int k = 0; k < 50; k++){
				while(hiddenWeights[j][k] == 0){
					Random generator = new Random();
					hiddenWeights[j][k] = generator.nextInt(7) + (-3);
				}
			}
		}
		
		for(int l = 0; l < 30; l++){
			while(outputWeights[l] == 0){
				Random generator = new Random();
				outputWeights[l] = generator.nextInt(7) + (-3);
			}
		}
		
		double[][] hiddenWeightsPre1 = new double[30][50];
		double[] outputWeightsPre1 = new double[30];
		
		//run one game for each desired example
		for(int i=0; i<numberExamples; i++) {
			bboolean gameDone = false;
			Game trainingGame = new Game('X','O');//make a game
			
			//keep track of previous states
			hiddenWeightsPre1 = hiddenWeights;
			outputWeightsPre1 = outputWeights;
			gameDone = trainingMove(trainingGame, 'X');//use trainingMove so player can be specified

			while(!gameDone)
			{
				//keep track of previous states
				hiddenWeightsPre1 = hiddenWeights;
				outputWeightsPre1 = outputWeights;
				gameDone = trainingMove(trainingGame, 'O');//use trainingMove so player can be specified
	
				double gamma = 0.9; //discount factor, can probably just be kept at 1
				int reward = 0; //current reward, in book but not sure if we need
				double alpha = .8;
				//use mod to avoid nested for loops
				for(int j = 0; j<1500; j++)
				{
					int x = j/50;
					int y = j%50;
					hiddenFreq[x][y]++; //increment state frequency
					hiddenWeights[x][y] = hiddenWeightsPre1[x][y] + (alpha * hiddenFreq[x][y] * (reward + (gamma*hiddenWeights[x][y]) - hiddenWeightsPre1[x][y]));
				}
				for(int j = 0; j<30; j++)
				{
					outputFreq[j]++; //increment state frequency
					outputWeights[j] = outputWeightsPre1[j] + (alpha * outputFreq[j] * (reward + (gamma*outputWeights[j]) - outputWeightsPre1[j]));
				}
				
				//Do a second move with other player
				//keep track of previous states
				hiddenWeightsPre1 = hiddenWeights;
				outputWeightsPre1 = outputWeights;
				gameDone = trainingMove(trainingGame, 'X');
				
				//use mod to avoid nested for loops
				for(int j = 0; j<1500; j++)
				{
					int x = j/50;
					int y = j%50;
					hiddenFreq[x][y]++; //increment state frequency
					hiddenWeights[x][y] = hiddenWeightsPre1[x][y] + (alpha * hiddenFreq[x][y] * (reward + (gamma*hiddenWeights[x][y]) - hiddenWeightsPre1[x][y]));
				}
				for(int j = 0; j<30; j++)
				{
					outputFreq[j]++; //increment state frequency
					outputWeights[j] = outputWeightsPre1[j] + (alpha * outputFreq[j] * (reward + (gamma*outputWeights[j]) - outputWeightsPre1[j]));
				}
			}
		}
	}

}
