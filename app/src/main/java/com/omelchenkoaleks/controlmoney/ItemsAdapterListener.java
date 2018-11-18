package com.omelchenkoaleks.controlmoney;

import com.omelchenkoaleks.controlmoney.Api.Item;

public interface ItemsAdapterListener {

    void onItemClick(Item item, int position);
    void onItemLongClick(Item item, int position);
}
