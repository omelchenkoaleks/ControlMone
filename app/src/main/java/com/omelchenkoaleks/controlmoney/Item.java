package com.omelchenkoaleks.controlmoney;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Item implements Parcelable {

    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_INCOMES = "incomes";
    public static final String TYPE_EXPENSES = "expenses";

    public int id;
    public String name;
    public String price;
    public String type;

    public Item(int id, String name, String price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Item(String name, String price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readString();
        type = in.readString();
    }

    // CREATOR будет вызываться тогда, когда системе нужно будет возвратить наш объект из байтов.
    public static final Creator<Item> CREATOR = new Creator<Item>() {

        // Из этого Parcel нужно вернуть наш объект.
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        // Не используем.
        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    // Этот метод никогда не трогаем.
    @Override
    public int describeContents() {
        return 0;
    }

    // В этом методе нужно записать все поля - важна очередность.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(type);
    }
}
