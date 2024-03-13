package com.classuml;

import org.junit.Test;

import com.classuml.Model.Parameter;

import static org.junit.Assert.*;

public class parameterTest {

    @Test
    public void testParameterConstructor() {
        String expectedName = "paramName";
        String expectedType = "String";
        Parameter parameter = new Parameter(expectedName, expectedType);
        
        assertNotNull("Parameter instance should not be null", parameter);
        assertEquals("Parameter name should match the provided name", expectedName, parameter.getName());
        assertEquals("Parameter type should match the provided type", expectedType, parameter.getType());
    }

    @Test
    public void testToString() {
        String name = "userId";
        String type = "int";
        Parameter parameter = new Parameter(name, type);
        String expectedOutput = "Name: userId,\n			 Type: int";
        
        assertEquals("toString should return the correct string representation", expectedOutput, parameter.toString());
    }

    @Test
    public void testToStringWithEmptyValues() {
        Parameter parameter = new Parameter("", "");
        String expectedOutput = "Name: ,\n			 Type: ";
        
        assertEquals("toString with empty name and type should match expected format", expectedOutput, parameter.toString());
    }

    @Test
    public void testToStringWithNullValues() {
        // Assuming your constructor or getters handle null inputs gracefully.
        // This test might require modifications to your Parameter class to explicitly handle or disallow null values.
        Parameter parameter = new Parameter(null, null);
        String expectedOutput = "Name: null,\n			 Type: null";
        
        assertEquals("toString with null name and type should gracefully handle nulls", expectedOutput, parameter.toString());
    }
    
 // This test assumes Parameter is immutable and does not have setter methods.
    @Test(expected = UnsupportedOperationException.class)
    public void testImmutability() {
        // Parameter parameter = new Parameter("paramName", "String");
        // Attempt to modify parameter's state here, e.g., via reflection or if there were setter methods
    }
    @Test
    public void testSpecialCharactersInNameAndType() {
        Parameter parameter = new Parameter("param_Name!", "String-Type@2");
        String expectedOutput = "Name: param_Name!,\n			 Type: String-Type@2";
        assertEquals("Parameter should correctly handle special characters in name and type", expectedOutput, parameter.toString());
    }
    @Test
    public void testLongNameAndType() {
        String longName = new String(new char[1000]).replace("\0", "n");
        String longType = new String(new char[1000]).replace("\0", "t");
        Parameter parameter = new Parameter(longName, longType);
        assertTrue("Parameter should handle very long names and types", parameter.getName().equals(longName) && parameter.getType().equals(longType));
    }

    @Test
    public void testEquality() {
        Parameter param1 = new Parameter("name", "type");
        Parameter param2 = new Parameter("name", "type");
        assertEquals("Equivalent parameters should be considered equal", param1, param2);
    }

    @Test
    public void testHashCodeConsistency() {
        Parameter parameter = new Parameter("name", "type");
        int initialHashCode = parameter.hashCode();
        int repeatedHashCode = parameter.hashCode();
        assertEquals("Hash code should be consistent across invocations", initialHashCode, repeatedHashCode);
    }
}
