package com.classuml.Model;

public class Parameter extends FieldAndMethodLayOut{
   
    /**
     * Create new parameter with name and type.
     * 
     * @param name the name new parameter.
     * @param type the type the new parameter.
     */
    public Parameter(String name, String type) {
       super(name, type);
    }
    


    /**
     * Returns the string representation of the parameter.
     */
    @Override
	public String toString() {
		return "Name: " + getName() + "," + "\n			 Type: " + getType();
	}
} 