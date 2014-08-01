/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dbConnect;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 *
 * @author korena
 */
public interface  ConnectionFactoryDX {   
    public abstract Session getSession();

    public abstract Cluster getCluster();

    public abstract void setCluster(Cluster cluster);

    public abstract void close();
}
