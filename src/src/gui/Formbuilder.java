/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.*;

/**
 *
 * @author Henk
 */
public class Formbuilder {
    
    public ArrayList<JTextField> textFields;
    public ArrayList<JComboBox> comboBoxes;
    public LinkedHashMap<JLabel, String> labels;
    JTextArea textArea;
    JButton submitButton;
    
    Map data;
    
    public Formbuilder(){
        
        textFields = new ArrayList<>();
        comboBoxes = new ArrayList<>();
        labels = new LinkedHashMap<>();
    }
        
    public void addTextfield(JLabel label, JTextField textField) {
              
        textField.setSize(new Dimension(250, 100));
        textField.setMaximumSize(new Dimension(250, 40));
        
        textFields.add(textField);
        labels.put(label, "textfield-" + String.valueOf(textFields.size() - 1));
        
        label.setLabelFor(textField);
        // Eventual styling
    }

    public void addCombobox(JLabel label, JComboBox box) {
        
        comboBoxes.add(box);
        labels.put(label, "combobox-" + String.valueOf(comboBoxes.size() - 1));
        
        label.setLabelFor(box);
     
    }
        
    public JPanel render() {
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 500));
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
                        
        for(Entry<JLabel, String> entry : labels.entrySet()) {
                        
            String target = entry.getValue();
            String[] parts = target.split("-");
            
            JLabel label = entry.getKey();
            panel.add(label);
            
            switch (parts[0]) {
                case "textfield":
                    JTextField textField = textFields.get(Integer.parseInt(parts[1]));
                    panel.add(textField);
                    break;
                case "combobox":
                    JComboBox comboBox = comboBoxes.get(Integer.parseInt(parts[1]));
                    panel.add(comboBox);
                    break;
            }
            
        }
        
        SpringUtilities.makeCompactGrid(panel,
            labels.size(), 2, //rows, cols
            20, 20,        //initX, initY
            10, 15        //xPad, yPad
        );       
        
        return panel;
    }
    
}
