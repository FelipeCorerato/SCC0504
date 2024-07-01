package main.java.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class RoleAdapter implements JsonSerializer<Role>, JsonDeserializer<Role> {
    @Override
    public JsonElement serialize(Role src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.name());
    }

    @Override
    public Role deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Role.valueOf(json.getAsString().toUpperCase());
    }
}