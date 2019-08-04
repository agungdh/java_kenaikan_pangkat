/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Classes;

/**
 *
 * @author user
 */
import java.awt.Color;
import java.awt.Component;
 
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
 
/**
 * @author ashraf_sarhan
 * 
 */
public class MendekatiTMT implements TableCellRenderer {
 
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
 
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);
 
        // Apply zebra style on table rows
        if (row % 2 == 0) {
            c.setBackground(Color.RED);
        } else {
            c.setBackground(Color.GREEN);
        }
 
        return c;
    }
 
}