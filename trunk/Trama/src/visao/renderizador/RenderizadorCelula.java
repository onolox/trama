package visao.renderizador;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderizadorCelula extends DefaultTableCellRenderer {
	public RenderizadorCelula() {
		super();
	}
	
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		
		JLabel label = ( JLabel ) super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		
		label.setHorizontalAlignment( JLabel.CENTER );
		label.setBackground( javax.swing.UIManager.getDefaults().getColor( "Button.highlight" ) );
		label.setOpaque( true );
		label.setFont( getFont().deriveFont( 16f ) );
		if( value.equals( "0" ) ){
			label.setText( "" );
		} else if( value.equals( "1" ) ){
			label.setText( "X" );
			label.setBackground( new Color( 150, 150, 150 ) );
		} else if( value.equals( "2" ) ){
			label.setText( "X" );
			label.setBackground( javax.swing.UIManager.getDefaults().getColor( "ColorChooser.background" ) );
		} else if( value.equals( "3" ) ){
			label.setText( "X" );
			label.setBackground( javax.swing.UIManager.getDefaults().getColor( "Button.light" ) );
		} else if( value.equals( "4" ) ){
			label.setText( "X" );
			label.setBackground( javax.swing.UIManager.getDefaults().getColor( "CheckBox.shadow" ) );
		} else if( value.equals( "20" ) ){
			label.setText( "" );
			label.setBackground( javax.swing.UIManager.getDefaults().getColor( "ColorChooser.background" ) );
		} else if( value.equals( "30" ) ){
			label.setText( "" );
			label.setBackground( javax.swing.UIManager.getDefaults().getColor( "Button.light" ) );
		} else if( value.equals( "40" ) ){
			label.setText( "" );
			label.setBackground( javax.swing.UIManager.getDefaults().getColor( "CheckBox.shadow" ) );
		}
				return label;
	}
}
