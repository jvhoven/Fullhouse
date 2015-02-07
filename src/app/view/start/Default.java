/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.view.start;

import java.awt.*;
import javax.swing.*;
import src.gui.Frame;
import src.gui.Layout;

/**
 *
 * @author Henk
 */
public class Default extends src.view.View {
    
    // Elements
    JLabel logo, icon, credits;
    
    public Default(Layout layout){
        
        super(layout);
    }
    
    @Override
    public void preInit(){
        
    }
    
    @Override
    public void init(){
        
        super.init();
              
        //center.setSize(new Dimension(Application.WIDTH, 700));
        //center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        
        Color darkGrey = new Color(89, 89, 89);
        
        logo = new JLabel("FULL HOUSE", JLabel.CENTER);
        icon = new JLabel("℗", JLabel.CENTER);
        credits = new JLabel("Gemaakt door Rutger Burggraaf, Milan Fazzi, Eric Johanssen en Jeffrey van Hoven", JLabel.CENTER);
        
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        credits.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logo.setFont(new java.awt.Font("Calibri", Font.BOLD, 48));
        icon.setFont(new java.awt.Font("Calibri", Font.BOLD, 300));
        credits.setFont(new java.awt.Font("Calibri", 0, 16));
        
        logo.setForeground(darkGrey);
        icon.setForeground(darkGrey);  
        
        render();
    }
    
    @Override
    public void render(){
        
        JPanel innerPanel = new JPanel();
        
        innerPanel.setMaximumSize(new Dimension(5, 5));
        innerPanel.setPreferredSize(new Dimension(5, 5));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));
        innerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(Box.createVerticalBox());
        
        innerPanel.add(Box.createRigidArea(new Dimension(0, 120)));
        innerPanel.add(icon);
        innerPanel.add(logo);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        innerPanel.add(credits);
        
        //addMenu();
        
        container.add(innerPanel, BorderLayout.CENTER);
        
        container.revalidate();
        container.repaint();
    }

    
}
