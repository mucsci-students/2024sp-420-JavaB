package com.classuml.Model;

import com.classuml.Controller.UMLCli;
import com.classuml.Controller.UMLGui;
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
	private transient Memento memento = new Memento();

	private transient UMLGui gui;

	private int x = 20;
	private int y = 20;

	public UMLDiagram(UMLDiagram diagram2)
	{
		this.gui = diagram2.getGui();
		setClassNameMap(diagram2.getClassNameMapToName());
		setClassMap(diagram2.getClassMapToRelation());
		setMemento(diagram2.getMemento());
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
	public void setMemento(Memento memento2)
	{
		Memento testMem = new Memento(memento2.getUndo(), memento2.getRedo());
		this.memento = testMem;
		
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
	    if (className == null || className.isEmpty() || classNameMapToName.containsKey(className)||!(Character.isLetter(className.charAt(0)))) {    	
			memento.popUndo();
	        return false; // Class already exists or invalid name      
	    }
		Point pos = new Point(x, y);
		for (UMLClass c : classNameMapToName.values()) {
			if (Math.abs(c.position.getX() - pos.getX()) < 100 && Math.abs(c.position.getY() - pos.getY()) < 100) {
				// If overlapping, adjust x and y
				x += 75;
				pos = new Point(x, y);
				if (x > gui.prefMaxWidth) { // assuming a maximum x value of 500
					x = 20;
					y += 40; // move to the next row
				}
			}
		}
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
		
		    if (newName == null || newName.isEmpty()||!(Character.isLetter(newName.charAt(0)))) {
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
	    if (umlClass != null&& fieldName != null && (Character.isLetter(fieldName.charAt(0)))) {
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
			umlClass.position.setLocation(position);
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
	    if (newAttributeName == null || newAttributeName.isEmpty() || !classNameMapToName.containsKey(className)||!(Character.isLetter(newAttributeName.charAt(0)))) {
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
		if (classNameMapToName.containsKey(className)&&(Character.isLetter(methodName.charAt(0)))) {
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
		memento.clearRedo();
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
		memento.clearRedo();
		saveState();
	    if (classNameMapToName.containsKey(className) && originalName != null && newName != null) {
			if(classNameMapToName.get(className).renameMethod(originalName, newName)&&(Character.isLetter(newName.charAt(0))))
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
	    if (targetClass == null||!(Character.isLetter(parameterName.charAt(0)))) {
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
	    if (umlClass != null&&newParameterName != null &&newParameterName.length()!= 0 && (Character.isLetter(newParameterName.charAt(0)))) {
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
	public boolean replaceParameters(String className, String methodName, String[] parameterNames, String[] parameterTypes)
	{
		memento.clearRedo();
		saveState();
		clearParameters(className, methodName);
		int i = 0;
		boolean success = true;
		
		for (String paramName : parameterNames) {
			success = addParameter(className, methodName, paramName, parameterTypes[i]);
			i++;
			if(!success)
			{
				memento.popUndo();
				return false;
			}
		}
		return true;
	}
	public boolean clearParameters(String className, String methodName)
	{
		memento.clearRedo();
		saveState();
		UMLClass umlClass = getClassByName(className);
		
	    if (umlClass != null) {
			if(umlClass.removeAllPar(methodName))
				return true;
			memento.popUndo();
	        return false;
	    }
		memento.popUndo();
	    return false;

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
		state.setMemento(this.memento);
		UMLDiagram  undo = memento.undoState();
		if(undo==null)
			return false;
		setGui(undo.getGui());
		setClassNameMap(undo.getClassNameMapToName());
		setClassMap(undo.getClassMapToRelation());
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
		state.setMemento(this.memento);
		memento.saveState(state);
		return true;
	}

	/**
     * Clears all classes and relationships from the UML diagram.
     */

	public void clear() {
		//memento.clearRedo();
		//memento.clearUndo();
		saveState();
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