package persistencia;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Classe usada para persistir o projeto serializando o em XML. Também é usada para desserializar o XML de volta para uma entidade {@link Projeto}.<br>
 * Para fazer a serialização e desserialização é utilizada a biblioteca XSTream.
 * 
 * @author Fabio Marmitt
 */
public class PersistenciaProjeto {
	
	/**
	 * Usado para ler o arquivo XML de projeto e torná-lo um {@link Projeto} para poder ser usado novamente.
	 * 
	 * @param nome nome do arquivo
	 * @return instância de Projeto
	 */
	public Projeto abrir( String nome ) {
		Projeto p = null;
		try{
			p = ( Projeto ) new XStream( new DomDriver() ).fromXML( new FileReader( "arquivos/" + nome ) );
		} catch( FileNotFoundException e ){
			e.printStackTrace();
			p = null;
		}
		return p;
	}
	
	/**
	 * Usado para persistir o {@link Projeto} em XML.
	 * 
	 * @param projeto Projeto
	 * @return estatus da operação
	 */
	public String salvar( Projeto projeto ) {
		String s = "ok";
		try{
			FileWriter file = new FileWriter( "arquivos/" + projeto.getNome() + ".trama" );
			
			XStream x = new XStream( new DomDriver() );
			x.toXML( projeto, file );
			file.close();
		} catch( IOException e ){
			e.printStackTrace();
			s = "Erro na gravação do arquivo";
		}
		
		return s;
	}
}
