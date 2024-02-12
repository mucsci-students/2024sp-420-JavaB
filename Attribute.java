import java.util.Objects;

/**
 * A class that represents an attribute of a UML class.
 */
public class Attribute extends AttributeAndMethodLayOut {

    /**
     * Initializes an attribute object with a name, type, and className.
     * @param name The name of the attribute.
     * @param type The type of the attribute.
     * @param className 
     */
    public Attribute(String name, String type, String className) {
        super(name, type);
    }

    /**
     * Returns a string representation of the attribute containing its name and type.
     */
    public String toString() {
        return  getName() + ": " + getType();
    }

    /**
     * Checks whether this attribute is equal to another object.
     * Attributes are considered equal if they have the same name.
     * @param obj The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof AttributeAndMethodLayOut) {
            AttributeAndMethodLayOut check = (AttributeAndMethodLayOut) obj;
            return Objects.equals(this.getName(), check.getName());
        }
        return false;
    }
}
