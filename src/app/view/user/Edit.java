/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.user;

import app.controller.UserController;
import java.awt.BorderLayout;
import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
    JTextField name, address, postal, city, phone, email, rating, totalScore, wins, losses;
    JComboBox locations, payments;
    
    Overview overview;
    UserController controller;
    
    public Edit(UserController controller, Overview overview) {
       
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
        
        Heading header = new Heading("Gebruikers", "detail");
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
                
        rating = new JTextField(String.valueOf((int)dbData.get("rating")), 25);
        totalScore = new JTextField(String.valueOf((int)dbData.get("total_score")), 25);        
        wins = new JTextField(String.valueOf((int)dbData.get("wins")), 25);
        losses = new JTextField(String.valueOf((int)dbData.get("losses")), 25);
        
        totalScore.setEditable(false);
        rating.setEditable(false);
        wins.setEditable(false);
        losses.setEditable(false);

        formBuilder.addTextfield(new JLabel("Naam: ", JLabel.LEFT), name = new JTextField((String)dbData.get("name"), 25));
        formBuilder.addTextfield(new JLabel("Adres: ", JLabel.LEFT), address = new JTextField((String)dbData.get("address"), 25));
        formBuilder.addTextfield(new JLabel("Postcode: ", JLabel.LEFT), postal = new JTextField((String)dbData.get("postal"), 25));
        formBuilder.addTextfield(new JLabel("Woonplaats: ", JLabel.LEFT), city = new JTextField((String)dbData.get("city"), 25));
        formBuilder.addTextfield(new JLabel("Telefoon: ", JLabel.LEFT), phone = new JTextField((String)dbData.get("phone"), 25));
        formBuilder.addTextfield(new JLabel("Email: ", JLabel.LEFT), email = new JTextField((String)dbData.get("email"), 25));
        formBuilder.addTextfield(new JLabel("Rating: ", JLabel.LEFT), rating);
        formBuilder.addTextfield(new JLabel("Totale score: ", JLabel.LEFT), totalScore);
        formBuilder.addTextfield(new JLabel("Gewonnen: ", JLabel.LEFT), wins);
        formBuilder.addTextfield(new JLabel("Verloren: ", JLabel.LEFT), losses);
    
    }
    
    private LinkedHashMap<String, Object> retrieveData() {
        
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        
        if(dbData != null) {
            data.put("id", (int)dbData.get("id"));
        }
            
        data.put("name", name.getText());
        data.put("address", address.getText());
        data.put("postal", postal.getText());
        data.put("city", city.getText());
        data.put("phone", phone.getText());
        data.put("email", email.getText());
        data.put("rating", rating.getText());
        data.put("total_score", totalScore.getText());
        data.put("wins", wins.getText());
        data.put("losses", losses.getText());
        
        ArrayList<String> errors = Validator.form(formBuilder, data);
        if(errors.isEmpty()) {
            return data;
        }
        
        return null;
    }
    
    private void newEdit() {
        
        rating = new JTextField(25);
        totalScore = new JTextField(25);        
        wins = new JTextField(25);
        losses = new JTextField(25);
        
        totalScore.setEditable(false);
        rating.setEditable(false);
        wins.setEditable(false);
        losses.setEditable(false);

        formBuilder.addTextfield(new JLabel("Naam: ", JLabel.LEFT), name = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Adres: ", JLabel.LEFT), address = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Postcode: ", JLabel.LEFT), postal = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Woonplaats: ", JLabel.LEFT), city = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Telefoon: ", JLabel.LEFT), phone = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Email: ", JLabel.LEFT), email = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Rating: ", JLabel.LEFT), rating);
        formBuilder.addTextfield(new JLabel("Totale score: ", JLabel.LEFT), totalScore);
        formBuilder.addTextfield(new JLabel("Gewonnen: ", JLabel.LEFT), wins);
        formBuilder.addTextfield(new JLabel("Verloren: ", JLabel.LEFT), losses);
        
    }
        
}