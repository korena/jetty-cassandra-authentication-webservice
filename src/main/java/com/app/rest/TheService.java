package com.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by korena on 30/07/2014.
 */

@Path("Service")
public class TheService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Path("/{param}")
    @Produces(MediaType.TEXT_HTML)
    public String getStatus(@PathParam("param") String msg) {
        String output = "<span>ERROR:unsupported request</span>";
        if (msg.equals("usrAuth")) {
            output = "<span>user authentication up and running ...</span>";
        }
        return output;
    }


}
