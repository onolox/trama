package negocio.plugin;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarFile;

public class LerDoModelo {
    private final String DIRBASE = "plugins/";
    private LinkedList<String> list;
    PluginInterface pl = null;

    public String getNomes() {
        list = getJars();

        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public LinkedList<String> getExtensoes() {
        list = getJars();


        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public LinkedList<String> getObjetos( String arquivo ) {
        list = getJars();

        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public LinkedList<String> getJars() { // retorna uma lista de jars no diretorio

        LinkedList<String> l = new LinkedList<String>();
        File f = new File( DIRBASE );
        for ( File fi : f.listFiles() ) {
            String s = fi.toString();
            if ( s.contains( ".jar" ) ) {
                l.add( s );
            }
        }
        return l;
    }

    public PluginInterface getClasseJar( String arquivo ) {
        URLClassLoader load;
        try {
            URL url = new URL( "jar", "", "file:" + arquivo + "!/" );

            JarURLConnection jar = ( JarURLConnection ) url.openConnection(); // abro o url pro jar

            JarFile j = jar.getJarFile();
            Enumeration arq = j.entries(); // pego os arquivos que estão no jar

            load = new URLClassLoader( new URL[]{ url } ); // pra ler a classe do jar

            while ( arq.hasMoreElements() ) {
                Object object = arq.nextElement();

                if ( object.toString().contains( ".class" ) &&
                        !object.toString().contains( "PluginInterface.class" ) ) {

                    Class cl = load.loadClass( object.toString().replace( ".class", "" ) );

                    pl = ( PluginInterface ) cl.newInstance();
                }
            }
        } catch ( MalformedURLException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        } catch ( InstantiationException e ) {
            e.printStackTrace();
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }
        return pl;
    }
}
