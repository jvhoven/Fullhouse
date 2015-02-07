/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.participant;

import app.controller.ParticipantController;
import app.controller.TournamentController;
import app.controller.UserController;
import java.awt.BorderLayout;
import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import src.controller.Utils;
import src.validator.Validator;
import src.gui.element.Heading;

/**
 *
 * @author milan
 */
public class Edit extends src.view.Edit {
    
    Overview overview;
    ParticipantController controller;
    
    JTextField hasPaid, startDate, dateApproved;
    JComboBox users, tournaments;
    JButton actionButton, cancelButton;
    
    public Edit(ParticipantController controller, Overview overview) {
        super.setController(controller);
        
        this.controller = controller;
        this.overview = overview;
        
        actionButton = new JButton("Opslaan");
        cancelButton = new JButton("Annuleren");
        
        addListeners();
    }
    
    @Override
    public void preInit() {
    }

    @Override
    public void render() {
        container.setPreferredSize(new Dimension());
        container.setLayout(new BorderLayout());
        
        Heading header = new Heading("Inschrijving", "detail");
        header.setBorder(new EmptyBorder(0, 20, 0, 0));
                
        container.add(header, BorderLayout.NORTH);
        
        if(dbData != null) {
            edit();
        } else {
            newEdit();
        }
        
        container.add(formBuilder.render(), BorderLayout.WEST);    
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(450, 120));
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setAlignmentX(RIGHT_ALIGNMENT);
        
        buttonPanel.add(actionButton);
        buttonPanel.add(cancelButton);
        
        container.add(buttonPanel, BorderLayout.PAGE_END);
    }
    
    private void addListeners(){
        
        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        actionButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                LinkedHashMap data = retrieveData();
                
                // Op het moment dat er errors in de form zitten word data op null gezet
                if(data == null) {
                    return;
                }
                
                try{
                    if(controller.save(data)) {
                        overview.rerender();
                        dispose();
                        Utils.sendMessage("Success", "Item succesvol opgeslagen.", false);
                    }
                } catch(SQLException ex) {
                    Utils.sendMessage("Error", "Er is iets misgegaan met het opslaan van de data.", false);
                }
            }
        });
    }
    
    private void edit(){
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String start_date = df.format((Date)dbData.get("start_date"));
        
        String date_approved = "";
        
        if(String.valueOf(dbData.get("date_approved")).length() > 5) {
            date_approved = df.format((Date)dbData.get("date_approved"));
        }
        
        TournamentController tournamentController = new TournamentController();
        UserController userController = new UserController();
        
        Map tournament = new HashMap<>();
        Map user = new HashMap<>();
        
        try {
            tournament = tournamentController.get(dbData.get("tournament_id"), null);
            user = userController.get(dbData.get("user_id"), null);
        } catch(SQLException e) {
            
        }
        
        int heeftBetaald = 0;
        
        if((boolean)dbData.get("has_paid")) {
            heeftBetaald = 1;
        }
        
        DefaultComboBoxModel cTournaments = controller.getTournaments();
        cTournaments.setSelectedItem(tournament.get("name"));
        tournaments = new JComboBox(cTournaments);
        tournaments.setEditable(false);
        tournaments.setEnabled(false);
        
        DefaultComboBoxModel cUsers = controller.getUsers();
        cUsers.setSelectedItem(user.get("name"));
        users = new JComboBox(cUsers);
        users.setEditable(false);
        users.setEnabled(false);
        
        formBuilder.addCombobox(new JLabel("Toernooi: ", JLabel.LEFT), tournaments);
        formBuilder.addCombobox(new JLabel("Gebruiker: ", JLabel.LEFT), users);
        formBuilder.addTextfield(new JLabel("Betaald: ", JLabel.LEFT), hasPaid = new JTextField(String.valueOf(heeftBetaald), 25));
        formBuilder.addTextfield(new JLabel("Start datum: ", JLabel.LEFT), startDate = new JTextField(start_date, 25));
        formBuilder.addTextfield(new JLabel("Goedkeurings datum: ", JLabel.LEFT), dateApproved = new JTextField(date_approved, 25));
    
    }
    
    private LinkedHashMap<String, Object> retrieveData() {
        
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        
        if(dbData != null) {
            data.put("id", dbData.get("tournament_id") + " " + dbData.get("user_id"));
        }
        
        data.put("tournament_id", tournaments.getSelectedItem());
        data.put("user_id", users.getSelectedItem());
        data.put("has_paid", hasPaid.getText());
        data.put("start_date", Utils.convertDate("yyyy-MM-dd", startDate.getText()));
        data.put("date_approved", Utils.convertDate("yyyy-MM-dd", dateApproved.getText()));
        
        ArrayList<String> errors = Validator.form(formBuilder, data);
        if(errors.isEmpty()) {
            return data;
        }
        
        return null;
    }
    
    private void newEdit() {
        
        DefaultComboBoxModel cTournaments = controller.getTournaments();
        DefaultComboBoxModel cUsers = controller.getUsers();
        
        formBuilder.addCombobox(new JLabel("Toernooi: ", JLabel.LEFT), tournaments = new JComboBox(cTournaments));
        formBuilder.addCombobox(new JLabel("Gebruiker: ", JLabel.LEFT), users = new JComboBox(cUsers));
        formBuilder.addTextfield(new JLabel("Betaald: ", JLabel.LEFT), hasPaid = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Start datum: ", JLabel.LEFT), startDate = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Goedkeurings datum: ", JLabel.LEFT), dateApproved = new JTextField(25));        
        
    }
}