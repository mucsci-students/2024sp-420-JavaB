package com.classuml.Model;


import java.util.*;

import java.awt.Point;
import java.awt.FontMetrics;

/**
 * Represents a UML class with its class name, fields and methods.
 */
public class UMLClass {

	private String className;
	private ArrayList<Field> fields;
	private ArrayList<Method> methods;
	public Point position;

	public FontMetrics fm;
    public int uniformWidth = -1; // Cached width for uniform drawing
    public int totalHeight = -1; // Cached total height

    // public int boxWidth = 0;
    // public int boxHeight = 0;
	
	/**
	 * Constructs a new UMLClass with default constructor.
	 */
	public UMLClass() {
		this.fields = new ArrayList<>();
	    this.methods = new ArrayList<>();
		if (this.position == null){
			this.position = new Point();
		}
	}
	public UMLClass(UMLClass class2)
	{
		className = class2.getName();
		this.fields = new ArrayList<>();
	    this.methods = new ArrayList<>();
		for(Field field2 : class2.getFields())
		{
			addField(field2.getName(), field2.getType());
		}
		for(Method method2 : class2.getMethods())
		{
			addMethod(method2.getName(), method2.getReturnType());
			for(Parameter param2 : method2.getParameters())
			{
				addParameter(method2.getName(),param2.getName(), param2.getType());
			}
	
		}
		if (this.position == null){
			this.position = class2.position;
		}

	}

	/**
	 * Constructs a new UMLClass with the given name.
	 * @param name The name of the UML class.
	 */
	public UMLClass(String name) {
		this();
	    if (name != null && !name.isEmpty()) {
	        this.className = name;
	    }

	}
	public UMLClass(String name, Point pos) {
		this();
	    if (name != null && !name.isEmpty()) {
	        this.className = name;
	    }
		this.position = pos;

	}


	/**
	 * Retrieves the name of the UML class.
	 * @return The name of the UML class.
	 */
	public String getName() {
		return this.className;		
	}

	/**
	 * Sets the name of the UML class.
	 * @param newName The new name to be set.
	 */
	public void setName(String newName) {
		if (!(newName == null || newName == "")) {
		this.className = newName;
		}
	}

	/***********************************************************************************************/
	                             // FIELD
	/***********************************************************************************************/

	/**
	 * Checks if a field with the given name exists in the UML class.
	 * @param name The name of the field to check.
	 * @return true if the field exists, false otherwise.
	 */
	public boolean containsField(String name) {
		if (name == null || name == "") {
			return false;
		}
		else
		for(Field fld : fields) {
			if(fld.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieves all the fields of the UML class.
	 * @return ArrayList containing all the fields.
	 */
	public ArrayList<Field> getFields(){
		return fields;
	}

	/**
	 * Retrieves the field with the given name.
	 * @param name The name of the field to retrieve.
	 * @return The field object if found, otherwise null.
	 */
	public Field getField(String name){
		if(name == null || name == "") {
			return null;
		}
		for(Field  field : fields) {
			if(field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Adds a new field to the UML class.
	 * @param name The name of the field.
	 * @param type The type of the field.
	 * @return true if the field is added successfully, false otherwise.
	 */
	public boolean addField(String fieldName, String fieldType) {
	    // Check for null or empty field names and types
	    if (fieldName == null || fieldName.isEmpty() || fieldType == null || fieldType.isEmpty()) {
	        return false;
	    }
	    
	    // Check if a field with the same name already exists
	    for (Field field : this.fields) {
	        if (field.getName().equals(fieldName)) {
	            return false; // Field already exists
	        }
	    }
	    
	    // Add the new field
	    this.fields.add(new Field(fieldName, fieldType));
	    return true; // Field added successfully
	}


	/**
	 * Renames a field in the class.
	 *
	 * This method renames a field from its original name to a new name.
	 * If either the original name or the new name is null or an empty string,
	 * or if the original name does not exist as a field in the class and
	 * the new name conflicts with an existing method name, the renaming fails.
	 *
	 * @param original the original name of the field to be renamed
	 * @param newName the new name for the field
	 * @return true if the field is successfully renamed, false otherwise
	 */
	public boolean renameField(String original, String newName) {
		if(original == null || original == "" || newName == null || newName == "") {
			return false;
		}
		if(containsField(newName)) {
			return false;
		}
		for(Field fld : fields) {
			if(fld.getName().equals(original)) {
				fld.setName(newName);
				return true;
			}
		}
		return false;
	}
 
	/**
	 * Changes the type of a field in the class.
	 *
	 * This method changes the type of a field with the specified name to the new type.
	 * If the name or new type is null or an empty string, or if the field with the given name
	 * does not exist in the class, the operation fails.
	 *
	 * @param name the name of the field whose type is to be changed
	 * @param newType the new type for the field
	 * @return true if the field type is successfully changed, false otherwise
	 */
	public boolean changeFieldType(String name, String newType) {
		if(name == null || name == "" || newType == null || newType == "") {
			return false;
		}
		for(Field fld : fields) {
			if(fld.getName().equals(name)) {
				fld.setType(newType);
				return true;
			}
		}
		return false;
	}

	/**
	 * Deletes a field from the class.
	 *
	 * This method removes the field with the specified name from the class.
	 * If the name is null or an empty string, or if the field with the given name
	 * does not exist in the class, the deletion fails.
	 *
	 * @param name the name of the field to be deleted
	 * @return true if the field is successfully deleted, false otherwise
	 */
	public boolean deleteField(String name) {
		if(name == null || name == "") {
			return false;
		}
		for(Field fld : fields) {
			if(fld.getName().equals(name)) {
				return fields.remove(fld);
			}
		}
		return false;
	}

	/*******************************************************************************************************/
                       /**   METHOD   **/
	/*******************************************************************************************************/

	/**
	 * Checks if a method with the specified name exists in the class.
	 *
	 * This method checks if a method with the given name exists in the class.
	 * If the name is null or an empty string, the method returns false.
	 *
	 * @param name the name of the method to check
	 * @return true if the method exists in the class, false otherwise
	 */
	public boolean containsMethod(String name) {
		if(name == null || name == "") {
			return false;
		}
		for(Method method : methods) {
			if(method.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieves the list of methods in the class.
	 *
	 * @return the list of methods in the class
	 */
	public ArrayList<Method> getMethods() {
		return methods;
	}
	/**
	 * Retrieves the method with the specified name.
	 *
	 * @param name the name of the method to retrieve
	 * @return the method object if found, null otherwise
	 */
	public Method getMethod(String name){
		if(name == null || name == "") {
			return null;
		}
		for(Method method : methods) {
			if(method.getName().equals(name)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * Adds a new method to the class.
	 *
	 * This method adds a new method with the specified name and type to the class.
	 * If either the name or type is null or an empty string, or if a method with the
	 * given name already exists in the class, the addition fails.
	 *
	 * @param name the name of the new method
	 * @param type the return type of the new method
	 * @return true if the method is successfully added, false otherwise
	 */
	public boolean addMethod(String name, String type) {
		if((name == null || name == "") || (type == null || type == "")) {
			return false;
		}
		if(containsMethod(name)) {
			return false;
		}
		return methods.add(new Method(name, type));
	}

	/**
	 * Renames a method in the class.
	 *
	 * This method renames a method from its original name to a new name. If either the
	 * original name or the new name is null or an empty string, or if the original name
	 * does not exist as a method in the class and the new name conflicts with an existing
	 * method name, the renaming fails.
	 *
	 * @param name the original name of the method to be renamed
	 * @param newName the new name for the method
	 * @return true if the method is successfully renamed, false otherwise
	 */
	public boolean renameMethod(String name, String newName) {
		if(name == null || name == "" || newName == null || newName == "") {
			return false;
		}
		if(!containsMethod(name) && containsMethod(newName)) {
			return false;
		}
		for(Method method : methods) {
			if(method.getName().equals(name)) {
				method.setName(newName);
				return true;
			}
		}
		return false;
	}

	/**
	 * Changes the return type of method in the class.
	 *
	 * This method changes the return type of method with the specified name to the
	 * new type. If either the name or type is null or an empty string, or if the method
	 * with the given name does not exist in the class, the operation fails.
	 *
	 * @param name the name of the method whose return type is to be changed
	 * @param type the new return type for the method
	 * @return true if the return type is successfully changed, false otherwise
	 */
	public boolean changeMethodType(String name, String type) {
		if(name == null || name == "" || type == null || type == "") {
			return false;
		}
		for(Method method : methods) {
			if(method.getName().equals(name)) {
				method.setType(type);
				return true;
			}
		}
		return false;
	}

	/**
	 * Deletes a method from the class.
	 *
	 * This method removes the method with the specified name from the class.
	 * If the name is null or an empty string, or if the method with the given name
	 * does not exist in the class, the deletion fails.
	 *
	 * @param name the name of the method to be deleted
	 * @return true if the method is successfully deleted, false otherwise
	 */
	public boolean deleteMethod(String name) {
		if(name == null || name == "") {
			return false;
		}
		for(Method method : methods) {
			if(method.getName().equals(name)) {
				return methods.remove(method);
			}
		}
		return false;	
	}


	/****************************** Parameters *****************************************************************/

	
	/**
	 * Adds a parameter to a method in the class.
	 *
	 * This method adds a new parameter with the specified name and type to the method
	 * with the given name. If the method does not exist or if either the method name,
	 * parameter name, or parameter type is null or an empty string, the addition fails.
	 *
	 * @param methodName the name of the method to which the parameter will be added
	 * @param paramName the name of the new parameter
	 * @param paramType the type of the new parameter
	 * @return true if the parameter is successfully added, false otherwise
	 */
	public boolean addParameter(String methodName, String parameterName, String parameterType) {
	    Method method = getMethodByName(methodName);
	    if (method != null) {
	        return method.addParameter(parameterName, parameterType);
	    }
	    return false;
	}

	/** 
	 * Deletes a parameter from a method in the class.
	 *
	 * This method removes the parameter with the specified name from the method
	 * with the given name. If the method does not exist or if either the method name
	 * or parameter name is null or an empty string, the deletion fails.
	 *
	 * @param methodName the name of the method from which the parameter will be deleted
	 * @param parameterName the name of the parameter to be deleted
	 * @return true if the parameter is successfully deleted, false otherwise
	 */
	public boolean deleteParameter(String methodName, String parameterName) {
	    Method method = getMethodByName(methodName);
	    if (method != null) {
	        return method.deleteParameter(parameterName);
	    }
	    return false;
	}
	
	
	
	public Method getMethodByName(String methodName) {
	    // Return the method with the given name or null if not found
	    for (Method m : methods) {
	        if (m.getName().equals(methodName)) {
	            return m;
	        }
	    }
	    return null;
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
		for(Method method : methods){
			if (method.getName().equals(methodName)){
			return	method.deleteAllParameters();
			}
		}
		return false;
	}
}