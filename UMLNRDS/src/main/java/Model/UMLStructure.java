package Model;


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
    // boolean hasClass(String className);

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
     * @param type             The identifier for the relationship type.
     * @return True if the relationship is successfully established, false otherwise.
     */
    boolean addRelationship(String sourceClass, String destinationClass, int type);

    /**
     * Removes a relationship between classes.
     *
     * @param sourceClass      The name of the source class.
     * @param destinationClass The name of the destination class.
     * @return True if the relationship is successfully removed, false otherwise.
     */
    boolean deleteRelationship(String sourceClass, String destinationClass);

    // add comments 
    
    boolean changeRelType(String sourceClass, String destinationClass, int type);

    /**
     * Adds an attribute to a class.
     *
     * @param className     The name of the class to which the attribute will be added.
     * @param fieldName The name of the attribute to be added.
     * @param fieldType The type of the attribute.
     * @return True if the attribute is successfully added, false otherwise.
     */
    boolean addField(String className, String fieldName, String fieldType);

    /**
     * Removes an attribute from a class.
     *
     * @param className     The name of the class from which the attribute will be removed.
     * @param attributeName The name of the attribute to be removed.
     * @return True if the attribute is successfully removed, false otherwise.
     */
    boolean deleteField(String className, String attributeName);

    /**
     * Renames an attribute in a class.
     *
     * @param className        The name of the class containing the attribute.
     * @param oldAttributeName The current name of the attribute.
     * @param newAttributeName The new name for the attribute.
     * @return True if the attribute is successfully renamed, false otherwise.
     */
    boolean renameField(String className, String oldAttributeName, String newAttributeName);

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
     * Adds a parameter to a method in a specified class.
     * 
     * @param className The name of the class containing the method.
     * @param methodName The name of the method to which the parameter will be added.
     * @param parameterName The name of the parameter to be added.
     * @param parameterType The data type of the parameter to be added.
     * @return true if the parameter was successfully added, false otherwise.
     */
    boolean addParameter(String className, String methodName, String parameterName, String parameterType);

    /**
     * Renames an existing parameter in a specified method of a class.
     * 
     * @param className The name of the class containing the method.
     * @param methodName The name of the method containing the parameter to be renamed.
     * @param oldParameterName The current name of the parameter to be renamed.
     * @param newParameterName The new name for the parameter.
     * @return true if the parameter was successfully renamed, false otherwise.
     */
    boolean renameParameter(String className, String methodName, String oldParameterName, String newParameterName);

    /**
     * Deletes a parameter from a method in a specified class.
     * 
     * @param className The name of the class containing the method.
     * @param methodName The name of the method from which the parameter will be deleted.
     * @param parameterName The name of the parameter to be deleted.
     * @return true if the parameter was successfully deleted, false otherwise.
     */
    boolean deleteParameter(String className, String methodName, String parameterName);

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
