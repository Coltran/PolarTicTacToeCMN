/**
 * 
 */
package game;

import java.util.Random;

/**
 * @author Coltran
 *
 */
public class NeuralNetAI implements AI{
	
	public Character player;//not used currently
	private Character opponent;
	int numberExamples;//number of example games to run can be chosen by user
	private int movex;
	private int movey;
	int[][] hiddenWeights;
	int[] outputWeights;
	double[] inputNodes;
	double[] hiddenNodes;
	double outputNode;
	
	/**
	 * 
	 */
	//constructor 
	public NeuralNetAI(Character playerChoice, int inNumberExamples) {
		player = playerChoice;
		numberExamples = inNumberExamples;
		opponent = 'X';
		if(playerChoice == 'X') {
			opponent = 'O';
		}
		//build neural net construct
		inputNodes = new double[50];
		hiddenWeights = new int[30][50];
			//using 30 hidden nodes and 50 input nodes, we have 50 weights for each of the hidden nodes
		hiddenNodes = new double[30];
		outputWeights = new int[30]; // we have one weight coming into our ourput node for each hidden node
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
			lookAhead(game.board, player, depth, game);//call recursive lookahead function
			//lookahead will set movex and movey to best values
		}
		boolean win = game.move(player, movex, movey);//make move
		return win;//return if we won the game
	}
	
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
			lookAhead(game.board, movePlayer, depth, game);//call recursive lookahead function
			//lookahead will set movex and movey to best values
		}
		boolean win = game.move(movePlayer, movex, movey);//make move
		return win;//return if we won the game
	}

	/**
	 * recursively checks ahead and returns best heuristic value
	 * sets movex and movey corrosponding to the best move found
	 * @param board
	 * @param thisPlayer
	 * @param depth
	 * @return
	 */
	private Integer lookAhead(Board board, Character thisPlayer, int depth, Game game) {
		boolean[][] moves = LegalMoves.Moves(board);//all available moves
		Integer[][] values = new Integer[4][12];//stores heuristic values for each available move
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
								values[i][j] = 1;
								return values[i][j];//just return the winning move
							}
							//if our opponent won
							else {
								values[i][j] = -1;
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
						//recursive function call
						values[i][j] = lookAhead(candidate, otherPlayer, depth-1, game);
					}
				}
				//if we can't move there
				else {
					values[i][j] = null;
				}
			}
		}
		if(numberLegalMoves == 0) {
			return 0;
		}
		//if us (max player)
		if(thisPlayer == player) {
			int maxvalue = -999999999;//maximum heuristic value of returned move
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
			return maxvalue;//we will choose best move for us
		}
		//otherwise it's our opponent (min player)
		else {
			int minvalue = 999999999;//minimum heuristic value of returned move
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
	public int evaluate(Board board) {
		//TODO initialize first 48 input nods to values of board locations, last 2 to number moves made by x and by y
		//for each board space, 0 represents open, 1 represents we have moved there, -1 represents opponent has moved there
		//number moves made by y should be negative?
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

		//TODO for each hidden node, multiply each weight for that node by the value in the corresponding inputNode
			//sum these 50 results and divide by 50? this will give you the value for that hidden node
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
		
		//TODO now repeat this process for the output node, summing over the 30 (output weights * corresponding hiddenNode)
			//divide by 30? and this will give you the value for the input game state, return this value.
		for (int i=0; i<30; i++)
		{
			outputNode += outputWeights[i] * hiddenNodes[i];
		}
		outputNode /= 30.0;

		return (int)outputNode;
	}
	
	//play lots of games, training neural net on each game. 
	private void learn() {
		//TODO initialize hiddenWeights and and outputWeights to random number between -3 and 3 excluding 0
		//loop once for each desired example
		for(int j = 0; j <= 30; j++){
			for(int k = 0; j <= 50; j++){
				while(hiddenWeights[j][k] == 0){
					Random generator = new Random();
					hiddenWeights[j][k] = generator.nextInt(7) + (-3);
				}
			}
		}
		
		for(int l = 0; l <= 30; l++){
			while(outputWeights[l] == 0){
				Random generator = new Random();
				outputWeights[l] = generator.nextInt(7) + (-3);
			}
		}
		
		int[][] hiddenWeightsPre1 = new int[30][50], hiddenWeightsPre2 = new int[30][50];;
		int[] outputWeightsPre1 = new int[30], outputWeightsPre2 = new int[30];
		double[] inputNodesPre1 = new double[50], inputNodesPre2 = new double[50];
		double[] hiddenNodesPre1 = new double[30], hiddenNodesPre2 = new double[30];
		double outputNodePre1 = 0, outputNodePre2 = 0;
		
		
		for(int i=0; i<numberExamples; i++) {
			boolean gameDone = false;
			Game trainingGame = new Game('X','O');//make a game
			//TODO call the move method repeatedly but with alternating players to have the net play the game. 
				//Before each move, make a copy of all the weights, after each move make a copy of all the node values (including output).
				//we only need to save these weights and values going back two moves.
			
			hiddenWeightsPre2 = hiddenWeightsPre1;
			hiddenWeightsPre1 = hiddenWeights;
			outputWeightsPre2 = outputWeightsPre1;
			outputWeightsPre1 = outputWeights;
			trainingMove(trainingGame, 'X');//Will have to find a way to specify player
			inputNodesPre2 = inputNodesPre1;
			inputNodesPre1 = inputNodes;
			hiddenNodesPre2 = hiddenNodesPre1;
			hiddenNodesPre1 = hiddenNodes;
			outputNodePre2 = outputNodePre1;
			outputNodePre1 = outputNode;
			
			while(!gameDone)
			{
				//Do a second move with other player, will have to figure that out.
				hiddenWeightsPre2 = hiddenWeightsPre1;
				hiddenWeightsPre1 = hiddenWeights;
				outputWeightsPre2 = outputWeightsPre1;
				outputWeightsPre1 = outputWeights;
				trainingMove(trainingGame, 'O');//Will have to find a way to specify player
				inputNodesPre2 = inputNodesPre1;
				inputNodesPre1 = inputNodes;
				hiddenNodesPre2 = hiddenNodesPre1;
				hiddenNodesPre1 = hiddenNodes;
				outputNodePre2 = outputNodePre1;
				outputNodePre1 = outputNode;
				
				//TODO after each move other than the first, update all Weights
					//to do this, for each node, subtract the most recent value from the previous value (this is the temporal difference part)
						//for each weight coming into the node, multiply the difference by the inputs contribution and by the learning rate. 
						//the contribution (i'm not sure about this part as it's the gradient stuff) is something like
						// the most recent input to that weight times the weight, or maybe how this changed from the previous time
				//I think that's it. the lookahead, etc shouldn't have to be changed
				
				//Other way around, subtract the previous value from the most recent value
				//Need to not do this after first move somehow
				int gamma = 1; //discount factor, can probably just be kept at 1
				int reward = 0; //current reward, in book but not sure if we need
				int alpha = 1; //learning rate, not sure what we want here
				int contrib = 1; //input contribution, I thought this was just an integer then incremented every time a state was hit?
				for(int j = 0; j<1500; j++)
				{
					int x = j/50;
					int y = j%50;
					hiddenWeights[x][y] = hiddenWeightsPre1[x][y] + (alpha * contrib * (reward + (gamma*hiddenWeights[x][y]) - hiddenWeightsPre1[x][y]));
				}
				for(int j = 0; j<30; j++)
				{
					outputWeights[j] = outputWeightsPre1[j] + (alpha * contrib * (reward + (gamma*outputWeights[j]) - outputWeightsPre1[j]));
				}
				
				//Do a second move with other player, will have to figure that out.
				hiddenWeightsPre2 = hiddenWeightsPre1;
				hiddenWeightsPre1 = hiddenWeights;
				outputWeightsPre2 = outputWeightsPre1;
				outputWeightsPre1 = outputWeights;
				trainingMove(trainingGame, 'X');//Will have to find a way to specify player
				inputNodesPre2 = inputNodesPre1;
				inputNodesPre1 = inputNodes;
				hiddenNodesPre2 = hiddenNodesPre1;
				hiddenNodesPre1 = hiddenNodes;
				outputNodePre2 = outputNodePre1;
				outputNodePre1 = outputNode;
				
				for(int j = 0; j<1500; j++)
				{
					int x = j/50;
					int y = j%50;
					hiddenWeights[x][y] = hiddenWeightsPre1[x][y] + (alpha * contrib * (reward + (gamma*hiddenWeights[x][y]) - hiddenWeightsPre1[x][y]));
				}
				for(int j = 0; j<30; j++)
				{
					outputWeights[j] = outputWeightsPre1[j] + (alpha * contrib * (reward + (gamma*outputWeights[j]) - outputWeightsPre1[j]));
				}
			}
		}
	}

}

//if U[currentBoard] does not exist then U[currentBoard] = reward
//if s (previous state) exists then 
	//N[s]++
	//U[s] = U[s] + alpha*(N[s])*(r + gamma*U[s`] - U[s])
//if s' is end state then
	//s, a, r = null
//else
	//s = currentBaord
	//a = pi[currenBoard]
	//r = reward



///**
// * 
// */
//package game;
//
//import java.util.Random;
//
///**
// * @author Coltran
// * This class Implements the Heuristic based AI to play Polar Tic Tac Toe
// * Instantiate with HeuristicAI name = new HeuristicAI(playerChoice) 
// * where playerChoice is 'X' or 'O'
// * To have the AI make a move: name.move(game) 
// * where game is a Game
// */
//public class NeuralNetAI implements AI {
//	
//	private Character player;//not used currently
//	private Character opponent;
//	private Board[][] examples;//holds all of our examples to compare against
//	Character result[];//holds the results for all the example games (x,o or d)
//	int numberExamples;//number of example games to run can be chosen by user
//	int similarity[];//holds the similarity to each example at the same turn level
//	private int movex;
//	private int movey;
//
//	/**
//	 * 
//	 */
//	public NeuralNetAI(Character playerChoice, int inNumberExamples) {
//		player = playerChoice;
//		examples = new Board[inNumberExamples][48];//48 is maximum possible moves in a game
//		numberExamples = inNumberExamples;
//		similarity = new int[inNumberExamples];
//		result = new Character[inNumberExamples];
//		opponent = 'X';
//		if(playerChoice == 'X') {
//			opponent = 'O';
//		}
//		learn();
//	}
//	
//	public boolean move(Game game) {
//		movex = 0;//best move in x direction (there should always be a valid move when this is called)
//		movey = 0;//best move in y direction ''
//		if(game.moveNumber == 0) {//just pick a random move if we're going first
//			Random generator = new Random(System.nanoTime());
//			movex = generator.nextInt(4);
//			movey = generator.nextInt(12);
//		}
//		else{
//			int ply = 2;//number of plys to search
//			int depth = ply*2;//moves to look ahead = ply * 2
//			//lookAhead(game.board, player, depth);//call recursive lookahead function
//			//lookahead will set movex and movey to best values
//		}
//		boolean win = game.move(player, movex, movey);//make move
//		return win;//return if we wone the game
//	}
//	
//	
//	//play lots of games, saving every board state and classifying each game as win or loss
//	private void learn() {
//		//loop once for each desired example
//		for(int i=0; i<numberExamples; i++) {
//			Game trainingGame = new Game('x','o');//make a game
//			//Heuristic based AIs to play the game
//			HeuristicAI playerX = new HeuristicAI('x');
//			HeuristicAI playerO = new HeuristicAI('o');
//			int turnNumber = 0;//tracks turn for saving intermediate game boards
//			//until game is done
//			while(trainingGame.done == false) {
//				playerX.move(trainingGame);//first player moves
//				examples[i][turnNumber] = Board.clone(trainingGame.board);//save game board
//				turnNumber++;//next turn
//				if(trainingGame.winningPlayer == 'x') {
//					result[i] = 'x';//set result to x win if x wone
//				}
//				//if the game is not done
//				else if(trainingGame.done == false) {
//					playerO.move(trainingGame);//seccond player moves
//					examples[i][turnNumber] = Board.clone(trainingGame.board);//save game board
//					turnNumber++;//next turn
//					if(trainingGame.winningPlayer == 'y') {
//						result[i] = 'y';//set result to x win if x won
//					}
//				}
//				if(trainingGame.done && trainingGame.winningPlayer == null) {
//					result[i] = 'd';//set result to draw if game is over and nobody won
//				}
//			}
//		}
//	}
//}




//last state is last board state
//the neural net is huge then, every state and every middle state yada yada
//one hidden layer, but at least 20 nodes in the hidden layer, maybe more
//train once, not every time we run?

//Some websites

//http://en.literateprograms.org/Perceptron_%28Java%29
//https://code.google.com/p/aima-java/		go to TD code
//http://aima-java.googlecode.com/svn/trunk/aima-all/release/aima3ejavademos.html		has tictactoe minmax and AlphaBeta
//http://aispace.org/neural/help/tutorial4.shtml


//loop and find all legal moves
//These moves are the input to the current state in the neural net?
//Whichever move is best would be weighted 1, rest would be -1.
	//Maybe have some at 0? But no reason to do that

//Run TD function on any play
//Only have a reward on win, loss, or draw
//TD is recursive
//can enter any state to start with, will go to end.
	//Opposing playing will return multiple different results to any state, this should be factored in automatically after enough tests

//Input is current state, outputs are all possible legal moves?



//input percept indicating current state s` and reward r`
//persistent
	//pi is fixed policy?
	//U[] is table of utilities, initially empty (UTILITY IS WEIGHT!!)
	//N[] is table of frequencies, initially zero
	//s, a, r are previous state, previous action, and previous reward, initially null
//if s` is new then U[s`]=r`
//if s is not null then
	//N[s]++
	//U[s] = U[s] + alpha*(N[s])*(r + gamma*U[s`] - U[s])
//if s`.TERMINAL? then s, a, r = null else s, a, r = s`,pi[s`],r` (end state)
//return a

//1, 0, -1 are reward options
//gamma is discount factor set to 1 for now, never more then 1?
//alpha is learning rate, don't know what to set as


//input current board and reward (0?)
//if U[currentBoard] does not exist then U[currentBoard] = reward
//if s (previous state) exists then 
	//N[s]++
	//U[s] = U[s] + alpha*(N[s])*(r + gamma*U[s`] - U[s])
//if s' is end state then
	//s, a, r = null
//else
	//s = currentBaord
	//a = pi[currenBoard]
	//r = reward
