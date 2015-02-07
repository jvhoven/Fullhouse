/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui.element;

import src.gui.Menu;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 *
 * @author Jeffrey
 */
public class Button extends JButton {
    
    private final int MAIN = 0;
    private final int DETAIL = 1;
    
    public String text;
    String img;
    int type;
    boolean active;
    
    Menu menu;
    
    public Button(String text, String img, int type, Menu menu) {
        super(text, new ImageIcon(img));
        
        this.text = text;
        this.img = img;
        this.type = type;
        this.menu = menu;
        
        style();
        addListener();
    }
    
    private void style(){
         if(type == MAIN) {
            
            // Styling voor main
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setVerticalTextPosition(SwingConstants.BOTTOM);
            setHorizontalTextPosition(SwingConstants.CENTER);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Styling
            Color lightGrey = new Color(105, 105, 105);
        
            setForeground(Color.WHITE);
            setFont(new java.awt.Font("Calibri", Font.BOLD, 14));
            setPreferredSize(new Dimension(180, 80));
            
        } else if(type == DETAIL) {
            
            // Styling voor detail
            
        }
    }
    
    public void setActive(boolean active){
        
        this.active = active;
        
        if(active) {
            setOpaque(true);
            setBackground(new Color(61, 76, 96));
            
        } else {
            setOpaque(false);
            setBackground(null);
        }
    
    }
    
    private void addListener(){
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                
                if(!active) {
                    setOpaque(true);
                    setBackground(new Color(61, 76, 96));
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                
                if(!active) {
                    setOpaque(false);
                    setBackground(null);
                }
                
            }
            
            @Override 
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              
                menu.setActive(text);
            }
            
        });
        
    }
    
   
    
}
