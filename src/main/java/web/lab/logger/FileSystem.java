package web.lab.logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileSystem {

    private final List<Record> recordList = new ArrayList<>();

    public boolean containsRecord(Optional<LocalDateTime> creationDate, String url, Action action) {
        return recordList.stream()
                .anyMatch(r -> r.url.equals(url) && r.action == action);
    }

    public List<Record> listOfRecord(String url) {
        return recordList.stream()
                .filter(r -> r.url.equals(url))
                .collect(Collectors.toList());
    }

    public void store(LocalDateTime time, Action action, String url) {
        recordList.add(new Record(time, action, url));
    }

}
