package com.tosan.deposit.data;

public enum DepositType {
    SHORT_TERM("short-term account"),
    LONG_TERM("long-term account"),
    CURRENT("current account"),
    SAVING("saving account");

    private String name;
    DepositType(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}

