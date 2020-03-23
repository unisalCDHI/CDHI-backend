package com.cdhi.domain.enums;

public enum Background {

    B0(0, "BACKGROUND 0"),
    B1(1, "BACKGROUND 1"),
    B2(2, "BACKGROUND 2"),
    B3(3, "BACKGROUND 3"),
    B4(4, "BACKGROUND 4"),
    B5(5, "BACKGROUND 5"),
    B6(6, "BACKGROUND 6"),
    B7(7, "BACKGROUND 7"),
    B8(8, "BACKGROUND 8"),
    B9(9, "BACKGROUND 9"),
    B10(10, "BACKGROUND 10");

    private int cod;
    private String description;

    private Background(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription () {
        return description;
    }

    public static Background toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (Background x : Background.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid Id: " + cod);


    }
}
