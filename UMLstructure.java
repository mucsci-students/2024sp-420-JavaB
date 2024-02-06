public interface UMLstructure { //
    // Create new classes
    void addClass(String className);

    // Remove existing classes
    void deleteClass(String className);

    // Change the name of classes
    void renameClass(String oldClassName, String newClassName);

    // Establish connections between classes
    void addRelationship(String sourceClass, String destinationClass);

    // Remove connections between classes
    void deleteRelationship(String sourceClass, String destinationClass);

    // Attach attributes to classes
    void addAttribute(String className, String attributeName);

    // Remove attributes from classes
    void deleteAttribute(String className, String attributeName);

    // Rename attributes form classes
    void addAttribute(String className, String oldAttributeName, String newAttributeName);

    // Store diagram to file
    void saveDiagramToFile(String fileName);

    // Retrieve diagram from file
    void loadDiagramFromFile(String fileName);

    // List of all classes
    void listAllClasses();

    // Listing of classes
    void listClass(String className);

    // LIst of all the relationship
    void listAllRelationships();

    // Help class
    void help();

    // Exit class
    void exit();
}
