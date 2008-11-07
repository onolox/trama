package visao.renderizador;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;

import visao.JTableCustomizado;

/**
 * Classe utilizada para renderizar os títulos das linhas de uma matriz, esta matriz é representada por {@link JTableCustomizado}.<br>
 * 
 * @author Fabio Marmitt
 */
public class RenderizadorTituloLinha extends DefaultTableCellRenderer {
	
	/** Tipo de borda "levantanda". */
	private SoftBevelBorder raized;
	/** Tipo de borda "pressionada". */
	private SoftBevelBorder low;
	/** Tipo de cor pré estabelecida. */
	private Color color, color2;
	
	/** Construtor Padrão. */
	public RenderizadorTituloLinha() {
		super();
		setHorizontalAlignment( RIGHT );
		setOpaque( true );
		setForeground( new Color( 0, 0, 0 ) );
		setFont( getFont().deriveFont( 12f ) );
		raized = new SoftBevelBorder( BevelBorder.RAISED );
		low = new SoftBevelBorder( BevelBorder.LOWERED );
		color = new Color( 244, 103, 84 );
		color2 = UIManager.getDefaults().getColor( "Button.light" );
	}
	
	/**
	 * Método sobreescrito para renderizar o componente de acorco com as necessidades do projeto.<br>
	 * <br>{@inheritDoc}
	 */
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		String v = value.toString();
		
		JTableCustomizado tab = ( JTableCustomizado ) table;
		
		setBorder( raized );
		
		if( v.length() > 30 ) setToolTipText( v );
		else setToolTipText( null );
		
		if( !v.startsWith( "|||" ) ){
			if( ( tab.getColunaAtual() > 0 && tab.getLinhaAtual() == row ) || ( tab.getLinhaSelecionada() == row ) ) setBorder( low );
			setBackground( color2 );
			( ( JLabel ) this ).setText( v );
		} else{
			if( ( tab.getColunaAtual() > 0 && tab.getLinhaAtual() == row ) || ( tab.getLinhaSelecionada() == row ) ) setBorder( low );
			setBackground( color );
			( ( JLabel ) this ).setText( v.replace( "|||", "" ) );
		}
		
		return this;
	}
}
