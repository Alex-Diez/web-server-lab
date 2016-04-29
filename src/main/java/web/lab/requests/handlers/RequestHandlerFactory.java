package web.lab.requests.handlers;

import web.lab.http.Method;
import web.lab.logger.FileSystem;
import web.lab.logger.URLLogger;
import web.lab.requests.Request;
import web.lab.requests.ResourceActionListener;
import web.lab.requests.UrlActionListener;
import web.lab.response.Response;

import java.util.HashMap;

import static web.lab.http.Header.REQUEST_METHOD;

public class RequestHandlerFactory {

    private final UrlActionListener urlListener  = new UrlActionListener(new URLLogger(new FileSystem()));
    private final ResourceActionListener resourcesManager = new ResourceActionListener(new HashMap<>());

    private Method retrieveHttpMethod(Request request) {
        return Method.valueOf(request.getHeader(REQUEST_METHOD));
    }

    public RequestHandler createHandler(Request request) {
        switch (retrieveHttpMethod(request)) {
            case PUT:
                return new PutRequestHandler(request, urlListener, resourcesManager, resourcesManager);
            case DELETE:
                return new DeleteRequestHandler(request, urlListener, resourcesManager, resourcesManager);
            case POST:
                return new PostRequestHandler(request, urlListener, resourcesManager, resourcesManager);
            case GET:
                return new GetRequestHandler(request, resourcesManager);
            default:
                throw new RuntimeException();
        }
    }

    public interface RequestHandler {

        Response handle();
    }
}
