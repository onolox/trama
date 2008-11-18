package negocio;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import negocio.leitor.LeitorDeModelo;
import visao.ModeloTabela;
import visao.Tela;

/**
 * Esta classe gerencia as regras de negócio do aplicativo e é o único ponto de iteração com a GUI, que é representada por {@link Tela}.
 * 
 * @author Fabio Marmitt
 */
public class ControleTela {
	
	/** Referência para a classe {@link ControleProjeto} */
	private ControleProjeto controleProjeto;
	/** Referência para a classe {@link Tela} */
	private Tela tela;
	/** Referência para a classe {@link LeitorDeModelo} */
	private LeitorDeModelo leitorDeModelo;
	/** A coluna que está atualmente selecionada */
	private int colunaAtual;
	/** A linha que está atualmente selecionada */
	private int linhaAtual;
	/** Matriz atualmente selecionada */
	private String matrizAtual;
	
	/**
	 * Construtor padrão da classe.
	 * 
	 * @param tela instância da classe {@link Tela}
	 */
	public ControleTela( Tela tela ) {
		this.tela = tela;
		leitorDeModelo = new LeitorDeModelo();
		colunaAtual = 0;
		linhaAtual = 0;
		matrizAtual = null;
	}
	
	/**
	 * Usado para abrir um novo projeto.
	 * 
	 * @param nome do projeto à ser aberto
	 * @param vazio se não existem matrizes no projeto
	 * @return lista de ModeloTabela
	 */
	public LinkedList< ModeloTabela > abrirProjeto( String nome, boolean vazio ) {
		LinkedList< ModeloTabela > l = new LinkedList< ModeloTabela >();
		int sn = 0;
		
		if( controleProjeto == null || vazio ){
			controleProjeto = new ControleProjeto();
			
			tela.setExcluirMatriz( true );
			tela.setExcluirMatrizMenu( true );
			tela.setImprimirMenu( true );
			tela.setSalvarImagemMenu( true );
			tela.setSalvarPDFMenu( true );
			tela.setSalvarProjeto( true );
			tela.setNovaMatriz( true );
			tela.setSalvarProjetoMenu( true );
			tela.setSalvarProjetoComo( true );
			tela.setNovaMatrizMenu( true );
			tela.setFecharProjetoMenu( true );
			
			l = controleProjeto.abrirProjeto( nome );
		} else{
			int c = JOptionPane.showConfirmDialog( tela, "Deseja salvar o projeto atual?", "Salvar projeto atual", 1 );
			
			if( c == JOptionPane.CANCEL_OPTION ) return new LinkedList< ModeloTabela >();
			if( c == JOptionPane.YES_OPTION ){
				String s = controleProjeto.salvarProjeto( "|vazio|" );
				if( s.equals( "|sem nome|" ) ){
					s = JOptionPane.showInputDialog( tela, "Insira um nome para o projeto", "Deseja salvar o projeto?", 1 );
					if( s != null ){
						if( new File( "arquivos/" + s.trim().replace( ".trama", "" ).replace( ".Trama", "" ).replace( "TRAMA", "" ) + ".trama" ).exists() )
							sn = JOptionPane.showConfirmDialog( tela, "Arquivo já existente, deseja sobreescrever?", "Atenção", JOptionPane.WARNING_MESSAGE );
						
						if( sn == JOptionPane.YES_OPTION ){
							s = controleProjeto.salvarProjeto( s );
							if( s.equals( "|sem nome|" ) ) controleProjeto.salvarProjeto( "|vazio|" );
						}
					}
				}
			}
			controleProjeto = null;
			l = abrirProjeto( nome, true );
		}
		return l;
	}
	
	/**
	 * Usado pra adicionar uma nova coluna.
	 * 
	 * @param nome da nova coluna
	 * @return estatus da operação
	 */
	public String adicionarColuna( String nome ) {
		String s = "ok";
		s = controleProjeto.adicionarColuna( nome, matrizAtual );
		return s;
	}
	
	/**
	 * Usado para adicionar colunas provindas de um arquivo que deverá ser selecionado pelo usuário.
	 * 
	 * @return lista de colunas
	 */
	public LinkedList< String > adicionarColunasModelo() {
		LinkedList< String > lista = null;
		boolean bol = true;
		final HashMap< String, LinkedList< String >> nE = leitorDeModelo.getNomesExtensoes();
		if( nE == null ){
			JOptionPane.showMessageDialog( tela, "Não há plugins disponíveis para uso", "", 0 );
			return null;
		}
		
		JFileChooser ch = new JFileChooser( "arquivos/" );
		
		try{
			ch.setDialogTitle( "Importar Colunas" );
			
			for( final String str : nE.keySet() ){
				ch.setFileFilter( new FileFilter() { // Filtro pra arquivos e diretorios
					
						/** {@inheritDoc} */
						@Override
						public boolean accept( File f ) {
							for( String s : nE.get( str ) ){
								if( f.getName().endsWith( s ) || f.isDirectory() ) return true;
							}
							return false;
						}
						
						/** {@inheritDoc} */
						@Override
						public String getDescription() {
							return str;
						}
					} );
			}
			
			int i = ch.showOpenDialog( tela );
			if( i == JFileChooser.APPROVE_OPTION ){
				File fil = ch.getSelectedFile();
				if( !fil.exists() ){
					JOptionPane.showMessageDialog( tela, "Erro na abertura do arquivo, arquivo não existente", "", 0 );
					return null;
				}
				String arq = controleProjeto.triagemMatrizes( matrizAtual, "coluna" );
				
				if( arq != null && !arq.endsWith( fil.getName() ) ){
					arq = new File( arq ).getName();
					
					int mess = JOptionPane.showConfirmDialog( tela, "<HTML>Atualmente já existe um arquivo definido para <b>" + matrizAtual.split( " X " )[ 1 ] + "</b> que é o arquivo <b>" + arq
							+ "</b><br/> deseja realmente importar deste arquivo <b>" + fil.getName() + "</b>?", "Problema na Importação", 2 );
					if( mess != JOptionPane.YES_OPTION ) bol = false;
				}
				if( bol ){
					lista = leitorDeModelo.getObjetos( fil.getAbsolutePath() );
					lista = controleProjeto.triagemObjetos( matrizAtual, "coluna", lista );
					controleProjeto.setArquivoColuna( fil.getAbsolutePath(), matrizAtual );
					
					for( String str : lista ){
						controleProjeto.adicionarColuna( str, matrizAtual );
					}
				}
			}
		} catch( Exception e ){
			lista = null;
			JOptionPane.showMessageDialog( tela, "Erro na importação" );
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
	
	/**
	 * Usado para adicionar linhas provindas de um arquivo que deverá ser selecionado pelo usuário.
	 * 
	 * @return lista de linhas
	 */
	public LinkedList< String > adicionarLinhasModelo() {
		LinkedList< String > lista = null;
		final HashMap< String, LinkedList< String >> nE = leitorDeModelo.getNomesExtensoes();
		if( nE == null ){
			JOptionPane.showMessageDialog( tela, "Não há plugins disponíveis para uso", "", 0 );
			return null;
		}
		JFileChooser ch = new JFileChooser( "arquivos/" );
		
		try{
			ch.setDialogTitle( "Importar Linhas" );
			
			for( final String str : nE.keySet() ){
				ch.setFileFilter( new FileFilter() { // Filtro pra arquivos e diretorios
					
						/** {@inheritDoc} */
						@Override
						public boolean accept( File f ) {
							for( String s : nE.get( str ) ){
								if( f.getName().endsWith( s ) || f.isDirectory() ) return true;
							}
							return false;
						}
						
						/** {@inheritDoc} */
						@Override
						public String getDescription() {
							return str;
						}
					} );
			}
			
			int i = ch.showOpenDialog( tela );
			if( i == JFileChooser.APPROVE_OPTION ){
				File fil = ch.getSelectedFile();
				if( !fil.exists() ){
					JOptionPane.showMessageDialog( tela, "Erro na abertura do arquivo, arquivo não existente", "", 0 );
					return null;
				}
				String arq = controleProjeto.triagemMatrizes( matrizAtual, "linha" );
				
				int mess = 0;
				if( arq != null && !arq.endsWith( fil.getName() ) ){
					arq = new File( arq ).getName();
					
					mess = JOptionPane.showConfirmDialog( tela, "<HTML>Atualmente já existe um arquivo definido para <b>" + matrizAtual.split( " X " )[ 0 ] + "</b> que é o arquivo <b>" + arq
							+ "</b><br/> deseja realmente importar deste arquivo <b>" + fil.getName() + "</b>?", "Problema na Importação", 2 );
					
				} else if( mess == JOptionPane.YES_OPTION ){
					
					lista = leitorDeModelo.getObjetos( fil.getAbsolutePath() );
					lista = controleProjeto.triagemObjetos( matrizAtual, "linha", lista );
					controleProjeto.setArquivoLinha( fil.getAbsolutePath(), matrizAtual );
					
					for( String str : lista )
						controleProjeto.adicionarLinha( str, matrizAtual );
				}
			}
		} catch( Exception e ){
			lista = null;
			JOptionPane.showMessageDialog( tela, "Erro na importação" );
		}
		return lista;
	}
	
	/**
	 * Usado para adicionar uma nova matriz ao projeto.
	 * 
	 * @param nome nome da nova matriz
	 * @return o ModeloTabela novo
	 */
	public ModeloTabela adicionarMatriz( String nome ) {
		ModeloTabela m = controleProjeto.adicionarMatriz( nome );
		tela.setExcluirMatriz( true );
		tela.setExcluirMatrizMenu( true );
		tela.setImprimirMenu( true );
		tela.setSalvarImagemMenu( true );
		tela.setSalvarPDFMenu( true );
		tela.setSalvarProjeto( true );
		tela.setSalvarProjetoMenu( true );
		tela.setSalvarProjetoComo( true );
		tela.setSincronizarMatrizMenu( true );
		
		matrizAtual = m.getNomeMatriz();
		
		adicionarLinha( "Linha" );
		adicionarColuna( "Coluna" );
		return m;
	}
	
	/**
	 * Usado para alterar a posição de uma coluna.
	 * 
	 * @param para para o lado que deve mover a coluna
	 * @return estatus da operação
	 */
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
	
	/**
	 * Usado para alterar a posição de uma linha.
	 * 
	 * @param para o lado que deve mover a linha
	 * @return estatus da operação
	 */
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
	
	/**
	 * Usado para atualizar o nome de uma coluna.
	 * 
	 * @param nome novo para a coluna
	 * @return estatus da operação
	 */
	public String atualizarColuna( String nome ) {
		String s = "ok";
		s = controleProjeto.atualizarColuna( matrizAtual, colunaAtual, nome );
		return s;
	}
	
	/**
	 * Usado para atualizar o nome de uma linha.
	 * 
	 * @param nome novo para a linha
	 * @return estatus da operação
	 */
	public String atualizarLinha( String nome ) {
		String s = "ok";
		s = controleProjeto.atualizarLinha( matrizAtual, linhaAtual, nome );
		return s;
	}
	
	/**
	 * Usado para criar um novo projeto.
	 * 
	 * @param vazio se não existem matrizes no projeto atual
	 * @return estatus da operação
	 */
	public String criarNovoProjeto( boolean vazio ) {
		String s = "ok";
		int sn = 0;
		
		if( controleProjeto == null || vazio ){
			controleProjeto = new ControleProjeto();
			
			tela.setNovaMatriz( true );
			tela.setNovaMatrizMenu( true );
			tela.setCancelarEdicao( false );
			tela.setOkEdicao( false );
			tela.setNomeTextField( false );
			tela.setDeslocar1( false );
			tela.setDeslocar2( false );
			tela.setNovaLinhaColuna( false );
			tela.setNovaLinhaColunaMenu( false );
			tela.setExcluirLinhaColuna( false );
			tela.setExcluirLinhaColunaMenu( false );
			tela.setOrdenar( false );
			tela.setOrdenarMenu( false );
			tela.setImportar( false );
			tela.setImportarDoModeloMenu( false );
			if( tela.isDestaque() ){
				tela.setDestacar( false );
				tela.setDestacarMenu( false );
			}
			tela.setSincronizar( false );
			tela.setSincronizarMatrizMenu( false );
			tela.setSincronizarMenu( false );
			tela.setResetarCamposNovosMenu( false );
			tela.setSalvarProjeto( false );
			tela.setSalvarProjetoMenu( false );
			tela.setSalvarProjetoComo( false );
			tela.setExcluirMatriz( false );
			tela.setExcluirMatrizMenu( false );
			tela.setImprimirMenu( false );
			tela.setSalvarPDFMenu( false );
			tela.setSalvarImagemMenu( false );
			tela.setFecharProjetoMenu( true );
			
		} else{
			int c = JOptionPane.showConfirmDialog( tela, "Deseja salvar o projeto atual?", "Salvar projeto atual", 1 );
			
			if( c == JOptionPane.CANCEL_OPTION ) return "Cancelar";
			
			if( c == JOptionPane.YES_OPTION ){
				s = controleProjeto.salvarProjeto( "|vazio|" );
				if( s.equals( "|sem nome|" ) ){
					s = JOptionPane.showInputDialog( tela, "Insira um nome para o projeto", "Deseja salvar o projeto?", 1 );
					if( s != null ){
						if( new File( "arquivos/" + s.trim().replace( ".trama", "" ).replace( ".Trama", "" ).replace( "TRAMA", "" ) + ".trama" ).exists() )
							sn = JOptionPane.showConfirmDialog( tela, "Arquivo já existente, deseja sobreescrever?", "Atenção", JOptionPane.WARNING_MESSAGE );
						
						if( sn == JOptionPane.YES_OPTION ){
							s = controleProjeto.salvarProjeto( s );
							controleProjeto = null;
							criarNovoProjeto( true );
						} else{
							s = "Cancelar";
						}
					}
				}
			} else criarNovoProjeto( true );
		}
		return s;
	}
	
	/**
	 * Usado para destacar os elementos que tenham relação com o objeto selecionado atualmente.
	 */
	public void destacarElementos() {
		if( linhaAtual == -1 ) controleProjeto.destacarElementos( colunaAtual, "coluna", matrizAtual );
		else if( colunaAtual == 0 ) controleProjeto.destacarElementos( linhaAtual, "linha", matrizAtual );
	}
	
	/**
	 * Usado para excluir uma coluna da matriz atual.
	 * 
	 * @return estatus da operação
	 */
	public String excluirColuna() {
		String s = "ok";
		if( colunaAtual > 0 ){
			s = controleProjeto.excluirColuna( colunaAtual, matrizAtual );
		}
		return s;
	}
	
	/**
	 * Usado para excluir uma linha da matriz atual.
	 * 
	 * @return estatus da operação
	 */
	public String excluirLinha() {
		String s = "ok";
		s = controleProjeto.excluirLinha( linhaAtual, matrizAtual );
		return s;
	}
	
	/**
	 * Usado para excluir uma matriz do projeto.
	 * 
	 * @return estatus da operação
	 */
	public String excluirMatriz() {
		String s = "ok";
		s = controleProjeto.excluirMatriz( matrizAtual );
		return s;
	}
	
	/**
	 * Usado para fechar o projeto atualmente aberto.
	 * 
	 * @param vazio se não existe matrizes
	 * @return se o projeto foi fechado
	 */
	public boolean fecharProjeto( boolean vazio ) {
		boolean bol = false;
		int sn = 0;
		
		if( vazio ){
			controleProjeto = null;
			tela.setFecharProjetoMenu( false );
			tela.setNovaMatriz( false );
			tela.setNovaMatrizMenu( false );
			tela.setExcluirMatriz( false );
			tela.setExcluirMatrizMenu( false );
			tela.setSalvarProjetoMenu( false );
			tela.setSalvarProjetoComo( false );
			tela.setSalvarProjeto( false );
			tela.setSalvarImagemMenu( false );
			tela.setSalvarPDFMenu( false );
			tela.setImprimirMenu( false );
			
			return true;
		}
		
		int c = JOptionPane.showConfirmDialog( tela, "Deseja salvar o projeto atual?", "Salvar projeto atual", 1 );
		
		if( c == JOptionPane.YES_OPTION ){
			String s = controleProjeto.salvarProjeto( "|vazio|" );
			if( s.equals( "|sem nome|" ) ){
				s = JOptionPane.showInputDialog( tela, "Insira um nome para o projeto", "Deseja salvar o projeto?", 0 );
				
				if( s != null ){
					if( new File( "arquivos/" + s.trim().replace( ".trama", "" ).replace( ".Trama", "" ).replace( "TRAMA", "" ) + ".trama" ).exists() )
						sn = JOptionPane.showConfirmDialog( tela, "Arquivo já existente, deseja sobreescrever?", "Atenção", JOptionPane.WARNING_MESSAGE );
					
					if( sn == JOptionPane.YES_OPTION ){
						s = controleProjeto.salvarProjeto( s.trim().replace( ".trama", "" ).replace( ".Trama", "" ).replace( "TRAMA", "" ) );
						controleProjeto = null;
						tela.setFecharProjetoMenu( false );
						tela.setNovaMatriz( false );
						tela.setNovaMatrizMenu( false );
						tela.setExcluirMatriz( false );
						tela.setExcluirMatrizMenu( false );
						tela.setSalvarProjetoMenu( false );
						tela.setSalvarProjetoComo( false );
						tela.setSalvarProjeto( false );
						tela.setSalvarImagemMenu( false );
						tela.setSalvarPDFMenu( false );
						tela.setImprimirMenu( false );
						bol = true;
					}
				}
			}
		} else if( c == JOptionPane.NO_OPTION ){
			controleProjeto = null;
			tela.setFecharProjetoMenu( false );
			tela.setNovaMatriz( false );
			tela.setNovaMatrizMenu( false );
			tela.setExcluirMatriz( false );
			tela.setExcluirMatrizMenu( false );
			tela.setSalvarProjetoMenu( false );
			tela.setSalvarProjetoComo( false );
			tela.setSalvarProjeto( false );
			tela.setSalvarImagemMenu( false );
			tela.setSalvarPDFMenu( false );
			tela.setImprimirMenu( false );
			bol = true;
		}
		
		return bol;
	}
	
	/**
	 * Usado para ordenar as colunas da matriz atual.
	 * 
	 * @return estatus da operação
	 */
	public String ordenarColuna() {
		String s = "ok";
		s = controleProjeto.ordenarColuna( matrizAtual );
		return s;
	}
	
	/**
	 * Usado para ordenar as linhas da matriz atual.
	 * 
	 * @return estatus da operação
	 */
	public String ordenarLinha() {
		String s = "ok";
		s = controleProjeto.ordenarLinha( matrizAtual );
		return s;
	}
	
	/**
	 * Usado para resetar o destaque que a matriz possa ter.
	 * 
	 * @return estatus da operação
	 */
	public String resetarDestaque() {
		String s = "ok";
		s = controleProjeto.resetarDestaque( matrizAtual );
		return s;
	}
	
	/**
	 * Usado para salvar o projeto atual.
	 * 
	 * @param nome nome do projeto
	 * @return estatus da operação
	 */
	public String salvarProjeto( String nome ) {
		String s = "ok";
		s = controleProjeto.salvarProjeto( nome.trim().replace( ".trama", "" ).replace( ".Trama", "" ).replace( "TRAMA", "" ) );
		return s;
	}
	
	/**
	 * Usado para setar um dado nas células da matriz atual.
	 * 
	 * @return estatus da operação
	 */
	public String setDado() {
		String s = "ok";
		s = controleProjeto.setDado( linhaAtual, colunaAtual, matrizAtual );
		return s;
	}
	
	/**
	 * Usado para sincronizar as colunas com um arquivo.
	 * 
	 * @return estatus da operação
	 */
	public String sincronizarColuna() {
		String s = "ok";
		String s2 = "ok";
		LinkedList< String > l = null;
		JDialog dialog = null;
		try{
			s = controleProjeto.getTituloColunaArquivo( matrizAtual );
			
			if( s == null ){
				adicionarColunasModelo();
			} else{
				File fil = new File( s );
				if( !fil.exists() ){
					int op = JOptionPane.showConfirmDialog( tela, "<HTML>Arquivo não encontrado, deseja importá-lo novamente?<br/>" + "O arquivo é: <b>" + fil.getName(),
						"Erro na abertura do arquivo", 0 );
					if( op == JOptionPane.YES_OPTION ) adicionarColunasModelo();
					
				} else{
					l = leitorDeModelo.getObjetos( s );
					
					if( l == null || l.isEmpty() ) return "Erro ao buscar objetos do arquivo";
					final LinkedList< String > l2 = controleProjeto.triagemObjetos( matrizAtual, "coluna", l );
					if( l2.size() > 0 ){
						dialog = new JDialog( tela ) {
							
							{
								initComponents();
								setVisible( true );
							}
							private static final long serialVersionUID = 1L;
							
							/**
							 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
							 * Editor.
							 */
							@SuppressWarnings( { "serial", "synthetic-access" } )
							private void initComponents() {
								
								jPanel1 = new javax.swing.JPanel();
								jScrollPane1 = new javax.swing.JScrollPane();
								listaNova = new javax.swing.JList();
								jPanel2 = new javax.swing.JPanel();
								adicionar = new javax.swing.JButton();
								atualizar = new javax.swing.JButton();
								fechar = new javax.swing.JButton();
								jPanel3 = new javax.swing.JPanel();
								jScrollPane2 = new javax.swing.JScrollPane();
								listaAtual = new javax.swing.JList();
								
								setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
								setTitle( "Sincronizar Colunas" );
								setLocation( tela.getLocationOnScreen() );
								setModal( true );
								
								jPanel1.setBorder( javax.swing.BorderFactory.createTitledBorder( null, "Novos Objetos", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION ) );
								
								listaNova.setModel( new DefaultListModel() );
								for( int i = 0; i < l2.size(); i++ )
									( ( DefaultListModel ) listaNova.getModel() ).addElement( l2.get( i ) );
								
								jScrollPane1.setViewportView( listaNova );
								
								GroupLayout jPanel1Layout = new GroupLayout( jPanel1 );
								jPanel1.setLayout( jPanel1Layout );
								jPanel1Layout.setHorizontalGroup( jPanel1Layout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( jScrollPane1, GroupLayout.DEFAULT_SIZE, 173,
									Short.MAX_VALUE ) );
								jPanel1Layout.setVerticalGroup( jPanel1Layout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( jScrollPane1, GroupLayout.DEFAULT_SIZE, 412,
									Short.MAX_VALUE ) );
								
								adicionar.setText( "Adicionar >>" );// ------------------------------Listeners
								adicionar.addActionListener( new ActionListener() {
									
									/** {@inheritDoc} */
									public void actionPerformed( ActionEvent e ) {
										if( listaNova.isSelectionEmpty() ){
											JOptionPane.showMessageDialog( tela, "Você deve escolher um nome na lista de Novos Objetos", "", 0 );
											return;
										}
										Object[] obj = listaNova.getSelectedValues();
										for( int i = 0; i < obj.length; i++ ){
											controleProjeto.adicionarColuna( obj[ i ].toString(), matrizAtual );
											( ( DefaultListModel ) listaAtual.getModel() ).addElement( obj[ i ] );
											( ( DefaultListModel ) listaNova.getModel() ).removeElement( obj[ i ] );
										}
									}
								} );
								
								atualizar.setText( "Atualizar >>" );
								atualizar.addActionListener( new ActionListener() {
									
									/** {@inheritDoc} */
									public void actionPerformed( ActionEvent e ) {
										if( listaNova.isSelectionEmpty() ){
											JOptionPane.showMessageDialog( tela, "Você deve escolher um nome na lista de Novos Objetos", "", 0 );
											return;
										}
										if( listaAtual.isSelectionEmpty() ){
											JOptionPane.showMessageDialog( tela, "Você deve escolher um nome na lista de Objetos Atuais", "", 0 );
											return;
										}
										
										controleProjeto.atualizarColuna( matrizAtual, listaAtual.getSelectedIndex(), listaNova.getSelectedValue().toString() );
										( ( DefaultListModel ) listaAtual.getModel() ).add( listaAtual.getSelectedIndex(), listaNova.getSelectedValue() );
										( ( DefaultListModel ) listaAtual.getModel() ).remove( listaAtual.getSelectedIndex() );
										( ( DefaultListModel ) listaNova.getModel() ).remove( listaNova.getSelectedIndex() );
									}
								} );
								
								fechar.setText( "Fechar" );
								fechar.addActionListener( new ActionListener() {
									
									/** {@inheritDoc} */
									public void actionPerformed( ActionEvent e ) {
										setVisible( false );
									}
								} );
								
								javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout( jPanel2 );
								jPanel2.setLayout( jPanel2Layout );
								jPanel2Layout.setHorizontalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( fechar,
									javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ).addComponent( adicionar, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ).addComponent(
									atualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ) );
								jPanel2Layout.setVerticalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
									jPanel2Layout.createSequentialGroup().addGap( 177, 177, 177 ).addComponent( adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
										javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.UNRELATED ).addComponent( atualizar,
										javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148,
										Short.MAX_VALUE ).addComponent( fechar ).addContainerGap() ) );
								
								jPanel2Layout.linkSize( SwingConstants.VERTICAL, new Component[] { adicionar, atualizar } );
								jPanel3.setBorder( BorderFactory.createTitledBorder( null, "Objetos Atuais", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION ) );
								
								listaAtual.setModel( new DefaultListModel() );
								listaAtual.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
								
								for( String str : controleProjeto.getNomes( matrizAtual, "coluna" ) ){
									( ( DefaultListModel ) listaAtual.getModel() ).addElement( str );
								}
								
								jScrollPane2.setViewportView( listaAtual );
								
								javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout( jPanel3 );
								jPanel3.setLayout( jPanel3Layout );
								jPanel3Layout.setHorizontalGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jScrollPane2,
									javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE ) );
								jPanel3Layout.setVerticalGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jScrollPane2,
									javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE ) );
								
								javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane() );
								getContentPane().setLayout( layout );
								layout.setHorizontalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
									layout.createSequentialGroup().addContainerGap().addComponent( jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addComponent( jPanel2,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addComponent( jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE ).addContainerGap( javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) ) );
								layout
									.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
										layout.createSequentialGroup().addContainerGap().addGroup(
											layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ).addComponent( jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ).addComponent( jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) ).addContainerGap() ) );
								
								pack();
							}// </editor-fold>
							
							// Variables declaration - do not modify
							private javax.swing.JButton adicionar;
							private javax.swing.JButton atualizar;
							private javax.swing.JButton fechar;
							private javax.swing.JPanel jPanel1;
							private javax.swing.JPanel jPanel2;
							private javax.swing.JPanel jPanel3;
							private javax.swing.JScrollPane jScrollPane1;
							private javax.swing.JScrollPane jScrollPane2;
							private javax.swing.JList listaAtual;
							private javax.swing.JList listaNova;
							// End of variables declaration
							
						};
						dialog.dispose();
					} else JOptionPane.showMessageDialog( tela, "Não há itens novos para as colunas" );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s2 = "Erro na sincronização";
		}
		return s2;
	}
	
	/**
	 * Usado para sincronizar as linhas com um arquivo.
	 * 
	 * @return estatus da operação
	 */
	public String sincronizarLinha() {
		String s = "ok";
		String s2 = "ok";
		LinkedList< String > l = null;
		
		try{
			s = controleProjeto.getTituloLinhaArquivo( matrizAtual );
			
			if( s == null ){
				adicionarLinhasModelo();
			} else{
				File fil = new File( s );
				if( !fil.exists() ){
					int op = JOptionPane.showConfirmDialog( tela, "<HTML>Arquivo não encontrado, deseja importá-lo novamente?<br/>" + "O arquivo é: <b>" + fil.getName(),
						"Erro na abertura do arquivo", 0 );
					if( op == JOptionPane.YES_OPTION ) adicionarLinhasModelo();
				} else{
					l = leitorDeModelo.getObjetos( s );
					if( l == null || l.isEmpty() ) return "Erro ao buscar objetos do arquivo";
					
					final LinkedList< String > l2 = controleProjeto.triagemObjetos( matrizAtual, "linha", l );
					
					if( l2.size() > 0 ){
						JDialog dialog = new JDialog( tela ) {
							
							{
								initComponents();
								setVisible( true );
							}
							private static final long serialVersionUID = 1L;
							
							/**
							 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
							 * Editor.
							 */
							@SuppressWarnings( { "serial", "synthetic-access" } )
							private void initComponents() {
								
								jPanel1 = new javax.swing.JPanel();
								jScrollPane1 = new javax.swing.JScrollPane();
								listaNova = new javax.swing.JList();
								jPanel2 = new javax.swing.JPanel();
								adicionar = new javax.swing.JButton();
								atualizar = new javax.swing.JButton();
								fechar = new javax.swing.JButton();
								jPanel3 = new javax.swing.JPanel();
								jScrollPane2 = new javax.swing.JScrollPane();
								listaAtual = new javax.swing.JList();
								
								setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
								setTitle( "Sincronizar Linhas" );
								setLocation( tela.getLocationOnScreen() );
								setModal( true );
								
								jPanel1.setBorder( javax.swing.BorderFactory.createTitledBorder( null, "Novos Objetos", javax.swing.border.TitledBorder.CENTER,
									javax.swing.border.TitledBorder.DEFAULT_POSITION ) );
								
								listaNova.setModel( new DefaultListModel() );
								for( int i = 0; i < l2.size(); i++ )
									( ( DefaultListModel ) listaNova.getModel() ).addElement( l2.get( i ) );
								
								jScrollPane1.setViewportView( listaNova );
								
								javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout( jPanel1 );
								jPanel1.setLayout( jPanel1Layout );
								jPanel1Layout.setHorizontalGroup( jPanel1Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jScrollPane1,
									javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE ) );
								jPanel1Layout.setVerticalGroup( jPanel1Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jScrollPane1,
									javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE ) );
								
								adicionar.setText( "Adicionar >>" );// ------------------------------Listeners
								adicionar.addActionListener( new ActionListener() {
									
									public void actionPerformed( ActionEvent e ) {
										if( listaNova.isSelectionEmpty() ){
											JOptionPane.showMessageDialog( tela, "Você deve escolher um nome na lista de Novos Objetos", "", 0 );
											return;
										}
										Object[] obj = listaNova.getSelectedValues();
										for( int i = 0; i < obj.length; i++ ){
											controleProjeto.adicionarLinha( obj[ i ].toString(), matrizAtual );
											( ( DefaultListModel ) listaAtual.getModel() ).addElement( obj[ i ] );
											( ( DefaultListModel ) listaNova.getModel() ).removeElement( obj[ i ] );
										}
									}
								} );
								
								atualizar.setText( "Atualizar >>" );
								atualizar.addActionListener( new ActionListener() {
									
									public void actionPerformed( ActionEvent e ) {
										if( listaNova.isSelectionEmpty() ){
											JOptionPane.showMessageDialog( tela, "Você deve escolher um nome na lista de Novos Objetos", "", 0 );
											return;
										}
										if( listaAtual.isSelectionEmpty() ){
											JOptionPane.showMessageDialog( tela, "Você deve escolher um nome na lista de Objetos Atuais", "", 0 );
											return;
										}
										
										controleProjeto.atualizarLinha( matrizAtual, listaAtual.getSelectedIndex(), listaNova.getSelectedValue().toString() );
										( ( DefaultListModel ) listaAtual.getModel() ).add( listaAtual.getSelectedIndex(), listaNova.getSelectedValue() );
										( ( DefaultListModel ) listaAtual.getModel() ).remove( listaAtual.getSelectedIndex() );
										( ( DefaultListModel ) listaNova.getModel() ).remove( listaNova.getSelectedIndex() );
									}
								} );
								
								fechar.setText( "Fechar" );
								fechar.addActionListener( new ActionListener() {
									
									public void actionPerformed( ActionEvent e ) {
										setVisible( false );
									}
									
								} );
								
								javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout( jPanel2 );
								jPanel2.setLayout( jPanel2Layout );
								jPanel2Layout.setHorizontalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( fechar,
									javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ).addComponent( adicionar, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ).addComponent(
									atualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ) );
								jPanel2Layout.setVerticalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
									jPanel2Layout.createSequentialGroup().addGap( 177, 177, 177 ).addComponent( adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
										javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.UNRELATED ).addComponent( atualizar,
										javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148,
										Short.MAX_VALUE ).addComponent( fechar ).addContainerGap() ) );
								
								jPanel2Layout.linkSize( javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] { adicionar, atualizar } );
								
								jPanel3.setBorder( javax.swing.BorderFactory.createTitledBorder( null, "Objetos Atuais", javax.swing.border.TitledBorder.CENTER,
									javax.swing.border.TitledBorder.DEFAULT_POSITION ) );
								
								listaAtual.setModel( new DefaultListModel() );
								listaAtual.setSelectionMode( javax.swing.ListSelectionModel.SINGLE_SELECTION );
								
								for( String str : controleProjeto.getNomes( matrizAtual, "linha" ) ){
									( ( DefaultListModel ) listaAtual.getModel() ).addElement( str );
								}
								
								jScrollPane2.setViewportView( listaAtual );
								
								javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout( jPanel3 );
								jPanel3.setLayout( jPanel3Layout );
								jPanel3Layout.setHorizontalGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jScrollPane2,
									javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE ) );
								jPanel3Layout.setVerticalGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jScrollPane2,
									javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE ) );
								
								javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane() );
								getContentPane().setLayout( layout );
								layout.setHorizontalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
									layout.createSequentialGroup().addContainerGap().addComponent( jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addComponent( jPanel2,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addComponent( jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE ).addContainerGap( javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) ) );
								layout
									.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
										layout.createSequentialGroup().addContainerGap().addGroup(
											layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ).addComponent( jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ).addComponent( jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) ).addContainerGap() ) );
								
								pack();
							}// </editor-fold>
							
							// Variables declaration - do not modify
							private javax.swing.JButton adicionar;
							private javax.swing.JButton atualizar;
							private javax.swing.JButton fechar;
							private javax.swing.JPanel jPanel1;
							private javax.swing.JPanel jPanel2;
							private javax.swing.JPanel jPanel3;
							private javax.swing.JScrollPane jScrollPane1;
							private javax.swing.JScrollPane jScrollPane2;
							private javax.swing.JList listaAtual;
							private javax.swing.JList listaNova;
							// End of variables declaration
							
						};
						dialog.dispose();
					} else JOptionPane.showMessageDialog( tela, "Não há itens novos para as linhas" );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s2 = "Erro na sincronização";
		}
		return s2;
	}
	
	/**
	 * Busca a coluna atual.
	 * 
	 * @return the colunaAtual
	 */
	public int getColunaAtual() {
		return colunaAtual;
	}
	
	/**
	 * Seta a coluna atual.
	 * 
	 * @param colunaAtual the colunaAtual to set
	 */
	public void setColunaAtual( int colunaAtual ) {
		this.colunaAtual = colunaAtual;
	}
	
	/**
	 * Busca a linha atual.
	 * 
	 * @return the linhaAtual
	 */
	public int getLinhaAtual() {
		return linhaAtual;
	}
	
	/**
	 * Seta a linha atual.
	 * 
	 * @param linhaAtual the linhaAtual to set
	 */
	public void setLinhaAtual( int linhaAtual ) {
		this.linhaAtual = linhaAtual;
	}
	
	/**
	 * Busca a matriz atualmente selecionada.
	 * 
	 * @return the matrizAtual
	 */
	public String getMatrizAtual() {
		return matrizAtual;
	}
	
	/**
	 * Seta uma nova matriz atualmente selecionada.
	 * 
	 * @param matrizAtual the matrizAtual to set
	 */
	public void setMatrizAtual( String matrizAtual ) {
		this.matrizAtual = matrizAtual;
	}
}