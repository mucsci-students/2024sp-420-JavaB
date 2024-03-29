package com.classuml.Model;

import com.classuml.Controller.UMLCli;
import com.classuml.Controller.UMLGui;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Point;


public class UMLDiagram implements UMLStructure {

	// Maps class names to UMLClass objects
	private Map<String, UMLClass> classNameMapToName = new HashMap<>();
	// Maps relationship IDs to Relationships
	private Map<String, Relationship> classMapToRelation = new HashMap<>();
	private String className;
	private UMLDiagram diagram;
	private List<Parameter> parameters = new ArrayList<>();
	private ArrayList<Method> methods = new ArrayList<>();
	private transient Memento memento = new Memento();

	private transient UMLGui gui;

	public UMLDiagram(UMLGui gui) {
		this.gui = gui;
	}
	public UMLDiagram(UMLDiagram diagram2)
	{
		this.gui = diagram2.getGui();
		setClassNameMap(diagram2.getClassNameMapToName());
		setClassMap(diagram2.getClassMapToRelation());
		setParams(diagram2.getParameters());
		setMethods(diagram2.getMethods());
		setMemento(diagram2.getMemento());
	}
	public String getClassName() {
		return className;
	}
	public UMLDiagram getDiagram() {
		return diagram;
	}
	public List<Parameter> getParameters() {
		return parameters;
	}
	public ArrayList<Method> getMethods() {
		return methods;
	}
	public Memento getMemento() {
		return memento;
	}
	public UMLGui getGui() {
		return gui;
	}
	public void setGui(UMLGui gui) {
	    this.gui = gui;
	}

 
	public UMLDiagram() {
		classNameMapToName = new HashMap<>();
	}

	public void setClassNameMap(Map<String, UMLClass> classNameMap)
	{
		Map<String, UMLClass> map2 = new HashMap<String, UMLClass>();
		for(Map.Entry<String, UMLClass> entry: classNameMap.entrySet())
		{
			UMLClass class2 = new UMLClass(entry.getValue());
			map2.put(entry.getKey(), class2);
		}
		classNameMapToName = map2;
	}
	public void setClassMap(Map<String, Relationship> classMapRel)
	{
		Map<String, Relationship> map2 = new HashMap<String, Relationship>();
		for(Map.Entry<String, Relationship> entry: classMapRel.entrySet())
		{
			Relationship rel2 = new Relationship(entry.getValue().getSource(), entry.getValue().getDestination(), entry.getValue().getType());
			
			map2.put(entry.getKey(), rel2);
		}
		classMapToRelation = map2;

	}
	public void setParams(List<Parameter> params)
	{
		this.parameters.clear();
		for(Parameter param:params)
		{
			Parameter param2 = new Parameter(param.getName(), param.getType());
			parameters.add(param2);
		}

	}
	public void setMethods(ArrayList<Method> methods2)
	{
		this.methods.clear();
		for(Method method2:methods2)
		{
			Method method3 = new Method(method2.getName(), method2.getReturnType());
			for(Parameter param2:method2.getParameters())
			{
				method3.addParameter(param2.getName(),param2.getType());
			}

			this.methods.add(method3);
		}
	}
	public void setMemento(Memento memento2)
	{
		Memento testMem = new Memento(memento2.getUndo(), memento2.getRedo());
		this.memento = testMem;
		
	}
	public void setClassName(String classTwo)
	{
		this.className = classTwo;
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

	
	/****************************** CLASSES *****************************************************************/
	
	
	/**
	 * Adds a new class to the UML diagram with the specified class name.
	 *
	 * @param className The name of the class to add.
	 * @return true if the class was successfully added, false if the class already
	 *         exists.
	 */
	public boolean addClass(String className) {
		memento.clearRedo();
		saveState();
	    if (className == null || className.isEmpty() || classNameMapToName.containsKey(className)) {    	
			memento.popUndo();
	        return false; // Class already exists or invalid name      
	    }
		int x = 20;
		int y = 20;
		Point pos = new Point(x, y);
		if(!classNameMapToName.containsKey(className)) {
			classNameMapToName.put(className, new UMLClass(className, pos));
	    }
	    return true;
	}


	/**
	 * Deletes the class with the specified name from the UML diagram.
	 *
	 * @param className The name of the class to delete.
	 * @return true if the class was successfully deleted, false if the class does
	 *         not exist.
	 */
 
	public boolean deleteClass(String className) {
		memento.clearRedo();
		saveState();
	    // Check if the class name exists in the map before attempting to remove
	    if (classNameMapToName.containsKey(className)) {
	        classNameMapToName.remove(className);

	        classMapToRelation.values().removeIf(relationship ->
	                relationship.getSource().equals(className) || relationship.getDestination().equals(className));
	        return true; // Return true to indicate successful removal
	    }
		memento.popUndo();
	    return false; // Return false if the class name does not exist
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
		memento.clearRedo();
		saveState();
		
		    if (newName == null || newName.isEmpty()) {
				memento.popUndo();
		        return false; // New name cannot be null or empty
		    }
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
		memento.popUndo();
		return false;
	}

	/****************************** RELATIONSHIPS *****************************************************************/

	
	// Method to add a relationship to the diagram
	public boolean addRelationship(String class1, String class2, int type) {
		memento.clearRedo();
		saveState();
	    if (class1 == null || class1.isEmpty() || class2 == null || class2.isEmpty() || type < 1 || type > 4){
			memento.popUndo();
	        return false; // Reject if class names are null/empty or if type is out of valid range
	    }
	    // Check if both classes exist in the diagram
	    if (classNameMapToName.containsKey(class1) && classNameMapToName.containsKey(class2)) {
	        // Check if the relationship already exists
	        if (!relationshipExists(class1, class2)) {
	            // Add the relationship
	            classMapToRelation.put(generateRelationshipKey(class1, class2), new Relationship(class1, class2, type));
	            return true; // Relationship successfully added
	        }
	    }
		memento.popUndo();
	    return false;
	} 

	/**
	 * Deletes a relationship between two classes from the UML diagram.
	 *
	 * @param sourceClass      The name of the source class.
	 * @param destinationClass The name of the destination class.
	 * @return true if the relationship was successfully deleted, false otherwise.
	 */

	public boolean deleteRelationship(String sourceClass, String destinationClass) {
		memento.clearRedo();
		saveState();
	    // Construct the relationship key
	    String relationshipKey = generateRelationshipKey(sourceClass, destinationClass);

	    // Check if the relationship exists
	    if (classMapToRelation.containsKey(relationshipKey)) {
	        // Remove the relationship
	        classMapToRelation.remove(relationshipKey);
	        return true; // Relationship successfully deleted
	    }
		memento.popUndo();
	    return false; // Relationship does not exist
	}

	//add comment
	
 
	public boolean changeRelType(String class1, String class2, int type){
		memento.clearRedo();
		saveState();
		if (class1 == null || class1.isEmpty() || class2 == null || class2.isEmpty() || type < 1 || type > 4){
			memento.popUndo();
	        return false; // Reject null or empty class names
	    }
		String relationshipKey = generateRelationshipKey(class1, class2);
	// Check if both classes exist in the diagram
	if (classNameMapToName.containsKey(class1) && classNameMapToName.containsKey(class2)) {
				if(classMapToRelation.containsKey(relationshipKey)){
				classMapToRelation.remove(relationshipKey);
	        // Check if the relationship already exists
	        	if (!relationshipExists(class1, class2)) {
	            // Add the relationship
	            	classMapToRelation.put(generateRelationshipKey(class1, class2), new Relationship(class1, class2, type));
	            	return true; // Relationship successfully added
	        	}
	    	}
		}
		memento.popUndo();
	    return false;
	}
	
	
	// Helper method to generate a unique key for a relationship
	private String generateRelationshipKey(String sourceClass, String destinationClass) {
	    return sourceClass + "-" + destinationClass;
	}

	// Helper method to check if a relationship exists between two classes
	private boolean relationshipExists(String class1, String class2) {
	    String key1 = generateRelationshipKey(class1, class2);
	    String key2 = generateRelationshipKey(class2, class1);
	    return classMapToRelation.containsKey(key1) || classMapToRelation.containsKey(key2);
	}


	
	/****************************** FIELDS *****************************************************************/

	/**
	 * Adds an attribute to a class in the UML diagram.
	 *
	 * @param className     The name of the class to add the attribute to.
	 * @param attributeName The name of the attribute.
	 * @param attributeType The type of the attribute.
	 * @return true if the attribute was successfully added, false if the class does
	 *         not exist.
	 */

	public boolean addField(String className, String fieldName, String fieldType) {
		memento.clearRedo();
		saveState();
	    UMLClass umlClass = this.getClassByName(className); // Assuming getClassByName is implemented correctly
	    if (umlClass != null) {
			if(umlClass.addField(fieldName, fieldType))// Corrected to match UMLClass's method signature
				return true;
			memento.popUndo();
	        return false;
	    }
		memento.popUndo();
	    return false;
	}

	public boolean setPosition(String className, Point position){
		UMLClass umlClass = this.getClassByName(className);
		if(umlClass != null){
			umlClass.setPosition(position);
			return true;
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


	public boolean deleteField(String className, String attributeName) {
		memento.clearRedo();
		saveState();
		if (classNameMapToName.containsKey(className)) {
			if(classNameMapToName.get(className).deleteField(attributeName))
				return true;
			memento.popUndo();
			return false;
		}
		memento.popUndo();
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
 
	public boolean renameField(String className, String oldAttributeName, String newAttributeName) {
		memento.clearRedo();
		saveState();
	    // Check for null or empty new attribute name, or if the class does not exist
	    if (newAttributeName == null || newAttributeName.isEmpty() || !classNameMapToName.containsKey(className)) {
			memento.popUndo();
	        return false;
	    }

	    // Retrieve the UMLClass instance
	    UMLClass umlClass = classNameMapToName.get(className);

	    // Ensure the old attribute exists before attempting to rename
	    if (umlClass != null && umlClass.containsField(oldAttributeName)) {
	        // Delegate the renaming to the UMLClass instance
			if(umlClass.renameField(oldAttributeName, newAttributeName))
				return true;
			memento.popUndo();
	        return false;
	    }
	    // Return false if the class does not exist or the field does not exist in the class
		memento.popUndo();
	    return false;
	}

	
	/****************************** METHODS *****************************************************************/


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
		memento.clearRedo();
		saveState();
		if (classNameMapToName.containsKey(className)) {
			if(classNameMapToName.get(className).addMethod(methodName, methodType))
				return true;
			memento.popUndo();
			return false;
		}
		memento.popUndo();
		return false;
	}

	// Method to delete a method from a class in the UML diagram

	public boolean deleteMethod(String className, String methodName) {
		saveState();
		if (classNameMapToName.containsKey(className)) {
			if(classNameMapToName.get(className).deleteMethod(methodName))
				return true;
			memento.popUndo();
			return false;
		}
		memento.popUndo();
		return false;
	}

	// Method to rename a method in a class in the UML diagram
 
	public boolean renameMethod(String className, String originalName, String newName) {
		saveState();
	    if (classNameMapToName.containsKey(className) && originalName != null && newName != null) {
			if(classNameMapToName.get(className).renameMethod(originalName, newName))
				return true;
			memento.popUndo();
	        return false;
	    }
		memento.popUndo();
	    return false;
	}
	
	/****************************** PARAMETERS *****************************************************************/

	/**
	 * Adds a parameter to a method in the class.
	 *
	 * This method adds a new parameter with the specified name and type to the method
	 * with the given name. If the method does not exist or if either the method name,
	 * parameter name, or parameter type is null or an empty string, the addition fails.
	 *
	 * @param methodName the name of the method to which the parameter will be added
	 * @param paramName the name of the new parameter
	 * @param parameterType 
	 * @return true if the parameter is successfully added, false otherwise
	 */
	public boolean addParameter(String className, String methodName, String parameterName, String parameterType) {
		memento.clearRedo();
		saveState();
	    UMLClass targetClass = classNameMapToName.get(className);
	    if (targetClass == null) {
			memento.popUndo();
	        return false; // Class not found
	    }
	    // Delegate the task to add the parameter to the specific method of the UMLClass
		if(targetClass.addParameter(methodName, parameterName, parameterType))
			return true;
		memento.popUndo();
	    return false;
	}

	/**
	 * Renames a parameter of a method in the class.
	 *
	 * This method renames a parameter from its old name to a new name in the method
	 * with the specified name. If the method does not exist or if either the method name,
	 * old parameter name, or new parameter name is null or an empty string, the renaming fails.
	 *
	 * @param className 
	 * @param methodName the name of the method containing the parameter to be renamed
	 * @param oldParameterName the current name of the parameter to be renamed
	 * @param newParameterName the new name for the parameter
	 * @return true if the parameter is successfully renamed, false otherwise
	 */
	// In UMLDiagram class
	public boolean renameParameter(String className, String methodName, String oldParameterName, String newParameterName) {
		memento.clearRedo();
		saveState();
	    UMLClass umlClass = getClassByName(className);
	    if (umlClass != null) {
	        Method method = umlClass.getMethodByName(methodName);
	        if (method != null) {
				if(method.renameParameter(oldParameterName, newParameterName))
					return true;
				memento.popUndo();
	            return false;
	        }
	    }
		memento.popUndo();
	    return false; // Class or method not found
	}


	
	/**
	 * Deletes a parameter from a method in the class.
	 *
	 * This method removes the parameter with the specified name from the method
	 * with the given name. If the method does not exist or if either the method name
	 * or parameter name is null or an empty string, the deletion fails.
	 *
	 * @param methodName the name of the method from which the parameter will be deleted
	 * @return true if the parameter is successfully deleted, false otherwise
	 */
	public boolean deleteParameter(String className, String methodName, String parameterName) {
		memento.clearRedo();
		saveState();
	    UMLClass umlClass = getClassByName(className);
	    if (umlClass != null) {
			if(umlClass.deleteParameter(methodName, parameterName))
				return true;
			memento.popUndo();
	        return false;
	    }
		memento.popUndo();
	    return false;
	}

	
	
	/**
	 * Changes the type of a parameter in a method in the class.
	 *
	 * This method changes the type of the parameter with the specified name to the
	 * new type in the method with the given name. If the method does not exist or
	 * if either the method name, parameter name, or new parameter type is null or
	 * an empty string, the operation fails.
	 *
	 * @param methodName the name of the method containing the parameter whose type will be changed
	 * @param parameterName the name of the parameter whose type will be changed
	 * @param newParamType the new type for the parameter
	 * @return true if the parameter type is successfully changed, false otherwise
	 */
	public boolean changeParamType(String methodName, String parameterName, String newParamType){
		memento.clearRedo();
		saveState();
		for(Method method : methods){
			if(method.getName().equals(methodName)){
				if(method.changeParameterType(parameterName, newParamType))
					return true;
				memento.popUndo();
				return false;
			}
		}
		memento.popUndo();
		return false;
	}

	/**
	 * Removes all parameters from a method in the class.
	 *
	 * This method removes all parameters from the method with the specified name.
	 * If the method does not exist or if the method name is null or an empty string,
	 * the operation fails.
	 *
	 * @param methodName the name of the method from which all parameters will be removed
	 * @return true if all parameters are successfully removed, false otherwise
	 */
	public boolean removeAllPar(String methodName){
		memento.clearRedo();
		saveState();
		for(Method method : methods){
			if (method.getName().equals(methodName)){
				if(method.deleteAllParameters())
					return true;
				memento.popUndo();
				return	false;
			}
		}
		memento.popUndo();
		return false;
	}

	/**
	 * Replaces the parameter list of a method in the class with a new list.
	 *
	 * This method replaces the parameter list of the method with the specified name
	 * with a new list of parameters. If the method does not exist or if the method name
	 * is null or an empty string, the operation fails.
	 *
	 * @param methodName the name of the method whose parameter list will be replaced
	 * @param newParameterList the new list of parameters to replace the existing ones
	 * @return true if the parameter list is successfully replaced, false otherwise
	 */
	public boolean replaceParameterList(String methodName, ArrayList<Parameter> newParameterList){
		memento.clearRedo();
		saveState();
		for(Method method : methods){
			if(method.getName().equals(methodName)){
				method.replaceParameterList(newParameterList);
			}
		}
		return true;
	}

	
	/****************************** SAVE & LOAD *****************************************************************/
	/**
	 * Undos to previous state.
	 * @return Returns true if worked, false if nothing to undo.
	 */
	public boolean undo()
	{
		UMLDiagram state = new UMLDiagram();
		state.setGui(this.gui);
		state.setClassNameMap(this.classNameMapToName);
		state.setClassMap(this.classMapToRelation);
		state.setParams(this.parameters);
		if(this.methods != null)
			state.setMethods(this.methods);
		state.setMemento(this.memento);
		state.setClassName(this.className);
		UMLDiagram  undo = memento.undoState();
		if(undo==null)
			return false;
		setGui(undo.getGui());
		setClassNameMap(undo.getClassNameMapToName());
		setClassMap(undo.getClassMapToRelation());
		setParams(undo.getParameters());
		setMethods(undo.getMethods());
		setMemento(undo.getMemento());
		memento.pushRedo(state);
		return true;
	}
	/**
	 * Redos to state ontop of redo deque.
	 * @return boolean, returns true if it worked, if nothing in it false.
	 */
	public boolean redo()
	{
		
		UMLDiagram  redo = memento.redo();
		if(redo==null)
		{
			return false;
		}
		saveState();
		setGui(redo.getGui());
		setClassNameMap(redo.getClassNameMapToName());
		setClassMap(redo.getClassMapToRelation());
		setParams(redo.getParameters());
		setMethods(redo.getMethods());
		setMemento(redo.getMemento());
		
		return true;
	}
	/**
	 * Saves current state into undo deque.
	 * @return boolean, will always return true.
	 */
	public boolean saveState()
	{
		UMLDiagram state = new UMLDiagram();
		state.setGui(this.gui);
		state.setClassNameMap(this.classNameMapToName);
		state.setClassMap(this.classMapToRelation);
		state.setParams(this.parameters);
		if(this.methods != null)
			state.setMethods(this.methods);
		state.setMemento(this.memento);
		state.setClassName(this.className);
		memento.saveState(state);
		return true;
	}
	public boolean clearState()
	{
		memento.clearStates();
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
		//saveState();
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

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();

	    List<UMLClass> classes = this.getClasses();
	    for (UMLClass umlClass : classes) {
	        // Append class name
	        sb.append(umlClass.getName()).append("\n");

	        // Append fields
	        for (Field field : umlClass.getFields()) {
	            sb.append(field.getName()).append(" : ").append(field.getType()).append("\n");
	        }

	        // Append methods
	        for (Method method : umlClass.getMethods()) {
	            sb.append(method.getName()).append("(");
	            // Append parameters
	            List<Parameter> params = method.getParameters();
	            for (int i = 0; i < params.size(); i++) {
	                Parameter param = params.get(i);
	                sb.append(param.getName()).append(" : ").append(param.getType());
	                if (i < params.size() - 1) {
	                    sb.append(", ");
	                }
	            }
	            sb.append(") : ").append(method.getReturnType()).append("\n");
	        }

	        sb.append("\n"); // Add an empty line between classes
	    }

	    // Append relationships
	    List<Relationship> relationships = this.getRelationships();
	    for (Relationship relationship : relationships) {
	        sb.append(relationship.getSource()).append(" ")
	          .append(Relationship.getTypeAsString(relationship.getType()))
	          .append(" ").append(relationship.getDestination()).append("\n");
	    }

	    return sb.toString();
	}


	public List<Relationship> getRelationshipsForClass(String className) {
	    List<Relationship> relevantRelationships = new ArrayList<>();
	    for (Relationship relationship : classMapToRelation.values()) {
	        if (relationship.getSource().equals(className) || relationship.getDestination().equals(className)) {
	            relevantRelationships.add(relationship);
	        }
	    }
	    return relevantRelationships;
	}

	// Method to get help information from UMLCli
    public String helperMethodForHelp() {
        return UMLCli.helperMethodForHelp();
    }
	
	
	public UMLClass getClassByName(String className) {
	    // Assuming classNameMapToName is a Map<String, UMLClass>
	    return this.classNameMapToName.get(className);
	}

}