package com.app.rest;

import com.app.dao.UsersDao;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * http://localhost:8081/webapp/internal/api/authentication/authenticate?subject=secondRobot@roboworld.com&password=secondTestUser-68
 * Root resource (exposed at "authentication" path)
 */
@Path("authentication")
public class Authentication {
    @Inject
    UsersDao userDao;

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

    @GET
    @Path("/authenticate")
//    @Produces(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String authenticateSubject(@QueryParam("subject") String username, @QueryParam("password") String password) {
        String valid = "<span>false</span>";
        // call the userDao here ... connecting to cassandra ...
        if (userDao.authenticate(username, password) != null) {
            valid = "authenticated";
        }
        return valid;
    }
}
