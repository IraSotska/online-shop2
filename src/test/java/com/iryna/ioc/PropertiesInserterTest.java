package com.iryna.ioc;

import com.iryna.ioc.postProcessor.PropertiesInserter;
import com.iryna.ioc.entity.TestClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesInserterTest {

    @Test
    void shouldFillProperty() {
        var intProperty = 12;
        var stringProperty = "test";
        var booleanProperty = true;
        var doubleProperty = 12.99;

        var testClass = new TestClass();
        PropertiesInserter propertiesInserter = new PropertiesInserter();

        var postProcessedTestClass = (TestClass) propertiesInserter.postProcessBeforeInitialization(testClass, "");
        assertEquals(intProperty, postProcessedTestClass.getIntProperty());
        assertEquals(stringProperty, postProcessedTestClass.getStringProperty());
        assertEquals(booleanProperty, postProcessedTestClass.getBooleanProperty());
        assertEquals(doubleProperty, postProcessedTestClass.getDoubleProperty());
    }
}