package web.lab.response;

import web.lab.http.Status;

import static web.lab.http.Status.NOT_FOUND;

public class Response {
    private final Status status;

    private Response(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object != null && object.getClass().equals(getClass())) {
            Response response = (Response) object;
            return response.status == status;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ status - " + status + " ]";
    }

    public static class Builder {
        private Status status;

        public Response build() {
            status = status != null ? status : NOT_FOUND;
            return new Response(status);
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }
    }
}
