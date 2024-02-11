import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class contains JUnit test cases for the {@link Relationship} class.
 */
public class relationshipTestClass {

    /**
     * Tests the constructor and getter methods of the {@link Relationship} class.
     */
    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", "1");
        assertEquals("SourceClass", relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
        assertEquals("1", relationship.getId());
    }

    /**
     * Tests the setter methods of the {@link Relationship} class.
     */
    @Test
    public void testSetters() {
        // Test setter methods
        Relationship relationship = new Relationship();
        relationship.setSource("SourceClass");
        relationship.setDestination("DestinationClass");
        relationship.setId("1");
        assertEquals("SourceClass", relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
        assertEquals("1", relationship.getId());
    }

    /**
     * Tests the {@code toString()} method of the {@link Relationship} class.
     */
    @Test
    public void testToString() {
        // Test toString method
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", "1");
        String expected = "Relationship ID: 1\n" +
                          "Relationship source: SourceClass\n" +
                          "Relationship destination: DestinationClass\n";
        assertEquals(expected, relationship.toString());
    }
    
    /**
     * Tests the empty constructor of the {@link Relationship} class.
     */
    @Test
    public void testEmptyConstructor1() {
        // Test empty constructor
        Relationship relationship = new Relationship();
        assertNull(relationship.getId());
        assertNull(relationship.getSource());
        assertNull(relationship.getDestination());
    }

    /**
     * Tests the setter and getter methods of the {@link Relationship} class.
     */
    @Test
    public void testSettersAndGetters() {
        // Test setter and getter methods
        Relationship relationship = new Relationship();
        relationship.setSource("SourceClass");
        relationship.setDestination("DestinationClass");
        relationship.setId("1");
        assertEquals("SourceClass", relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
        assertEquals("1", relationship.getId());
    }

    /**
     * Tests the {@code toString()} method with an empty constructor of the {@link Relationship} class.
     */
    @Test
    public void testToStringWithEmptyConstructor() {
        // Test toString method with empty constructor
        Relationship relationship = new Relationship();
        String expected = "Relationship ID: null\n" +
                          "Relationship source: null\n" +
                          "Relationship destination: null\n";
        assertEquals(expected, relationship.toString());
    }

    /**
     * Tests the {@code toString()} method with a non-empty constructor of the {@link Relationship} class.
     */
    @Test
    public void testToStringWithNonEmptyConstructor() {
        // Test toString method with non-empty constructor
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", "1");
        String expected = "Relationship ID: 1\n" +
                          "Relationship source: SourceClass\n" +
                          "Relationship destination: DestinationClass\n";
        assertEquals(expected, relationship.toString());
    }

    /**
     * Tests the setter methods of the {@link Relationship} class.
     */
    @Test
    public void testSetterMethods() {
        // Test setter methods
        Relationship relationship = new Relationship();
        relationship.setId("123");
        relationship.setSource("A");
        relationship.setDestination("B");
        assertEquals("123", relationship.getId());
        assertEquals("A", relationship.getSource());
        assertEquals("B", relationship.getDestination());
    }
    
    /**
     * Tests the empty constructor of the {@link Relationship} class.
     */
    @Test
    public void testEmptyConstructor() {
        // Test empty constructor
        Relationship relationship = new Relationship();
        assertNull(relationship.getId());
        assertNull(relationship.getSource());
        assertNull(relationship.getDestination());
    }

    /**
     * Tests the setId method of the {@link Relationship} class.
     */
    @Test
    public void testSetId() {
        // Test setId method
        Relationship relationship = new Relationship();
        relationship.setId("2");
        assertEquals("2", relationship.getId());
    }

    /**
     * Tests the setSource method of the {@link Relationship} class.
     */
    @Test
    public void testSetSource() {
        // Test setSource method
        Relationship relationship = new Relationship();
        relationship.setSource("NewSource");
        assertEquals("NewSource", relationship.getSource());
    }

    /**
     * Tests the setDestination method of the {@link Relationship} class.
     */
    @Test
    public void testSetDestination() {
        // Test setDestination method
        Relationship relationship = new Relationship();
        relationship.setDestination("NewDestination");
        assertEquals("NewDestination", relationship.getDestination());
    }

    /**
     * Tests the {@code toString()} method with an empty constructor of the {@link Relationship} class.
     */
    @Test
    public void testToStringWithEmptyConstructor1() {
        // Test toString method with empty constructor
        Relationship relationship = new Relationship();
        String expected = "Relationship ID: null\n" +
                          "Relationship source: null\n" +
                          "Relationship destination: null\n";
        assertEquals(expected, relationship.toString());
    }

    /**
     * Tests the {@code toString()} method with set values of the {@link Relationship} class.
     */
    @Test
    public void testToStringWithSetValues() {
        // Test toString method with set values
        Relationship relationship = new Relationship();
        relationship.setId("3");
        relationship.setSource("Source");
        relationship.setDestination("Destination");
        String expected = "Relationship ID: 3\n" +
                          "Relationship source: Source\n" +
                          "Relationship destination: Destination\n";
        assertEquals(expected, relationship.toString());
    }
}
