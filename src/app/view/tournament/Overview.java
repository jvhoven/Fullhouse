/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.tournament;

import app.controller.TournamentController;
import app.model.Tournament;
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
   
    TournamentController controller = new TournamentController();
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
        newButton = new JButton("Toernooi aanmaken");
        deleteButton = new JButton("Toernooi annuleren");
        
        container.add(new Heading("TOERNOOIEN"));
        render();  
        addListeners();
    }
    
    @Override
    public void render(){
        
        // Get all tournaments from database
        try{
            ResultSet tournaments = controller.getAll();                                 
            String[] kolommen = {"id", "Naam", "Start datum", "Inleg", "Locatie"};
            overzicht = new Table(kolommen, edit);
            overzicht.removeColumn(0);
                        
            while(tournaments.next()) {
                
                Tournament tournament = Factory.createTournament(
                    tournaments.getInt("id"),
                    tournaments.getString("location"),
                    tournaments.getString("name"),
                    tournaments.getString("description"),
                    tournaments.getString("start_date"),
                    tournaments.getInt("payment"),
                    tournaments.getInt("min_participants")
                );
                                
                overzicht.add(tournament.getInfo());
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
                int id = (int)overzicht.active;
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
       
    }
    
}
