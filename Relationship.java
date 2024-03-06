/**
 * Represents a relationship between two classes in a UML diagram.
 */
public class Relationship {

    private String source;
    private String destination;
    private String relationType;

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
    	String typeString = type + "";
    		switch (typeString){
            case "1":
            this.relationType = "Aggregation";
            break;

            case "2":
            this.relationType = "Composition";
            break;

            case "3":
            this.relationType = "Inheritance";
            break;

            case "4":
            this.relationType = "Realization";
            break;
            default : 
            	this.relationType = null;
            	break;
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

    public String getType(){
        return relationType;
    }
    
    //add comments

    public void changeRelType(String sourceClass, String destinationClass, int type){
    	if ((type > 0 && type < 5) && !sourceClass.equals(destinationClass) && this.getSource().equals(sourceClass) &&
    			this.getDestination().equals(destinationClass)) {
        String typeString = type + "";
        switch (typeString){
            case "1":
            this.relationType = "Aggregation";
            break;

            case "2":
            this.relationType = "Composition";
            break;

            case "3":
            this.relationType = "Inheritance";
            break;

            case "4":
            this.relationType = "Realization";
            break;
        }
    	}
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
