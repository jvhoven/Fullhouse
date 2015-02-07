/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.gui.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import main.Application;

/**
 *
 * @author Henk
 */
public class Heading extends JLabel {
    
    public Heading(String string){
        
        super(string.toUpperCase());
        setPreferredSize(new Dimension(Application.WIDTH, 50));

        setForeground( new Color(50, 62, 78));
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        setFont(f);
        
    }
    
    public Heading(String string, String type) {
        
        super(string.toUpperCase());
        
        if(type.equals("detail")) {
            
            setOpaque(true);
            setPreferredSize(new Dimension(300, 70));
            setForeground(Color.WHITE);
            setBackground(new Color(50, 62, 78));
            
            Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
            setFont(f);
        }
        
    }
    
}
