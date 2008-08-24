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

public class RenderizadorTituloColuna extends DefaultTableCellRenderer {
	public RenderizadorTituloColuna() {
		super();
	}
	
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		
		JLabel label = new JLabel();
		
		if( !value.toString().startsWith( "|||" ) ){
			label.setHorizontalAlignment( CENTER );
			label.setOpaque( true );
			label.setBackground( UIManager.getDefaults().getColor( "Button.light" ) );
			label.setBorder( new javax.swing.border.SoftBevelBorder( BevelBorder.RAISED ) );
		} else{
			label.setHorizontalAlignment( CENTER );
			label.setOpaque( true );
			label.setBackground( new Color( 244, 103, 84 ) );
			label.setBorder( new SoftBevelBorder( BevelBorder.RAISED ) );
		}
		Icon icon = getVerticalCaption( label, value.toString().replace( "|||", "" ), true );
		label.setIcon( icon );
		
		return label;
	}
	
	/**
	 * @param componente
	 * @param texto
	 * @param sentido
	 */
	private Icon getVerticalCaption( JComponent componente, String texto, boolean sentido ) {
		Font f = componente.getFont();
		FontMetrics fm = componente.getFontMetrics( f );
		int captionHeight = fm.getHeight();
		int captionWidth = fm.stringWidth( texto );
		BufferedImage bi = new BufferedImage( captionHeight + 4, captionWidth + 4, BufferedImage.TYPE_INT_ARGB );
		Graphics2D g = ( Graphics2D ) bi.getGraphics();
		
		g.setColor( new Color( 0, 0, 0, 0 ) ); // transparent
		
		g.fillRect( 0, 0, bi.getWidth(), bi.getHeight() );
		
		g.setColor( componente.getForeground() );
		g.setFont( f );
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		
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
