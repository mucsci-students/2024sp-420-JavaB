import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class UMLCli {
    // Scanner object for user input
    private static final Scanner scanner = new Scanner(System.in);
    // Gson object for JSON serialization and de-serialization
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // UMLDiagram object to store the UML diagram
    private static final UMLDiagram umlDiagram = new UMLDiagram();

    // Main method
    public static void main(String[] args) {
        System.out.println("Welcome to NRDS UML Editor!");

        boolean exit = false;

        // Display menu and get user choice
        displayMenu();

        // Loop until the user chooses to exit
        while (!exit) {
            
            String choice = getUserChoice();

            // Perform actions based on user choice
            switch (choice) {
                case "menu":
                    displayMenu();
                    break;
                case "addclass":
                    createClass();
                    break;
                case "deleteclass":
                    deleteClass();
                    break;
                case "renameclass":
                    renameClass();
                    break;
                case "addattribute":
                    addAttribute();
                    break;
                case "deleteattribute":
                    deleteAttribute();
                    break;
                case "renameattribute":
                    renameAttribute();
                    break;
                case "addmethod":
                    addMethod();
                    break;
                case "deletemethod":
                    deleteMethod();
                    break;
                case "renamemethod":
                    renameMethod();
                    break;
                case "addrelationship":
                    addRelationship();
                    break;
                case "deleterelationship":
                    deleteRelationship();
                    break;
                case "changetype":
                    changeType();
                    break;
                case "listclasses":
                    listClasses();
                    break;
                case "listclass":
                    listClassContents();
                    break;
                case "listrelationships":
                    listRelationships();
                    break;
                case "save":
                    saveDiagram();
                    break;
                case "load":
                    loadDiagram();
                    break;
                case "help":
                    help();
                    break;
                case "exit":
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid command.");
                    break;
            }
        }

        // Close scanner
        scanner.close();
    }

    // Display the menu
    protected static void displayMenu() {
    	System.out.println("  ");
        System.out.println("\n		Menu:");
        System.out.println("AddClass - Creates a new class.");
        System.out.println("DeleteClass - Deletes a class.");
        System.out.println("RenameClass - Renames a class.");
        System.out.println("  ");
        System.out.println("AddAttribute - Add an attribute to a class.");
        System.out.println("DeleteAttribute - Delete an attribute from a class.");
        System.out.println("RenameAttribute - Rename an attribute in a class.");
        System.out.println("  ");
        System.out.println("AddMethod - Add a method to a class.");
        System.out.println("DeleteMethod - Delete a method from a class.");
        System.out.println("RenameMethod - Rename a method in a class.");
        System.out.println("  ");
        System.out.println("AddRelationship - Add a relationship between classes.");
        System.out.println("DeleteRelationship - Delete a relationship between classes.");
        System.out.println("ChangeType - Change the relationship type between classes. ");
        System.out.println("  ");
        System.out.println("ListClasses - List all classes.");
        System.out.println("ListClass - List contents of a specified class.");
        System.out.println("ListRelationships - List all relationships.");
        System.out.println("Save - Save diagram to JSON file.");
        System.out.println("Load - Load diagram from JSON file.");
        System.out.println("Menu - Display main menu.");
        System.out.println("Help - Help.");
        System.out.println("Exit - Exit.");
        System.out.println("  ");
        System.out.println("  ");
        System.out.print("Please enter your choice: ");
    }

    // Get user choice
    protected static String getUserChoice() {
        return scanner.next().toLowerCase().trim();
    }

    // Method to create a new class
    private static void createClass() {
        System.out.println("Enter the name of the class: ");
        String className = scanner.next();
        boolean success = umlDiagram.addClass(className);
        if (success) {
            System.out.println("Class " + className + " created successfully.");
        } else {
            System.out.println("Failed to create class " + className + ". Class may already exist.");
        }
    }

    // Method to delete a class
    protected static void deleteClass() {
        System.out.println("Enter the name of the class to delete: ");
        String className = scanner.next();
        boolean success = umlDiagram.deleteClass(className);
        if (success) {
            System.out.println("Class " + className + " deleted successfully.");
        } else {
            System.out.println("Failed to delete class " + className + ". Class may not exist.");
        }
    }

    // Method to rename a class
    protected static void renameClass() {
        System.out.println("Enter the current name of the class: ");
        String oldName = scanner.next();
        System.out.print("Enter the new name of the class: ");
        String newName = scanner.next();
        boolean success = umlDiagram.renameClass(oldName, newName);
        if (success) {
            System.out.println("Class " + oldName + " renamed to " + newName + " successfully.");
        } else {
            System.out.println("Failed to rename class " + oldName + ". Class may not exist or new name may already be in use.");
        }
    }

    // Method to add an attribute to a class
    protected static void addAttribute() {
        System.out.println("Enter the name of the class to add attribute to: ");
        String className = scanner.next();
        System.out.print("Enter the name of the attribute: ");
        String attributeName = scanner.next();
        System.out.print("Enter the type of the attribute: ");
        String attributeType = scanner.next();
        boolean success = umlDiagram.addAttribute(className, attributeName, attributeType);
        if (success) {
            System.out.println("Attribute " + attributeName + " added to class " + className + " successfully.");
        } else {
            System.out.println("Failed to add attribute to class " + className + ". Class may not exist.");
        }
    }

    // Method to delete an attribute from a class
    protected static void deleteAttribute() {
        System.out.println("Enter the name of the class to delete attribute from: ");
        String className = scanner.next();
        System.out.print("Enter the name of the attribute to delete: ");
        String attributeName = scanner.next();
        boolean success = umlDiagram.deleteAttribute(className, attributeName);
        if (success) {
            System.out.println("Attribute " + attributeName + " deleted from class " + className + " successfully.");
        } else {
            System.out.println("Failed to delete attribute from class " + className + ". Class or attribute may not exist.");
        }
    }

    // Method to rename an attribute in a class
    protected static void renameAttribute() {
        System.out.println("Enter the name of the class containing the attribute: ");
        String className = scanner.next();
        System.out.print("Enter the current name of the attribute: ");
        String oldAttributeName = scanner.next();
        System.out.print("Enter the new name of the attribute: ");
        String newAttributeName = scanner.next();
        boolean success = umlDiagram.renameAttribute(className, oldAttributeName, newAttributeName);
        if (success) {
            System.out.println("Attribute " + oldAttributeName + " renamed to " + newAttributeName + " in class " + className + " successfully.");
        } else {
            System.out.println("Failed to rename attribute in class " + className + ". Class or attribute may not exist, or new name may already be in use.");
        }
    }

    // Method to add a method to a class
    protected static void addMethod() {
        System.out.println("Enter the name of the class to add method to: ");
        String className = scanner.next();
        System.out.print("Enter the name of the method: ");
        String methodName = scanner.next();
        System.out.print("Enter the return type of the method: ");
        String returnType = scanner.next();
        boolean success = umlDiagram.addMethod(className, methodName, returnType);
        if (success) {
            System.out.println("Method " + methodName + " added to class " + className + " successfully.");
        } else {
            System.out.println("Failed to add method to class " + className + ". Class may not exist.");
        }
    }

    // Method to delete a method from a class
    protected static void deleteMethod() {
        System.out.println("Enter the name of the class to delete method from: ");
        String className = scanner.next();
        System.out.print("Enter the name of the method to delete: ");
        String methodName = scanner.next();
        boolean success = umlDiagram.deleteMethod(className, methodName);
        if (success) {
            System.out.println("Method " + methodName + " deleted from class " + className + " successfully.");
        } else {
            System.out.println("Failed to delete method from class " + className + ". Class or method may not exist.");
        }
    }

    // Method to rename a method in a class
    protected static void renameMethod() {
        System.out.println("Enter the name of the class containing the method: ");
        String className = scanner.next();
        System.out.print("Enter the current name of the method: ");
        String oldMethodName = scanner.next();
        System.out.print("Enter the new name of the method: ");
        String newMethodName = scanner.next();
        boolean success = umlDiagram.renameMethod(className, oldMethodName, newMethodName);
        if (success) {
            System.out.println("Method " + oldMethodName + " renamed to " + newMethodName + " in class " + className + " successfully.");
        } else {
            System.out.println("Failed to rename method in class " + className + ". Class or method may not exist, or new name may already be in use.");
        }
    }
    
    //Method to add a relationship to a class
    protected static void addRelationship() {
        System.out.println("Enter the name of the source class: ");
        String sourceClass = scanner.next();
        System.out.println("Enter the name of the destination class: ");
        String destinationClass = scanner.next();
        System.out.println("Choose a relationship type (Enter a number): \n1. Aggregation\n2. Composition\n3. Inheritance\n4. Realization\n");
        int type = scanner.nextInt();

        boolean success = umlDiagram.addRelationship(sourceClass, destinationClass, type);
        if (success) {
            System.out.println("Relationship added successfully.");
        } else {
            System.out.println("Failed to add relationship.");
        }
    }

    // Method to delete a relationship between classes
    protected static void deleteRelationship() {
        System.out.println("Enter the name of the first class in the relationship: ");
        String class1 = scanner.next();
        System.out.print("Enter the name of the second class in the relationship: ");
        String class2 = scanner.next();
        
        boolean success = umlDiagram.deleteRelationship(class1, class2);
        if (success) {
            System.out.println("Relationship deleted successfully between " + class1 + " and " + class2 + ".");
        } else {
            System.out.println("Failed to delete relationship between " + class1 + " and " + class2 + ".");
        }
    }

    // Method to change the type of an existing relationship
    protected static void changeType(){
        System.out.println("Enter the name of the first class in the relationship: ");
        String class1 = scanner.next();
        System.out.print("Enter the name of the second class in the relationship: ");
        String class2 = scanner.next();
        System.out.println("Enter the number for the relationship type: \n1. Aggregation\n2. Composition\n3. Inheritance\n4. Realization\n");
        int type = scanner.nextInt();

        if(type < 1 || type > 4){
            System.out.println("Invalid number chosen. ");
        }
        else {
        boolean success = umlDiagram.changeRelType(class1, class2, type);

        if(success){
            System.out.println("Relationship type changed succesfully.");
        }
        else {
            System.out.println("Failed to change type. Relationship has to exist.");
        }
        }
        
    }


 // Method to save the UML diagram to a JSON file
    protected static void saveDiagram() {
        System.out.print("Enter the file path to save the diagram (JSON format): ");
        String filePath = scanner.next().trim();
        try (FileWriter writer = new FileWriter(filePath)) {
            JsonObject jsonDiagram = new JsonObject();
            
            // Convert classes to JSON array
            JsonArray jsonClasses = new JsonArray();
            for (UMLClass umlClass : umlDiagram.getClasses()) {
                JsonObject jsonClass = new JsonObject();
                jsonClass.addProperty("name", umlClass.getName());
                
                JsonArray jsonAttributes = new JsonArray();
                for (Attribute attribute : umlClass.getAttributes()) {
                    JsonObject jsonAttribute = new JsonObject();
                    jsonAttribute.addProperty("name", attribute.getName());
                    jsonAttribute.addProperty("type", attribute.getType());
                    jsonAttributes.add(jsonAttribute);
                }
                jsonClass.add("attributes", jsonAttributes);
                
                JsonArray jsonMethods = new JsonArray();
                for (Method method : umlClass.getMethods()) {
                    JsonObject jsonMethod = new JsonObject();
                    jsonMethod.addProperty("name", method.getName());
                    jsonMethod.addProperty("returnType", method.getType());
                    jsonMethods.add(jsonMethod);
                }
                jsonClass.add("methods", jsonMethods);
                
                jsonClasses.add(jsonClass);
            }
            jsonDiagram.add("classes", jsonClasses);
            
            // Convert relationships to JSON array
            JsonArray jsonRelationships = new JsonArray();
            for (Relationship relationship : umlDiagram.getRelationships()) {
                JsonObject jsonRelationship = new JsonObject();
                jsonRelationship.addProperty("source", relationship.getSource());
                jsonRelationship.addProperty("destination", relationship.getDestination());
                jsonRelationship.addProperty("type", relationship.getType());
                jsonRelationships.add(jsonRelationship);
            }
            jsonDiagram.add("relationships", jsonRelationships);
            
            gson.toJson(jsonDiagram, writer);
            System.out.println("Diagram saved successfully to " + filePath + ".");
        } catch (IOException e) {
            System.out.println("Failed to save diagram to " + filePath + ": " + e.getMessage());
        }
    }

    // Method to load a UML diagram from a JSON file
    protected static void loadDiagram() {
        System.out.print("Enter the file path to load the diagram (JSON format): ");
        String filePath = scanner.next().trim();
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonDiagram = gson.fromJson(reader, JsonObject.class);

            // Clear existing diagram
            umlDiagram.clear();

            // Load classes
            JsonArray jsonClasses = jsonDiagram.getAsJsonArray("classes");
            for (JsonElement element : jsonClasses) {
                JsonObject jsonClass = element.getAsJsonObject();
                String className = jsonClass.get("name").getAsString();
                umlDiagram.addClass(className);

                // Load attributes
                JsonArray jsonAttributes = jsonClass.getAsJsonArray("attributes");
                for (JsonElement attrElement : jsonAttributes) {
                    JsonObject jsonAttribute = attrElement.getAsJsonObject();
                    String attributeName = jsonAttribute.get("name").getAsString();
                    String attributeType = jsonAttribute.get("type").getAsString();
                    umlDiagram.addAttribute(className, attributeName, attributeType);
                }

                // Load methods
                JsonArray jsonMethods = jsonClass.getAsJsonArray("methods");
                for (JsonElement methodElement : jsonMethods) {
                    JsonObject jsonMethod = methodElement.getAsJsonObject();
                    String methodName = jsonMethod.get("name").getAsString();
                    String returnType = jsonMethod.get("returnType").getAsString();
                    umlDiagram.addMethod(className, methodName, returnType);
                }
            }

            // Load relationships
            JsonArray jsonRelationships = jsonDiagram.getAsJsonArray("relationships");
            for (JsonElement element : jsonRelationships) {
                JsonObject jsonRelationship = element.getAsJsonObject();
                String source = jsonRelationship.get("source").getAsString();
                String destination = jsonRelationship.get("destination").getAsString();
                String tempType = jsonRelationship.get("type").getAsString();
                int type = Integer.parseInt(tempType);
                if (tempType.equals("Aggregation")) {
                	type = 1;
                }
                else if (tempType.equals("Composition")){
                	type = 2;
                }
                else if (tempType.equals("Inheritance")) {
                	type = 3;
                }
                else if (tempType.equals("Realization")) {
                	type = 4;
                }
                
                umlDiagram.addRelationship(source, destination, type);
            }

            System.out.println("Diagram loaded successfully from " + filePath + ".");
        } catch (IOException e) {
            System.out.println("Failed to load diagram from " + filePath + ": " + e.getMessage());
        }
    }


    // Method to list all classes in the UML diagram
    protected static void listClasses() {
        ArrayList<UMLClass> classes = umlDiagram.getClasses();
        System.out.println("Classes:");
        for (UMLClass umlClass : classes) {
            System.out.println("- " + umlClass.getName());
        }
        
    }

    // Method to list the contents of a specified class in the UML diagram
    protected static void listClassContents() {
        System.out.print("Enter the name of the class to list its contents: ");
        String className = scanner.next();
        UMLClass umlClass = umlDiagram.getClassNameMapToName().get(className);
        if (umlClass != null) {
            System.out.println("Attributes of class " + className + ":");
            for (Attribute attribute : umlClass.getAttributes()) {
                System.out.println("  " + attribute.getName() + ": " + attribute.getType());
            }
            System.out.println("Methods of class " + className + ":");
            for (Method method : umlClass.getMethods()) {
                System.out.println("  " + method.getName() + ": " + method.getType());
            }
        } else {
            System.out.println("Class " + className + " does not exist.");
        }
    }

    // Method to list all relationships in the UML diagram
    protected static void listRelationships() {
        ArrayList<Relationship> relationships = umlDiagram.getRelationships();
        System.out.println("Relationships:");
        for (Relationship relationship : relationships) {
            System.out.println("  " + relationship.getSource() + " --> " + relationship.getDestination());
        }
    }

    // Method to display help information about the commands supported by the UML CLI
    protected static void help() {
        System.out.println("Help:");
        System.out.println("This UML Diagram Editor CLI supports the following commands along with their arguments:\n");
        System.out.println("1. AddClass - Create a new class");
        System.out.println("   Arguments: Name of the class\n");
        System.out.println("2. DeleteClass - Delete a class");
        System.out.println("   Arguments: Name of the class to delete\n");
        System.out.println("3. RenameClass - Rename a class");
        System.out.println("   Arguments: Current name of the class, New name for the class\n");
        System.out.println("4. AddAttribute - Add an attribute to a class");
        System.out.println("   Arguments: Name of the class, Name of the attribute, Type of the attribute\n");
        System.out.println("5. DeleteAttribute - Delete an attribute from a class");
        System.out.println("   Arguments: Name of the class, Name of the attribute to delete\n");
        System.out.println("6. RenameAttribute - Rename an attribute in a class");
        System.out.println("   Arguments: Name of the class, Current name of the attribute, New name for the attribute\n");
        System.out.println("7. AddMethod - Add a method to a class");
        System.out.println("   Arguments: Name of the class, Name of the method, Return type of the method\n");
        System.out.println("8. RemoveMethod - Delete a method from a class");
        System.out.println("   Arguments: Name of the class, Name of the method to delete\n");
        System.out.println("9. ChangeMethod - Rename a method in a class");
        System.out.println("   Arguments: Name of the class, Current name of the method, New name for the method\n");
        System.out.println("10. AddRelationship - Add a relationship between classes");
        System.out.println("    Arguments: Name of the first class, Name of the second class, Type of relationship\n");
        System.out.println("11. DeleteRelationship - Delete a relationship between classes");
        System.out.println("    Arguments: Name of the first class, Name of the second class\n");
        System.out.println("12. ChangeType - Delete a relationship between classes");
        System.out.println("    Arguments: Name of the first class, Name of the second class, New selection for attribute\n");
        System.out.println("13. ListClasses - List all classes\n");
        System.out.println("14. ListRelationships - List all relationships\n");
        System.out.println("15. ListClass - List contents of a specified class");
        System.out.println("    Arguments: Name of the class to list its contents\n");
        System.out.println("16. Save - Save diagram to JSON file");
        System.out.println("    Arguments: File path to save the diagram (JSON format)\n");
        System.out.println("17. Load - Load diagram from JSON file");
        System.out.println("    Arguments: File path to load the diagram (JSON format)\n");
        System.out.println("18. Menu - Displays Main menu\n");
        System.out.println("19. Help - Displays detailed information of program\n");
        System.out.println("20. Exit - Exits program\n");
    
    }

    // Method to handle exit from the UML CLI
    protected static void exit() {
        System.out.print("Do you want to save the diagram before exiting? (yes/no): ");
        String saveChoice = scanner.next().trim().toLowerCase();
        if (saveChoice.equals("yes") || saveChoice.equals("y")) {
            saveDiagram();
            System.out.println("Diagram saved.");
        } else {
            System.out.println("Exited without Saving.");
        }
       
        System.exit(1);
    }

}
