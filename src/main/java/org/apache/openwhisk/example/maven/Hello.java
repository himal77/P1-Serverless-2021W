package org.apache.openwhisk.example.maven;

import com.google.gson.JsonObject;

public class Hello {
    public static JsonObject main(JsonObject args){
        String name;

        try {
            name = args.getAsJsonPrimitive("name").getAsString();
        } catch(Exception e) {
            name = "stranger";
        }

        JsonObject headers = new JsonObject();
        headers.addProperty("content-type", "application/json");

        JsonObject response = new JsonObject();
        response.add("headers", headers);
        response.addProperty("body", name);
        return response;
    }
}
