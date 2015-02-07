/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.controller;

import app.model.Participant;
import app.model.Tournament;
import app.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import src.Factory;
import src.controller.BaseController;
import src.rating.Matchmaking;

/**
 *
 * @author Henk
 */
public class TournamentController extends BaseController {
    
    private final static String table = "tournament";
    
    public TournamentController() {
        super(table);
    }
    
    public void startMatchmaking(Map dbData) {
        
        Tournament tournament = Factory.createTournament(
                dbData.get("id"), 
                dbData.get("location"),
                dbData.get("name"),
                dbData.get("description"),
                dbData.get("start_date"),
                dbData.get("payment"),
                dbData.get("min_participants"),
                dbData.get("players_per_table"));
        
        // Alle participants ophalen
        ParticipantController pController = new ParticipantController();
        UserController uController = new UserController();
        
        ResultSet parts = pController.getAllByTournament((int)dbData.get("id"));
        ArrayList<Participant> participants = new ArrayList<>();
        
        try {
            while(parts.next()) {
                 
                Map user = new HashMap<>();
                
                try {
                    user = uController.get(parts.getInt("user_id"), null);
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
                            
                User userObject = Factory.createUser(
                        user.get("id"),
                        user.get("name"),
                        user.get("address"),
                        user.get("postal"),
                        user.get("city"),
                        user.get("phone"),
                        user.get("email"),
                        user.get("rating"),
                        user.get("total_score"),
                        user.get("wins"),
                        user.get("losses")
                );
                        
                Participant participant = Factory.createParticipant(
                        userObject,
                        tournament,
                        parts.getInt("has_paid"),
                        parts.getDate("start_date"),
                        parts.getDate("date_approved")
                );
                
                
                participants.add(participant);
            }
        } catch(SQLException ex) {
            
        }

        // Matchmaking doen
        //new tests.Matchmaking();
        Matchmaking matchmake = new Matchmaking(tournament, participants);
        
   
    }
    
    public boolean canStart(int id) {
        
        //if()
        
        return false;
        
    }
    
    public void save(LinkedHashMap<String, Object> data) throws SQLException{
        
        if(data.get("id") != null) {

            // Update
            String query = 
                    "UPDATE " + table +
                    " SET"
                        + " name = ?," 
                        + " location = ?,"
                        + " description = ?,"
                        + " start_date = ?,"
                        + " payment = ?,"
                        + " min_participants = ?," 
                        + " players_per_table = ?" + 
                    " WHERE id = ?";

            PreparedStatement update = conn.prepareStatement(query);

            update.setString(1, (String) data.get("name"));
              if(data.get("location").equals(" ")) {
                update.setNull(2, java.sql.Types.CHAR);
            } else {
                update.setString(2, (String)data.get("location"));
            }
            update.setString(3, (String) data.get("description"));
            update.setString(4, (String) data.get("start_date"));
            update.setInt(5, Integer.valueOf((String)data.get("payment")));
            update.setString(6, (String) data.get("min_participants"));
            update.setInt(7, Integer.valueOf((String)data.get("players_per_table")));
            update.setInt(8, (int) data.get("id"));

            try{
                super.update(update);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }

        } else {

            String query = "INSERT INTO " + table + " (name, location, description, start_date, payment, min_participants, players_per_table) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement insert = conn.prepareStatement(query);
                                    
            insert.setString(1, (String) data.get("name"));
            
            if(data.get("location").equals(" ")) {
                insert.setNull(2, java.sql.Types.CHAR);
            } else {
                insert.setString(2, (String)data.get("location"));
            }
            
            insert.setString(3, (String) data.get("description"));
            insert.setString(4, (String) data.get("start_date"));
            insert.setInt(5, Integer.valueOf((String)data.get("payment")));
            insert.setString(6, (String) data.get("min_participants"));
            insert.setInt(7, Integer.valueOf((String)data.get("players_per_table")));
           
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
    
    public DefaultComboBoxModel getRandomValues(int steps, int max){
          
        DefaultComboBoxModel payments = new DefaultComboBoxModel();
        payments.addElement("- Selecteer waarde -");
        
        for(int i = 1; i < max; i++) {
            payments.addElement(steps * i);
        }
        
        return payments;
    }    
}
