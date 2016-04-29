package web.lab.logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static web.lab.logger.Action.CREATE;
import static web.lab.logger.Action.DELETE;
import static web.lab.logger.Action.MODIFY;

public class URLLogger {
    private final Map<String, LocalDateTime> modificationTable = new HashMap<>();
    private final FileSystem fileSystem;

    public URLLogger(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public LocalDateTime add(String url) {
        LocalDateTime creationTime = LocalDateTime.now();
        fileSystem.store(creationTime, CREATE, url);
        modificationTable.put(url, creationTime);
        return creationTime;
    }

    public LocalDateTime update(String url) {
        LocalDateTime modificationTime = LocalDateTime.now();
        modificationTable.put(url, modificationTime);
        fileSystem.store(modificationTime, MODIFY, url);
        return modificationTime;
    }

    public Optional<LocalDateTime> getLastModificationTimeFor(String url) {
        return Optional.ofNullable(modificationTable.get(url));
    }

    public LocalDateTime delete(String url) {
        LocalDateTime deleteTime = LocalDateTime.now();
        modificationTable.put(url, deleteTime);
        fileSystem.store(deleteTime, DELETE, url);
        return deleteTime;
    }
}
