package com.omelchenkoaleks.controlmoney;

public class Item {

    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_INCOMES = "incomes";
    public static final String TYPE_EXPENSES = "expenses";

    public int id;
    public String title;
    public String price;
    public String type;

    public Item(int id, String title, String price, String type) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.type = type;
    }
}
