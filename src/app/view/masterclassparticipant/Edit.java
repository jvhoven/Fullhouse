/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.masterclassparticipant;

import app.controller.*;
import java.awt.BorderLayout;
import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import src.gui.element.Heading;

/**
 *
 * @author milan
 */
public class Edit extends src.view.Edit {
    
    Overview overview;
    MasterclassparticipantController controller;
    
    JTextField hasPaid, startDate, dateApproved;
    JComboBox users, masterclasses;
    JButton actionButton, cancelButton;
    
    public Edit(MasterclassparticipantController controller, Overview overview) {
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
                try{
                    controller.save(data);
                    overview.rerender();
                    dispose();
                } catch(SQLException ex) {
                    
                }
            }
        });
    }
    
    private void edit(){
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String start_date = df.format((Date)dbData.get("start_date"));
        String date_approved = df.format((Date)dbData.get("date_approved"));
        
        MasterclassController masterclassController = new MasterclassController();
        UserController userController = new UserController();
        
        Map masterclass = new HashMap<>();
        Map user = new HashMap<>();
        
        try {
            masterclass = masterclassController.get(dbData.get("masterclass_id"), null);
            user = userController.get(dbData.get("user_id"), null);
        } catch(SQLException e) {
            
        }
        
        DefaultComboBoxModel cMasterclasses = controller.getMasterclasses();
        cMasterclasses.setSelectedItem(masterclass.get("name"));
        masterclasses = new JComboBox(cMasterclasses);
        masterclasses.setEditable(false);
        masterclasses.setEnabled(false);
        
        DefaultComboBoxModel cUsers = controller.getUsers();
        cUsers.setSelectedItem(user.get("name"));
        users = new JComboBox(cUsers);
        users.setEditable(false);
        users.setEnabled(false);
        
        int heeftBetaald = 0;
        
        if((boolean)dbData.get("has_paid")) {
            heeftBetaald = 1;
        }
        
        formBuilder.addCombobox(new JLabel("Masterclass: ", JLabel.LEFT), masterclasses );
        formBuilder.addCombobox(new JLabel("Gebruiker: ", JLabel.LEFT), users);
        formBuilder.addTextfield(new JLabel("Betaald: ", JLabel.LEFT), hasPaid = new JTextField(String.valueOf(heeftBetaald), 25));
        formBuilder.addTextfield(new JLabel("Start datum: ", JLabel.LEFT), startDate = new JTextField(start_date, 25));
        formBuilder.addTextfield(new JLabel("Goedkeurings datum: ", JLabel.LEFT), dateApproved = new JTextField(date_approved, 25));
    
    }
    
    private LinkedHashMap<String, Object> retrieveData() {
        
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        
        if(dbData != null) {
            data.put("id", dbData.get("masterclass_id") + " " + dbData.get("user_id"));
        }
        
        data.put("masterclass_id", masterclasses.getSelectedItem());
        data.put("user_id", users.getSelectedItem());
        data.put("has_paid", hasPaid.getText());
        data.put("start_date", startDate.getText());
        data.put("date_approved", dateApproved.getText());
      
        return data;
    }
    
    private void newEdit() {
        
        DefaultComboBoxModel cMasterclasses = controller.getMasterclasses();
        DefaultComboBoxModel cUsers = controller.getUsers();
        
        formBuilder.addCombobox(new JLabel("Masterclass: ", JLabel.LEFT), masterclasses = new JComboBox(cMasterclasses ));
        formBuilder.addCombobox(new JLabel("Gebruiker: ", JLabel.LEFT), users = new JComboBox(cUsers));
        formBuilder.addTextfield(new JLabel("Betaald: ", JLabel.LEFT), hasPaid = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Start datum: ", JLabel.LEFT), startDate = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Goedkeurings datum: ", JLabel.LEFT), dateApproved = new JTextField(25));        
        
    }
    
}