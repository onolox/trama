package negocio;

import java.util.HashMap;
import java.util.LinkedList;

import persistencia.DadosMatriz;
import persistencia.PersistenciaProjeto;
import persistencia.Projeto;
import visao.ModeloTabela;

public class ControleProjeto {
        private LinkedList<Matriz> matrizes;
        private PersistenciaProjeto persistenciaProjeto;
        private Projeto projeto;

        public ControleProjeto() {
                projeto = new Projeto();
                persistenciaProjeto = new PersistenciaProjeto();
                matrizes = new LinkedList< Matriz >();
        }

        public void abrirProjeto() {
        }

        public LinkedList<ModeloTabela> abrirProjeto( String nome ) {
                return null;
        }

        public String adicionarColuna( String nome, String nomeMatriz ) {
               String s = "ok";

                try {
                        for ( Matriz matriz : matrizes ) {
                                if ( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ) {
                                        matriz.adicionarColuna( nome );
                                }
                        }
                } catch ( Exception e ) {
                        s = "Erro";
                }
                return s;
        }

        public void adicionarColunasModelo() {
        }

        public String adicionarLinha( String nome, String nomeMatriz ) {
                String s = "ok";

                try {
                        for ( Matriz matriz : matrizes ) {
                                if ( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ) {
                                        matriz.adicionarLinha( nome );
                                }
                        }
                } catch ( Exception e ) {
                        s = "Erro";
                }
                return s;
        }

        public void adicionarLinhasModelo() {
        }

        public ModeloTabela adicionarMatriz( String nomeMatriz ) {
                Matriz m = new Matriz( new DadosMatriz( nomeMatriz ) );
                m.adicionarLinha( "");
                m.adicionarColuna( " ");
                ModeloTabela mT = new ModeloTabela( m );
                matrizes.add( m );

                return mT;
        }

        public String alterarPosicaoColuna( int para, int de, String nomeMatriz ) {
                return null;
        }

        public String alterarPosicaoLinha( int para, int de, String nomeMatriz ) {
                return null;
        }

        public String atualizarColuna( String nomeMatriz, int coluna, String nomeNovo ) {
                return null;
        }

        public String atualizarLinha( String nomeMatriz, int linha, String nomeNovo ) {
                return null;
        }

        public void criarNovoProjeto() {
        }

        public HashMap<String, LinkedList<String>> destacarElementos( int elemento, String tipo, String nomeMatriz ) {
                return null;
        }

        public String excluirColuna( int coluna, String nomeMatriz ) {
                return null;
        }

        public String excluirLinha( int linha, String nomeMatriz ) {
                return null;
        }

        public String excluirMatriz( String nomeMatriz ) {
                return null;
        }

        public void exportarImagem() {
        }

        public void fecharProjeto() {
        }

        public String getTituloColunaArquivo( String nome ) {
                return "";
        }

        public String getTituloLinhaArquivo( String nome ) {
                return "";
        }

        public void imprimir() {
        }

        public String ordenarColuna( String nomeMatriz ) {
                return null;
        }

        public String ordenarLinha( String nomeMatriz ) {
                return null;
        }

        public String resetarDestaque( String nomeMatriz ) {
                return "";
        }

        public String salvarProjeto() {
                return null;
        }

        public String setArquivoColuna( String nomeArquivo, String nomeMatriz ) {
                return null;
        }

        public String setArquivoLinha( String nomeArquivo, String nomeMatriz ) {
                return null;
        }

        public String setDado( int linha, int coluna, String nomeMatriz ) {
                return "";
        }

        public void sincronizarColuna() {
        }

        public void sincronizarLinha() {
        }

        public void sincronizarMatriz() {
        }

        public LinkedList<String> triagemObjetos( String nomeMatriz, LinkedList<String> lista ) {
                return null;
        }
}
