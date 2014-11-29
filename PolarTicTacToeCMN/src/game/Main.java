package game;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		boolean done = false;  //Boolean for while loop below
		
		String player = null;
		Game game = new Game(player);
		
		/* 
		 * Choices for game include:
		 * Human vs. Human
		 * Human vs. Computer
		 * Computer vs. Copmuter
		 * or the user can quit.
		 */
		String[] choices = {"HvH", "HvC", "CvC", "Quit"};
		
		/*
		 * Choices of AI the user game play against or have compete against each other include:
		 * With Alpha-Beta Pruning,
		 * Without Alpha-Beta Pruning,
		 * Temporal Differenct Neural Network
		 * Heuristic Function
		 * Naive Bayes/Nearest Neighbor/Decision Tree
		 */
		String[] aiChoices = {"Basic Game Heuristic", "Naive Bayes/Decision Tree/Nearest Neighbor Heuristic",
		"Temporal Difference Neural Network", "Alpha-Beta Pruning?"};
		
		/*
		 * Player choose either be either X or O
		 */
		String[] playerVariable = {"x", "o"};
		
                int choice = JOptionPane.showOptionDialog(
            	    null,
            	    "Pick an Option",
            	    "Pick an Option",
            	    JOptionPane.YES_NO_CANCEL_OPTION,
            	    JOptionPane.QUESTION_MESSAGE,
            	    null,
            	    choices,
            	    choices[0]);
            	
            	while(!done){
            		
            	    switch(choice){
            	    	    // Human vs. Human
            		    case 0:
            		    	    int playerVariables = JOptionPane.showOptionDialog(
            		    	    	null,
            		    	    	"Choose X or O",
            		    	    	"Choose X or O",
            		    	    	JOptionPane.YES_NO_CANCEL_OPTION,
            		    	    	JOptionPane.QUESTION_MESSAGE,
            		    	    	null,
            		    	    	playerVariable,
            		    	    	playerVariable[0]);
            		    	    	
            		    	    switch(playerVariables){
            		    	    	case 0:
            		    	    		game.player = "x"; // Need to create a player class with method Variable and some others...
            		    	    		// Create player 2 to be "o"
            		    	    		break;
            		    	    	case 1:
            		    	    		game.player = "o";
            		    	    		// Create player 2 to be "x"
            		    	    		break;
            		    	    	default:
            		    	    	    JOptionPane.showMessageDialog(
            		        		        null,
            		        		        "Invalid Option");
            		        		    break;
            		    	    }
            			    game.resetGame();  // Start new game
            			    game.printState(); // Print the blank board
            			    
            			    break;
            			    
            	            // Human vs. Computer
            		    case 1:
            			    int aiOption = JOptionPane.showOptionDialog(
            				    null,
            				    "Choose an AI Opponent",
            				    "Choose an AI Opponent",
            				    JOptionPane.YES_NO_CANCEL_OPTION,
            				    JOptionPane.QUESTION_MESSAGE,
            				    null,
            				    aiChoices,
            				    aiChoices[0]);
            				
            		            switch(aiOption){
            		            	    // Heuristic created from excercise 5.9 in book
            		        	    case 0:
            		        		
            		        		    break;
            		        		    
            		        	    // Naive Bayes/Decision Tree/Nearest Neighbor Heuristic whichever we choose to implement
            		        	    case 1:
            		        		
            		        		    break;
            		        	    // temporal Difference Neural Network Heuristic  
            		                    case 2:
            		                    	
            		                    	    break;
            		                    // Implement Alpha-Beta Pruning to heuristic function
            		                    case 3: 
            		                    	
            		                    	    break;
            		        	    default:
            		        		    JOptionPane.showMessageDialog(
            		        			    null,
            		        			    "Invalid Option");
            		            }
            			    break;
            			    
            		    // Computer vs. Computer
            		    case 2:
            			    //Choose two AIs to compete against each other.
            			    break;
            			    
            	            // Quit
            		    case 3:
            			    done = true;
            		    	    break;
            		    default:
            			    JOptionPane.showMessageDialog(
            				    null,
            				    "Invalid Option");
            	    }
            	}  
        }
}
