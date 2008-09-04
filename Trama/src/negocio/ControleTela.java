package negocio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
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
				
				for( String str : lista ){
					controleProjeto.adicionarColuna( str, matrizAtual );
				}
				controleProjeto.setArquivoColuna( nomeArquivo, matrizAtual );
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
				controleProjeto.setArquivoLinha( nomeArquivo, matrizAtual );
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
		String s = "ok";
		s = controleProjeto.resetarDestaque( matrizAtual );
		return s;
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
		LinkedList< String > l = null;
		
		try{
			s = controleProjeto.getTituloColunaArquivo( matrizAtual );
			
			if( s == null ){
				adicionarColunasModelo();
			} else{
				l = leitorDeModelo.getObjetos( s );
				final LinkedList< String > l2 = controleProjeto.triagemObjetos( matrizAtual, "coluna", l );
				
				JDialog dialog = new JDialog( tela ) {
					{
						initComponents();
						setVisible( true );
					}
					private static final long serialVersionUID = 1L;
					
					/** This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor. */
					@SuppressWarnings( { "unchecked", "serial", "synthetic-access" } )
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
						setTitle( "Sincronizar" );
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
							public void actionPerformed( ActionEvent e ) {
								controleProjeto.atualizarColuna( matrizAtual, listaAtual.getSelectedIndex(), listaNova.getSelectedValue().toString() );
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
						jPanel2Layout.setHorizontalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( fechar, javax.swing.GroupLayout.DEFAULT_SIZE,
							109, Short.MAX_VALUE ).addComponent( adicionar, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ).addComponent( atualizar,
							javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ) );
						jPanel2Layout.setVerticalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
							jPanel2Layout.createSequentialGroup().addGap( 177, 177, 177 ).addComponent( adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE )
								.addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.UNRELATED ).addComponent( atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
									javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE ).addComponent( fechar )
								.addContainerGap() ) );
						
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
						layout.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
							layout.createSequentialGroup().addContainerGap().addGroup(
								layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ).addComponent( jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
									Short.MAX_VALUE ).addComponent( jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) ).addContainerGap() ) );
						
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
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "erro";
		}
		return s;
	}
	
	public String sincronizarLinha() {
		String s = "ok";
		LinkedList< String > l = null;
		
		try{
			s = controleProjeto.getTituloLinhaArquivo( matrizAtual );
			
			if( s == null ){
				adicionarLinhasModelo();
			} else{
				l = leitorDeModelo.getObjetos( s );
				final LinkedList< String > l2 = controleProjeto.triagemObjetos( matrizAtual, "linha", l );
				
				JDialog dialog = new JDialog( tela ) {
					{
						initComponents();
						setVisible( true );
					}
					private static final long serialVersionUID = 1L;
					
					/** This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor. */
					@SuppressWarnings( { "unchecked", "serial", "synthetic-access" } )
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
						setTitle( "Sincronizar" );
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
						jPanel2Layout.setHorizontalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( fechar, javax.swing.GroupLayout.DEFAULT_SIZE,
							109, Short.MAX_VALUE ).addComponent( adicionar, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ).addComponent( atualizar,
							javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE ) );
						jPanel2Layout.setVerticalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
							jPanel2Layout.createSequentialGroup().addGap( 177, 177, 177 ).addComponent( adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE )
								.addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.UNRELATED ).addComponent( atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
									javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE ).addComponent( fechar )
								.addContainerGap() ) );
						
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
						layout.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
							layout.createSequentialGroup().addContainerGap().addGroup(
								layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ).addComponent( jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
									Short.MAX_VALUE ).addComponent( jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) ).addContainerGap() ) );
						
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
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "erro";
		}
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