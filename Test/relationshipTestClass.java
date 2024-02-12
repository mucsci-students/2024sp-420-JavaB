import org.junit.Test;
import static org.junit.Assert.*;

public class relationshipTestClass {

    /**
     * Tests the constructor and getter methods of the {@link Relationship} class.
     */
    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass");
        assertEquals("SourceClass", relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
    }

    /**
     * Tests the setter methods of the {@link Relationship} class.
     */
    @Test
    public void testSetters() {
        // Test setter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass");
        relationship.setSource("NewSourceClass");
        relationship.setDestination("NewDestinationClass");
        assertEquals("NewSourceClass", relationship.getSource());
        assertEquals("NewDestinationClass", relationship.getDestination());
    }

    /**
     * Tests the {@code toString()} method of the {@link Relationship} class.
     */
    @Test
    public void testToString() {
        // Test toString method
        Relationship relationship = new Relationship("SourceClass", "DestinationClass");
        String expected = "Relationship between SourceClass and DestinationClass";
        assertEquals(expected, relationship.toString());
    }

    /**
     * Tests the empty constructor of the {@link Relationship} class.
     */
    @Test
    public void testEmptyConstructor() {
        // Test empty constructor
        Relationship relationship = new Relationship(null, null);
        assertNull(relationship.getSource());
        assertNull(relationship.getDestination());
    }

    /**
     * Tests setting the source and destination classes separately.
     */
    @Test
    public void testSetSourceAndDestinationSeparately() {
        Relationship relationship = new Relationship(null, null);
        relationship.setSource("SourceClass");
        relationship.setDestination("DestinationClass");
        assertEquals("SourceClass", relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
    }

    /**
     * Tests setting the source and destination classes to the same value.
     */
    @Test
    public void testSetSameSourceAndDestination() {
        Relationship relationship = new Relationship(null, null);
        String classValue = "SourceDestinationClass";
        relationship.setSource(classValue);
        relationship.setDestination(classValue);
        assertEquals(classValue, relationship.getSource());
        assertEquals(classValue, relationship.getDestination());
    }

    /**
     * Tests setting the source and destination classes to null values.
     */
    @Test
    public void testSetNullSourceAndDestination() {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass");
        relationship.setSource(null);
        relationship.setDestination(null);
        assertNull(relationship.getSource());
        assertNull(relationship.getDestination());
    }

    /**
     * Tests setting the source class to null.
     */
    @Test
    public void testSetNullSource() {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass");
        relationship.setSource(null);
        assertNull(relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
    }

    /**
     * Tests setting the destination class to null.
     */
    @Test
    public void testSetNullDestination() {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass");
        relationship.setDestination(null);
        assertEquals("SourceClass", relationship.getSource());
        assertNull(relationship.getDestination());
    }
}

