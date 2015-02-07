/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui;

import java.util.LinkedHashMap;
import java.util.Map;
import main.Application;
import src.gui.element.Button;

/**
 *
 * @author Jeffrey
 */
public class Menu {
    
    private final Map buttons = new LinkedHashMap();
    
    public final String TOURNAMENT = "Toernooien";
    public final String USERS = "Gebruikers";
    public final String SUBSCRIPTIONS = "Inschrijvingen";
    public final String MASTERCLASS = "Masterclasses";
    public final String MASTER_PARTICIPANTS = "Minschrijvingen";
    public final String LOCATIONS = "Locaties";
    
    public String active;
    
    public Menu(){
        
        // Alle buttons
        buttons.put(TOURNAMENT, new Button(TOURNAMENT, "src/assets/menu/cup.png", 0, this));
        buttons.put(USERS, new Button(USERS, "src/assets/menu/user.png", 0, this));
        buttons.put(SUBSCRIPTIONS, new Button(SUBSCRIPTIONS, "src/assets/menu/registration.png", 0, this));
        buttons.put(MASTERCLASS, new Button(MASTERCLASS, "src/assets/menu/master.png", 0, this));
        buttons.put(MASTER_PARTICIPANTS, new Button(MASTER_PARTICIPANTS, "src/assets/menu/registration.png", 0, this));
        buttons.put(LOCATIONS, new Button(LOCATIONS, "src/assets/menu/maps.png", 0, this));
      
    }
    
    public LinkedHashMap getElements(){
        return (LinkedHashMap) buttons;
    }
    
    public void setActive(String key){
                        
        if(active != null && !active.equals(key)) {
            Button prevActive = (Button) buttons.get(active);
            prevActive.setActive(false);
            
        }
                
        active = key;
        
        Button newActive = (Button) buttons.get(active);
        newActive.setActive(true);
        
        // Load in the view
        Application.manager.setView(key);
    }
    
}
