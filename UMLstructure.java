import java.util.ArrayList;

/**
 * Interface defining methods related to UML Editor
 */
public interface UMLStructure {
    /**
     * Creates a new class.
     * @param className The name of the class to be created.
     */
    void addClass(String className);

    /**
     * Removes an existing class.
     * @param className The name of the class to be removed.
     */
    void deleteClass(String className);

    /**
     * Renames an existing class.
     * @param oldClassName The current name of the class.
     * @param newClassName The new name for the class.
     */
    void renameClass(String oldClassName, String newClassName);

    /**
     * Establishes a relationship between classes.
     * @param sourceClass      The name of the source class.
     * @param destinationClass The name of the destination class.
     * @param ID               The identifier for the relationship.
     * @param newType          The type of the relationship.
     * @return True if the relationship is successfully established, false otherwise.
     */
    boolean addRelationship(String sourceClass, String destinationClass, String ID, String newType);

    /**
     * Removes a relationship between classes.
     * @param sourceClass      The name of the source class.
     * @param destinationClass The name of the destination class.
     * @param ID               The identifier for the relationship.
     * @return True if the relationship is successfully removed, false otherwise.
     */
    boolean deleteRelationship(String sourceClass, String destinationClass, String ID);

    /**
     * Adds an attribute to a class.
     * @param className     The name of the class to which the attribute will be added.
     * @param attributeName The name of the attribute to be added.
     */
    void addAttribute(String className, String attributeName);

    /**
     * Removes an attribute from a class.
     * @param className     The name of the class from which the attribute will be removed.
     * @param attributeName The name of the attribute to be removed.
     * @return True if the attribute is successfully removed, false otherwise.
     */
    boolean deleteAttribute(String className, String attributeName);

    /**
     * Renames an attribute in a class.
     * @param className        The name of the class containing the attribute.
     * @param oldAttributeName The current name of the attribute.
     * @param newAttributeName The new name for the attribute.
     * @return True if the attribute is successfully renamed, false otherwise.
     */
    boolean renameAttribute(String className, String oldAttributeName, String newAttributeName);

    /**
     * Stores the diagram to a file.
     * @param fileName The name of the file to which the diagram will be saved.
     */
    void saveDiagramToFile(String fileName);

    /**
     * Retrieves the diagram from a file.
     * @param fileName The name of the file from which the diagram will be loaded.
     */
    void loadDiagramFromFile(String fileName);

    /**
     * Retrieves all classes in the UML structure.
     * @return An ArrayList containing all ClassObject instances.
     */
    ArrayList<classDiagram> getClasses();

    /**
     * Retrieves all relationships in the UML structure.
     * @return An ArrayList containing all Relationship instances.
     */
    ArrayList<Relationship> getRelationships();

    /**
     * Checks if a class exists.
     * @param className The name of the class to check.
     */
    void hasClass(String className);
}
