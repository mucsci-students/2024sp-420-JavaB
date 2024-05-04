package com.classuml.Model;

import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

/**
 * Represents a relationship between two classes in a UML diagram.
 */
public class Relationship {

    private String source;
    private String destination;
    private int relationType;

    public boolean rerouted;
    public List<Point[]> drawnLines = new ArrayList<Point[]>();

   
    /**
     * Constructs a Relationship object with specified attributes.
     *
     * @param source      The name of the source class in the relationship.
     * @param destination The name of the destination class in the relationship.
     */
   
    /**
     * Constructs a Relationship object with specified attributes.
     *
     * @param source      The name of the source class in the relationship.
     * @param destination The name of the destination class in the relationship.
     */
    public Relationship(String source, String destination, int type) { 
        this.source = source;
        this.destination = destination;
        this.relationType = type;       
    }
    
    // A method to get a string representation of the relationship type
    public static String getTypeAsString(int relationType) {
        switch (relationType) {
            case 1:
                return "Aggregation";
            case 2:
                return "Composition";
            case 3:
                return "Inheritance";
            case 4:
                return "Realization";
            default:
                return "Unknown";
        }
    }

    /**
     * Sets the source class in the relationship.
     *
     * @param source The name of the source class to set.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Sets the destination class in the relationship.
     *
     * @param destination The name of the destination class to set.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the name of the source class in the relationship.
     *
     * @return The name of the source class.
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets the name of the destination class in the relationship.
     *
     * @return The name of the destination class.
     */
    public String getDestination() {
        return destination;
    }

    public int getType(){
        return relationType;
    }

    
    //add comments
    public void changeRelType(int type) {
        // Directly switch on the int type without converting to a String
        switch (type) {
            case 1:
                // Assign the integer representing the relationship type
                relationType = 1; // Aggregation
                break;
            case 2:
                this.relationType = 2; // Composition
                break;
            case 3:
                this.relationType = 3; // Inheritance
                break;
            case 4:
                this.relationType = 4; // Realization
                break;
            default:
                // Optionally handle invalid types
                System.out.println("Invalid relationship type");
                break;
        }
    }

    /**
     * Returns a string representation of the Relationship object.
     *
     * @return A string representation including the source and destination classes.
     */ 
    @Override
    public String toString() {
        // Including type description in the toString output
        return "Relationship is (" + getTypeAsString(relationType) + ")" + "\nbetween " + source + " and " + destination;
    }
}