package com.omelchenkoaleks.controlmoney;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        MainPagesAdapter pagesAdapter =
                new MainPagesAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagesAdapter);
        tabLayout.setupWithViewPager(viewPager);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                intent.putExtra(AddItemActivity.TYPE_KEY, "expense");
                startActivityForResult(intent, ItemsFragment.ADD_ITEM_REQUEST_CODE);

            }
        });
    }
}
