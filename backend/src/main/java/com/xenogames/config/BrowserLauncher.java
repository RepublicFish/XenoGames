package com.xenogames.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

@Slf4j
@Component
public class BrowserLauncher {

    @Value("${server.port:8080}")
    private int port;

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowser() {
        String url = "http://localhost:" + port;

        // 方式1: Java Desktop API
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                log.info("浏览器已打开: {}", url);
                return;
            }
        } catch (Exception e) {
            log.warn("Desktop.browse 失败, 尝试 cmd start");
        }

        // 方式2: Windows cmd start (备选)
        try {
            new ProcessBuilder("cmd", "/c", "start", url).start();
            log.info("浏览器已打开(cmd): {}", url);
        } catch (Exception e) {
            log.warn("无法自动打开浏览器，请手动访问 {}", url);
        }
    }
}
