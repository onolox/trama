package visao;

import visao.renderer.RendererTituloColuna;

import visao.renderer.RendererTituloLinha;
import visao.ModeloTabela;
import java.awt.Font;
import java.util.Enumeration;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import visao.renderer.RendererCelula;

/**
 *
 * @author Fabio
 */
public class JTableCustomizado extends JTable {
    DefaultTableCellRenderer cell;
    DefaultTableCellRenderer cell0;
    Enumeration<TableColumn> l;

    public JTableCustomizado() {
        cell = new RendererCelula();
        cell0 = new RendererTituloLinha();

        setAutoResizeMode( AUTO_RESIZE_OFF );
        setFont( new Font( "Arial", 0, 12 ) );

        setRowSelectionAllowed( false );
        setColumnSelectionAllowed( false );
        setCellSelectionEnabled( true );

       

        setModel( new ModeloTabela( "UC X Requisitos" ) );

        setDefaultRenderer( String.class, cell );
        setDefaultRenderer( RendererTituloLinha.class, cell0 );

        TableColumnModel modelocoluna = getColumnModel();
        l = modelocoluna.getColumns();
        while ( l.hasMoreElements() ) {
            TableColumn tc = l.nextElement();
            tc.setResizable( false );
            tc.setPreferredWidth( 20 );
            if ( tc.getHeaderValue().equals( "" ) ) {
                tc.setPreferredWidth( 100 );

            }

            tc.setHeaderRenderer( new RendererTituloColuna() );












        }
    }
}
