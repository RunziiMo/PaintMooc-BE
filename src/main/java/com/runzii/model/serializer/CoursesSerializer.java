package com.runzii.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.runzii.model.entity.Course;
import com.runzii.model.entity.User;

import java.io.IOException;
import java.util.Set;

/**
 * Created by runzii on 16-6-13.
 */
public class CoursesSerializer extends JsonSerializer<Set<Course>> {
    @Override
    public void serialize(Set<Course> courseSet, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(courseSet.size());
    }
}
