package com.classuml;

import org.junit.jupiter.api.Test;

import com.classuml.Model.*;

import static org.junit.Assert.assertEquals;

public class UMLClassTest {

    @Test
    public void testChangeMethodType() {
        // Create a new UMLClass
        UMLClass clazz = new UMLClass("TestClass");

        // Add a method to the class
        clazz.addMethod("method1", "int");
        Method method1 = clazz.getMethod("method1");

        // Change the return type of the method
        clazz.changeMethodType(method1.getName(), "float");

        // Check that the return type of the method has been changed
        assertEquals("float", method1.getType());
    }

    @Test
    public void testChangeMethodType_whenMethodDoesNotExist_shouldReturnFalse() {
        // Create a new UMLClass
        UMLClass clazz = new UMLClass("TestClass");

        // Try to change the return type of a non-existent method
        boolean result = clazz.changeMethodType("non-existent", "float");

        // Check that the method returned false
        assertEquals(false, result);
    }

    @Test
    public void testChangeMethodType_whenMethodNameIsEmpty_shouldReturnFalse() {
        // Create a new UMLClass
        UMLClass clazz = new UMLClass("TestClass");

        // Add a method to the class
        clazz.addMethod("method1", "int");

        // Try to change the return type of the method with an empty name
        boolean result = clazz.changeMethodType("", "float");

        // Check that the method returned false
        assertEquals(false, result);
    }

    @Test
    public void testChangeMethodType_whenNewTypeIsEmpty_shouldReturnFalse() {
        // Create a new UMLClass
        UMLClass clazz = new UMLClass("TestClass");

        // Add a method to the class
        clazz.addMethod("method1", "int");

        // Try to change the return type of the method with an empty new type
        boolean result = clazz.changeMethodType("method1", "");

        // Check that the method returned false
        assertEquals(false, result);
    }

    @Test
    public void testChangeFieldType() {
        // Create a new UMLClass
        UMLClass clazz = new UMLClass("TestClass");

        // Add a field to the class
        clazz.addField("field1", "int");
        Field field1 = clazz.getField("field1");

        // Change the type of the field
        clazz.changeFieldType(field1.getName(), "float");

        // Check that the field type has been changed
        assertEquals("float", field1.getType());
    }

    @Test
    public void testChangeFieldType_whenFieldDoesNotExist_shouldReturnFalse() {
        // Create a new UMLClass
        UMLClass clazz = new UMLClass("TestClass");

        // Try to change the type of a non-existent field
        boolean result = clazz.changeFieldType("non-existent", "float");

        // Check that the method returned false
        assertEquals(false, result);
    }

    @Test
    public void testChangeFieldType_whenFieldNameIsEmpty_shouldReturnFalse() {
        // Create a new UMLClass
        UMLClass clazz = new UMLClass("TestClass");

        // Add a field to the class
        clazz.addField("field1", "int");

        // Try to change the type of the field with an empty name
        boolean result = clazz.changeFieldType("", "float");

        // Check that the method returned false
        assertEquals(false, result);
    }

    @Test
    public void testChangeFieldType_whenNewTypeIsEmpty_shouldReturnFalse() {
        // Create a new UMLClass
        UMLClass clazz = new UMLClass("TestClass");

        // Add a field to the class
        clazz.addField("field1", "int");

        // Try to change the type of the field with an empty new type
        boolean result = clazz.changeFieldType("field1", "");

        // Check that the method returned false
        assertEquals(false, result);
    }
    // @Test
    // public void testToString()
    // {
    //     UMLDiagram diagram = new UMLDiagram();
    //     assertEquals(diagram.toString(), diagram.toString());
    // }
}