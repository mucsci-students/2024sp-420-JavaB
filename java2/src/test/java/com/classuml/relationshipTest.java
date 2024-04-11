package com.classuml;


import com.classuml.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.List;


public class relationshipTest {
    private Relationship relationship;

    @BeforeEach
    void setUp() {
        relationship = new Relationship("SourceClass", "DestinationClass", 1);
    }

    /**
     * Tests the constructor and getter methods of the {@link Relationship} class.
     */
    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 1);
        assertEquals("SourceClass", relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
        assertEquals("Aggregation", relationship.getTypeAsString(relationship.getType()));
    }
/**{@link Relationship} */
    @Test
    public void testConstructorAndGetters2() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 5);
        assertEquals("SourceClass", relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
        assertEquals(5, relationship.getType());
    }
    /**{@link Relationship} */
    @Test
    public void testConstructorAndGetters3() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("Source", "Destination", 0);
        assertEquals("Source", relationship.getSource());
        assertEquals("Destination", relationship.getDestination());
        assertEquals(0, relationship.getType());
    }
/**{@link Relationship} */
    @Test
    public void testConstructorAndGetters4() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("", "", 0);
        assertEquals("", relationship.getSource());
        assertEquals("", relationship.getDestination());
        assertEquals(0, relationship.getType());
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
        assertEquals("Realization", relationship.getTypeAsString(relationship.getType()));
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
        assertEquals("Realization",relationship.getTypeAsString(relationship.getType()));
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
        assertEquals("Composition", relationship.getTypeAsString(relationship.getType()));
    }
    
    /**
     * Tests the {@code toString()} method of the {@link Relationship} class.
     */
    @Test
    public void testToString() {
        // Test toString method
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 1);
        String expected = "Relationship is (Aggregation)\nbetween SourceClass and DestinationClass";
        assertEquals(expected, relationship.toString());
    }

    /**
     * Tests the empty constructor of the {@link Relationship} class.
     */
    @Test
    public void testEmptyConstructor() {
        // Test empty constructor
        Relationship relationship = new Relationship(null, null, 0);
        assertNull(relationship.getSource());
        assertNull(relationship.getDestination());
    }
    @Test
    public void testChangeRelTypes()
    {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 2);
        relationship.setSource("NewSourceClass");
        relationship.setDestination("NewDestinationClass");
        relationship.changeRelType(1);
        assertEquals("NewSourceClass", relationship.getSource());
        assertEquals("NewDestinationClass", relationship.getDestination());
        assertEquals("Aggregation", relationship.getTypeAsString(relationship.getType()));
    }
    @Test
    public void testChangeRelTypes2()
    {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 3);
        relationship.setSource("NewSourceClass");
        relationship.setDestination("NewDestinationClass");
        relationship.changeRelType(2);
        assertEquals("NewSourceClass", relationship.getSource());
        assertEquals("NewDestinationClass", relationship.getDestination());
        assertEquals("Composition", relationship.getTypeAsString(relationship.getType()));
    }
    @Test
    public void testChangeRelTypes3()
    {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 2);
        relationship.setSource("NewSourceClass");
        relationship.setDestination("NewDestinationClass");
        relationship.changeRelType(3);
        assertEquals("NewSourceClass", relationship.getSource());
        assertEquals("NewDestinationClass", relationship.getDestination());
        assertEquals("Inheritance", relationship.getTypeAsString(relationship.getType()));
    }

    /**
     * Tests setting the source and destination classes separately. {@link Relationship}
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
     * {@link Relationship}
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

    /**
     * Tests setting the source and destination classes to null values.
     * {@link Relationship}
     */
    @Test
    public void testSetNullSourceAndDestination() {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 1);
        relationship.setSource(null);
        relationship.setDestination(null);
        assertNull(relationship.getSource());
        assertNull(relationship.getDestination());
    }

    /**
     * Tests setting the source class to null.
     * {@link Relationship}
     */
    @Test
    public void testSetNullSource() {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 1);
        relationship.setSource(null);
        assertNull(relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
    }

    /**
     * Tests setting the destination class to null.
     * {@link Relationship}
     */
    @Test
    public void testSetNullDestination() {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 1);
        relationship.setDestination(null);
        assertEquals("SourceClass", relationship.getSource());
        assertNull(relationship.getDestination());
    }
}
