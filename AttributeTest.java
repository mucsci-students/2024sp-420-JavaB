import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AttributeTest {

    private Attribute attribute;

    @Before
    public void setUp() {
        // Initialize an Attribute object
        attribute = new Attribute("id", "int", "public ");
    }

    @Test
    public void testToString() {
        // Test the toString method
        String expected = "public id: int";
        assertEquals(expected, attribute.toString());
    }

    @Test
    public void testThat() {
        // Attributes with the same name should be equal
        Attribute anotherAttributeWithSameName = new Attribute("id", "String", "private ");
        assertTrue(attribute.equals(anotherAttributeWithSameName));

        // Attributes with different names should not be equal
        Attribute attributeWithDifferentName = new Attribute("userId", "int", "public ");
        assertFalse(attribute.equals(attributeWithDifferentName));

        // Ensure it does not equal an object of a different type
        assertFalse(attribute.equals(new Object()));

        // Ensure it does not equal null
        assertFalse(attribute.equals(null));

        // Test equality with an object of the parent class having the same name, which should be false
        // because they are instances of different classes
        Attribute parentObjectWithSameName = new Attribute("id", "int", "public");
        assertFalse(attribute.equals(parentObjectWithSameName));
    }
}
