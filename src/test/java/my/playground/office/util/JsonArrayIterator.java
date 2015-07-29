package my.playground.office.util;

import java.util.Iterator;

import org.json.JSONArray;

public class JsonArrayIterator<T> implements Iterator<T> {

    private final JSONArray array;
    private int currentIndex = 0;

    public JsonArrayIterator(JSONArray array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < array.length();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
        return (T) array.get(currentIndex++);
    }

}
