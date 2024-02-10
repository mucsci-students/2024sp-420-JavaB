import java.util.Objects;
/**
 *  Class that helps to structure the layout of attribute name and method name
 *  with their type and visibility.
 */
public class AttributeAndMethodLayOut {
    /**
     * Attributes and methods have name and type.
     *  e.g
     *    +Id: int
     *    +managerID()
     */
    private String name;
    private  String type;

    /**
     * Constructor to initialize attributes and methods with their parameter.
     * @param name The name of the object.
     * @param type Type of the object.
     */
    public AttributeAndMethodLayOut(String name, String  type){
        
        this.name = name;
        this.type = type;
    }

    /**
     * Get the name of the object.
     * @return Then of the object.
     */
    public String getName(){
        return name;
    }

    /**
     * Get the data type of the object.
     * @return the type of the object.
     */
    public String getType(){
        return type;
    }

    /**
     * Set a new type for the object.
     * @param newType New type name for the object be set.
     */
    public void setType(String newType) {
        this.type = newType;
    }

    /**
     * This method will check whether the instance of another object can not have the same name
     * this one.
     * @param obj Object name.
     * @return true if their same name is otherwise false.
     */
    public boolean equals(Object obj) {
        if(obj instanceof AttributeAndMethodLayOut){
            AttributeAndMethodLayOut check = (AttributeAndMethodLayOut) obj;
            return Objects.equals(this.name, check.name);
        }
        return false;
    }
}
