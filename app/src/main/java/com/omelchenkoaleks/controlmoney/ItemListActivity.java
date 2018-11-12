package com.omelchenkoaleks.controlmoney;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ItemListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Record> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createData();
        recyclerView.setAdapter(new ItemListAdapter());
    }

    private void createData() {
        data.add(new Record("Молоко", 10));
        data.add(new Record("Хлеб", 10));
        data.add(new Record("Первый ужин за свою зарплату", 500));
        data.add(new Record("путешествие", 20000));
        data.add(new Record("фильм посмотрел", 100));
        data.add(new Record("врачу", 200));
        data.add(new Record("дорога в месяц", 2000));
    }

    public class ItemListAdapter extends RecyclerView.Adapter<RecordViewHolder> {

        @NonNull
        @Override
        public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_record, parent, false);
            return new RecordViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
            Record record = data.get(position);
            holder.applyData(record);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class RecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView price;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }

        public void applyData(Record record) {
            title.setText(record.getTitle());
            price.setText(String.valueOf(record.getPrice()));
        }
    }
}
