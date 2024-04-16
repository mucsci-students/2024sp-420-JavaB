package com.classuml;


import com.classuml.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class parameterTest {
    private Parameter parameter;

    @BeforeEach
    public void setUp() {
        parameter = new Parameter("paramName", "String");
    }

    @Test
    public void testParameterConstructor() {
        String expectedName = "paramName";
        String expectedType = "String";
        Parameter parameter = new Parameter(expectedName, expectedType);
        
        assertNotNull( parameter);
        assertEquals( expectedName, parameter.getName());
        assertEquals( expectedType, parameter.getType());
    }

    @Test
    public void testToString() {
        String name = "userId";
        String type = "int";
        Parameter parameter = new Parameter(name, type);
        String expectedOutput = "Name: userId,\n			 Type: int";
        
        assertEquals( expectedOutput, parameter.toString());
    }

    @Test
    public void testToStringWithEmptyValues() {
        Parameter parameter = new Parameter("", "");
        String expectedOutput = "Name: ,\n			 Type: ";
        
        assertEquals( expectedOutput, parameter.toString());
    }

    @Test
    public void testToStringWithNullValues() {
        // Assuming your constructor or getters handle null inputs gracefully.
        // This test might require modifications to your Parameter class to explicitly handle or disallow null values.
        Parameter parameter = new Parameter(null, null);
        String expectedOutput = "Name: null,\n			 Type: null";
        
        assertEquals( expectedOutput, parameter.toString());
    }
    
 // This test assumes Parameter is immutable and does not have setter methods.
    // @Test(expected = UnsupportedOperationException.class)
    // public void testImmutability() {
    //     // Parameter parameter = new Parameter("paramName", "String");
    //     // Attempt to modify parameter's state here, e.g., via reflection or if there were setter methods
    // }
    @Test
    public void testSpecialCharactersInNameAndType() {
        Parameter parameter = new Parameter("param_Name!", "String-Type@2");
        String expectedOutput = "Name: param_Name!,\n			 Type: String-Type@2";
        assertEquals( expectedOutput, parameter.toString());
    }
    @Test
    public void testLongNameAndType() {
        String longName = new String(new char[1000]).replace("\0", "n");
        String longType = new String(new char[1000]).replace("\0", "t");
        Parameter parameter = new Parameter(longName, longType);
        assertTrue(parameter.getName().equals(longName) && parameter.getType().equals(longType));
    }

    @Test
    public void testEquality() {
        Parameter param1 = new Parameter("name", "type");
        Parameter param2 = new Parameter("name", "type");
        assertEquals( param1, param2);
    }

    @Test
    public void testHashCodeConsistency() {
        Parameter parameter = new Parameter("name", "type");
        int initialHashCode = parameter.hashCode();
        int repeatedHashCode = parameter.hashCode();
        assertEquals(initialHashCode, repeatedHashCode);
    }
}
