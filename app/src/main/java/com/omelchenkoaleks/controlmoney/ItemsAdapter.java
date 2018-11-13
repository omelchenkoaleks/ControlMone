package com.omelchenkoaleks.controlmoney;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> data = new ArrayList<>();

//    public ItemsAdapter() {
//        // Пока здесь будем создавать данные...
//        createData();
//    }

    public void setData(List<Item> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = data.get(position);
        holder.applyData(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    private void createData() {
//        data.add(new Item("Молоко", 10));
//        data.add(new Item("Хлеб", 10));
//        data.add(new Item("Первый ужин за свою зарплату", 500));
//        data.add(new Item("путешествие", 20000));
//        data.add(new Item("фильм посмотрел", 100));
//        data.add(new Item("врачу", 200));
//        data.add(new Item("дорога в месяц", 2000));
//        data.add(new Item("дорога", 200));
//        data.add(new Item("отдых", 2035));
//        data.add(new Item("помощь другу", 2000));
//        data.add(new Item("игры", 20));
//        data.add(new Item("покупка компьютера", 200000));
//        data.add(new Item("дорога к родителям", 3000));
//        data.add(new Item("отпуск", 300));
//        data.add(new Item("гитара", 6000));
//    }

    // Создаем static, чтобы он не неследовал как внутренний класс ссылку на
    // внешний класс и не было сильной связанности.
    protected static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }

        public void applyData(Item item) {
            title.setText(item.getTitle());
            price.setText(String.valueOf(item.getPrice()));
        }
    }
}
