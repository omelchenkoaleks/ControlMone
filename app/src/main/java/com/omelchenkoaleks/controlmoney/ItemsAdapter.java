package com.omelchenkoaleks.controlmoney;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> data = new ArrayList<>();

    // Переменная, которая будет хранить слушателя обработки нажатия.
    private ItemsAdapterListener listener = null;

    // Возможность задать слушателя обработки нажатия.
    public void setListener(ItemsAdapterListener listener) {
        this.listener = listener;
    }

    public void setData(List<Item> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        // Запись в начало списка.
        data.add(0, item);
        notifyItemInserted(0);
    }

    // Может хранить состояния нажатых объектов - true !true...
    // Нам для каждой позиции нужно записывать выделена она
    // или не выделена - тут и есть помощь.
    // Заметка: можно было бы использовать HashMap<> = но это очень затратно по памяти...
    private SparseBooleanArray selections = new SparseBooleanArray();

    // Метод нужен, чтобы передавать позицию item.
    public void toggleSelection(int position) {
        // Если по нашей позиции уже есть выделенный элемент, то надо снять выделение.
        if (selections.get(position, false)) {
            selections.delete(position);
        } else {
            selections.put(position, true);
        }
        // Нужно адаптеру сказать, что что-то поменялось:
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ItemsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemViewHolder holder, int position) {
        Item record = data.get(position);
        holder.bind(record, position, listener,
                selections.get(position, false));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Среди прочего здесь есть доступ к нашим отдельным элементам -
    // это полезно при обработке нажатий на эти элементы.
    // Этот доступ производится через itemView.
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }

        public void bind(final Item item,
                         final int position,
                         final ItemsAdapterListener listener,
                         boolean selected) {
            title.setText(item.name);
            price.setText(item.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Если мы передали адаптер (т.е. не равен null).
                    if (listener != null) {
                        listener.onItemClick(item, position);
                    }

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onItemLongClick(item, position);
                    }
                    return true;
                }
            });

            itemView.setActivated(selected);
        }
    }
}
