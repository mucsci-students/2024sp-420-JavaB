/**
 * Represents a relationship between two classes in a UML diagram.
 */
public class Relationship {

    private String ID;
    private String source;
    private String destination;

    /**
     * Constructs an empty Relationship object.
     */
    public Relationship() {

    }

    /**
     * Constructs a Relationship object with specified attributes.
     *
     * @param source      The name of the source class in the relationship.
     * @param destination The name of the destination class in the relationship.
     * @param id          The identifier for the relationship.
     */
    public Relationship(String source, String destination, String id) {
        this.source = source;
        this.destination = destination;
        ID = id;
    }

    /**
     * Sets the identifier for the relationship.
     *
     * @param ID The identifier to set.
     */
    public void setId(String ID) {
        this.ID = ID;
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
     * Gets the identifier for the relationship.
     *
     * @return The identifier of the relationship.
     */
    public String getId() {
        return ID;
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
     * @return A string representation including the relationship ID, source and destinatione.
     */
    
    public String toString() {
        return "Relationship ID: " + ID + "\n" +
                "Relationship source: " + source + "\n" +
                "Relationship destination: " + destination + "\n";
    }
}
