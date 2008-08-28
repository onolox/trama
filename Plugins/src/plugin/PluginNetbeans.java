package plugin;

import java.util.LinkedList;

import Interface.PluginInterface;

public class PluginNetbeans implements PluginInterface {
	private String DIRBASE = "arquivos/";
	
	@Override
	public LinkedList< String > getExtensoes() {
		return null;
	}
	
	@Override
	public String getNome() {
		return "Arquivos do Netbeans";
	}
	
	@Override
	public LinkedList< String > getObjetos( String arquivo ) {
		return null;
	}
	
}
