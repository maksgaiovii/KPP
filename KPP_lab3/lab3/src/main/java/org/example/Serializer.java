package org.example;

import java.io.IOException;
import java.util.List;

public interface Serializer {
    void serialize(List<Vehicle> vehicle, String fileName) throws IOException;
    List<Vehicle> deserialize(String fileName) throws IOException, ClassNotFoundException;
}

