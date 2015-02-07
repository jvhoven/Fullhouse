/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import src.controller.Utils;

/**
 *
 * @author Henk
 */
public class Helper {
    
    /*
    * Valideer datum aan de hand van yyyy-MM-dd format
    *
    * @param String inDate
    */
    protected static boolean isValidDate(String inDate) {
        
        // Als de datum te lang is
        if(inDate.length() != 10) {
            return false;
        }
            
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException | NullPointerException pe) {
            return false;
        }
        
        return true;
    }
    
    /*
    * Valideer telefoonnummer
    * format: 0600000000 
    *         0790000000
    *
    * @param String phone
    */
    protected static boolean isValidPhone(String phone) {
                 
        String ePattern = "^[0-9]{10}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(phone);
        
        return m.matches();
    }
    
    /*
    * Valideer postcode met een regex
    * format: 2705ZJ
    * 
    * @param String postal
    */
    protected static boolean isValidPostal(String postal) {
        String ePattern = "^[0-9]{4}[a-zA-z]{2}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(postal);
        
        return m.matches();
    }
    
    /*
    * Valideer email met een regex
    *
    * @param String email
    */
    protected static boolean isValidEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    
    /*
    * Haalt alle errors uit de Validator en laat ze zien
    *
    * @param ArrayList<String> errors
    */
    protected static void showErrors(ArrayList<String> errors) {
        
        String message = "Verbeter de volgende fouten: \n\n";
        
        for(String error : errors) {
            message += "   - " + error + " \n";
        }
       
        message += "\n";
        Utils.sendMessage("Error", message, false);
    }
    
    /*
    * Bekijkt het verschil tussen twee datums, als het einde voor start is
    * returned de functie false
    *
    * @param Date start
    * @param Date end
    */
    protected static boolean compareDates(Date start, Date end) {
        return start.compareTo(end) <= 0;
    }
    
    /*
    * Haalt de naam van een label op die geprint word als "key" van een error
    *
    * @param JLabel label
    */
    protected static String getKey(JLabel label){
        return label.getText().replace(":", "");
    }
    
    /*
    * Haalt de default waarde uit comboboxes
    *
    * @param JComboBox combobox
    */
    public static String removeDefault(JComboBox comboBox) {
                
        if(comboBox.getSelectedItem() == null) {
            return " ";
        }
        return comboBox.getSelectedItem().toString().contains("- Selecteer") ? " " : comboBox.getSelectedItem().toString();
    }
    
    /*
    * Haalt postcode uit de totaal string van een locatie
    *
    * @param String locationData
    */
    public static String getPostal(String locationData) {
        
        try {
            String ePattern = "\\[([A-Za-z0-9.]+)\\]";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(locationData);
            m.find();

            return m.group(1);
        } catch(java.lang.IllegalStateException ex) {
            return " ";
        }
    }
}
