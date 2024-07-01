package main.java.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * A custom Gson adapter for serializing and deserializing {@link LocalDate} objects.
 * This adapter converts LocalDate objects to and from their JSON representation as strings.
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    /**
     * Serializes a {@link LocalDate} object to its JSON representation as a string.
     *
     * @param date the LocalDate object to serialize
     * @param typeOfSrc the actual type of the source object
     * @param context the context of the serialization process
     * @return a JsonElement representing the serialized LocalDate object as a string
     */
    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.toString());
    }

    /**
     * Deserializes a JSON representation of a {@link LocalDate} object from a string.
     *
     * @param json the JSON element to deserialize
     * @param typeOfT the type of the object to deserialize to
     * @param context the context of the deserialization process
     * @return the deserialized LocalDate object
     * @throws JsonParseException if the JSON element cannot be parsed into a LocalDate
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(json.getAsString());
    }
}