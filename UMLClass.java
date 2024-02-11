package UMLL;

import java.util.*;


public class UMLClass {
		
	private String className;
	private ArrayList<Attribute> attributes; 
	private ArrayList<Method> methods;
	
	/**
	 * Initializes the class object with a name.
	 * Attributes and methods are automatically initialized since they 
	 * are a part of a class object.
	 * 
	 * @param String - The name of the class.
	 * 
	 * name cannot be null, nor empty, noe have empty spaces
	 * 
	 */
	
	public UMLClass() {
		
	}
	
	public UMLClass(String name) {
		this.className = name;
		attributes = new ArrayList<Attribute>(); 
		methods = new ArrayList<Method>();
	}
	
	/**
	 * Getter method to return the name of the class.
	 * 
	 * @returns the name of the current class.
	 * 	
	 */

	public String getName() {
		return className;		
	}
	
	/**
	 * Mutator method that changes the name of the class.
	 * 
	 * @param String - The new name of the class.
	 * 		
	 * newName cannot be null, empty, or contain empty spaces.
	 * 
	 */

	public void setName(String newName) {
		this.className = newName;
	}
	
	
	/**
	 * A method to find an instance of an Attribute object
	 * that has the same name since Attributes cannot have 
	 * the same names.
	 * 
	 * @param String -The name to search for.
	 * 
	 * @returns boolean - true when there is NOT another instance
	 * of the object with the name "name". False otherwise.
	 */

	public boolean containsAttribute(String name) {
		for(Attribute attribute : attributes) {
			if(attribute.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Getter method that gets the ArrayList of attributes.
	 * 
	 * @returns attributes from class.
	 * 
	 */

	public ArrayList<Attribute> getAttributes(){
		return attributes;
	}
	
	/**
	 * Sets attributes to parameter passed.
	 * 
	 * @params ArrayList<Attribute> - arraylist of attributes to update class attributes.
	 * 
	 * attrs cannot be null, or empty.
	 * 
	 */

	public void setAttributes(ArrayList<Attribute> attrs) {
		attributes = attrs;
	}
	
	/**
	 * Gets attributes from parameter passed if it exists within the class.
	 * 
	 * @params String - Name of existing attribute within class.
	 * 
	 * name cannot be null, empty, or contain empty spaces.
	 * 
	 * @returns an attribute if name passed exists, returns null otherwise.
	 * 
	 */

	public Attribute getAttribute(String name)
	{
		for(Attribute attribute : attributes) {
			if(attribute.getName().equals(name)) {
				return attribute;
			}
		}
		return null;
	}
	
	/**
	 * A method that adds a new attribute.
	 * 
	 * @param String - The name of the new attribute.
	 * @param String - The type of the new attribute.
	 * @param String - Name of the class where to add the attribute. 
	 * 
	 * @return true when a new attribute is added. False otherwise.
	 * 
	 * params cannot be null, empty, or contain empty spaces.
	 * 
	 */

	public boolean addAttribute(String name, String type, String className) {
		if(containsAttribute(name)) {
			return false;
		}
		return attributes.add(new Attribute(name, type, className));
	}

	/**
	 * A method that renames the specified attribute.
	 * 
	 * @param String - The name of the attribute to change.
	 * @param String - The new name of the attribute.
	 * 
	 * @returns true when the attribute's name is changed.
	 * returns false otherwise.
	 * 
	 * params cannot be null, empty, or contain empty spaces.
	 * 
	 */

	public boolean renameAttribute(String original, String newName) {
		if(!containsAttribute(original) && containsMethod(newName)) {
			return false;
		}
		for(Attribute attribute : attributes) {
			if(attribute.getName().equals(original)) {
				attribute.setName(newName);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A method that changes the type of the specified attribute.
	 * 
	 * @param String - The attribute to search for.
	 * @param String - The new type to set the attribute to.
	 *  
	 * @returns true when the attribute's type is changed
	 * returns false otherwise.
	 * 
	 * params cannot be null, empty, or contain empty spaces.
	 */

	public boolean changeAttributeType(String name, String newType) {
		for(Attribute attribute : attributes) {
			if(attribute.getName().equals(name)) {
				attribute.setType(newType);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A method that removes an attribute from the list.
	 * 
	 * @param String - The name of the attribute to delete.
	 * 
	 * @returns true when the attribute is deleted
	 * returns false otherwise.
	 * 
	 * param cannot be null, empty, or contain empty spaces.
	 * 
	 */

	public boolean deleteAttribute(String name) {
		if(!containsAttribute(name)) {
			return false;
		}
		for(Attribute attribute : attributes) {
			if(attribute.getName().equals(name)) {
				return attributes.remove(attribute);
			}
		}
		return false;
	}
	
	/**
	 * A method that removes all the attributes from the list.
	 * 
	 * @param String - The name of the attribute to delete.
	 * 
	 * @return true when the attribute is deleted
	 * false otherwise.
	 */

	public boolean deleteAttributes() {
		if(attributes.isEmpty()) {
			return false;
		}
		
		attributes.clear();
		return true;
	}
	
	
	/**
	 * A function that finds a method.
	 * 
	 * @param String - The name of the method.
	 * 
	 * @return true when the method is found,
	 * false otherwise.
	 * 
	 * param cannot be null, empty, or contain empty spaces.
	 */

	public boolean containsMethod(String name) {
		for(Method method : methods) {
			if(method.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A getter function that gets the methods list.
	 * 
	 * @return methods from class.
	 * 
	 */

	public ArrayList<Method> getMethods() {
		return methods;
	}
	
	/**
	 * A function that sets the methods from a passed ArrayList.
	 * 
	 * @param ArrayList<Method> - An array list of method type.
	 * 
	 * 
	 * param cannot be null or empty.
	 * 
	 */

	public void setMethods(ArrayList<Method> m) {
		methods = m;
	}
	
	/**
	 * A function that gets the methods from a class.
	 * 
	 * @param Sting - Name of method.
	 * 
	 * @returns method from a class if it exists,
	 * returns null otherwise.
	 * 
	 * param cannot be null, empty, or contain empty spaces.
	 * 
	 */

	public Method getMethod(String name)
	{
		for(Method method : methods) {
			if(method.getName().equals(name)) {
				return method;
			}
		}
		return null;
	}
	
	/**
	 * A function that adds a method.
	 * 
	 * @param String - The name of the new method.
	 * @param String - The type of the method.
	 * 
	 * @return true when the new method is added,
	 * false if otherwise.
	 * 
	 * params cannot be null, empty, or contain empty spaces.
	 * 
	 */

	public boolean addMethod(String name, String type) {
		if(containsMethod(name)) {
			return false;
		}
		return methods.add(new Method(name, type));
	}
	
	/**
	 * A function that changes the name of an existing method.
	 * 
	 * @param String - The original name of the method.
	 * @param String - The new name of the method.
	 * 
	 * @returns when the name is changed.
	 * 
	 * params cannot be null, empty, or contain empty spaces.
	 */

	public boolean renameMethod(String name, String newName) {
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
	 * A function that changes the type of a method.
	 * 
	 * @param String - The name of the method.
	 * @param String - The new type of the method.
	 * 
	 * @return true when the type of the method has changed.
	 * 
	 * params cannot be null, empty, or contain empty spaces.
	 */

	public boolean changeMethodType(String name, String type) {
		for(Method method : methods) {
			if(method.getName().equals(name)) {
				method.setType(type);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A function that deletes a method.
	 * 
	 * @param String - The name of the method to delete.
	 * 
	 * @return true when the method has been deleted,
	 * returns false otherwise.
	 * 
	 * param cannot be null, empty, or contain empty spaces.
	 */

	public boolean deleteMethod(String name) {
		for(Method method : methods) {
			if(method.getName().equals(name)) {
				return methods.remove(method);
			}
		}
		return false;	
	}
	
	/**
	 * A method that removes all the methods from the list.
	 * 
	 * @return true when the methods are deleted
	 * returns false otherwise.
	 * 
	 */
	public boolean deleteMethods() {
		if(methods.isEmpty()) {
			return false;
		}
		
		methods.clear();
		return true;
	}
	
	
	/**
	 * A method that prints all the methods from the class.
	 * 
	 * @return a string with the methods listed.
	 * 
	 */
	
	public String printMethods() {
		return " Methods:  " + methods;
	}
	
	/**
	 * A method that prints all the attributes from the class.
	 * 
	 * @return a string with the attributes listed.
	 * 
	 */

	public String printAttributes() {
		return " Attributes:  " + attributes;
	}
	
	/**
	 * prints all attributes and methods.
	 */

 @Override
	public String toString() {
		return printAttributes() + printMethods();
	}
}
