package visao;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import negocio.ControleTela;
import negocio.Matriz;

/**
 * Esta classe é usada para gerar e gerenciar a interface gráfica com o usuário.<br>
 * Usa {@link JTableCustomizado} para exibir as matrizes.
 * 
 * @author Fabio Marmitt
 */
public class Tela extends JFrame implements ActionListener {
	
	/** Referência ao header de um {@link JTableCustomizado} */
	private JTableHeader header;
	/** Contém uma instância de {@link ControleTela} */
	private ControleTela controle;
	/** Contém uma lista de {@link JTableCustomizado} que estão na interface */
	private LinkedList< JTableCustomizado > matrizes;
	/** Contém uma lista de JPanel que estão na interface */
	private LinkedList< JPanel > JP;
	
	/**
	 * Construtor padrão.
	 */
	public Tela() {
		initComponents();
		setLocationRelativeTo( null );
		
		addWindowListener( new WindowAdapter() {
			
			/** {@inheritDoc} */
			public void windowClosing( WindowEvent evt ) {
				if( salvarProjeto.isEnabled() ){
					int s1 = JOptionPane.showConfirmDialog( null, "Deseja salvar o projeto atual?" );
					if( s1 == JOptionPane.YES_OPTION ){
						String s = controle.salvarProjeto( "|vazio|" );
						if( s.equals( "|sem nome|" ) ){
							s = JOptionPane.showInputDialog( null, "Insira um nome para o projeto", "Nome do projeto", 3 );
							if( s != null ){
								s = controle.salvarProjeto( s );
								if( s.equals( "|sem nome|" ) ){
									salvarProjeto();
								} else{
									setTitle( "Trama  ---->  Projeto Salvo Com Sucesso" );
									try{
										Thread.sleep( 3000 );
									} catch( InterruptedException e ){
										e.printStackTrace();
									}
									setTitle( "Trama" );
									System.exit( 0 );
								}
							} else return;
						} else{
							if( s.equalsIgnoreCase( "ok" ) ){
								setTitle( "Trama  ---->  Projeto Salvo Com Sucesso" );
								try{
									Thread.sleep( 3000 );
								} catch( InterruptedException e ){
									e.printStackTrace();
								}
								setTitle( "Trama" );
								System.exit( 0 );
							} else JOptionPane.showMessageDialog( null, s, "", 0 );
						}
					} else if( s1 == JOptionPane.NO_OPTION ) System.exit( 0 );
				} else System.exit( 0 );
			}
		} );
		
		controle = new ControleTela( this );
		matrizes = new LinkedList< JTableCustomizado >();
		JP = new LinkedList< JPanel >();
	}
	
	/**
	 * Usado para abrir um novo projeto.
	 */
	private void abrirProjeto() {
		try{
			JFileChooser ch = new JFileChooser( "arquivos/" );
			ch.setDialogTitle( "Abrir projeto" );
			ch.setFileFilter( new FileFilter() { // Filtro pra xml e diretorios
				
					/** {@inheritDoc} */
					@Override
					public boolean accept( File f ) {
						if( f.getName().endsWith( "trama" ) || f.isDirectory() ){ return true; }
						return false;
					}
					
					/** {@inheritDoc} */
					@Override
					public String getDescription() {
						return "Arquivos de projeto";
					}
				} );
			
			int i = ch.showOpenDialog( this );
			if( i == JFileChooser.APPROVE_OPTION ){
				File fil = ch.getSelectedFile();
				LinkedList< ModeloTabela > lis = controle.abrirProjeto( fil.getName(), matrizes.isEmpty() );
				
				if( lis == null ){
					JOptionPane.showMessageDialog( this, "Erro na abertura do projeto, arquivo não existente", "", 0 );
					return;
				} else if( lis.isEmpty() ) return;
				
				matrizes = new LinkedList< JTableCustomizado >();
				jTabbedPane1.removeAll();
				JP.clear();
				
				for( ModeloTabela modeloTabela : lis ){
					JPanel j = new JPanel();
					JScrollPane js = new JScrollPane();
					JTableCustomizado jT = new JTableCustomizado( modeloTabela );
					GroupLayout jPanelLayout = new GroupLayout( j );
					
					jPanelLayout.setHorizontalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE ) );
					jPanelLayout.setVerticalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE ) );
					j.setLayout( jPanelLayout );
					
					j.setName( modeloTabela.getNomeMatriz() );
					adicionarListener( jT );
					js.setViewportView( jT );
					j.add( js );
					
					JP.add( j );
					matrizes.add( jT );
					jTabbedPane1.add( j );
				}
				jTabbedPane1.repaint();
				jTabbedPane1.revalidate();
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado pra adicionar uma nova coluna.
	 */
	private void adicionarColuna() {
		String s = "";
		try{
			s = JOptionPane.showInputDialog( this, "Insira o nome desejado para a coluna", "Adicionar Coluna", JOptionPane.QUESTION_MESSAGE );
			if( s == null ) return;
			s = s.trim();
			if( s.equalsIgnoreCase( "" ) ) adicionarColuna();
			else{
				s = controle.adicionarColuna( s );
				
				if( !s.equalsIgnoreCase( "ok" ) ){
					JOptionPane.showMessageDialog( this, s, "Erro", 1 );
				}
				for( int i = 0; i < matrizes.size(); i++ ){
					if( matrizes.get( i ).getNome().equalsIgnoreCase( controle.getMatrizAtual() ) ){
						JTableCustomizado jt = matrizes.remove( i );
						ModeloTabela mod = ( ModeloTabela ) jt.getModel();
						JPanel jpanel = JP.get( i );
						jpanel.removeAll();
						JTableCustomizado cus = new JTableCustomizado( mod );
						adicionarListener( cus );
						matrizes.add( i, cus );
						JScrollPane js = new JScrollPane();
						js.setViewportView( cus );
						
						GroupLayout jPanelLayout = new GroupLayout( jpanel );
						jpanel.setLayout( jPanelLayout );
						jPanelLayout.setHorizontalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE ) );
						jPanelLayout.setVerticalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE ) );
						jpanel.add( js );
						jpanel.updateUI();
						jpanel.repaint();
						
						cus.setColunaAtual( controle.getColunaAtual() );
						cus.setColunaSelecionada( controle.getColunaAtual() );
						cus.setLinhaSelecionada( -1 );
						cus.setLinhaAtual( -1 );
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para adicionar colunas provindas de um arquivo que deverá ser selecionado pelo usuário.
	 */
	private void adicionarColunasModelo() {
		if( controle.adicionarColunasModelo() != null ){
			for( int i = 0; i < matrizes.size(); i++ ){
				if( matrizes.get( i ).getNome().equalsIgnoreCase( controle.getMatrizAtual() ) ){
					JTableCustomizado jt = matrizes.remove( i );
					ModeloTabela mod = ( ModeloTabela ) jt.getModel();
					JPanel jpanel = JP.get( i );
					jpanel.removeAll();
					JTableCustomizado cus = new JTableCustomizado( mod );
					adicionarListener( cus );
					matrizes.add( i, cus );
					JScrollPane js = new JScrollPane();
					js.setViewportView( cus );
					
					GroupLayout jPanelLayout = new GroupLayout( jpanel );
					jpanel.setLayout( jPanelLayout );
					jPanelLayout.setHorizontalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE ) );
					jPanelLayout.setVerticalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE ) );
					jpanel.add( js );
					resetarDestaque();
					jpanel.updateUI();
					jpanel.repaint();
					
					cus.setColunaAtual( controle.getColunaAtual() );
					cus.setColunaSelecionada( controle.getColunaAtual() );
					cus.setLinhaSelecionada( -1 );
					cus.setLinhaAtual( -1 );
				}
			}
		}
	}
	
	/**
	 * Usado para adicionar uma nova linha na matriz atual.
	 */
	private void adicionarLinha() {
		String s = "";
		try{
			s = JOptionPane.showInputDialog( this, "Insira o nome desejado para a linha", "Adicionar Linha", JOptionPane.QUESTION_MESSAGE );
			if( s == null ) return;
			s = s.trim();
			if( s.equalsIgnoreCase( "" ) ) adicionarLinha();
			else{
				s = controle.adicionarLinha( s );
				
				if( !s.equalsIgnoreCase( "ok" ) ) JOptionPane.showMessageDialog( this, s, "Erro", 1 );
				
				for( JTableCustomizado j : matrizes ){
					if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
						ModeloTabela t = ( ModeloTabela ) j.getModel();
						t.fireTableDataChanged();
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para adicionar linhas provindas de um arquivo que deverá ser selecionado pelo usuário.
	 */
	private void adicionarLinhasModelo() {
		if( controle.adicionarLinhasModelo() != null ){
			for( JTableCustomizado j : matrizes ){
				if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
					ModeloTabela t = ( ModeloTabela ) j.getModel();
					t.fireTableDataChanged();
					resetarDestaque();
				}
			}
		}
	}
	
	/**
	 * Usado para adicionar uma nova matriz ao projeto.
	 */
	private void adicionarMatriz() {
		new JDialog( this, true ) {
			
			String s = "";
			{
				initComponents();
				setVisible( true );
				setResizable( false );
				
			}
			
			/**
			 * This method is called from within the constructor to initialize the form.
			 */
			@SuppressWarnings( "unchecked" )
			private void initComponents() {
				jLabel1 = new javax.swing.JLabel();
				okButton = new javax.swing.JButton();
				cancelarButton = new javax.swing.JButton();
				jLabel2 = new javax.swing.JLabel();
				linha = new javax.swing.JTextField();
				jLabel3 = new javax.swing.JLabel();
				coluna = new javax.swing.JTextField();
				jLabel4 = new javax.swing.JLabel();
				
				setDefaultCloseOperation( javax.swing.WindowConstants.DISPOSE_ON_CLOSE );
				setLocation( getOwner().getLocationOnScreen().x, getOwner().getLocationOnScreen().y + 130 );
				setTitle( "Nova Matriz" );
				
				jLabel1.setHorizontalAlignment( javax.swing.SwingConstants.CENTER );
				jLabel1.setText( "Insira o nome da matriz" );
				
				okButton.setText( "OK" );
				okButton.addActionListener( new ActionListener() {
					
					/** {@inheritDoc } */
					@SuppressWarnings( "synthetic-access" )
					public void actionPerformed( ActionEvent e ) {
						if( ( !linha.getText().trim().isEmpty() && !coluna.getText().trim().isEmpty() ) && ( !linha.getText().trim().equals( coluna.getText().trim() ) ) ){
							linha.setText( linha.getText().trim() );
							coluna.setText( coluna.getText().trim() );
							s = linha.getText() + " X " + coluna.getText();
							
							for( JTableCustomizado jtab : matrizes ){
								if( ( linha.getText().equalsIgnoreCase( jtab.getNome().split( " X " )[ 0 ] ) && coluna.getText().equalsIgnoreCase( jtab.getNome().split( " X " )[ 1 ] ) )
										|| ( linha.getText().equalsIgnoreCase( jtab.getNome().split( " X " )[ 1 ] ) && coluna.getText().equalsIgnoreCase( jtab.getNome().split( " X " )[ 0 ] ) ) ){
									
									JOptionPane.showMessageDialog( null, "Já existe uma matriz com esses 2 nomes.", "Alerta de matriz duplicada", 2 );
									return;
								}
							}
							
							ModeloTabela m = controle.adicionarMatriz( s );
							JPanel j = new JPanel();
							JScrollPane js = new JScrollPane();
							GroupLayout jPanelLayout = new GroupLayout( j );
							
							j.setLayout( jPanelLayout );
							jPanelLayout.setHorizontalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE ) );
							jPanelLayout.setVerticalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE ) );
							JP.add( j );
							j.setName( m.getNomeMatriz() );
							JTableCustomizado jT = new JTableCustomizado( m );
							
							js.setViewportView( jT );
							j.add( js );
							
							adicionarListener( jT );
							
							matrizes.add( jT );
							jTabbedPane1.add( j );
							dispose();
						}
					}
				} );
				
				cancelarButton.setText( "Cancelar" );
				cancelarButton.addActionListener( new ActionListener() {
					
					/** {@inheritDoc } */
					public void actionPerformed( ActionEvent e ) {
						dispose();
					}
				} );
				
				jLabel2.setText( "Linha" );
				linha.setHorizontalAlignment( JTextField.RIGHT );
				
				jLabel3.setFont( new java.awt.Font( "Tahoma", 1, 14 ) ); // NOI18N
				jLabel3.setText( "X" );
				jLabel4.setText( "Coluna" );
				
				javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane() );
				getContentPane().setLayout( layout );
				layout.setHorizontalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
					layout.createSequentialGroup().addContainerGap().addGroup(
						layout.createParallelGroup( javax.swing.GroupLayout.Alignment.TRAILING, false ).addComponent( jLabel1, javax.swing.GroupLayout.Alignment.LEADING,
							javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ).addGroup(
							javax.swing.GroupLayout.Alignment.LEADING,
							layout.createSequentialGroup().addGroup(
								layout.createParallelGroup( javax.swing.GroupLayout.Alignment.TRAILING ).addComponent( okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74,
									javax.swing.GroupLayout.PREFERRED_SIZE ).addGroup(
									layout.createSequentialGroup().addComponent( jLabel2 ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addComponent( linha,
										javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE ) ) ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
								.addComponent( jLabel3 ).addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addGroup(
									layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
										layout.createSequentialGroup().addComponent( coluna, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap(
											javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addComponent( jLabel4 ) ).addComponent( cancelarButton ) ) ) ).addContainerGap() ) );
				layout.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
					layout.createSequentialGroup().addContainerGap().addComponent( jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addGroup(
						layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE ).addComponent( jLabel2 ).addComponent( linha, javax.swing.GroupLayout.PREFERRED_SIZE,
							javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE ).addComponent( jLabel3 ).addComponent( coluna, javax.swing.GroupLayout.PREFERRED_SIZE,
							javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE ).addComponent( jLabel4 ) ).addGap( 28, 28, 28 ).addGroup(
						layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE ).addComponent( okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
							javax.swing.GroupLayout.PREFERRED_SIZE ).addComponent( cancelarButton ) ).addContainerGap( javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) ) );
				
				pack();
			}// </editor-fold>
			
			private javax.swing.JButton cancelarButton;
			private javax.swing.JTextField coluna;
			private javax.swing.JLabel jLabel1;
			private javax.swing.JLabel jLabel2;
			private javax.swing.JLabel jLabel3;
			private javax.swing.JLabel jLabel4;
			private javax.swing.JTextField linha;
			private javax.swing.JButton okButton;
		};
	}
	
	/**
	 * Usado para alterar a posição de uma coluna.
	 * 
	 * @param lado O lado para o qual a coluna deve ser deslocada.
	 */
	private void alterarPosicaoColuna( String lado ) {
		String s = "";
		s = controle.alterarPosicaoColuna( lado );
		
		if( !s.equalsIgnoreCase( "fora" ) ){
			for( int i = 0; i < matrizes.size(); i++ ){
				if( matrizes.get( i ).getNome().equalsIgnoreCase( controle.getMatrizAtual() ) ){
					JTableCustomizado jt = matrizes.remove( i );
					ModeloTabela mod = ( ModeloTabela ) jt.getModel();
					jt.setModel( new DefaultTableModel() );
					jt = null;
					
					JPanel jpanel = JP.get( i );
					jpanel.removeAll();
					JTableCustomizado cus = new JTableCustomizado( mod );
					adicionarListener( cus );
					matrizes.add( i, cus );
					JScrollPane js = new JScrollPane();
					js.setViewportView( cus );
					
					GroupLayout jPanelLayout = new GroupLayout( jpanel );
					jpanel.setLayout( jPanelLayout );
					jPanelLayout.setHorizontalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE ) );
					jPanelLayout.setVerticalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE ) );
					jpanel.add( js );
					jpanel.updateUI();
					cus.setColunaAtual( controle.getColunaAtual() );
					cus.setColunaSelecionada( controle.getColunaAtual() );
					cus.setLinhaSelecionada( -1 );
					cus.setLinhaAtual( -1 );
				}
			}
		}
	}
	
	/**
	 * Usado para alterar a posição de uma linha.
	 * 
	 * @param lado O lado para o qual a linha deve ser deslocada.
	 */
	private void alterarPosicaoLinha( String lado ) {
		String s = "";
		try{
			s = controle.alterarPosicaoLinha( lado );
			if( !s.equalsIgnoreCase( "ok" ) ){
				JOptionPane.showMessageDialog( this, s, "Erro", 1 );
			} else{
				for( JTableCustomizado j : matrizes ){
					if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
						j.updateUI();
						j.setColunaAtual( -1 );
						j.setColunaSelecionada( -1 );
						j.setLinhaSelecionada( controle.getLinhaAtual() );
						j.setLinhaAtual( controle.getLinhaAtual() );
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para atualizar o nome de uma coluna.
	 */
	private void atualizarColuna() {
		try{
			if( nomeTextField.getText().trim().equals( "" ) ){
				JOptionPane.showMessageDialog( this, "O nome não pode ser vazio", "Erro no nome", 0 );
				cancelarEdicao.doClick();
				
			} else{
				String s = controle.atualizarColuna( nomeTextField.getText().trim().replace( "|||", "" ) );
				
				if( !s.equalsIgnoreCase( "ok" ) ) JOptionPane.showMessageDialog( this, s, "Erro", 1 );
				else{
					for( JTableCustomizado j : matrizes ){
						if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
							j.getColumnModel().getColumn( controle.getColunaAtual() ).setHeaderValue( nomeTextField.getText().trim().replace( "|||", "" ) );
							j.getTableHeader().repaint();
						}
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para atualizar o nome de uma linha.
	 */
	private void atualizarLinha() {
		try{
			if( nomeTextField.getText().trim().equals( "" ) ){
				JOptionPane.showMessageDialog( this, "O nome não pode ser vazio", "Erro no nome", 0 );
				cancelarEdicao.doClick();
			} else{
				String s = controle.atualizarLinha( nomeTextField.getText().trim().replace( "|||", "" ) );
				if( !s.equalsIgnoreCase( "ok" ) ) JOptionPane.showMessageDialog( this, s, "Erro", 1 );
				else{
					for( JTableCustomizado j : matrizes ){
						if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
							ModeloTabela t = ( ModeloTabela ) j.getModel();
							t.fireTableDataChanged();
						}
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para criar um novo projeto.
	 */
	private void criarNovoProjeto() {
		String s = controle.criarNovoProjeto( matrizes.isEmpty() );
		if( s.equals( "ok" ) ){
			matrizes = new LinkedList< JTableCustomizado >();
			jTabbedPane1.removeAll();
			JP = new LinkedList< JPanel >();
			
		} else if( !s.equalsIgnoreCase( "Cancelar" ) ){
			JOptionPane.showMessageDialog( this, s, "Erro", 0 );
		}
	}
	
	/**
	 * Usado para destacar os elementos que tenham relação com o objeto selecionado atualmente.
	 */
	private void destacarElementos() {
		if( destacar.getName().equals( "desligado" ) ){
			destacar.setIcon( new ImageIcon( getClass().getResource( "/icons/destON.png" ) ) );
			destacar.setName( "ligado" );
			
			setNovoProjeto( false );
			setAbrirProjeto( false );
			setSalvarProjetoComo( false );
			setSalvarProjetoMenu( false );
			setSalvarProjeto( false );
			setFecharProjetoMenu( false );
			setSalvarImagemMenu( false );
			setSalvarPDFMenu( false );
			setImprimirMenu( false );
			setNovaMatriz( false );
			setNovaMatrizMenu( false );
			setExcluirMatriz( false );
			setExcluirMatrizMenu( false );
			setSincronizarMatrizMenu( false );
			setCancelarEdicao( false );
			setOkEdicao( false );
			setNomeTextField( false );
			setDeslocar1( false );
			setDeslocar2( false );
			setNovaLinhaColuna( false );
			setNovaLinhaColunaMenu( false );
			setExcluirLinhaColuna( false );
			setExcluirLinhaColunaMenu( false );
			setOrdenar( false );
			setOrdenarMenu( false );
			setImportar( false );
			setImportarDoModeloMenu( false );
			setSincronizar( false );
			setSincronizarMenu( false );
		} else{
			destacar.setIcon( new ImageIcon( getClass().getResource( "/icons/irkickoff-26.png" ) ) );
			destacar.setName( "desligado" );
			
			for( JTableCustomizado j : matrizes ){
				j.setLinhaAtual( -1 );
				j.setColunaAtual( -1 );
				j.setLinhaSelecionada( -1 );
				j.setColunaSelecionada( -1 );
			}
			
			if( !sincronizar.isEnabled() ){
				setDestacar( false );
				setDestacarMenu( false );
			}
			setNovoProjeto( true );
			setAbrirProjeto( true );
			setSalvarProjetoComo( true );
			setSalvarProjetoMenu( true );
			setSalvarProjeto( true );
			setFecharProjetoMenu( true );
			setSalvarImagemMenu( true );
			setSalvarPDFMenu( true );
			setImprimirMenu( true );
			setNovaMatriz( true );
			setNovaMatrizMenu( true );
			setExcluirMatriz( true );
			setExcluirMatrizMenu( true );
			setSincronizarMatrizMenu( true );
		}
		
		int linhaAtual = 0, colunaAtual = 0, linhaSelecionada = 0, ColunaSelecionada = 0;
		try{
			controle.destacarElementos();
			
			for( int i = 0; i < matrizes.size(); i++ ){
				JTableCustomizado jt = matrizes.remove( i );
				
				linhaAtual = jt.getLinhaAtual();
				colunaAtual = jt.getColunaAtual();
				linhaSelecionada = jt.getLinhaSelecionada();
				ColunaSelecionada = jt.getColunaSelecionada();
				
				ModeloTabela mod = ( ModeloTabela ) jt.getModel();
				JPanel jpanel = JP.get( i );
				jpanel.removeAll();
				JTableCustomizado cus = new JTableCustomizado( mod );
				adicionarListener( cus );
				matrizes.add( i, cus );
				JScrollPane js = new JScrollPane();
				js.setViewportView( cus );
				
				cus.setLinhaAtual( linhaAtual );
				cus.setColunaAtual( colunaAtual );
				cus.setLinhaSelecionada( linhaSelecionada );
				cus.setColunaSelecionada( ColunaSelecionada );
				
				GroupLayout jPanelLayout = new GroupLayout( jpanel );
				jpanel.setLayout( jPanelLayout );
				jPanelLayout.setHorizontalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE ) );
				jPanelLayout.setVerticalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE ) );
				jpanel.add( js );
				jpanel.updateUI();
				
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para excluir uma matriz do projeto.
	 */
	private void excluirMatriz() {
		try{
			int ge = JOptionPane.showConfirmDialog( this, "Tem certeza que deseja excluir a matriz " + controle.getMatrizAtual() + " do projeto?", "", 0 );
			if( ge == 0 ){
				for( int i = 0; i < matrizes.size(); i++ ){
					if( matrizes.get( i ).getNome().equals( controle.getMatrizAtual() ) ){
						String s = controle.excluirMatriz();
						
						if( !s.equalsIgnoreCase( "ok" ) ){
							JOptionPane.showMessageDialog( this, s, "Erro", 1 );
						} else{
							jTabbedPane1.remove( i );
							matrizes.remove( i );
							JP.remove( i );
							
							if( matrizes.isEmpty() ){
								controle.setMatrizAtual( null );
								controle.setColunaAtual( -1 );
								controle.setLinhaAtual( -1 );
								jTabbedPane1.removeAll();
								JP.clear();
								
								setCancelarEdicao( false );
								setOkEdicao( false );
								setNomeTextField( false );
								setDeslocar1( false );
								setDeslocar2( false );
								setNovaLinhaColuna( false );
								setNovaLinhaColunaMenu( false );
								setExcluirLinhaColuna( false );
								setExcluirLinhaColunaMenu( false );
								setOrdenar( false );
								setOrdenarMenu( false );
								setImportar( false );
								setImportarDoModeloMenu( false );
								if( isDestaque() ){
									setDestacar( false );
									setDestacarMenu( false );
								}
								setSincronizar( false );
								setSincronizarMatrizMenu( false );
								setSincronizarMenu( false );
								setResetarCamposNovosMenu( false );
								setSalvarProjeto( false );
								setSalvarProjetoMenu( false );
								setSalvarProjetoComo( false );
								setExcluirMatriz( false );
								setExcluirMatrizMenu( false );
								setImprimirMenu( false );
								setSalvarPDFMenu( false );
								setSalvarImagemMenu( false );
							}
						}
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para excluir uma coluna da matriz atual.
	 */
	private void exclulirColuna() {
		String s = "ok";
		try{
			s = controle.excluirColuna();
			
			if( !s.equalsIgnoreCase( "ok" ) ){
				JOptionPane.showMessageDialog( this, s, "Erro", 1 );
			} else{
				for( int i = 0; i < matrizes.size(); i++ ){ // Gambiarra fodastica
					if( matrizes.get( i ).getNome().equalsIgnoreCase( controle.getMatrizAtual() ) ){
						JTableCustomizado jt = matrizes.remove( i );
						ModeloTabela mod = ( ModeloTabela ) jt.getModel();
						jt.setModel( new DefaultTableModel() );
						jt = null;
						
						JPanel jpanel = JP.get( i );
						jpanel.removeAll();
						JTableCustomizado cus = new JTableCustomizado( mod );
						adicionarListener( cus );
						matrizes.add( i, cus );
						JScrollPane js = new JScrollPane();
						js.setViewportView( cus );
						
						GroupLayout jPanelLayout = new GroupLayout( jpanel );
						jpanel.setLayout( jPanelLayout );
						jPanelLayout.setHorizontalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE ) );
						jPanelLayout.setVerticalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE ) );
						jpanel.add( js );
						jpanel.updateUI();
						if( controle.getColunaAtual() <= 1 ) controle.setColunaAtual( 1 );
						else controle.setColunaAtual( controle.getColunaAtual() - 1 );
						setNomeTextField( mod.getMatriz().getTituloColuna( controle.getColunaAtual() ) );
						cus.setColunaAtual( controle.getColunaAtual() );
						cus.setColunaSelecionada( controle.getColunaAtual() );
						cus.setLinhaSelecionada( -1 );
						cus.setLinhaAtual( -1 );
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para excluir uma linha da matriz atual.
	 */
	private void exclulirLinha() {
		String s = "ok";
		try{
			s = controle.excluirLinha();
			
			if( !s.equalsIgnoreCase( "ok" ) ){ // Donde estas?
				JOptionPane.showMessageDialog( this, s, "Erro", 1 );
			} else{
				for( JTableCustomizado j : matrizes ){
					if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
						ModeloTabela t = ( ModeloTabela ) j.getModel();
						t.fireTableDataChanged();
						int linh = controle.getLinhaAtual();
						if( linh < 1 ) linh = 0;
						else linh--;
						controle.setLinhaAtual( linh );
						setNomeTextField( t.getMatriz().getTituloLinha( linh ) );
						j.setLinhaAtual( linh );
						j.setLinhaSelecionada( linh );
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para exportar uma imagem da matriz atual.
	 */
	private void exportarImagem() {
		for( JTableCustomizado j : matrizes ){
			if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
				String s = "";
				JFileChooser ch = new JFileChooser( "arquivos/" );
				
				try{
					ch.setDialogTitle( "Salvar Imagem" );
					ch.setSelectedFile( new File( "Matriz " + j.getNome() ) );
					
					ch.setFileFilter( new FileFilter() { // Filtro pra arquivos e diretorios
						
							/** {@inheritDoc} */
							public boolean accept( File f ) {
								if( f.getName().endsWith( ".png" ) || f.isDirectory() ) return true;
								return false;
							}
							
							/** {@inheritDoc} */
							public String getDescription() {
								return "Arquivos de imagem PNG";
							}
						} );
					
					int i = ch.showSaveDialog( this );
					if( i == JFileChooser.APPROVE_OPTION ){
						File fil = ch.getSelectedFile();
						s = fil.getAbsolutePath();
						if( s.endsWith( ".png" ) ) s = s.replace( ".png", "" ).replace( "PNG", "" );
						
						int sn = 0;
						if( new File( s + ".png" ).exists() ) sn = JOptionPane.showConfirmDialog( this, "Arquivo já existente, deseja sobreescrever?", "Atenção", JOptionPane.WARNING_MESSAGE );
						if( sn == JOptionPane.YES_OPTION ){
							j.exportarImagem( s );
							setTitle( "Trama  ---->  Imagem Exportada Com Sucesso" );
							try{
								Thread.sleep( 3000 );
							} catch( InterruptedException e ){
								e.printStackTrace();
							}
							setTitle( "Trama" );
						}
					}
				} catch( Exception e ){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Usado para exportar um PDF da matriz atual.
	 */
	private void exportarPDF() {
		for( JTableCustomizado j : matrizes ){
			if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
				String s = "";
				JFileChooser ch = new JFileChooser( "arquivos/" );
				
				try{
					ch.setDialogTitle( "Salvar PDF" );
					ch.setSelectedFile( new File( "Matriz " + j.getNome() ) );
					
					ch.setFileFilter( new FileFilter() { // Filtro pra arquivos e diretorios
						
							/** {@inheritDoc} */
							public boolean accept( File f ) {
								if( f.getName().endsWith( ".pdf" ) || f.isDirectory() ) return true;
								return false;
							}
							
							/** {@inheritDoc} */
							public String getDescription() {
								return "Arquivos PDF";
							}
						} );
					
					int i = ch.showSaveDialog( this );
					if( i == JFileChooser.APPROVE_OPTION ){
						File fil = ch.getSelectedFile();
						s = fil.getAbsolutePath();
						if( s.endsWith( ".pdf" ) ) s = s.replace( ".pdf", "" ).replace( "PDF", "" );
						
						int sn = 0;
						if( new File( s + ".pdf" ).exists() ) sn = JOptionPane.showConfirmDialog( this, "Arquivo já existente, deseja sobreescrever?", "Atenção", JOptionPane.WARNING_MESSAGE );
						// System.out.println( sn );
						if( sn == JOptionPane.YES_OPTION ){
							j.exportarPDF( s );
							setTitle( "Trama  ---->  PDF Exportado Com Sucesso" );
							try{
								Thread.sleep( 3000 );
							} catch( InterruptedException e ){
								e.printStackTrace();
							}
							setTitle( "Trama" );
						}
					}
				} catch( Exception e ){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Usado para fechar o projeto atualmente aberto.
	 */
	private void fecharProjeto() {
		if( controle.fecharProjeto( matrizes.isEmpty() ) ){
			matrizes = new LinkedList< JTableCustomizado >();
			jTabbedPane1.removeAll();
			JP.clear();
		}
	}
	
	/**
	 * Usado para imprimir a matriz atual.
	 */
	private void imprimir() {
		for( JTableCustomizado j : matrizes ){
			if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
				j.imprimir();
			}
		}
	}
	
	/**
	 * Usado para ordenar as colunas da matriz atual.
	 */
	private void ordenarColuna() {
		String s = "ok";
		try{
			s = controle.ordenarColuna();
			
			if( !s.equalsIgnoreCase( "ok" ) ){
				JOptionPane.showMessageDialog( this, s, "Erro", 1 );
			} else{
				for( int i = 0; i < matrizes.size(); i++ ){ // Gambiarra fodastica
					if( matrizes.get( i ).getNome().equalsIgnoreCase( controle.getMatrizAtual() ) ){
						JTableCustomizado jt = matrizes.remove( i );
						ModeloTabela mod = ( ModeloTabela ) jt.getModel();
						jt.setModel( new DefaultTableModel() );
						jt = null;
						JPanel jpanel = JP.get( i );
						jpanel.removeAll();
						JTableCustomizado cus = new JTableCustomizado( mod );
						adicionarListener( cus );
						matrizes.add( i, cus );
						JScrollPane js = new JScrollPane();
						js.setViewportView( cus );
						
						GroupLayout jPanelLayout = new GroupLayout( jpanel );
						jpanel.setLayout( jPanelLayout );
						jPanelLayout.setHorizontalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE ) );
						jPanelLayout.setVerticalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE ) );
						jpanel.add( js );
						jpanel.updateUI();
						
						setNomeTextField( mod.getMatriz().getTituloColuna( controle.getColunaAtual() ) );
						
						cus.setColunaAtual( controle.getColunaAtual() );
						cus.setColunaSelecionada( controle.getColunaAtual() );
						cus.setLinhaSelecionada( -1 );
						cus.setLinhaAtual( -1 );
					}
				}
			}
			System.gc();
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para ordenar as linhas da matriz atual.
	 */
	private void ordenarLinha() {
		String s = "ok";
		try{
			s = controle.ordenarLinha();
			
			if( !s.equalsIgnoreCase( "ok" ) ){
				JOptionPane.showMessageDialog( this, s, "Erro", 1 );
			}
			for( JTableCustomizado j : matrizes ){
				if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
					ModeloTabela t = ( ModeloTabela ) j.getModel();
					t.fireTableDataChanged();
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Usado para resetar o destaque que a matriz possa ter.
	 */
	private void resetarDestaque() {
		controle.resetarDestaque();
		for( JTableCustomizado j : matrizes ){
			if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
				ModeloTabela t = ( ModeloTabela ) j.getModel();
				t.fireTableDataChanged();
			}
		}
	}
	
	/**
	 * Usado para salvar o projeto atual.
	 */
	private void salvarProjeto() {
		String s = "";
		int sn = 0;
		if( matrizes.isEmpty() ){
			JOptionPane.showMessageDialog( this, "Para salvar o projeto é necessário que este tenha no mínimo 1 matriz.", "", 0 );
			return;
		}
		s = controle.salvarProjeto( "|vazio|" );
		if( s.equals( "|sem nome|" ) ){
			s = JOptionPane.showInputDialog( this, "Insira um nome para o projeto", "Nome do projeto", JOptionPane.QUESTION_MESSAGE );
			if( s != null ){
				
				if( new File( "arquivos/" + s.trim().replace( ".trama", "" ).replace( ".Trama", "" ).replace( "TRAMA", "" ) + ".trama" ).exists() )
					sn = JOptionPane.showConfirmDialog( this, "Arquivo já existente, deseja sobreescrever?", "Atenção", JOptionPane.WARNING_MESSAGE );
				
				if( sn == JOptionPane.YES_OPTION ){
					s = controle.salvarProjeto( s );
					if( s.equals( "|sem nome|" ) ){
						salvarProjeto();
					} else{
						setTitle( "Trama  ---->  Projeto Salvo Com Sucesso" );
						try{
							Thread.sleep( 3000 );
						} catch( InterruptedException e ){
							e.printStackTrace();
						}
						setTitle( "Trama" );
					}
				}
			} else return;
		} else{
			if( s.equalsIgnoreCase( "ok" ) ){
				setTitle( "Trama  ---->  Projeto Salvo Com Sucesso" );
				try{
					Thread.sleep( 3000 );
				} catch( InterruptedException e ){
					e.printStackTrace();
				}
				setTitle( "Trama" );
			} else JOptionPane.showMessageDialog( this, s, "", 0 );
		}
	}
	
	/**
	 * Usado para sincronizar uma coluna com um arquivo.
	 */
	private void sincronizarColuna() {
		String s = "ok";
		try{
			s = controle.sincronizarColuna();
			if( s == null || !s.equalsIgnoreCase( "ok" ) ){
				JOptionPane.showMessageDialog( this, s, "Ops houve um erro", 1 );
			} else{
				for( int i = 0; i < matrizes.size(); i++ ){
					if( matrizes.get( i ).getNome().equalsIgnoreCase( controle.getMatrizAtual() ) ){
						JTableCustomizado jt = matrizes.remove( i );
						ModeloTabela mod = ( ModeloTabela ) jt.getModel();
						jt.setModel( new DefaultTableModel() );
						jt = null;
						
						JPanel jpanel = JP.get( i );
						jpanel.removeAll();
						JTableCustomizado cus = new JTableCustomizado( mod );
						adicionarListener( cus );
						matrizes.add( i, cus );
						JScrollPane js = new JScrollPane();
						js.setViewportView( cus );
						
						GroupLayout jPanelLayout = new GroupLayout( jpanel );
						jpanel.setLayout( jPanelLayout );
						jPanelLayout.setHorizontalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE ) );
						jPanelLayout.setVerticalGroup( jPanelLayout.createParallelGroup( GroupLayout.Alignment.LEADING ).addComponent( js, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE ) );
						jpanel.add( js );
						jpanel.updateUI();
						jpanel.repaint();
						cus.setColunaAtual( controle.getColunaAtual() );
						cus.setColunaSelecionada( controle.getColunaAtual() );
						cus.setLinhaSelecionada( -1 );
						cus.setLinhaAtual( -1 );
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para sincronizar uma linha com um arquivo.
	 */
	private void sincronizarLinha() {
		String s = controle.sincronizarLinha();
		if( s == null || !s.equalsIgnoreCase( "ok" ) ){
			JOptionPane.showMessageDialog( this, s, "Ops houve um erro", 0 );
		} else{
			for( JTableCustomizado j : matrizes ){
				if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
					ModeloTabela t = ( ModeloTabela ) j.getModel();
					t.fireTableDataChanged();
				}
			}
		}
	}
	
	/**
	 * Usado para sincronizar as linhas e colunas da matriz atual.
	 */
	private void sincronizarMatriz() {
		sincronizarLinha();
		sincronizarColuna();
	}
	
	/**
	 * Usado para adicionar listeners de eventos à um JTable.
	 * 
	 * @param jT o JTable à ter listeners.
	 */
	private void adicionarListener( final JTableCustomizado jT ) {
		final JPopupMenu menu = new JPopupMenu();
		final JPopupMenu menu2 = new JPopupMenu();
		
		{
			final JMenuItem item1 = new JMenuItem( "Nova Linha", new ImageIcon( getClass().getResource( "/icons/7days-26.png" ) ) );
			final JMenuItem item2 = new JMenuItem( "Ordenar Linha", new ImageIcon( getClass().getResource( "/icons/db-26.png" ) ) );
			final JMenuItem item3 = new JMenuItem( "Importar do Modelo", new ImageIcon( getClass().getResource( "/icons/inbox-26 copy.png" ) ) );
			final JMenuItem item4 = new JMenuItem( "Sincronizar do Modelo", new ImageIcon( getClass().getResource( "/icons/reload_all_tabs-26.png" ) ) );
			item1.addActionListener( new ActionListener() {
				
				/** {@inheritDoc} */
				public void actionPerformed( ActionEvent e ) {
					menu.setVisible( false );
					adicionarLinha();
				}
			} );
			item2.addActionListener( new ActionListener() {
				
				/** {@inheritDoc} */
				public void actionPerformed( ActionEvent e ) {
					menu.setVisible( false );
					ordenarLinha();
				}
			} );
			item3.addActionListener( new ActionListener() {
				
				/** {@inheritDoc} */
				public void actionPerformed( ActionEvent e ) {
					menu.setVisible( false );
					adicionarLinhasModelo();
				}
			} );
			item4.addActionListener( new ActionListener() {
				
				/** {@inheritDoc} */
				public void actionPerformed( ActionEvent e ) {
					menu.setVisible( false );
					sincronizarLinha();
				}
			} );
			
			item1.addMouseListener( new MouseAdapter() {
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					item1.setBackground( Color.WHITE );
				}
				
				/** {@inheritDoc} */
				public void mouseEntered( MouseEvent e ) {
					item1.setBackground( new Color( 240, 240, 250 ) );
				}
			} );
			item2.addMouseListener( new MouseAdapter() {
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					item2.setBackground( Color.WHITE );
				}
				
				/** {@inheritDoc} */
				public void mouseEntered( MouseEvent e ) {
					item2.setBackground( new Color( 240, 240, 250 ) );
				}
			} );
			item3.addMouseListener( new MouseAdapter() {
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					item3.setBackground( Color.WHITE );
				}
				
				/** {@inheritDoc} */
				public void mouseEntered( MouseEvent e ) {
					item3.setBackground( new Color( 240, 240, 250 ) );
				}
			} );
			item4.addMouseListener( new MouseAdapter() {
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					item4.setBackground( Color.WHITE );
				}
				
				/** {@inheritDoc} */
				public void mouseEntered( MouseEvent e ) {
					item4.setBackground( new Color( 240, 240, 250 ) );
				}
			} );
			menu.setBorder( BorderFactory.createTitledBorder( "" ) );
			menu.add( item1 );
			menu.add( item2 );
			menu.add( item3 );
			menu.add( item4 );
			
		}
		
		{
			final JMenuItem item1 = new JMenuItem( "Nova Coluna", new ImageIcon( getClass().getResource( "/icons/coluna 24.png" ) ) );
			final JMenuItem item2 = new JMenuItem( "Ordenar Coluna", new ImageIcon( getClass().getResource( "/icons/db-26.png" ) ) );
			final JMenuItem item3 = new JMenuItem( "Importar do Modelo", new ImageIcon( getClass().getResource( "/icons/inbox-26 copy.png" ) ) );
			final JMenuItem item4 = new JMenuItem( "Sincronizar do Modelo", new ImageIcon( getClass().getResource( "/icons/reload_all_tabs-26.png" ) ) );
			item1.addActionListener( new ActionListener() {
				
				/** * {@inheritDoc} */
				public void actionPerformed( ActionEvent e ) {
					menu2.setVisible( false );
					adicionarColuna();
				}
			} );
			item2.addActionListener( new ActionListener() {
				
				/** {@inheritDoc} */
				public void actionPerformed( ActionEvent e ) {
					menu2.setVisible( false );
					ordenarColuna();
				}
			} );
			item3.addActionListener( new ActionListener() {
				
				/** {@inheritDoc} */
				@SuppressWarnings( "synthetic-access" )
				public void actionPerformed( @SuppressWarnings( "unused" ) ActionEvent e ) {
					menu2.setVisible( false );
					adicionarColunasModelo();
				}
			} );
			item4.addActionListener( new ActionListener() {
				
				/** {@inheritDoc} */
				@SuppressWarnings( "synthetic-access" )
				public void actionPerformed( ActionEvent e ) {
					menu2.setVisible( false );
					sincronizarColuna();
				}
			} );
			
			item1.addMouseListener( new MouseAdapter() {
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					item1.setBackground( Color.WHITE );
				}
				
				/** {@inheritDoc} */
				public void mouseEntered( MouseEvent e ) {
					item1.setBackground( new Color( 240, 240, 250 ) );
				}
			} );
			item2.addMouseListener( new MouseAdapter() {
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					item2.setBackground( Color.WHITE );
				}
				
				/** {@inheritDoc} */
				public void mouseEntered( MouseEvent e ) {
					item2.setBackground( new Color( 240, 240, 250 ) );
				}
			} );
			item3.addMouseListener( new MouseAdapter() {
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					item3.setBackground( Color.WHITE );
				}
				
				/** {@inheritDoc} */
				public void mouseEntered( MouseEvent e ) {
					item3.setBackground( new Color( 240, 240, 250 ) );
				}
			} );
			item4.addMouseListener( new MouseAdapter() {
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					item4.setBackground( Color.WHITE );
				}
				
				/** {@inheritDoc} */
				public void mouseEntered( MouseEvent e ) {
					item4.setBackground( new Color( 240, 240, 250 ) );
				}
			} );
			menu2.setBorder( BorderFactory.createTitledBorder( "" ) );
			menu2.add( item1 );
			menu2.add( item2 );
			menu2.add( item3 );
			menu2.add( item4 );
		}
		
		jT.addMouseListener( new MouseAdapter() { // Adiciona listener as tabelas
			
				/** {@inheritDoc} */
				@SuppressWarnings( "synthetic-access" )
				public void mouseClicked( MouseEvent e ) {
					try{
						int linha = jT.rowAtPoint( e.getPoint() );
						int coluna = jT.columnAtPoint( e.getPoint() );
						if( coluna == -1 ) coluna = 0;
						
						menu.setVisible( false );
						menu2.setVisible( false );
						
						for( JTableCustomizado jTableCustomizado : matrizes ){
							if( jTableCustomizado == e.getSource() ) controle.setMatrizAtual( jTableCustomizado.getNome() );
						}
						controle.setLinhaAtual( linha );
						controle.setColunaAtual( coluna );
						
						jT.setLinhaSelecionada( linha );
						jT.setLinhaAtual( linha );
						jT.setColunaSelecionada( -1 );
						// System.out.println( "Linha=" + ( linha ) + "   coluna= " + coluna + "     Matriz atual:::" + jT.getNome() );
						
						if( coluna <= 0 ){// Toda vez que se clicar em um nome de------------------------------------------------------------ linha -------
							if( destacar.getName().equals( "desligado" ) ){
								for( JTableCustomizado jTableCustomizado : matrizes ){
									if( jTableCustomizado.getNome().equals( controle.getMatrizAtual() ) ){
										setNomeTextField( ( ( ModeloTabela ) jTableCustomizado.getModel() ).getMatriz().getTituloLinha( linha ) );
									}
								}
								if( e.getButton() == 3 ){
									menu.setLocation( e.getLocationOnScreen() );
									menu.setVisible( true );
								} else menu.setVisible( false );
								deslocar1.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/1downarrow-24.png" ) ) );
								deslocar1.setToolTipText( "Deslocar Linha Para Baixo" );
								deslocar2.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/1downarrow1-24.png" ) ) );
								deslocar1.setToolTipText( "Deslocar Linha Para Cima" );
								novaLinhaColuna.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/7days-26.png" ) ) );
								novaLinhaColuna.setToolTipText( "Nova Linha" );
								novaLinhaColunaMenu.setText( "Nova Linha..." );
								nomeTextField.setToolTipText( "Nome da Linha Selecionada" );
								excluirLinhaColuna.setToolTipText( "Excluir Linha" );
								excluirLinhaColunaMenu.setText( "Excluir Linha" );
								setCancelarEdicao( true );
								setOkEdicao( true );
								setNomeTextField( true );
								setDeslocar1( true );
								setDeslocar2( true );
								setNovaLinhaColuna( true );
								setNovaLinhaColunaMenu( true );
								setExcluirLinhaColuna( true );
								setExcluirLinhaColunaMenu( true );
								setOrdenar( true );
								setOrdenarMenu( true );
								setImportar( true );
								setImportarDoModeloMenu( true );
								setDestacar( true );
								setDestacarMenu( true );
								setSincronizar( true );
								setSincronizarMenu( true );
								setResetarCamposNovosMenu( true );
								jT.repaint();
								jT.getTableHeader().repaint();
							} else{
								setCancelarEdicao( false );
								setOkEdicao( false );
								setNomeTextField( false );
								setDeslocar1( false );
								setDeslocar2( false );
								setNovaLinhaColuna( false );
								setNovaLinhaColunaMenu( false );
								setExcluirLinhaColuna( false );
								setExcluirLinhaColunaMenu( false );
								setOrdenar( false );
								setOrdenarMenu( false );
								setImportar( false );
								setImportarDoModeloMenu( false );
								setSincronizar( false );
								setSincronizarMenu( false );
								setSincronizarMatrizMenu( false );
								setResetarCamposNovosMenu( false );
							}
						} else{ // Aqui é quando se clica nas ---------------------------------------------------------células ---------------------
						
							if( destacar.getName().equals( "desligado" ) && controle.getLinhaAtual() >= 0 && controle.getColunaAtual() > 0 ){
								controle.setDado();
								
								( ( ModeloTabela ) jT.getModel() ).fireTableDataChanged();
							}
							
							controle.setLinhaAtual( -1 );
							jT.setLinhaSelecionada( -1 );
							jT.setColunaSelecionada( -1 );
							
							novaLinhaColunaMenu.setText( "Nova Linha/Coluna" );
							excluirLinhaColunaMenu.setText( "Excluir Linha/Coluna" );
							
							setCancelarEdicao( false );
							setOkEdicao( false );
							setNomeTextField( false );
							setDeslocar1( false );
							setDeslocar2( false );
							setNovaLinhaColuna( false );
							setNovaLinhaColunaMenu( false );
							setExcluirLinhaColuna( false );
							setExcluirLinhaColunaMenu( false );
							setOrdenar( false );
							setOrdenarMenu( false );
							setImportar( false );
							setImportarDoModeloMenu( false );
							if( isDestaque() ){
								setDestacar( false );
								setDestacarMenu( false );
							}
							setSincronizar( false );
							setSincronizarMenu( false );
							jT.repaint();
							jT.getTableHeader().repaint();
						}// Modafoca
					} catch( Exception e1 ){
						e1.printStackTrace();
					}
				}
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					jT.setLinhaAtual( -2 );
					jT.setColunaAtual( -2 );
					menu2.setVisible( false );
					jT.repaint();
					jT.getTableHeader().repaint();
				}
				
				public void mouseEntered( MouseEvent e ) {
					menu.setVisible( false );
					menu2.setVisible( false );
				}
			} );
		
		header = jT.getTableHeader();
		header.addMouseListener( new MouseAdapter() { // adiciona listeners aos cabecalhos ----Serve pros nomes de colunas ------------header--
			
				/** {@inheritDoc} */
				@SuppressWarnings( "synthetic-access" )
				@Override
				public void mouseClicked( MouseEvent e ) {
					try{
						int coluna = jT.columnAtPoint( e.getPoint() );
						controle.setLinhaAtual( -1 );
						if( coluna < 1 ) coluna = 1;
						controle.setColunaAtual( coluna );
						
						jT.setLinhaSelecionada( -1 );
						jT.setColunaSelecionada( coluna );
						
						for( JTableCustomizado jTableCustomizado : matrizes ){
							if( jTableCustomizado.getTableHeader() == e.getSource() ) controle.setMatrizAtual( jTableCustomizado.getNome() );
						}
						// System.out.println( "Linha=" + ( controle.getLinhaAtual() ) + "   coluna= " + coluna + "   Matriz atual:::: " + jT.getNome() );
						if( coluna > 0 ){
							
							if( destacar.getName().equals( "desligado" ) ){
								if( e.getButton() == 3 ){
									menu2.setLocation( e.getLocationOnScreen() );
									menu2.setVisible( true );
								} else menu2.setVisible( false );
								menu.setVisible( false );
								
								for( JTableCustomizado jTableCustomizado : matrizes ){
									if( jTableCustomizado.getNome().equalsIgnoreCase( controle.getMatrizAtual() ) ){
										setNomeTextField( ( ( ModeloTabela ) jTableCustomizado.getModel() ).getMatriz().getTituloColuna( coluna ) );
									}
								}
								deslocar1.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/1leftarrow-24.png" ) ) );
								deslocar1.setToolTipText( "Deslocar Coluna Para Esquerda" );
								deslocar2.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/1rightarrow-24.png" ) ) );
								deslocar2.setToolTipText( "Deslocar Coluna Para Direita" );
								novaLinhaColuna.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/coluna 24.png" ) ) );
								novaLinhaColuna.setToolTipText( "Nova Coluna" );
								novaLinhaColunaMenu.setText( "Nova Coluna..." );
								nomeTextField.setToolTipText( "Nome da Coluna Selecionada" );
								excluirLinhaColuna.setToolTipText( "Excluir Coluna" );
								excluirLinhaColunaMenu.setText( "Excluir Coluna" );
								setCancelarEdicao( true );
								setOkEdicao( true );
								setNomeTextField( true );
								setDeslocar1( true );
								setDeslocar2( true );
								setNovaLinhaColuna( true );
								setNovaLinhaColunaMenu( true );
								setExcluirLinhaColuna( true );
								setExcluirLinhaColunaMenu( true );
								setOrdenar( true );
								setOrdenarMenu( true );
								setImportar( true );
								setImportarDoModeloMenu( true );
								setDestacar( true );
								setDestacarMenu( true );
								setSincronizar( true );
								setSincronizarMenu( true );
								setResetarCamposNovosMenu( true );
							} else{
								setCancelarEdicao( false );
								setOkEdicao( false );
								setNomeTextField( false );
								setDeslocar1( false );
								setDeslocar2( false );
								setNovaLinhaColuna( false );
								setNovaLinhaColunaMenu( false );
								setExcluirLinhaColuna( false );
								setExcluirLinhaColunaMenu( false );
								setOrdenar( false );
								setOrdenarMenu( false );
								setImportar( false );
								setImportarDoModeloMenu( false );
								setSincronizar( false );
								setSincronizarMenu( false );
								setSincronizarMatrizMenu( false );
								setResetarCamposNovosMenu( false );
							}
						}
						jT.getTableHeader().repaint();
						jT.repaint();
					} catch( Exception e1 ){
						e1.printStackTrace();
					}
				}
				
				/** {@inheritDoc} */
				public void mouseExited( MouseEvent e ) {
					jT.setLinhaAtual( -2 );
					jT.setColunaAtual( -2 );
					menu.setVisible( false );
				}
				
				public void mouseEntered( MouseEvent e ) {
					menu.setVisible( false );
					menu2.setVisible( false );
				}
			} );
		
	}
	
	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        novoProjeto = new javax.swing.JButton();
        abrirProjeto = new javax.swing.JButton();
        salvarProjeto = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        novaMatriz = new javax.swing.JButton();
        excluirMatriz = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        cancelarEdicao = new javax.swing.JButton();
        okEdicao = new javax.swing.JButton();
        nomeTextField = new javax.swing.JTextField();
        deslocar1 = new javax.swing.JButton();
        deslocar2 = new javax.swing.JButton();
        novaLinhaColuna = new javax.swing.JButton();
        excluirLinhaColuna = new javax.swing.JButton();
        jToolBar4 = new javax.swing.JToolBar();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        ordenar = new javax.swing.JButton();
        importar = new javax.swing.JButton();
        sincronizar = new javax.swing.JButton();
        jToolBar5 = new javax.swing.JToolBar();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        destacar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        novoProjetoMenu = new javax.swing.JMenuItem();
        abrirProjetoMenu = new javax.swing.JMenuItem();
        salvarProjetoMenu = new javax.swing.JMenuItem();
        salvarComoMenu = new javax.swing.JMenuItem();
        fecharProjetoMenu = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        salvarPDFMenu = new javax.swing.JMenuItem();
        salvarImagemMenu = new javax.swing.JMenuItem();
        imprimirMenu = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        sairMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        novaMatrizMenu = new javax.swing.JMenuItem();
        sincronizarMatrizMenu = new javax.swing.JMenuItem();
        excluirMatrizMenu = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        novaLinhaColunaMenu = new javax.swing.JMenuItem();
        excluirLinhaColunaMenu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        sincronizarMenu = new javax.swing.JMenuItem();
        ordenarMenu = new javax.swing.JMenuItem();
        importarDoModeloMenu = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        destacarMenu = new javax.swing.JMenuItem();
        resetarCamposNovosMenu = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        ajudaMenu = new javax.swing.JMenuItem();
        sobreMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Trama");
        setIconImage( Toolkit.getDefaultToolkit().getImage( java.net.URLClassLoader.getSystemResource( "icons/Teia 2.png" ) ));
        setMinimumSize(new java.awt.Dimension(800, 550));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(777, 36));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setAlignmentX(0.0F);
        jToolBar1.setMaximumSize(new java.awt.Dimension(150, 35));
        jToolBar1.setMinimumSize(new java.awt.Dimension(158, 30));
        jToolBar1.setPreferredSize(new java.awt.Dimension(110, 45));

        novoProjeto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new window-26.png"))); // NOI18N
        novoProjeto.setText("Novo");
        novoProjeto.setToolTipText("Novo Projeto");
        novoProjeto.setFocusable(false);
        novoProjeto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        novoProjeto.setIconTextGap(1);
        novoProjeto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(novoProjeto);
        novoProjeto.addActionListener(this);

        abrirProjeto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fileopen-26.png"))); // NOI18N
        abrirProjeto.setText("Abrir");
        abrirProjeto.setToolTipText("Abrir Projeto");
        abrirProjeto.setFocusable(false);
        abrirProjeto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        abrirProjeto.setIconTextGap(1);
        abrirProjeto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(abrirProjeto);
        abrirProjeto.addActionListener(this);

        salvarProjeto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/filesave-26.png"))); // NOI18N
        salvarProjeto.setText("Salvar");
        salvarProjeto.setToolTipText("Salvar Projeto");
        salvarProjeto.setEnabled(false);
        salvarProjeto.setFocusable(false);
        salvarProjeto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        salvarProjeto.setIconTextGap(1);
        salvarProjeto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(salvarProjeto);
        salvarProjeto.addActionListener(this);

        jPanel1.add(jToolBar1);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setAlignmentY(0.5F);
        jToolBar3.setMaximumSize(new java.awt.Dimension(50, 50));
        jToolBar3.setMinimumSize(new java.awt.Dimension(50, 50));
        jToolBar3.setPreferredSize(new java.awt.Dimension(85, 45));
        jToolBar3.add(jSeparator1);

        novaMatriz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1day-26 copy.png"))); // NOI18N
        novaMatriz.setText("Novo");
        novaMatriz.setToolTipText("Nova Matriz");
        novaMatriz.setEnabled(false);
        novaMatriz.setFocusable(false);
        novaMatriz.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        novaMatriz.setIconTextGap(1);
        novaMatriz.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(novaMatriz);
        novaMatriz.addActionListener(this);

        excluirMatriz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/agt_action_fail-26.png"))); // NOI18N
        excluirMatriz.setText("Excluir");
        excluirMatriz.setToolTipText("Excluir Matriz Atual");
        excluirMatriz.setEnabled(false);
        excluirMatriz.setFocusable(false);
        excluirMatriz.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        excluirMatriz.setIconTextGap(1);
        excluirMatriz.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(excluirMatriz);
        excluirMatriz.addActionListener(this);

        jPanel1.add(jToolBar3);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setMaximumSize(new java.awt.Dimension(400, 30));
        jToolBar2.setMinimumSize(new java.awt.Dimension(300, 30));
        jToolBar2.setPreferredSize(new java.awt.Dimension(340, 45));
        jToolBar2.add(jSeparator4);

        cancelarEdicao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.gif"))); // NOI18N
        cancelarEdicao.setToolTipText("Cancelar Edição");
        cancelarEdicao.setEnabled(false);
        cancelarEdicao.setFocusable(false);
        cancelarEdicao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelarEdicao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(cancelarEdicao);
        cancelarEdicao.addActionListener(this);

        okEdicao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/agt_action_success-26.png"))); // NOI18N
        okEdicao.setToolTipText("Confirmar Edição");
        okEdicao.setEnabled(false);
        okEdicao.setFocusable(false);
        okEdicao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        okEdicao.setIconTextGap(1);
        okEdicao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(okEdicao);
        okEdicao.addActionListener(this);

        nomeTextField.setColumns(15);
        nomeTextField.setFont(new java.awt.Font("Arial", 0, 12));
        nomeTextField.setToolTipText("Nome da Linha ou Coluna Selecionada");
        nomeTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        nomeTextField.setEnabled(false);
        nomeTextField.setMaximumSize(new java.awt.Dimension(150, 30));
        nomeTextField.setMinimumSize(new java.awt.Dimension(10, 10));
        nomeTextField.setPreferredSize(new java.awt.Dimension(50, 10));
        jToolBar2.add(nomeTextField);
        nomeTextField.addActionListener(this);

        deslocar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1downarrow-24.png"))); // NOI18N
        deslocar1.setToolTipText("Mover Para Esquerda/Cima");
        deslocar1.setAlignmentX(0.5F);
        deslocar1.setEnabled(false);
        deslocar1.setFocusable(false);
        deslocar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deslocar1.setIconTextGap(1);
        deslocar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(deslocar1);
        deslocar1.addActionListener(this);

        deslocar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1downarrow1-24.png"))); // NOI18N
        deslocar2.setToolTipText("Mover Para Direita/Baxo");
        deslocar2.setEnabled(false);
        deslocar2.setFocusable(false);
        deslocar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deslocar2.setIconTextGap(1);
        deslocar2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(deslocar2);
        deslocar2.addActionListener(this);

        novaLinhaColuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/7days-26.png"))); // NOI18N
        novaLinhaColuna.setText("Novo");
        novaLinhaColuna.setToolTipText("Nova linha");
        novaLinhaColuna.setEnabled(false);
        novaLinhaColuna.setFocusable(false);
        novaLinhaColuna.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        novaLinhaColuna.setIconTextGap(1);
        novaLinhaColuna.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(novaLinhaColuna);
        novaLinhaColuna.addActionListener(this);

        excluirLinhaColuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/button_cancel-26.png"))); // NOI18N
        excluirLinhaColuna.setText("Excluir");
        excluirLinhaColuna.setToolTipText("Excluir Linha/Coluna");
        excluirLinhaColuna.setEnabled(false);
        excluirLinhaColuna.setFocusable(false);
        excluirLinhaColuna.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        excluirLinhaColuna.setIconTextGap(1);
        excluirLinhaColuna.setMaximumSize(new java.awt.Dimension(100, 100));
        excluirLinhaColuna.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(excluirLinhaColuna);
        excluirLinhaColuna.addActionListener(this);

        jPanel1.add(jToolBar2);

        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);
        jToolBar4.setMaximumSize(new java.awt.Dimension(400, 30));
        jToolBar4.setMinimumSize(new java.awt.Dimension(200, 30));
        jToolBar4.setPreferredSize(new java.awt.Dimension(170, 45));
        jToolBar4.add(jSeparator5);

        ordenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/db-26.png"))); // NOI18N
        ordenar.setText("Ordenar");
        ordenar.setToolTipText("Ordenar");
        ordenar.setEnabled(false);
        ordenar.setFocusable(false);
        ordenar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ordenar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(ordenar);
        ordenar.addActionListener(this);

        importar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/inbox-26 copy.png"))); // NOI18N
        importar.setText("Importar");
        importar.setToolTipText("Importar do Modelo");
        importar.setEnabled(false);
        importar.setFocusable(false);
        importar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        importar.setIconTextGap(1);
        importar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(importar);
        importar.addActionListener(this);

        sincronizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload_all_tabs-26.png"))); // NOI18N
        sincronizar.setText("Sincronizar");
        sincronizar.setToolTipText("Sincronizar do Modelo");
        sincronizar.setEnabled(false);
        sincronizar.setFocusable(false);
        sincronizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sincronizar.setIconTextGap(1);
        sincronizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(sincronizar);
        sincronizar.addActionListener(this);

        jPanel1.add(jToolBar4);

        jToolBar5.setFloatable(false);
        jToolBar5.setRollover(true);
        jToolBar5.add(jSeparator6);

        destacar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/irkickoff-26.png"))); // NOI18N
        destacar.setText("Destacar");
        destacar.setToolTipText("Destacar Elementos Relacionados");
        destacar.setEnabled(false);
        destacar.setFocusable(false);
        destacar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        destacar.setIconTextGap(1);
        destacar.setName("desligado"); // NOI18N
        destacar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar5.add(destacar);
        destacar.addActionListener(this);

        jPanel1.add(jToolBar5);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );

        jTabbedPane1.addChangeListener( new javax.swing.event.ChangeListener() {
            public void stateChanged( javax.swing.event.ChangeEvent evt ) {
                novaLinhaColunaMenu.setText( "Nova Linha/Coluna" );
                excluirLinhaColunaMenu.setText( "Excluir Linha/Coluna" );
                try{
                    controle.setMatrizAtual( jTabbedPane1.getTitleAt( jTabbedPane1.getSelectedIndex() ) );
                } catch( Exception e ){

                }
                for( JTableCustomizado jtc : matrizes ){
                    jtc.setLinhaAtual( -1 );
                    jtc.setColunaAtual( -1 );
                    jtc.setLinhaSelecionada( -1 );
                    jtc.setColunaSelecionada( -1 );
                }

                setCancelarEdicao( false );
                setOkEdicao( false );
                setNomeTextField( false );
                setDeslocar1( false );
                setDeslocar2( false );
                setNovaLinhaColuna( false );
                setNovaLinhaColunaMenu( false );
                setExcluirLinhaColuna( false );
                setExcluirLinhaColunaMenu( false );
                setOrdenar( false );
                setOrdenarMenu( false );
                setImportar( false );
                setImportarDoModeloMenu( false );
                setSincronizar( false );
                if( destacar.getName().equals( "desligado" ) )     {
                    setSincronizarMatrizMenu( true );
                    setDestacar( false );
                    setDestacarMenu( false );
                }
                setSincronizarMenu( false );
                setResetarCamposNovosMenu( false);
            }
        } );

        jMenu1.setMnemonic('A');
        jMenu1.setText("Arquivo");

        novoProjetoMenu.setText("Novo Projeto...");
        jMenu1.add(novoProjetoMenu);
        novoProjetoMenu.addActionListener(this);

        abrirProjetoMenu.setText("Abrir Projeto...");
        jMenu1.add(abrirProjetoMenu);
        abrirProjetoMenu.addActionListener(this);

        salvarProjetoMenu.setText("Salvar Projeto");
        salvarProjetoMenu.setEnabled(false);
        jMenu1.add(salvarProjetoMenu);
        salvarProjetoMenu.addActionListener(this);

        salvarComoMenu.setText("Salvar Projeto Como...");
        salvarComoMenu.setEnabled(false);
        jMenu1.add(salvarComoMenu);
        salvarComoMenu.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                int sn = 0;
                try {
                    String s = JOptionPane.showInputDialog( null, "Insira um nome para o projeto", "Nome do projeto", 3 );
                    if( s != null ){
                        if( new File( "arquivos/" + s.trim().replace( ".trama", "" ).replace( ".Trama", "" ).replace( "TRAMA", "" ) + ".trama" ).exists() )
                        sn = JOptionPane.showConfirmDialog( null, "Arquivo já existente, deseja sobreescrever?", "Atenção", JOptionPane.WARNING_MESSAGE );

                        if( sn == JOptionPane.YES_OPTION ) {
                            s = controle.salvarProjeto( s );
                            if( s.equals( "|sem nome|" ) ){
                                salvarProjeto();
                            } else{
                                setTitle( "Trama  ---->  Projeto Salvo Com Sucesso" );
                                try{
                                    Thread.sleep( 3000 );
                                } catch( InterruptedException e5 ){
                                    e5.printStackTrace();
                                }
                                setTitle( "Trama" );
                            }
                        }
                    } else return;
                } catch ( Exception ex ) {
                    JOptionPane.showMessageDialog( null, "Erro ao salvar o arquivo", "", 0 );
                }
            }
        });

        fecharProjetoMenu.setText("Fechar Projeto");
        fecharProjetoMenu.setEnabled(false);
        jMenu1.add(fecharProjetoMenu);
        fecharProjetoMenu.addActionListener(this);
        jMenu1.add(jSeparator3);

        salvarPDFMenu.setText("Salvar Como PDF...");
        salvarPDFMenu.setEnabled(false);
        jMenu1.add(salvarPDFMenu);
        salvarPDFMenu.addActionListener(this);

        salvarImagemMenu.setText("Salvar Como Imagem...");
        salvarImagemMenu.setEnabled(false);
        jMenu1.add(salvarImagemMenu);
        salvarImagemMenu.addActionListener(this);

        imprimirMenu.setText("Imprimir...");
        imprimirMenu.setEnabled(false);
        jMenu1.add(imprimirMenu);
        imprimirMenu.addActionListener(this);
        jMenu1.add(jSeparator7);

        sairMenu.setText("Sair");
        jMenu1.add(sairMenu);
        sairMenu.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if( salvarProjeto.isEnabled() ){
                    int s1 = JOptionPane.showConfirmDialog( null, "Deseja salvar o projeto atual?" );
                    if( s1 == JOptionPane.YES_OPTION ){
                        String s = controle.salvarProjeto( "|vazio|" );
                        if( s.equals( "|sem nome|" ) ){
                            s = JOptionPane.showInputDialog( null, "Insira um nome para o projeto", "Nome do projeto", 3 );
                            if( s != null ){
                                s = controle.salvarProjeto( s );
                                if( s.equals( "|sem nome|" ) ){
                                    salvarProjeto();
                                } else{
                                    setTitle( "Trama  ---->  Projeto Salvo Com Sucesso" );
                                    try{
                                        Thread.sleep( 3000 );
                                    } catch( Exception e4 ){
                                        e4.printStackTrace();
                                    }
                                    setTitle( "Trama" );
                                    System.exit( 0 );
                                }
                            } else return;
                        } else{
                            if( s.equalsIgnoreCase( "ok" ) ){
                                setTitle( "Trama  ---->  Projeto Salvo Com Sucesso" );
                                try{
                                    Thread.sleep( 3000 );
                                } catch( Exception e3 ){
                                    e3.printStackTrace();
                                }
                                setTitle( "Trama" );
                                System.exit( 0 );
                            } else JOptionPane.showMessageDialog( null, s, "", 0 );
                        }
                    } else if( s1 == JOptionPane.NO_OPTION ) System.exit( 0 );
                } else System.exit( 0 );
            }
        } );

        jMenuBar1.add(jMenu1);

        jMenu2.setMnemonic('M');
        jMenu2.setText("Matriz");

        novaMatrizMenu.setText("Nova Matriz...");
        novaMatrizMenu.setEnabled(false);
        jMenu2.add(novaMatrizMenu);
        novaMatrizMenu.addActionListener(this);

        sincronizarMatrizMenu.setText("Sincronizar Matriz...");
        sincronizarMatrizMenu.setEnabled(false);
        jMenu2.add(sincronizarMatrizMenu);
        sincronizarMatrizMenu.addActionListener(this);

        excluirMatrizMenu.setText("Excluir Matriz");
        excluirMatrizMenu.setEnabled(false);
        jMenu2.add(excluirMatrizMenu);
        excluirMatrizMenu.addActionListener(this);

        jMenuBar1.add(jMenu2);

        jMenu5.setMnemonic('L');
        jMenu5.setText("Linha/Coluna");

        novaLinhaColunaMenu.setText("Nova Linha/Coluna");
        novaLinhaColunaMenu.setEnabled(false);
        jMenu5.add(novaLinhaColunaMenu);
        novaLinhaColunaMenu.addActionListener(this);

        excluirLinhaColunaMenu.setText("Excluir Linha/Coluna");
        excluirLinhaColunaMenu.setEnabled(false);
        jMenu5.add(excluirLinhaColunaMenu);
        excluirLinhaColunaMenu.addActionListener(this);
        jMenu5.add(jSeparator2);

        sincronizarMenu.setText("Sincronizar do Modelo...");
        sincronizarMenu.setEnabled(false);
        jMenu5.add(sincronizarMenu);
        sincronizarMenu.addActionListener( this);

        ordenarMenu.setText("Ordenar");
        ordenarMenu.setEnabled(false);
        jMenu5.add(ordenarMenu);
        ordenarMenu.addActionListener(this);

        importarDoModeloMenu.setText("Importar do Modelo...");
        importarDoModeloMenu.setEnabled(false);
        jMenu5.add(importarDoModeloMenu);
        importarDoModeloMenu.addActionListener(this);

        jMenuBar1.add(jMenu5);

        jMenu10.setMnemonic('F');
        jMenu10.setText("Ferramentas");

        destacarMenu.setText("Destacar Elementos Relacionados");
        destacarMenu.setEnabled(false);
        jMenu10.add(destacarMenu);
        destacarMenu.addActionListener(this);

        resetarCamposNovosMenu.setText("Resetar Campos Novos");
        resetarCamposNovosMenu.setEnabled(false);
        jMenu10.add(resetarCamposNovosMenu);
        resetarCamposNovosMenu.addActionListener(this);

        jMenuBar1.add(jMenu10);

        jMenu3.setMnemonic('j');
        jMenu3.setText("Ajuda");

        ajudaMenu.setText("Ajuda");
        jMenu3.add(ajudaMenu);
        ajudaMenu.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                try {
                    Desktop.getDesktop().open( new File( "doc/Manual Trama.pdf" ) );
                } catch ( Exception ex ) {
                    JOptionPane.showMessageDialog( null, "Erro na abertura do arquivo, arquivo de ajuda não encontrado", "", 0 );
                }
            }
        });

        sobreMenu.setText("Sobre");
        jMenu3.add(sobreMenu);
        sobreMenu.addActionListener( new ActionListener() {
            public void actionPerformed( final ActionEvent e ) {
                JDialog jd = new JDialog(  ){
                    {
                        initComponents();
                        setVisible( true);
                    }

                    /** This method is called from within the constructor to
                    * initialize the form.
                    * WARNING: Do NOT modify this code. The content of this method is
                    * always regenerated by the Form Editor.
                    */
                    @SuppressWarnings("unchecked")

                    private void initComponents() {

                        jLabel1 = new javax.swing.JLabel();
                        jLabel2 = new javax.swing.JLabel();

                        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                        setLocation( jTabbedPane1.getLocationOnScreen().x + 150, jTabbedPane1.getLocationOnScreen().y + 50 );
                        setModal(true);
                        setResizable(false);

                        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Teia.png"))); // NOI18N

                        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        jLabel2.setText("<html><head><title></title><style type=\"text/css\">\n<!-- .style1 { font-size: 24px; font-weight: bold; color: #FF8000; }\n.style2 { font-size: 7px } .style3 {font-size: 8px} -->\n</style> </head> <body> \n<p><span class=\"style1\">Trama </span>v1.0</p>\n<p>2008 Fabio Marmitt </p>\n<p>&nbsp;</p><p>&nbsp;</p> <p>&nbsp;</p>\n<p>Obrigado por utilizar este software.</p>\n<p>Espero que goste e seja útil para você.</p>\n<p>&nbsp;</p>\n<span class=\"style3\">Software Livre sob licença GPLv3.<br>\nEste programa não provê nenhuma garantia. Use por sua conta e risco.</span>\n<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>\n<p class=\"style2\">Às vezes a única diferença entre o mocinho e o bandido é que o mocinho foi o cara que teve sorte.</p>\n</body></html>");
                        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

                        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                        getContentPane().setLayout(layout);
                        layout.setHorizontalGroup(
                            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                                .addContainerGap())
                        );
                        layout.setVerticalGroup(
                            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
                                .addContainerGap())
                        );

                        pack();
                    }// </editor-fold>

                    // Variables declaration - do not modify
                    private javax.swing.JLabel jLabel1;
                    private javax.swing.JLabel jLabel2;
                    // End of variables declaration

                };
                jd.dispose();
            }
        });

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
	
	/**
	 * Método usado para receber os eventos gerados pelos componentes da GUI.
	 */
	/** {@inheritDoc } */
	@Override
	public void actionPerformed( ActionEvent e ) {
		if( e.getSource() == novoProjeto || e.getSource() == novoProjetoMenu ) criarNovoProjeto();
		else if( e.getSource() == abrirProjeto || e.getSource() == abrirProjetoMenu ) abrirProjeto();
		else if( e.getSource() == salvarProjeto || e.getSource() == salvarProjetoMenu ) salvarProjeto();
		else if( e.getSource() == fecharProjetoMenu ) fecharProjeto();
		else if( e.getSource() == novaMatriz || e.getSource() == novaMatrizMenu ) adicionarMatriz();
		else if( e.getSource() == excluirMatriz || e.getSource() == excluirMatrizMenu ) excluirMatriz();
		else if( e.getSource() == sincronizarMatrizMenu ) sincronizarMatriz();
		else if( e.getSource() == cancelarEdicao ){
			for( JTableCustomizado j : matrizes ){
				if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
					ModeloTabela t = ( ModeloTabela ) j.getModel();
					Matriz mat = t.getMatriz();
					
					if( controle.getLinhaAtual() == -1 ) setNomeTextField( mat.getTituloColuna( controle.getColunaAtual() ) );
					else if( controle.getColunaAtual() == 0 ) setNomeTextField( mat.getTituloLinha( controle.getLinhaAtual() ) );
				}
			}
		} else if( e.getSource() == okEdicao || e.getSource() == nomeTextField ){
			if( controle.getLinhaAtual() == -1 ) atualizarColuna();
			else if( controle.getColunaAtual() == 0 ) atualizarLinha();
		} else if( e.getSource() == deslocar1 ){
			if( controle.getLinhaAtual() == -1 ) alterarPosicaoColuna( "esq" );
			else if( controle.getColunaAtual() == 0 ) alterarPosicaoLinha( "cima" );
			
		} else if( e.getSource() == deslocar2 ){
			if( controle.getLinhaAtual() == -1 ) alterarPosicaoColuna( "dir" );
			else if( controle.getColunaAtual() == 0 ) alterarPosicaoLinha( "baixo" );
			
		} else if( e.getSource() == novaLinhaColuna || e.getSource() == novaLinhaColunaMenu ){
			if( controle.getLinhaAtual() == -1 ) adicionarColuna();
			else if( controle.getColunaAtual() == 0 ) adicionarLinha();
			
		} else if( e.getSource() == excluirLinhaColuna || e.getSource() == excluirLinhaColunaMenu ){
			if( controle.getLinhaAtual() == -1 ) exclulirColuna();
			else if( controle.getColunaAtual() == 0 ) exclulirLinha();
			
		} else if( e.getSource() == ordenar || e.getSource() == ordenarMenu ){
			if( controle.getLinhaAtual() == -1 ) ordenarColuna();
			else if( controle.getColunaAtual() == 0 ) ordenarLinha();
			
		} else if( e.getSource() == importar || e.getSource() == importarDoModeloMenu ){
			if( controle.getLinhaAtual() == -1 ) adicionarColunasModelo();
			else if( controle.getColunaAtual() == 0 ) adicionarLinhasModelo();
			
		} else if( e.getSource() == sincronizar || e.getSource() == sincronizarMenu ){
			if( controle.getLinhaAtual() == -1 ) sincronizarColuna();
			else if( controle.getColunaAtual() == 0 ) sincronizarLinha();
			
		} else if( e.getSource() == destacar || e.getSource() == destacarMenu ) destacarElementos();
		else if( e.getSource() == resetarCamposNovosMenu ) resetarDestaque();
		else if( e.getSource() == salvarPDFMenu ) exportarPDF();
		else if( e.getSource() == salvarImagemMenu ) exportarImagem();
		else if( e.getSource() == imprimirMenu ) imprimir();
		
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrirProjeto;
    private javax.swing.JMenuItem abrirProjetoMenu;
    private javax.swing.JMenuItem ajudaMenu;
    private javax.swing.JButton cancelarEdicao;
    private javax.swing.JButton deslocar1;
    private javax.swing.JButton deslocar2;
    private javax.swing.JButton destacar;
    private javax.swing.JMenuItem destacarMenu;
    private javax.swing.JButton excluirLinhaColuna;
    private javax.swing.JMenuItem excluirLinhaColunaMenu;
    private javax.swing.JButton excluirMatriz;
    private javax.swing.JMenuItem excluirMatrizMenu;
    private javax.swing.JMenuItem fecharProjetoMenu;
    private javax.swing.JButton importar;
    private javax.swing.JMenuItem importarDoModeloMenu;
    private javax.swing.JMenuItem imprimirMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JTextField nomeTextField;
    private javax.swing.JButton novaLinhaColuna;
    private javax.swing.JMenuItem novaLinhaColunaMenu;
    private javax.swing.JButton novaMatriz;
    private javax.swing.JMenuItem novaMatrizMenu;
    private javax.swing.JButton novoProjeto;
    private javax.swing.JMenuItem novoProjetoMenu;
    private javax.swing.JButton okEdicao;
    private javax.swing.JButton ordenar;
    private javax.swing.JMenuItem ordenarMenu;
    private javax.swing.JMenuItem resetarCamposNovosMenu;
    private javax.swing.JMenuItem sairMenu;
    private javax.swing.JMenuItem salvarComoMenu;
    private javax.swing.JMenuItem salvarImagemMenu;
    private javax.swing.JMenuItem salvarPDFMenu;
    private javax.swing.JButton salvarProjeto;
    private javax.swing.JMenuItem salvarProjetoMenu;
    private javax.swing.JButton sincronizar;
    private javax.swing.JMenuItem sincronizarMatrizMenu;
    private javax.swing.JMenuItem sincronizarMenu;
    private javax.swing.JMenuItem sobreMenu;
    // End of variables declaration//GEN-END:variables
	
	/**
	 * Seta o estado visível de abrirProjeto e AbrirProjetoMenu.
	 * 
	 * @param estado boolean
	 */
	public void setAbrirProjeto( boolean estado ) {
		this.abrirProjeto.setEnabled( estado );
		this.abrirProjetoMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de excluirMatriz.
	 * 
	 * @param estado boolean
	 */
	public void setExcluirMatriz( boolean estado ) {
		excluirMatriz.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de cancelarEdicao.
	 * 
	 * @param estado boolean
	 */
	public void setCancelarEdicao( boolean estado ) {
		this.cancelarEdicao.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de deslocar1.
	 * 
	 * @param estado boolean
	 */
	public void setDeslocar1( boolean estado ) {
		this.deslocar1.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de deslocar2.
	 * 
	 * @param estado boolean
	 */
	public void setDeslocar2( boolean estado ) {
		this.deslocar2.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de destacar.
	 * 
	 * @param estado boolean
	 */
	public void setDestacar( boolean estado ) {
		this.destacar.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de destacarMenu.
	 * 
	 * @param estado boolean
	 */
	public void setDestacarMenu( boolean estado ) {
		this.destacarMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de excluirLinhaColuna.
	 * 
	 * @param estado boolean
	 */
	public void setExcluirLinhaColuna( boolean estado ) {
		this.excluirLinhaColuna.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de excluirLinhaColunaMenu.
	 * 
	 * @param estado boolean
	 */
	public void setExcluirLinhaColunaMenu( boolean estado ) {
		this.excluirLinhaColunaMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de excluirMatrizMenu.
	 * 
	 * @param estado boolean
	 */
	public void setExcluirMatrizMenu( boolean estado ) {
		this.excluirMatrizMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de fecharProjetoMenu.
	 * 
	 * @param estado boolean
	 */
	public void setFecharProjetoMenu( boolean estado ) {
		this.fecharProjetoMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de header.
	 * 
	 * @param estado boolean
	 */
	public void setHeader( boolean estado ) {
		this.header.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de importar.
	 * 
	 * @param estado boolean
	 */
	public void setImportar( boolean estado ) {
		this.importar.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de importarDoModeloMenu.
	 * 
	 * @param estado boolean
	 */
	public void setImportarDoModeloMenu( boolean estado ) {
		this.importarDoModeloMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de imprimirMenu.
	 * 
	 * @param estado boolean
	 */
	public void setImprimirMenu( boolean estado ) {
		this.imprimirMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de nomeTextField.
	 * 
	 * @param estado boolean
	 */
	public void setNomeTextField( boolean estado ) {
		nomeTextField.setEnabled( estado );
		if( !estado ) setNomeTextField( "" );
	}
	
	/**
	 * Seta o estado texto de nomeTextField.
	 * 
	 * @param nome texto a ser exibido
	 */
	public void setNomeTextField( String nome ) {
		nomeTextField.setText( nome.replace( "|||", "" ) );
	}
	
	/**
	 * Seta o estado visível de novaLinhaColuna.
	 * 
	 * @param estado boolean
	 */
	public void setNovaLinhaColuna( boolean estado ) {
		this.novaLinhaColuna.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de novaLinhaColunaMenu.
	 * 
	 * @param estado boolean
	 */
	public void setNovaLinhaColunaMenu( boolean estado ) {
		this.novaLinhaColunaMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de novaMatriz.
	 * 
	 * @param estado boolean
	 */
	public void setNovaMatriz( boolean estado ) {
		this.novaMatriz.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de novaMatrizMenu.
	 * 
	 * @param estado boolean
	 */
	public void setNovaMatrizMenu( boolean estado ) {
		this.novaMatrizMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de novoProjeto e novoProjetoMenu.
	 * 
	 * @param estado boolean
	 */
	public void setNovoProjeto( boolean estado ) {
		this.novoProjeto.setEnabled( estado );
		this.novoProjetoMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de okEdicao.
	 * 
	 * @param estado boolean
	 */
	public void setOkEdicao( boolean estado ) {
		this.okEdicao.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de ordenar.
	 * 
	 * @param estado boolean
	 */
	public void setOrdenar( boolean estado ) {
		this.ordenar.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de ordenarMenu.
	 * 
	 * @param estado boolean
	 */
	public void setOrdenarMenu( boolean estado ) {
		this.ordenarMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de resetarCamposNovosMenu.
	 * 
	 * @param estado boolean
	 */
	public void setResetarCamposNovosMenu( boolean estado ) {
		this.resetarCamposNovosMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de salvarImagemMenu.
	 * 
	 * @param estado boolean
	 */
	public void setSalvarImagemMenu( boolean estado ) {
		this.salvarImagemMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de salvarPDFMenu.
	 * 
	 * @param estado boolean
	 */
	public void setSalvarPDFMenu( boolean estado ) {
		this.salvarPDFMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de salvarComoMenu.
	 * 
	 * @param estado boolean
	 */
	public void setSalvarProjetoComo( boolean estado ) {
		this.salvarComoMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de salvarProjeto.
	 * 
	 * @param estado boolean
	 */
	public void setSalvarProjeto( boolean estado ) {
		this.salvarProjeto.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de salvarProjetoMenu.
	 * 
	 * @param estado boolean
	 */
	public void setSalvarProjetoMenu( boolean estado ) {
		this.salvarProjetoMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de sincronizar.
	 * 
	 * @param estado boolean
	 */
	public void setSincronizar( boolean estado ) {
		this.sincronizar.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de sincronizarMatrizMenu.
	 * 
	 * @param estado boolean
	 */
	public void setSincronizarMatrizMenu( boolean estado ) {
		this.sincronizarMatrizMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de sincronizarMenu.
	 * 
	 * @param estado boolean
	 */
	public void setSincronizarMenu( boolean estado ) {
		this.sincronizarMenu.setEnabled( estado );
	}
	
	/**
	 * Seta o estado visível de sobreMenu.
	 * 
	 * @param estado boolean
	 */
	public void setSobreMenu( boolean estado ) {
		this.sobreMenu.setEnabled( estado );
	}
	
	/**
	 * Método usado para checar se o destaque está ligado.
	 * 
	 * @return se o destaque está ligado ou não
	 */
	public boolean isDestaque() {
		if( destacar.getName().equals( "desligado" ) ) return true;
		return false;
	}
}
