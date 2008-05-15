package persistencia;

import java.util.LinkedList;

public class Matriz {
    private int colunas = 1;
    private int linhas = 0;
    private String nome;
    private LinkedList<String> tituloLinha;
    private LinkedList<String> tituloColuna;
    private LinkedList<LinkedList> linha;

    public Matriz() {
        tituloLinha = new LinkedList<String>();
        tituloColuna = new LinkedList<String>();
        linha = new LinkedList<LinkedList>();
    }

    public Matriz( String nome ) {
        tituloLinha = new LinkedList<String>();
        tituloColuna = new LinkedList<String>();
        linha = new LinkedList<LinkedList>();
        this.nome = nome;
        tituloColuna.add( "" );
        addLinha( "Linha 01" );
        addLinha( "Linha 02" );
        addLinha( "Linha 03" );
        addLinha( "Linha 04" );
        addColuna( "Coluna 01" );
        addColuna( "Coluna 02" );
        addColuna( "Coluna 03" );
        addColuna( "Coluna 04" );


        for ( int l = 0; l < linhas; l++ ) {

            System.out.println( l + ":" + (linha.get( l ).size() -1) );


        }
    }

    public void setDadoMatriz( int valor, int linha, int coluna ) {
        this.linha.get( linha ).set( coluna, valor );
    }

    public String getDadoMatriz( int linha, int coluna ) {
        //  System.out.println( linha +"::"+coluna );
        switch ( coluna ) {
            case 0:
                return getTituloLinha( linha );
            default:
                return this.linha.get( linha ).get( coluna ).toString();
        }
    }

    public void addLinha( String titulo ) {
        linhas++;
        tituloLinha.add( titulo );
        linha.add( new LinkedList<Integer>() );
        for ( int l = 0; l < colunas; l++ ) {
            linha.getLast().add( 0 );
        }
    }

    public void removeLinha( int index ) {
        linhas--;
        linha.remove( index );
        tituloLinha.remove( index );
    }

    public void alterarPosicaoLinha( int de, int para ) {
        LinkedList l = linha.remove( de );
        linha.add( para, l );
    }

    public void mudarTituloLinha( int index, String titulo ) {
        tituloLinha.set( index, titulo );
    }

    public String getTituloLinha( int index ) {
        return tituloLinha.get( index );
    }

    public void addColuna( String titulo ) { //------------------------------
        colunas++;
        tituloColuna.add( titulo );

        for ( int i = 0; i < linhas; i++ ) {
            linha.get( i ).add( 0 );
        }
    }

    public void removeColuna( int index ) {
        colunas--;
        tituloColuna.remove( index );

        for ( int i = 0; i < linha.size(); i++ ) {
            linha.get( i ).remove( index );
        }
    }

    public void alterarPosicaoColuna( int de, int para ) {
        String s = "";
        for ( int i = 0; i < linha.size(); i++ ) {
            s = ( String ) linha.get( i ).remove( de );
            linha.get( i ).add( para, s );
        }
    }

    public void mudarTituloColuna( int index, String titulo ) {
        tituloColuna.set( index, titulo );

    }

    public String getTituloColuna( int index ) {
        return tituloColuna.get( index );
    }

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public int getColunas() {
        return colunas;
    }

    public int getLinhas() {
        return linhas;
    }
}










