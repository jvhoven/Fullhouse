package app.model;

import java.util.Map;

/**
 * Master class
 * 
 * This class will hold the data for the master classes. 
 * Basically a master class is a class for beginner players given by one of the higher players
 * defined by the ELO rating system
 * 
 * For more information to which data it contains
 * @see src.Keys
 * 
 * @version 0.0.1
 * @author Project Group 9
 */
public class Masterclass extends Helper {
    
    public Masterclass(Map<String, Object> params) {
        super(params);
    }    

    public Object[] getInfo(){
        return new Object[] { this.getInt("id"), this.getTeacher().get("name"), this.get("location"), this.getPrice("price"), this.get("start_date") };
    }
    
}
