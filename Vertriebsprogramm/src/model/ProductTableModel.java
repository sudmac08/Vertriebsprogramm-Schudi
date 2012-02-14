/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Vector;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Mike
 */
public class ProductTableModel implements TableModel{

    
    
    String[] columnNames = {"Artikelbezeichnung"};
    Vector modelproducts;//Kunden

    public ProductTableModel(Vector modelproducts) {
        this.modelproducts = modelproducts;
    }

    @Override
    public int getRowCount() {
        return modelproducts.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex)
        {
//            case 0: return modelproducts.get(rowIndex).getArtikel().getArtikelBezeichnung();          
        }
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }

    
}
