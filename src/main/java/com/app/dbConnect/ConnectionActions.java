/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dbConnect;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

/**
 *
 * @author korena
 */
public class ConnectionActions {  

    public static void connect(String node, Cluster cluster) {
        cluster = Cluster.builder()
                .addContactPoint(node)
                // .withSSL() // Uncomment if using client to node encryption
                .build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n",
                metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }

    }


    public static void close(Cluster cluster) {
   cluster.shutdown();
}    
}
