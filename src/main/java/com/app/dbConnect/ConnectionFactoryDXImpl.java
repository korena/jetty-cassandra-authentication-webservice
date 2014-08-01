/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dbConnect;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import java.io.Serializable;

/**
 *
 * @author korena
 */
public class ConnectionFactoryDXImpl implements ConnectionFactoryDX,Serializable {
    

    private Cluster cluster;
    private final Session session;   // thread-safe !!
    
    public ConnectionFactoryDXImpl(){
     cluster = Cluster.builder()
                .addContactPoint("192.168.0.123")
                // .withSSL() // Uncomment if using client to node encryption
                .build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n",
                metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }
        session = cluster.connect();
    }
    
    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public Cluster getCluster() {
        return cluster;
    }

    @Override
    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
      
    @Override
    public void close() {
   cluster.shutdown();
} 
    
    
    
    
}
