package web.lab.requests;

import web.lab.listeners.AddListener;
import web.lab.listeners.DeleteListener;
import web.lab.listeners.UpdateListener;
import web.lab.logger.URLLogger;

public class UrlActionListener
        implements AddListener,
                   DeleteListener,
                   UpdateListener {
    private final URLLogger logger;

    public UrlActionListener(URLLogger logger) {
        this.logger = logger;
    }

    @Override
    public void onAddUrl(String url) {
        logger.add(url);
    }

    @Override
    public void onDelete(String url) {
        logger.delete(url);
    }

    @Override
    public void onUpdate(String url) {
        logger.update(url);
    }
}
