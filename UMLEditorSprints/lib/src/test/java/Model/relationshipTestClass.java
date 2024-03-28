package Model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class relationshipTestClass {

    /**
     * Tests the constructor and getter methods of the {@link Relationship} class.
     */
    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 1);
        assertEquals("SourceClass", relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
        assertEquals("Aggregation", relationship.getType());
    }

    /**
     * Tests the setter methods of the {@link Relationship} class.
     */
    @Test
    public void testSetters() {
        // Test setter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 2);
        relationship.setSource("NewSourceClass");
        relationship.setDestination("NewDestinationClass");
        relationship.changeRelType(4);
        assertEquals("NewSourceClass", relationship.getSource());
        assertEquals("NewDestinationClass", relationship.getDestination());
        assertEquals("Realization", relationship.getType());
    }

    /**
     * Tests the setter methods of the {@link Relationship} class.
     */
    @Test
    public void testSettersBadClass() {
        // Test setter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 2);
        relationship.setSource("NewSourceClass");
        relationship.setDestination("NewDestinationClass");
        relationship.changeRelType(4);
        assertEquals("NewSourceClass", relationship.getSource());
        assertEquals("NewDestinationClass", relationship.getDestination());
        assertEquals("Composition", relationship.getType());
    }
    
    /**
     * Tests the setter methods of the {@link Relationship} class.
     */
    @Test
    public void testSettersBadType() {
        // Test setter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 2);
        relationship.setSource("NewSourceClass");
        relationship.setDestination("NewDestinationClass");
        relationship.changeRelType(5);
        assertEquals("NewSourceClass", relationship.getSource());
        assertEquals("NewDestinationClass", relationship.getDestination());
        assertEquals("Composition", relationship.getType());
    }
    
    /**
     * Tests the {@code toString()} method of the {@link Relationship} class.
     */
    @Test
    public void testToString() {
        // Test toString method
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 1);
        String expected = "Relationship between SourceClass and DestinationClass";
        assertEquals(expected, relationship.toString());
    }


    /**
     * Tests setting the source and destination classes separately.
     */
    @Test
    public void testSetSourceAndDestinationSeparately() {
        Relationship relationship = new Relationship(null, null, 0);
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
        Relationship relationship = new Relationship(null, null, 0);
        String classValue = "SourceDestinationClass";
        relationship.setSource(classValue);
        relationship.setDestination(classValue);
        assertEquals(classValue, relationship.getSource());
        assertEquals(classValue, relationship.getDestination());
    }

}