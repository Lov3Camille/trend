package org.lov3camille.trend.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {

    private int serverport;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverport = event.getWebServer().getPort();
    }

    public int getPort() {
        return this.serverport;
    }
}
