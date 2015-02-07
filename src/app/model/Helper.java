package app.model;

import java.math.BigDecimal;
import java.util.*;
import src.builder.Builder;

/**
 * Helper class
 * 
 * This is more of a utility class that allows you to dynamically use
 * the various setters and getters
 * 
 * It does so by using the key values in @see test.Keys.
 * 
 * @version 0.0.1
 * @author Project Group 9
 */
public class Helper {
    
    Map<String, Object> data = new HashMap<>();
    
    public Helper(Map<String, Object> params) {
        this.data = params;
    }
    
    /*
    * Dynamic getter method
    */
    public Object get(String index) {
        if(data.get(index) == null) {
            return null;
        }
        return data.get(index).toString();
    }
    
    public BigDecimal getBigDecimal(String index) {
        
        if(data.get(index) == null) {
            return null;
        }
        
        return (BigDecimal) data.get(index);
    }
    
    public String getPrice(String index) {
        
        if(data.get(index) == null) {
            return null;
        }
        
        return "€ "+ data.get(index);
    }
    
    public String getDate(String index) {
        if(data.get(index) == null) {
            return null;
        }
        
        return Builder.dateToString(data.get(index));
    }
    
    public String getBoolean(String index) {
        
        if(data.get(index) == null ) {
            return null;
        }
        
        return Boolean.valueOf(String.valueOf(data.get(index))) == false ? "Ja" : "Nee";
    }

    /*
    * Dynamic setter method
    * How surprising
    */
    public void set(String index, Object value) {
        data.put(index, value);
    }
    
    /*
    * We override the toString method to give a string presentation
    * of an object. For example this would return all the keys from a Tournament
    * as a string.
    */
    /*@Override
    public String toString() {
        
        String string = "";
        
        for(Entry<String, Object> entry : data.entrySet()) {
            string = entry.getValue() + " " + string;
        }
        
        return string;
    }*/
    
    public int getInt(String index) {
        
        Object value = get(index);
        return Integer.parseInt(value.toString());
    }
    
    public User getUser() {
        return (User) data.get("user");
    }
    
    public Location getLocation() {
        return (Location) data.get("location");
    }
    
    public Masterclass getMasterclass() {
        return (Masterclass) data.get("masterclass");
    }
    
    public Tournament getTournament() {
        return (Tournament) data.get("tournament");
    }
    
    public User getTeacher() {
        return (User) data.get("teacher");
    }
}
