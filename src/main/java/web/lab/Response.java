package web.lab;

import java.util.Objects;

public class Response {
    private final int status;
    private final String body;

    private Response(int status, String body) {
        this.status = status;
        this.body = body;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object != null && object.getClass().equals(getClass())) {
            Response response = (Response) object;
            return response.status == status
                    && Objects.equals(response.body, body);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ status - " + status + " body - '" + body + "' ]";
    }

    public static class Builder {
        private int status;
        private String body;

        public Response build() {
            status = status != 0 ? status : 404;
            body = body != null ? body : "";
            return new Response(status, body);
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }
    }
}
