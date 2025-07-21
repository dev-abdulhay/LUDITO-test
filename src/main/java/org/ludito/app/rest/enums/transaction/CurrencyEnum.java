package org.ludito.app.rest.enums.transaction;

import lombok.Getter;

@Getter
public enum CurrencyEnum {

    UZS("000"),
    USD("840"),
    RUB("643"),
    EUR("978"),
    UNKNOWN("-1");

    private final String code;

    CurrencyEnum(String code) {
        this.code = code;
    }


    public static String getByCode(String code) {
        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.name();
            }
        }
        return UNKNOWN.name();
    }

    public static CurrencyEnum getEnumByCode(String code) {
        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }


    public static CurrencyEnum getByName(final String name) {
        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
