import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface defining methods related to UML Editor.
 * These methods allow interaction with a UML structure including classes, relationships, attributes, and methods.
 */
public interface UMLStructure {

    /**
     * Retrieves all classes in the UML structure.
     *
     * @return An ArrayList containing all ClassObject instances.
     */
    ArrayList<UMLClass> getClasses();

    /**
     * Retrieves all relationships in the UML structure.
     *
     * @return An ArrayList containing all Relationship instances.
     */
    ArrayList<Relationship> getRelationships();

    /**
     * Checks if a class exists.
     *
     * @param className The name of the class to check.
     * @return true if the class exists in the structure, false otherwise.
     */
    boolean hasClass(String className);

    /**
     * Creates a new class.
     *
     * @param className The name of the class to be created.
     * @return true if the class was successfully created, false otherwise.
     */
    boolean addClass(String className);

    /**
     * Removes an existing class.
     *
     * @param className The name of the class to be removed.
     * @return true if the class was successfully removed, false otherwise.
     */
    boolean deleteClass(String className);

    /**
     * Renames an existing class.
     *
     * @param oldClassName The current name of the class.
     * @param newClassName The new name for the class.
     * @return true if the class was successfully renamed, false otherwise.
     */
    boolean renameClass(String oldClassName, String newClassName);

    /**
     * Establishes a relationship between classes.
     *
     * @param sourceClass      The name of the source class.
     * @param destinationClass The name of the destination class.
     * @param ID               The identifier for the relationship.
     * @return True if the relationship is successfully established, false otherwise.
     */
    boolean addRelationship(String sourceClass, String destinationClass, String ID);

    /**
     * Removes a relationship between classes.
     *
     * @param sourceClass      The name of the source class.
     * @param destinationClass The name of the destination class.
     * @param ID               The identifier for the relationship.
     * @return True if the relationship is successfully removed, false otherwise.
     */
    boolean deleteRelationship(String sourceClass, String destinationClass, String ID);

    /**
     * Adds an attribute to a class.
     *
     * @param className     The name of the class to which the attribute will be added.
     * @param attributeName The name of the attribute to be added.
     * @param attributeType The type of the attribute.
     * @return True if the attribute is successfully added, false otherwise.
     */
    boolean addAttribute(String className, String attributeName, String attributeType);

    /**
     * Removes an attribute from a class.
     *
     * @param className     The name of the class from which the attribute will be removed.
     * @param attributeName The name of the attribute to be removed.
     * @return True if the attribute is successfully removed, false otherwise.
     */
    boolean deleteAttribute(String className, String attributeName);

    /**
     * Renames an attribute in a class.
     *
     * @param className        The name of the class containing the attribute.
     * @param oldAttributeName The current name of the attribute.
     * @param newAttributeName The new name for the attribute.
     * @return True if the attribute is successfully renamed, false otherwise.
     */
    boolean renameAttribute(String className, String oldAttributeName, String newAttributeName);

    /**
     * Adds a method to a class.
     *
     * @param className  The name of the class to which the method will be added.
     * @param methodName The name of the method to be added.
     * @param methodType The return type of the method.
     * @return True if the method is successfully added, false otherwise.
     */
    boolean addMethod(String className, String methodName, String methodType);

    /**
     * Deletes a method from a class.
     *
     * @param className  The name of the class from which the method will be deleted.
     * @param methodName The name of the method to be deleted.
     * @return True if the method is successfully deleted, false otherwise.
     */
    boolean deleteMethod(String className, String methodName);

    /**
     * Renames a method in a class.
     *
     * @param className    The name of the class containing the method.
     * @param originalName The current name of the method.
     * @param newName      The new name for the method.
     * @return True if the method is successfully renamed, false otherwise.
     */
    boolean renameMethod(String className, String originalName, String newName);

    /**
     * Saves the UML diagram to a JSON file.
     *
     * @param fileName The name of the file to which the diagram will be saved.
     * @return True if the diagram is successfully saved, false otherwise.
     * @throws IOException if an I/O error occurs while saving the file.
     */
    boolean saveToJSON(String fileName) throws IOException;

    /**
     * Loads a UML diagram from a JSON file.
     *
     * @param fileName The name of the file from which the diagram will be loaded.
     * @return True if the diagram is successfully loaded, false otherwise.
     */
    boolean loadFromJSON(String fileName);

    /**
     * Clears all classes and relationships from the UML structure.
     */
    void clear();
}
