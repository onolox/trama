package negocio;

import java.util.LinkedList;

import persistencia.DadosMatriz;

public class Matriz {
	private DadosMatriz matriz;
	
	public Matriz( DadosMatriz matriz ) {
		this.matriz = matriz;
	}
	
	public void adicionarColuna( String titulo ) {
		matriz.setQColunas( 1 );
		matriz.getTituloColuna().add( titulo );
		
		for( int i = 0; i < matriz.getQLinhas(); i++ ){
			matriz.getLinha( i ).add( "0" );
		}
	}
	
	public void adicionarLinha( String titulo ) {
		matriz.setQLinhas( 1 );
		matriz.getTituloLinha().add( titulo );
		matriz.getLinhas().add( new LinkedList< String >() );
		for( int l = 0; l < matriz.getQColunas(); l++ ){
			matriz.getLinhas().getLast().add( "0" );
		}
	}
	
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
	
	public void alterarPosicaoLinha( int de, int para ) {
		LinkedList l = matriz.getLinhas().remove( de );
		matriz.getLinhas().add( para, l );
		
		String g = matriz.getTituloLinha().remove( de );
		matriz.getTituloLinha().add( para, g );
	}
	
	public LinkedList< String > destacarElementos( String nomeElemento, String tipo ) {
		return null;
	}
	
	public String getDadoMatriz( int linha, int coluna ) {
		switch( coluna ) {
			case 0:
				return getTituloLinha( linha );
			default:
				return matriz.getLinha( linha ).get( coluna ).toString();
		}
	}
	
	public DadosMatriz getDadosMatriz() {
		return null;
	}
	
	public String getNomeMatriz() {
		return matriz.getNomeMatriz();
	}
	
	public int getQColunas() {
		return matriz.getQColunas();
	}
	
	/**
	 * Numero positivo aumenta, negativo diminui.
	 * 
	 * @param colunas Quantidade para aumentar ou diminuir.
	 */
	public int getQLinhas() {
		return matriz.getQLinhas();
	}
	
	public String getTituloColuna( int index ) {
		return matriz.getTituloColuna( index );
	}
	
	public String getTituloLinha( int index ) {
		return matriz.getTituloLinha().get( index );
	}
	
	public void removeColuna( int index ) {
		matriz.setQColunas( -1 );
		matriz.getTituloColuna().remove( index );
		
		for( int i = 0; i < matriz.getQLinhas(); i++ ){
			matriz.getLinha( i ).remove( index );
		}
	}
	
	public void removeLinha( int index ) {
		matriz.setQLinhas( -1 );
		matriz.getLinhas().remove( index );
		matriz.getTituloLinha().remove( index );
	}
	
	public void setDadoMatriz( int valor, int linha, int coluna ) {
		matriz.getLinha( linha ).set( coluna, valor + "" );
	}
	
	public String setNomeMatriz( String nome ) {
		String s = "ok";
		try{
			matriz.setNomeMatriz( nome );
		} catch( Exception e ){
			s = "erro";
		}
		return s;
	}
	
	public void setTituloColuna( int index, String titulo ) {
		matriz.setTituloColuna( index, titulo );
		
	}
	
	public void setTituloLinha( int index, String titulo ) {
		matriz.getTituloLinha().set( index, titulo );
	}
	
	public void ordenarLinhas() {
		boolean mak = true;
		boolean vaibe = true;
		try{
			while( mak ){ // Bubble sort do capeta
				mak = false;
				for( int i = 0; i < matriz.getQLinhas() - 1; i++ ){
					int com = matriz.getTituloLinha().get( i ).compareToIgnoreCase( matriz.getTituloLinha().get( i + 1 ) );
					if( com >= 0 ){
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
					System.out.println( com );
					if( com < 0 ){
						alterarPosicaoLinha( i, i + 1 );
						mak = true;
					}
				}
			}
		}
	}
	
	public void ordenarColunas() {
		boolean mak = true;
		boolean vaibe = true;
		
		try{
			while( mak ){
				mak = false;
				for( int i = 1; i < matriz.getQColunas() - 1; i++ ){
					int com = matriz.getTituloColuna( i ).compareToIgnoreCase( matriz.getTituloColuna( i + 1 ) );
					if( com >= 0 ){
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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	
}
