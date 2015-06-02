package nz.co.xingsoft.memribox.server.ws.resources;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.ws.util.RestResourceContext;

import org.springframework.stereotype.Service;

@Service
public abstract class RestResource {

    @Inject
    protected RestResourceContext resourceUtil;

    @Inject
    protected ErrorHandlerResource errorHandlerResource;

    public String getCurrentUsername() {

        return resourceUtil.getCurrentUsername();
    }
}
