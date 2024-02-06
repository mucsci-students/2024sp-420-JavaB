import java.util.HashMap;
import java.util.Map;

public class Attribute {

    // Fields of attribute
    private String attributeName;
    private String type;
    private String visibility;
    /**
     * This classNameMapToAttribute field is the map variable that hold the class name mapping with its attribute.
     */
    private Map<String, String> classNameMapToAttribute;

    /**
     * Constructor to new Attribute object.
     */
    public Attribute(String attributeName, String type, String visibility) {
        this.attributeName = attributeName;
        this.type = type;
        this.visibility = visibility;
        this.classNameMapToAttribute = new HashMap<>();
    }

    /**
     * @return The name the attribute.
     */
    public String getAttributeName(){
        return attributeName;
    }

    /**
     * @param attributeName new attribute name to initialize.
     */
    public void setAttributeName(String attributeName){
        this.attributeName = attributeName;
    }

    /**
     * @return the data type of the attribute.
     */
    public String type(){
        return type;
    }

    /**
     * @param type type of the attribute.
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * @return the visibility of the attribute.
     */
    public String getVisibility(){
        return visibility;
    }
    /**
     * @param visibility show whether the attribute is visible or not.(- or +).
     */
    public void setVisibility(String visibility){
        this.visibility = visibility;
    }
     @Override
     public String toString(){
        return visibility + type + ":" + attributeName;
     }

    /**
     * @return
     */
    public Map<String, String> getClassNameMapToAttribute() {
        return classNameMapToAttribute;
    }

    /** addAttribute adds attribute to an existing class.
     * @param className: It will be class object type that hold class name.
     * @param attributeName: New attribute name to br added.
     */
    public void addAttribute(String className, String attributeName) throws Exception{
        if (attributeName == null || attributeName.isEmpty() || className == null || className.isEmpty()) {
            throw new Exception("Invalid name");
        }
        if (classNameMapToAttribute.containsValue(attributeName)) {
            throw new Exception("Duplicate name");
        }
        if (!classNameMapToAttribute.containsKey(className)){
            throw new Exception("Class name doesn't exist");
        }

        classNameMapToAttribute.put(className, attributeName);
    }

    /** Delete existing attribute from the existing class.
     * @param className is the class name which the attribute going to be removed.
     * @param attributeName is an attribute name which will deleted.
     */
    public void deleteAttribute(String className, String attributeName) throws Exception {
        if (attributeName == null || attributeName.isEmpty()) {
            throw new Exception("Invalid name");
        }
        if (!classNameMapToAttribute.containsKey(attributeName)) {
            throw new Exception("Name not found");
        }
        classNameMapToAttribute.remove(attributeName);
    }

    /** Rename the existing attribute from the existing class name.
     * @param className the name class which its attribute going to rename.
     * @param oldAttributeName the name of an existing attribute which going to be renamed.
     * @param newAttributeName a name which oldAttributeName going to have new.
     */
    public void renameAttribute(String className, String oldAttributeName, String newAttributeName) throws Exception{
        if (oldAttributeName == null || oldAttributeName.isEmpty()) {
            throw new Exception("Invalid old attribute name");
        }
        if (newAttributeName == null || newAttributeName.isEmpty()){
            throw new Exception("Invalid new attribute name:");
        }
        if (!classNameMapToAttribute.containsKey(oldAttributeName)) {
            throw new Exception("Attribute name not found");
        }
        if (classNameMapToAttribute.containsKey(newAttributeName)) {
            throw new Exception("Duplicate attribute name");
        }
        if(className == null || className.isEmpty()){
            throw new Exception("Invalid class name:");
        }
        if(!classNameMapToAttribute.containsKey(className)){
            throw new Exception("Class name not found. ");
        }

        classNameMapToAttribute.put(classNameMapToAttribute.get(oldAttributeName), newAttributeName);
        classNameMapToAttribute.remove(oldAttributeName);
    }
}

