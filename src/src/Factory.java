package src;

import app.model.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import static src.Keys.*;
import src.builder.Builder;
import src.exceptions.MissmatchDataException;

/**
 *
 * @author Project group 9
 */
public class Factory implements src.Keys {
    
    private static void validateData(Map<String, Object> data, String[] comparisonData) throws MissmatchDataException {
        
        int i = 0;
                
        if(data.size() > comparisonData.length) {
            throw new MissmatchDataException("Too many arguments given.");
        }
        
        for(Map.Entry<String, Object> entry : data.entrySet()) {
            if(!entry.getKey().equals(comparisonData[i])) {
                String error = comparisonData[i] + " wasn't found in given data.";
                throw new MissmatchDataException(error);
            }
            i++;
        }
    
    }
   
    // <editor-fold defaultstate="collapsed" desc="Click to unfold to show methods of creating all objects">
    public static Participant createParticipant(Object... data){
        
        ArrayList<Object> array = new ArrayList<>();
        
        for(Object value : data) {
            array.add(value);
        }
             
        try {
            LinkedHashMap mapData = Builder.toLinkedHashMap(data, participant);
            validateData(mapData, participant);
            return new Participant(mapData);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Tournament createTournament(Object... data){
        
        ArrayList<Object> array = new ArrayList<>();
        
        for(Object value : data) {
            array.add(value);
        }
             
        try {
            LinkedHashMap mapData = Builder.toLinkedHashMap(data, tournament);
            validateData(mapData, tournament);
            return new Tournament(mapData);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Location createLocation(Object... data){
        
        ArrayList<Object> array = new ArrayList<>();
        
        for(Object value : data) {
            array.add(value);
        }
             
        try {
            LinkedHashMap mapData = Builder.toLinkedHashMap(data, location);
            validateData(mapData, location);
            return new Location(mapData);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Masterclass createMasterclass(Object... data){
        
        ArrayList<Object> array = new ArrayList<>();
        
        for(Object value : data) {
            array.add(value);
        }
             
        try {
            LinkedHashMap mapData = Builder.toLinkedHashMap(data, masterclass);
            validateData(mapData, masterclass);
            return new Masterclass(mapData);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
     public static Session createSession(){
      
        return new Session();
     
    }
     
    public static Position createPosition(Object... data){
        
        ArrayList<Object> array = new ArrayList<>();
        
        for(Object value : data) {
            array.add(value);
        }
             
        try {
            LinkedHashMap mapData = Builder.toLinkedHashMap(data, position);
            validateData(mapData, position);
            return new Position(mapData);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static User createUser(Object... data){
        
        ArrayList<Object> array = new ArrayList<>();
        
        for(Object value : data) {
            array.add(value);
        }
             
        try {
                        
            LinkedHashMap mapData = Builder.toLinkedHashMap(data, user);
            validateData(mapData, user);
            return new User(mapData);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
       
    public static Masterclass_participant createMasterclassParticipant(Object... data){
        
        ArrayList<Object> array = new ArrayList<>();
        
        for(Object value : data) {
            array.add(value);
        }
             
        try {
            LinkedHashMap mapData = Builder.toLinkedHashMap(data, master_participant);
            validateData(mapData, master_participant);
            return new Masterclass_participant(mapData);
        } catch(MissmatchDataException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    // </editor-fold>
}
