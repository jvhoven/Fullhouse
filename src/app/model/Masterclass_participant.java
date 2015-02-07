package app.model;

import java.util.Map;

/**
 * Participant
 * 
 * This class is related to the User class, as it is in fact
 * a slightly advanced version of the class whereas this class contains
 * which master class the User has submitted a participation in.
 * 
 * For more information to which data it contains
 * @see src.Keys
 * 
 * @version 0.0.1
 * @author Project Group 9
 */
 public class Masterclass_participant extends Helper {
    
    public Masterclass_participant(Map<String, Object> params) {
        super(params);
    } 

    public Object[] getInfo(){
        return new Object[] { this.getUser().getInt("id") + "-" + this.getMasterclass().getInt("id"), this.getUser().get("name"), this.getMasterclass().get("name"), this.getBoolean("has_paid") };
    }
}
    
