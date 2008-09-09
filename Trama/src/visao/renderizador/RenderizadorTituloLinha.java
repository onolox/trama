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

public class RenderizadorTituloLinha extends DefaultTableCellRenderer {
	boolean bol = true;
	public RenderizadorTituloLinha() {
		super();
	}
	
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		JTableCustomizado tab = ( JTableCustomizado ) table;
		JLabel label = ( JLabel ) super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		label.setHorizontalAlignment( RIGHT );
		label.setOpaque( true );
		label.setBorder( new SoftBevelBorder( BevelBorder.RAISED ) );
		label.setForeground( new Color( 0, 0, 0 ) );
		
		if( value.toString().length() > 30 ) label.setToolTipText( value.toString() );
		else label.setToolTipText( null );
		label.setFont( getFont().deriveFont( 12f ) );
		
		if( !value.toString().startsWith( "|||" ) ){
			if( ( tab.getColunaAtual() > 0 && tab.getLinhaAtual() == row ) || ( tab.getLinhaSelecionada() == row ) ) label.setBorder( new SoftBevelBorder( BevelBorder.LOWERED ) );
			label.setBackground( UIManager.getDefaults().getColor( "Button.light" ) );
		} else{
			if( ( tab.getColunaAtual() > 0 && tab.getLinhaAtual() == row ) || ( tab.getLinhaSelecionada() == row ) ) label.setBorder( new SoftBevelBorder( BevelBorder.LOWERED ) );
			label.setBackground( new java.awt.Color( 244, 103, 84 ) );
			label.setText( value.toString().replace( "|||", "" ) );
		}
		return label;
	}
}
