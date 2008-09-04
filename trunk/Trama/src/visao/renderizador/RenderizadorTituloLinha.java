package visao.renderizador;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderizadorTituloLinha extends DefaultTableCellRenderer {
	public RenderizadorTituloLinha() {
		super();
	}
	
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		
		JLabel label = ( JLabel ) super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		label.setHorizontalAlignment( RIGHT );
		label.setOpaque( true );
		label.setBorder( new SoftBevelBorder( BevelBorder.RAISED ) );
		label.setFont( getFont().deriveFont( 12f ) );
		// label.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
		
		if( !value.toString().startsWith( "|||" ) ){
			label.setBackground( UIManager.getDefaults().getColor( "Button.light" ) );
		} else{
			label.setBackground( new java.awt.Color( 244, 103, 84 ) );
		}
		return label;
	}
}
