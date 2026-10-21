package com.example.pizzaorder;


import android.os.Parcel;
import android.os.Parcelable;


public class Order implements Parcelable {
    String size;
    String crust;

    public Order(String size, String crust) {
        this.size = size;
        this.crust = crust;
    }

    protected Order(Parcel in) {
        size = in.readString();
        crust = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(size);
        dest.writeString(crust);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getPrice() {
        int price = 0;

        switch (size) {
            case "small":
                price += 25;
                break;
            case "medium":
                price += 35;
                break;
            case "family":
                price += 45;
                break;
        }

        if (crust.equals("thick")) {
            price += 5;
        }

        return price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }
}
