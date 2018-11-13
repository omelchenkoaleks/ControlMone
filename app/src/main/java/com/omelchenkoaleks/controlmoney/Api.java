package com.omelchenkoaleks.controlmoney;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("/items")
    Call<List<Item>> getItem();
}
