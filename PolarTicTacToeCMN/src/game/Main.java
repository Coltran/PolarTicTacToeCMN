package game;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		boolean done = false;  //Boolean for while loop below
		
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
		String[] aiChoices = {"", "", "", "", ""},
		
                int choice = JOptionPane.showOptionDialog(
            	    null,
            	    "Pick an Option",
            	    JOptionPane.YES_NO_CANCEL_OPTION,
            	    JOptionPane.QUESTION_MESSAGE,
            	    null,
            	    choices,
            	    choices[0]);
            	
            	while(!done){
            	    switch(choice){
            		    case 0:
            			    //Start game
            			    break;
            		    case 1:
            			    int aiOption = JOptionPane.showOptionDialog(
            				    null,
            				    "Choose an AI Opponent",
            				    JOptionPane.YES_NO_CANCEL_OPTION,
            				    JOptionPane.QUESTION_MESSAGE,
            				    null,
            				    aiChoices,
            				    aiChoices[0]);
            				
            		            switch(aiOption){
            		        	    case 0:
            		        		
            		        		    break;
            		        	    case 1:
            		        		
            		        		    break;
            		        	    default:
            		        		    JOptionPane.showMessageDialog(
            		        			    null,
            		        			    "Invalid Option");
            		            }
            			    break;
            		    case 2:
            			    //Choose two AIs to compete against each other.
            			    break;
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
