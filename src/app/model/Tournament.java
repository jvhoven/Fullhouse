package app.model;

import java.util.Map;

/**
 * Tournament class
 * 
 * This class will be used to register the different kind of tournaments defined
 * in the system
 * 
 * The basic one is a poker tournament but it might also be possible for other card
 * games
 * 
 * For more information to which data it contains
 * @see src.Keys
 * 
 * @version 0.0.1
 * @author Project Group 9
 */
public class Tournament extends Helper {
        
    public Tournament(Map<String, Object> params) {
        super(params);
    }    
    
    public Object[] getInfo(){
        return new Object[] { this.getInt("id"), this.get("name"), this.get("start_date"), this.getPrice("payment"), this.get("location") };
    }
}
