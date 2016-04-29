package web.lab.requests.handlers;

import web.lab.requests.Request;
import web.lab.requests.ResourceManager;
import web.lab.response.Response;
import web.lab.response.Response.Builder;

import java.time.LocalDateTime;

import static web.lab.http.Header.IF_MODIFIED_SINCE;
import static web.lab.http.Header.REQUEST_URL;
import static web.lab.http.Status.NOT_FOUND;
import static web.lab.http.Status.NOT_MODIFIED;
import static web.lab.http.Status.OK;
import static web.lab.requests.Request.FORMATTER;

class GetRequestHandler
        implements RequestHandlerFactory.RequestHandler {
    private final Builder builder;
    private final Request request;
    private final ResourceManager manager;

    GetRequestHandler(Request request, ResourceManager manager) {
        this.request = request;
        this.manager = manager;
        builder = new Builder();
    }

    @Override
    public Response handle() {
        String url = request.getHeader(REQUEST_URL);
        if (!manager.containsResource(url)) {
            builder.setStatus(NOT_FOUND);
        }
        else {
            LocalDateTime time = manager.modificationTime(url);
            LocalDateTime ifModifiedSince = LocalDateTime.parse(
                    request.getHeader(IF_MODIFIED_SINCE),
                    FORMATTER
            );
            builder.setStatus(ifModifiedSince.compareTo(time) < 0 ? OK : NOT_MODIFIED);
        }
        return builder.build();
    }
}
