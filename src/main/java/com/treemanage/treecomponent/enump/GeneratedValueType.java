package com.treemanage.treecomponent.enump;

/**
 * @Author: Mist
 * @Version: 1.0
 * @Date: 2019/12/29 10:48
 */
public enum GeneratedValueType {
    AUTO(1,"数据库自增");

    private int Code;
    private String value;

    GeneratedValueType(int code, String value) {
        Code = code;
        this.value = value;
    }

    public int getCode() {
        return Code;
    }

    public String getValue() {
        return value;
    }

    public static GeneratedValueType getEnum(int code) {
        for (GeneratedValueType c : GeneratedValueType.values()) {
            if (c.getCode() == code) {
                return c;
            }
        }
        return null;
    }
    public static GeneratedValueType getEnum(String value) {
        for (GeneratedValueType c : GeneratedValueType.values()) {
            if (c.getValue().equals(value)) {
                return c;
            }
        }
        return null;
    }
}
