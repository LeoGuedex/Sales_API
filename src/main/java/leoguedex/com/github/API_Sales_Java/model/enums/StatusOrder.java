package leoguedex.com.github.API_Sales_Java.model.enums;

import java.util.Arrays;

public enum StatusOrder {

    PENDING(0, "Pending"),
    ACCOMPLISHED(1, "Accomplished"),
    CANCELED(2, "Canceled"),
    FINISHED(3,"Finished");

    private int cod;
    private String description;

    StatusOrder(int id, String description) {
        this.cod = id;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static StatusOrder toEnum(Integer cod) {
        return Arrays.stream(StatusOrder.values())
                .filter(x -> cod != null && cod.equals(x.getCod()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID " + cod));
    }

}
