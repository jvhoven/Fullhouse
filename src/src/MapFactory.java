package src;

/*
* DEPRECATED 
* 
* Use Factory instead
*/

import app.model.*;
import src.exceptions.MissmatchDataException;
import java.util.Map;

/**
 *
 * @author Project group 9
 */
public class MapFactory implements src.Keys {
    
    private static void validateData(Map<String, Object> data, String[] comparisonData) throws MissmatchDataException {
        
        int i = 0;
        
        for(Map.Entry<String, Object> entry : data.entrySet()) {
            if(!entry.getKey().equals(comparisonData[i])) {
                String error = comparisonData[i] + " wasn't found in given data.";
                throw new MissmatchDataException(error);
            }
            i++;
        }
    
    }
   
    // <editor-fold defaultstate="collapsed" desc="Click to unfold to show methods of creating all objects">
    public static Participant createParticipant(Map<String, Object> data){
        
        try {
            validateData(data, participant);
            return new Participant(data);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Tournament createTournament(Map<String, Object> data){
        
        try {
            validateData(data, tournament);
            return new Tournament(data);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Location createLocation(Map<String, Object> data){
        
        try {
            validateData(data, location);
            return new Location(data);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Masterclass createMasterclass(Map<String, Object> data){
        
        try {
            validateData(data, masterclass);
            return new Masterclass(data);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static User createUser(Map<String, Object> data){
        
        try {
            validateData(data, user);
            return new User(data);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }    
    // </editor-fold>
}
