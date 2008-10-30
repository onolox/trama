package negocio;

import java.util.LinkedList;

import persistencia.DadosMatriz;
import persistencia.PersistenciaProjeto;
import persistencia.Projeto;
import visao.ModeloTabela;

/**
 * Classe usada para processar dados referentes ao sistema.
 * 
 * @author Fabio Marmitt
 */
public class ControleProjeto {
	/** Lista de matrizes do projeto */
	private LinkedList< Matriz > matrizes;
	private PersistenciaProjeto persistenciaProjeto;
	private Projeto projeto;
	private boolean estado = false;
	
	/**
	 * Construtor padrão da classe.
	 */
	public ControleProjeto() {
		projeto = new Projeto();
		persistenciaProjeto = new PersistenciaProjeto();
		matrizes = new LinkedList< Matriz >();
	}
	
	/**
	 * Usado para abrir um novo projeto.
	 * 
	 * @param nome nome do projeto
	 * @return lista de ModeloTabelas
	 */
	public LinkedList< ModeloTabela > abrirProjeto( String nome ) {
		LinkedList< ModeloTabela > l = new LinkedList< ModeloTabela >();
		
		try{
			projeto = persistenciaProjeto.abrir( nome );
			LinkedList< DadosMatriz > matri = projeto.getMatrizes();
			for( DadosMatriz dados : matri ){
				Matriz m = new Matriz( dados );
				ModeloTabela mT = new ModeloTabela( m );
				l.add( mT );
				matrizes.add( m );
			}
		} catch( Exception e ){
			e.printStackTrace();
			l = null;
		}
		return l;
	}
	
	/**
	 * Usado pra adicionar uma nova coluna em uma matriz.
	 * 
	 * @param nome nome da coluna
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String adicionarColuna( String nome, String nomeMatriz ) {
		String s = "ok";
		
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ) matriz.adicionarColuna( nome );
			}
		} catch( Exception e ){
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Método usado para inserir uma linha em uma matriz.
	 * 
	 * @param nome Nome da linha
	 * @param nomeMatriz Nome da matriz
	 * @return Estado da transação
	 */
	public String adicionarLinha( String nome, String nomeMatriz ) {
		String s = "ok";
		
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ) matriz.adicionarLinha( nome );
			}
		} catch( Exception e ){
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para adicionar uma nova matriz ao projeto.
	 * 
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return ModeloTabela
	 */
	public ModeloTabela adicionarMatriz( String nomeMatriz ) {
		Matriz m = new Matriz( new DadosMatriz( nomeMatriz ) );
		ModeloTabela mT = new ModeloTabela( m );
		matrizes.add( m );
		
		return mT;
	}
	
	/**
	 * Usado para alterar a posição de uma coluna.
	 * 
	 * @param para posição
	 * @param de posição
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String alterarPosicaoColuna( int para, int de, String nomeMatriz ) {
		String s = "ok";
		if( para < 1 ){ return "fora"; }
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					if( matriz.getQColunas() <= para ) return "fora";
					
					matriz.alterarPosicaoColuna( de, para );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para alterar a posição de uma linha.
	 * 
	 * @param para posição
	 * @param de posição
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String alterarPosicaoLinha( int para, int de, String nomeMatriz ) {
		String s = "ok";
		if( para < 0 ) return "fora";
		
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					if( matriz.getQLinhas() <= para ){ return "fora"; }
					matriz.alterarPosicaoLinha( de, para );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para atualizar o nome de uma coluna em uma matriz.
	 * 
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @param coluna indice da coluna
	 * @param nomeNovo nome novo
	 * @return estatus da operação
	 */
	public String atualizarColuna( String nomeMatriz, int coluna, String nomeNovo ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					matriz.setTituloColuna( coluna, nomeNovo );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para atualizar o nome de uma linha em uma matriz.
	 * 
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @param linha índice
	 * @param nomeNovo novo nome para a linha
	 * @return estatus da operação
	 */
	public String atualizarLinha( String nomeMatriz, int linha, String nomeNovo ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ) matriz.setTituloLinha( linha, nomeNovo );
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para destacar os elementos que tenham relação com o objeto selecionado atualmente.
	 * 
	 * @param elemento íncide da linha ou coluna
	 * @param tipo se é linha ou coluna
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 */
	public void destacarElementos( int elemento, String tipo, String nomeMatriz ) {
		if( estado ){
			for( Matriz matriz : matrizes )
				matriz.resetarDestaque();
			estado = false;
		} else{
			estado = true;
			
			String mauElemento = "";
			String toq = nomeMatriz.split( " X " )[ 1 ];
			if( tipo.equalsIgnoreCase( "linha" ) ) toq = nomeMatriz.split( " X " )[ 0 ];
			
			try{
				for( Matriz matriz : matrizes ){
					if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
						if( tipo.equalsIgnoreCase( "linha" ) ) mauElemento = matriz.getTituloLinha( elemento );
						else mauElemento = matriz.getTituloColuna( elemento );
					}
				}
				LinkedList< String > list = new LinkedList< String >();
				list.add( mauElemento );
				recursivo( list, toq, toq, list );
			} catch( Exception e ){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Usado para destacar elementos. Método recursivo.
	 * 
	 * @param nomes das linhas ou colunas
	 * @param toq metade do nome da matriz
	 */
	private void recursivo( LinkedList< String > nomes, String toq, String toqOriginal, LinkedList< String > nomeOriginal ) {
		LinkedList< String > l = null;
		
		try{
			for( Matriz matriz : matrizes ){
				String t1 = matriz.getNomeMatriz().split( " X " )[ 0 ];
				String t2 = matriz.getNomeMatriz().split( " X " )[ 1 ];
				
				if( t1.equals( toq ) ){
					if( t1.equals( toqOriginal ) ) l = matriz.destacarElementos( nomeOriginal, "linha" );
					else l = matriz.destacarElementos( nomes, "linha" );
					
					if( t2.equals( toqOriginal ) ) l = nomeOriginal;
					if( !l.isEmpty() ) recursivo( l, t2, toqOriginal, nomeOriginal );
					
				} else if( t2.equals( toq ) ){
					if( t2.equals( toqOriginal ) ) l = matriz.destacarElementos( nomeOriginal, "coluna" );
					else l = matriz.destacarElementos( nomes, "coluna" );
					
					if( t1.equals( toqOriginal ) ) l = nomeOriginal;
					if( !l.isEmpty() ) recursivo( l, t1, toqOriginal, nomeOriginal );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para excluir uma coluna de uma matriz.
	 * 
	 * @param coluna índice da coluna
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String excluirColuna( int coluna, String nomeMatriz ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					if( matriz.getQColunas() < 3 ){
						s = "A matriz não pode ter menos de uma coluna";
					} else{
						matriz.removeColuna( coluna );
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para excluir uma linha de uma matriz.
	 * 
	 * @param linha indice da linha
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String excluirLinha( int linha, String nomeMatriz ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					if( matriz.getQLinhas() < 2 ){
						s = "A matriz não pode ter menos de uma linha";
					} else{
						matriz.removeLinha( linha );
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para excluir uma matriz do projeto.
	 * 
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String excluirMatriz( String nomeMatriz ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					matrizes.remove( matriz );
					break;
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Busca a lista de nomes das linhas ou colunas de uma matriz.
	 * 
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @param tipo se é linha ou coluna
	 * @return lista de nomes
	 */
	public LinkedList< String > getNomes( String nomeMatriz, String tipo ) {
		LinkedList< String > l = new LinkedList< String >();
		for( Matriz matriz : matrizes ){
			if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
				if( tipo.equals( "linha" ) ){
					for( int i = 0; i < matriz.getQLinhas(); i++ )
						l.add( matriz.getTituloLinha( i ) );
				} else{
					for( int i = 0; i < matriz.getQColunas(); i++ )
						l.add( matriz.getTituloColuna( i ) );
				}
			}
		}
		
		return l;
	}
	
	/**
	 * Busca o nome do arquivo que foi usado para importar os nomes.
	 * 
	 * @param nome nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String getTituloColunaArquivo( String nome ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nome ) ){
					s = matriz.getDadosMatriz().getTituloColunaArquivo();
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Busca o nome do arquivo que foi usado para importar os nomes.
	 * 
	 * @param nome nome da matriz que se deseja buscar o nome
	 * @return estatus da operação
	 */
	public String getTituloLinhaArquivo( String nome ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nome ) ){
					s = matriz.getDadosMatriz().getTituloLinhaArquivo();
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para ordenar as colunas da matriz atual.
	 * 
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String ordenarColuna( String nomeMatriz ) {
		String s = "ok";
		
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ) matriz.ordenarColunas();
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para ordenar as linhas da matriz atual.
	 * 
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String ordenarLinha( String nomeMatriz ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ) matriz.ordenarLinhas();
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para resetar o destaque que a matriz possa ter.
	 * 
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String resetarDestaque( String nomeMatriz ) {
		String s = "ok";
		
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					for( int j = 1; j < matriz.getQColunas(); j++ ){
						for( int i = 0; i < matriz.getQLinhas(); i++ ){
							String dado = matriz.getDadoMatriz( i, j );
							if( dado.equals( "20" ) ){
								matriz.setDadoMatriz( 0, i, j );
							} else if( dado.equals( "2" ) ){
								matriz.setDadoMatriz( 1, i, j );
							} else if( dado.equals( "30" ) ){
								matriz.setDadoMatriz( 0, i, j );
							} else if( dado.equals( "3" ) ){
								matriz.setDadoMatriz( 1, i, j );
							} else if( dado.equals( "40" ) ){
								matriz.setDadoMatriz( 0, i, j );
							} else if( dado.equals( "4" ) ){
								matriz.setDadoMatriz( 1, i, j );
							}
						}
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
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
		LinkedList< DadosMatriz > lista = new LinkedList< DadosMatriz >();
		
		if( ( nome.equals( "|vazio|" ) || nome.equals( "" ) ) && projeto.getNome().equals( "" ) ) s = "|sem nome|";
		else if( !nome.equals( "|vazio|" ) ){
			try{
				for( Matriz matriz : matrizes ){
					lista.add( matriz.getDadosMatriz() );
				}
				projeto.setNome( nome );
				projeto.setMatrizes( lista );
				s = persistenciaProjeto.salvar( projeto );
			} catch( Exception e ){
				e.printStackTrace();
				s = "Erro ao tentar salvar o projeto";
			}
		} else{
			try{
				for( Matriz matriz : matrizes ){
					lista.add( matriz.getDadosMatriz() );
				}
				projeto.setMatrizes( lista );
				s = persistenciaProjeto.salvar( projeto );
			} catch( Exception e ){
				e.printStackTrace();
				s = "Erro ao tentar salvar o projeto";
			}
		}
		return s;
	}
	
	/**
	 * Seta o nome do arquivo que foi usado pra importar nomes das colunas de uma matriz.
	 * 
	 * @param nomeArquivo nome do arquivo
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String setArquivoColuna( String nomeArquivo, String nomeMatriz ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					matriz.getDadosMatriz().setTituloColunaArquivo( nomeArquivo );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Seta o nome do arquivo que foi usado pra importar nomes das linhas de uma matriz.
	 * 
	 * @param nomeArquivo nome do arquivo
	 * @param nomeMatriz nome da matriz que se deseja alterar
	 * @return estatus da operação
	 */
	public String setArquivoLinha( String nomeArquivo, String nomeMatriz ) {
		String s = "ok";
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					matriz.getDadosMatriz().setTituloLinhaArquivo( nomeArquivo );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Usado para setar um dado nas células de uma matriz.
	 * 
	 * @param linha índice da linha
	 * @param coluna índice da coluna
	 * @param nomeMatriz nome da matriz que se deseja setar o dado
	 * @return estatus da operação
	 */
	public String setDado( int linha, int coluna, String nomeMatriz ) {
		String s = "ok";
		
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					if( matriz.getDadoMatriz( linha, coluna ).equals( "0" ) ) matriz.setDadoMatriz( 1, linha, coluna );
					else if( matriz.getDadoMatriz( linha, coluna ).equals( "1" ) ) matriz.setDadoMatriz( 0, linha, coluna );
					else if( matriz.getDadoMatriz( linha, coluna ).equals( "20" ) ) matriz.setDadoMatriz( 2, linha, coluna );
					else if( matriz.getDadoMatriz( linha, coluna ).equals( "2" ) ) matriz.setDadoMatriz( 20, linha, coluna );
					else if( matriz.getDadoMatriz( linha, coluna ).equals( "30" ) ) matriz.setDadoMatriz( 3, linha, coluna );
					else if( matriz.getDadoMatriz( linha, coluna ).equals( "3" ) ) matriz.setDadoMatriz( 30, linha, coluna );
					else if( matriz.getDadoMatriz( linha, coluna ).equals( "40" ) ) matriz.setDadoMatriz( 4, linha, coluna );
					else if( matriz.getDadoMatriz( linha, coluna ).equals( "4" ) ) matriz.setDadoMatriz( 40, linha, coluna );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	
	/**
	 * Faz a triagem de objetos que não existem em determinadas linhas ou colunas de uma matriz.
	 * 
	 * @param nomeMatriz o nome da matriz que se deseja consultar
	 * @param tipo se é linha ou coluna
	 * @param lista a lista de nomes importados de um arquivo
	 * @return lista de nomes
	 */
	public LinkedList< String > triagemObjetos( String nomeMatriz, String tipo, LinkedList< String > lista ) {
		LinkedList< String > l = null;
		try{
			l = ( LinkedList< String > ) lista.clone();
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
					for( String nom : lista ){
						if( tipo.equals( "linha" ) ){
							for( int i = 0; i < matriz.getQLinhas(); i++ ){
								if( matriz.getTituloLinha( i ).equals( nom ) ) l.remove( nom );
							}
						} else{
							for( int i = 0; i < matriz.getQColunas(); i++ ){
								if( matriz.getTituloColuna( i ).equals( nom ) ) l.remove( nom );
							}
						}
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			l = null;
		}
		return l;
	}
	
	/**
	 * Usado para verificar a existência de um nome igual de linha ou coluna. Existindo retorna o arquivo de importação.
	 * 
	 * @param nomeMatriz nome da matriz base
	 * @param tipo se é linha ou coluna
	 * @return o nome do arquivo ou null se não houver
	 */
	public String triagemMatrizes( String nomeMatriz, String tipo ) {
		String s = "";
		if( tipo.equals( "linha" ) ) s = nomeMatriz.split( " X " )[ 0 ];
		else s = nomeMatriz.split( " X " )[ 1 ];
		
		for( Matriz matriz : matrizes ){
			String t1 = matriz.getNomeMatriz().split( " X " )[ 0 ];
			if( t1.equals( s ) ) return getTituloLinhaArquivo( matriz.getNomeMatriz() );
			
			String t2 = matriz.getNomeMatriz().split( " X " )[ 1 ];
			if( t2.equals( s ) ) return getTituloColunaArquivo( matriz.getNomeMatriz() );
		}
		return null;
	}
}
