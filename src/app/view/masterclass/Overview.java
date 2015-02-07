/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.masterclass;

import app.controller.MasterclassController;
import app.controller.UserController;
import app.model.Masterclass;
import app.model.User;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JButton;
import src.Factory;
import src.gui.Layout;
import src.gui.element.Heading;
import src.gui.element.Table;
import src.view.View;

/**
 *
 * @author milan
 */
public class Overview extends View {
    
    MasterclassController controller = new MasterclassController();
    JButton newButton, deleteButton;
    Edit edit;
    Table overzicht;
    
    public Overview(Layout layout) {
        super(layout);
        this.edit = new Edit(controller, this);
    }
    
    @Override
    public void init(){
        
        super.init();
        
        newButton = new JButton("Masterclass aanmaken");
        deleteButton = new JButton("Masterclass verwijderen");
        
        container.add(new Heading("Masterclasses"));
        render();
        addListeners();
    }
    
    @Override
    public void render(){
        
        try{
            ResultSet masterclasses = controller.getAll();    
            String[] kolommen = {"Id", "Leraar", "Locatie", "Kosten", "Start datum" };
            overzicht = new Table(kolommen, edit);
            overzicht.removeColumn(0);
            
            UserController userController = new UserController();
            
            while(masterclasses.next()) {
                
                //User user = Factory.createUser(users.getInt("id"), users.getString("name"), users.getString("address"), users.getString("postal"), users.getString("city"), users.getString("phone"), users.getString("email"), users.getInt("total_score"), users.getInt("rating"), users.getInt("wins"), users.getInt("losses"));
                
                Map user = userController.get(masterclasses.getInt("teacher"), null);
                
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

                Masterclass masterclass = Factory.createMasterclass(
                    masterclasses.getInt("id"),
                    masterclasses.getString("name"),
                    userObject,
                    masterclasses.getInt("min_rating"),
                    masterclasses.getInt("price"),
                    masterclasses.getString("location"),
                    masterclasses.getString("start_date")
                );
                
                overzicht.add(masterclass.getInfo());
            }
            container.add(overzicht.render(), FlowLayout.CENTER);
            
        } catch(SQLException e) {
     
        }

        container.add(newButton);
        container.add(deleteButton);
    }

    
     private void addListeners(){
        
        newButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                edit.init(null);
            }
        });        
        
        deleteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Object id = overzicht.active;
                try{
                    controller.delete(null, id);
                    rerender();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });        
    }
     
    @Override
    public void preInit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
