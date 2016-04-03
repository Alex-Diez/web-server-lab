package web.lab;

import java.time.LocalDateTime;

import static web.lab.HttpHeaders.IF_MODIFIED_SINCE;
import static web.lab.HttpHeaders.REQUEST_METHOD;
import static web.lab.HttpHeaders.REQUEST_URL;
import static web.lab.Request.FORMATTER;

public class RequestHandler {

    public static final String NEVER_MODIFIED_URL = "never-modified-url";
    public static final String HTML_HANDLER_URL = "html-handler";
    public static final String NON_EXISTED_URL = "non-existed-url";
    public static final String MODIFIABLE_URL = "modifiable-url";
    public static final String HTML_URL_HANDLER_CONTENT = "<!DOCTYPE html><html></html>";

//    private final URLResolver resolver;
    private final URLLogger logger = new URLLogger(new FileSystem()) {
        {
            add(NEVER_MODIFIED_URL);
            add(MODIFIABLE_URL);
        }
    };

    public RequestHandler() {
//        this.resolver = resolver;
    }

    public Response handle(Request request) {
        String url = request.getParameter(REQUEST_URL);
        Response.Builder builder = new Response.Builder();
        logger.getLastModificationTimeFor(url).ifPresent(
                (time) -> {
                    String requestMethod = request.getParameter(REQUEST_METHOD);
                    if ("POST".equals(requestMethod)) {
                        logger.update(url);
                    }
                    LocalDateTime ifModifiedSince = LocalDateTime.parse(request.getParameter(IF_MODIFIED_SINCE), FORMATTER);
                    int status = ifModifiedSince.compareTo(time) < 0 || "POST".equals(requestMethod) ? 200 : 304;
                    builder.setStatus(status);
                }
        );
//        Optional<URLHandler> urlHandler = resolver.getHandler(url);
//        urlHandler.ifPresent(
//                (handler) -> {
//                    builder.setBody(handler.buildContent());
//
//                }
//        );
        return builder.build();
    }

}
