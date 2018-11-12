package com.omelchenkoaleks.controlmoney;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Record> data = new ArrayList<>();

    public ItemAdapter() {
        // Пока здесь будем создавать данные...
        createData();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Record record = data.get(position);
        holder.applyData(record);
    }

    /**
     * ViewHolder не выносим в отдельный класс, потому что у нас только один item.
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    private void createData() {
        data.add(new Record("Молоко", 10));
        data.add(new Record("Хлеб", 10));
        data.add(new Record("Первый ужин за свою зарплату", 500));
        data.add(new Record("путешествие", 20000));
        data.add(new Record("фильм посмотрел", 100));
        data.add(new Record("врачу", 200));
        data.add(new Record("дорога в месяц", 2000));
        data.add(new Record("дорога", 200));
        data.add(new Record("отдых", 2035));
        data.add(new Record("помощь другу", 2000));
        data.add(new Record("игры", 20));
        data.add(new Record("покупка компьютера", 200000));
        data.add(new Record("дорога к родителям", 3000));
        data.add(new Record("отпуск", 300));
        data.add(new Record("гитара", 6000));
    }

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

        public void applyData(Record record) {
            title.setText(record.getTitle());
            price.setText(String.valueOf(record.getPrice()));
        }
    }
}
