/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import src.controller.BaseController;

/**
 *
 * @author milan
 */
public class MasterclassController extends BaseController {
    
    private final static String table = "masterclass";
    
    public MasterclassController() {
        super(table);
    }
    
    public HashMap getByName(String name) {
        
        try{
            return get(name, "name");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void save(LinkedHashMap<String, Object> data) throws SQLException{
        
        UserController uController = new UserController();
        Map user = uController.getByName((String)data.get("teacher"));
        
        if(data.get("id") != null) {
                      
            String query = 
                    "UPDATE " + table +
                    " SET"
                        + " name = ?,"
                        + " start_date = ?," 
                        + " min_rating = ?,"
                        + " teacher = ?,"
                        + " price  = ?," 
                        + " location = ?"
                        +
                    " WHERE id = ?";

            PreparedStatement update = conn.prepareStatement(query);
            
            update.setString(1, (String)data.get("name"));
            update.setString(2, (String)data.get("start_date"));
            update.setString(3, (String) data.get("min_rating"));
            update.setInt(4, (int)user.get("id"));
            update.setString(5, (String) data.get("price"));
             
            if(data.get("location").equals(" ")) {
                update.setNull(6, java.sql.Types.CHAR);
            } else {
                update.setString(6, (String)data.get("location"));
            }
            
            update.setInt(7, (int) data.get("id"));

            try{
                super.update(update);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }

        } else {
            
            String query = "INSERT INTO " + table + " (name,start_date, min_rating, teacher, price, location) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement insert = conn.prepareStatement(query);
            
            insert.setString(1, (String) data.get("name"));
            insert.setString(2, (String) data.get("start_date"));
            insert.setString(3, (String) data.get("min_rating"));
            insert.setInt(4, (int)user.get("id"));
            insert.setString(5, (String) data.get("price"));
            
            if(data.get("location").equals(" ")) {
                insert.setNull(6, java.sql.Types.CHAR);
            } else {
                insert.setString(6, (String)data.get("location"));
            }

            try {
                super.insert(insert);
            } catch(SQLException ex) {

            }

            
        }
    }
    
    public DefaultComboBoxModel getTeachers(){
        
        DefaultComboBoxModel users = new DefaultComboBoxModel();
        UserController userController = new UserController();
        
        users.addElement("- Selecteer leraar -");
        
        try{
            ResultSet allUsers = userController.getAll();
            while(allUsers.next()) {
                users.addElement(allUsers.getString("name"));
            }
        } catch(SQLException e) {
            
        }
    
        return users;
        
    }
    
    public DefaultComboBoxModel getLocations() {
        
        DefaultComboBoxModel locations = new DefaultComboBoxModel();
        locations.addElement("- Selecteer locatie -");
        
        LocationController locationController = new LocationController();
        
        try{
            ResultSet allLocations = locationController.getAll();
            while(allLocations.next()) {
                locations.addElement("[" + allLocations.getString("postal") + "] " + allLocations.getString("address") + " " + allLocations.getInt("streetnr") + ", " + allLocations.getString("city"));
            }
        } catch(SQLException e) {
            
        }

        return locations;
    }
}
