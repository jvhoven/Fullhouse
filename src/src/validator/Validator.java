/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import src.controller.Utils;
import src.gui.Formbuilder;

/**
 *
 * @author Henk
 */
public class Validator {
        
    public static ArrayList<String> form(Formbuilder form, LinkedHashMap<String, Object> data) {
                
        // This will hold all errors
        ArrayList<String> errors = new ArrayList<>();
        
        for(Map.Entry<JLabel, String> entry : form.labels.entrySet()) {
                        
            boolean focusable = true;
            String target = entry.getValue();
            String[] parts = target.split("-");
            
            JLabel label = entry.getKey();
            String value = null;

            switch (parts[0]) {
                case "textfield":
                    JTextField textField = form.textFields.get(Integer.parseInt(parts[1]));
                    focusable = textField.isEditable();
                    value = textField.getText();
                    break;
                case "combobox":
                    JComboBox comboBox = form.comboBoxes.get(Integer.parseInt(parts[1]));
                    focusable = comboBox.isEditable();
                    
                    try {
                        value = Helper.removeDefault(comboBox);
                    } catch(java.lang.ClassCastException ex) {
                        value = String.valueOf(Helper.removeDefault(comboBox));
                    }
             
                    break;
            }
                 
            String labelText = label.getText().toLowerCase();         
            
            if((value.length() == 0) && focusable || value.equals(" ") && !labelText.contains("locatie")) {
                errors.add(label.getText().replace(":", "") + "moet ingevuld zijn.");
            } else {
                
                // Datum validaties
                if(labelText.contains("datum")) {
                    
                    // Check voor als het een nieuwe item is
                    boolean newItem = !data.containsKey("id");
                       
                    // Valideer datum als eerste
                    if(!Helper.isValidDate(Utils.convertDate("yyyy-MM-dd", value))) {
                        errors.add(label.getText().replace(":", "") + "is geen valide datum.");
                        
                    // Als het een nieuwe entry is, check of de datums niet in het verleden liggen    
                    } else if(newItem) {
                        Date today = Utils.convertDate("yyyy-MM-dd", null, true);
                        Date givenDate = Utils.convertDate("yyyy-MM-dd", value, true);
                        
                        if(!Helper.compareDates(today, givenDate)) {
                            errors.add(label.getText().replace(":", "") + "ligt in het verleden, wat niet mogelijk is.");
                        }
                    }
                    
                // Betaald is een boolean die allen 1 of 0 mag bevatten    
                } else if(labelText.contains("betaald")) {
                    
                    if(!(value.equals("1") || value.equals("0"))) {
                        errors.add(label.getText().replace(":", "") + "moet 1 of 0 zijn.");
                    }
                    
                // Email valideren    
                } else if(labelText.contains("email")) {
                    
                    if(!Helper.isValidEmail(value)) {
                       errors.add(Helper.getKey(label) + "is geen valide email adres.");
                    }
                    
                // Postcode valideren    
                } else if(labelText.contains("postcode")) {
                    
                    if(!Helper.isValidPostal(value)) {
                        errors.add(Helper.getKey(label) + "is geen valide postcode. [bijv: 2704BK]");
                    }
                    
                // Telefoonnummer valideren
                } else if(labelText.contains("telefoon")) {
                    
                    if(!Helper.isValidPhone(value)) {
                        errors.add(Helper.getKey(label) + "is geen valide telefoonnummer. [bijv: 0600000000, 079000000]");
                    }
                    
                } else if(labelText.contains("inschrijvingen")) {
                    
                    try{
                        Integer.parseInt(value);
                    } catch(NumberFormatException ex) {
                        errors.add(Helper.getKey(label) + "moet een numerieke waarde bevatten.");
                    }
                    
                }
  
            }
            
        }
        
        if(errors.size() > 0)
            Helper.showErrors(errors);
               
        return errors;
    }
       
}
