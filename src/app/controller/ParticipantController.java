/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.controller;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import src.controller.BaseController;
import src.controller.Utils;

/**
 *
 * @author Henk
 */
public class ParticipantController extends BaseController {
    
    private final static String table = "participant";
    
    public ParticipantController() {
        super(table);
    }
    
    public void delete(Object id) throws SQLException{
        
        if(Utils.sendMessage("", "Weet u zeker dat u dit item wilt verwijderen?", true) != 0) {
            return;
        }

        String ids = (String) id;
        String[] pieces = ids.split("-");
        
        int userId = Integer.parseInt(pieces[0]);
        int tournamentId = Integer.parseInt(pieces[1]);
        
        String query = "DELETE FROM " + this.table + " WHERE user_id = ? AND tournament_id = ?";
        PreparedStatement delete = conn.prepareStatement(query);

        delete.setInt(1, userId);
        delete.setInt(2, tournamentId);

        delete.execute();
        
        Utils.sendMessage("Success", "Item succesvol verwijderd.", false);
    }
    
    public boolean save(LinkedHashMap<String, Object> data) throws SQLException {
        
        TournamentController tController = new TournamentController();
        UserController uController = new UserController();
        
        Map toernooi = tController.getByName((String)data.get("tournament_id"));
        Map user = uController.getByName((String)data.get("user_id"));
        
         if(data.get("id") != null) {
             
            // Update
            String query = 
                    "UPDATE " + table +
                    " SET"
                        + " has_paid = ?,"
                        + " start_date = ?,"
                        + " date_approved = ?" +
                    " WHERE tournament_id = ? AND user_id = ?";

            PreparedStatement update = conn.prepareStatement(query);

            update.setString(1, (String)data.get("has_paid"));
            update.setString(2, (String)data.get("start_date"));
            update.setString(3, (String)data.get("date_approved"));
            update.setInt(4, (int)toernooi.get("id"));
            update.setInt(5, (int)user.get("id"));

            try{
                super.update(update);
            } catch(MySQLIntegrityConstraintViolationException ex) {
                Utils.sendMessage("Error", "Gebruiker is al ingeschreven in dit toernooi.", false);
                return false;
            }

        } else {
              
            String query = "INSERT INTO " + table + " (user_id, tournament_id, has_paid, start_date, date_approved) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement insert = conn.prepareStatement(query);

            insert.setInt(1, (int)user.get("id"));
            insert.setInt(2, (int)toernooi.get("id"));
            insert.setString(3, (String)data.get("has_paid"));
            insert.setString(4, (String)data.get("start_date"));
            insert.setString(5, (String)data.get("date_approved"));

            try {
                super.insert(insert);
            } catch(MySQLIntegrityConstraintViolationException ex) {
                Utils.sendMessage("Error", "Gebruiker is al ingeschreven in dit toernooi.", false);
                return false;
            }

        }
         
        return true;
    }
    
    public ResultSet getAllByTournament(int tournamentId) {
        
        // Hier je query
        String query = "SELECT * FROM " + this.table + " WHERE tournament_id = " + tournamentId;
        
        try {
            return stat.executeQuery(query); 
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public HashMap get(String value) throws SQLException{
        
        String[] pieces = value.split("-");
        
        int userId = Integer.parseInt(pieces[0]);
        int tournamentId = Integer.parseInt(pieces[1]);
        
        String query = "SELECT * FROM " + table + " WHERE tournament_id = " + tournamentId + " AND user_id = " + userId;

        return (HashMap)super.resultSetToArrayList(stat.executeQuery(query)).get(0);
    }
    
    public DefaultComboBoxModel getUsers(){
        
        DefaultComboBoxModel users = new DefaultComboBoxModel();
        UserController userController = new UserController();
        
        users.addElement("- Selecteer gebruiker -");
        
        try{
            ResultSet allUsers = userController.getAll();
            while(allUsers.next()) {
                users.addElement(allUsers.getString("name"));
            }
        } catch(SQLException e) {
            
        }
        
        return users;
        
    }
    
    public DefaultComboBoxModel getTournaments() {
        
        DefaultComboBoxModel tournaments = new DefaultComboBoxModel();
        TournamentController tournamentController = new TournamentController();
        
        tournaments.addElement("- Selecteer toernooi -");
        
        try{
            ResultSet allTournaments = tournamentController.getAll();
            while(allTournaments.next()) {
                tournaments.addElement(allTournaments.getString("name"));
            }
        } catch(SQLException e) {
            
        }
        
        return tournaments;
        
    }
    
}
