import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.LoggerFactory;

import javax.naming.Reference;
import java.net.URL;
import java.security.ProtectionDomain;

public class JServer {

    /**
     * logger
     */
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(JServer.class);

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getProperty("port", "8080"));
        Server embed_server = new Server(port);

        org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(embed_server);
        // this line sets up the server to pick up JNDI related configurations in override-web.xml. This is a direct copy of the documentation.
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");


        ProtectionDomain domain = JServer.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        logger.debug("[custom] "+location.toExternalForm());
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/webapp");
        webapp.setLogUrlOnStart(true);
        webapp.setParentLoaderPriority(true);
        webapp.setWar(location.toExternalForm());
        webapp.setDescriptor("WEB-INF/web.xml");
        webapp.setOverrideDescriptor("WEB-INF/override-web.xml");
        webapp.prependServerClass("-org.eclipse.jetty.servlet.,-org.eclipse.jetty.server.");
        webapp.setServer(embed_server);
        embed_server.setHandler(webapp);
        new org.eclipse.jetty.plus.jndi.Resource(webapp,"BeanManager", new Reference("javax.enterprise.inject.spi.BeanManager","org.jboss.weld.resources.ManagerObjectFactory",null));
        embed_server.start();
        embed_server.join();
    }
}
