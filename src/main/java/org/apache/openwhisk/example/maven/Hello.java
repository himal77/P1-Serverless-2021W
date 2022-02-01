package org.apache.openwhisk.example.maven;

import com.google.gson.JsonObject;

public class Hello {
    public static JsonObject main(JsonObject args) {

        String img = args.getAsJsonPrimitive("image").getAsString();

        JsonObject headers = new JsonObject();
        headers.addProperty("content-type", "image/png");

        JsonObject response = new JsonObject();
        response.add("headers", headers);
        response.addProperty("statusCode", "200");
        response.addProperty("body", img);

        return response;
    }
}
