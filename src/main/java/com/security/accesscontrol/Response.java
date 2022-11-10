package com.security.accesscontrol;

import java.io.Serializable;

public class Response<T> implements Serializable {
    // T stands for "Type"
    private T value;
    private String token;
    private int unique;

    public Response(T value, String token, int unique) {
        this.value = value;
        this.token = token;
        this.unique = unique;
    }

    public T value() {
        return value;
    }

    public String token() {
        return token;
    }

    public int unique() {
        return unique;
    }

    @Override
    public String toString() {
        return "Response(value=" + value + ", token=" + token + ", unique=" + unique + ")";
    }
}