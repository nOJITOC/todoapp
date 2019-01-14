package com.mmteams91.todoapp.common.presentation.ui.adapters;

import android.support.annotation.NonNull;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.mmteams91.todoapp.common.utils.SortedList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mmaruknin on 08.03.18.
 */

public class BaseListDelegationAdapter<T> extends ListDelegationAdapter<List<T>> {

    public BaseListDelegationAdapter(@NonNull AdapterDelegatesManager<List<T>> delegatesManager) {
        super(delegatesManager);
    }

    @Override
    public void setItems(List<T> items) {
        super.setItems(items);
        notifyDataSetChanged();
    }

    public void setItemsWithoutNotify(List<T> items) {
        super.setItems(items);
    }

    public void addItem(T item) {
        prepareItems();
        if (items instanceof SortedList) {
            addSortedItem((SortedList<T>) this.items, item);
        } else {
            items.add(item);
            notifyItemInserted(items.size() - 1);
        }
    }

    private void addSortedItem(SortedList<T> items, T item) {
        items.add(item);
        notifyItemInserted(items.getLastAddedElementPosition());
    }


    public void addItem(int index, T item) {
        prepareItems();
        items.add(index, item);
        notifyItemInserted(index);
    }

    public void addItems(Collection<T> items) {
        prepareItems();
        if (items instanceof SortedList) {
            for (T item : items) {
                addSortedItem((SortedList<T>) items, item);
            }
            return;
        }
        int count = items.size();
        int index = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(index, count);
    }

    public T removeItem(int position) {
        T item = items.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    public void removeItem(T item) {
        int position = items.indexOf(item);
        if (position >= 0) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public int getPositionOfItem(T item) {
        return items.indexOf(item);
    }

    private void prepareItems() {
        if (items == null)
            items = new ArrayList<>();
    }


    public AdapterDelegate<List<T>> getDelegate(int viewType) {
        return delegatesManager.getDelegateForViewType(viewType);
    }

    public void notifyDataSetChanged(Object payload) {
        notifyItemRangeChanged(0, getItemCount(), payload);
    }
}
