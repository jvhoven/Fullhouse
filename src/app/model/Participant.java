package app.model;

import java.util.Map;

/**
 * Participant
 * 
 * This class is related to the User class, as it is in fact
 * a slightly advanced version of the class whereas this class contains
 * which tournament class the User has submitted a participation in.
 * 
 * For more information to which data it contains
 * @see src.Keys
 * 
 * @version 0.0.1
 * @author Project Group 9
 */
 public class Participant extends Helper {
     
    public Participant(Map<String, Object> params) {
        super(params);
    }

    public Object[] getInfo(){
        return new Object[] { this.getUser().getInt("id") + "-" + this.getTournament().getInt("id"), this.getUser().get("name"), this.getTournament().get("name"), this.getBoolean("has_paid") };
    }
}
    
