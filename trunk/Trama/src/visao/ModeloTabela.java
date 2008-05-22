package visao;

import negocio.Matriz;
import javax.swing.table.AbstractTableModel;
import visao.renderizador.RenderizadorTituloLinha;

public class ModeloTabela extends AbstractTableModel {
    Matriz matriz;
	public RenderizadorTituloLinha m_RenderizadorTituloLinha;

    public ModeloTabela( String nome ) {
        super();
        matriz = new Matriz( nome );
    }

    @Override
    public int getRowCount() {
        return matriz.getQLinhas();
    }

    @Override
    public int getColumnCount() {
        return matriz.getQColunas();
    }

    @Override
    public Object getValueAt( int rowIndex, int columnIndex ) {
        return matriz.getDadoMatriz( rowIndex, columnIndex );
    }

    @Override
    public Class getColumnClass( int columnIndex ) {
        switch ( columnIndex ) {
            case 0:
                return RenderizadorTituloLinha.class;
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
