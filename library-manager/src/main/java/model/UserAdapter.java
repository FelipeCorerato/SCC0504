package main.java.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class UserAdapter implements JsonSerializer<User>, JsonDeserializer<User> {
    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", src.getUsername());
        jsonObject.addProperty("password", src.getPassword());
        jsonObject.addProperty("role", src.getRole().name());
        return jsonObject;
    }

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String username = jsonObject.has("username") ? jsonObject.get("username").getAsString() : null;
        String password = jsonObject.has("password") ? jsonObject.get("password").getAsString() : null;
        Role role = jsonObject.has("role") ? Role.valueOf(jsonObject.get("role").getAsString()) : null;

        if (username == null || password == null || role == null) {
            throw new JsonParseException("Invalid User data");
        }

        if (role == Role.ADMIN) {
            return new Admin(username, password);
        } else if (role == Role.LIBRARIAN) {
            return new Librarian(username, password);
        } else {
            throw new JsonParseException("Unknown role: " + role);
        }
    }
}