package negocio;

import java.util.LinkedList;

import persistencia.DadosMatriz;

/**
 * Classe que representa uma matriz e é uma fachada para acesso ao {@link DadosMatriz}. <br>
 * Esta classe é responsável pelo processamento de dados referente a uma matriz do projeto.
 * 
 * @author Fabio Marmitt
 */
public class Matriz {
	
	/** Instância de {@link DadosMatriz} */
	private DadosMatriz matriz;
	
	/**
	 * Construtor padrão.
	 * 
	 * @param matriz DadosMatriz que esta classe vai usar
	 */
	public Matriz( DadosMatriz matriz ) {
		this.matriz = matriz;
	}
	
	/**
	 * Usado pra adicionar uma nova coluna.
	 * 
	 * @param titulo nome
	 */
	public void adicionarColuna( String titulo ) {
		matriz.setQColunas( 1 );
		matriz.getTituloColuna().add( titulo );
		
		for( int i = 0; i < matriz.getQLinhas(); i++ ){
			matriz.getLinha( i ).add( "40" );
		}
		
		for( int j = 1; j < getQColunas() - 1; j++ ){
			for( int i = 0; i < getQLinhas(); i++ ){
				String dado = getDadoMatriz( i, j );
				if( dado.equals( "20" ) ){
					setDadoMatriz( 0, i, j );
				} else if( dado.equals( "2" ) ){
					setDadoMatriz( 1, i, j );
				} else if( dado.equals( "30" ) ){
					setDadoMatriz( 20, i, j );
				} else if( dado.equals( "3" ) ){
					setDadoMatriz( 2, i, j );
				} else if( dado.equals( "40" ) ){
					setDadoMatriz( 30, i, j );
				} else if( dado.equals( "4" ) ){
					setDadoMatriz( 3, i, j );
				}
			}
		}
	}
	
	/**
	 * Usado pra adicionar uma nova linha.
	 * 
	 * @param titulo nome
	 */
	public void adicionarLinha( String titulo ) {
		matriz.setQLinhas( 1 );
		matriz.getTituloLinha().add( titulo );
		matriz.getLinhas().add( new LinkedList< String >() );
		for( int l = 0; l < matriz.getQColunas(); l++ ){
			matriz.getLinhas().getLast().add( "40" );
		}
		
		for( int i = 0; i < getQLinhas() - 1; i++ ){
			for( int j = 1; j < getQColunas(); j++ ){
				String dado = getDadoMatriz( i, j );
				if( dado.equals( "20" ) ){
					setDadoMatriz( 0, i, j );
				} else if( dado.equals( "2" ) ){
					setDadoMatriz( 1, i, j );
				} else if( dado.equals( "30" ) ){
					setDadoMatriz( 20, i, j );
				} else if( dado.equals( "3" ) ){
					setDadoMatriz( 2, i, j );
				} else if( dado.equals( "40" ) ){
					setDadoMatriz( 30, i, j );
				} else if( dado.equals( "4" ) ){
					setDadoMatriz( 3, i, j );
				}
			}
		}
	}
	
	/**
	 * Usado para alterar a posição de uma coluna.
	 * 
	 * @param de índice
	 * @param para índice
	 */
	public void alterarPosicaoColuna( int de, int para ) {
		// System.out.println( "de " + de + "       para " + para );
		String s = "";
		for( int i = 0; i < matriz.getQLinhas(); i++ ){
			s = matriz.getLinha( i ).remove( de );
			matriz.getLinha( i ).add( para, s );
		}
		s = matriz.getTituloColuna().remove( de );
		matriz.getTituloColuna().add( para, s );
	}
	
	/**
	 * Usado para alterar a posição de uma linha.
	 * 
	 * @param de índice
	 * @param para índice
	 */
	public void alterarPosicaoLinha( int de, int para ) {
		LinkedList l = matriz.getLinhas().remove( de );
		matriz.getLinhas().add( para, l );
		
		String g = matriz.getTituloLinha().remove( de );
		matriz.getTituloLinha().add( para, g );
	}
	
	/**
	 * Usado para destacar os elementos que tenham relação com o objeto selecionado atualmente.
	 * 
	 * @param elementos nomes dos elementos
	 * @param tipo se é linha ou coluna
	 * @return lista de elementos
	 */
	public LinkedList< String > destacarElementos( LinkedList< String > elementos, String tipo ) {
		LinkedList< String > l = new LinkedList< String >();
		int elemento = -1;
		try{
			if( tipo.equalsIgnoreCase( "linha" ) ){
				for( int i = 0; i < getQLinhas(); i++ ){
					if( getTituloLinha( i ).startsWith( "|||" ) ) return l;
				}
				
				boolean boleta = false;
				for( String nome1 : elementos )
					for( int x = 0; x < getQLinhas(); x++ )
						if( getTituloLinha( x ).equals( nome1 ) ) boleta = true;
				
				if( boleta ){
					for( String nome : elementos ){
						for( int i = 0; i < getQLinhas(); i++ )
							if( getTituloLinha( i ).equals( nome ) ) elemento = i;
						
						setTituloLinha( elemento, "|||" + getTituloLinha( elemento ) );
						
						for( int i = 1; i < getQColunas(); i++ ){
							int da = Integer.parseInt( getDadoMatriz( elemento, i ) );
							if( da == 1 || da == 2 || da == 3 || da == 4 ){
								l.add( getTituloColuna( i ) );
								setTituloColuna( i, "|||" + getTituloColuna( i ) );
							}
						}
					}
				}
				
			} else if( tipo.equalsIgnoreCase( "coluna" ) ){// --------------------------coluna
				for( int i = 0; i < getQColunas(); i++ ){
					if( getTituloColuna( i ).startsWith( "|||" ) ) return l;
				}
				
				boolean boleta = false;
				for( String nome1 : elementos )
					for( int x = 0; x < getQColunas(); x++ )
						if( getTituloColuna( x ).equals( nome1 ) ) boleta = true;
				
				if( boleta ){
					for( String nome : elementos ){
						for( int i = 0; i < getQColunas(); i++ )
							if( getTituloColuna( i ).equals( nome ) ) elemento = i;
						
						setTituloColuna( elemento, "|||" + getTituloColuna( elemento ) );
						
						for( int i = 0; i < getQLinhas(); i++ ){
							int da = Integer.parseInt( getDadoMatriz( i, elemento ) );
							if( da == 1 || da == 2 || da == 3 || da == 4 ){
								l.add( getTituloLinha( i ) );
								setTituloLinha( i, "|||" + getTituloLinha( i ) );
							}
						}
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * @param linha índice
	 * @param coluna índice
	 * @return dado da matriz
	 */
	public String getDadoMatriz( int linha, int coluna ) {
		if( coluna == 0 ) return getTituloLinha( linha );
		return matriz.getLinha( linha ).get( coluna );
		
	}
	
	/**
	 * Retorna o DadoMatriz.
	 * 
	 * @return DadoMatriz desta matriz
	 */
	public DadosMatriz getDadosMatriz() {
		return matriz;
	}
	
	/**
	 * Retorna o nome da matriz.
	 * 
	 * @return nome
	 */
	public String getNomeMatriz() {
		return matriz.getNomeMatriz();
	}
	
	/**
	 * Método usado para buscar a quantidade de colunas que a matriz possui.
	 * 
	 * @return a quantidade de colunas.
	 */
	public int getQColunas() {
		return matriz.getQColunas();
	}
	
	/**
	 * Método usado para buscar a quantidade de linhas que a matriz possui.
	 * 
	 * @return a quantidade de linhas.
	 */
	public int getQLinhas() {
		return matriz.getQLinhas();
	}
	
	/**
	 * Retorna o título de uma coluna.
	 * 
	 * @param index índice
	 * @return título
	 */
	public String getTituloColuna( int index ) {
		return matriz.getTituloColuna( index );
	}
	
	/**
	 * Retorna o título de uma linha
	 * 
	 * @param index índice
	 * @return título
	 */
	public String getTituloLinha( int index ) {
		return matriz.getTituloLinha().get( index );
	}
	
	/**
	 * Remove uma coluna.
	 * 
	 * @param index índice
	 */
	public void removeColuna( int index ) {
		matriz.setQColunas( -1 );
		matriz.getTituloColuna().remove( index );
		
		for( int i = 0; i < matriz.getQLinhas(); i++ ){
			matriz.getLinha( i ).remove( index );
		}
	}
	
	/**
	 * Usado para remover uma linha.
	 * 
	 * @param index índice
	 */
	public void removeLinha( int index ) {
		matriz.setQLinhas( -1 );
		matriz.getLinhas().remove( index );
		matriz.getTituloLinha().remove( index );
	}
	
	/**
	 * Usado para resetar o destaque da matriz.
	 */
	public void resetarDestaque() {
		
		try{
			for( int i = 0; i < getQLinhas(); i++ ){
				if( getTituloLinha( i ).startsWith( "|||" ) ) setTituloLinha( i, getTituloLinha( i ).replace( "|||", "" ) );
			}
			for( int i = 1; i < getQColunas(); i++ ){
				if( getTituloColuna( i ).startsWith( "|||" ) ) setTituloColuna( i, getTituloColuna( i ).replace( "|||", "" ) );
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * Usado para setar um dado em uma matriz.
	 * 
	 * @param valor valor a ser colocado
	 * @param linha índice
	 * @param coluna índice
	 */
	public void setDadoMatriz( int valor, int linha, int coluna ) {
		matriz.getLinha( linha ).set( coluna, valor + "" );
	}
	
	/**
	 * Usado para setar o nome da matriz.
	 * 
	 * @param nome nome da matriz
	 * @return estatus da operação
	 */
	public String setNomeMatriz( String nome ) {
		String s = "ok";
		try{
			matriz.setNomeMatriz( nome );
		} catch( Exception e ){
			s = "erro";
		}
		return s;
	}
	
	/**
	 * Usado pra setar o título de uma coluna.
	 * 
	 * @param index índice
	 * @param titulo nome
	 */
	public void setTituloColuna( int index, String titulo ) {
		matriz.setTituloColuna( index, titulo );
		
	}
	
	/**
	 * Usado pra colocar o título de uma coluna.
	 * 
	 * @param index índice
	 * @param titulo nome
	 */
	public void setTituloLinha( int index, String titulo ) {
		matriz.getTituloLinha().set( index, titulo );
	}
	
	/**
	 * Ordena linhas em ordem alfabética ou alfabético-inverso.
	 */
	public void ordenarLinhas() {
		boolean mak = true;
		boolean vaibe = true;
		try{
			while( mak ){ // Bubble sort do capeta
				mak = false;
				for( int i = 0; i < matriz.getQLinhas() - 1; i++ ){
					int com = matriz.getTituloLinha().get( i ).compareToIgnoreCase( matriz.getTituloLinha().get( i + 1 ) );
					if( com > 0 ){
						alterarPosicaoLinha( i, i + 1 );
						vaibe = false;
						mak = true;
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
		mak = true;
		if( vaibe ){
			while( mak ){ // Alfabético - inverso
				mak = false;
				for( int i = 0; i < matriz.getQLinhas() - 1; i++ ){
					int com = matriz.getTituloLinha().get( i ).compareToIgnoreCase( matriz.getTituloLinha().get( i + 1 ) );
					if( com < 0 ){
						alterarPosicaoLinha( i, i + 1 );
						mak = true;
					}
				}
			}
		}
	}
	
	/**
	 * Ordena colunas em ordem alfabética ou alfabético-inverso.
	 */
	public void ordenarColunas() {
		boolean mak = true;
		boolean vaibe = true;
		
		try{
			while( mak ){// Bubblesort do capeta II a Missão
				mak = false;
				for( int i = 1; i < matriz.getQColunas() - 1; i++ ){
					int com = matriz.getTituloColuna( i ).compareToIgnoreCase( matriz.getTituloColuna( i + 1 ) );
					if( com > 0 ){
						alterarPosicaoColuna( i, i + 1 );
						vaibe = false;
						mak = true;
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
		mak = true;
		if( vaibe ){
			while( mak ){
				mak = false;
				for( int i = 1; i < matriz.getQColunas() - 1; i++ ){
					int com = matriz.getTituloColuna( i ).compareToIgnoreCase( matriz.getTituloColuna( i + 1 ) );
					if( com < 0 ){
						alterarPosicaoColuna( i, i + 1 );
						mak = true;
					}
				}
			}
		}
	}
}
