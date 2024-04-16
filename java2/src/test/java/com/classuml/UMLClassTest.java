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
        clazz.addMethod("b", "c");

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
        clazz.addField("b", "cc");

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
    @Test 
    public void testRemBadMethod()
    {
        UMLClass class2 = new UMLClass("a");
        assertEquals(false, class2.deleteMethod(null));
    }
    @Test
    public void testRenameFalseMethod()
    {
        UMLClass class2 = new UMLClass("a");
        class2.addMethod("test", "b");
        class2.addMethod("test2", "c");
        assertEquals(false, class2.renameMethod("test3", "test2"));
        assertEquals(false, class2.renameMethod("b", "c"));
    }
    @Test
    public void testAddMethod()
    {
        UMLClass class2 = new UMLClass("a");
        class2.addMethod("null", "notnull");
        assertEquals(false, class2.addMethod(null, null));
        assertEquals(false, class2.addMethod(null, "no"));
    }
    @Test
    public void testGetMethod()
    {
        UMLClass class2 = new UMLClass("a");
        class2.addMethod("c", "c");
        assertEquals(null, class2.getMethod(null));
        assertEquals(null, class2.getMethod("b"));
    }
    @Test
    public void testContainsMethod()
    {
        UMLClass class2 = new UMLClass("a");
        assertEquals(false, class2.containsMethod(null));
    }
    @Test
    public void testDelMethod()
    {
        UMLClass class2 = new UMLClass("a");
        class2.addMethod("a", "B");
        assertEquals(false, class2.deleteMethod("c"));
    }
    @Test
    public void testDelField()
    {
        UMLClass class2 = new UMLClass("a");
        class2.addField("c", "c");
        assertEquals(false, class2.deleteField("a"));
    }
    @Test
    public void testRenField()
    {
        UMLClass class2 = new UMLClass();
        class2.addField("c", "d");
        assertEquals(false, class2.renameField(null, null));
        assertEquals(false, class2.renameField("a", "b") );
    }
    @Test 
    public void testGetField()
    {
        UMLClass class2 = new UMLClass();
        class2.addField("c", "c");
        assertEquals(null, class2.getField(null));
        assertEquals(null, class2.getField("b"));
    }
}