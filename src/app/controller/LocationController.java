/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import src.controller.BaseController;

/**
 *
 * @author milan
 */
public class LocationController extends BaseController {
    
    private final static String table = "location";
    
    public LocationController() {
        super(table);
    }
    
     public void save(LinkedHashMap<String, Object> data) throws SQLException{
            
            if(data.get("id") != null) {
                
            
                
                String query = 
                    "UPDATE " + table +
                    " SET"
                        + " postal = ?,"
                        + " address = ?," 
                        + " streetnr = ?,"
                        + " city = ?,"
                        + " max_capacity = ?" +
                    " WHERE postal LIKE ?";
                
                PreparedStatement update = conn.prepareStatement(query);
                
                update.setString(1, (String) data.get("postal"));
                update.setString(2, (String) data.get("address"));
                update.setString(3, (String) data.get("streetnr"));
                update.setString(4, (String)data.get("city"));
                update.setInt(5, Integer.parseInt((String)data.get("max_capacity")));
                update.setString(6, (String) data.get("id"));
   
                try{
                    super.update(update);
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }                
            } else {
                String query = "INSERT INTO " + table + " (postal, address, streetnr, city, max_capacity) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement insert = conn.prepareStatement(query);
                
                insert.setString(1, (String) data.get("postal"));
                insert.setString(2, (String) data.get("address"));
                insert.setString(3, (String) data.get("streetnr"));
                insert.setString(4, (String)data.get("city"));
                insert.setInt(5, Integer.parseInt((String)data.get("max_capacity")));
                
                try {
                    super.insert(insert);
                } catch(SQLException ex) {

                }
            }
        
         
    }
}
