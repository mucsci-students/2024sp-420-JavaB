import java.util.Objects;
/**
 *  Class that help to structure the lay out of attribute name and method name
 *  with their type and visibility.
 */
public class AttributeAndMethodLayOut {
    /**
     * Attributes and methods has visibility , name and type.
     *  e.g
     *    +Id: int
     *    +managerID()
     */
    private String name;
    private  String type;
    private String visibility;

    /**
     * Constructor to initializes attribute and methods with their parameter.
     * @param name The name of the object.
     * @param type Type of the object.
     * @param visibility visibility of the object.
     */
    public AttributeAndMethodLayOut(String name, String  type, String visibility){
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
    public String getVisibility(){
        return visibility;
    }

    /**
     * Set the object name with new name.
     * @param newName New name for object.
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
    public void setVisibility(String newVisibility) {
        this.visibility = newVisibility;
    }

    /**
     * This methode will check whether the instance of another object can not have same name
     * this one.
     * @param obj Object name.
     * @return true if their same name otherwise false.
     */
    public boolean equals(Object obj) {
        if(obj instanceof AttributeAndMethodLayOut){
            AttributeAndMethodLayOut check = (AttributeAndMethodLayOut) obj;
            return Objects.equals(this.name, check.name);
        }
        return false;
    }
}