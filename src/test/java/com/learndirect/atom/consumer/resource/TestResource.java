package com.learndirect.atom.consumer.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Produces(MediaType.TEXT_PLAIN)
public class TestResource {

    @GET
    public String go() {
        return "SUCCESS";
    }
}
