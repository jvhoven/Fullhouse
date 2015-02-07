/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view.matchmaking;

import app.controller.MatchmakingController;
import app.model.Participant;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author milan
 */
public class Overview extends JFrame {
    
    public final static int WIDTH = 960;
    public final static int HEIGHT = 500;
    
    JPanel buttonPane;
    JTable table;
    TableModel model;
    JScrollPane pane;
    JButton volgRnd, slctWnr;
    
    Object eerstePlaats, tweedePlaats, derdePlaats;
    MatchmakingController controller;
    
    ArrayList<Participant> winnaars = new ArrayList<>();
    ArrayList<Participant> participants;

    int sessionSize;
    double numberSessions;
    int ronde;
    int inleg;
    int totaalMensjes;
    boolean finaleCheck;
    
    public Overview(ArrayList<Participant> participants, int sessionSize, double numberSessions, int ronde) {
        
        showMessage("Selecteer winnaars per tafel", "uitleg");
        
        this.participants = participants;
        totaalMensjes = participants.size();
        this.sessionSize = sessionSize;
        this.numberSessions = numberSessions;
        this.controller = new MatchmakingController();
        this.ronde = ronde;
        inleg = participants.get(0).getTournament().getInt("payment");
        
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setPreferredSize(new Dimension(WIDTH, HEIGHT));
                setSize(new Dimension(WIDTH, HEIGHT));
                setLocationRelativeTo(null);
                setLayout(new BorderLayout());
            }
        
        }); 
        
        init();
        addListener();
        buttonListeners();
        render();
           
        pack(); 
        setVisible(true);       
    }
    
    private void init() {
                
        // Alles initialisere      
        finaleCheck = false;
        model = new TableModel();
        String[] kolommen = {"Naam", "Toernooi", "Tafel"};
        model.setColumnIdentifiers(kolommen);
        
        table = new JTable(model);
        TableCellRenderer renderer = new EvenOddRenderer();
        table.setDefaultRenderer(Object.class, renderer);
        
        pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(940, 400));
        
        buttonPane = new JPanel();
        BoxLayout newLayout = new BoxLayout(buttonPane, BoxLayout.LINE_AXIS);
        buttonPane.setLayout(newLayout);
        
        volgRnd = new JButton("Volgende ronde!");
        volgRnd.setPreferredSize(new Dimension(0, 40));
        slctWnr = new JButton("Selecteer winnaars");
        slctWnr.setPreferredSize(new Dimension(0, 40));
    }
    
    private void render() {
                
        if(winnaars.size() > 0) {
            winnaars.clear();
        }        
        
        // Alles rendere        
        int tableCounter = 0;
        
        for (int i = 0; i < participants.size(); i++) {
             
            if(i % sessionSize == 0) {
                tableCounter++;
            }
            
            Participant part = participants.get(i);
            
            ArrayList Object = new ArrayList<>();
            Object.add(part.getUser().get("name"));
            Object.add(part.getTournament().get("name"));
            Object.add(tableCounter);
            
            model.addRow(Object.toArray());
            model.setRowCount(i + 1);
        }
        
        buttonPane.add(volgRnd);
        buttonPane.add(slctWnr);
        
        add(pane, BorderLayout.NORTH);
        add(buttonPane, BorderLayout.SOUTH);  

    }
    
    private void addListener(){
        
        table.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent me) {
                
                if (me.getClickCount() == 2) {

                    int row = table.getSelectedRow();
                    int column = table.getColumnCount();
                    int rowCount = table.getRowCount();

                    ArrayList<Object> list = new ArrayList();
                    for(int i = 0; i < column; i++) {
                        list.add(table.getValueAt(row, i));
                    }

                    if(winnaars.size() == numberSessions && finaleCheck == false) {
                        showMessage("Je hebt al genoeg spelers geselecteerd, ga verder naar de volgende ronde.", "Selectiefout!");
                    } else {
                        
                        double roundTable = Math.floor((row / sessionSize)+1);
                        int table = (int)roundTable;

                        int tableMax = table * sessionSize;
                        int tableMin = tableMax - sessionSize;
                        
                        // Als er minder mensen in de laatste tafel zitten
                        if(tableMax > rowCount) {
                            tableMax = rowCount;
                        }

                        // Haal alle andere participants uit dezelfde tafel op
                       
                        if(alreadyAdded(row)) {
                            showMessage("Je hebt deze speler al als winnaar toegevoegd", "Selectiefout");
                        } else if(tableHasWinner(tableMax, tableMin) && finaleCheck == false){
                            showMessage("Er is al een winnaar voor deze tafel gekozen", "Selectiefout");
                        } else {
                            if(showConfirm("Weet je zeker dat je deze persoon als winnaar wilt opgeven?") == 0) {

                                ArrayList<Participant> losers = new ArrayList<>();

                                int winnaarFinale = 0;
                                boolean winnaarGekozen = false;

                                for(tableMin = tableMin; tableMin < tableMax; tableMin++) {
                                    if(!finaleCheck) {
                                        if(tableMin != row) {
                                            losers.add(participants.get(tableMin));
                                        } 
                                    } else {
                                        if(!winnaarGekozen) {
                                            winnaarFinale = row;
                                            winnaarGekozen = true;
                                        } else {
                                            if(tableMin != winnaarFinale) {
                                                losers.add(participants.get(tableMin));
                                            } 
                                        }
                                    }
                                }

                                Participant winnaar;

                                if(finaleCheck) {
                                    winnaar = participants.get(winnaarFinale);
                                    winnaars.add(participants.get(row));
                                } else {
                                    winnaar = participants.get(row);
                                    winnaars.add(winnaar);
                                }

                                if(losers.size() > 0) {
                                    controller.calculateScores(winnaar, losers);
                                }
                            }
                        }
                    }
                }
            }
        });    
    }
    
    private void buttonListeners(){
        volgRnd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!finaleCheck) {
                    if(winnaars.size() == numberSessions) {
                            numberSessions = Math.ceil((double)winnaars.size() / (double)sessionSize);
                            int rowCount = table.getRowCount(); 

                            /*
                            * Finale checks
                            *
                            * Eerste om sessionSize een goede waarde te geven
                            * Tweede om de finale aan te geven
                            */
                            if(winnaars.size() == 3 && rowCount == (3 * sessionSize)) {
                                sessionSize = 3;
                                numberSessions = 1;
                            }

                            if(numberSessions <= 1) {
                                System.out.println("finale = true");
                                sessionSize = 3;
                                finaleCheck = true;
                            }

                            participants = (ArrayList)winnaars.clone();

                            if(!finaleCheck) {
                                boolean fairSessions = false;

                                int alleSpelers = participants.size();
                                int sessieGrootte = sessionSize;
                                double aantalSessies = Math.ceil((double)alleSpelers / (double)sessieGrootte);
                                int teKort = (int)aantalSessies * sessieGrootte - alleSpelers;

                                while(!fairSessions) {

                                    //System.out.println("te kort : " + teKort + " \n" + "alle spelers: " + alleSpelers + " \n" + "sessie grootte: " + sessieGrootte + " \n" + "aantal sessies: " + (int)aantalSessies);

                                    if(aantalSessies < 3) {
                                        sessieGrootte = alleSpelers / 3;
                                        numberSessions = 3;
                                        sessionSize = sessieGrootte;
                                        fairSessions = true;
                                    } else {
                                        if(teKort == 0 || teKort == 1) {
                                            fairSessions = true;
                                        } else if(sessieGrootte - teKort >= 2){
                                            numberSessions = aantalSessies;
                                            sessionSize = sessieGrootte;
                                            fairSessions = true;
                                        } else {
                                            sessieGrootte--;
                                            aantalSessies = Math.ceil((double)alleSpelers / (double)sessieGrootte);
                                            teKort = (int)aantalSessies * sessieGrootte - alleSpelers;
                                        }   
                                    }
                                }
                            }

                            getContentPane().removeAll();
                            getContentPane().invalidate();
                            getContentPane().revalidate();
                            getContentPane().repaint();

                            model.setRowCount(0);
                            render();

                            getContentPane().invalidate();
                            getContentPane().revalidate();
                            getContentPane().repaint();
                            //render();
                        } else {
                            showMessage("Nog niet genoeg spelers geselecteerd.", "Selectiefout!");
                        }
                } else {
                    showMessage("Je zit in de finale, er is geen volgende ronde.", "Logicafout!");
                }
            }
        });
        
        slctWnr.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(winnaars.size() == 3 && finaleCheck == true){
                    eerstePlaats = winnaars.get(0).getUser().get("name");
                    tweedePlaats = winnaars.get(1).getUser().get("name");
                    derdePlaats = winnaars.get(2).getUser().get("name");
                    System.out.println("1e " + eerstePlaats + "  2e " + tweedePlaats + " 3e " + derdePlaats);
                    
                    
                    getContentPane().removeAll();
                    getContentPane().invalidate();
                    getContentPane().revalidate();
                    getContentPane().repaint();
                    
                    JTextArea erepodium = new JTextArea(
                            "Eerste plaats: " + eerstePlaats + ", wint €" + ((totaalMensjes * inleg) * 0.40) + "   \n" +
                            "Tweede plaats: " + tweedePlaats + ", wint €" + ((totaalMensjes * inleg) * 0.25) + "   \n" +
                            "Derde plaats: " + derdePlaats + ", wint €" + ((totaalMensjes * inleg) * 0.10) + "    \n"
                    );
                    
                    getContentPane().add(erepodium);
                    erepodium.setEditable(false);
                    //repaint ALL the things!
                    getContentPane().invalidate();
                    getContentPane().revalidate();
                    getContentPane().repaint();
                }
            }
            
        });
    }
    
    private boolean tableHasWinner(int tableMax, int tableMin) {
        
        boolean exists = false;
     
        
        for(tableMin = tableMin; tableMin < tableMax; tableMin++) {
            Participant part = participants.get(tableMin);
 
            for(int i = 0; i < winnaars.size(); i++){
           
                if(winnaars.get(i).equals(part)) {
                    exists = true;
                }
            }
        }
        
        return exists;
    }
    
    // Kijkt of de geselecteerde participant al als winnaar voorkomt
    private boolean alreadyAdded(int row){
        boolean alreadyAdded = false;
   
        for(Participant winnaar : winnaars) {
            if(winnaar.equals(participants.get(row))) {
                alreadyAdded = true;
            }
        }
         
        return alreadyAdded;
    }
    
    private void showMessage(String msg, String tit) {
        JOptionPane.showMessageDialog(this, msg, tit, JOptionPane.WARNING_MESSAGE);
    }
    
    private int showConfirm(String msg) {
        int confirm = JOptionPane.showConfirmDialog(this, msg);
        
        return confirm;
    }
    
    class EvenOddRenderer implements TableCellRenderer {

        public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(
              table, value, isSelected, hasFocus, row, column);
            ((JLabel) renderer).setOpaque(true);
            Color foreground, background;
            if (isSelected) {
                foreground = new Color(17, 17, 17);
                background = new Color(177, 230, 250);
            } else {
                if (row % 2 == 0) {
                    foreground = new Color(17, 17, 17);
                    background = Color.white;
                } else {
                    foreground = new Color(17, 17, 17);
                    background = new Color(242, 242, 242);
                }
            }
            renderer.setForeground(foreground);
            renderer.setBackground(background);
           
            return renderer;
        }
    }
    
    public class TableModel extends DefaultTableModel {

      @Override
      public boolean isCellEditable(int row, int column){  
          return false;  
      }

    }
}
