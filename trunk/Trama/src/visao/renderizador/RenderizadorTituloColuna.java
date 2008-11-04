package visao.renderizador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;

import visao.JTableCustomizado;

/**
 * Classe utilizada para renderizar os títulos das colunas de uma matriz, esta matriz é representada por {@link JTableCustomizado}.<br>
 * 
 * @author Fabio Marmitt
 */
public class RenderizadorTituloColuna extends DefaultTableCellRenderer {
	
	/** Tipo de borda "levantanda". */
	private SoftBevelBorder raized;
	/** Tipo de borda "pressionada". */
	private SoftBevelBorder low;
	/** Cor pré estabelecida. */
	private Color color, color2;
	/** Icone que será utilizada para exibir o texto na vertical */
	private Icon icon = null;
	
	/** Construtor Padrão. */
	public RenderizadorTituloColuna() {
		super();
		setOpaque( true );
		setHorizontalAlignment( CENTER );
		setVerticalAlignment( JLabel.BOTTOM );
		setFont( getFont().deriveFont( 12f ) );
		setForeground( new Color( 0, 0, 0 ) );
		
		color = new Color( 244, 103, 84 );
		color2 = UIManager.getDefaults().getColor( "Button.light" );
		raized = new SoftBevelBorder( BevelBorder.RAISED );
		low = new SoftBevelBorder( BevelBorder.LOWERED );
	}
	
	/** Método sobreescrito para renderizar o componente de acorco com as necessidades do projeto. */
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		JTableCustomizado tab = ( JTableCustomizado ) table;
		setBorder( raized );
		String v = value.toString();
		
		if( !v.startsWith( "|||" ) ){
			if( ( tab.getColunaAtual() == column && tab.getLinhaAtual() >= 0 ) || tab.getColunaSelecionada() == column ) setBorder( low );
			setBackground( color2 );
		} else{
			if( ( tab.getColunaAtual() == column && tab.getLinhaAtual() >= 0 ) || tab.getColunaSelecionada() == column ) setBorder( low );
			setBackground( color );
		}
		if( !v.equals( getName() ) ){
			setName( v );
			if( v.length() > 31 ){
				String rr = v.replace( "|||", "" );
				setToolTipText( rr );
				
				try{
					rr = rr.substring( 0, 27 ) + "...";
				} catch( Exception e ){
					rr = v.replace( "|||", "" );
				}
				icon = getVerticalCaption( this, rr, true );
			} else{
				
				String ss = v.replace( "|||", "" );
				for( int i = 0; i < 35; i++ ){
					if( ss.length() < 35 ) ss = " " + ss;
				}
				icon = getVerticalCaption( this, ss, true );
			}
			setIcon( icon );
		}
		if( column == 0 ) setBorder( raized );
		
		return this;
	}
	
	/**
	 *Método que gera um ícone baseando-se em um texto.
	 * 
	 * @param componente o componente que vai receber o ícone
	 * @param texto o texto que deverá ser inserido na imagem
	 * @param sentido sentodo horário ou anti-horário
	 */
	private Icon getVerticalCaption( JComponent componente, String texto, boolean sentido ) {
		Font f = componente.getFont();
		FontMetrics fm = componente.getFontMetrics( f );
		int captionHeight = fm.getHeight();
		int captionWidth = fm.stringWidth( texto );
		BufferedImage bi = new BufferedImage( captionHeight + 5, captionWidth + 3, BufferedImage.TYPE_INT_ARGB );
		Graphics2D g = ( Graphics2D ) bi.getGraphics();
		
		g.setColor( new Color( 0, 0, 0, 0 ) ); // transparent
		
		g.fillRect( 0, 0, bi.getWidth(), bi.getHeight() );
		
		g.setColor( componente.getForeground() );
		g.setFont( f );
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB );
		g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		
		if( sentido ){
			g.rotate( Math.PI / 2 );
		} else{
			g.rotate( -Math.PI / 2 );
			g.translate( -bi.getHeight(), bi.getWidth() );
		}
		g.drawString( texto, 2, -6 );
		
		Icon icon = new ImageIcon( bi );
		return icon;
	}
}
