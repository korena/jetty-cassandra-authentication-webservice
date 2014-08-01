package com.app.binding;


import com.app.dao.UsersDao;
import com.app.dao.UsersDaoImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


public class InternalsBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UsersDaoImpl.class).to(UsersDao.class);
//        bind(Authentication.class).to(Authentication.class);
//        bind(ConnectionFactoryDXImpl.class).to(ConnectionFactoryDXImpl.class);
    }
}