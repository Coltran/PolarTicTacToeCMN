package game;

public class Main {
	public static void main(String[] args) {
		String[] choices = {"HvH", "HvC", "CvC"};
		String[] aiChoices = {},
                int choice = JOptionPane.showOptionDialog(
            	    null,
            	    "Pick an Option",
            	    JOptionPane.YES_NO_CANCEL_OPTION,
            	    JOptionPane.QUESTION_MESSAGE,
            	    null,
            	    choices,
            	    choices[0]);
            	    
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
            		default:
            			JOptionPane.showMessageDialog(
            				null,
            				"Invalid Option");
            	}
        }
}
