package lib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Model.Method;

public class methodTest {

    private Method method;

    @BeforeAll
    public void setUp() throws Exception {
        method = new Method("sampleMethod", "void");
    }
    
 // Utility method for adding parameters
    private void addTestParameters() {
        method.addParameter("param1", "String");
        method.addParameter("param2", "int");
    }

    @Test
    public void testAddAndDeleteParameter() {
        String paramName = "param1";
        String paramType = "int";
        // Test adding a parameter
        assertTrue(method.addParameter(paramName, paramType));
        assertEquals(1, method.getParameters().size());
        
        // Test deleting the parameter
        assertTrue(method.deleteParameter(paramName));
        assertEquals(0, method.getParameters().size());
    }
    
    @Test
    public void addParameterWithNullName() {
        assertFalse(method.addParameter(null, "String"));
    }

    @Test
    public void addParameterWithBlankType() {
        assertFalse(method.addParameter("paramName", "  "));
    }
    
    @Test
    public void deleteNonExistingParameter() {
        method.addParameter("existingParam", "int");
        assertFalse(method.deleteParameter("nonExistingParam"));
    }

    @Test
    public void changeTypeOfNonExistingParameter() {
        assertFalse(method.changeParameterType("nonExisting", "String"));
    }

    @Test
    public void changeParameterTypeToNull() {
        method.addParameter("param1", "int");
        assertFalse(method.changeParameterType("param1", null));
    }

    
    @Test
    public void renameParameterToExistingName() {
        method.addParameter("param1", "int");
        method.addParameter("param2", "String");
        assertFalse(method.renameParameter("param1", "param2"));
    }

    @Test
    public void renameNonExistingParameter() {
        assertFalse(method.renameParameter("nonExisting", "newName"));
    }

    @Test
    public void replaceParametersWithNullList() {
        method.addParameter("param1", "int");
        method.replaceParameterList(null);
        assertTrue(method.getParameters().isEmpty());
    }


    @Test
    public void testToStringNoParameters() {
        assertEquals("String representation for method without parameters should match expected format",
                "\n    Name: sampleMethod,\n    Type: void,", method.toString().trim());
    }

    @Test
    public void testToStringWithParameters() {
        addTestParameters();
        String expected = "\n    Name: sampleMethod,\n    Type: void,    \nParam: \n    param1: String,\n    param2: int\n";
        assertEquals("String representation with parameters should match expected format", expected.trim(), method.toString().trim());
    }

}
