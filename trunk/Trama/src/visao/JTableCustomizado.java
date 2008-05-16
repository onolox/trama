package visao;

import visao.renderer.RendererTitulo;
import visao.renderer.RendererTituloLinha;
import visao.renderer.RendererLinha;
import visao.ModeloTabela;
import java.awt.Font;
import java.util.Enumeration;
import javax.swing.JTable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Fabio
 */
public class JTableCustomizado extends JTable {
    DefaultTableCellRenderer cell;
    DefaultTableCellRenderer cell0;
    Enumeration<TableColumn> l;

    public JTableCustomizado() {
        cell = new RendererTituloLinha();
        cell0 = new RendererLinha();

        setAutoResizeMode( AUTO_RESIZE_OFF );
        setFont( new Font( "Arial", 0, 12 ) );



        setModel( new ModeloTabela( "UC X Requisitos" ) );

        setDefaultRenderer( String.class, cell );
        setDefaultRenderer( RendererLinha.class, cell0 );

        TableColumnModel modelocoluna = getColumnModel();
        l = modelocoluna.getColumns();
        while ( l.hasMoreElements() ) {
            TableColumn tc = l.nextElement();
            tc.setResizable( false );
            tc.setPreferredWidth( 20 );
            if ( tc.getHeaderValue().equals( "" ) ) {
                tc.setPreferredWidth( 100 );

            }

            tc.setHeaderRenderer( new RendererTitulo() );












        }
    }
}
