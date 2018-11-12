package com.omelchenkoaleks.controlmoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.omelchenkoaleks.controlmone.R;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";

    private EditText name;
    private EditText price;
    private Button addBatton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        setTitle(R.string.add_item_screen_title);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addBatton = findViewById(R.id.add_btn);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Если у нас строка пустая, то кнопка неактивна, и наоборот.
                addBatton.setEnabled(!TextUtils.isEmpty(s));
                Log.i(TAG, "afterTextChanged: " + s);
            }
        });
    }


}
