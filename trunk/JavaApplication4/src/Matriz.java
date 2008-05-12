
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

    void setDadoMatriz( int linha, int coluna ) {
    }

    void addLinha( String titulo ) {
        linhas++;
        linha.add( new LinkedList<Integer>() );
        for ( int i = 0; i < colunas; i++ ) {
            linha.getFirst().add( 0 );
        }
        tituloLinha.add( titulo);
    }

    void removeLinha( int index ) {
        linhas--;
        linha.remove( index );
        tituloLinha.remove( index );
    }

    void mudarTituloLinha( int index, String titulo ) {
        
        
    }
    

    void addColuna( String tiutlo ) {
        colunas++;
    }

    void removeColuna( int index ) {
        colunas--;
    }
}
