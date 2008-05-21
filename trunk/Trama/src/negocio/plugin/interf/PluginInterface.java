package negocio.plugin.interf;

import java.util.LinkedList;

public interface PluginInterface {
    public String getNome();

    public LinkedList<String> getExtensoes();

    public LinkedList<String> getObjetos( String arquivo );
}
