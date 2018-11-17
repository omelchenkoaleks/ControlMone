package com.omelchenkoaleks.controlmoney;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ActionMode actionMode = null;

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
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // При нажатии на кнопку - здесь получаем текущую страницу.
                int currentPage = viewPager.getCurrentItem();

                String type = null;
                if (currentPage == MainPagesAdapter.PAGE_INCOMES) {
                    type = Item.TYPE_INCOMES;
                } else if (currentPage == MainPagesAdapter.PAGE_EXPENSES) {
                    type = Item.TYPE_EXPENSES;
                }

                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                intent.putExtra(AddItemActivity.TYPE_KEY, type);
                startActivityForResult(intent, ItemsFragment.ADD_ITEM_REQUEST_CODE);

            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case MainPagesAdapter.PAGE_INCOMES:
            case MainPagesAdapter.PAGE_EXPENSES:
                fab.show();
                break;
            case MainPagesAdapter.PAGE_BALANCE:
                fab.hide();
                break;
        }
    }

    // Изменилось состояние страницы.
    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                fab.setEnabled(true);
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
            case ViewPager.SCROLL_STATE_SETTLING:
                if (actionMode != null) {
                    actionMode.finish();
                }
                fab.setEnabled(false);
                break;
        }
    }

    // Если из активити вызывается onActivityResult, то назад данные не вернуться,
    // если в самом активити не определить этот метод...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    // В этом методе мы получаем ActionMode для активити
    // (потому-что он определен во фрагменте).
    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        Log.i(TAG, "onSupportActionModeStarted: ");
        // можем при нажатии прятать кнопку...
        fab.hide();
        // получаем ссылку на ActionMode
        actionMode = mode;
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        Log.i(TAG, "onSupportActionModeFinished: ");
        fab.show();
        actionMode = null;
    }
}
