package game;
//This is the game definition which can be used to keep track of the current game state an implement win checks
public class Game {
	//board keeps track of game state
	public Board board;
	public String player1, player2;
	
	//constructor
	public Game(String playerChoice1, String playerChoice2) {
		board = new Board();
		player1 = playerChoice1;
		player2 = playerChoice2;
	}

	//winCheck returns the winning player if a player has one, null otherwise
	private String winCheck(int x, int y) {
		String winningPlayer = null;
		//check for 4-in-a-row here
		return winningPlayer;
	}
	//move allows a player to make a move
	//returns true if player wins, false otherwise
	public boolean move(String player, int x, int y) {
		board.theBoard[x][y] = player;
		String winningPlayer = winCheck(x,y);
		if(winningPlayer != null) {
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
