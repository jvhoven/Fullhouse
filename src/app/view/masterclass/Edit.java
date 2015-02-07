/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.masterclass;

import app.controller.MasterclassController;
import app.controller.UserController;
import java.awt.BorderLayout;
import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
    
    JButton actionButton, cancelButton;
    JTextField start_date,min_rating,price,name;
    JComboBox teacher, locations;
    
    Overview overview;
    MasterclassController controller;
    
    public Edit(MasterclassController controller, Overview overview) {
       
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
        
        Heading header = new Heading("Masterclass", "detail");
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
                
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format((Date)dbData.get("start_date"));
        BigDecimal deciPrice = (BigDecimal)dbData.get("price");
        
        start_date = new JTextField(date, 25);
        min_rating = new JTextField(String.valueOf((int)dbData.get("min_rating")), 25);        
        price = new JTextField(deciPrice.toString(), 25);
        
        UserController userController = new UserController();
        
        DefaultComboBoxModel cTeacher = controller.getTeachers();
        teacher = new JComboBox(cTeacher);
        
        DefaultComboBoxModel cLocations = controller.getLocations();
        cLocations.setSelectedItem(dbData.get("location"));
        
        if(dbData.get("teacher") != null) {
            
            Map user = new HashMap<>();
            try {
                user = userController.get((int)dbData.get("teacher"), null);
            } catch(SQLException ex) {
                
            }
            teacher.setSelectedItem(user.get("name"));
        }
        
        formBuilder.addTextfield(new JLabel("Naam: ", JLabel.LEFT), name = new JTextField((String)dbData.get("name"), 25));
        formBuilder.addTextfield(new JLabel("Start datum: ", JLabel.LEFT), start_date);
        formBuilder.addTextfield(new JLabel("Minimale rating: ", JLabel.LEFT), min_rating = new JTextField(String.valueOf((int)dbData.get("min_rating")), 25));
        formBuilder.addTextfield(new JLabel("Prijs: ", JLabel.LEFT), price);
        formBuilder.addCombobox(new JLabel("Locatie: ", JLabel.LEFT), locations = new JComboBox(cLocations));
        formBuilder.addCombobox(new JLabel("Leraar: ", JLabel.LEFT), teacher);

    
    }
    
    private LinkedHashMap<String, Object> retrieveData() {
        
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        
        if(dbData != null) {
            data.put("id", (int)dbData.get("id"));
        }
            
        data.put("name", name.getText());
        data.put("start_date", Utils.convertDate("yyyy-MM-dd", start_date.getText()));
        data.put("min_rating", min_rating.getText());
        data.put("teacher", Helper.removeDefault(teacher));
        
        String location = Helper.removeDefault(locations);
        if(!location.equals(" ")) {
            location = Helper.getPostal(location);
        }
        
        data.put("location", location);
        data.put("price", price.getText());
 
        ArrayList<String> errors = Validator.form(formBuilder, data);
        if(errors.isEmpty()) {
            return data;
        }
        
        return null;
    }
    
    private void newEdit() {
        
        DefaultComboBoxModel cTeacher = controller.getTeachers();
        teacher = new JComboBox(cTeacher);

        formBuilder.addTextfield(new JLabel("Naam: ", JLabel.LEFT), name = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Startdatum: ", JLabel.LEFT), start_date = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Minimale Rating: ", JLabel.LEFT), min_rating = new JTextField(25));
        formBuilder.addTextfield(new JLabel("Prijs: ", JLabel.LEFT), price = new JTextField(25));
        formBuilder.addCombobox(new JLabel("Locatie: ", JLabel.LEFT), locations = new JComboBox(controller.getLocations()));
        formBuilder.addCombobox(new JLabel("Leraar: ", JLabel.LEFT), teacher);
        
    }
    
}