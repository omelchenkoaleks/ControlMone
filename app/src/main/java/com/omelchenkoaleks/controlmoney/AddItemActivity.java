package com.omelchenkoaleks.controlmoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";

    public static final String TYPE_KEY = "type";

    private EditText name;
    private EditText price;
    private Button addButton;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addButton = findViewById(R.id.add_btn);

        type = getIntent().getStringExtra(TYPE_KEY);

//        name.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Если у нас строка пустая, то кнопка неактивна, и наоборот.
//                addButton.setEnabled(!TextUtils.isEmpty(s));
//                Log.i(TAG, "afterTextChanged: " + s);
//            }
//        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Нужно передать данные как-то...
                String nameValue = name.getText().toString();
                String priceValue = price.getText().toString();

                // Теперь нужен интент, чтобы получить данные...
                Intent intent = new Intent();
                intent.putExtra("name", nameValue);
                intent.putExtra("price", priceValue);

                setResult(RESULT_OK, intent);
                // Важно, чтобы кнопка сработала, нужно покинуть этот экран.
                finish();
            }
        });
    }
}
