package com.dddga.messagemod.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.UUID;

public class MessageRepository {
    private final SessionFactory sessionFactory;

    public MessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(UUID playerUuid, String text) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            MessageEntity message = new MessageEntity(playerUuid, text);
            session.persist(message);
            session.getTransaction().commit();
        }

    }
    public List<MessageEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM MessageEntity ORDER BY id DESC",
                    MessageEntity.class).list();
        }
    }
    public List<MessageEntity> findByPlayer(UUID playerUuid) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM MessageEntity WHERE uuid = :uuid ORDER BY id DESC",
                    MessageEntity.class
            ).setParameter("uuid", playerUuid).list();
        }

    }
}
