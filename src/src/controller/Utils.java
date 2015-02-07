/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Henk
 */
public class Utils {
    
    public static String convertDate(String format, String value) {
        
       SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        
       // If the value is null return date from today
       if(value == null) {
           Date date = new Date();
           value = dateFormatter.format(date);
       } else {
           value = value.replace("/", "-");
       }

       try {
           Date myDate = dateFormatter.parse(value);
           SimpleDateFormat mdyFormat = new SimpleDateFormat(format);

           // Format the date to Strings
           String mdy = mdyFormat.format(myDate);

           return mdy;
       } catch(ParseException ex) {
       }

       return null;
    }
    
    public static Date convertDate(String format, String value, boolean dates) {
        
       SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        
       // If the value is null return date from today
       if(value == null) {
           Date date = new Date();
           value = dateFormatter.format(date);
       } else {
           value = value.replace("/", "-");
       }

       try {
           Date myDate = dateFormatter.parse(value);
           SimpleDateFormat mdyFormat = new SimpleDateFormat(format);

           return myDate;
       } catch(ParseException ex) {
       }

       return null;
    }
    
    public static int sendMessage(String title, String message, boolean confirm) {
        if(confirm) {
            return JOptionPane.showConfirmDialog(null, message);
        } else {
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
        
        return 0;
    }
    
}
