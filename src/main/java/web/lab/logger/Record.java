package web.lab.logger;

import java.time.LocalDateTime;

public class Record {
    LocalDateTime time;
    Action action;
    String url;

    public Record(LocalDateTime time, Action action, String url) {
        this.time = time;
        this.action = action;
        this.url = url;
    }
}
