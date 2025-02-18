package org.forum.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context initialized");
        Dotenv dotenv = Dotenv.load();
        System.setProperty("FORUM_MAIL_USERNAME", Objects.requireNonNull(dotenv.get("FORUM_MAIL_USERNAME")));
        System.setProperty("FORUM_MAIL_PASSWORD", Objects.requireNonNull(dotenv.get("FORUM_MAIL_PASSWORD")));
    }
}
