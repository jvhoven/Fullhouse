/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.gui.element;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.table.*;
import main.Application;
import src.view.Edit;

/**
 *
 * @author Henk
 */
public class Table {
    
    String[] columns;
    ResultSet data;
    JScrollPane object;
    JTable table;
    DefaultTableModel model;
    Edit editView;
    
    public Object active;
    
    public Table(String[] columns, Edit editPage){
        
        super();
        
        this.columns = columns;
        this.editView = editPage;
        //this.data = data;
        
        initComponents();
        styleTable();
        
    }
    
    private void initComponents(){
        
        model = new TableModel();
        table = new JTable(model);
                 
        model.setColumnIdentifiers(columns);
        
        object = new JScrollPane(table);
        object.setPreferredSize(new Dimension(Application.WIDTH - 150, 500));
        
        addListener();        
    }
    
    public void removeColumn(int columnIndex){
        table.getColumnModel().getColumn(columnIndex).setPreferredWidth(0);
        table.getColumnModel().getColumn(columnIndex).setMinWidth(0);
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
    }
    
    private void addListener(){
        
        table.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent me) {
                 
                try {
                    int row = table.getSelectedRow();
                    int column = table.getColumnCount();
                   
                    ArrayList<Object> list = new ArrayList();
                    for(int i = 0; i < column; i++) {
                        list.add(table.getValueAt(row, i));
                    } 

                    active = list.get(0);
                    TreeMap<String, Object> data = new TreeMap<>();
                    data.put(table.getColumnName(0), active);

                    if (me.getClickCount() == 2) {
                        editView.init(data);
                    }
                } catch(java.lang.ArrayIndexOutOfBoundsException e) {
                }
            }
        });    
    }
    
    public void add(Object[] obj) {
        model.addRow(obj);
    }
    
    private void styleTable() {
        
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(new Color(15, 69, 107));
        header.setPreferredSize(new Dimension(100, 35));
        header.setForeground(Color.WHITE);
        header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        
        table.setIntercellSpacing(new Dimension(0, 0));
        //table.setPreferredSize(new Dimension(Application.WIDTH - 150, 500));
        table.setShowGrid(false);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setRowHeight(table.getRowHeight() + 25); 

        TableCellRenderer renderer = new EvenOddRenderer();
        table.setDefaultRenderer(Object.class, renderer);
        
        object.setBorder(BorderFactory.createEmptyBorder());
        object.setOpaque(true);
        
        table.setOpaque(true);
    }
    
    public JScrollPane render() {
              
        return object;
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
