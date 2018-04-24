package com.fomina.dao.exceptions;

import java.util.Iterator;

public class UserNotFoundException extends DaoException {

    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserNotFoundException(Throwable cause) {
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
