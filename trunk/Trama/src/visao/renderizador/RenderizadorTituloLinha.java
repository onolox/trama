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
		
		if( !value.toString().startsWith( "|||" ) ){
			label.setHorizontalAlignment( RIGHT );
			label.setOpaque( true );
			label.setBackground( UIManager.getDefaults().getColor( "Button.light" ) );
			label.setBorder( new SoftBevelBorder( BevelBorder.RAISED ) );
		} else{
			label.setHorizontalAlignment( RIGHT );
			label.setOpaque( true );
			label.setBackground( new java.awt.Color( 244, 103, 84 ) );
			label.setBorder( new SoftBevelBorder( BevelBorder.RAISED ) );
		}
		return label;
	}
}
