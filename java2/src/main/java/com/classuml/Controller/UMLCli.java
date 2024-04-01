package com.classuml.Controller;

import java.io.IOException;
import jline.console.ConsoleReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.classuml.Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class UMLCli {
	// Scanner object for user input
	//private static final Scanner scanner = new Scanner(System.in);
	private static ConsoleReader reader;
	// Gson object for JSON serialization and de-serialization
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	// UMLDiagram object to store the UML diagram
	private static final UMLDiagram umlDiagram = new UMLDiagram();

	
/**************************************************************************************************************************************/
    /**   MENU DISPLAY   **/
/**************************************************************************************************************************************/

	// Main method
	public static void launch() throws IOException {

	reader = getConsoleReader();
	reader.addCompleter(new UMLCompleter());

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
      case "ac":
        createClass();
        break;
      case "deleteclass":
      case "dc":
        deleteClass();
        break;
      case "renameclass":
      case "rc":
        renameClass();
        break;
      case "addparameter":
      case "ap":
        addParameter();
        break;
      case "deleteparameter":
      case "dp":
        deleteParameter();
        break;
      case "renameparameter":
      case "rp":
        renameParameter();
        break;
      case "replaceparams":
        replaceParams();
        break;
      case "addfield":
      case "af":
        addField();
        break;
      case "renamefield":
      case "rf":
        renameField();
        break;
      case "deletefield":
      case "df":
        deleteField();
        break;
      case "addmethod":
      case "am":
        addMethod();
        break;
      case "deletemethod":
      case "dm":
        deleteMethod();
        break;
      case "renamemethod":
      case "rm":
        renameMethod();
        break;
      case "addrelationship":
      case "ar":
        addRelationship();
        break;
      case "deleterelationship":
      case "dr":
        deleteRelationship();
        break;
      case "changetype":
      case "ct":
        changeType();
        break;
      case "listclasses":
      case "lcs":
        listClasses();
        break;
      case "listclass":
      case "lc":
        listClassContents();
        break;
      case "listrelationships":
      case "lr":
        listRelationships();
        break;
      case "save":
      case "s":
        saveDiagram();
        break;
      case "load":
      case "lo":
        loadDiagram();
        break;
      case "help":
      case "h":
        help();
        break;
      case "exit":
        exit();
        break;
	  case "undo":
	    undo();
	    break;
	  case "redo":
	    redo();
	    break;
      default:
        System.out.println("Invalid choice. Please enter a valid command.");
        break;
      	}
	  }
	}

	// Display the menu
    protected static void displayMenu() {
		System.out.println("  ");
		System.out.println("\n		Menu:");
		System.out.println("AddClass (ac) - Creates a new class.");
		System.out.println("DeleteClass (dc)- Deletes a class.");
		System.out.println("RenameClass (rc)- Renames a class.");
		System.out.println("  ");
		System.out.println("AddParameter (ap)- Add a Parameter to a class.");
		System.out.println("DeleteParameter (dp)- Delete a parameter from a class.");
		System.out.println("RenameParameter (rp)- Rename a parameter in a class.");
		System.out.println("ReplaceParams - Replaces params of a method with a new list of parameters.");
		System.out.println("  ");
		System.out.println("AddField (af)- Add a Field to a class.");
		System.out.println("DeleteField (df)- Delete a Field from a class.");
		System.out.println("RenameField (rf)- Rename a Field in a class.");
		System.out.println("ChangeFieldType (cft)- Change the type of a Field in a class.");
		System.out.println("  ");
		System.out.println("AddMethod (am)- Add a method to a class.");
		System.out.println("DeleteMethod (dm)- Delete a method from a class.");
		System.out.println("RenameMethod (rm)- Rename a method in a class.");
		System.out.println("  ");
		System.out.println("AddRelationship (ar)- Add a relationship between classes.");
		System.out.println("DeleteRelationship (dr)- Delete a relationship between classes.");
		System.out.println("ChangeType (ct)- Change the relationship type between classes. ");
		System.out.println("  ");
		System.out.println("ListClasses (lcs)- List all classes.");
		System.out.println("ListClass (lc)- List contents of a specified class.");
		System.out.println("ListRelationships (lr)- List all relationships.");
		System.out.println("Save (s)- Save diagram to JSON file.");
		System.out.println("Load (lo)- Load diagram from JSON file.");
		System.out.println("Menu (m)- Display main menu.");
		System.out.println("Help (h)- Help.");
		System.out.println("Exit - Exit.");
		System.out.println("Undo - undos the last command.");
		System.out.println("Redo - goes back to state before previous undo.");
		System.out.println("  ");
    }

	private static ConsoleReader getConsoleReader() throws IOException {
		ConsoleReader creader = new ConsoleReader();
		creader.setPrompt("\n> ");
		return creader;
	}

	// Get user choice
	private static String getUserChoice() throws IOException {
		//return scanner.nextLine().toLowerCase().trim();
		return reader.readLine().toLowerCase().trim();
	}

	private static String getReaderValue() throws IOException {
		return reader.readLine();
	}

	//Calls undo.
	protected static void undo() {
		boolean success = umlDiagram.undo();
		if (success) {
			System.out.println("Undo worked!");
		} else {
			System.out.println("There was nothing to undo!");
		}
	}
	//Calls redo.
	protected static void redo() {
		boolean success = umlDiagram.redo();
		if (success) {
			System.out.println("Redo worked!");
		} else {
			System.out.println("There was nothing to redo!");
		}
	}

/**************************************************************************************************************************************/
    /**   CLASSES   **/
/** @throws IOException ************************************************************************************************************************************/

	// Method to create a new class
	protected static void createClass() throws IOException {
		System.out.println("Enter the name of the class: ");
		String className = getReaderValue();
		boolean success = umlDiagram.addClass(className);
		if (success) {
			System.out.println("Class " + className + " created successfully.");
		} else {
			System.out.println("Failed to create class " + className + ". Class may already exist.");
		}
	}

	// Method to delete a class
	protected static void deleteClass() throws IOException {
		System.out.println("Enter the name of the class to delete: ");
		String className = getReaderValue();
		boolean success = umlDiagram.deleteClass(className);
		if (success) {
			System.out.println("Class " + className + " deleted successfully.");
		} else {
			System.out.println("Failed to delete class " + className + ". Class may not exist.");
		}
	}

	// Method to rename a class
	protected static void renameClass() throws IOException {
		System.out.println("Enter the current name of the class: ");
		String oldName = getReaderValue();
		System.out.print("Enter the new name of the class: ");
		String newName = getReaderValue();
		boolean success = umlDiagram.renameClass(oldName, newName);
		if (success) {
			System.out.println("Class " + oldName + " renamed to " + newName + " successfully.");
		} else {
			System.out.println(
					"Failed to rename class " + oldName + ". Class may not exist or new name may already be in use.");
		}
	}
	
/**************************************************************************************************************************************/
    /**   PARAMETERS   **/ 
/** @throws IOException *************************************************************************************************************************************/

	// Method to add a parameter to a class
  protected static void addParameter() throws IOException {
    System.out.println("Enter the name of the class to add parameter to: ");
    String className = getReaderValue();
    System.out.print("Enter the name of the method to add a parameter: ");
    String methodName = getReaderValue();
    System.out.print("Enter the parameter name: ");
    String paramName = getReaderValue();
    System.out.print("Enter the name of parameter type: ");
    String typeName = getReaderValue();
    boolean success = umlDiagram.addParameter(className, methodName, paramName, typeName);
     if (success) {
      System.out.println("Parameter " + paramName + " added to class " + className + " successfully.");
    } else {
      System.out.println("Failed to add parameter to class " + className + ". Class or method may not exist.");
    }
  }

  // Method to delete a parameter from a class
  protected static void deleteParameter() throws IOException {
    System.out.println("Enter the name of the class to delete parameter from: ");
    String className = getReaderValue();
    System.out.println("Enter the name of the method to to delete parameter from: ");
    String methodName = getReaderValue();
    System.out.print("Enter the name of the parameter to delete: ");
    String paramName = getReaderValue();
    boolean success = umlDiagram.deleteParameter(className, methodName, paramName);
    if (success) {
        System.out.println("Parameter " + paramName + " deleted from class " + className + " successfully.");
    } else {
        System.out.println("Failed to delete parameter from class " + className + ". Class or method may not exist.");
    }
  }

  // Method to rename a parameter in a class
  protected static void renameParameter() throws IOException {
      System.out.println("Enter the name of the class containing the parameter: ");
      String className = getReaderValue();
      System.out.println("Enter the name of the method to to delete parameter from: ");
      String methodName = getReaderValue();
      System.out.print("Enter the current name of the parameter: ");
      String oldParamName = getReaderValue();
      System.out.print("Enter the new name of the parameter: ");
      String newParamName = getReaderValue();
      boolean success = umlDiagram.renameParameter(className, methodName, oldParamName, newParamName);
      if (success) {
          System.out.println("Parameter " + oldParamName + " renamed to " + newParamName + " in class " + className + " successfully.");
      } else {
          System.out.println("Failed to rename Parameter in class " + className + ". Class or Parameter may not exist, or new name may already be in use.");
      }
  }
  
	protected static void replaceParams() throws IOException {
		System.out.println("Enter the name of the class containing the parameters to be replaced: ");
    String className = getReaderValue();
    System.out.println("Enter the name of the method to to replace parameters in: ");
    String methodName = getReaderValue();
		System.out.println("Please type the name and type of the first param in the format: name type");
		String paramList = getReaderValue();
		boolean done = false;
		while(!done)
		{
			System.out.println("Please type the next name and type, or type 'done' to stop.");
			String nextLine = getReaderValue();
			if(nextLine.equals("done"))
			{
				done = true;
			}
			else
			{
				paramList = paramList.concat("\n");
				paramList = paramList.concat(nextLine);
			}
		}
		String[] paramsSep = paramList.split("\n");
		String[] paramNames = new String[paramsSep.length];
		String[] paramTypes = new String[paramsSep.length];
		int i = 0;
		for (String param : paramsSep) {
			String[] paramSplit = param.split(" ");
			paramNames[i] = paramSplit[0];
			paramTypes[i] = paramSplit[1];
			i++;			
		}
		if(umlDiagram.replaceParameters(className, methodName, paramNames, paramTypes))
		{
			System.out.println("Parameters were successfully replaced!");
		}
		else
		{
			System.out.println("Parameters failed to be completely replaced, class or method may not exist, or duplicate names might've been listed.");
			umlDiagram.clearParameters(className, methodName);
		}
	}
	
    
/**************************************************************************************************************************************/
    /**   FIELDS   **/
/** @throws IOException *************************************************************************************************************************************/


	// Method to add an attribute to a class
	protected static void addField() throws IOException {
		System.out.println("Enter the name of the class to add field to: ");
		String className = getReaderValue();
		System.out.print("Enter the name of the field: ");
		String fieldName = getReaderValue();
		System.out.print("Enter the type of the field: ");
		String fieldType = getReaderValue();
		boolean success = umlDiagram.addField(className, fieldName, fieldType);
		if (success) {
			System.out.println("Field " + fieldName + " added to class " + className + " successfully.");
		} else {
			System.out.println("Failed to add field to class " + className + ". Class may not exist.");
		}
	}

	// Method to delete an attribute from a class
	protected static void deleteField() throws IOException {
		System.out.println("Enter the name of the class to delete field from: ");
		String className = getReaderValue();
		System.out.print("Enter the name of the field to delete: ");
		String fieldName = getReaderValue();
		boolean success = umlDiagram.deleteField(className, fieldName);
		if (success) {
			System.out.println("Field " + fieldName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println(
					"Failed to delete field from class " + className + ". Class or field may not exist.");
		}
	}

	// Method to rename an attribute in a class
	protected static void renameField() throws IOException {
		System.out.println("Enter the name of the class containing the field: ");
		String className = getReaderValue();
		System.out.print("Enter the current name of the field: ");
		String oldFieldName = getReaderValue();
		System.out.print("Enter the new name of the field: ");
		String newFieldName = getReaderValue();
		boolean success = umlDiagram.renameField(className, oldFieldName, newFieldName);
		if (success) {
			System.out.println("Field " + oldFieldName + " renamed to " + newFieldName + " in class "
					+ className + " successfully.");
		} else {
			System.out.println("Failed to rename field in class " + className
					+ ". Class or field may not exist, or new name may already be in use.");
		}
	}
	
	
/**************************************************************************************************************************************/
    /**   METHODS   **/
/** @throws IOException ************************************************************************************************************************************/


	// Method to add a method to a class
	protected static void addMethod() throws IOException {
		System.out.println("Enter the name of the class to add method to: ");
		String className = getReaderValue();
		System.out.print("Enter the name of the method: ");
		String methodName = getReaderValue();
		System.out.print("Enter the return type of the method: ");
		String returnType = getReaderValue();
		boolean success = umlDiagram.addMethod(className, methodName, returnType);
		if (success) {
			System.out.println("Method " + methodName + " added to class " + className + " successfully.");
		} else {
			System.out.println("Failed to add method to class " + className + ". Class may not exist.");
		}
	}

	// Method to delete a method from a class
	protected static void deleteMethod() throws IOException {
		System.out.println("Enter the name of the class to delete method from: ");
		String className = getReaderValue();
		System.out.print("Enter the name of the method to delete: ");
		String methodName = getReaderValue();
		boolean success = umlDiagram.deleteMethod(className, methodName);
		if (success) {
			System.out.println("Method " + methodName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println("Failed to delete method from class " + className + ". Class or method may not exist.");
		}
	}

	// Method to rename a method in a class
	protected static void renameMethod() throws IOException {
		System.out.println("Enter the name of the class containing the method: ");
		String className = getReaderValue();
		System.out.print("Enter the current name of the method: ");
		String oldMethodName = getReaderValue();
		System.out.print("Enter the new name of the method: ");
		String newMethodName = getReaderValue();
		boolean success = umlDiagram.renameMethod(className, oldMethodName, newMethodName);
		if (success) {
			System.out.println("Method " + oldMethodName + " renamed to " + newMethodName + " in class " + className
					+ " successfully.");
		} else {
			System.out.println("Failed to rename method in class " + className
					+ ". Class or method may not exist, or new name may already be in use.");
		}
	} 

/**************************************************************************************************************************************/
    /**   RELATIONSHIPS   **/
/** @throws IOException ************************************************************************************************************************************/

	// Method to add a relationship to a class
	protected static void addRelationship() throws IOException {
		System.out.println("Enter the name of the source class: ");
		String sourceClass = getReaderValue();
		System.out.println("Enter the name of the destination class: ");
		String destinationClass = getReaderValue();
		System.out.println(
				"Choose a relationship type (Enter a number): \n1. Aggregation\n2. Composition\n3. Inheritance\n4. Realization\n");
		int type = Integer.parseInt(getReaderValue());

		boolean success = umlDiagram.addRelationship(sourceClass, destinationClass, type);
		if (success) {
			System.out.println("Relationship added successfully.");
		} else {
			System.out.println("Failed to add relationship.");
		}
	}

	// Method to delete a relationship between classes
	protected static void deleteRelationship() throws IOException {
		System.out.println("Enter the name of the first class in the relationship: ");
		String class1 = getReaderValue();
		System.out.print("Enter the name of the second class in the relationship: ");
		String class2 = getReaderValue();

		boolean success = umlDiagram.deleteRelationship(class1, class2);
		if (success) {
			System.out.println("Relationship deleted successfully between " + class1 + " and " + class2 + ".");
		} else {
			System.out.println("Failed to delete relationship between " + class1 + " and " + class2 + ".");
		}
	}

	// Method to change the type of an existing relationship
	protected static void changeType() throws IOException {
		System.out.println("Enter the name of the first class in the relationship: ");
		String class1 = getReaderValue();
		System.out.print("Enter the name of the second class in the relationship: ");
		String class2 = getReaderValue();
		System.out.println(
				"Enter the number for the relationship type: \n1. Aggregation\n2. Composition\n3. Inheritance\n4. Realization\n");
		int type = Integer.parseInt(getReaderValue());

		if (type < 1 || type > 4) {
			System.out.println("Invalid number chosen. ");
		} else {
			boolean success = umlDiagram.changeRelType(class1, class2, type);

			if (success) {
				System.out.println("Relationship type changed succesfully.");
			} else {
				System.out.println("Failed to change type. Relationship has to exist.");
			}
		}
	}

/**************************************************************************************************************************************/
    /**   SAVE DIAGRAM   **/
/*** @throws IOException ************************************************************************************************************************************/

	
	// Method to save the UML diagram to a JSON file
	protected static void saveDiagram() throws IOException {
		System.out.print("Enter the file name to save the diagram (JSON format): ");
		String filePath = getReaderValue().trim();
		if(!filePath.contains(".json")){
			filePath += ".json";
		}
		try (FileWriter writer = new FileWriter(filePath)) {
			JsonObject jsonDiagram = new JsonObject();

			// Convert classes to JSON array
			JsonArray jsonClasses = new JsonArray();
			for (UMLClass umlClass : umlDiagram.getClasses()) {
				JsonObject jsonClass = new JsonObject();
				jsonClass.addProperty("name", umlClass.getName());

				JsonArray jsonFields = new JsonArray();
				for (Field field : umlClass.getFields()) {
					JsonObject jsonField = new JsonObject();
					jsonField.addProperty("name", field.getName());
					jsonField.addProperty("type", field.getType());
					jsonFields.add(jsonField);
				}
				jsonClass.add("attributes", jsonFields);

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

	
/**************************************************************************************************************************************/
    /**   LOAD DIAGRAM   **/
/*** @throws IOException ************************************************************************************************************************************/

	
	// Method to load a UML diagram from a JSON file
	protected static void loadDiagram() throws IOException {
        System.out.print("Enter the file name to load the diagram (JSON format): ");
        String filePath = getReaderValue().trim();
		if (!filePath.contains(".json")){
			filePath += ".json";
		}
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
                    umlDiagram.addField(className, attributeName, attributeType);
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

/**************************************************************************************************************************************/
    /**   INTERFACES   **/
/**************************************************************************************************************************************/

	// Method to list all classes in the UML diagram
	protected static void listClasses() {
		ArrayList<UMLClass> classes = umlDiagram.getClasses();
		System.out.println("Classes:");
		for (UMLClass umlClass : classes) {
			System.out.println("- " + umlClass.getName());
		}

	}

	protected static void listClassContents() throws IOException {
	    System.out.print("Enter the name of the class to list its contents: ");
	    String className = getReaderValue();
	    UMLClass umlClass = umlDiagram.getClassNameMapToName().get(className);

	    if (umlClass != null) {
	        System.out.println("Fields of class " + className + ":");
	        for (Field field : umlClass.getFields()) {
	            System.out.println("  " + field.getName() + ": " + field.getType());
	        }
	        System.out.println("Methods of class " + className + ":");
	        for (Method method : umlClass.getMethods()) {
	            System.out.print("  " + method.getName() + "(");
	            // Print method parameters
	            List<Parameter> params = method.getParameters();
	            for (int i = 0; i < params.size(); i++) {
	                Parameter param = params.get(i);
	                System.out.print(param.getName() + ": " + param.getType());
	                if (i < params.size() - 1) {
	                    System.out.print(", ");
	                }
	            }
	            System.out.println("): " + method.getReturnType());
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

	
/**************************************************************************************************************************************/
    /**   HELP   **/
/**************************************************************************************************************************************/

	// Method to display help information about the commands supported by the UML
	// CLI
	protected static void help() {
		System.out.println("Help:");
		System.out.println("This UML Diagram Editor CLI supports the following commands along with their arguments:\n");
		System.out.println("1. AddClass - Create a new class");
		System.out.println("   Arguments: Name of the class\n");
		System.out.println("2. DeleteClass - Delete a class");
		System.out.println("   Arguments: Name of the class to delete\n");
		System.out.println("3. RenameClass - Rename a class");
		System.out.println("   Arguments: Current name of the class, New name for the class\n");
		System.out.println("4. AddField - Add an fieldto a class");
		System.out.println("   Arguments: Name of the class, Name of the field, Type of the field\n");
		System.out.println("5. DeleteField - Delete an field from a class");
		System.out.println("   Arguments: Name of the class, Name of the field to delete\n");
		System.out.println("6. RenameField - Rename an field in a class");
		System.out.println(
				"   Arguments: Name of the class, Current name of the field, New name for the field\n");
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
		System.out.println(
				"    Arguments: Name of the first class, Name of the second class, New selection for attribute\n");
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
	protected static void exit() throws IOException {
		System.out.print("Do you want to save the diagram before exiting? (yes/no): ");
		String saveChoice = getReaderValue().trim().toLowerCase();
		if (saveChoice.equals("yes")) {
			saveDiagram();
			System.out.println("Diagram saved.");
			System.exit(1);
		} else {
			System.out.println("Exited without Saving.");
			System.exit(1);
		}
		

	}
	
	/**
	 * Provides a help string containing usage information for all commands supported by the UML Diagram Editor CLI.
	 * This includes commands for creating, deleting, and modifying classes, fields, methods, and relationships,
	 * as well as saving and loading the UML diagram from a JSON file.
	 *
	 * @return A multiline {@link String} detailing each command, its purpose, and its required arguments. Commands
	 *         include operations for managing classes (AddClass, DeleteClass, RenameClass), managing fields within a
	 *         class (AddField, DeleteField, RenameField), managing methods within a class (AddMethod, RemoveMethod,
	 *         ChangeMethod), managing relationships between classes (AddRelationship, DeleteRelationship, ChangeType),
	 *         and utilities for listing diagram components, saving, loading, displaying help, and exiting the program.
	 */
	public static String helperMethodForHelp() {
	    return """
	            Help Information:
	            This UML Diagram Editor CLI supports the following commands along with their arguments:

	            AddClass -            Create a new class
	            Arguments:            Name of the class
	            DeleteClass -         Delete a class
	            Arguments:            Name of the class to delete
	            RenameClass -         Rename a class
	            Arguments:            Current name of the class, New name for the class

	            AddField -            Add a field to a class
	            Arguments:            Name of the class, Name of the field, Type of the field
	            DeleteField -         Delete a field from a class
	            Arguments:            Name of the class, Name of the field to delete
	            RenameField -         Rename a field in a class
	            Arguments:            Name of the class, Current name of the field, New name for the field

	            AddMethod -           Add a method to a class
	            Arguments:            Name of the class, Name of the method, Return type of the method
	            RemoveMethod -        Delete a method from a class
	            Arguments:            Name of the class, Name of the method to delete
	            ChangeMethod -        Rename a method in a class
	            Arguments:            Name of the class, Current name of the method, New name for the method

	            AddRelationship -     Add a relationship between classes
	            Arguments:            Name of the first class, Name of the second class, Type of relationship
	            DeleteRelationship -  Delete a relationship between classes
	            Arguments:            Name of the first class, Name of the second class
	            ChangeType -          Change the type of a relationship between classes
	            Arguments:            Name of the first class, Name of the second class, New type of relationship

	            ListClasses -         List all classes
	            ListRelationships -   List all relationships
	            ListClass -           List contents of a specified class
	            Arguments:            Name of the class to list its contents

	            Save -                Save diagram to JSON file
	            Arguments:            File path to save the diagram (JSON format)
	            Load -                Load diagram from JSON file
	            Arguments:            File path to load the diagram (JSON format)

	            Menu -                Displays the main menu
	            Help -                Displays detailed information of program

	            Exit -                Exits the program
	            """;
	}
}