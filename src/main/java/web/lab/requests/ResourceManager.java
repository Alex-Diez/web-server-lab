package web.lab.requests;

import java.time.LocalDateTime;

public interface ResourceManager {

    boolean containsResource(String url);

    LocalDateTime modificationTime(String url);
}
