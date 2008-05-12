
import java.util.LinkedList;

public class Matriz {
    int colunas = 0;
    int linhas = 0;
    LinkedList<String> tituloLinha;
    LinkedList<String> tituloColuna;
    LinkedList<LinkedList> linha;

    public Matriz() {
        tituloLinha = new LinkedList<String>();
        tituloColuna = new LinkedList<String>();

        linha = new LinkedList<LinkedList>();



    }

    public void setDadoMatriz( int linha, int coluna ) {
    }

    public void addLinha( String titulo ) {
        linhas++;
        linha.add( new LinkedList<Integer>() );
        for ( int i = 0; i < colunas; i++ ) {
            linha.getFirst().add( 0 );
        }
        tituloLinha.add( titulo );
    }

    public void removeLinha( int index ) {
        linhas--;
        linha.remove( index );
        tituloLinha.remove( index );
    }
    
    public void alterarPosicaoLinha(int de, int para){
        
    }

    public void mudarTituloLinha( int index, String titulo ) {
        
    }
    
    public void addColuna( String titulo ) {
        colunas++;
        
        tituloColuna.add( titulo );
    }

    public void removeColuna( int index ) {
        colunas--;
        
        tituloColuna.remove( index );
    }
    
    public void alterarPosicaoColuna(int de, int para){
        
    }

    public void mudarTituloColuna( int index, String titulo ) {
        
    }
    
    
    
    
    
}
