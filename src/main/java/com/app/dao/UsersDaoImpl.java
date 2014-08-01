package com.app.dao;


import com.app.dbConnect.ConnectionFactoryDX;
import com.app.dbConnect.ConnectionFactoryDXImpl;
import com.app.hash.PasswordHash;
import com.app.model.Users;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author moaz
 */

@ApplicationScoped
public class UsersDaoImpl implements UsersDao, Serializable {

    ConnectionFactoryDX connection;
    Session session;
    
    @Inject
    UsersDaoImpl(){
        // this was needed for the connection and session fields to be populated,
        // cdi bean manager cant scan datastax classes for some reason that I do not care about ...
    this.connection = new ConnectionFactoryDXImpl();
    session = connection.getSession();
    }
            /**
     * logger
     */
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(UsersDaoImpl.class);

    /**
     *
     * @param user
     */
    
    @Override
    public void createUser(Users user) {
        PreparedStatement insertIntoUsers = session.prepare(
                "INSERT INTO templates.users "
                + "(uid,passwd,questions,emails,ualias, fname, lname,address) "
                + "VALUES (?, ?, ?, ?, ?, ?, ? , ? );");
        BoundStatement boundStatement = new BoundStatement(insertIntoUsers);
        session.execute(boundStatement.bind(
                user.getUid(),
                user.getPassword(),   // should be hashed at some point, but I dont care.
                user.getQuestions(),
                user.getEmails(),
                user.getUalias(),
                user.getFname(),
                user.getLname(),
                user.getAddress()));

        session.execute("INSERT INTO templates.user_by_email"
                + "(email,uid) VALUES(" + user.getEmails().get(0) + "," + user.getUid() + ");");  // user can use only one email for login.
    }

    /**
     *
     * @param user
     */
    @Override
    public void deleteUser(Users user) {
        // you should always use prepared statements, executing strings with variable 
        // string inputs is not a good thing to do, but I will do it here.
        session.execute("DELETE FROM templates.users WHERE uid =" + user.getUid() + ";");
        session.execute("DELETE FROM templates.user_by_email WHERE email =" + user.getEmails() + ";");
    }

    /**
     *
     * @param oldUser
     * @param newUser
     */
    @Override
    public void UpdateUser(Users oldUser, Users newUser) {
//        fields should be checked for null values ... but you get the idea ...
        
        session.execute("UPDATE templates.users SET uid="
                + newUser.getUid()
                + ",ualias=" + newUser.getUalias()
                + ",fname=" + newUser.getFname()
                + ",lname=" + newUser.getLname()
                + ",address=" + newUser.getAddress()
                + ",emails=" + newUser.getEmails()
                + "WHERE uid=" + oldUser.getUid());
    }

    /**
     *
     * @param email
     * @return
     */
    @Override
    public Users getUserByEmail(String email) {
        logger.debug("getting user uid by email ... \n");
        ResultSet firstFetch = session.execute("SELECT uid FROM templates.user_by_email WHERE email = '" + email + "';");
        Row retrieved = firstFetch.one();
        logger.debug("the fetched UUID is: "+ retrieved.getUUID("uid").toString()+"\n");
        UUID uid = retrieved.getUUID("uid");
        return getUserById(uid);
    }

    /**
     *
     * @param pid
     * @return
     */
    @Override
    public Users getUserByPost(UUID pid) {

        ResultSet queryResult = session.execute("SELECT uid FROM templates.user_by_post WHERE pid=" + pid);
        UUID uid = UUID.fromString(queryResult.one().getString("uid"));
        return getUserById(uid);
    }

    /**
     *
     * @param uid
     * @return
     */
    public Users getUserById(UUID uid) {

        Users user = new Users();

        ResultSet queryResultFinal = session.execute("SELECT * FROM templates.users WHERE uid= "+uid+";");
        Row row = queryResultFinal.one();
        user.setUid(row.getUUID("uid"));
        user.setPassword(row.getString("passwd"));
        user.setQuestions(row.getMap("questions", String.class, String.class));
        user.setUalias(row.getString("ualias"));
        user.setFname(row.getString("fname"));
        user.setLname(row.getString("lname"));
        user.setAddress(row.getString("address"));
        user.setEmails(row.getList("emails", String.class));
        return user;
    }

    /**
     *
     * @param uid
     * @param passwd
     * @return
     */
    @Override
    public Users getUserByPass(UUID uid,String passwd) {
        ResultSet firstFetch = session.execute("SELECT * FROM templates.users WHERE uid =" + uid +"AND passwd='"+passwd+"';");
        try{
        Row retrieved = firstFetch.one();
        return getUserById(uid);
        }catch(Exception ex){
        ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Users authenticate(String username, String password) {

        Users user = getUserByEmail(username);
        if(user != null){
        try {
            logger.debug("got the user by email from cassandra ... \n");
            if (PasswordHash.validatePassword(password,user.getPassword())) {
                logger.debug("user validated successfully \n");
                return user;
            } else {
                return null;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(UsersDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return null;
    }

}