package Model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class parameterTest {

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
    
    @Test
    public void testSpecialCharactersInNameAndType() {
        Parameter parameter = new Parameter("param_Name!", "String-Type@2");
        String expectedOutput = "Name: param_Name!,\n			 Type: String-Type@2";
        assertEquals("Parameter should correctly handle special characters in name and type", expectedOutput, parameter.toString());
    }
}