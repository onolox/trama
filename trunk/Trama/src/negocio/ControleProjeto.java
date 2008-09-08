package negocio;

import java.util.HashMap;
import java.util.LinkedList;

import persistencia.DadosMatriz;
import persistencia.PersistenciaProjeto;
import persistencia.Projeto;
import visao.ModeloTabela;

public class ControleProjeto {
	private LinkedList< Matriz > matrizes;
	private PersistenciaProjeto persistenciaProjeto;
	private Projeto projeto;
	private boolean estado = false;
	
	public ControleProjeto() {
		projeto = new Projeto();
		persistenciaProjeto = new PersistenciaProjeto();
		matrizes = new LinkedList< Matriz >();
	}
	
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
	
	public ModeloTabela adicionarMatriz( String nomeMatriz ) {
		Matriz m = new Matriz( new DadosMatriz( nomeMatriz ) );
		ModeloTabela mT = new ModeloTabela( m );
		matrizes.add( m );
		
		return mT;
	}
	
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
	
	public HashMap< String, LinkedList< String >> destacarElementos( int elemento, String tipo, String nomeMatriz ) {
		HashMap< String, LinkedList< String >> m = new HashMap< String, LinkedList< String > >();
		if( estado ){
			for( Matriz matriz : matrizes )
				matriz.resetarDestaque();
			estado = false;
			return m;
		}
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
			recursivo( mauElemento, toq );
		} catch( Exception e ){
			e.printStackTrace();
			m = null;
		}
		
		return m;
	}
	
	private void recursivo( String nome, String toq ) {
		LinkedList< String > l = null;
		
		System.out.println( nome + " ------ " + toq );
		try{
			for( Matriz matriz : matrizes ){
				String t1 = matriz.getNomeMatriz().split( " X " )[ 0 ];
				String t2 = matriz.getNomeMatriz().split( " X " )[ 1 ];
				
				if( t1.equals( toq ) ){
					System.out.println( t1 + "   t1 igual a toq  na linha    " + toq );
					for( int i = 0; i < matriz.getQLinhas(); i++ ){
						if( matriz.getTituloLinha( i ).equals( nome ) ){
							l = matriz.destacarElementos( i, "linha" );
							for( int j = 0; j < l.size(); j++ ){
								System.out.println( l.get( j ) + "          " + t1 );
								recursivo( l.get( j ), t2 );
							}
						}
					}
				} else if( t2.equals( toq ) ){
					System.out.println( t2 + "   t2 igual a toq  na coluna    " + toq );
					for( int i = 1; i < matriz.getQColunas(); i++ ){
						if( matriz.getTituloColuna( i ).equals( nome ) ){
							l = matriz.destacarElementos( i, "coluna" );
							for( int j = 0; j < l.size(); j++ ){
								recursivo( l.get( j ), t1 );
							}
						}
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
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
	
	public String excluirMatriz( String nomeMatriz ) {
		String s = "ok";
		Matriz m = null;
		try{
			for( Matriz matriz : matrizes ){
				if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ) m = matriz;
			}
			matrizes.remove( m );
		} catch( Exception e ){
			e.printStackTrace();
			s = "Erro";
		}
		return s;
	}
	public LinkedList< String > getNomes( String nomeMatriz, String tipo ) {
		LinkedList< String > l = new LinkedList< String >();
		for( Matriz matriz : matrizes ){
			if( matriz.getNomeMatriz().equalsIgnoreCase( nomeMatriz ) ){
				if( tipo.equals( "linha" ) ){
					for( int i = 0; i < matriz.getQLinhas(); i++ )
						l.add( matriz.getTituloLinha( i ) );
				} else{
					for( int i = 0; i < matriz.getQLinhas(); i++ )
						l.add( matriz.getTituloColuna( i ) );
				}
			}
		}
		
		return l;
	}
	
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
	
	public String salvarProjeto( String nome ) {
		String s = "ok";
		LinkedList< DadosMatriz > lista = new LinkedList< DadosMatriz >();
		
		if( ( nome.equals( "vazio" ) || nome.equals( "" ) ) && projeto.getNome().equals( "" ) ) s = "sem nome";
		else if( !nome.equals( "vazio" ) ){
			try{
				for( Matriz matriz : matrizes ){
					lista.add( matriz.getDadosMatriz() );
				}
				projeto.setNome( nome );
				projeto.setMatrizes( lista );
				s = persistenciaProjeto.salvar( projeto );
			} catch( Exception e ){
				e.printStackTrace();
				s = "erro";
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
				s = "erro";
			}
		}
		return s;
	}
	
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
}