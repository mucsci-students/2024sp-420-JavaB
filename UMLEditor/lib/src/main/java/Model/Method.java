package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a method in a UML diagram.
 */
public class Method extends FieldAndMethodLayOut {

	private String returnType;
	private List<Parameter> parameters = new ArrayList<>();

	/**
	 * Initializes a method with a specific name and type.
	 *
	 * @param name Name of the method.
	 * @param type Type of the method.
	 */
	public Method(String name, String type) {
		super(name, type);
		this.returnType = type;
	}

	public String getReturnType() {
		return this.returnType; // Assuming 'returnType' is a field in your Method class holding the method's
								// return type
	}

	// Returns the parameters of the method
	public List<Parameter> getParameters() {
		return new ArrayList<>(parameters); // Return a copy of the parameters list
	}
	
	
	/**
	 * A string representation of the method.
	 *
	 * @return A string representation of the method.
	 */
	@Override
	public String toString() {
		// Join parameter strings, each on a new line, with an indent
		String parametersString = parameters.stream().map(Parameter::toString)
				.collect(Collectors.joining(",\n    ", "\n    ", ""));

		return "\n" + "    Name: " + name + ",\n" + "    Type: " + returnType + ","
				+ (parameters.isEmpty() ? "" : "    \nParam: " + parametersString + "\n");
	}

	public void replaceParameterList(ArrayList<Parameter> newParameterList) {
		// TODO Auto-generated method stub

	}

	// Deletes all parameters from the method
	public boolean deleteAllParameters() {
		if (!parameters.isEmpty()) {
			parameters.clear();
			return true;
		}
		return false;
	}

	// Changes the type of an existing parameter
	public boolean changeParameterType(String parameterName, String newParamType) {
		for (Parameter param : parameters) {
			if (param.getName().equals(parameterName)) {
				param.setType(newParamType);
				return true;
			}
		}
		return false; 
	}
	
	// In Method class

	// Adds a parameter to the method
	public boolean addParameter(String paramName, String paramType) {
	    // Validate parameter name and type are not null or empty
	    if (paramName == null || paramName.trim().isEmpty() || paramType == null || paramType.trim().isEmpty()) {
	        return false;
	    }
	    // Check if parameter with same name already exists
	    if (parameters.stream().anyMatch(param -> param.getName().equals(paramName))) {
	        return false; // Parameter already exists
	    }
	    parameters.add(new Parameter(paramName, paramType));
	    return true;
	}

	// Renames an existing parameter
	// Renames an existing parameter
	public boolean renameParameter(String oldParameterName, String newParameterName) {
	    if (newParameterName == null || newParameterName.trim().isEmpty() ||
	        parameters.stream().anyMatch(param -> param.getName().equals(newParameterName))) {
	        return false; // New parameter name is invalid or already exists
	    }
	    for (Parameter param : parameters) {
	        if (param.getName().equals(oldParameterName)) {
	            param.setName(newParameterName);
	            return true; // Parameter renamed successfully
	        }
	    }
	    return false; // Old parameter name does not exist
	}


	// Deletes a parameter from the method
	public boolean deleteParameter(String parameterName) {
	    // Assuming Parameter has a properly overridden equals() method if needed
	    return parameters.removeIf(param -> param.getName().equals(parameterName));
	}
}
