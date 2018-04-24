package com.fomina.dao.exceptions;

import java.util.Iterator;

public class MessageNotFoundException extends DaoException {

    public MessageNotFoundException() {
        super();
    }
    public MessageNotFoundException(String message) {
        super(message);
    }
    public MessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public MessageNotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public Iterator<Throwable> iterator() {
        return new Iterator<Throwable>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Throwable next() {
                return null;
            }
        };
    }
}
