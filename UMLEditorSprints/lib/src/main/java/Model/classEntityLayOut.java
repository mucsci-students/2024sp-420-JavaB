package Model;


/**
 * Class stores the entities of the classes.
 */
public class classEntityLayOut extends FieldAndMethodLayOut {
    /**
     * Constructor to layout attribute and methods
     * @param name name of the object.
     * @param type data type of the object.
     */
    public classEntityLayOut(String name, String type) throws Exception {
        super(name, type);
    }

    /**
     * This method gives the string representation the of objects(attribute and methods)
     * @return String representation or covert the objects into string.
     */
    public String toString() {
        return  getName() + getType();
    }

}