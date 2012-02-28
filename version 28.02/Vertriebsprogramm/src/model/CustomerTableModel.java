/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import admin.Kunde;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Mike
 */
public class CustomerTableModel implements TableModel {

    String[] columnNames = {"Vorname", "Zuname", "Straße", "PLZ", "Ort", "Routenzuordnung"};
    Vector<Kunde> modelcustomers;

    public CustomerTableModel(Vector<Kunde> modelcustomers) {
        this.modelcustomers = modelcustomers;
    }

    @Override
    public int getRowCount() {
        return modelcustomers.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
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
    public Object getValueAt(int rowIndex, int columnIndex){
                   switch (columnIndex) {
                case 0:
                    return modelcustomers.get(rowIndex).getVorname().toString();
                case 1:
                    return modelcustomers.get(rowIndex).getZuname().toString();
                case 2:
                    return modelcustomers.get(rowIndex).getStrasse().toString();
                case 3:
                    return modelcustomers.get(rowIndex).getPostleitzahl();
                case 4:
                    return modelcustomers.get(rowIndex).getOrt().toString();
                case 5:
                    return modelcustomers.get(rowIndex).getRouteid().getRoutename();
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
