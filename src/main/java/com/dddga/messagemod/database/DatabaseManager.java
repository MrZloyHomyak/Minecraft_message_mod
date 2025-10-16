package com.dddga.messagemod.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    private static SessionFactory sessionFactory;
    private static MessageRepository messageRepository;

    public static void initialize() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(MessageEntity.class);

            sessionFactory = configuration.buildSessionFactory();

            messageRepository = new MessageRepository(sessionFactory);

            System.out.println("Database manager initialized.");
        } catch (Exception e) {
            System.err.println("Database manager initialization failed: " + e.getMessage());
        }
    }
    public static void saveMessage(UUID playerUuid, String text) {
        if (messageRepository == null) {
            System.err.println("Repository is not initialized.");
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            messageRepository.save(playerUuid, text);
            System.out.println("Message saved via Repository to database: " + text);
        } catch (Exception e) {
            System.err.println("Repository error while saving message: " + e.getMessage());
        }
    }
    public static void shutDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static List<MessageEntity> getPlayerMessages(UUID playerUuid) {
        return messageRepository != null ? messageRepository.findByPlayer(playerUuid) : List.of();
    }
}
