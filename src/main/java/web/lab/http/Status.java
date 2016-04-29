package web.lab.http;

public enum Status {
    OK(200, "Ok"),
    NOT_FOUND(404, " Not Found"),
    NOT_MODIFIED(304, "Not Modified"),
    CREATED(201, "Created");

    private final int code;
    private final String humanRead;

    Status(int code, String humanRead) {
        this.code = code;
        this.humanRead = humanRead;
    }

    @Override
    public String toString() {
        return "Status code " + code + " " + humanRead;
    }
}
