public class Field extends AttributeAndMethodLayOut {
    /**
     * Initializes a field object that contains a name and a type.
     *
     * @param name The name of the field.
     * @param type The type of the field.
     */
    public Field(String name, String type) {
        super(name, type);
    }

    /**
     * A method that returns a string of the name and type of the field.
     */
    public String toString() {
        return "Name: " + getName() + " Type: " + getType();
    }
}
