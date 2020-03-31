package com.treemanage.treecomponent.enump;

/**
 * @Author: Mist
 * @Version: 1.0
 * @Date: 2019/12/29 10:42
 */
public enum JavaType {
    LONG(1,"long"),
    _LONG(2,"Long"),
    INT(3,"int"),
    _INT(4,"Integer"),
    STRING(5,"String"),
    OBJECT(6,"Object"),
    DATE(7,"Date"),
    ;

    private int Code;
    private String value;

    JavaType(int code, String value) {
        Code = code;
        this.value = value;
    }

    public int getCode() {
        return Code;
    }

    public String getValue() {
        return value;
    }

    public static JavaType getEnum(int code) {
        for (JavaType c : JavaType.values()) {
            if (c.getCode() == code) {
                return c;
            }
        }
        return null;
    }

    public static JavaType getEnum(String value) {
        for (JavaType c : JavaType.values()) {
            if (c.getValue().equals(value)) {
                return c;
            }
        }
        return null;
    }
}
