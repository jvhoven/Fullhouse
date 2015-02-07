/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.user;

import app.controller.UserController;
import app.model.User;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    UserController controller = new UserController();
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
        newButton = new JButton("Gebruiker aanmaken");
        deleteButton = new JButton("Gebruiker verwijderen");
        
        container.add(new Heading("Gebruikers"));
        render();
        addListeners();
    }
    
    @Override
    public void render(){
                
        try{
            ResultSet users = controller.getAll();    
            String[] kolommen = {"Id", "Naam", "Rating", "Adres", "Woonplaats"};
            overzicht = new Table(kolommen, edit);
            overzicht.removeColumn(0);
            
            while(users.next()) {
                
                User user = Factory.createUser(
                    users.getInt("id"),
                    users.getString("name"),
                    users.getString("address"),
                    users.getString("postal"),
                    users.getString("city"),
                    users.getString("phone"),
                    users.getString("email"),
                    users.getInt("total_score"),
                    users.getInt("rating"),
                    users.getInt("wins"),
                    users.getInt("losses")
                );
                
                overzicht.add(user.getInfo());
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
