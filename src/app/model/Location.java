package app.model;

import java.util.Map;

/**
 * Location
 * 
 * This class will hold the data for the different locations
 * that the user has specified in the Full House system
 * 
 * For more information to which data it contains
 * @see src.Keys
 * 
 * @version 1.0.0
 * @author Project Group 9
 */
public class Location extends Helper {
    
    public Location(Map<String, Object> params) {
        super(params);
    }    
       
    public Object[] getInfo(){
        return new Object[] { this.get("postal"), this.get("address") + " " + this.get("streetnr") + ", " + this.get("city") + " " + this.get("postal"), this.getInt("max_capacity") };
    }
}
