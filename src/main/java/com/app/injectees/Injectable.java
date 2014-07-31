package com.app.injectees;

import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;


/**
 * Created by korena on 7/25/14.
 */
@SessionScoped
public class Injectable implements Serializable {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(Injectable.class);

    String string = "I am not initialized yet.";

    public Injectable(){
    }

    public String method(){
        logger.debug("\nThe injectable object says:"+string+"\n");
        return "I am injected in an object that is then injected into the servlet that sent you this text.";
    }

}
