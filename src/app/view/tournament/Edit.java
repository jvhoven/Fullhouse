/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.tournament;

import app.controller.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import src.controller.Utils;
import src.gui.element.Heading;
import src.validator.Helper;
import src.validator.Validator;

/**
 *
 * @author milan
 */
public class Edit extends src.view.Edit {
    
    JButton startButton, actionButton, cancelButton;
    JTextField name, startDate, description, minParticipants;
    JComboBox locations, payments, playersPerTable;
    
    Overview overview;
    TournamentController controller;
    
    public Edit(TournamentController controller, Overview overview) {
        super.setController(controller);
        
        this.controller = controller;
        this.overview = overview;
        
        startButton = new JButton("Start");
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
        
        Heading header = new Heading("Toernooi", "detail");
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
        
        if(dbData != null) {
            buttonPanel.add(startButton);
        }
        
        buttonPanel.add(actionButton);
        buttonPanel.add(cancelButton);
        
        container.add(buttonPanel, BorderLayout.PAGE_END);
    }
    
    private void addListeners(){
        
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controller.canStart((int) dbData.get("id"))) {
                    controller.startMatchmaking(dbData);
                }
            }
        });
        
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
                 
                if(data == null) {
                    return;
                }
                
                try{
                    controller.save(data);
                    overview.rerender();
                    dispose();
                    Utils.sendMessage("Success", "Item succesvol opgeslagen.", false);
                } catch(SQLException ex) {
                    Utils.sendMessage("Error", "Er is iets misgegaan met het opslaan van de data.", false);
                }
            }
        });
    }
    
    private void edit(){
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format((Date)dbData.get("start_date"));

        LocationController lController = new LocationController();
        DefaultComboBoxModel cLocations = lController.getAllCombo();
        if(lController.getValueComboBox((String)dbData.get("location")) != null) {
            cLocations.setSelectedItem(lController.getValueComboBox((String)dbData.get("location")));
        }
        
        DefaultComboBoxModel cParticipantsPerTable = controller.getRandomValues(1, 20);
        cParticipantsPerTable.setSelectedItem(dbData.get("players_per_table"));
        
        DefaultComboBoxModel cPayments = controller.getRandomValues(10, 20);
        cPayments.setSelectedItem(dbData.get("payment"));
        
        formBuilder.addTextfield(new JLabel("Naam: ", JLabel.LEFT), name = new JTextField((String)dbData.get("name"), 25));
        formBuilder.addTextfield(new JLabel("Startdatum: ", JLabel.LEFT), startDate = new JTextField(date, 25));
        formBuilder.addTextfield(new JLabel("Beschrijving: ", JLabel.LEFT), description = new JTextField((String)dbData.get("description"), 25));
        formBuilder.addTextfield(new JLabel("Minimaal inschrijvingen: ", JLabel.LEFT), minParticipants = new JTextField(String.valueOf((int)dbData.get("min_participants"))));
        formBuilder.addCombobox(new JLabel("Spelers per sessie: ", JLabel.LEFT), playersPerTable = new JComboBox(cParticipantsPerTable));
        formBuilder.addCombobox(new JLabel("Locatie: ", JLabel.LEFT), locations = new JComboBox(cLocations));
        formBuilder.addCombobox(new JLabel("Inleg: ", JLabel.LEFT), payments = new JComboBox(cPayments));
    
    }
    
    private LinkedHashMap<String, Object> retrieveData() {
        
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        
        if(dbData != null) {
            data.put("id", (int)dbData.get("id"));
        }
                    
        data.put("name", name.getText());
        data.put("start_date", Utils.convertDate("yyyy-MM-dd", startDate.getText()));
        data.put("description", description.getText());
        data.put("min_participants", minParticipants.getText());
        data.put("players_per_table", Helper.removeDefault(playersPerTable));
        
        String location = Helper.removeDefault(locations);
        if(!location.equals(" ")) {
            location = Helper.getPostal(location);
        }
        
        data.put("location", location);
        data.put("payment", Helper.removeDefault(payments));
                
        ArrayList<String> errors = Validator.form(formBuilder, data);
        
        if(errors.isEmpty()) {
            return data;
        }
        
        return null;
    }
    
    private void newEdit() {
        
        LocationController lController = new LocationController();
        
        formBuilder.addTextfield(new JLabel("Naam: ", JLabel.LEFT), name = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Startdatum: ", JLabel.LEFT), startDate = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Beschrijving: ", JLabel.LEFT), description = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Minimaal inschrijvingen: ", JLabel.LEFT), minParticipants = new JTextField(25));
        formBuilder.addCombobox(new JLabel("Spelers per sessie: ", JLabel.LEFT), playersPerTable = new JComboBox(controller.getRandomValues(1, 20)));
        formBuilder.addCombobox(new JLabel("Locatie: ", JLabel.LEFT), locations = new JComboBox(lController.getAllCombo()));
        formBuilder.addCombobox(new JLabel("Inleg: ", JLabel.LEFT), payments = new JComboBox(controller.getRandomValues(10, 20)));
        
    }
        
}