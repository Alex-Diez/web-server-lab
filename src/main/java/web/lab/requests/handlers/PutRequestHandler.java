package web.lab.requests.handlers;

import web.lab.listeners.AddListener;
import web.lab.requests.Request;
import web.lab.requests.ResourceManager;
import web.lab.response.Response;
import web.lab.response.Response.Builder;

import static web.lab.http.Header.REQUEST_URL;
import static web.lab.http.Status.CREATED;

class PutRequestHandler
        implements RequestHandlerFactory.RequestHandler {

    private final Response.Builder builder;
    private final Request request;
    private final AddListener urlListener;
    private final AddListener resourceListener;
    private final ResourceManager manager;

    PutRequestHandler(
            Request request,
            AddListener urlListener,
            AddListener resourceListener,
            ResourceManager manager) {
        this.request = request;
        this.urlListener = urlListener;
        this.manager = manager;
        this.resourceListener = resourceListener;
        builder = new Builder();
    }

    @Override
    public Response handle() {
        String url = request.getHeader(REQUEST_URL);
        urlListener.onAddUrl(url);
        resourceListener.onAddUrl(url);
        builder.setStatus(CREATED);
        return builder.build();
    }
}
