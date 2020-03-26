package com.cdhi.domain.enums;

public enum Column {

    BACKLOG(0, "BACKLOG"),
    TODO(1, "TODO"),
    ONGOING(2, "ONGOING"),
    DONE(3, "DONE");

    private int cod;
    private String description;

    private Column(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription () {
        return description;
    }

    public static Column toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (Column x : Column.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid Id: " + cod);
    }
}
