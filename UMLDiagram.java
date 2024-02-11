import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UMLDiagram implements UMLStructure {

	// Maps class names to UMLClass objects
	private Map<String, UMLClass> classNameMapToName;
	// Maps relationship IDs to Relationships
	private Map<String, Relationship> classMapToRelation;

	// Constructor
	public UMLDiagram() {
		classNameMapToName = new HashMap<>();
		classMapToRelation = new HashMap<>();
	}

	public ArrayList<UMLClass> getClasses() {
		return new ArrayList<>(classNameMapToName.values());
	}

	@Override
	public ArrayList<Relationship> getRelationships() {
		return new ArrayList<>(classMapToRelation.values());
	}

	// Getters for maps
	public Map<String, UMLClass> getClassNameMapToName() {
		return classNameMapToName;
	}

	public Map<String, Relationship> getClassMapToRelation() {
		return classMapToRelation;
	}

	/**
	 * Adds a new class to the UML diagram with the specified class name.
	 *
	 * @param className The name of the class to add.
	 * @return true if the class was successfully added, false if the class already
	 *         exists.
	 */
	public boolean addClass(String className) {
		if (!classNameMapToName.containsKey(className)) {
			classNameMapToName.put(className, new UMLClass(className));
			return true;
		}
		return false;
	}

	/**
	 * Deletes the class with the specified name from the UML diagram.
	 *
	 * @param className The name of the class to delete.
	 * @return true if the class was successfully deleted, false if the class does
	 *         not exist.
	 */
	public boolean deleteClass(String className) {
		if (classNameMapToName.containsKey(className)) {
			classNameMapToName.remove(className);
			// Remove any relationships involving this class
			classMapToRelation.values().forEach(relationship -> {
				if (relationship.getSource().equals(className) || relationship.getDestination().equals(className)) {
					classMapToRelation.remove(relationship.getId());
				}
			});
			return true;
		}
		return false;
	}

	/**
	 * Renames a class in the UML diagram from oldName to newName.
	 *
	 * @param oldName The current name of the class.
	 * @param newName The new name for the class.
	 * @return true if the class was successfully renamed, false if the oldName does
	 *         not exist or newName is already in use.
	 */
	public boolean renameClass(String oldName, String newName) {
		if (classNameMapToName.containsKey(oldName) && !classNameMapToName.containsKey(newName)) {
			UMLClass umlClass = classNameMapToName.remove(oldName);
			umlClass.setName(newName);
			classNameMapToName.put(newName, umlClass);
			// Update any relationships involving this class
			classMapToRelation.values().forEach(relationship -> {
				if (relationship.getSource().equals(oldName)) {
					relationship.setSource(newName);
				}
				if (relationship.getDestination().equals(oldName)) {
					relationship.setDestination(newName);
				}
			});
			return true;
		}
		return false;
	}

	// Method to add a relationship to the diagram
	public boolean addRelationship(String class1, String class2, String ID) {
		if (!classMapToRelation.containsKey(ID) && classNameMapToName.containsKey(class1)
				&& classNameMapToName.containsKey(class2)) {
			classMapToRelation.put(ID, new Relationship(class1, class2, ID));
			return true;
		}
		return false;
	}

	// Method to delete a relationship from the diagram
	public boolean deleteRelationship(String sourceClass, String destinationClass, String ID) {
		String relationshipKey = sourceClass + "-" + destinationClass + "-" + ID;
		if (classMapToRelation.containsKey(relationshipKey)) {
			classMapToRelation.remove(relationshipKey);
			return true;
		}
		return false;
	}

	/**
	 * Adds an attribute to a class in the UML diagram.
	 *
	 * @param className     The name of the class to add the attribute to.
	 * @param attributeName The name of the attribute.
	 * @param attributeType The type of the attribute.
	 * @return true if the attribute was successfully added, false if the class does
	 *         not exist.
	 */
	public boolean addAttribute(String className, String attributeName, String attributeType) {
		if (classNameMapToName.containsKey(className)) {
			return classNameMapToName.get(className).addAttribute(attributeName, attributeType, className);
		}
		return false;
	}

	/**
	 * Deletes an attribute from a class in the UML diagram.
	 *
	 * @param className     The name of the class to delete the attribute from.
	 * @param attributeName The name of the attribute to delete.
	 * @return true if the attribute was successfully deleted, false if the class
	 *         does not exist.
	 */

	public boolean deleteAttribute(String className, String attributeName) {
		if (classNameMapToName.containsKey(className)) {
			return classNameMapToName.get(className).deleteAttribute(attributeName);
		}
		return false;
	}

	/**
	 * Renames an attribute in a class in the UML diagram.
	 *
	 * @param className     The name of the class containing the attribute.
	 * @param attributeName The current name of the attribute.
	 * @param newName       The new name for the attribute.
	 * @return true if the attribute was successfully renamed, false if the class
	 *         does not exist or the attribute does not exist.
	 */
	public boolean renameAttribute(String className, String attributeName, String newName) {
		if (classNameMapToName.containsKey(className)) {
			return classNameMapToName.get(className).renameAttribute(attributeName, newName);
		}
		return false;
	}

	/**
	 * Adds a method to a class in the UML diagram.
	 *
	 * @param className  The name of the class to add the method to.
	 * @param methodName The name of the method.
	 * @param methodType The return type of the method.
	 * @return true if the method was successfully added, false if the class does
	 *         not exist.
	 */
	public boolean addMethod(String className, String methodName, String methodType) {
		if (classNameMapToName.containsKey(className)) {
			return classNameMapToName.get(className).addMethod(methodName, methodType);
		}
		return false;
	}

	// Method to delete a method from a class in the UML diagram
	public boolean deleteMethod(String className, String methodName) {
		if (classNameMapToName.containsKey(className)) {
			return classNameMapToName.get(className).deleteMethod(methodName);
		}
		return false;
	}

	// Method to rename a method in a class in the UML diagram
	public boolean renameMethod(String className, String originalName, String newName) {
		if (classNameMapToName.containsKey(className)) {
			return classNameMapToName.get(className).renameMethod(originalName, newName);
		}
		return false;
	}

	/**
	 * Saves the UML diagram as a JSON file.
	 *
	 * @param fileName The name of the JSON file to save.
	 * @return true if the diagram was successfully saved, false if an error
	 *         occurred during the process.
	 * @throws IOException if an I/O error occurs while writing to the file.
	 */
	public boolean saveToJSON(String fileName) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter(fileName)) {
			gson.toJson(this, writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Loads a UML diagram from a JSON file.
	 *
	 * @param fileName The name of the JSON file to load.
	 * @return true if the diagram was successfully loaded, false if an error
	 *         occurred during the process.
	 */
	public boolean loadFromJSON(String fileName) {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(fileName)) {
			gson.fromJson(reader, UMLDiagram.class);
			return true; // Loading successful
		} catch (IOException | JsonSyntaxException e) {
			e.printStackTrace();
			return false; // Loading failed
		}
	}
	/**
     * Clears all classes and relationships from the UML diagram.
     */
	public void clear() {
		classNameMapToName.clear();
		classMapToRelation.clear();
	}
	 /**
     * Checks if a class exists in the UML diagram.
     *
     * @param className The name of the class to check.
     * @return true if the class exists in the diagram, false otherwise.
     */
	public boolean hasClass(String className) {
		return classNameMapToName.containsKey(className);
	}

}
