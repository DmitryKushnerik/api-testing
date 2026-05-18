package enums;

public enum Currency {
    RUB("RUB", 100),
    USD("USD", 1),
    EUR("EUR", 1);
    private final String code;
    private final int scale;

    Currency(String code, int scale) {
        this.code = code;
        this.scale = scale;
    }

    public String getCode() {
        return code;
    }

    public int getScale() {
        return scale;
    }
}
