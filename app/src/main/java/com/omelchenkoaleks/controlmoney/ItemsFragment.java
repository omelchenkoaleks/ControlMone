package com.omelchenkoaleks.controlmoney;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemsFragment extends Fragment {

    private static final String TAG = "ItemsFragment";

    private static final String TYPE_KEY = "type";

    public static final int ADD_ITEM_REQUEST_CODE = 123;

    private String type;

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private SwipeRefreshLayout refresh;

    private Api api;
    private App app;

    public static ItemsFragment createItemsFragment(String type) {
        ItemsFragment fragment = new ItemsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ItemsFragment.TYPE_KEY, type);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ItemsAdapter();
        adapter.setListener(new AdapterListener());

        Bundle bundle = getArguments();
        type = bundle.getString(TYPE_KEY, Item.TYPE_EXPENSES);

        if (type.equals(Item.TYPE_UNKNOWN)) {
            throw new IllegalArgumentException("Unknown type");
        }

        app = ((App) getActivity().getApplication());
        api = app.getApi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        refresh = view.findViewById(R.id.refresh);
        refresh.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.GREEN);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        loadItems();
    }

    private void loadItems() {
        Call<List<Item>> call = api.getItems(type);

        call.enqueue(new Callback<List<Item>>() {

            // Сюда придет ответ.
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                adapter.setData(response.body());
                refresh.setRefreshing(false);
            }

            // Если произойдет ошибка.
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                refresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Item item = data.getParcelableExtra("item");

            // Когда получили item по типу проверяем - наш ли это item.
            if (item.type.equals(type)) {
                adapter.addItem(item);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // Так как это вложенный класс - он наследует все поля и методы внешнего класса.
    // Зачем он нам нужен? Чтобы не ссылаться на самого себя this - потом можно запутаться.
    private class AdapterListener implements ItemsAdapterListener {

        @Override
        public void onItemClick(Item item, int position) {
            // Выделяем цвет при нажатии.
            if (isInActionMode()) {
                toggleSelection(position);
            }

            actionMode = ((AppCompatActivity) getActivity())
                    .startSupportActionMode(actionModeCallback);
            toggleSelection(position);
        }

        @Override
        public void onItemLongClick(Item item, int position) {
            // Предотвращаем от повторного нажимания.
            if (isInActionMode()) {
                return;
            }

            // TODO: Почему методы отличаются?
//            actionMode = ((AppCompatActivity) getActivity())
//                    .startSupportActionMode(actionModeCallback);
//            toggleSelection(position);
        }

        // Проверяем, что не пустой.
        private boolean isInActionMode() {
            return actionMode != null;
        }

        // Переключаем, что у нас item выбран.
        private void toggleSelection(int position) {
            adapter.toggleSelection(position);
        }
    }


    /*     ACTION MODE  заменяет то, что есть в Toolbar на другой режим.   */

    private ActionMode actionMode = null;

    //
    private void removeSelectedItems() {
        // Начинаем удаление выбранных элементов с конца - почему?
        for (int i = adapter.getSelectedItems().size() - 1; i >= 0; i-- ) {
            adapter.remove(adapter.getSelectedItems().get(i));
        }
        actionMode.finish();

    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.items_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.remove:
                    showDialog();
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelections();
            actionMode = null;
        }
    };

    private void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.delete_item_dialog)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

        alertDialog.show();
    }
}

