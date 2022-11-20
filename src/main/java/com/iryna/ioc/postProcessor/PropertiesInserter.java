package com.iryna.ioc.postProcessor;

import com.iryna.ioc.annotation.Value;
import com.iryna.util.ConfigLoader;
import com.study.ioc.processor.BeanPostProcessor;
import lombok.Setter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

@Setter
public class PropertiesInserter implements BeanPostProcessor {
    private static final String START_PATH_IDENTIFIER = "{$";
    private static final String END_PATH_IDENTIFIER = "}";

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) {
        Arrays.stream(o.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(Value.class))
                .peek(field -> field.setAccessible(true)).forEach(field -> {
                    var annotation = field.getAnnotation(Value.class);
                    var property = annotation.path().substring(START_PATH_IDENTIFIER.length(), annotation.path().length() - END_PATH_IDENTIFIER.length());
                    try {
                        field.set(o, castValue(load(property), field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Can't set property " + property, e);
                    }
                });
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) {
        return o;
    }

    private Object castValue(String propertyValue, Class<?> clazz) {
        if (int.class == clazz || Integer.class == clazz) {
            return Integer.valueOf(propertyValue);

        } else if (byte.class == clazz || Byte.class == clazz) {
            return Byte.valueOf(propertyValue);

        } else if (short.class == clazz || Short.class == clazz) {
            return Short.valueOf(propertyValue);

        } else if (long.class == clazz || Long.class == clazz) {
            return Long.valueOf(propertyValue);

        } else if (double.class == clazz || Double.class == clazz) {
            return Double.valueOf(propertyValue);

        } else if (boolean.class == clazz || Boolean.class == clazz) {
            return Boolean.valueOf(propertyValue);

        } else {
            return clazz.cast(propertyValue);
        }
    }

    private String load(String propertyName) {
        var property = new Properties();
        try (var inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            property.load(inputStream);

            return property.getProperty(propertyName);
        } catch (IOException e) {
            throw new RuntimeException("Can't load configuration properties", e);
        }
    }
}
