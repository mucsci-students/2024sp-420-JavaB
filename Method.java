/**
 * Represents a method in a UML diagram.
 */
public class Method extends AttributeAndMethodLayOut {

    /**
     * Initializes a method with a specific name and type.
     *
     * @param String - Name of the method.
     * @param String - Type of the method.
     * 
     * Neither param can be an empty string nor contain an empty space, or be null.
     * 
     */
    public Method(String name, String type) {
        super(name, type);
    }

    /**
     * A string representation of the method.
     *  
     * No parameters.
     * 
     * @return A string representation of the method.
     */
    @Override
    public String toString() {
        return "Name: " + getName() + " Type: " + getType();
    }
}
