package net.croz.pancakes_unlimited.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

public class JsonParserUtil
{
    public static <T> T getObjectFromJson(String resourcePath, Class<T> clazz) throws IOException
    {
        return new ObjectMapper().readValue(new ClassPathResource(resourcePath).getInputStream(), clazz);
    }
}
