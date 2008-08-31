package visao;

import java.awt.Font;
import java.awt.Graphics2D;
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
import com.lowagie.text.PageSize;
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
		setFont( new Font( "Arial", 0, 12 ) );
		
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
				tc.setPreferredWidth( 100 );
			}
			tc.setHeaderRenderer( new RenderizadorTituloColuna() );
		}
	}
	
	public String exportarImagem() {
		String s = "ok";
		
		try{
			BufferedImage image = new BufferedImage( getWidth(), getHeight() + getTableHeader().getHeight(), BufferedImage.TYPE_INT_RGB );
			Graphics2D g2 = image.createGraphics();
			getTableHeader().print( g2 );
			g2.translate( 0, getTableHeader().getHeight() );
			print( g2 );
			g2.dispose();
			
			ImageIO.write( image, "png", new File( "arquivos/ImagemTabela-" + getNome() + ".png" ) );
		} catch( IOException e ){
			e.printStackTrace();
			s = "erro";
		}
		return s;
	}
	
	public String exportarPDF() {
		String s = "ok";
		Document document = new Document( PageSize.A4.rotate(), 10.0f, 10.0f, 10.0f, 10.7f );
		try{
			PdfWriter w = PdfWriter.getInstance( document, new FileOutputStream( "arquivos/Tabela-" + getNome() + ".pdf" ) );
			document.open();
			PdfContentByte cb = w.getDirectContent();
			
			cb.saveState();
			Graphics2D g2 = cb.createGraphicsShapes( getWidth(), getHeight() + getTableHeader().getHeight() );
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
