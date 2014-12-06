package game;
//This is the game definition which can be used to keep track of the current game state an implement win checks
public class Game {
	//board keeps track of game state
	public Board board;
	public Character player1, player2;
	public boolean done;
	public Character winningPlayer;
	public int moveNumber;
	
	//constructor
	public Game(Character playerChoice1, Character playerChoice2) {
		board = new Board();
		player1 = playerChoice1;
		player2 = playerChoice2;
		done = false;
		winningPlayer = null;
		moveNumber = 0;
		resetBoard();
	}

	//move allows a player to make a move
	//returns true if player wins, false otherwise
	public boolean move(Character player, int x, int y) {
		board.theBoard[x][y] = player;
		moveNumber++;
		boolean win = WinCheck.check(x,y, board);
		if(win == true || moveNumber == 48) {
			done = true;
			return true;
		}
		else {
			return false;
		}
	}
	public void resetGame() {
		resetBoard();
		done = false;
		winningPlayer = null;
		moveNumber = 0;
	}
	//resets the game
	public void resetBoard() {
		for(int i=0; i<4; i++) {
			for(int j=0; j<12; j++) {
				board.theBoard[i][j] = 0;
			}
		}
	}
	
	// Prints the board state
	public void printState() {
		String[] row = {"A", "B", "C", "D"};
		String[] column = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		
		System.out.print("  ");
		
		for(int i = 0; i < 12; i++) {
			System.out.format("%3s", column[i]);
		}
		
		System.out.println();
		
		for(int i = 0; i<4; i++)
		{
			System.out.format("%2s", row[i]);
			
			for(int j = 0; j < 12; j++)
			{
				if(board.theBoard[i][j] == 0) {
					System.out.format("%3c", ' ');
				}
				else {
					System.out.format("%3c", board.theBoard[i][j]);
				}
			}
			
			System.out.println();
		}
	}
	
}
