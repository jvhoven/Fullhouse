/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.masterclassparticipant;

import app.controller.MasterclassController;
import app.controller.MasterclassparticipantController;
import app.controller.UserController;
import app.model.Masterclass;
import app.model.Masterclass_participant;
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
    
    MasterclassparticipantController controller = new MasterclassparticipantController();
    UserController userController = new UserController();
    MasterclassController masterclassController = new MasterclassController();
    
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
        container.add(new Heading("MASTERCLASS INSCHRIJVINGEN"));
        
        newButton = new JButton("Inschrijving aanmaken");
        deleteButton = new JButton("Inschrijving annuleren");
        
        render();
        addListeners();
    }
    
    @Override
    public void render(){
                
        try{
            ResultSet participants = controller.getAll();    
            String[] kolommen = {"Masterparticipant-Id", "Naam", "Masterclass", "Betaald"};
            overzicht = new Table(kolommen, edit);
            overzicht.removeColumn(0);
            
            while(participants.next()) {
                
                Map user = userController.get(participants.getInt("user_id"), null);
                Map masterclass = masterclassController.get(participants.getInt("masterclass_id"), null);
                
                Masterclass masterclassObject = Factory.createMasterclass(
                    masterclass.get("id"),
                    masterclass.get("name"),
                    masterclass.get("min_rating"),
                    masterclass.get("price"),
                    masterclass.get("start_date")
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
                
                Masterclass_participant masterparticipant = Factory.createMasterclassParticipant(
                    userObject,
                    masterclassObject,
                    participants.getInt("has_paid"),
                    participants.getString("start_date"),
                    participants.getString("date_approved")
                );
                                
                overzicht.add(masterparticipant.getInfo());
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
