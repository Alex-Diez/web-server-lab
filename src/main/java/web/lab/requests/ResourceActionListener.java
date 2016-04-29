package web.lab.requests;

import web.lab.listeners.AddListener;
import web.lab.listeners.DeleteListener;
import web.lab.listeners.UpdateListener;

import java.time.LocalDateTime;
import java.util.Map;

public class ResourceActionListener implements AddListener,
                                               DeleteListener,
                                               UpdateListener,
                                               ResourceManager {

    private final Map<String, LocalDateTime> resources;

    public ResourceActionListener(Map<String, LocalDateTime> resources) {
        this.resources = resources;
    }

    @Override
    public boolean containsResource(String url) {
        return resources.containsKey(url);
    }

    @Override
    public LocalDateTime modificationTime(String url) {
        return resources.get(url);
    }

    @Override
    public void onAddUrl(String url) {
        resources.put(url, LocalDateTime.now());
    }

    @Override
    public void onDelete(String url) {
        resources.remove(url);
    }

    @Override
    public void onUpdate(String url) {
        resources.put(url, LocalDateTime.now());
    }
}
