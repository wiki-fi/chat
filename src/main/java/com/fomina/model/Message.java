package com.fomina.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Message implements Serializable, Comparable<Message> {
    // Properties ---------------------------------------------------------------------------------

    private final int id;
    private final Date timestamp;
    private String text;
    private User sender;

    // Constructors -------------------------------------------------------------------------------

    public Message(String text, Date timestamp, User sender) {
        this.text = text;
        this.timestamp = timestamp;
        this.sender = sender;
        this.id = Math.abs(Objects.hash(text, timestamp));
    }

    // Getters & Setters --------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    // Methods ------------------------------------------------------------------------------------

    public int compareTo(Message message) { return this.getTimestamp().compareTo(message.getTimestamp()); }

    // Object overrides ---------------------------------------------------------------------------

    /**
     * Returns the String representation of this Message.
     * Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", sender=" + sender +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return getId() == message.getId() &&
                Objects.equals(getTimestamp(), message.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getTimestamp());
    }
}
