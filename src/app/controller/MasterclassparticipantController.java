/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import src.controller.BaseController;

/**
 *
 * @author Henk
 */
public class MasterclassparticipantController extends BaseController {
    
    private final static String table = "master_participant";
    
    public MasterclassparticipantController() {
        super(table);
    }
    
    
    public void delete(Object id) throws SQLException{
        
        String ids = (String) id;
        String[] pieces = ids.split("-");
        
        int userId = Integer.parseInt(pieces[0]);
        int tournamentId = Integer.parseInt(pieces[1]);
        
        String query = "DELETE FROM " + this.table + " WHERE user_id = ? AND masterclass_id = ?";
        PreparedStatement delete = conn.prepareStatement(query);

        delete.setInt(1, userId);
        delete.setInt(2, tournamentId);

        delete.execute();

    }
    
    public void save(LinkedHashMap<String, Object> data) throws SQLException {
        
        Date sDate = new Date((String)data.get("start_date"));
        Date aDate = new Date((String)data.get("date_approved"));
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        
        String start_date = df.format(sDate);
        String date_approved = df.format(aDate);
        
        MasterclassController controller = new MasterclassController();
        UserController uController = new UserController();
        
        Map Masterclass = controller.getByName((String)data.get("masterclass_id"));
        Map user = uController.getByName((String)data.get("user_id"));
        
         if(data.get("id") != null) {
             
            // Update
            String query = 
                    "UPDATE " + table +
                    " SET"
                        + " has_paid = ?,"
                        + " start_date = ?,"
                        + " date_approved = ?" +
                    " WHERE masterclass_id = ? AND user_id = ?";

            PreparedStatement update = conn.prepareStatement(query);

            update.setString(1, (String)data.get("has_paid"));
            update.setString(2, start_date);
            update.setString(3, date_approved);
            update.setInt(4, (int)Masterclass.get("id"));
            update.setInt(5, (int)user.get("id"));
         
            try{
                super.update(update);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }

        } else {
              
            String query = "INSERT INTO " + table + " (user_id, masterclass_id, has_paid, start_date, date_approved) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement insert = conn.prepareStatement(query);

            insert.setInt(1, (int)user.get("id"));
            insert.setInt(2, (int)Masterclass.get("id"));
            insert.setString(3, (String)data.get("has_paid"));
            insert.setString(4, start_date);
            insert.setString(5, date_approved);

            try {
                super.insert(insert);
            } catch(SQLException ex) {

            }

        }
    }
    
    public HashMap get(String value) throws SQLException{
        
        String[] pieces = value.split("-");

        int userId = Integer.parseInt(pieces[0]);
        int masterclassId = Integer.parseInt(pieces[1]);
        
        String query = "SELECT * FROM " + table + " WHERE masterclass_id = " + masterclassId + " AND user_id = " + userId;

        return (HashMap)super.resultSetToArrayList(stat.executeQuery(query)).get(0);
    }
    
    public DefaultComboBoxModel getUsers(){
        
        DefaultComboBoxModel users = new DefaultComboBoxModel();
        UserController userController = new UserController();
        
        try{
            ResultSet allUsers = userController.getAll();
            while(allUsers.next()) {
                users.addElement(allUsers.getString("name"));
            }
        } catch(SQLException e) {
            
        }
        
        return users;
        
    }
    
    public DefaultComboBoxModel getMasterclasses() {
        
        DefaultComboBoxModel masterclasses = new DefaultComboBoxModel();
       MasterclassController masterclassController = new MasterclassController();
        
        try{
            ResultSet allMasterclasses = masterclassController.getAll();
            while(allMasterclasses.next()) {
                masterclasses.addElement(allMasterclasses.getString("name"));
            }
        } catch(SQLException e) {
            
        }
        
        return masterclasses;
        
    }
    
}
