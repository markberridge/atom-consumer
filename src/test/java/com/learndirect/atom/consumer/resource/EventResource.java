package com.learndirect.atom.consumer.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.learndirect.atom.consumer.view.View;

@Path("/atom/event/{i}")
@Produces(MediaType.APPLICATION_ATOM_XML)
public class EventResource {

    @GET
    public View event(@PathParam("i") Integer i) {
        return new View(String.format("events/%s.ftl", i));
    }
}
