import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testAttributeAndMethodLayOut {
	 @Test
	    public void testConstructorAndGetters() {
	        AttributeAndMethodLayOut item = new AttributeAndMethodLayOut("name", "String");
	        assertAll("Constructor and Getter",
	            () -> assertEquals("name", item.getName(), "Name should be correctly assigned and retrieved"),
	            () -> assertEquals("String", item.getType(), "Type should be correctly assigned and retrieved")
	        );
	    }

	    @Test
	    public void testSetName() {
	        AttributeAndMethodLayOut item = new AttributeAndMethodLayOut("initialName", "String");
	        item.setName("updatedName");
	        assertEquals("updatedName", item.getName(), "setName should correctly update the name");
	    }

	    @Test
	    public void testSetType() {
	        AttributeAndMethodLayOut item = new AttributeAndMethodLayOut("name", "initialType");
	        item.setType("updatedType");
	        assertEquals("updatedType", item.getType(), "setType should correctly update the type");
	    }

	    @Test
	    public void testEqualsWithSameName() {
	        AttributeAndMethodLayOut item1 = new AttributeAndMethodLayOut("name", "Type1");
	        AttributeAndMethodLayOut item2 = new AttributeAndMethodLayOut("name", "Type2");
	        assertTrue(item1.equals(item2), "equals should return true for objects with the same name, regardless of type");
	    }

	    @Test
	    public void testEqualsWithDifferentName() {
	        AttributeAndMethodLayOut item1 = new AttributeAndMethodLayOut("name1", "Type");
	        AttributeAndMethodLayOut item2 = new AttributeAndMethodLayOut("name2", "Type");
	        assertFalse(item1.equals(item2), "equals should return false for objects with different names");
	    }
	    @Test
	    public void testSetNameToSameValue() {
	        AttributeAndMethodLayOut item = new AttributeAndMethodLayOut("name", "Type");
	        item.setName("name"); // Setting the same name again
	        assertEquals("name", item.getName(), "setName with the same value should not affect the name");
	    }

	    @Test
	    public void testSetTypeToSameValue() {
	        AttributeAndMethodLayOut item = new AttributeAndMethodLayOut("name", "Type");
	        item.setType("Type"); // Setting the same type again
	        assertEquals("Type", item.getType(), "setType with the same value should not affect the type");
	    }

	    @Test
	    public void testEqualsWithNull() {
	        AttributeAndMethodLayOut item = new AttributeAndMethodLayOut("name", "Type");
	        assertFalse(item.equals(null), "equals should return false when compared with null");
	    }

	    @Test
	    public void testEqualsWithItself() {
	        AttributeAndMethodLayOut item = new AttributeAndMethodLayOut("name", "Type");
	        assertTrue(item.equals(item), "An object should be equal to itself");
	    }

	    @Test
	    public void testEqualsWithDifferentObjectType() {
	        AttributeAndMethodLayOut item = new AttributeAndMethodLayOut("name", "Type");
	        String differentTypeObject = "I am not an AttributeAndMethodLayOut";
	        assertFalse(item.equals(differentTypeObject), "equals should return false when compared with a different type of object");
	    }
	   

}
