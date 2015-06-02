package nz.co.xingsoft.memribox.server.application.filter;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class RESTServiceFilter
        implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RESTServiceFilter.class);

    @Context
    private ServletContext scontext; // necessary for accessing resources on
                                     // classpath which in my case was the XML
                                     // Schema.

    @Override
    public ContainerRequest filter(final ContainerRequest containerRequest) {

        // Retrieve name of REST Service currently being accessed
        final String serviceMethod = containerRequest.getPath().toString().substring(containerRequest.getPath().toString().indexOf("/") + 1).trim();

        LOGGER.info("request service method: {}", serviceMethod);

        final String authenticationHeader = containerRequest.getHeaderValue("AUTHENTICATION");

        return containerRequest;
    }

    @Override
    public ContainerResponse filter(final ContainerRequest request, final ContainerResponse response) {
        return response;
    }

}
