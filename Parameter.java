public class Parameter extends AttributeAndMethodLayOut {

    /**
     * Initializes the parameter with a given name and type.
     *
     * @param name The name of the parameter.
     * @param type The type of the parameter.
     */
    public Parameter(String name, String type) {
        super(name, type); 
    }

    /**
     * Returns a string representation of the parameter.
     *
     * @return A string containing the name and type of the parameter.
     */
    @Override
    public String toString() {
        return "Name: " + getName() + ", Type: " + getType();
    }
}
