/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import app.controller.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jeffrey
 */
public class Controller {
        
    UserController users = new UserController();
    
    public Controller() throws SQLException{
        
        ResultSet result = users.getAll();
        
        while(result.next()){
            String naam = result.getString("name");
            String adres = result.getString("address");
            String postal = result.getString("postal");
            int phone = result.getInt("phone");
            String email = result.getString("email");
            int rating = result.getInt("rating");
            int wins = result.getInt("wins");
            int losses = result.getInt("losses");
            
            System.out.println(naam + "\t" + adres + "\t" + postal + "\t" + phone + "\t" + email + "\t" + rating + "\t" + wins + "\t" + losses + "\n");
        }
        
        // Test
        
    
        //System.out.println(user.get("name"));
        
    }
}
