package visao.renderizador;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import visao.JTableCustomizado;

public class RenderizadorCelula extends DefaultTableCellRenderer {
	public RenderizadorCelula() {
		super();
	}
	
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		
		JLabel label = ( JLabel ) super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		
		label.setHorizontalAlignment( JLabel.CENTER );
		label.setBackground( new Color( 245, 245, 245 ) );
		label.setOpaque( true );
		label.setFont( getFont().deriveFont( 16f ) );
		label.setForeground( new Color( 0, 0, 0 ) );
	
		JTableCustomizado tab = ( JTableCustomizado ) table;
		if( column == tab.getColunaAtual() && row < tab.getLinhaAtual() ) this.setBackground( new Color( 200, 210, 255 ) );
		else if( row == tab.getLinhaAtual() && column < tab.getColunaAtual() ) this.setBackground( new Color( 200, 210, 255 ) );
		else this.setBackground( new Color( 245, 245, 245 ) );
		
		if( value.equals( "0" ) ) label.setText( "" );
		else if( value.equals( "1" ) ){
			label.setText( "X" );
			label.setBackground( new Color( 150, 150, 150 ) );
		} else if( value.equals( "2" ) ){
			label.setText( "X" );
			label.setBackground( new Color( 230, 230, 230 ) );
		} else if( value.equals( "3" ) ){
			label.setText( "X" );
			label.setBackground( new Color( 210, 210, 210 ) );
		} else if( value.equals( "4" ) ){
			label.setText( "X" );
			label.setBackground( new Color( 190, 190, 190 ) );
		} else if( value.equals( "20" ) ){
			label.setText( "" );
			label.setBackground( new Color( 230, 230, 230 ) );
		} else if( value.equals( "30" ) ){
			label.setText( "" );
			label.setBackground( new Color( 210, 210, 210 ) );
		} else if( value.equals( "40" ) ){
			label.setText( "" );
			label.setBackground( new Color( 190, 190, 190 ) );
		}
		return label;
	}
}
