/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import admin.Bestellung;
import java.util.Vector;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Marco
 */
public class OrderTableModel implements TableModel
{

    String[] columnNames = {"Kunde", "Artikel", "Menge", "Datum"};
    Vector<Bestellung> modelorders;

    public OrderTableModel(Vector<Bestellung> modelorders) {
        this.modelorders = modelorders;
    }

    @Override
    public int getRowCount() {
        return modelorders.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
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
                    return modelorders.get(rowIndex).getKunde().getVorname() + " " + modelorders.get(rowIndex).getKunde().getZuname();
                case 1:
                    return modelorders.get(rowIndex).getArtikel().getBezeichnung();
                case 2:
                    return modelorders.get(rowIndex).getMenge();
                case 3:
                    return modelorders.get(rowIndex).getBestellungPK().getDatum();              
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
