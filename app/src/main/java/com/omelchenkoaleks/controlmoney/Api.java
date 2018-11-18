package com.omelchenkoaleks.controlmoney;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("auth")
    Call<AuthResult> auth(@Query("social_user_id") String userId);

    @GET("items")
    Call<List<Item>> getItems(@Query("type") String type);
}
