import java.util.Objects;
/**
 *  Class that helps to structure the layout of attribute name and method name
 *  with their type and visibility.
 */
public class AttributeAndMethodLayOut {
    /**
     * Attributes and methods have visibility, name, and type.
     *  e.g
     *    +Id: int
     *    +managerID()
     */
    private String name;
    private  String type;
    private Boolean visibility;

    /**
     * Constructor to initialize attributes and methods with their parameter.
     * @param name The name of the object.
     * @param type Type of the object.
     * @param visibility visibility of the object.
     */
    public AttributeAndMethodLayOut(String name, String  type, Boolean visibility){
        this.name = name;
        this.type = type;
        this.visibility = visibility;
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
     * Get the visibility of the object.
     * @return the visibility of the object.
     */
    public Boolean getVisibility(){
        return visibility;
    }

    /**
     * Set the object name with a new name.
     * @param newName New name for an object.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Set a new type for the object.
     * @param newType New type name for the object be set.
     */
    public void setType(String newType) {
        this.type = newType;
    }

    /**
     * Set a new visibility for the object.
     * @param newVisibility new visibility for the object.
     */
    public void setVisibility(Boolean newVisibility) {
        this.visibility = newVisibility;
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
