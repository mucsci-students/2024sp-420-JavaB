/**
 * Represents a relationship between two classes in a UML diagram.
 */
public class Relationship {

    private String source;
    private String destination;

    /**
     * Constructs a Relationship object with specified attributes.
     *
     * @param source      The name of the source class in the relationship.
     * @param destination The name of the destination class in the relationship.
     */
    public Relationship(String source, String destination) {
        this.source = source;
        this.destination = destination;
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

    /**
     * Returns a string representation of the Relationship object.
     *
     * @return A string representation including the source and destination classes.
     */
    @Override
    public String toString() {
        return "Relationship between " + source + " and " + destination;
    }
}
