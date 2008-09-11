package visao;

import javax.swing.table.AbstractTableModel;

import negocio.Matriz;
import visao.renderizador.RenderizadorTituloLinha;

public class ModeloTabela extends AbstractTableModel {
	private Matriz matriz;
	
	public ModeloTabela( Matriz matriz ) {
		super();
		this.matriz = matriz;
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
		if( columnIndex != 0 ) return String.class;
		return RenderizadorTituloLinha.class;
		
	}
	
	@Override
	public String getColumnName( int columnIndex ) {
		if( columnIndex != 0 ) return matriz.getTituloColuna( columnIndex );
		return "";
	}
	
	public String getNomeMatriz() {
		return matriz.getNomeMatriz();
	}
	
	public Matriz getMatriz() {
		return matriz;
	}
}
