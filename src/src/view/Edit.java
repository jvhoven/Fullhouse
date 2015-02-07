/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import main.Application;
import src.controller.BaseController;
import src.gui.Formbuilder;
import app.controller.*;

/**
 *
 * @author Henk
 */
public abstract class Edit extends JFrame {
    
    public Map dbData;
    Object[] data;
    Object lastObjectId = null;
    
    public Formbuilder formBuilder;     
    public JPanel container;
    BaseController controller;
        
    int width;
    int height;
    
    public Edit(){
        
        super();
        container = new JPanel();
        preInit();    
    
    }
    
    public void setController(BaseController controller) {
        this.controller = controller;
    }
   
    public void init(TreeMap<String, Object> data){
        
        dbData = null;
        
        if(data != null) {
            try{ 
                Entry<String, Object> entry = data.firstEntry();
                
                String keyName = entry.getKey();
                
                switch(keyName) {
                    case "Participant-Id":
                        ParticipantController pController = (ParticipantController)controller;
                        dbData = pController.get((String)entry.getValue());
                        break;
                    case "Masterparticipant-Id":
                        MasterclassparticipantController mController = (MasterclassparticipantController)controller;
                        dbData = mController.get((String)entry.getValue());
                        break;
                    default:
                        dbData = controller.get(entry.getValue(), entry.getKey());
                        break;
                }
                
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        
        formBuilder = new Formbuilder();
        
        this.width = Application.WIDTH / 2;
        this.height = 700;
        
        container.setPreferredSize(new Dimension(Application.WIDTH / 2, height));
        container.setSize(new Dimension(Application.WIDTH / 2, height));
        container.setBackground(Color.BLUE);
        
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setPreferredSize(new Dimension(Application.WIDTH / 2, height));
                setSize(new Dimension(Application.WIDTH / 2, height));
                setLocationRelativeTo(null);
                setContentPane(container);
                setLayout(new BorderLayout());   
            }
            
        }); 
                  
        getContentPane().removeAll();
        render();
        getContentPane().invalidate();
        getContentPane().validate();
        getContentPane().repaint();
 
        pack();     
        
        setVisible(true);  
    }
    
    abstract public void preInit();
    abstract public void render();
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
}
