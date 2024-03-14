package com.classuml;

import org.junit.Test;

import com.classuml.Model.Field;

import static org.junit.Assert.*;

public class fieldTest {
    
	@Test
	public void constructorShouldCorrectlyAssignNameAndType() {
	    Field field = new Field("fieldName", "String");
	    assertNotNull("Field instance should not be null", field);
	    assertEquals("Field name should match input", "fieldName", field.getName());
	    assertEquals("Field type should match input", "String", field.getType());
	}
	
	@Test
	public void toStringShouldReturnCorrectFormat() {
	    Field field = new Field("myField", "int");
	    String expectedOutput = "\n" +
	                            "    Name: myField,\n" +
	                            "    Type: int";
	    assertEquals("toString output should match expected format", expectedOutput, field.toString());
	}

	@Test
	public void toStringWithEmptyNameAndType() {
	    Field field = new Field("", "");
	    String expectedOutput = "\n" +
	                            "    Name: ,\n" +
	                            "    Type: ";
	    assertEquals("toString output with empty name and type should match expected format", expectedOutput, field.toString());
	}

	@Test
	public void toStringWithNullNameAndType() {
	    // Assuming your constructor and getters handle null values gracefully
	    // This might require modifications to your Field class to handle null inputs appropriately.
	    Field field = new Field(null, null);
	    String expectedOutput = "\n" +
	                            "    Name: null,\n" +
	                            "    Type: null";
	    assertEquals("toString output with null name and type should gracefully handle nulls", expectedOutput, field.toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void testFieldConstructorWithNullName() {
	    new Field(null, "int");
	}

	@Test(expected = NullPointerException.class)
	public void testFieldConstructorWithNullType() {
	    new Field("myField", null);
	}

	@Test(expected = NullPointerException.class)
	public void testFieldConstructorWithNullNameAndType() {
	    new Field(null, null);
	}

	@Test
	public void testToStringConsistency() {
	    Field field = new Field("consistentField", "String");
	    String firstInvocation = field.toString();
	    String secondInvocation = field.toString();
	    assertEquals("toString output should remain consistent across invocations", firstInvocation, secondInvocation);
	}
	


	@Test
	public void testFieldVisibilityOutput() {
	    Field field = new Field("age", "int");
	    String expected = "\n" +
	                      "    Visibility: public,\n" +
	                      "    Name: age,\n" +
	                      "    Type: int";
	    assertEquals("toString should include visibility when set", expected, field.toString());
	}

	@Test
	public void testFieldImmutability() {
	    // Field field = new Field("constant", "final int");
	    // Assuming a method that attempts to change the type or name
	    // field.setType("int");
	    // assertTrue("Field type should not be changeable for 'final' fields", field.getType().equals("final int"));
	}

	@Test
	public void testFieldEquality() {
	    Field field1 = new Field("name", "String");
	    Field field2 = new Field("name", "String");
	    assertEquals("Fields with the same name and type should be equal", field1, field2);
	}

	@Test
	public void testFieldInequality() {
	    Field field1 = new Field("name", "String");
	    Field field2 = new Field("name", "int");
	    assertNotEquals("Fields with different types should not be equal", field1, field2);
	}


}
