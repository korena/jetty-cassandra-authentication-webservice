/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.dbConnect;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 *
 * @author korena
 * 
 * @remark You need to build the project as a whole before attempting to run this file.
 */
public class ClusterTest {
    
    static ConnectionFactoryDX connection;
    static Session session;
    
    public static void main(String[] args){
    
    connection = new ConnectionFactoryDXImpl();
    session = connection.getSession();
                try{
        ResultSet firstFetch = session.execute("SELECT uid FROM templates.user_by_email WHERE email = 'john@email.com';");

        Row retrieved = firstFetch.one();
        System.out.println("the fetched UUID is: "+ retrieved.getUUID("uid").toString()+"\n");
                }catch(Exception ex){
                ex.printStackTrace();
                }
    }
}