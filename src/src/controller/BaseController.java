/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import src.database.Adapter;

/**
 *
 * @author Lenovo
 */
public class BaseController {
    
    public Statement stat;
    public Connection conn;
  
    String table;
    
    public BaseController(String table){
        
        this.table = table;
        
        try{
            init();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void init() throws SQLException{    
        Adapter adapter = new Adapter("root", "fullhousev2", "", "localhost");
        conn = adapter.getConnection();
        stat = conn.createStatement();  
    }
    
    public List resultSetToArrayList(ResultSet rs) throws SQLException{
        
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList(50);
        while (rs.next()){
           HashMap row = new HashMap(columns);
           for(int i=1; i<=columns; ++i){           
            row.put(md.getColumnName(i), rs.getObject(i));
           }
            list.add(row);
        }
        
        return list;
    }    
    
    public ResultSet getAll() throws SQLException{
        String query = "SELECT * FROM " + this.table;
        
        return stat.executeQuery(query); 
    } 
   
    public HashMap get(Object id, String column) throws SQLException{
        
        String query = "";
        if(column == null) {
            column = "id";
        }
        
        if(id instanceof Integer) {
            query = "SELECT * FROM " + this.table + " WHERE " + column + " = " + (int)id; 
        } else if(id instanceof String) {
            query = "SELECT * FROM " + this.table + " WHERE " + column + " LIKE '" + (String)id + "'"; 
        }
        
        return (HashMap)resultSetToArrayList(stat.executeQuery(query)).get(0);
    }
    
    public void insert(PreparedStatement insert) throws SQLException {   
        insert.execute();
        conn.commit();
    }
    
    public void update(PreparedStatement update) throws SQLException {
        update.executeUpdate();
        conn.commit();
    }
    
    public void delete(String column, Object id) throws SQLException{
        
        try {
            if(Utils.sendMessage("", "Weet u zeker dat u dit item wilt verwijderen?", true) != 0) {
                return;
            }

            if(column == null) {
                column = "id";
            }

            if(id != null) {
                String query = "DELETE FROM " + this.table + " WHERE " + column + " = ?";
                PreparedStatement update = conn.prepareStatement(query);

                if(id instanceof Integer) {
                    update.setInt(1, (int)id);
                } else if(id instanceof String) {
                    update.setString(1, (String)id);
                }

                update.execute();
               
            }
        } catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
            Utils.sendMessage("Error", "U kunt deze gebruiker niet verwijderen omdat de gebruiker is ingeschreven bij een toernooi of masterclass.", false);
            return;
        }
        
        Utils.sendMessage("Success", "Item succesvol verwijderd.", false);
    }    
   
}
