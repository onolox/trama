package visao;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import visao.renderizador.RenderizadorCelula;
import visao.renderizador.RenderizadorTituloColuna;
import visao.renderizador.RenderizadorTituloLinha;

import com.lowagie.text.Document;
import com.lowagie.text.RectangleReadOnly;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Fabio
 */
public class JTableCustomizado extends JTable {
	private DefaultTableCellRenderer cell;
	private DefaultTableCellRenderer cell0;
	private Enumeration< TableColumn > l;
	private ModeloTabela modelo;
	
	public JTableCustomizado( ModeloTabela modelo ) {
		cell = new RenderizadorCelula();
		cell0 = new RenderizadorTituloLinha();
		this.modelo = modelo;
		
		setAutoResizeMode( AUTO_RESIZE_OFF );
		setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
		
		getTableHeader().setReorderingAllowed( false );
		setAutoCreateColumnsFromModel( true );
		setSelectionMode( 2 );
		setColumnSelectionAllowed( false );
		setCellSelectionEnabled( false );
		setModel( modelo );
		setDefaultRenderer( String.class, cell );
		setDefaultRenderer( RenderizadorTituloLinha.class, cell0 );
		
		TableColumnModel modelocoluna = getColumnModel();
		l = modelocoluna.getColumns();
		
		while( l.hasMoreElements() ){
			TableColumn tc = l.nextElement();
			tc.setResizable( false );
			tc.setPreferredWidth( 20 );
			if( ( ( String ) tc.getHeaderValue() ).equalsIgnoreCase( "" ) ){
				tc.setPreferredWidth( 200 );
			}
			tc.setHeaderRenderer( new RenderizadorTituloColuna() );
		}
	}
	
	public String exportarImagem( String arquivo ) {
		String s = "ok";
		
		try{
			BufferedImage image = new BufferedImage( getWidth(), getHeight() + getTableHeader().getHeight(), BufferedImage.TYPE_INT_RGB );
			Graphics2D g2 = image.createGraphics();
			getTableHeader().print( g2 );
			g2.translate( 0, getTableHeader().getHeight() );
			g2.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
			print( g2 );
			
			ImageIO.write( image, "png", new File( "arquivos/" + arquivo + ".png" ) );
			g2.dispose();
		} catch( IOException e ){
			e.printStackTrace();
			s = "erro";
		}
		return s;
	}
	
	public String exportarPDF( String arquivo ) {
		String s = "ok";
		Document document = new Document( new RectangleReadOnly( getWidth() + 50, getHeight() + 80 + getTableHeader().getHeight() ) );
		try{
			PdfWriter w = PdfWriter.getInstance( document, new FileOutputStream( "arquivos/" + arquivo + ".pdf" ) );
			document.open();
			PdfContentByte cb = w.getDirectContent();
			
			cb.saveState();
			Graphics2D g2 = cb.createGraphicsShapes( getWidth() + 40, getHeight() + getTableHeader().getHeight() + 40 );
			
			g2.translate( 20, 0 );
			getTableHeader().print( g2 );
			g2.translate( 0, getTableHeader().getHeight() );
			
			print( g2 );
			
			g2.dispose();
			
			cb.restoreState();
		} catch( Exception e ){
			e.printStackTrace();
		}
		document.close();
		return "";
	}
	
	public String imprimir() {
		String s = "ok";
		try{
			print();
		} catch( PrinterException e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	public String getNome() {
		return modelo.getNomeMatriz();
	}
}
