import java.util.Objects;
/**
 *  Class that help to structure the lay out of attribute name and method name
 *  with their type.
 */
public class Attribute {
    /**
     * Attributes and methods hasname and type.
     *  e.g
     *    +Id: int
     *    +managerID()
     */
    private String name;
    private  String type;
   

    /**
     * Constructor to initializes attribute and methods with their parameter.
     * @param name The name of the object.
     * @param type Type of the object.
     */
    public Attribute(String name, String  type){
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
     * This methode will check whether the instance of another object can not have same name
     * this one.
     * @param obj Object name.
     * @return true if their same name otherwise false.
     */
    public boolean equals(Object obj) {
        if(obj instanceof Attribute){
            Attribute check = (Attribute) obj;
            return Objects.equals(this.name, check.name);
        }
        return false;
    }
}
