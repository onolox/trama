
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

public class Modelo1 extends AbstractTableModel {
    
    public Modelo1() {
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
