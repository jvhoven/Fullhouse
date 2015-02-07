package src.view;

import java.util.LinkedHashMap;
import java.util.Map;
import main.Application;
import src.gui.Layout;

public class Manager {
    
    Layout layout;
    
    private final Map<String, View> views = new LinkedHashMap<>();
    
    private String CURRENT_VIEW = "";
    private boolean menuChanged = false;
    
    public Manager(){
        
        layout = new Layout(Application.window);
        
        views.put("", new app.view.start.Default(layout));
        views.put(layout.menu.TOURNAMENT, new app.view.tournament.Overview(layout));
        views.put(layout.menu.USERS, new app.view.user.Overview(layout));
        views.put(layout.menu.MASTERCLASS, new app.view.masterclass.Overview(layout));
        views.put(layout.menu.SUBSCRIPTIONS, new app.view.participant.Overview(layout));
        views.put(layout.menu.MASTER_PARTICIPANTS, new app.view.masterclassparticipant.Overview(layout));
        views.put(layout.menu.LOCATIONS, new app.view.location.Overview(layout));
        
        setView(CURRENT_VIEW);
    }
    
    /*
    * Een nieuwe active panel initialiseren
    * dit betekent dus dat een nieuwe panel naar voren word gehaald
    */
    public void setView(String view){
        
        layout.getCenter().removeAll();
        views.get(view).init();
               
        CURRENT_VIEW = view;
       
        if(needsNewMenu()) {
   
            layout.changeMenu();
            menuChanged = true;
         
        }
        
        layout.getCenter().revalidate();
        layout.getCenter().repaint();
      
    }
    
    private boolean needsNewMenu(){
        if(!CURRENT_VIEW.equals("")) {
           if(menuChanged == false) {
               return true;
           }
       }
        
        return false;
    }
       
    /*
    * Een panel object ophalen uit de lijst met panels
    */
    public View getView(String view) {
        return views.get(view);
    } 
    
    /*
    * Haalt de panel die nu getoond wordt op
    */
    public View getActiveView() {
        return views.get(CURRENT_VIEW);
    }
   
    
}
