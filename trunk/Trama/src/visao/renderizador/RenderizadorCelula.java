package visao.renderizador;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import visao.JTableCustomizado;

/**
 * Classe utilizada para renderizar as células de uma matriz, esta matriz é representada por {@link JTableCustomizado}.<br>
 * Esta classe pode renderizar um espaço em branco ou um X no meio da célula. Estes 2 estados podem ter alguma das cores pré estabelecidas de fundo.
 * 
 * @author Fabio Marmitt
 */
public class RenderizadorCelula extends DefaultTableCellRenderer {
	
	/** Cor dos estados da célula */
	private Color color1, color2, color3, color4, color5;
	
	/** Construtor Padrão. */
	public RenderizadorCelula() {
		super();
		setHorizontalAlignment( JLabel.CENTER );
		setBackground( new Color( 245, 245, 245 ) );
		setOpaque( true );
		setFont( getFont().deriveFont( 16f ) );
		setForeground( new Color( 0, 0, 0 ) );
		color1 = new Color( 200, 210, 255 );
		color2 = new Color( 150, 150, 150 );
		color3 = new Color( 240, 240, 240 );
		color4 = new Color( 230, 230, 230 );
		color5 = new Color( 220, 220, 220 );
	}
	
	/**
	 * Método sobreescrito para renderizar o componente de acorco com as necessidades do projeto. <br>{@inheritDoc}
	 */
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		JTableCustomizado tab = ( JTableCustomizado ) table;
		int v = Integer.parseInt( value.toString() );
		
		if( column == tab.getColunaAtual() && row < tab.getLinhaAtual() ) setBackground( color1 );
		else if( row == tab.getLinhaAtual() && column < tab.getColunaAtual() ) setBackground( color1 );
		else setBackground( Color.WHITE );
		
		if( v == 0 ) setText( "" );
		else if( v == 1 ){
			setText( "X" );
			setBackground( color2 );
		} else if( v == 2 ){
			setText( "X" );
			setBackground( color3 );
		} else if( v == 3 ){
			setText( "X" );
			setBackground( color4 );
		} else if( v == 4 ){
			setText( "X" );
			setBackground( color5 );
		} else if( v == 20 ){
			setText( "" );
			setBackground( color3 );
		} else if( v == 30 ){
			setText( "" );
			setBackground( color4 );
		} else if( v == 40 ){
			setText( "" );
			setBackground( color5 );
		}
		return this;
	}
}
