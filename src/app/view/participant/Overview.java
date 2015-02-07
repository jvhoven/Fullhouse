/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.participant;

import app.controller.ParticipantController;
import app.controller.TournamentController;
import app.controller.UserController;
import app.model.Participant;
import app.model.Tournament;
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
    
    ParticipantController controller = new ParticipantController();
    UserController userController = new UserController();
    TournamentController tournamentController = new TournamentController();
    
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
        container.add(new Heading("Inschrijvingen"));
        
        newButton = new JButton("Inschrijving aanmaken");
        deleteButton = new JButton("Inschrijving annuleren");
        
        render();
        addListeners();
    }
    
    @Override
    public void render(){
                
        try{
            ResultSet participants = controller.getAll();    
            String[] kolommen = {"Participant-Id", "Naam", "Toernooi", "Betaald"};
            overzicht = new Table(kolommen, edit);
            overzicht.removeColumn(0);
            
            while(participants.next()) {
                
                Map user = userController.get(participants.getInt("user_id"), null);
                Map tournament = tournamentController.get(participants.getInt("tournament_id"), null);
                
                Tournament tournamentObject = Factory.createTournament(
                    tournament.get("id"),
                    tournament.get("location"),
                    tournament.get("name"),
                    tournament.get("description"),
                    tournament.get("start_date"),
                    tournament.get("payment"),
                    tournament.get("min_participants")
                );

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
                    tournamentObject,
                    participants.getInt("has_paid"),
                    participants.getString("start_date"),
                    participants.getString("date_approved")
                );
                                
                overzicht.add(participant.getInfo());
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
                    controller.delete(id);
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
