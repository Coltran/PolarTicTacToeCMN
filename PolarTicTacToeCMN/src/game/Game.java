package game;
//This is the game definition which can be used to keep track of the current game state an implement win checks
public class Game {
	//board keeps track of game state
	public Board board;
	public String player;
	
	//constructor
	public Game(String playerChoice) {
		board = new Board();
		player = playerChoice;
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
	
	public void printState() {
		for(int i = 0; i<4; i++) {
			for(int j = 0; j < 12; j++) {
				System.out.print(board.theBoard[i][j]);
			}
			System.out.println();
		}
	}
	
}
