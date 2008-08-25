package visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.JTableHeader;

import negocio.ControleTela;
import negocio.Matriz;

/**
 * @author Fabio
 */
public class Tela extends javax.swing.JFrame implements ActionListener {
	private JTableHeader header;
	private ControleTela controle;
	private LinkedList< JTableCustomizado > matrizes;
	private LinkedList< JPanel > JP;
	
	public Tela() {
		initComponents();
		setLocationRelativeTo( null );
		controle = new ControleTela( this );
		matrizes = new LinkedList< JTableCustomizado >();
		
		JP = new LinkedList< JPanel >();
	}
	
	private void abrirProjeto() {
	}
	
	private void adicionarColuna() {
		String s = "";
		s = JOptionPane.showInputDialog( this, "Insira o nome desejado para a coluna", "Adicionar Coluna", JOptionPane.QUESTION_MESSAGE );
		if( s.equalsIgnoreCase( "" ) ){
			s = "coluna " + controle.getColunaAtual();
		}
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
			}
		}
		
	}
	
	private void adicionarColunasModelo() {
	}
	
	private void adicionarLinha() {
		String s = "";
		s = JOptionPane.showInputDialog( this, "Insira o nome desejado para a linha", "Adicionar Linha", JOptionPane.QUESTION_MESSAGE );
		if( s.equalsIgnoreCase( "" ) ){
			s = "linha " + controle.getLinhaAtual();
		}
		s = controle.adicionarLinha( s );
		
		if( !s.equalsIgnoreCase( "ok" ) ){
			JOptionPane.showMessageDialog( this, s, "Erro", 1 );
		}
		
		for( JTableCustomizado j : matrizes ){
			if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
				ModeloTabela t = ( ModeloTabela ) j.getModel();
				t.fireTableDataChanged();
			}
		}
	}
	
	private void adicionarLinhasModelo() {
	}
	
	private void adicionarMatriz() {
		String s = "";
		boolean bol = true;
		
		s = JOptionPane.showInputDialog( this, "Insira o nome desejado para a matriz", "Adicionar Matriz", JOptionPane.QUESTION_MESSAGE );
		
		while( bol ){
			bol = false;
			if( s.equalsIgnoreCase( "" ) ){
				s = "Requisitos X UC";
			}
			for( JTableCustomizado jtab : matrizes ){
				if( s.equalsIgnoreCase( jtab.getNome() ) ){
					bol = true;
					s = JOptionPane.showInputDialog( this, "Nome j· existente, insira outro nome", "Adicionar Matriz", JOptionPane.QUESTION_MESSAGE );
				}
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
		final JTableCustomizado jT = new JTableCustomizado( m );
		
		js.setViewportView( jT );
		j.add( js );
		
		adicionarListener( jT );
		
		matrizes.add( jT );
		jTabbedPane1.add( j );
	}
	
	private void alterarPosicaoColuna( String lado ) {
		String s = "";
		s = controle.alterarPosicaoColuna( lado );
		
		if( !s.equalsIgnoreCase( "fora" ) ){
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
				}
			}
		}
	}
	
	private void alterarPosicaoLinha( String lado ) {
		String s = "";
		s = controle.alterarPosicaoLinha( lado );
		
		if( !s.equalsIgnoreCase( "ok" ) ){
			JOptionPane.showMessageDialog( this, s, "Erro", 1 );
		} else{
			for( JTableCustomizado j : matrizes ){
				if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
					j.updateUI();
				}
			}
		}
	}
	
	private void atualizarColuna() {
		if( nomeTextField.getText().equals( "" ) ){
			JOptionPane.showMessageDialog( this, "O nome n„o pode ser vazio", "Erro no nome", 0 );
			cancelarEdicao.doClick();
			
		} else{
			String s = controle.atualizarColuna( nomeTextField.getText().replace( "|||", "" ) );
			
			if( !s.equalsIgnoreCase( "ok" ) ) JOptionPane.showMessageDialog( this, s, "Erro", 1 );
			
			for( JTableCustomizado j : matrizes ){
				if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
					j.getColumnModel().getColumn( controle.getColunaAtual() ).setHeaderValue( nomeTextField.getText().replace( "|||", "" ) );
					j.updateUI();
				}
			}
		}
	}
	
	private void atualizarLinha() {
		if( nomeTextField.getText().equals( "" ) ){
			JOptionPane.showMessageDialog( this, "O nome n„o pode ser vazio", "Erro no nome", 0 );
			cancelarEdicao.doClick();
		} else{
			String s = controle.atualizarLinha( nomeTextField.getText().replace( "|||", "" ) );
			if( !s.equalsIgnoreCase( "ok" ) ) JOptionPane.showMessageDialog( this, s, "Erro", 1 );
			
			for( JTableCustomizado j : matrizes ){
				if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
					ModeloTabela t = ( ModeloTabela ) j.getModel();
					t.fireTableDataChanged();
				}
			}
		}
	}
	
	private void criarNovoProjeto() {
		String s = controle.criarNovoProjeto();
		if( !s.equalsIgnoreCase( "ok" ) ) JOptionPane.showMessageDialog( this, s, "Erro", 1 );
		
		adicionarMatriz();
	}
	
	private void destacarElementos() {
		controle.destacarElementos();
	}
	
	private void excluirMatriz() {
		int ge = JOptionPane.showConfirmDialog( this, "Tem certeza que deseja excluir a matriz " + controle.getMatrizAtual() + " do projeto?", "", 0 );
		if( ge == 0 ){
			for( int i = 0; i < matrizes.size(); i++ ){
				if( matrizes.get( i ).getNome().equalsIgnoreCase( controle.getMatrizAtual() ) ){
					matrizes.remove( i );
					JP.remove( i ).getParent().removeAll();
					String s = controle.excluirMatriz();
					if( !s.equalsIgnoreCase( "ok" ) ) JOptionPane.showMessageDialog( this, s, "Erro", 1 );
				}
			}
		}
	}
	
	private void exclulirColuna() {
		String s = "ok";
		s = controle.excluirColuna();
		
		if( !s.equalsIgnoreCase( "ok" ) ){
			JOptionPane.showMessageDialog( this, s, "Erro", 1 );
		} else{
			for( int i = 0; i < matrizes.size(); i++ ){ // Gambiarra fod·°stica
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
					
					setNomeTextField( mod.getMatriz().getTituloColuna( controle.getColunaAtual() ) );
				}
			}
		}
	}
	
	private void exclulirLinha() {
		String s = "ok";
		s = controle.excluirLinha();
		
		if( !s.equalsIgnoreCase( "ok" ) ){ // Donde est√°s?
			JOptionPane.showMessageDialog( this, s, "Erro", 1 );
		} else{
			for( JTableCustomizado j : matrizes ){
				if( controle.getMatrizAtual().equalsIgnoreCase( j.getNome() ) ){
					ModeloTabela t = ( ModeloTabela ) j.getModel();
					t.fireTableDataChanged();
					
					setNomeTextField( t.getMatriz().getTituloLinha( controle.getLinhaAtual() ) );
				}
			}
		}
	}
	
	private void exportarImagem() {
	}
	
	private void exportarPDF() {
	}
	
	private void fecharProjeto() {
	}
	
	private void imprimir() {
	}
	
	private void ordenarColuna() {
		String s = "ok";
		s = controle.ordenarColuna();
		
		if( !s.equalsIgnoreCase( "ok" ) ){
			JOptionPane.showMessageDialog( this, s, "Erro", 1 );
		} else{
			for( int i = 0; i < matrizes.size(); i++ ){ // Gambiarra fod·°stica
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
					
					setNomeTextField( mod.getMatriz().getTituloColuna( controle.getColunaAtual() ) );
				}
			}
		}
	}
	
	private void ordenarLinha() {
		String s = "ok";
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
		
	}
	
	private void posisaoJTable( ActionEvent evt ) {
	}
	
	private void resetarDestaque() {
	}
	
	private void salvarProjeto() {
		String s = "";
		s = controle.salvarProjeto( "vazio" );
		if( s.equals( "sem nome" ) ){
			s = JOptionPane.showInputDialog( this, "Insira um nome para o projeto", "Nome do projeto", 0 );
			
			s = controle.salvarProjeto( s );
		}
	}
	
	private void sincronizarColuna() {
	}
	
	private void sincronizarLinha() {
	}
	
	private void sincronizarMatriz() {
	}
	
	private void adicionarListener( final JTableCustomizado jT ) {
		jT.addMouseListener( new MouseAdapter() { // Adiciona listener as tabelas
				@Override
				public void mouseClicked( java.awt.event.MouseEvent e ) {
					int linha = jT.getSelectedRow();
					int coluna = jT.getSelectedColumn();
					
					for( JTableCustomizado jTableCustomizado : matrizes ){
						if( jTableCustomizado == e.getSource() ){
							controle.setMatrizAtual( jTableCustomizado.getNome() );
							System.out.println( "Matriz atual: " + jTableCustomizado.getNome() );
						}
					}
					controle.setLinhaAtual( linha );
					controle.setColunaAtual( coluna );
					System.out.println( "Linha=" + ( linha ) + "   coluna= " + coluna );
					
					if( coluna == 0 ){// Toda vez que se clicar em um nome de------------------------------------------------------------------ linha -------
						for( JTableCustomizado jTableCustomizado : matrizes ){
							if( jTableCustomizado.getNome().equalsIgnoreCase( controle.getMatrizAtual() ) ){
								setNomeTextField( ( ( ModeloTabela ) jTableCustomizado.getModel() ).getMatriz().getTituloLinha( linha ) );
							}
							
						}
						deslocar1.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/1downarrow-24.png" ) ) );
						deslocar1.setToolTipText( "Deslocar Linha Para Baixo" );
						deslocar2.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/1downarrow1-24.png" ) ) );
						deslocar1.setToolTipText( "Deslocar Linha Para Cima" );
						novaLinhaColuna.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/7days-26.png" ) ) );
						novaLinhaColuna.setToolTipText( "Nova Linha" );
						novaLinhaColunaMenu.setText( "Nova Linha" );
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
						setImportarMenu( true );
						setImportarDoModeloMenu( true );
						setDestacar( true );
						setDestacarMenu( true );
					} else{ // Aqui È quando se clica nas ---------------------------------------------------------cÈlulas ---------------------
					
						controle.setDado();
						( ( ModeloTabela ) jT.getModel() ).fireTableDataChanged();
						
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
						setImportarMenu( false );
						setImportarDoModeloMenu( false );
						setDestacar( false );
						setDestacarMenu( false );
					}// Modafoca
				}
			} );
		
		header = jT.getTableHeader();
		header.addMouseListener( new MouseAdapter() { // adiciona listeners aos cabecalhos ----Serve pros nomes de colunas ------------header--
				@Override
				public void mouseClicked( MouseEvent e ) {
					int coluna = header.columnAtPoint( e.getPoint() );
					controle.setLinhaAtual( -1 );
					if( coluna < 1 ){
						coluna = 1;
					}
					controle.setColunaAtual( coluna );
					
					System.out.println( "Linha=" + ( controle.getLinhaAtual() ) + "   coluna= " + coluna );
					
					for( JTableCustomizado jTableCustomizado : matrizes ){
						if( jTableCustomizado.getTableHeader() == e.getSource() ){
							controle.setMatrizAtual( jTableCustomizado.getNome() );
							System.out.println( "Matriz atual:::: " + jTableCustomizado.getNome() );
						}
						
					}
					
					if( coluna > 0 ){
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
						novaLinhaColunaMenu.setText( "Nova Coluna" );
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
						setImportarMenu( true );
						setImportarDoModeloMenu( true );
						setDestacar( true );
						setDestacarMenu( true );
					}
					
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
		fecharProjetoMenu = new javax.swing.JMenuItem();
		jSeparator3 = new javax.swing.JSeparator();
		salvarPDFMenu = new javax.swing.JMenuItem();
		salvarImagemMenu = new javax.swing.JMenuItem();
		imprimirMenu = new javax.swing.JMenuItem();
		jSeparator7 = new javax.swing.JSeparator();
		jMenuItem1 = new javax.swing.JMenuItem();
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
		importarMenu = new javax.swing.JMenuItem();
		destacarMenu = new javax.swing.JMenuItem();
		resetarCamposNovosMenu = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		sobreMenu = new javax.swing.JMenuItem();
		
		setDefaultCloseOperation( javax.swing.WindowConstants.EXIT_ON_CLOSE );
		setTitle( "Trama" );
		setMinimumSize( new java.awt.Dimension( 800, 550 ) );
		
		jPanel1.setBorder( javax.swing.BorderFactory.createEtchedBorder() );
		jPanel1.setPreferredSize( new java.awt.Dimension( 777, 36 ) );
		jPanel1.setLayout( new java.awt.FlowLayout( java.awt.FlowLayout.LEFT, 0, 0 ) );
		
		jToolBar1.setFloatable( false );
		jToolBar1.setRollover( true );
		jToolBar1.setAlignmentX( 0.0F );
		jToolBar1.setMaximumSize( new java.awt.Dimension( 150, 35 ) );
		jToolBar1.setMinimumSize( new java.awt.Dimension( 158, 30 ) );
		jToolBar1.setPreferredSize( new java.awt.Dimension( 110, 45 ) );
		
		novoProjeto.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/new window-26.png" ) ) ); // NOI18N
		novoProjeto.setText( "Novo" );
		novoProjeto.setToolTipText( "Novo Projeto" );
		novoProjeto.setFocusable( false );
		novoProjeto.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		novoProjeto.setIconTextGap( 1 );
		novoProjeto.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar1.add( novoProjeto );
		novoProjeto.addActionListener( this );
		
		abrirProjeto.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/fileopen-26.png" ) ) ); // NOI18N
		abrirProjeto.setText( "Abrir" );
		abrirProjeto.setToolTipText( "Abrir Projeto" );
		abrirProjeto.setFocusable( false );
		abrirProjeto.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		abrirProjeto.setIconTextGap( 1 );
		abrirProjeto.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar1.add( abrirProjeto );
		abrirProjeto.addActionListener( this );
		
		salvarProjeto.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/filesave-26.png" ) ) ); // NOI18N
		salvarProjeto.setText( "Salvar" );
		salvarProjeto.setToolTipText( "Salvar Projeto" );
		salvarProjeto.setEnabled( false );
		salvarProjeto.setFocusable( false );
		salvarProjeto.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		salvarProjeto.setIconTextGap( 1 );
		salvarProjeto.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar1.add( salvarProjeto );
		salvarProjeto.addActionListener( this );
		
		jPanel1.add( jToolBar1 );
		
		jToolBar3.setFloatable( false );
		jToolBar3.setRollover( true );
		jToolBar3.setAlignmentY( 0.5F );
		jToolBar3.setMaximumSize( new java.awt.Dimension( 50, 50 ) );
		jToolBar3.setMinimumSize( new java.awt.Dimension( 50, 50 ) );
		jToolBar3.setPreferredSize( new java.awt.Dimension( 85, 45 ) );
		jToolBar3.add( jSeparator1 );
		
		novaMatriz.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/1day-26 copy.png" ) ) ); // NOI18N
		novaMatriz.setText( "Novo" );
		novaMatriz.setToolTipText( "Nova Matriz" );
		novaMatriz.setEnabled( false );
		novaMatriz.setFocusable( false );
		novaMatriz.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		novaMatriz.setIconTextGap( 1 );
		novaMatriz.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar3.add( novaMatriz );
		novaMatriz.addActionListener( this );
		
		excluirMatriz.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/agt_action_fail-26.png" ) ) ); // NOI18N
		excluirMatriz.setText( "Excluir" );
		excluirMatriz.setToolTipText( "Excluir Matriz Atual" );
		excluirMatriz.setEnabled( false );
		excluirMatriz.setFocusable( false );
		excluirMatriz.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		excluirMatriz.setIconTextGap( 1 );
		excluirMatriz.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar3.add( excluirMatriz );
		excluirMatriz.addActionListener( this );
		
		jPanel1.add( jToolBar3 );
		
		jToolBar2.setFloatable( false );
		jToolBar2.setRollover( true );
		jToolBar2.setMaximumSize( new java.awt.Dimension( 400, 30 ) );
		jToolBar2.setMinimumSize( new java.awt.Dimension( 300, 30 ) );
		jToolBar2.setPreferredSize( new java.awt.Dimension( 340, 45 ) );
		jToolBar2.add( jSeparator4 );
		
		cancelarEdicao.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/delete.gif" ) ) ); // NOI18N
		cancelarEdicao.setToolTipText( "Cancelar Edi√ß√£o" );
		cancelarEdicao.setEnabled( false );
		cancelarEdicao.setFocusable( false );
		cancelarEdicao.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		cancelarEdicao.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar2.add( cancelarEdicao );
		cancelarEdicao.addActionListener( this );
		
		okEdicao.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/agt_action_success-26.png" ) ) ); // NOI18N
		okEdicao.setToolTipText( "Confirmar Edi√ß√£o" );
		okEdicao.setEnabled( false );
		okEdicao.setFocusable( false );
		okEdicao.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		okEdicao.setIconTextGap( 1 );
		okEdicao.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar2.add( okEdicao );
		okEdicao.addActionListener( this );
		
		nomeTextField.setColumns( 15 );
		nomeTextField.setFont( new java.awt.Font( "Arial", 0, 12 ) );
		nomeTextField.setToolTipText( "Nome da Linha ou Coluna Selecionada" );
		nomeTextField.setBorder( new javax.swing.border.LineBorder( new java.awt.Color( 204, 204, 204 ), 1, true ) );
		nomeTextField.setEnabled( false );
		nomeTextField.setMaximumSize( new java.awt.Dimension( 150, 30 ) );
		nomeTextField.setMinimumSize( new java.awt.Dimension( 10, 10 ) );
		nomeTextField.setPreferredSize( new java.awt.Dimension( 50, 10 ) );
		jToolBar2.add( nomeTextField );
		nomeTextField.addActionListener( this );
		
		deslocar1.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/1downarrow-24.png" ) ) ); // NOI18N
		deslocar1.setToolTipText( "Mover Para Esquerda/Cima" );
		deslocar1.setAlignmentX( 0.5F );
		deslocar1.setEnabled( false );
		deslocar1.setFocusable( false );
		deslocar1.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		deslocar1.setIconTextGap( 1 );
		deslocar1.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar2.add( deslocar1 );
		deslocar1.addActionListener( this );
		
		deslocar2.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/1downarrow1-24.png" ) ) ); // NOI18N
		deslocar2.setToolTipText( "Mover Para Direita/Baxo" );
		deslocar2.setEnabled( false );
		deslocar2.setFocusable( false );
		deslocar2.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		deslocar2.setIconTextGap( 1 );
		deslocar2.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar2.add( deslocar2 );
		deslocar2.addActionListener( this );
		
		novaLinhaColuna.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/7days-26.png" ) ) ); // NOI18N
		novaLinhaColuna.setText( "Novo" );
		novaLinhaColuna.setToolTipText( "Nova linha" );
		novaLinhaColuna.setEnabled( false );
		novaLinhaColuna.setFocusable( false );
		novaLinhaColuna.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		novaLinhaColuna.setIconTextGap( 1 );
		novaLinhaColuna.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar2.add( novaLinhaColuna );
		novaLinhaColuna.addActionListener( this );
		
		excluirLinhaColuna.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/button_cancel-26.png" ) ) ); // NOI18N
		excluirLinhaColuna.setText( "Excluir" );
		excluirLinhaColuna.setToolTipText( "Excluir Linha/Coluna" );
		excluirLinhaColuna.setEnabled( false );
		excluirLinhaColuna.setFocusable( false );
		excluirLinhaColuna.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		excluirLinhaColuna.setIconTextGap( 1 );
		excluirLinhaColuna.setMaximumSize( new java.awt.Dimension( 100, 100 ) );
		excluirLinhaColuna.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar2.add( excluirLinhaColuna );
		excluirLinhaColuna.addActionListener( this );
		
		jPanel1.add( jToolBar2 );
		
		jToolBar4.setFloatable( false );
		jToolBar4.setRollover( true );
		jToolBar4.setMaximumSize( new java.awt.Dimension( 400, 30 ) );
		jToolBar4.setMinimumSize( new java.awt.Dimension( 200, 30 ) );
		jToolBar4.setPreferredSize( new java.awt.Dimension( 170, 45 ) );
		jToolBar4.add( jSeparator5 );
		
		ordenar.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/db-26.png" ) ) ); // NOI18N
		ordenar.setText( "Ordenar" );
		ordenar.setToolTipText( "Ordenar" );
		ordenar.setEnabled( false );
		ordenar.setFocusable( false );
		ordenar.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		ordenar.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar4.add( ordenar );
		ordenar.addActionListener( this );
		
		importar.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/inbox-26 copy.png" ) ) ); // NOI18N
		importar.setText( "Importar" );
		importar.setToolTipText( "Importar do Modelo" );
		importar.setEnabled( false );
		importar.setFocusable( false );
		importar.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		importar.setIconTextGap( 1 );
		importar.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar4.add( importar );
		importar.addActionListener( this );
		
		sincronizar.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/reload_all_tabs-26.png" ) ) ); // NOI18N
		sincronizar.setText( "Sincronizar" );
		sincronizar.setToolTipText( "Sincronizar do Modelo" );
		sincronizar.setEnabled( false );
		sincronizar.setFocusable( false );
		sincronizar.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		sincronizar.setIconTextGap( 1 );
		sincronizar.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar4.add( sincronizar );
		sincronizar.addActionListener( this );
		
		jPanel1.add( jToolBar4 );
		
		jToolBar5.setFloatable( false );
		jToolBar5.setRollover( true );
		jToolBar5.add( jSeparator6 );
		
		destacar.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/icons/irkickoff-26.png" ) ) ); // NOI18N
		destacar.setText( "Destacar" );
		destacar.setToolTipText( "Destacar Elementos Relacionados" );
		destacar.setEnabled( false );
		destacar.setFocusable( false );
		destacar.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
		destacar.setIconTextGap( 1 );
		destacar.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
		jToolBar5.add( destacar );
		destacar.addActionListener( this );
		
		jPanel1.add( jToolBar5 );
		
		jTabbedPane1.setPreferredSize( new java.awt.Dimension( 20, 20 ) );
		
		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout( jPanel2 );
		jPanel2.setLayout( jPanel2Layout );
		jPanel2Layout.setHorizontalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 787,
			Short.MAX_VALUE ) );
		jPanel2Layout.setVerticalGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470,
			Short.MAX_VALUE ) );
		
		jTabbedPane1.addChangeListener( new javax.swing.event.ChangeListener() {
			public void stateChanged( javax.swing.event.ChangeEvent evt ) {
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
				setImportarMenu( false );
				setImportarDoModeloMenu( false );
				setDestacar( false );
				setDestacarMenu( false );
			}
		} );
		
		jMenu1.setMnemonic( 'A' );
		jMenu1.setText( "Arquivo" );
		
		novoProjetoMenu.setText( "Novo Projeto..." );
		jMenu1.add( novoProjetoMenu );
		novoProjetoMenu.addActionListener( this );
		
		abrirProjetoMenu.setText( "Abrir Projeto..." );
		jMenu1.add( abrirProjetoMenu );
		abrirProjetoMenu.addActionListener( this );
		
		salvarProjetoMenu.setText( "Salvar Projeto" );
		salvarProjetoMenu.setEnabled( false );
		jMenu1.add( salvarProjetoMenu );
		salvarProjetoMenu.addActionListener( this );
		
		fecharProjetoMenu.setText( "Fechar Projeto" );
		fecharProjetoMenu.setEnabled( false );
		jMenu1.add( fecharProjetoMenu );
		fecharProjetoMenu.addActionListener( this );
		jMenu1.add( jSeparator3 );
		
		salvarPDFMenu.setText( "Salvar Como PDF..." );
		salvarPDFMenu.setEnabled( false );
		jMenu1.add( salvarPDFMenu );
		salvarPDFMenu.addActionListener( this );
		
		salvarImagemMenu.setText( "Salvar Como Imagem..." );
		salvarImagemMenu.setEnabled( false );
		jMenu1.add( salvarImagemMenu );
		salvarImagemMenu.addActionListener( this );
		
		imprimirMenu.setText( "Imprimir..." );
		imprimirMenu.setEnabled( false );
		jMenu1.add( imprimirMenu );
		imprimirMenu.addActionListener( this );
		jMenu1.add( jSeparator7 );
		
		jMenuItem1.setText( "Sair" );
		jMenu1.add( jMenuItem1 );
		
		jMenuBar1.add( jMenu1 );
		
		jMenu2.setMnemonic( 'M' );
		jMenu2.setText( "Matriz" );
		
		novaMatrizMenu.setText( "Nova Matriz" );
		novaMatrizMenu.setEnabled( false );
		jMenu2.add( novaMatrizMenu );
		novaMatrizMenu.addActionListener( this );
		
		sincronizarMatrizMenu.setText( "Sincronizar Matriz..." );
		sincronizarMatrizMenu.setEnabled( false );
		jMenu2.add( sincronizarMatrizMenu );
		sincronizarMatrizMenu.addActionListener( this );
		
		excluirMatrizMenu.setText( "Excluir Matriz" );
		excluirMatrizMenu.setEnabled( false );
		jMenu2.add( excluirMatrizMenu );
		excluirMatrizMenu.addActionListener( this );
		
		jMenuBar1.add( jMenu2 );
		
		jMenu5.setMnemonic( 'L' );
		jMenu5.setText( "Linha/Coluna" );
		
		novaLinhaColunaMenu.setText( "Nova Linha/Coluna" );
		novaLinhaColunaMenu.setEnabled( false );
		jMenu5.add( novaLinhaColunaMenu );
		novaLinhaColunaMenu.addActionListener( this );
		
		excluirLinhaColunaMenu.setText( "Excluir Linha/Coluna" );
		excluirLinhaColunaMenu.setEnabled( false );
		jMenu5.add( excluirLinhaColunaMenu );
		excluirLinhaColunaMenu.addActionListener( this );
		jMenu5.add( jSeparator2 );
		
		sincronizarMenu.setText( "Sincronizar do Modelo" );
		sincronizarMenu.setEnabled( false );
		jMenu5.add( sincronizarMenu );
		
		ordenarMenu.setText( "Ordenar" );
		ordenarMenu.setEnabled( false );
		jMenu5.add( ordenarMenu );
		ordenarMenu.addActionListener( this );
		
		importarDoModeloMenu.setText( "Importar do Modelo..." );
		importarDoModeloMenu.setEnabled( false );
		jMenu5.add( importarDoModeloMenu );
		importarDoModeloMenu.addActionListener( this );
		
		jMenuBar1.add( jMenu5 );
		
		jMenu10.setMnemonic( 'F' );
		jMenu10.setText( "Ferramentas" );
		
		importarMenu.setText( "Importar" );
		importarMenu.setEnabled( false );
		jMenu10.add( importarMenu );
		importarMenu.addActionListener( this );
		
		destacarMenu.setText( "Destacar Elementos Relacionados" );
		destacarMenu.setEnabled( false );
		jMenu10.add( destacarMenu );
		destacar.addActionListener( this );
		
		resetarCamposNovosMenu.setText( "Resetar Campos Novos" );
		resetarCamposNovosMenu.setEnabled( false );
		jMenu10.add( resetarCamposNovosMenu );
		resetarCamposNovosMenu.addActionListener( this );
		
		jMenuBar1.add( jMenu10 );
		
		jMenu3.setMnemonic( 'j' );
		jMenu3.setText( "Ajuda" );
		
		sobreMenu.setText( "Sobre" );
		jMenu3.add( sobreMenu );
		sobreMenu.addActionListener( this );
		
		jMenuBar1.add( jMenu3 );
		
		setJMenuBar( jMenuBar1 );
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane() );
		getContentPane().setLayout( layout );
		layout.setHorizontalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jPanel2, javax.swing.GroupLayout.Alignment.TRAILING,
			javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ).addComponent( jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE ) );
		layout.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
			layout.createSequentialGroup().addComponent( jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE ).addPreferredGap(
				javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addComponent( jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) ) );
		
		pack();
	}// </editor-fold>//GEN-END:initComponents
	
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
			
		} else if( e.getSource() == importar || e.getSource() == importarMenu ){
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
	private javax.swing.JMenuItem importarMenu;
	private javax.swing.JMenuItem imprimirMenu;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu10;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenu jMenu5;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
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
	private javax.swing.JMenuItem salvarImagemMenu;
	private javax.swing.JMenuItem salvarPDFMenu;
	private javax.swing.JButton salvarProjeto;
	private javax.swing.JMenuItem salvarProjetoMenu;
	private javax.swing.JButton sincronizar;
	private javax.swing.JMenuItem sincronizarMatrizMenu;
	private javax.swing.JMenuItem sincronizarMenu;
	private javax.swing.JMenuItem sobreMenu;
	// End of variables declaration//GEN-END:variables
	
	public void setExcluirMatriz( boolean estado ) {
		excluirMatriz.setEnabled( estado );
	}
	
	public void setCancelarEdicao( boolean estado ) {
		this.cancelarEdicao.setEnabled( estado );
	}
	
	public void setDeslocar1( boolean estado ) {
		this.deslocar1.setEnabled( estado );
	}
	
	public void setDeslocar2( boolean estado ) {
		this.deslocar2.setEnabled( estado );
	}
	
	public void setDestacar( boolean estado ) {
		this.destacar.setEnabled( estado );
	}
	
	public void setDestacarMenu( boolean estado ) {
		this.destacarMenu.setEnabled( estado );
	}
	
	public void setExcluirLinhaColuna( boolean estado ) {
		this.excluirLinhaColuna.setEnabled( estado );
	}
	
	public void setExcluirLinhaColunaMenu( boolean estado ) {
		this.excluirLinhaColunaMenu.setEnabled( estado );
	}
	
	public void setExcluirMatrizMenu( boolean estado ) {
		this.excluirMatrizMenu.setEnabled( estado );
	}
	
	public void setFecharProjetoMenu( boolean estado ) {
		this.fecharProjetoMenu.setEnabled( estado );
	}
	
	public void setHeader( boolean estado ) {
		this.header.setEnabled( estado );
	}
	
	public void setImportar( boolean estado ) {
		this.importar.setEnabled( estado );
	}
	
	public void setImportarDoModeloMenu( boolean estado ) {
		this.importarDoModeloMenu.setEnabled( estado );
	}
	
	public void setImportarMenu( boolean estado ) {
		this.importarMenu.setEnabled( estado );
	}
	
	public void setImprimirMenu( boolean estado ) {
		this.imprimirMenu.setEnabled( estado );
	}
	
	public void setNomeTextField( boolean estado ) {
		nomeTextField.setEnabled( estado );
	}
	
	public void setNomeTextField( String nome ) {
		nomeTextField.setText( nome.replace( "|", "" ) );
	}
	
	public void setNovaLinhaColuna( boolean estado ) {
		this.novaLinhaColuna.setEnabled( estado );
	}
	
	public void setNovaLinhaColunaMenu( boolean estado ) {
		this.novaLinhaColunaMenu.setEnabled( estado );
	}
	
	public void setNovaMatriz( boolean estado ) {
		this.novaMatriz.setEnabled( estado );
	}
	
	public void setNovaMatrizMenu( boolean estado ) {
		this.novaMatrizMenu.setEnabled( estado );
	}
	
	public void setOkEdicao( boolean estado ) {
		this.okEdicao.setEnabled( estado );
	}
	
	public void setOrdenar( boolean estado ) {
		this.ordenar.setEnabled( estado );
	}
	
	public void setOrdenarMenu( boolean estado ) {
		this.ordenarMenu.setEnabled( estado );
	}
	
	public void setResetarCamposNovosMenu( boolean estado ) {
		this.resetarCamposNovosMenu.setEnabled( estado );
	}
	
	public void setSalvarImagemMenu( boolean estado ) {
		this.salvarImagemMenu.setEnabled( estado );
	}
	
	public void setSalvarPDFMenu( boolean estado ) {
		this.salvarPDFMenu.setEnabled( estado );
	}
	
	public void setSalvarProjeto( boolean estado ) {
		this.salvarProjeto.setEnabled( estado );
	}
	
	public void setSalvarProjetoMenu( boolean estado ) {
		this.salvarProjetoMenu.setEnabled( estado );
	}
	
	public void setSincronizar( boolean estado ) {
		this.sincronizar.setEnabled( estado );
	}
	
	public void setSincronizarMatrizMenu( boolean estado ) {
		this.sincronizarMatrizMenu.setEnabled( estado );
	}
	
	public void setSincronizarMenu( boolean estado ) {
		this.sincronizarMenu.setEnabled( estado );
	}
	
	public void setSobreMenu( boolean estado ) {
		this.sobreMenu.setEnabled( estado );
	}
}
