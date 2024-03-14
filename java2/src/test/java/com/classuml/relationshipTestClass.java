package com.classuml;

import org.junit.Test;

import com.classuml.Model.Relationship;

import static org.junit.Assert.*;

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

    @Test
    public void testConstructorAndGetters2() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 5);
        assertEquals("SourceClass", relationship.getSource());
        assertEquals("DestinationClass", relationship.getDestination());
        assertEquals(null, relationship.getType());
    }
    
    @Test
    public void testConstructorAndGetters3() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("Source", "Destination", 0);
        assertEquals("Source", relationship.getSource());
        assertEquals("Destination", relationship.getDestination());
        assertEquals(null, relationship.getType());
    }

    @Test
    public void testConstructorAndGetters4() {
        // Test constructor and getter methods
        Relationship relationship = new Relationship("", "", 0);
        assertEquals("", relationship.getSource());
        assertEquals("", relationship.getDestination());
        assertEquals(null, relationship.getType());
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
     * Tests the empty constructor of the {@link Relationship} class.
     */
    @Test
    public void testEmptyConstructor() {
        // Test empty constructor
        Relationship relationship = new Relationship(null, null, 0);
        assertNull(relationship.getSource());
        assertNull(relationship.getDestination());
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

    /**
     * Tests setting the source and destination classes to null values.
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
     */
    @Test
    public void testSetNullDestination() {
        Relationship relationship = new Relationship("SourceClass", "DestinationClass", 1);
        relationship.setDestination(null);
        assertEquals("SourceClass", relationship.getSource());
        assertNull(relationship.getDestination());
    }
}
