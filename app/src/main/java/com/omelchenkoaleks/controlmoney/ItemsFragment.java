package com.omelchenkoaleks.controlmoney;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemsFragment extends Fragment {

    private static final String TAG = "ItemsFragment";

    private static final String TYPE_KEY = "type";

    private String type;

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private FloatingActionButton fab;

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

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
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
            }

            // Если произойдет ошибка.
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }


    // AsyncTask. 2 версия получения данных из сети.
//    @SuppressLint("StaticFieldLeak")
//    private void loadItems() {
//
//        AsyncTask<Void, Void, List<Item>> task = new AsyncTask<Void, Void, List<Item>>() {
//
//            // Метод выполнится в главном потоке. В нем можно что-то сделать до выполнения
//            // нашей основной задачи - например показать прогресс бар до загрузки.
//            @Override
//            protected void onPreExecute() {
//                Log.d(TAG, "onPreExecute: thread name " + Thread.currentThread().getName());
//            }
//
//            // Метод будет выполнен в другом потоке, который будет создан для непосредственной
//            // нашей задачи...
//            @Override
//            protected List<Item> doInBackground(Void... voids) {
//                Log.d(TAG, "doInBackground: thread name " + Thread.currentThread().getName());
//
//                Call<List<Item>> call = api.getItems(type);
//                try {
//                    List<Item> items = call.execute().body();
//                    // Если пришли то возвращаем...
//                    return items;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                // Если не пришли...
//                return null;
//            }
//
//            // В этом методе данные вернутся в главный поток.
//            @Override
//            protected void onPostExecute(List<Item> items) {
//                // Но, конечно, нужно проверить, что они не равны null.
//                if (items != null) {
//                    adapter.setData(items);
//                }
//            }
//        };
//
//        // После создания его нужно запустить.
//        task.execute();
//    }


    // Thread and Handler. 1 Версия получения данных из сети.
//    private void loadItems() {
//
//        Log.d(TAG, "loadItems: current thread " + Thread.currentThread().getName());
//
//        new LoadItemsTask(new Handler(callback)).start();
//    }
//
//    private Handler.Callback callback = new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//
//            if (msg.what == DATA_LOADED) {
//                List<Item> items = (List<Item>) msg.obj;
//                adapter.setData(items);
//            }
//            return true;
//        }
//    };
//
//    private final static int DATA_LOADED = 123;
//
//    private class LoadItemsTask implements Runnable {
//
//        private Thread thread;
//        private Handler handler;
//
//        public LoadItemsTask(Handler handler) {
//            thread = new Thread(this);
//            this.handler = handler;
//        }
//
//        public void start() {
//            thread.start();
//        }
//
//        @Override
//        public void run() {
//
//            Log.d(TAG, "run: current thread " + Thread.currentThread().getName());
//
//            Call<List<Item>> call = api.getItems(type);
//            try {
//                List<Item> items = call.execute().body();
//                handler.obtainMessage(DATA_LOADED, items).sendToTarget();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
