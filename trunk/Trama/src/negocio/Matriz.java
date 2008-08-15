package negocio;

import persistencia.*;
import java.util.LinkedList;

public class Matriz {
    private DadosMatriz matriz;

    public Matriz() {
        matriz = new DadosMatriz();
    }

    public Matriz( String nome ) {
        matriz = new DadosMatriz();

        addLinha( "Linha 01" );
        addLinha( "Linha 02" );
        addLinha( "Linha 03" );
        addLinha( "Linha 04" );
        addLinha( "Linha 05" );
        addLinha( "Linha 06" );
        addLinha( "Linha 07" );
        addLinha( "Linha 08" );
        addColuna( "Coluna 01" );
        addColuna( "Coluna 02" );
        addColuna( "Coluna 03" );
        addColuna( "Coluna 04" );
        addColuna( "Coluna 05" );
        addColuna( "Coluna 06" );
        addColuna( "Coluna 07" );
        addColuna( "Coluna 08" );
        
        setDadoMatriz( 1, 2, 2 );
        setDadoMatriz( 1, 3, 4 );
        setDadoMatriz( 2, 5, 7 );

       // for ( int l = 0; l < matriz.getQLinhas(); l++ ) {
       //     System.out.println( l + ":" + ( matriz.getLinha( l ).size() - 1 ) );
       //}
    }

    public void setDadoMatriz( int valor, int linha, int coluna ) {
        matriz.getLinha( linha - 1 ).set( coluna, valor + "" );
    }

    public String getDadoMatriz( int linha, int coluna ) {
        //  System.out.println( linha +"::"+coluna );
        switch ( coluna ) {
            case 0:
                return getTituloLinha( linha );
            default:
                return matriz.getLinha( linha ).get( coluna ).toString();
        }
    }

    public void addLinha( String titulo ) {
        matriz.setQLinhas( 1 );
        matriz.getTituloLinha().add( titulo );
        matriz.getLinhas().add( new LinkedList<String>() );
        for ( int l = 0; l < matriz.getQColunas(); l++ ) {
            matriz.getLinhas().getLast().add( "0" );
        }
    }

    public void removeLinha( int index ) {
        matriz.setQLinhas( -1 );
        matriz.getLinhas().remove( index );
        matriz.getTituloLinha().remove( index );
    }

    public void alterarPosicaoLinha( int de, int para ) {
        LinkedList l = matriz.getLinhas().remove( de );
        matriz.getLinhas().add( para, l );
    }

    public void setTituloLinha( int index, String titulo ) {
        matriz.getTituloLinha().set( index, titulo );
    }

    public String getTituloLinha( int index ) {
        return matriz.getTituloLinha().get( index );
    }

    public void addColuna( String titulo ) { //------------------------------

        matriz.setQColunas( 1 );
        matriz.getTituloColuna().add( titulo );

        for ( int i = 0; i < matriz.getQLinhas(); i++ ) {
            matriz.getLinha( i ).add( "0" );
        }
    }

    public void removeColuna( int index ) {
        matriz.setQColunas( -1 );
        matriz.getTituloColuna().remove( index );

        for ( int i = 0; i < matriz.getQLinhas(); i++ ) {
            matriz.getLinha( i ).remove( index );
        }
    }

    public void alterarPosicaoColuna( int de, int para ) {
        String s = "";
        for ( int i = 0; i < matriz.getQLinhas(); i++ ) {
            s = ( String ) matriz.getLinha( i ).remove( de );
            matriz.getLinha( i ).add( para, s );
        }
    }

    public void setTituloColuna( int index, String titulo ) {
        matriz.setTituloColuna( index, titulo );

    }

    public String getTituloColuna( int index ) {
        return matriz.getTituloColuna( index );
    }

    public int getQColunas() {
        return matriz.getQColunas();
    }

    /**
     * NÃºmero positivo aumenta, negativo diminui.
     * @param colunas Quantidade para aumentar ou diminuir.
     */
    public int getQLinhas() {
        return matriz.getQLinhas();
    }
}

