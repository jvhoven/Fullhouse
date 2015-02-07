package src.rating;

import app.model.Participant;
import java.util.ArrayList;

/**
 *
 * @author Project group 9
 */
public class Rating {
   
    public static int[] updateScore(Participant participant, ArrayList<Participant> opponents, boolean won) {
       
        /*
        * This will hold the return value
        *    0 - total_score
        *    1 - win/loss depending if youve won
        *    2 - new rating
        */
        int[] newValues = new int[3];
        
        int totalScore = 0;
        
        /*
        * We need to get all the opponents rating and add that to our 
        * total_score this is the first part of the formula.
        */
        for(Participant opponent : opponents){
            totalScore += opponent.getUser().getInt("rating");
        }
        
        int newScore = participant.getUser().getInt("total_score") + (totalScore / opponents.size());
        newValues[0] = newScore;
        
        /*
        * Depending on if youve won, youll either gain a win or a loss and itll 
        * then calculate your new score accordingly.
        */
        if(won) {
            int newWin = participant.getUser().getInt("wins") + 1;
            newValues[1] = newWin;

            newValues[2] = calculate(newScore, newWin, participant.getUser().getInt("losses"));
        } else {
            int newLoss = participant.getUser().getInt("losses") + 1;
            newValues[1] = newLoss;
            
            newValues[2] = calculate(newScore, participant.getUser().getInt("wins"), newLoss);
        }
        
        return newValues;
    }
    
    /*
    * The formula to calculate a new ELO rating
    */
    private static int calculate(int totalScore, int win, int loss) {
        return (totalScore + (400*(win-loss)))/(win+loss);
    }
    
}
