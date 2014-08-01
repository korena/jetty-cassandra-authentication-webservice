package com.app.panel;

import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;


/**
 * Created by korena on 7/25/14.
 */
@SessionScoped
public class Injectable implements Serializable {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(Injectable.class);


    public Injectable(){
    }

    public String method(){
        logger.debug("\nThe injectable object says:\n");
        return "Servlet injection successful.";
    }

}
