/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.gui;

import java.awt.*;
import java.util.LinkedHashMap;
import javax.swing.JPanel;
import src.gui.element.Button;

/**
 *
 * @author Henk
 */
public class Layout extends JPanel {
    
    Frame window;
    public Menu menu;
    Color darkBlue = new Color(50, 62, 78);
    
    JPanel topPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel menuPanel = new JPanel();
    
    int width, height;
    
    public Layout(Frame window) {
        this.window = window;
        this.menu = new Menu();
        
        init();
        render();
    }
    
    private void init(){
        
        width = window.getWidth();
        height = window.getHeight();
                
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setSize(new Dimension(width, height));
        
        menuPanel.setPreferredSize(new Dimension(width, 80));
        menuPanel.setBackground(darkBlue);
        
        renderMenu();
        
        topPanel.setPreferredSize(new Dimension(width, 24));
        topPanel.setBackground(Color.LIGHT_GRAY);
    }
    
    public void render() {
        
        window.add(menuPanel, BorderLayout.SOUTH);
        window.add(topPanel, BorderLayout.NORTH);
        window.add(centerPanel, BorderLayout.CENTER);
    }
        
    public JPanel getCenter() {
        return centerPanel;
    }
    
    public void changeMenu(){
        
        window.remove(menuPanel);
        
        FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
        flow.setHgap(10);
        flow.setVgap(10);
        centerPanel.setLayout(flow);
        
        menuPanel.setSize(new Dimension(100, height));
        menuPanel.setPreferredSize(new Dimension(100, height));
        
        window.add(menuPanel, BorderLayout.WEST);
        
    }
    
    public void renderMenu() {
    
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        FlowLayout newLayout = new FlowLayout();
        newLayout.setVgap(0);
        menuPanel.setLayout(newLayout);
        menuPanel.setOpaque(true);
        menuPanel.setBackground(darkBlue);

        LinkedHashMap menuElements = menu.getElements();
        for(Object key : menuElements.keySet()) {
            Button button = (Button) menuElements.get(key);
            menuPanel.add(button);
        }
    }
    
   
}
