package Model;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

public class fieldTest {
    
	
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


}