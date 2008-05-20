package visao;

import java.awt.Font;
import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import visao.renderizador.RenderizadorCelula;
import visao.renderizador.RenderizadorTituloColuna;
import visao.renderizador.RenderizadorTituloLinha;

/**
 *
 * @author Fabio
 */
public class JTableCustomizado extends JTable {
    private DefaultTableCellRenderer cell;
    private DefaultTableCellRenderer cell0;
    private Enumeration<TableColumn> l;
    ModeloTabela tabe;

    public JTableCustomizado() {
        cell = new RenderizadorCelula();
        cell0 = new RenderizadorTituloLinha();

        setAutoResizeMode( AUTO_RESIZE_OFF );
        setFont( new Font( "Arial", 0, 12 ) );

        setRowSelectionAllowed( false );
        setColumnSelectionAllowed( true );
        setCellSelectionEnabled( true );
        tabe = new ModeloTabela( "UC X Requisitos" );
        setModel( tabe );

        setDefaultRenderer( String.class, cell );
        setDefaultRenderer( RenderizadorTituloLinha.class, cell0 );


        TableColumnModel modelocoluna = getColumnModel();
        l = modelocoluna.getColumns();
        while ( l.hasMoreElements() ) {
            TableColumn tc = l.nextElement();
            tc.setResizable( false );
            tc.setPreferredWidth( 20 );
            if ( tc.getHeaderValue().equals( "" ) ) {
                tc.setPreferredWidth( 100 );
            }

            tc.setHeaderRenderer( new RenderizadorTituloColuna() );
        }
    }
}
