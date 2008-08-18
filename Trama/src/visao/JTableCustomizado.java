package visao;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import visao.renderizador.RenderizadorCelula;
import visao.renderizador.RenderizadorTituloColuna;
import visao.renderizador.RenderizadorTituloLinha;

/**
 * @author Fabio
 */
public class JTableCustomizado extends JTable {
	private DefaultTableCellRenderer cell;
	private DefaultTableCellRenderer cell0;
	private Enumeration< TableColumn > l;
	private ModeloTabela modelo;

        public JTableCustomizado() {
        }
        
        
	
	public JTableCustomizado( ModeloTabela modelo ) {
		cell = new RenderizadorCelula();
		cell0 = new RenderizadorTituloLinha();
		this.modelo = modelo;
		
		setAutoResizeMode( AUTO_RESIZE_OFF );
		setFont( new Font( "Arial", 0, 12 ) );
		
		setRowSelectionAllowed( false );
		setColumnSelectionAllowed( false );
		setCellSelectionEnabled( true );
	
		setModel( modelo );
		
		setDefaultRenderer( String.class, cell );
		setDefaultRenderer( RenderizadorTituloLinha.class, cell0 );
		
		TableColumnModel modelocoluna = getColumnModel();
		l = modelocoluna.getColumns();
		         
		
		  while( l.hasMoreElements() ){
			TableColumn tc = l.nextElement();
                          System.out.println( tc );
			tc.setResizable( false );
			tc.setPreferredWidth( 20 );
			if( tc.getHeaderValue().equals( "" ) ){
				tc.setPreferredWidth( 100 );
			}
			tc.setHeaderRenderer( new RenderizadorTituloColuna() );
		}
	}
	
		
	public String exportarImagem() {
		return "";
	}
	
	public String exportarPDF() {
		return "";
	}
	
	public String imprimir() {
		return "";
	}
	
	public String getNome() {
		return modelo.getNomeMatriz();
	}
}
