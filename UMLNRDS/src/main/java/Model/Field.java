package Model;


public class Field extends FieldAndMethodLayOut {
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
     * A method that returns a string of the name and type of the field on separate lines.
     */
    public String toString() {
        return "\n" +
        		"    Name: " + getName() + ",\n" +
        				"    Type: " + getType();
    }

}
 