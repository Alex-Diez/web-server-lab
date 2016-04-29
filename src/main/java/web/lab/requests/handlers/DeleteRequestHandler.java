package web.lab.requests.handlers;

import web.lab.listeners.DeleteListener;
import web.lab.requests.Request;
import web.lab.requests.ResourceManager;
import web.lab.response.Response;
import web.lab.response.Response.Builder;

import static web.lab.http.Header.REQUEST_URL;
import static web.lab.http.Status.NOT_FOUND;
import static web.lab.http.Status.OK;

class DeleteRequestHandler
        implements RequestHandlerFactory.RequestHandler {
    private final Request request;
    private final DeleteListener urlListener;
    private final DeleteListener resourceListener;
    private final ResourceManager manager;
    private final Builder builder;

    DeleteRequestHandler(
            Request request,
            DeleteListener urlListener,
            DeleteListener resourceListener,
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
        urlListener.onDelete(url);
        builder.setStatus(!manager.containsResource(url) ? NOT_FOUND : OK);
        resourceListener.onDelete(url);
        return builder.build();
    }
}
