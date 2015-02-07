/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.location;

import app.controller.LocationController;
import app.model.Location;
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
   
    LocationController controller = new LocationController();
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
        container.add(new Heading("Locaties"));
        
        newButton = new JButton("Locatie aanmaken");
        deleteButton = new JButton("Locatie verwijderen");
        
        render();
        addListeners();
    }
    
    @Override
    public void render(){
        
        // Get all locations from database
        try{
            ResultSet locations = controller.getAll();                                 
            String[] kolommen = {"Postal", "Address", "Max inschrijvingen"};
            overzicht = new Table(kolommen, edit);
            overzicht.removeColumn(0);
                        
            while(locations.next()) {
                
                Location location = Factory.createLocation(
                    locations.getString("postal"),
                    locations.getInt("streetnr"),
                    locations.getString("address"),
                    locations.getString("city"),
                    locations.getInt("max_capacity")
                );
                
                overzicht.add(location.getInfo());
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
                    controller.delete("postal", id);
                    rerender();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });        
    }

    @Override
    public void preInit() {
        
    }
    
}
