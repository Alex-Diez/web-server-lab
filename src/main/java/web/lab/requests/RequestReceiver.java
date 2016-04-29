package web.lab.requests;

import web.lab.requests.handlers.RequestHandlerFactory;
import web.lab.response.Response;

public class RequestReceiver {

    private final RequestHandlerFactory factory;

    public RequestReceiver() {
        factory = new RequestHandlerFactory();
    }

    public Response handle(Request request) {
        return factory.createHandler(request).handle();
    }

}
