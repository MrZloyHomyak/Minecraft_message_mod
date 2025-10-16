package com.dddga.messagemod.database;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "text", nullable = false, length = 256)
    private String text;

    public MessageEntity() {}

    public MessageEntity(final UUID uuid, final String text) {
        this.uuid = uuid;
        this.text = text;
    }
    public long getId() {
        return id;
    }
    public UUID getUuid() {
        return uuid;
    }
    public String getText() {
        return text;
    }
    public void setId(final long id) {
        this.id = id;
    }
    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }
    public void setText(final String text) {
        this.text = text;
    }
}
