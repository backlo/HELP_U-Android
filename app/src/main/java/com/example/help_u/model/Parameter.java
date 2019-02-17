package com.example.help_u.model;

import com.example.help_u.model.response.LoginResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public interface Parameter {

    public static class ParamSerializer implements JsonSerializer<ServerResponse> {


        @Override
        public JsonElement serialize(ServerResponse src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src, src.getClass());
        }
    }

    public static class ParamDeSerializer implements JsonDeserializer<ServerResponse> {

        @Override
        public ServerResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject src = json.getAsJsonObject();
            if (src.has("user_type")) {
                return context.deserialize(json, LoginResponse.class);
            }
            return null;
        }
    }

}
