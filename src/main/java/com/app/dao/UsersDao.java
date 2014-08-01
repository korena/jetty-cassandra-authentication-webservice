/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;


import com.app.model.Users;
import java.util.UUID;

/**
 *
 * @author moaz
 */
public interface UsersDao {

    
    public Users authenticate(String username, String password);

    public void createUser(Users user);

    public void deleteUser(Users user);

    public void UpdateUser(Users oldUser, Users newUser);

    public Users getUserByEmail(String email);

    public Users getUserByPass(UUID uid, String passwd);

    public Users getUserByPost(UUID vid);
}
