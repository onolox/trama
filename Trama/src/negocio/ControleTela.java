package negocio;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import negocio.leitor.LeitorDeModelo;
import visao.ModeloTabela;
import visao.Tela;

public class ControleTela {
	private ControleProjeto controleProjeto;
	private Tela tela;
	private LeitorDeModelo leitorDeModelo;
	private int colunaAtual, linhaAtual;
	private String matrizAtual;
	
	public ControleTela( Tela tela ) {
		this.tela = tela;
		leitorDeModelo = new LeitorDeModelo();
		colunaAtual = 0;
		linhaAtual = 0;
		matrizAtual = null;
	}
	
	public LinkedList< ModeloTabela > abrirProjeto( String nome ) {
		LinkedList< ModeloTabela > l = new LinkedList< ModeloTabela >();
		
		if( controleProjeto == null ){
			controleProjeto = new ControleProjeto();
			
			tela.setExcluirMatriz( true );
			tela.setExcluirMatrizMenu( true );
			tela.setImprimirMenu( true );
			tela.setSalvarImagemMenu( true );
			tela.setSalvarPDFMenu( true );
			tela.setSalvarProjeto( true );
			tela.setNovaMatriz( true );
			tela.setSalvarProjetoMenu( true );
			tela.setNovaMatrizMenu( true );
			tela.setFecharProjetoMenu( true );
			
			l = controleProjeto.abrirProjeto( nome );
		} else{
			int c = JOptionPane.showConfirmDialog( tela, "Deseja salvar o projeto atual?", "Salvar projeto atual", 0 );
			
			if( c == JOptionPane.YES_OPTION ){
				String s = controleProjeto.salvarProjeto( "vazio" );
				if( s.equals( "sem nome" ) ){
					s = JOptionPane.showInputDialog( tela, "Insira um nome para o projeto", "Deseja salvar o projeto?", 0 );
					if( s != null ){
						s = controleProjeto.salvarProjeto( s );
						if( s.equals( "sem nome" ) ) controleProjeto.salvarProjeto( "vazio" );
					}
				}
			}
			controleProjeto = null;
			l = abrirProjeto( nome );
		}
		return l;
	}
	
	public String adicionarColuna( String nome ) {
		String s = "ok";
		s = controleProjeto.adicionarColuna( nome, matrizAtual );
		return s;
	}
	
	public LinkedList< String > adicionarColunasModelo() {
		LinkedList< String > lista = null;
		final HashMap< String, LinkedList< String >> nE = leitorDeModelo.getNomesExtensoes();
		JFileChooser ch = new JFileChooser( "arquivos/" );
		
		try{
			ch.setDialogTitle( "Importar Colunas" ); 
			
			for( final String str : nE.keySet() ){
				ch.setFileFilter( new FileFilter() { // Filtro pra arquivos e diretorios
						@Override
						public boolean accept( File f ) {
							for( String s : nE.get( str ) ){
								if( f.getName().endsWith( s ) ) return true;
							}
							return false;
						}
						@Override
						public String getDescription() {
							return str;
						}
					} );
			}
			
			int i = ch.showSaveDialog( tela );
			if( i == JFileChooser.APPROVE_OPTION ){
				File fil = ch.getSelectedFile();
				String nomeArquivo = fil.getName();
				lista = leitorDeModelo.getObjetos( nomeArquivo );
				lista = controleProjeto.triagemObjetos( matrizAtual, "coluna", lista );
				
				for( String str : lista )
					controleProjeto.adicionarColuna( str, matrizAtual );
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
		return lista;
	}
	
	/**
	 * Método usado para inserir uma linha na matriz atual.
	 * 
	 * @param nome nome da linha a adicionar
	 * @return o estado da transação
	 */
	public String adicionarLinha( String nome ) {
		String s = "ok";
		s = controleProjeto.adicionarLinha( nome, matrizAtual );
		return s;
	}
	
	public LinkedList< String > adicionarLinhasModelo() {
		LinkedList< String > lista = null;
		final HashMap< String, LinkedList< String >> nE = leitorDeModelo.getNomesExtensoes();
		JFileChooser ch = new JFileChooser( "arquivos/" );
		
		try{
			ch.setDialogTitle( "Importar Linhas" );
			
			for( final String str : nE.keySet() ){
				ch.setFileFilter( new FileFilter() { // Filtro pra arquivos e diretorios
						@Override
						public boolean accept( File f ) {
							for( String s : nE.get( str ) ){
								if( f.getName().endsWith( s ) ) return true;
							}
							return false;
						}
						@Override
						public String getDescription() {
							return str;
						}
					} );
			}
			
			int i = ch.showSaveDialog( tela );
			if( i == JFileChooser.APPROVE_OPTION ){
				File fil = ch.getSelectedFile();
				String nomeArquivo = fil.getName();
				lista = leitorDeModelo.getObjetos( nomeArquivo );
				lista = controleProjeto.triagemObjetos( matrizAtual, "linha", lista );
				
				for( String str : lista )
					controleProjeto.adicionarLinha( str, matrizAtual );
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
		return lista;
	}
	public ModeloTabela adicionarMatriz( String nome ) {
		ModeloTabela m = controleProjeto.adicionarMatriz( nome );
		tela.setExcluirMatriz( true );
		tela.setExcluirMatrizMenu( true );
		tela.setImprimirMenu( true );
		tela.setSalvarImagemMenu( true );
		tela.setSalvarPDFMenu( true );
		
		matrizAtual = m.getNomeMatriz();
		
		adicionarLinha( "Linha" );
		adicionarColuna( "Coluna" );
		return m;
	}
	
	public String alterarPosicaoColuna( String para ) {
		String s = "ok";
		if( para.equalsIgnoreCase( "esq" ) ){
			s = controleProjeto.alterarPosicaoColuna( colunaAtual - 1, colunaAtual, matrizAtual );
			if( s.equalsIgnoreCase( "fora" ) ){ return "fora"; }
			colunaAtual = colunaAtual - 1;
		} else{
			s = controleProjeto.alterarPosicaoColuna( colunaAtual + 1, colunaAtual, matrizAtual );
			if( s.equalsIgnoreCase( "fora" ) ){ return "fora"; }
			colunaAtual = colunaAtual + 1;
		}
		return s;
	}
	
	public String alterarPosicaoLinha( String para ) {
		String s = "ok";
		if( para.equalsIgnoreCase( "cima" ) ){
			s = controleProjeto.alterarPosicaoLinha( linhaAtual - 1, linhaAtual, matrizAtual );
			if( s.equalsIgnoreCase( "fora" ) ){ return "ok"; }
			linhaAtual = linhaAtual - 1;
		} else{
			s = controleProjeto.alterarPosicaoLinha( linhaAtual + 1, linhaAtual, matrizAtual );
			if( s.equalsIgnoreCase( "fora" ) ){ return "ok"; }
			linhaAtual = linhaAtual + 1;
		}
		return s;
	}
	
	public String atualizarColuna( String nome ) {
		String s = "ok";
		s = controleProjeto.atualizarColuna( matrizAtual, colunaAtual, nome );
		return s;
	}
	
	public String atualizarLinha( String nome ) {
		String s = "ok";
		s = controleProjeto.atualizarLinha( matrizAtual, linhaAtual, nome );
		return s;
	}
	
	public String criarNovoProjeto() {
		String s = "ok";
		
		if( controleProjeto == null ){
			controleProjeto = new ControleProjeto();
			
			tela.setSalvarProjeto( true );
			tela.setNovaMatriz( true );
			
			tela.setSalvarProjetoMenu( true );
			tela.setNovaMatrizMenu( true );
			tela.setFecharProjetoMenu( true );
		} else{
			int c = JOptionPane.showConfirmDialog( tela, "Deseja salvar o projeto atual?", "Salvar projeto atual", 0 );
			
			if( c == JOptionPane.YES_OPTION ){
				s = controleProjeto.salvarProjeto( "vazio" );
				if( s.equals( "sem nome" ) ){
					s = JOptionPane.showInputDialog( tela, "Insira um nome para o projeto", "Deseja salvar o projeto?", 0 );
					if( s != null ) s = controleProjeto.salvarProjeto( s );
				}
			}
			controleProjeto = null;
		}
		return s;
	}
	
	public HashMap< String, LinkedList< String >> destacarElementos() {
		HashMap< String, LinkedList< String >> map = new HashMap< String, LinkedList< String > >();
		if( linhaAtual == -1 ) map = controleProjeto.destacarElementos( colunaAtual, "coluna", matrizAtual );
		else if( colunaAtual == 0 ) map = controleProjeto.destacarElementos( linhaAtual, "linha", matrizAtual );
		return map;
	}
	
	public String excluirColuna() {
		String s = "ok";
		if( colunaAtual > 0 ){
			s = controleProjeto.excluirColuna( colunaAtual, matrizAtual );
		}
		return s;
	}
	
	public String excluirLinha() {
		String s = "ok";
		s = controleProjeto.excluirLinha( linhaAtual, matrizAtual );
		return s;
	}
	
	public String excluirMatriz() {
		String s = "ok";
		System.out.println( matrizAtual );
		s = controleProjeto.excluirMatriz( matrizAtual );
		return s;
	}
	
	public void fecharProjeto() {
		int c = JOptionPane.showConfirmDialog( tela, "Deseja salvar o projeto atual?", "Salvar projeto atual", 0 );
		
		if( c == JOptionPane.YES_OPTION ){
			String s = controleProjeto.salvarProjeto( "vazio" );
			if( s.equals( "sem nome" ) ){
				s = JOptionPane.showInputDialog( tela, "Insira um nome para o projeto", "Deseja salvar o projeto?", 0 );
				if( s != null ) s = controleProjeto.salvarProjeto( s );
			}
		}
		controleProjeto = null;
		tela.setFecharProjetoMenu( false );
		tela.setNovaMatriz( false );
		tela.setNovaMatrizMenu( false );
		tela.setExcluirMatriz( false );
		tela.setExcluirMatrizMenu( false );
		tela.setSalvarProjetoMenu( false );
		tela.setSalvarProjeto( false );
		tela.setSalvarImagemMenu( false );
		tela.setSalvarPDFMenu( false );
		tela.setImprimirMenu( false );
	}
	
	public HashMap< String, LinkedList< String >> getNomeExtensoes() {
		return null;
	}
	
	public String ordenarColuna() {
		String s = "ok";
		s = controleProjeto.ordenarColuna( matrizAtual );
		return s;
	}
	
	public String ordenarLinha() {
		String s = "ok";
		s = controleProjeto.ordenarLinha( matrizAtual );
		return s;
	}
	
	public String resetarDestaque() {
		return "";
	}
	
	public String salvarProjeto( String nome ) {
		String s = "ok";
		s = controleProjeto.salvarProjeto( nome );
		return s;
	}
	
	public String setDado() {
		String s = "ok";
		s = controleProjeto.setDado( linhaAtual, colunaAtual, matrizAtual );
		return s;
	}
	
	public String sincronizarColuna() {
		String s = "ok";
		
		return s;
	}
	
	public String sincronizarLinha() {
		String s = "ok";
		
		return s;
	}
	
	public String sincronizarMatriz() {
		String s = "ok";
		
		return s;
	}
	
	/**
	 * @return the colunaAtual
	 */
	public int getColunaAtual() {
		return colunaAtual;
	}
	
	/**
	 * @param colunaAtual the colunaAtual to set
	 */
	public void setColunaAtual( int colunaAtual ) {
		this.colunaAtual = colunaAtual;
	}
	
	/**
	 * @return the linhaAtual
	 */
	public int getLinhaAtual() {
		return linhaAtual;
	}
	
	/**
	 * @param linhaAtual the linhaAtual to set
	 */
	public void setLinhaAtual( int linhaAtual ) {
		this.linhaAtual = linhaAtual;
	}
	
	/**
	 * @return the matrizAtual
	 */
	public String getMatrizAtual() {
		return matrizAtual;
	}
	
	/**
	 * @param matrizAtual the matrizAtual to set
	 */
	public void setMatrizAtual( String matrizAtual ) {
		this.matrizAtual = matrizAtual;
	}
}