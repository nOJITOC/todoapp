package com.mmteams91.todoapp.core.utils;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public final class SortedList<T> extends ArrayList<T> {
    private static final long serialVersionUID = 1L;

    private final SerializableComparator<T> comparator;

    private int lastAddedElementPosition = NO_POSITION;

    public SortedList(final SerializableComparator<T> c) {
        this.comparator = c;
    }


    public SortedList(final SortedList<T> list) {
        this.comparator = list.comparator;
        addAll(list);
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException("Can't insert into sorted list to specific index");
    }

    @Override
    public boolean add(T e) {
        int left, right, mid;
        left = 0;
        right = size();

        while (left < right) {
            mid = (left + right) / 2;
            int result = comparator.compare(get(mid), e);

            if (result > 0) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        super.add(left, e);
        lastAddedElementPosition = left;
        return true;

    }

    public int getLastAddedElementPosition() {
        return lastAddedElementPosition;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c instanceof SortedList && ((SortedList) c).comparator == comparator) {
            return super.addAll(c);
        }
        for (T t : c) {
            add(t);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Can't insert into sorted list to specific index");
    }

    public SerializableComparator<T> getComparator() {
        return comparator;
    }

    public interface SerializableComparator<T>
            extends Comparator<T>, Serializable {
    }

    public static class CompositeSerializableComparator<T> implements SerializableComparator<T> {

        List<SerializableComparator<T>> comparators = new ArrayList<>();

        public void add(SerializableComparator<T> comparator) {
            comparators.add(comparator);
        }


        @Override
        public int compare(T t, T t1) {
            for (SerializableComparator<T> serializableComparator : comparators) {
                int div;
                if ((div = serializableComparator.compare(t, t1)) != 0) return div;
            }
            return 0;
        }

        public void add(int index, SerializableComparator<T> comparator) {
            comparators.add(index, comparator);
        }

        public boolean isEmpty() {
            return comparators.isEmpty();
        }
    }

}

