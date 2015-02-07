/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.location;

import app.controller.*;
import java.awt.*;
import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import src.controller.Utils;
import src.gui.element.Heading;
import src.validator.Validator;

/**
 *
 * @author milan
 */
public class Edit extends src.view.Edit {
    
    JButton actionButton, cancelButton;
    JTextField postal, streetnr, address, city, maxCapacity;
    JComboBox locations, payments;
    
    Overview overview;
    LocationController controller;
    
    public Edit(LocationController controller, Overview overview) {
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
        
        Heading header = new Heading("Locatie", "detail");
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
                
                // Als er iets fout gaat bij het valideren
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
        
        formBuilder.addTextfield(new JLabel("Postcode: ", JLabel.LEFT), postal = new JTextField((String)dbData.get("postal"), 25));
        formBuilder.addTextfield(new JLabel("Straat: ", JLabel.LEFT), address = new JTextField((String)dbData.get("address"), 25));
        formBuilder.addTextfield(new JLabel("Straat nr: ", JLabel.LEFT), streetnr = new JTextField((String)dbData.get("streetnr"), 25));
        formBuilder.addTextfield(new JLabel("Plaats: ", JLabel.LEFT), city = new JTextField((String)dbData.get("city"), 25));
        formBuilder.addTextfield(new JLabel("Max capaciteit: ", JLabel.LEFT), maxCapacity = new JTextField(String.valueOf(dbData.get("max_capacity")), 25));
    
    }
    
    private LinkedHashMap<String, Object> retrieveData() {
        
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        
        if(dbData != null) {
            data.put("id", dbData.get("postal"));
        }
        
        data.put("postal", postal.getText());
        data.put("streetnr", streetnr.getText());
        data.put("address", address.getText());
        data.put("city", city.getText());
        data.put("max_capacity", maxCapacity.getText());
        
        ArrayList<String> errors = Validator.form(formBuilder, data);
        if(errors.isEmpty()) {
            return data;
        }
        
        return null;
    }
    
    private void newEdit() {
         formBuilder.addTextfield(new JLabel("Postcode: ", JLabel.LEFT), postal = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Straat: ", JLabel.LEFT), address = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Straat nr: ", JLabel.LEFT), streetnr = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Plaats: ", JLabel.LEFT), city = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Max capaciteit: ", JLabel.LEFT), maxCapacity = new JTextField(25));
        
        
    }
        
}