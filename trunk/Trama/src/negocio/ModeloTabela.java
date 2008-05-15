package negocio;

import visao.renderer.RendererLinha;
import persistencia.*;
import visao.*;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

public class ModeloTabela extends AbstractTableModel {
    private Matriz matriz;

    public ModeloTabela( String nome ) {
        super();
        matriz = new Matriz( nome );
    }

    @Override
    public int getRowCount() {
        return matriz.getLinhas();
    }

    @Override
    public int getColumnCount() {
        return matriz.getColunas();
    }

    @Override
    public Object getValueAt( int rowIndex, int columnIndex ) {
        return matriz.getDadoMatriz( rowIndex, columnIndex );
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
                return matriz.getTituloColuna( columnIndex );
        }
    }
}
