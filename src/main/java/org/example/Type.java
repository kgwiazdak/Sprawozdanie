package org.example;

public enum Type {
    SMALL(20), BIG(50);

    private final int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
