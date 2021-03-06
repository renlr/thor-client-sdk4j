package com.vechain.thorclient.core.model;

public enum OrderFilter {
    DESC("DESC"),
    ASC("ASC");

    private final String value;

    OrderFilter(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
