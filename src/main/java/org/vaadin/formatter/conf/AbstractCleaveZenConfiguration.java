package org.vaadin.formatter.conf;

import java.util.function.Consumer;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

public abstract class AbstractCleaveZenConfiguration {

    protected abstract String getFormatType();

    protected abstract JsonObject toJson();

    protected  <T> void ifNotNull(T value, Consumer<T> put) {
        if (value != null) {
            put.accept(value);
        }
    }

    protected void ifNotNullArray(int[] value, Consumer<JsonArray> put) {
        if (value != null) {
            JsonArray array = Json.createArray();
            for (int i = 0; i < value.length; i++) {
                array.set(i, value[i]);
            }
            put.accept(array);
        }
    }

    protected void ifNotNullArray(String[] value, Consumer<JsonArray> put) {
        if (value != null) {
            JsonArray array = Json.createArray();
            for (int i = 0; i < value.length; i++) {
                array.set(i, value[i]);
            }
            put.accept(array);
        }
    }
}
