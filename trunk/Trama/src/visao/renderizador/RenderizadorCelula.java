package visao.renderizador;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import visao.JTableCustomizado;

public class RenderizadorCelula extends DefaultTableCellRenderer {
    private Color color1, color2,  color3, color4, color5, color6, color7;
	public RenderizadorCelula() {
		super();
        setHorizontalAlignment( JLabel.CENTER );
		setBackground( new Color( 245, 245, 245 ) );
		setOpaque( true );
		setFont( getFont().deriveFont( 16f ) );
		setForeground( new Color( 0, 0, 0 ) );
	}
	
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		JTableCustomizado tab = ( JTableCustomizado ) table;
		
        if( column == tab.getColunaAtual() && row < tab.getLinhaAtual() ) setBackground( new Color( 200, 210, 255 ) );
		else if( row == tab.getLinhaAtual() && column < tab.getColunaAtual() ) setBackground( new Color( 200, 210, 255 ) );
		else setBackground( Color.BLACK );
		
		if( value.equals( "0" ) ) setText( "" );
		else if( value.equals( "1" ) ){
		    setText( "X" );
			setBackground( new Color( 150, 150, 150 ) );
		} else if( value.equals( "2" ) ){
			setText( "X" );
			setBackground( new Color( 230, 230, 230 ) );
		} else if( value.equals( "3" ) ){
			setText( "X" );
			setBackground( new Color( 210, 210, 210 ) );
		} else if( value.equals( "4" ) ){
			setText( "X" );
			setBackground( new Color( 190, 190, 190 ) );
		} else if( value.equals( "20" ) ){
		setText( "" );
			setBackground( new Color( 230, 230, 230 ) );
		} else if( value.equals( "30" ) ){
			setText( "" );
			setBackground( new Color( 210, 210, 210 ) );
		} else if( value.equals( "40" ) ){
			setText( "" );
			setBackground( new Color( 190, 190, 190 ) );
		}
		return this;
	}
}
