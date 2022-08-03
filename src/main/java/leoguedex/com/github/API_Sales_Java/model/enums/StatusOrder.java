package leoguedex.com.github.API_Sales_Java.model.enums;

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
        if (cod == null) {
            return null;
        }
        for (StatusOrder x : StatusOrder.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw  new IllegalArgumentException("Invalid ID" + cod);
    }
}
