import java.util.ArrayList;
import java.util.List;

/**
 * Represents a method in a class, extending the AttributeAndMethodLayOut class.
 * This class provides functionality to manage parameters associated with the method.
 */
public class Method extends AttributeAndMethodLayOut {
    private ArrayList<Parameter> parameters;

    /**
     * Constructs a Method object with the specified name and type, initializing the parameters list.
     * 
     * @param name The name of the method.
     * @param type The type of the method.
     */
    public Method(String name, String type) {
        super(name, type);
        parameters = new ArrayList<>();
    }

    /**
     * Adds a parameter to the method.
     * 
     * @param name The name of the parameter.
     * @param type The type of the parameter.
     * @return True if the parameter is successfully added, false otherwise.
     */
    public boolean addParameter(String name, String type) {
        if (containsParameter(name)) {
            return false;
        }
        return parameters.add(new Parameter(name, type));
    }

    /**
     * Deletes a parameter from the method.
     * 
     * @param name The name of the parameter to delete.
     * @return True if the parameter is successfully deleted, false otherwise.
     */
    public boolean deleteParameter(String name) {
        for (Parameter param : parameters) {
            if (param.getName().equals(name)) {
                parameters.remove(param);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all parameters from the method.
     * 
     * @return True if all parameters are successfully deleted, false if the parameters list is empty.
     */
    public boolean deleteAllParameters() {
        if (parameters.isEmpty()) {
            return false;
        }
        parameters.clear();
        return true;
    }

    /**
     * Replaces the current list of parameters with a new list.
     * 
     * @param newList The new list of parameters.
     * @return True if the list of parameters is successfully replaced, false if the new list is empty.
     */
    public boolean replaceParameterList(ArrayList<Parameter> newList) {
        if (newList.isEmpty()) {
            return false;
        }
        parameters = newList;
        return true;
    }

    /**
     * Changes the type of a parameter in the method.
     * 
     * @param name    The name of the parameter whose type will be changed.
     * @param newType The new type for the parameter.
     * @return True if the parameter's type is successfully changed, false if the parameter is not found.
     */
    public boolean changeParameterType(String name, String newType) {
        for (Parameter param : parameters) {
            if (param.getName().equals(name)) {
                param.setType(newType);
                return true;
            }
        }
        return false;
    }

    /**
     * Renames a parameter in the method.
     * 
     * @param originalName The original name of the parameter.
     * @param newName      The new name for the parameter.
     * @return True if the parameter is successfully renamed, false if the new name is already in use or the parameter is not found.
     */
    public boolean renameParameter(String originalName, String newName) {
        if (containsParameter(newName)) {
            return false;
        }
        for (Parameter param : parameters) {
            if (param.getName().equals(originalName)) {
                param.setName(newName);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a parameter with the given name exists in the method.
     * 
     * @param name The name of the parameter to check.
     * @return True if a parameter with the given name exists, false otherwise.
     */
    public boolean containsParameter(String name) {
        for (Parameter param : parameters) {
            if (param.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a parameter has a specific type.
     * 
     * @param name The name of the parameter.
     * @param type The type to check against.
     * @return True if the parameter has the specified type, false otherwise.
     */
    public boolean checkParameterType(String name, String type) {
        for (Parameter param : parameters) {
            if (param.getName().equals(name)) {
                if (param.getType().equals(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves the list of parameters associated with the method.
     * 
     * @return The list of parameters.
     */
    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Retrieves the names of parameters associated with the method.
     * 
     * @return The list of parameter names.
     */
    public List<String> getParameterNames() {
        ArrayList<String> params = new ArrayList<>();
        for (Parameter parameter : parameters) {
            params.add(parameter.getName());
        }
        return params;
    }

 /**
 * Generates a string representation of the method, including its name, type, and parameters.
 * 
 * @return A string representation of the method.
 */
public String toString() {
    // Start building the string representation with the method's name and type
    StringBuilder methodString = new StringBuilder("Name: " + getName() + " Type: " + getType());

    // If there are parameters, add them to the string representation
    if (!parameters.isEmpty()) {
        methodString.append(" (");
        for (int i = 0; i < parameters.size(); i++) {
            Parameter param = parameters.get(i);
            methodString.append(param.getName()).append(": ").append(param.getType());
            // If it's not the last parameter, add a comma and space
            if (i < parameters.size() - 1) {
                methodString.append(", ");
            }
        }
        methodString.append(")");
    }

    // Return the constructed string representation
    return methodString.toString();
}
