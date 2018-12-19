package com.ejin.quickhttp;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11764 on 2018/12/19.
 */
public class EnumTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {

        if (!typeToken.getRawType().isEnum()) {
            return null;
        }

        final Map<T, ValueType> maps = new HashMap<T, ValueType>();

        for(Object item: typeToken.getRawType().getEnumConstants()) {
            T tt = (T) item;

            try {
                SerializedName serializedName = tt.getClass().getField(tt.toString()).getAnnotation(SerializedName.class);
                if (serializedName != null) {
                    maps.put(tt, new ValueType(serializedName.value(), BasicType.STRING));
                    continue;
                }

                Field field = null;
                for (Field item2: tt.getClass().getDeclaredFields()) {
                    if (BasicType.isBasicType(item2.getType().getName())) {
                        field = item2;
                        break;
                    }
                }

                if (field != null) {
                    field.setAccessible(true);
                    BasicType basicType = BasicType.get(field.getType().getName());
                    Object value = null;
                    switch (basicType) {
                        case INT:
                            value = field.getInt(tt);
                            break;
                        case STRING:
                            value = (String)(field.get(tt));
                            break;
                        case LONG:
                            value = field.getLong(tt);
                            break;
                        case DOUBLE:
                            value = field.getDouble(tt);
                            break;
                        case BOOLEAN:
                            value = field.getBoolean(tt);
                            break;
                    }
                    maps.put(tt, new ValueType(value, basicType));
                } else {
                    maps.put(tt, new ValueType(tt.toString(), BasicType.STRING));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }

                ValueType valueType = maps.get(value);
                switch (valueType.type) {
                    case INT:
                        out.value((Number) valueType.value);
                        break;
                    case STRING:
                        out.value((String) valueType.value);
                        break;
                    case LONG:
                        out.value((Long) valueType.value);
                        break;
                    case DOUBLE:
                        out.value((Double) valueType.value);
                        break;
                    case BOOLEAN:
                        out.value((Boolean) valueType.value);
                        break;
                }

            }

            @Override
            public T read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }

                String source = reader.nextString();
                T tt = null;
                for (T item: maps.keySet()) {
                    if (maps.get(item).value.toString().equals(source)) {
                        tt = item;
                        break;
                    }
                }
                return tt;
            }
        };
    }

    private class ValueType {

        Object value;
        BasicType type;

        ValueType(Object value, BasicType type) {
            this.value = value;
            this.type = type;
        }

    }

    private enum BasicType {

        INT("int"),
        STRING("java.lang.String"),
        LONG("long"),
        DOUBLE("double"),
        BOOLEAN("boolean");

        private String name;

        BasicType(String name) {
            this.name = name;
        }

        static boolean isBasicType(String name) {
            for(BasicType item: values()) {
                if (item.name.equals(name)) {
                    return true;
                }
            }
            return false;
        }

        static BasicType get(String name) {
            for(BasicType item: values()) {
                if (item.name.equals(name)) {
                    return item;
                }
            }
            return BasicType.STRING;
        }

    }

}
