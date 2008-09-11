package persistencia;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class PersistenciaProjeto {
	public PersistenciaProjeto() {
	}
	
	public Projeto abrir( String nome ) {
		Projeto p = null;
		try{
			p = ( Projeto ) new XStream( new DomDriver() ).fromXML( new FileReader( "arquivos/" + nome ) );
		} catch( FileNotFoundException e ){
			e.printStackTrace();
		}
		return p;
	}
	
	public String salvar( Projeto projeto ) {
		String s = "ok";
		try{
			FileWriter file = new FileWriter( "arquivos/" + projeto.getNome() + ".trama" );
			
			XStream x = new XStream( new DomDriver() );
			x.toXML( projeto, file );
			file.close();
		} catch( IOException e ){
			e.printStackTrace();
			s = "erro";
		}
		
		return s;
	}
}
