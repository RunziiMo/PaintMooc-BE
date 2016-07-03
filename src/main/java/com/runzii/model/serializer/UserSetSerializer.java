package com.runzii.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.runzii.model.entity.User;

import java.io.IOException;
import java.util.Set;

/**
 * Created by runzii on 16-6-13.
 */
public class UserSetSerializer extends JsonSerializer<Set<User>> {
    @Override
    public void serialize(Set<User> userSet, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(userSet.size());
    }
}
