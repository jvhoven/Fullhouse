/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.view;

import javax.swing.JPanel;
import src.gui.Layout;
import src.gui.Menu;

/**
 *
 * @author Henk
 */
public abstract class View {
    
    public Menu menu;
    public Layout layout;
    public JPanel container;
            
    public View(Layout layout){

        this.layout = layout;
        this.container = layout.getCenter();
        this.menu = layout.menu;
     
    }
    
    public Layout retrieveLayout(){
        return layout;
    } 
    
    public void init(){

    }
    
    public void rerender() {
        
        container.removeAll();
        init();
        
        container.revalidate();
        container.repaint();
    }
    
    
    public abstract void preInit();
    public abstract void render();
    
}
