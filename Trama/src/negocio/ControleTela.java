package negocio;

import java.util.HashMap;
import java.util.LinkedList;

import negocio.leitor.LeitorDeModelo;
import visao.ModeloTabela;
import visao.Tela;

public class ControleTela {
        private ControleProjeto controleProjeto;
        private Tela tela;
        private LeitorDeModelo leitorDeModelo;
        private int colunaAtual,  linhaAtual;
        private String matrizAtual;

        public ControleTela( Tela tela ) {
                this.tela = tela;
                leitorDeModelo = new LeitorDeModelo();
                colunaAtual = 0;
                linhaAtual = 0;
                matrizAtual = null;
        }

        public LinkedList<ModeloTabela> abrirProjeto( String nome ) {
                return null;
        }

        public String adicionarColuna( String nome ) {
                String s = "ok";
                s = controleProjeto.adicionarColuna( nome, matrizAtual );
                return s;
        }

        public LinkedList<String> adicionarColunasModelo( String nome ) {
                return null;
        }

        public String adicionarLinha( String nome ) {
                String s = "ok";
                s = controleProjeto.adicionarLinha( nome, matrizAtual );
                return s;
        }

        public LinkedList<String> adicionarLinhasModelo( String nome ) {
                return null;
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

        /**
         * @param para para
         */
        public String alterarPosicaoColuna( String para ) {
                String s = "ok";
                if ( para.equalsIgnoreCase( "esq" ) ) {
                        s = controleProjeto.alterarPosicaoColuna( colunaAtual - 1, colunaAtual, matrizAtual );
                        if ( s.equalsIgnoreCase( "fora" ) ) {
                                return "fora";
                        } else {
                                colunaAtual = colunaAtual - 1;
                        }
                } else {
                        s = controleProjeto.alterarPosicaoColuna( colunaAtual + 1, colunaAtual, matrizAtual );
                        if ( s.equalsIgnoreCase( "fora" ) ) {
                                return "fora";
                        } else {
                                colunaAtual = colunaAtual + 1;
                        }
                }
                return s;
        }

        public String alterarPosicaoLinha( String para ) {
                String s = "ok";
                if ( para.equalsIgnoreCase( "cima" ) ) {
                        s = controleProjeto.alterarPosicaoLinha( linhaAtual - 1, linhaAtual, matrizAtual );
                        if ( s.equalsIgnoreCase( "fora" ) ) {
                                return "ok";
                        } else {
                                linhaAtual = linhaAtual - 1;
                        }
                } else {
                        s = controleProjeto.alterarPosicaoLinha( linhaAtual + 1, linhaAtual, matrizAtual );
                        if ( s.equalsIgnoreCase( "fora" ) ) {
                                return "ok";
                        } else {
                                linhaAtual = linhaAtual + 1;
                        }
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
                controleProjeto = new ControleProjeto();

                tela.setSalvarProjeto( true );
                tela.setNovaMatriz( true );

                tela.setSalvarProjetoMenu( true );
                tela.setNovaMatrizMenu( true );
                tela.setFecharProjetoMenu( true );

                return s;
        }

        public HashMap<String, LinkedList<String>> destacarElementos() {
                return null;
        }

        public String excluirColuna() {
                String s = "ok";
                if ( colunaAtual > 0 ) {
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
                s = controleProjeto.excluirMatriz( matrizAtual );
                return s;
        }

        public void fecharProjeto() {
        }

        public HashMap<String, LinkedList<String>> getNomeExtensoes() {
                return null;
        }

        public String ordenarColuna() {
                String s = "ok";

                return s;
        }

        public String ordenarLinha() {
                String s = "ok";

                return s;
        }

        public String resetarDestaque() {
                return "";
        }

        public String salvarProjeto( String nome ) {
                String s = "ok";

                return s;
        }

        public String setDado() {
                String s = "ok";

                return s;
        }

        public String setMatrizAtual( String nome, int linha, int coluna ) {
                String s = "ok";

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