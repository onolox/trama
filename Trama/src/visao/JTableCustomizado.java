package visao;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import visao.renderizador.RenderizadorCelula;
import visao.renderizador.RenderizadorTituloColuna;
import visao.renderizador.RenderizadorTituloLinha;

import com.lowagie.text.Document;
import com.lowagie.text.RectangleReadOnly;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 *Classe que extende JTable e foi customizada para representar uma matriz.
 * 
 * @author Fabio
 */
public class JTableCustomizado extends JTable {
	private DefaultTableCellRenderer cell;
	private DefaultTableCellRenderer cell0;
	private Enumeration< TableColumn > l;
	private ModeloTabela modelo;
	private int linhaAtual = 0;
	private int colunaAtual = 0;
	private int linhaSelecionada = -1;
	private int colunaSelecionada = -1;
	
	/**
	 * Construtor padrão.
	 * 
	 * @param modelo ModeloTabela para a JTable.
	 */
	public JTableCustomizado( ModeloTabela modelo ) {
		cell = new RenderizadorCelula();
		cell0 = new RenderizadorTituloLinha();
		this.modelo = modelo;
		
		setAutoResizeMode( AUTO_RESIZE_OFF );
		setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
		
		getTableHeader().setReorderingAllowed( false );
		setAutoCreateColumnsFromModel( true );
		setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		setColumnSelectionAllowed( false );
		setModel( modelo );
		setDefaultRenderer( String.class, cell );
		setDefaultRenderer( RenderizadorTituloLinha.class, cell0 );
		setRowHeight( 18 );
		
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
		
		addMouseMotionListener( new MouseMotionAdapter() {
			/** {@inheritDoc} */
			@SuppressWarnings( "synthetic-access" )
			public void mouseMoved( MouseEvent e ) {
				JTable aTable = ( JTable ) e.getSource();
				int lin = aTable.rowAtPoint( e.getPoint() );
				int col = aTable.columnAtPoint( e.getPoint() );
				if( lin != linhaAtual || col != colunaAtual ){
					linhaAtual = lin;
					colunaAtual = col;
					aTable.repaint();
					aTable.getTableHeader().repaint();
				}
			}
		} );
		
		getTableHeader().addMouseMotionListener( new MouseMotionAdapter() {// Pro header
				/** {@inheritDoc} */
				@SuppressWarnings( "synthetic-access" )
				public void mouseMoved( MouseEvent e ) {
					JTableHeader head = ( JTableHeader ) e.getSource();
					int col = head.columnAtPoint( e.getPoint() );
					if( col != colunaAtual ){
						linhaAtual = -1;
						colunaAtual = col;
						if( colunaAtual == 0 ){
							colunaAtual = -1;
						}
						head.repaint();
					}
				}
			} );
	}
	
	/**
	 * Usado para exportar uma imagem da matriz atual.
	 * 
	 * @param arquivo nome do arquivo
	 * @return estatus da operação
	 */
	public String exportarImagem( String arquivo ) {
		String s = "ok";
		
		try{
			BufferedImage image = new BufferedImage( getWidth(), getHeight() + getTableHeader().getHeight(), BufferedImage.TYPE_INT_RGB );
			Graphics2D g2 = image.createGraphics();
			getTableHeader().print( g2 );
			g2.translate( 0, getTableHeader().getHeight() );
			g2.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
			print( g2 );
			
			ImageIO.write( image, "png", new File( arquivo + ".png" ) );
			g2.dispose();
		} catch( IOException e ){
			e.printStackTrace();
			s = "erro";
		}
		return s;
	}
	
	/**
	 * Usado para exportar um PDF da matriz atual.
	 * 
	 * @param arquivo nome do arquivo
	 * @return estatus da operação
	 */
	public String exportarPDF( String arquivo ) {
		String s = "ok";
		Document document = new Document( new RectangleReadOnly( getWidth() + 50, getHeight() + 80 + getTableHeader().getHeight() ) );
		try{
			PdfWriter w = PdfWriter.getInstance( document, new FileOutputStream( arquivo + ".pdf" ) );
			document.open();
			PdfContentByte cb = w.getDirectContent();
			
			cb.saveState();
			Graphics2D g2 = cb.createGraphicsShapes( getWidth() + 40, getHeight() + getTableHeader().getHeight() + 40 );
			
			g2.translate( 20, 0 );
			getTableHeader().print( g2 );
			g2.translate( 0, getTableHeader().getHeight() );
			
			print( g2 );
			cb.beginText();
			BaseFont bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
			cb.setFontAndSize( bf, 12 );
			cb.setTextMatrix( 10, 10 );
			cb.showText( getNome() );
			g2.dispose();
			
			cb.restoreState();
		} catch( Exception e ){
			e.printStackTrace();
			s = "erro";
		}
		document.close();
		return s;
	}
	
	/**
	 * Usado para imprimir a matriz.
	 * 
	 * @return estatus da operação
	 */
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
	
	/**
	 * @return nome da matriz
	 */
	public String getNome() {
		return modelo.getNomeMatriz();
	}
	
	/**
	 * @return the linhaAtual
	 */
	public int getLinhaAtual() {
		return linhaAtual;
	}
	
	/**
	 * @return the colunaAtual
	 */
	public int getColunaAtual() {
		return colunaAtual;
	}
	
	/**
	 * @param linhaAtual the linhaAtual to set
	 */
	public void setLinhaAtual( int linhaAtual ) {
		this.linhaAtual = linhaAtual;
	}
	
	/**
	 * @param colunaAtual the colunaAtual to set
	 */
	public void setColunaAtual( int colunaAtual ) {
		this.colunaAtual = colunaAtual;
	}
	
	/**
	 * @return the linhaSelecionada
	 */
	public int getLinhaSelecionada() {
		return linhaSelecionada;
	}
	
	/**
	 * @param linhaSelecionada the linhaSelecionada to set
	 */
	public void setLinhaSelecionada( int linhaSelecionada ) {
		this.linhaSelecionada = linhaSelecionada;
	}
	
	/**
	 * @return the colunaSelecionada
	 */
	public int getColunaSelecionada() {
		return colunaSelecionada;
	}
	
	/**
	 * @param colunaSelecionada the colunaSelecionada to set
	 */
	public void setColunaSelecionada( int colunaSelecionada ) {
		this.colunaSelecionada = colunaSelecionada;
	}
}
