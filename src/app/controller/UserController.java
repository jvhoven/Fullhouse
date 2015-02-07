/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import src.controller.BaseController;

/**
 *
 * @author Jeffrey
 */
public class UserController extends BaseController {
    
    private final static String table = "user";
    
    public UserController() {
        super(table);
    }
    
    public void save(LinkedHashMap<String, Object> data) throws SQLException{
        
        if(data.get("id") != null) {
            
            String query = 
                    "UPDATE " + table +
                    " SET"
                        + " name = ?," 
                        + " address = ?,"
                        + " postal = ?,"
                        + " city = ?,"
                        + " phone = ?,"
                        + " email = ?" +
                    " WHERE id = ?";

            PreparedStatement update = conn.prepareStatement(query);

            update.setString(1, (String) data.get("name"));
            update.setString(2, (String) data.get("address"));
            update.setString(3, (String) data.get("postal"));
            update.setString(4, (String) data.get("city"));
            update.setString(5, (String) data.get("phone"));
            update.setString(6, (String) data.get("email"));
            update.setInt(7, (int) data.get("id"));

            try{
                super.update(update);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }

        } else {
            
            String query = "INSERT INTO " + table + " (name, address, postal, city, phone, email) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement insert = conn.prepareStatement(query);

            insert.setString(1, (String) data.get("name"));
            insert.setString(2, (String) data.get("address"));
            insert.setString(3, (String) data.get("postal"));
            insert.setString(4, (String) data.get("city"));
            insert.setString(5, (String) data.get("phone"));
            insert.setString(6, (String) data.get("email"));

            try {
                super.insert(insert);
            } catch(SQLException ex) {

            }

            
        }
    }
    
    public HashMap getByName(String name) {
        
        try{
            return get(name, "name");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
