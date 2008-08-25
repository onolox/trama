package persistencia;

import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class PersistenciaProjeto {
	public PersistenciaProjeto() {
	}
	
	public Projeto abrir( String nome ) {
		Projeto p = null;
		return p;
	}
	
	public String salvar( Projeto projeto ) {
		String s = "ok";
		try{
			FileWriter file = new FileWriter( "arquivos/" + projeto.getNome() + ".xml" );
			
			XStream x = new XStream( new DomDriver() );
			x.toXML( projeto, file );
		} catch( IOException e ){
			e.printStackTrace();
			s = "erro";
		}
		
		return s;
	}
}
