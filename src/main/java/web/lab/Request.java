package web.lab;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static web.lab.HttpHeaders.IF_MODIFIED_SINCE;
import static web.lab.HttpHeaders.REQUEST_URL;

public class Request {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm:ss:SSSSSSSSS");

    private final String url;
    private final HttpMethod method;
    private final LocalDateTime ifModifiedSince;

    private Request(String url, LocalDateTime ifModifiedSince) {
        this(url, HttpMethod.GET, ifModifiedSince);
    }

    private Request(String url, HttpMethod method, LocalDateTime ifModifiedSince) {
        this.url = url;
        this.method = method;
        this.ifModifiedSince = ifModifiedSince;
    }

    public String getParameter(String parameter) {
        if (REQUEST_URL.equals(parameter)) return url;
        else if (IF_MODIFIED_SINCE.equals(parameter)) return ifModifiedSince.format(FORMATTER);
        else return method.toString();
    }

    public static class Builder {
        private final String url;
        private HttpMethod method;
        private LocalDateTime ifModifiedSince;

        public Builder(String url) {
            this.url = url;
        }

        public Request build() {
            ifModifiedSince = ifModifiedSince != null ? ifModifiedSince : LocalDateTime.now();
            if (method != null) {
                return new Request(url, method, ifModifiedSince);
            }
            return new Request(url, ifModifiedSince);
        }

        public Builder setMethod(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder setIfModifiedSince(LocalDateTime ifModifiedSince) {
            this.ifModifiedSince = ifModifiedSince;
            return this;
        }
    }
}
