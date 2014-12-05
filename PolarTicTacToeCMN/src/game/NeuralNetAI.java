/**
 * 
 */
package game;

import java.util.Random;

/**
 * @author Coltran
 * This class Implements the Heuristic based AI to play Polar Tic Tac Toe
 * Instantiate with HeuristicAI name = new HeuristicAI(playerChoice) 
 * where playerChoice is 'X' or 'O'
 * To have the AI make a move: name.move(game) 
 * where game is a Game
 */
public class NeuralNetAI implements AI {
	
	private Character player;//not used currently
	private Character opponent;
	private Board[][] examples;//holds all of our examples to compare against
	Character result[];//holds the results for all the example games (x,o or d)
	int numberExamples;//number of example games to run can be chosen by user
	int similarity[];//holds the similarity to each example at the same turn level
	private int movex;
	private int movey;

	/**
	 * 
	 */
	public NeuralNetAI(Character playerChoice, int inNumberExamples) {
		player = playerChoice;
		examples = new Board[inNumberExamples][48];//48 is maximum possible moves in a game
		numberExamples = inNumberExamples;
		similarity = new int[inNumberExamples];
		result = new Character[inNumberExamples];
		opponent = 'X';
		if(playerChoice == 'X') {
			opponent = 'O';
		}
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
			//lookAhead(game.board, player, depth);//call recursive lookahead function
			//lookahead will set movex and movey to best values
		}
		boolean win = game.move(player, movex, movey);//make move
		return win;//return if we wone the game
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
					result[i] = 'x';//set result to x win if x wone
				}
				//if the game is not done
				else if(trainingGame.done == false) {
					playerO.move(trainingGame);//seccond player moves
					examples[i][turnNumber] = Board.clone(trainingGame.board);//save game board
					turnNumber++;//next turn
					if(trainingGame.winningPlayer == 'y') {
						result[i] = 'y';//set result to x win if x won
					}
				}
				if(trainingGame.done && trainingGame.winningPlayer == null) {
					result[i] = 'd';//set result to draw if game is over and nobody won
				}
			}
		}
	}
}
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