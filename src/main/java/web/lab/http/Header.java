package web.lab.http;

public enum Header {
    REQUEST_METHOD("Request Method"),
    REQUEST_URL("Request URL"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    STATUS_CODE("Status Code");

    private final String humanRead;

    Header(String humanRead) {
        this.humanRead = humanRead;
    }

    @Override
    public String toString() {
        return humanRead;
    }
}
