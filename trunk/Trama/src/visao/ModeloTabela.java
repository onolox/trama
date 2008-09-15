package visao;

import javax.swing.table.AbstractTableModel;

import negocio.Matriz;
import visao.renderizador.RenderizadorTituloLinha;

/**
 * Classe que representa o modelo da tabela, que será utilizado por uma JTable.
 * 
 * @author Fabio Marmitt
 */
public class ModeloTabela extends AbstractTableModel {
	/** A matriz que possui acesso aos dados */
	private Matriz matriz;
	
	/**
	 * Construtor padrão da classe.
	 * 
	 * @param matriz a matriz que contém os dados.
	 */
	public ModeloTabela( Matriz matriz ) {
		super();
		this.matriz = matriz;
	}
	
	/**
	 * Sobreescrita do método para buscar a quantidade de linhas.
	 * 
	 * @return quantidade de linhas
	 */
	@Override
	public int getRowCount() {
		return matriz.getQLinhas();
	}
	
	/**
	 * Sobreescrita do método para buscar a quantidade de colunas.
	 * 
	 * @return quantidade de colunas
	 */
	@Override
	public int getColumnCount() {
		return matriz.getQColunas();
	}
	
	/**
	 * Sobreescrita do método para buscar o valor em uma coordenada.
	 * 
	 * @return o valor
	 */
	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {
		return matriz.getDadoMatriz( rowIndex, columnIndex );
	}
	
	/**
	 * Sobreescrita do método para buscar o tipo de cada coluna.
	 * 
	 * @return a classe da coluna
	 */
	@Override
	public Class getColumnClass( int columnIndex ) {
		if( columnIndex != 0 ) return String.class;
		return RenderizadorTituloLinha.class;
		
	}
	
	/**
	 * Sobreescrita do método para buscar o nome de cada coluna.
	 * 
	 * @return nome da coluna
	 */
	@Override
	public String getColumnName( int columnIndex ) {
		if( columnIndex != 0 ) return matriz.getTituloColuna( columnIndex );
		return "";
	}
	
	/**
	 * Usado para buscar o nome da matriz.
	 * 
	 * @return nome da matriz
	 */
	public String getNomeMatriz() {
		return matriz.getNomeMatriz();
	}
	
	/**
	 * Usado para buscar a classe matriz.
	 * 
	 * @return a instância de Matriz
	 */
	public Matriz getMatriz() {
		return matriz;
	}
}
