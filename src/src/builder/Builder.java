/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.builder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 *
 * @author Jeffrey
 */
public class Builder {
    
    public static <E> ArrayList<E> newArrayList(E... elements) {
        ArrayList<E> list = new ArrayList<>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }
    
    public static LinkedHashMap toLinkedHashMap(Object[] data, String[] comparisonData) {
        
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        for(int i = 0; i < data.length; i++) {
            result.put(comparisonData[i], data[i]);
        }
        
        return result;
        
    }
    
    public static LinkedHashMap<String, Object> buildMap(Object... data){
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        if(data.length % 2 != 0) 
            throw new IllegalArgumentException("Odd number of arguments");      

        String key = null;
        Integer step = -1;

        for(Object value : data){
            step++;
            switch(step % 2){
            case 0: 
                if(value == null)
                    throw new IllegalArgumentException("Null key value"); 
                key = (String) value;
                continue;
            case 1:             
                result.put(key, value);
                break;
            }
        }

        return result;
    }
    
    public static String dateToString(Object date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate returnDate = LocalDate.parse((String)date, formatter);

        return returnDate.toString();
    }
    
    public static Date stringToDate(Object date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        
        System.out.println(df.parse((String)date));
        
        try{
            return df.parse((String)date);
        } catch(ParseException ex) {
         
        }
        
        return null;
    }
}
