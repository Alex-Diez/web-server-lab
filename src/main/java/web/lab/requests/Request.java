package web.lab.requests;

import web.lab.http.Header;
import web.lab.http.Method;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static web.lab.http.Header.IF_MODIFIED_SINCE;
import static web.lab.http.Header.REQUEST_URL;

public class Request {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm:ss:SSSSSSSSS");

    private final String url;
    private final Method method;
    private final LocalDateTime ifModifiedSince;

    private Request(String url, LocalDateTime ifModifiedSince) {
        this(url, Method.GET, ifModifiedSince);
    }

    private Request(String url, Method method, LocalDateTime ifModifiedSince) {
        this.url = url;
        this.method = method;
        this.ifModifiedSince = ifModifiedSince;
    }

    public String getHeader(Header parameter) {
        if (REQUEST_URL == parameter) return url;
        else if (IF_MODIFIED_SINCE == parameter) return ifModifiedSince.format(FORMATTER);
        else return method.toString();
    }

    public static class Builder {
        private final String url;
        private Method method;
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

        public Builder setMethod(Method method) {
            this.method = method;
            return this;
        }

        public Builder setIfModifiedSince(LocalDateTime ifModifiedSince) {
            this.ifModifiedSince = ifModifiedSince;
            return this;
        }
    }
}
