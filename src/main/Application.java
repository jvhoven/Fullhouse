/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.sql.SQLException;
import src.gui.Frame;
import src.view.Manager;

/**
 *
 * @author Henk
 */
public class Application {
    
    public static Manager manager; 
    public static Frame window;
    
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 960;
    
    public Application() throws SQLException{
        
        window = new Frame(WIDTH, HEIGHT);
        manager = new Manager();
        
        preInit();
        init();
        // Test case for Factory class
        //tests.Factory factory = new tests.Factory();
        
        // Test case for elo system
        //Rating rating = new tests.Rating();
        
        // Test case for matchmaking
       //new tests.Matchmaking();
        
        // Test case for controller functionality
        //new tests.Controller();
        
    }
    
    private void preInit(){
        
       //manager = new Manager();
        
    }
    
    private void init(){
       
   
    
    }
    
    
}
