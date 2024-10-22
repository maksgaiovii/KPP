package org.example;
import com.google.gson.*;
import com.google.gson.reflect.*;

import java.lang.reflect.*;
import java.util.*;

import java.io.*;

public class JsonSerializer implements Serializer {
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Override
    public void serialize(List<Vehicle> vehicles, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(vehicles, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vehicle> deserialize(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type listType = new TypeToken<List<Vehicle>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

