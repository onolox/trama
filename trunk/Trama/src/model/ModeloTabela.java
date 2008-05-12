package model;


import view.*;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

public class ModeloTabela extends AbstractTableModel {
    
    public ModeloTabela() {
        super();

    }

    public int getRowCount() {
        return 25;
    }

    public int getColumnCount() {
        return 25;
    }

    public Object getValueAt( int rowIndex, int columnIndex ) {

        switch ( columnIndex ) {
            case 0:
                return "Linha";
            default:
                return "X";
        }
    }

    @Override
    public Class getColumnClass( int columnIndex ) {

        switch ( columnIndex ) {
            case 0:
                return RendererLinha.class;
            default:
                return String.class;
        }

    }

    @Override
    public String getColumnName( int columnIndex ) {
        switch ( columnIndex ) {
            case 0:
                return "";
            default:
                return "Coluna";
        }
    }
    
}
