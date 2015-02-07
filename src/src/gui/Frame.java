/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jeffrey
 */
public class Frame extends JFrame {
    
    public Frame(final int width, final int height){
        super();
        
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setPreferredSize(new Dimension(width, height));
                setSize(new Dimension(width, height));
                setLocationRelativeTo(null);
                setLayout(new BorderLayout());
                setVisible(true); 
                pack();     
            }
        
        }); 
    }
    
    @Override
    public int getWidth() {
        return super.getWidth();
    }
    
    @Override
    public int getHeight() {
        return super.getHeight();
    }
    
}
