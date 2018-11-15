package com.omelchenkoaleks.controlmoney;

public interface ItemsAdapterListener {

    void onItemClick(Item item, int position);
    void onItemLongClick(Item item, int position);
}
