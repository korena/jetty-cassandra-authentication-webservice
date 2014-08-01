package com.app.rest;

import com.app.dao.UsersDao;
import com.app.model.Users;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * http://localhost:8080/webapp/api/authentication/authenticate?subject=secondRobot@roboworld.com&password=secondTestUser-68
 * Root resource (exposed at "authentication" path)
 */
@Path("authentication")
public class Authentication {


    @Inject
    UsersDao userDao;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Jersey Resources Exposed!!";
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
    @Produces(MediaType.APPLICATION_JSON)
    public String authenticateSubject(@QueryParam("subject") String username, @QueryParam("password") String password) {
        String valid = "{\"name\" : \"Wrong credentials, Not Authenticated!!\"}";

        // call the userDao here ... connecting to cassandra ...
        Users authUser = null;
        // paranoid tests, I dont think they can ever be null ...
        if (username != null && password != null) {
            if (!username.isEmpty() && !password.isEmpty()) {
                authUser = userDao.authenticate(username, password);
            } else {
                valid = "{\"name\" : \"An empty field detected\"}";
            }
        }
        if (null != authUser) {
//           One would properly build a json object using json.simple, jackson, Gson or the likes, but well just build it as a string
            valid = "{\"name\":\"" + authUser.getFname() + " " + authUser.getLname() + " Authenticated!\"}";
        }
        return valid;
    }
}
