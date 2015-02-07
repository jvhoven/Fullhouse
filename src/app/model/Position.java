package app.model;

import java.util.ArrayList;
import java.util.Map;
import src.rating.Rating;

/**
 * Position
 * 
 * This class is the cross reference between the participant class and the 
 * session class.
 * 
 * For more information to which data it contains
 * @see src.Keys
 * 
 * @version 0.0.1
 * @author Project Group 9
 */
 public class Position extends Helper {
    
    private ArrayList<Participant> participants;
    private Participant sessionWinner;   
        
    
    public Position(Map<String, Object> params) {
        super(params);
    }

}
    
