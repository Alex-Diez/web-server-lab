package web.lab.requests.handlers;

import web.lab.listeners.UpdateListener;
import web.lab.requests.Request;
import web.lab.requests.ResourceManager;
import web.lab.response.Response;
import web.lab.response.Response.Builder;

import static web.lab.http.Header.REQUEST_URL;
import static web.lab.http.Status.NOT_FOUND;
import static web.lab.http.Status.OK;

class PostRequestHandler
        implements RequestHandlerFactory.RequestHandler {
    private final Request request;
    private final UpdateListener urlListener;
    private final UpdateListener resourceListener;
    private final ResourceManager manager;
    private final Builder builder;

    PostRequestHandler(
            Request request,
            UpdateListener urlListener,
            UpdateListener resourceListener,
            ResourceManager manager) {
        this.request = request;
        this.urlListener = urlListener;
        this.resourceListener = resourceListener;
        this.manager = manager;
        builder = new Builder();
    }

    @Override
    public Response handle() {
        String url = request.getHeader(REQUEST_URL);
        if (manager.containsResource(url)) {
            urlListener.onUpdate(url);
            resourceListener.onUpdate(url);
        }
        return builder.setStatus(!manager.containsResource(url) ? NOT_FOUND : OK).build();
    }
}
