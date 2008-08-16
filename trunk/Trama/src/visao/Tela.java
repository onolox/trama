package visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;

import negocio.ControleTela;

/**
 *
 * @author  Fabio
 */
public class Tela extends javax.swing.JFrame implements ActionListener {
    private JTableHeader header;
    private Object jScrollPane1;
    private ControleTela controle;

    public Tela() {
        initComponents();
        //  setExtendedState( MAXIMIZED_BOTH );
        controle = new ControleTela( this );

      //  jTable1.addMouseListener( new java.awt.event.MouseAdapter() {
      //                        public void mouseClicked( java.awt.event.MouseEvent evt ) {
       //                           jTable1MouseClicked( evt );
     //                         }
      //                    } );
  //      jScrollPane2.setViewportView( jTable1 );

    //    headerColunaClicked();


    }

    private void abrirProjeto() {
    }

  
    private void adicionarColuna() {
    }

   
    private void adicionarColunasModelo() {
    }

   
    private void adicionarLinha() {
    }

   
    private void adicionarLinhasModelo() {
    }

    private void adicionarMatriz() {
    }

    
    private void alterarPosicaoColuna() {
    }

  
    private void alterarPosicaoLinha() {
    }

   
    private void atualizarColuna() {
    }

    
    private void atualizarLinha() {
    }

    
    private void criarNovoProjeto() {
    }

    
    private void destacarElementos() {
    }

    
    private void excluirMatriz() {
    }

   
    private void exclulirColuna() {
    }

   
    private void exclulirLinha() {
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
    }

   
    private void ordenarLinha() {
    }

   
    private void posisaoJTable( ActionEvent evt ) {
    }

    private void resetarDestaque() {
    }

    
    private void salvarProjeto() {
    }

    
    private void sincronizarColuna() {
    }

    
    private void sincronizarLinha() {
    }

   
    private void sincronizarMatriz() {
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        novoProjeto = new javax.swing.JButton();
        abrirProjeto = new javax.swing.JButton();
        salvarProjeto = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        novaMatriz = new javax.swing.JButton();
        apagarMatriz = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        cancelarEdicao = new javax.swing.JButton();
        okEdicao = new javax.swing.JButton();
        nomeTextField = new javax.swing.JTextField();
        deslocar1 = new javax.swing.JButton();
        deslocar2 = new javax.swing.JButton();
        novaLinhaColuna = new javax.swing.JButton();
        excluirLinhaColuna = new javax.swing.JButton();
        jToolBar4 = new javax.swing.JToolBar();
        ordenar = new javax.swing.JButton();
        importar = new javax.swing.JButton();
        sincronizar = new javax.swing.JButton();
        jToolBar5 = new javax.swing.JToolBar();
        destacar = new javax.swing.JButton();
        jToolBar6 = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Trama");
        setMinimumSize(new java.awt.Dimension(800, 550));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(777, 36));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jToolBar1.setRollover(true);
        jToolBar1.setAlignmentX(0.0F);
        jToolBar1.setMaximumSize(new java.awt.Dimension(150, 35));
        jToolBar1.setMinimumSize(new java.awt.Dimension(158, 30));
        jToolBar1.setPreferredSize(new java.awt.Dimension(110, 45));

        novoProjeto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new window-26.png"))); // NOI18N
        novoProjeto.setText("Novo");
        novoProjeto.setFocusable(false);
        novoProjeto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        novoProjeto.setIconTextGap(1);
        novoProjeto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(novoProjeto);
        novoProjeto.addActionListener(this);

        abrirProjeto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fileopen-26.png"))); // NOI18N
        abrirProjeto.setText("Abrir");
        abrirProjeto.setFocusable(false);
        abrirProjeto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        abrirProjeto.setIconTextGap(1);
        abrirProjeto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(abrirProjeto);
        abrirProjeto.addActionListener(this);

        salvarProjeto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/filesave-26.png"))); // NOI18N
        salvarProjeto.setText("Salvar");
        salvarProjeto.setFocusable(false);
        salvarProjeto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        salvarProjeto.setIconTextGap(1);
        salvarProjeto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(salvarProjeto);
        salvarProjeto.addActionListener(this);

        jPanel1.add(jToolBar1);

        jToolBar3.setRollover(true);
        jToolBar3.setAlignmentY(0.5F);
        jToolBar3.setMaximumSize(new java.awt.Dimension(50, 50));
        jToolBar3.setMinimumSize(new java.awt.Dimension(50, 50));
        jToolBar3.setPreferredSize(new java.awt.Dimension(85, 45));

        novaMatriz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1day-26 copy.png"))); // NOI18N
        novaMatriz.setText("Novo");
        novaMatriz.setFocusable(false);
        novaMatriz.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        novaMatriz.setIconTextGap(1);
        novaMatriz.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(novaMatriz);
        novoProjeto.addActionListener(this);

        apagarMatriz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/agt_action_fail-26.png"))); // NOI18N
        apagarMatriz.setText("Apagar");
        apagarMatriz.setFocusable(false);
        apagarMatriz.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        apagarMatriz.setIconTextGap(1);
        apagarMatriz.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(apagarMatriz);
        apagarMatriz.addActionListener(this);

        jPanel1.add(jToolBar3);

        jToolBar2.setRollover(true);
        jToolBar2.setMaximumSize(new java.awt.Dimension(400, 30));
        jToolBar2.setMinimumSize(new java.awt.Dimension(300, 30));
        jToolBar2.setPreferredSize(new java.awt.Dimension(340, 45));

        jLabel1.setText(null);
        jToolBar2.add(jLabel1);

        cancelarEdicao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.gif"))); // NOI18N
        cancelarEdicao.setFocusable(false);
        cancelarEdicao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelarEdicao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(cancelarEdicao);
        cancelarEdicao.addActionListener(this);

        okEdicao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/agt_action_success-26.png"))); // NOI18N
        okEdicao.setFocusable(false);
        okEdicao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        okEdicao.setIconTextGap(1);
        okEdicao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(okEdicao);
        okEdicao.addActionListener(this);

        nomeTextField.setColumns(15);
        nomeTextField.setFont(new java.awt.Font("Arial", 0, 12));
        nomeTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        nomeTextField.setCaretColor(new java.awt.Color(255, 255, 255));
        nomeTextField.setMaximumSize(new java.awt.Dimension(150, 30));
        nomeTextField.setMinimumSize(new java.awt.Dimension(10, 10));
        nomeTextField.setPreferredSize(new java.awt.Dimension(50, 10));
        jToolBar2.add(nomeTextField);
        nomeTextField.addActionListener(this);

        deslocar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1downarrow-24.png"))); // NOI18N
        deslocar1.setAlignmentX(0.5F);
        deslocar1.setFocusable(false);
        deslocar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deslocar1.setIconTextGap(1);
        deslocar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(deslocar1);
        deslocar1.addActionListener(this);

        deslocar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1downarrow1-24.png"))); // NOI18N
        deslocar2.setFocusable(false);
        deslocar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deslocar2.setIconTextGap(1);
        deslocar2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(deslocar2);
        deslocar2.addActionListener(this);

        novaLinhaColuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/7days-26.png"))); // NOI18N
        novaLinhaColuna.setText("Novo");
        novaLinhaColuna.setFocusable(false);
        novaLinhaColuna.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        novaLinhaColuna.setIconTextGap(1);
        novaLinhaColuna.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(novaLinhaColuna);
        novaLinhaColuna.addActionListener(this);

        excluirLinhaColuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/button_cancel-26.png"))); // NOI18N
        excluirLinhaColuna.setText("Excluir");
        excluirLinhaColuna.setFocusable(false);
        excluirLinhaColuna.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        excluirLinhaColuna.setIconTextGap(1);
        excluirLinhaColuna.setMaximumSize(new java.awt.Dimension(100, 100));
        excluirLinhaColuna.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(excluirLinhaColuna);
        excluirLinhaColuna.addActionListener(this);

        jPanel1.add(jToolBar2);

        jToolBar4.setRollover(true);
        jToolBar4.setMaximumSize(new java.awt.Dimension(400, 30));
        jToolBar4.setMinimumSize(new java.awt.Dimension(200, 30));
        jToolBar4.setPreferredSize(new java.awt.Dimension(170, 45));

        ordenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/db-26.png"))); // NOI18N
        ordenar.setText("Ordenar");
        ordenar.setFocusable(false);
        ordenar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ordenar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(ordenar);
        ordenar.addActionListener(this);

        importar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/inbox-26 copy.png"))); // NOI18N
        importar.setText("Importar");
        importar.setFocusable(false);
        importar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        importar.setIconTextGap(1);
        importar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(importar);
        importar.addActionListener(this);

        sincronizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload_all_tabs-26.png"))); // NOI18N
        sincronizar.setText("Sincronizar");
        sincronizar.setFocusable(false);
        sincronizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sincronizar.setIconTextGap(1);
        sincronizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(sincronizar);
        sincronizar.addActionListener(this);

        jPanel1.add(jToolBar4);

        jToolBar5.setRollover(true);

        destacar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/irkickoff-26.png"))); // NOI18N
        destacar.setText("Destacar");
        destacar.setFocusable(false);
        destacar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        destacar.setIconTextGap(1);
        destacar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar5.add(destacar);
        destacar.addActionListener(this);

        jPanel1.add(jToolBar5);

        jToolBar6.setRollover(true);
        jPanel1.add(jToolBar6);

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.setName("Requisitos X UC"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Requisitos X UC", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Requisitos X UC");

        jMenu1.setText("Arquivo");

        novoProjetoMenu.setText("Novo Projeto...");
        jMenu1.add(novoProjetoMenu);
        novoProjetoMenu.addActionListener(this);

        abrirProjetoMenu.setText("Abrir Projeto...");
        jMenu1.add(abrirProjetoMenu);
        abrirProjetoMenu.addActionListener(this);

        salvarProjetoMenu.setText("Salvar Projeto");
        jMenu1.add(salvarProjetoMenu);
        salvarProjetoMenu.addActionListener(this);

        fecharProjetoMenu.setText("Fechar Projeto");
        jMenu1.add(fecharProjetoMenu);
        fecharProjetoMenu.addActionListener(this);
        jMenu1.add(jSeparator3);

        salvarPDFMenu.setText("Salvar Como PDF...");
        jMenu1.add(salvarPDFMenu);
        salvarPDFMenu.addActionListener(this);

        salvarImagemMenu.setText("Salvar Como Imagem...");
        jMenu1.add(salvarImagemMenu);
        salvarImagemMenu.addActionListener(this);

        imprimirMenu.setText("Imprimir...");
        jMenu1.add(imprimirMenu);
        imprimirMenu.addActionListener(this);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Matriz");

        novaMatrizMenu.setText("Nova Matriz");
        jMenu2.add(novaMatrizMenu);
        novaMatrizMenu.addActionListener(this);

        sincronizarMatrizMenu.setText("Sincronizar Matriz...");
        jMenu2.add(sincronizarMatrizMenu);
        sincronizarMatrizMenu.addActionListener(this);

        excluirMatrizMenu.setText("Excluir Matriz");
        jMenu2.add(excluirMatrizMenu);
        excluirMatrizMenu.addActionListener(this);

        jMenuBar1.add(jMenu2);

        jMenu5.setText("Linha/Coluna");

        novaLinhaColunaMenu.setText("Nova Linha/Coluna");
        jMenu5.add(novaLinhaColunaMenu);
        novaLinhaColunaMenu.addActionListener(this);

        excluirLinhaColunaMenu.setText("Excluir Linha/Coluna");
        jMenu5.add(excluirLinhaColunaMenu);
        excluirLinhaColunaMenu.addActionListener(this);
        jMenu5.add(jSeparator2);

        sincronizarMenu.setText("Sincronizar do Modelo");
        jMenu5.add(sincronizarMenu);

        ordenarMenu.setText("Ordenar");
        jMenu5.add(ordenarMenu);
        ordenarMenu.addActionListener(this);

        importarDoModeloMenu.setText("Importar do Modelo...");
        jMenu5.add(importarDoModeloMenu);
        importarDoModeloMenu.addActionListener(this);

        jMenuBar1.add(jMenu5);

        jMenu10.setText("Ferramentas");

        importarMenu.setText("Importar");
        jMenu10.add(importarMenu);
        importarMenu.addActionListener(this);

        destacarMenu.setText("Destacar Relações");
        jMenu10.add(destacarMenu);
        destacar.addActionListener(this);

        resetarCamposNovosMenu.setText("Resetar Campos Novos");
        jMenu10.add(resetarCamposNovosMenu);
        resetarCamposNovosMenu.addActionListener(this);

        jMenuBar1.add(jMenu10);

        jMenu3.setText("Ajuda");

        sobreMenu.setText("Sobre");
        jMenu3.add(sobreMenu);
        sobreMenu.addActionListener(this);

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

    private void jTable1MouseClicked( java.awt.event.MouseEvent evt ) {
      //  int linha = jTable1.getSelectedRow();
     //   int coluna = jTable1.getSelectedColumn();

    //    System.out.println( "Linha=" + ( linha + 1 ) + "   coluna= " + coluna );

    }

    private void headerColunaClicked() {
     //   header = jTable1.getTableHeader();

        header.addMouseListener( new MouseAdapter() {
                             @Override
                             public void mouseClicked( MouseEvent e ) {
                                 int coluna = header.columnAtPoint( e.getPoint() );

                                 System.out.println( "coluna= " + coluna );
                                // matriz.setTituloColuna( coluna, "coluna9999" );
                                // jTable1.getColumnModel().getColumn( coluna ).setHeaderValue( "45" );
                             }
                         } );
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        if ( e.getSource() == novoProjeto || e.getSource() == novoProjetoMenu ) {
        } else if ( e.getSource() == abrirProjeto || e.getSource() == abrirProjetoMenu ) {
        } else if ( e.getSource() == salvarProjeto || e.getSource() == salvarProjetoMenu ) {
        } else if ( e.getSource() == fecharProjetoMenu ) {
        } else if ( e.getSource() == novaMatriz || e.getSource() == novaMatrizMenu ) {
        } else if ( e.getSource() == apagarMatriz || e.getSource() == excluirMatrizMenu ) {
        } else if ( e.getSource() == sincronizarMatrizMenu ) {
        } else if ( e.getSource() == cancelarEdicao ) {
        } else if ( e.getSource() == okEdicao || e.getSource() == nomeTextField ) {
        } else if ( e.getSource() == deslocar1 ) {
        } else if ( e.getSource() == deslocar2 ) {
        } else if ( e.getSource() == novaLinhaColuna || e.getSource() == novaLinhaColunaMenu ) {
        } else if ( e.getSource() == excluirLinhaColuna || e.getSource() == excluirLinhaColunaMenu ) {
        } else if ( e.getSource() == ordenar || e.getSource() == ordenarMenu ) {
        } else if ( e.getSource() == importar || e.getSource() == importarMenu ) {
        } else if ( e.getSource() == sincronizar || e.getSource() == sincronizarMenu ) {
        } else if ( e.getSource() == destacar || e.getSource() == destacarMenu ) {
        } else if ( e.getSource() == resetarCamposNovosMenu ) {
        } else if ( e.getSource() == salvarPDFMenu ) {
        } else if ( e.getSource() == salvarImagemMenu ) {
        } else if ( e.getSource() == imprimirMenu ) {
        }
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrirProjeto;
    private javax.swing.JMenuItem abrirProjetoMenu;
    private javax.swing.JButton apagarMatriz;
    private javax.swing.JButton cancelarEdicao;
    private javax.swing.JButton deslocar1;
    private javax.swing.JButton deslocar2;
    private javax.swing.JButton destacar;
    private javax.swing.JMenuItem destacarMenu;
    private javax.swing.JButton excluirLinhaColuna;
    private javax.swing.JMenuItem excluirLinhaColunaMenu;
    private javax.swing.JMenuItem excluirMatrizMenu;
    private javax.swing.JMenuItem fecharProjetoMenu;
    private javax.swing.JButton importar;
    private javax.swing.JMenuItem importarDoModeloMenu;
    private javax.swing.JMenuItem importarMenu;
    private javax.swing.JMenuItem imprimirMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JToolBar jToolBar6;
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

    

}
