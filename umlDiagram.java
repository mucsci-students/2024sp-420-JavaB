import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UMLDiagram extends UMLClass {

    // Maps class names to UMLClass objects
    private Map<String, UMLClass> classNameMapToName;
    // Maps relationship IDs to Relationships
    private Map<String, Relations> classMapToRelation;

    // Constructor
    public UMLDiagram() {
        classNameMapToName = new hashMap<>();
        classMapToRelation = new hashMap<>();
    }

    // Getters for maps
    public Map<String, UMLClass> getClassNameMapToName() {
        return classNameMapToName;
    }

    public Map<String, Relations> getClassMapToRelation() {
        return classMapToRelation;
    }

    // Method to add a class to the diagram
    public boolean addClass(String className) {
        if (!classNameMapToName.containsKey(className)) {
            classNameMapToName.put(className, new UMLClass(className));
            return true;
        }
        return false;
    }

    // Method to delete a class from the diagram
    public boolean deleteClass(String className) {
        if (classNameMapToName.containsKey(className)) {
            classNameMapToName.remove(className);
            // Also remove any relationships involving this class
            classMapToRelation.entrySet().removeIf(entry ->
                    entry.getValue().containsClass(className)
            );
            return true;
        }
        return false;
    }

    // Method to rename a class in the diagram
    public boolean renameClass(String oldName, String newName) {
        if (classNameMapToName.containsKey(oldName) && !classNameMapToName.containsKey(newName)) {
            UMLClass umlClass = classNameMapToName.remove(oldName);
            umlClass.setName(newName);
            classNameMapToName.put(newName, umlClass);
            // Update any relationships involving this class
            classMapToRelation.forEach((key, value) -> value.updateClassName(oldName, newName));
            return true;
        }
        return false;
    }

    // Method to add a relationship to the diagram
    public boolean addRelationship(String class1, String class2, String ID, String type) {
        if (!classMapToRelation.containsKey(ID) && classNameMapToName.containsKey(class1) && classNameMapToName.containsKey(class2)) {
            classMapToRelation.put(ID, new Relationship(class1, class2, ID, type));
            return true;
        }
        return false;
    }

    // Method to delete a relationship from the diagram
    public boolean deleteRelationship(String ID) {
        if (classMapToRelation.containsKey(ID)) {
            classMapToRelation.remove(ID);
            return true;
        }
        return false;
    }

    public boolean addAttribute(String className, String attributeName, String attributeType) {
        if (classNameMapToName.containsKey(className)) {
            return classNameMapToName.get(className).addAttribute(attributeName, attributeType);
        }
        return false;
    }

    // Method to delete an attribute from a class in the diagram
    public boolean deleteAttribute(String className, String attributeName) {
        if (classNameMapToName.containsKey(className)) {
            return classNameMapToName.get(className).deleteAttribute(attributeName);
        }
        return false;
    }

    // Method to rename an attribute in a class in the diagram
    public boolean renameAttribute(String className, String attributeName, String newName) {
        if (classNameMapToName.containsKey(className)) {
            return classNameMapToName.get(className).renameAttribute(attributeName, newName);
        }
        return false;
    }

    // Method to save the diagram as JSON
    public boolean saveJSON(String name) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(name)) {
            gson.toJson(this, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Static method to load a UML diagram from a JSON file
    public static UMLDiagram loadJSON(String filepath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            return gson.fromJson(reader, UMLDiagram.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Static method to check if a file exists
    public static boolean fileCheck(String file) {
        File f = new File(file);
        return f.exists() && !f.isDirectory();
    }
}
