package com.mmteams91.todoapp.core.ui.adapters;

import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import com.mmteams91.todoapp.core.utils.SortedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmaruknin on 09.03.18.
 */

public class ListDelegationAdapterBuilder<T> {
    AdapterDelegatesManager<List<T>> delegatesManager = new AdapterDelegatesManager<>();
    SortedList.CompositeSerializableComparator<T> comparator = new SortedList.CompositeSerializableComparator<>();

    public ListDelegationAdapterBuilder<T> addDelegate(AbsListItemAdapterDelegate delegate) {
        delegatesManager.addDelegate(delegate);
        return this;
    }


    public ListDelegationAdapterBuilder<T> addDelegate(int viewType, AbsListItemAdapterDelegate<? extends T, T, ?> delegate) {
        delegatesManager.addDelegate(viewType, delegate);
        return this;
    }

    public ListDelegationAdapterBuilder<T> addSort(SortedList.SerializableComparator<T> comparator) {
        this.comparator.add(comparator);
        return this;
    }

    public ListDelegationAdapterBuilder<T> addSort(int index, SortedList.SerializableComparator<T> comparator) {
        this.comparator.add(index, comparator);
        return this;
    }


    public BaseListDelegationAdapter<T> build() {
        BaseListDelegationAdapter<T> adapter = new BaseListDelegationAdapter<>(delegatesManager);
        if (comparator.isEmpty()) {
            adapter.setItems((List<T>) new ArrayList<>());
        } else {
            SortedList<T> items = new SortedList<>(comparator);
            adapter.setItems(items);
        }
        return adapter;
    }


}
