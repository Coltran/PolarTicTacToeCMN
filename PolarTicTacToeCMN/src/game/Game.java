package game;
//This is the game definition which can be used to keep track of the current game state an implement win checks
public class Game {
	//board keeps track of game state
	public Board board;
	public Character player1, player2;
	public boolean done;
	public Character winningPlayer;
	
	//constructor
	public Game(Character playerChoice1, Character playerChoice2) {
		board = new Board();
		player1 = playerChoice1;
		player2 = playerChoice2;
		done = false;
		winningPlayer = null;
	}

	//move allows a player to make a move
	//returns true if player wins, false otherwise
	public boolean move(Character player, int x, int y) {
		board.theBoard[x][y] = player;
		boolean win = WinCheck.check(x,y, board);
		if(win) {
			done = true;
			return true;
		}
		else {
			return false;
		}
	}
	//resets the game
	public void resetGame() {
		for(int i=0; i<4; i++) {
			for(int j=0; j<12; j++) {
				board.theBoard[i][j] = null;
			}
		}
	}
	
	// Prints the board state
	public void printState() {
		String[] row = {"A", "B", "C", "D"};
		String[] column = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		
		System.out.print("  ");
		
		for(int i = 0; i < 12; i++) {
			System.out.print(column[i] + "   ");
		}
		
		System.out.println();
		
		for(int i = 0; i<4; i++) {
			System.out.print(row[i] + " ");
			
			for(int j = 0; j < 12; j++) {
				System.out.print(board.theBoard[i][j]);
			}
			
			System.out.println();
		}
	}
	
}
