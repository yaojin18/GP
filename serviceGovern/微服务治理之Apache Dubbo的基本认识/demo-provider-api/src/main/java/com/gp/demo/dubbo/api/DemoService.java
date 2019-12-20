package com.gp.demo.dubbo.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/demo")
public interface DemoService {

	@GET
    @Path("/{name}")
	public String sayHello(@PathParam("name") String name);
}
