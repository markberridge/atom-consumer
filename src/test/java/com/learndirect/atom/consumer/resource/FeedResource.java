package com.learndirect.atom.consumer.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.learndirect.atom.consumer.view.View;

@Path("/atom")
@Produces(MediaType.APPLICATION_ATOM_XML)
public class FeedResource {

    @GET
    @Path("/recent")
    public View recent() {
        return entries_6_8();
    }

    @GET
    @Path("/6,8")
    public View entries_6_8() {
        return new View("6,8.ftl");
    }

    @GET
    @Path("/3,5")
    public View entries_3_5() {
        return new View("3,5.ftl");
    }

    @GET
    @Path("/0,2")
    public View entries_0_2() {
        return new View("0,2.ftl");
    }
}
