package com.omelchenkoaleks.controlmoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.omelchenkoaleks.controlmone.R;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        setTitle(R.string.add_item_screen_title);
    }
}
