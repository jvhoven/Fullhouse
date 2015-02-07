package app.model;

import java.util.Map;

/**
 * User class
 * 
 * This class will hold the private information for users
 * who have yet to participate in a tournament, or have already participated
 * in a tournament
 * 
 * For more information to which data it contains
 * @see src.Keys
 * 
 * @version 0.0.1
 * @author Project Group 9
 */
public class User extends Helper {
      
    public User(Map<String, Object> params) {
        super(params);
    }        
    
    public Object[] getInfo(){
        return new Object[] { this.getInt("id"), this.get("name"), this.getInt("rating"), this.get("address"), this.get("city") };
    }
}
