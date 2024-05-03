package com.classuml.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import jline.console.ConsoleReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.image.*;

import com.classuml.Model.*;
import com.classuml.View.guiView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.Point;

public class UMLCli {
	// Scanner object for user input
	//private static final Scanner scanner = new Scanner(System.in);
	private static ConsoleReader reader;
	// Gson object for JSON serialization and de-serialization
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	// UMLDiagram object to store the UML diagram
	private static final UMLDiagram umlDiagram = new UMLDiagram();

	private static final UMLCompleter generalCompleter = new UMLCompleter();

	private static JPanel classPanelContainer = new JPanel();
	private static guiView view = new guiView(umlDiagram.getClasses(), umlDiagram.getRelationships());

	
/**************************************************************************************************************************************/
    /**   MENU DISPLAY   **/
/**************************************************************************************************************************************/

	// Main method
	public static void launch() throws IOException {
		reader = getConsoleReader();
		reader.addCompleter(generalCompleter);

		System.out.println("Welcome to JAVA2 UML Editor!");

		boolean exit = false; 

		// Display menu and get user choice
		displayMenu();

		// Loop until the user chooses to exit
		while (!exit) {

		String choice = getLowerCaseTrimmedUserChoice();
		String[] choiceTokens = choice.split(" ");

		// Perform actions based on user choice
		switch (choiceTokens[0]) {
			case "menu":
				displayMenu();
				break;
			case "addclass":
			case "ac":
				if (choiceTokens.length == 1) {
					createClass();
				}
				else if (choiceTokens.length == 2) {
					createClass(choiceTokens[1]);
				}
				else {
					System.out.println("Need the following arguments for AddClass: classname");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "deleteclass":
			case "dc":
				if (choiceTokens.length == 1) {
					deleteClass();
				}
				else if (choiceTokens.length == 2) {
					deleteClass(choiceTokens[1]);
				}
				else {
					System.out.println("Need the following arguments for DeleteClass: classname");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "renameclass":
			case "rc":
				if (choiceTokens.length == 1) {
					renameClass();
				}
				else if (choiceTokens.length == 2) {
					renameClass(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					renameClass(choiceTokens[1], choiceTokens[2]);
				}
				else {
					System.out.println("Need the following arguments for RenameClass: oldclassname newclassname");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "addparameter":
			case "ap":
				if (choiceTokens.length == 1) {
					addParameter();
				}
				else if (choiceTokens.length == 2) {
					addParameter(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					addParameter(choiceTokens[1], choiceTokens[2]);
				}
				else if (choiceTokens.length == 4) {
					addParameter(choiceTokens[1], choiceTokens[2], choiceTokens[3]);
				}
				else if (choiceTokens.length == 5) {
					addParameter(choiceTokens[1], choiceTokens[2], choiceTokens[3], choiceTokens[4]);
				}
				else {
					System.out.println("Need the following arguments for AddParameter: classname methodname parametername parametertype");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "deleteparameter":
			case "dp":
				if (choiceTokens.length == 1) {
					deleteParameter();
				}
				else if (choiceTokens.length == 2) {
					deleteParameter(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					deleteParameter(choiceTokens[1], choiceTokens[2]);
				}
				else if (choiceTokens.length == 4) {
					deleteParameter(choiceTokens[1], choiceTokens[2], choiceTokens[3]);
				}
				else {
					System.out.println("Need the following arguments for DeleteParameter: classname methodname parametername");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "renameparameter":
			case "rp":
				if (choiceTokens.length == 1) {
					renameParameter();
				}
				else if (choiceTokens.length == 2) {
					renameParameter(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					renameParameter(choiceTokens[1], choiceTokens[2]);
				}
				else if (choiceTokens.length == 4) {
					renameParameter(choiceTokens[1], choiceTokens[2], choiceTokens[3]);
				}
				else if (choiceTokens.length == 5) {
					renameParameter(choiceTokens[1], choiceTokens[2], choiceTokens[3], choiceTokens[4]);
				}
				else {
					System.out.println("Need the following arguments for RenameParameter: classname methodname oldparametername newparametername");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "replaceparams":
			case "replaceparameters":
				if (choiceTokens.length == 1) {
					replaceParams();
				}
				else if (choiceTokens.length == 2) {
					replaceParams(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					replaceParams(choiceTokens[1], choiceTokens[2]);
				}
				else {
					System.out.println("Need the following arguments for ReplaceParameters: classname methodname");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "addfield":
			case "af":
				if (choiceTokens.length == 1) {
					addField();
				}
				else if (choiceTokens.length == 2) {
					addField(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					addField(choiceTokens[1], choiceTokens[2]);
				}
				else if (choiceTokens.length == 4) {
					addField(choiceTokens[1], choiceTokens[2], choiceTokens[3]);
				}
				else {
					System.out.println("Need the following arguments for AddField: classname fieldname fieldtype");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "renamefield":
			case "rf":
				if (choiceTokens.length == 1) {
					renameField();
				}
				else if (choiceTokens.length == 2) {
					renameField(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					renameField(choiceTokens[1], choiceTokens[2]);
				}
				else if (choiceTokens.length == 4) {
					renameField(choiceTokens[1], choiceTokens[2], choiceTokens[3]);
				}
				else {
					System.out.println("Need the following arguments for RenameField: classname oldfieldname newfieldname");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "deletefield":
			case "df":
				if (choiceTokens.length == 1) {
					deleteField();
				}
				else if (choiceTokens.length == 2) {
					deleteField(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					deleteField(choiceTokens[1], choiceTokens[2]);
				}
				else {
					System.out.println("Need the following arguments for DeleteField: classname fieldname");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "addmethod":
			case "am":
				if (choiceTokens.length == 1) {
					addMethod();
				}
				else if (choiceTokens.length == 2) {
					addMethod(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					addMethod(choiceTokens[1], choiceTokens[2]);
				}
				else if (choiceTokens.length == 4) {
					addMethod(choiceTokens[1], choiceTokens[2], choiceTokens[3]);
				}
				else {
					System.out.println("Need the following arguments for AddMethod: classname methodname returntype");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "deletemethod":
			case "dm":
				if (choiceTokens.length == 1) {
					deleteMethod();
				}
				else if (choiceTokens.length == 2) {
					deleteMethod(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					deleteMethod(choiceTokens[1], choiceTokens[2]);
				}
				else {
					System.out.println("Need the following arguments for DeleteMethod: classname methodname");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "renamemethod":
			case "rm":
				if (choiceTokens.length == 1) {
					renameMethod();
				}
				else if (choiceTokens.length == 2) {
					renameMethod(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					renameMethod(choiceTokens[1], choiceTokens[2]);
				}
				else if (choiceTokens.length == 4) {
					renameMethod(choiceTokens[1], choiceTokens[2], choiceTokens[3]);
				}
				else {
					System.out.println("Need the following arguments for RenameMethod: classname oldmethodname newmethodname");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "addrelationship":
			case "ar":
				if (choiceTokens.length == 1) {
					addRelationship();
				}
				else if (choiceTokens.length == 2) {
					addRelationship(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					addRelationship(choiceTokens[1], choiceTokens[2]);
				}
				else if (choiceTokens.length == 4) {
					addRelationship(choiceTokens[1], choiceTokens[2], choiceTokens[3]);
				}
				else {
					System.out.println("Need the following arguments for AddRelationship: sourceclassname destinationclassname reltype");
					System.out.println("Choices for reltype are (please type only a number):");
					System.out.println("    1 for Aggregation, 2 for Composition, 3 for Inheritance, 4 for Realization");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "deleterelationship":
			case "dr":
				if (choiceTokens.length == 1) {
					deleteRelationship();
				}
				else if (choiceTokens.length == 2) {
					deleteRelationship(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					deleteRelationship(choiceTokens[1], choiceTokens[2]);
				}
				else {
					System.out.println("Need the following arguments for DeleteRelationship: sourceclassname destinationclassname");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "changetype":
			case "changerelationshiptype":
			case "ct":
				if (choiceTokens.length == 1) {
					changeType();
				}
				else if (choiceTokens.length == 2) {
					changeType(choiceTokens[1]);
				}
				else if (choiceTokens.length == 3) {
					changeType(choiceTokens[1], choiceTokens[2]);
				}
				else if (choiceTokens.length == 4) {
					changeType(choiceTokens[1], choiceTokens[2], choiceTokens[3]);
				}
				else {
					System.out.println("Need the following arguments for ChangeRelationshipType: sourceclassname destinationclassname reltype");
					System.out.println("Choices for reltype are (please type only a number):");
					System.out.println("    1 for Aggregation, 2 for Composition, 3 for Inheritance, 4 for Realization");
					System.out.println("Please enter a valid command:");
				}
				changeComponent();
				break;
			case "listclasses":
			case "lcs":
				listClasses();
				break;
			case "listclass":
			case "listclasscontents":
			case "lc":
				if (choiceTokens.length == 1) {
					listClassContents();
				}
				else if (choiceTokens.length == 2) {
					listClassContents(choiceTokens[1]);
				}
				else {
					System.out.println("Need the following arguments for ListClassContents: classname");
					System.out.println("Please enter a valid command:");
				}
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
			case "snapshot":
			case "saveasimage":
			case "si":
				CLIOutputAsImage();
				break;
			default:
				System.out.println("Invalid choice.");
				System.out.println("Please enter a valid command:");
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
		System.out.println("SaveAsImage (si) - Outputs listing of classes, fields, methods, and relationships as a .jpg.");
		System.out.println("  ");
		System.out.println("Save (s)- Save diagram to JSON file.");
		System.out.println("Load (lo)- Load diagram from JSON file.");
		System.out.println("Menu (m)- Display main menu.");
		System.out.println("Help (h)- Help.");
		System.out.println("Exit - Exit.");
		System.out.println("Undo - undos the last command.");
		System.out.println("Redo - goes back to state before previous undo.");
		System.out.println("  ");
    }

	private static UMLCompleter setClassCompleter(){
		ArrayList<UMLClass> clsList = umlDiagram.getClasses();
		ArrayList<String> clsSet = new ArrayList<>();
		for(UMLClass cls : clsList){
			clsSet.add(cls.getName());
		}
		UMLCompleter classCompleter = new UMLCompleter(clsSet);
		return classCompleter;
	}

	private static UMLCompleter setMethodCompleter(UMLClass cls){
		ArrayList<Method> methList = cls.getMethods();
		ArrayList<String> methSet = new ArrayList<>();
		for(Method meth : methList){
			methSet.add(meth.getName());
		}
		UMLCompleter methodCompleter = new UMLCompleter(methSet);
		return methodCompleter;

	}

	private static UMLCompleter setFieldCompleter(UMLClass cls){
		ArrayList<Field> fieldList = cls.getFields();
		ArrayList<String> fieldSet = new ArrayList<>();
		for(Field field : fieldList){
			fieldSet.add(field.getName());
		}
		UMLCompleter fieldCompleter = new UMLCompleter(fieldSet);
		return fieldCompleter;
	}

	private static UMLCompleter setParamCompleter(Method meth){
		ArrayList<Parameter> paramList = (ArrayList<Parameter>) meth.getParameters();
		ArrayList<String> paramSet = new ArrayList<>();
		for(Parameter param : paramList){
			paramSet.add(param.getName());
		}
		UMLCompleter paramCompleter = new UMLCompleter(paramSet);
		return paramCompleter;
	}

	private static ConsoleReader getConsoleReader() throws IOException {
		ConsoleReader creader = new ConsoleReader();
		creader.setPrompt("\n> ");
		return creader;
	}

	// Get user choice
	private static String getLowerCaseTrimmedUserChoice() throws IOException {
		//return scanner.nextLine().toLowerCase().trim();
		String c = reader.readLine();
		if (c == null) {
			// Handle end of input
			System.out.println("End of input detected. Exiting program.");
			System.exit(0);
			return null;
		}
		else {
			return c.toLowerCase().trim();
		}
	}

	private static String getReaderValue() throws IOException {
		String c = reader.readLine();
		if (c == null) {
			// Handle end of input
			System.out.println("End of input detected. Exiting program.");
			System.exit(0);
			return null;
		}
		else 
		{
			return c.trim();
		}
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

	private static void changeComponent() {
		classPanelContainer.remove(view);
		view.updateContents(umlDiagram.getClasses(), umlDiagram.getRelationships());
		classPanelContainer.add(view);
	    classPanelContainer.revalidate();
	    classPanelContainer.repaint();
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

	protected static void createClass(String className) throws IOException {
		if (umlDiagram.addClass(className)) {
			System.out.println("Class " + className + " created successfully.");
		}
		else {
			System.out.println("Failed to create class " + className + ". Class may already exist."); 
		}
	}

	// Method to delete a class
	protected static void deleteClass() throws IOException {
		UMLCompleter deleteCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(deleteCompleter);
		
		System.out.println("Enter the name of the class to delete: ");
		String className = getReaderValue();
		boolean success = umlDiagram.deleteClass(className);
		if (success) {
			System.out.println("Class " + className + " deleted successfully.");
		} else {
			System.out.println("Failed to delete class " + className + ". Class may not exist.");
		}
		reader.removeCompleter(deleteCompleter);
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteClass(String className) throws IOException {
		UMLCompleter deleteCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(deleteCompleter);

		if (umlDiagram.deleteClass(className)) {
			System.out.println("Class " + className + " deleted successfully.");
		} else {
			System.out.println("Failed to delete class " + className + ". Class may not exist.");
		}
		reader.removeCompleter(deleteCompleter);
		reader.addCompleter(deleteCompleter);
	}

	// Method to rename a class
	protected static void renameClass() throws IOException {

		UMLCompleter renameCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(renameCompleter);

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
		reader.removeCompleter(renameCompleter);
		reader.addCompleter(generalCompleter);
	}

	protected static void renameClass(String oldClassName) throws IOException {
		UMLCompleter renameCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(renameCompleter);
		
		System.out.print("Enter the new name of the class to replace " + oldClassName + ": ");
		String newClassName = getReaderValue();
		boolean success = umlDiagram.renameClass(oldClassName, newClassName);
		if (success) {
			System.out.println("Class " + oldClassName + " renamed to " + newClassName + " successfully.");
		} else {
			System.out.println(
					"Failed to rename class " + oldClassName + ". Class may not exist or new name may already be in use.");
		}
		reader.removeCompleter(renameCompleter);
		reader.addCompleter(generalCompleter);
	}

	protected static void renameClass(String oldClassName, String newClassName) throws IOException {
		
		UMLCompleter renameCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(renameCompleter);

		boolean success = umlDiagram.renameClass(oldClassName, newClassName);
		if (success) {
			System.out.println("Class " + oldClassName + " renamed to " + newClassName + " successfully.");
		} else {
			System.out.println(
					"Failed to rename class " + oldClassName + ". Class may not exist or new name may already be in use.");
		}
		reader.removeCompleter(renameCompleter);
		reader.addCompleter(generalCompleter);
	}
	
/**************************************************************************************************************************************/
    /**   PARAMETERS   **/ 
/** @throws IOException *************************************************************************************************************************************/

	// Method to add a parameter to a class
	protected static void addParameter() throws IOException {

		UMLCompleter classCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(classCompleter);

		System.out.println("Enter the name of the class to add parameter to: ");
		String className = getReaderValue();

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);

		System.out.print("Enter the name of the method to add a parameter: ");
		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void addParameter(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(classCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);

		System.out.print("Enter the name of the method to add a parameter: ");
		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void addParameter(String className, String methodName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(classCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void addParameter(String className, String methodName, String paramName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(classCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
		System.out.print("Enter the name of parameter type: ");
		String typeName = getReaderValue();
		boolean success = umlDiagram.addParameter(className, methodName, paramName, typeName);
		if (success) {
		System.out.println("Parameter " + paramName + " added to class " + className + " successfully.");
		} else {
		System.out.println("Failed to add parameter to class " + className + ". Class or method may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void addParameter(String className, String methodName, String paramName, String typeName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.removeCompleter(generalCompleter);
		reader.addCompleter(classCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
		boolean success = umlDiagram.addParameter(className, methodName, paramName, typeName);
		if (success) {
		System.out.println("Parameter " + paramName + " added to class " + className + " successfully.");
		} else {
		System.out.println("Failed to add parameter to class " + className + ". Class or method may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	// Method to delete a parameter from a class
	protected static void deleteParameter() throws IOException {

		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the class to delete parameter from: ");

		String className = getReaderValue();
		reader.removeCompleter(classCompleter);
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		System.out.println("Enter the name of the method to to delete parameter from: ");

		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		System.out.print("Enter the name of the parameter to delete: ");
		String paramName = getReaderValue();
		reader.removeCompleter(paramCompleter);
		boolean success = umlDiagram.deleteParameter(className, methodName, paramName);
		if (success) {
			System.out.println("Parameter " + paramName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println("Failed to delete parameter from class " + className + ". Class or method may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteParameter(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		System.out.println("Enter the name of the method to to delete parameter from: ");

		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		System.out.print("Enter the name of the parameter to delete: ");
		String paramName = getReaderValue();
		reader.removeCompleter(paramCompleter);
		boolean success = umlDiagram.deleteParameter(className, methodName, paramName);
		if (success) {
			System.out.println("Parameter " + paramName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println("Failed to delete parameter from class " + className + ". Class or method may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteParameter(String className, String methodName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		System.out.print("Enter the name of the parameter to delete: ");
		String paramName = getReaderValue();
		reader.removeCompleter(paramCompleter);
		boolean success = umlDiagram.deleteParameter(className, methodName, paramName);
		if (success) {
			System.out.println("Parameter " + paramName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println("Failed to delete parameter from class " + className + ". Class or method may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteParameter(String className, String methodName, String paramName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);
		
		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		reader.removeCompleter(paramCompleter);
		boolean success = umlDiagram.deleteParameter(className, methodName, paramName);
		if (success) {
			System.out.println("Parameter " + paramName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println("Failed to delete parameter from class " + className + ". Class or method may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	// Method to rename a parameter in a class
	protected static void renameParameter() throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the class containing the parameter: ");

		String className = getReaderValue();
		reader.removeCompleter(classCompleter);
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		System.out.println("Enter the name of the method to to delete parameter from: ");

		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		System.out.print("Enter the current name of the parameter: ");
		String oldParamName = getReaderValue();
		reader.removeCompleter(paramCompleter);
		System.out.print("Enter the new name of the parameter: ");
		String newParamName = getReaderValue();
		boolean success = umlDiagram.renameParameter(className, methodName, oldParamName, newParamName);
		if (success) {
		System.out.println("Parameter " + oldParamName + " renamed to " + newParamName + " in class " + className + " successfully.");
		} else {
		System.out.println("Failed to rename Parameter in class " + className + ". Class or Parameter may not exist, or new name may already be in use.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void renameParameter(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		System.out.println("Enter the name of the method to to delete parameter from: ");

		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		System.out.print("Enter the current name of the parameter: ");
		String oldParamName = getReaderValue();
		reader.removeCompleter(paramCompleter);
		System.out.print("Enter the new name of the parameter: ");
		String newParamName = getReaderValue();
		boolean success = umlDiagram.renameParameter(className, methodName, oldParamName, newParamName);
		if (success) {
		System.out.println("Parameter " + oldParamName + " renamed to " + newParamName + " in class " + className + " successfully.");
		} else {
		System.out.println("Failed to rename Parameter in class " + className + ". Class or Parameter may not exist, or new name may already be in use.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void renameParameter(String className, String methodName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		System.out.print("Enter the current name of the parameter: ");
		String oldParamName = getReaderValue();
		reader.removeCompleter(paramCompleter);
		System.out.print("Enter the new name of the parameter: ");
		String newParamName = getReaderValue();
		boolean success = umlDiagram.renameParameter(className, methodName, oldParamName, newParamName);
		if (success) {
		System.out.println("Parameter " + oldParamName + " renamed to " + newParamName + " in class " + className + " successfully.");
		} else {
		System.out.println("Failed to rename Parameter in class " + className + ". Class or Parameter may not exist, or new name may already be in use.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void renameParameter(String className, String methodName, String oldParamName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		reader.removeCompleter(paramCompleter);
		System.out.print("Enter the new name of the parameter: ");
		String newParamName = getReaderValue();
		boolean success = umlDiagram.renameParameter(className, methodName, oldParamName, newParamName);
		if (success) {
		System.out.println("Parameter " + oldParamName + " renamed to " + newParamName + " in class " + className + " successfully.");
		} else {
		System.out.println("Failed to rename Parameter in class " + className + ". Class or Parameter may not exist, or new name may already be in use.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void renameParameter(String className, String methodName, String oldParamName, String newParamName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		reader.removeCompleter(paramCompleter);
		boolean success = umlDiagram.renameParameter(className, methodName, oldParamName, newParamName);
		if (success) {
		System.out.println("Parameter " + oldParamName + " renamed to " + newParamName + " in class " + className + " successfully.");
		} else {
		System.out.println("Failed to rename Parameter in class " + className + ". Class or Parameter may not exist, or new name may already be in use.");
		}
		reader.addCompleter(generalCompleter);
	}
  
	protected static void replaceParams() throws IOException {

		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the class containing the parameters to be replaced: ");

		String className = getReaderValue();
		reader.removeCompleter(classCompleter);
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		System.out.println("Enter the name of the method to to replace parameters in: ");

		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		System.out.println("Please type the name and type of the first param in the format: name type");
		String paramList = getReaderValue();
		reader.removeCompleter(paramCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void replaceParams(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		System.out.println("Enter the name of the method to to replace parameters in: ");

		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		System.out.println("Please type the name and type of the first param in the format: name type");
		String paramList = getReaderValue();
		reader.removeCompleter(paramCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void replaceParams(String className, String methodName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
		Method meth = cls.getMethod(methodName);
		UMLCompleter paramCompleter = setParamCompleter(meth);
		reader.addCompleter(paramCompleter);

		System.out.println("Please type the name and type of the first param in the format: name type");
		String paramList = getReaderValue();
		reader.removeCompleter(paramCompleter);
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
		reader.addCompleter(generalCompleter);
	}
    
/**************************************************************************************************************************************/
    /**   FIELDS   **/
/** @throws IOException *************************************************************************************************************************************/


	// Method to add an attribute to a class
	protected static void addField() throws IOException {

		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the class to add field to: ");
		String className = getReaderValue();
		reader.removeCompleter(classCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void addField(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void addField(String className, String fieldName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		System.out.print("Enter the type of the field: ");
		String fieldType = getReaderValue();
		boolean success = umlDiagram.addField(className, fieldName, fieldType);
		if (success) {
			System.out.println("Field " + fieldName + " added to class " + className + " successfully.");
		} else {
			System.out.println("Failed to add field to class " + className + ". Class may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void addField(String className, String fieldName, String fieldType) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		boolean success = umlDiagram.addField(className, fieldName, fieldType);
		if (success) {
			System.out.println("Field " + fieldName + " added to class " + className + " successfully.");
		} else {
			System.out.println("Failed to add field to class " + className + ". Class may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	// Method to delete an attribute from a class
	protected static void deleteField() throws IOException {

		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the class to delete field from: ");
		
		String className = getReaderValue();
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter fieldCompleter = setFieldCompleter(cls);
		reader.addCompleter(fieldCompleter);

		System.out.print("Enter the name of the field to delete: ");
		String fieldName = getReaderValue();
		reader.removeCompleter(fieldCompleter);
		boolean success = umlDiagram.deleteField(className, fieldName);
		if (success) {
			System.out.println("Field " + fieldName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println(
					"Failed to delete field from class " + className + ". Class or field may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteField(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter fieldCompleter = setFieldCompleter(cls);
		reader.addCompleter(fieldCompleter);

		System.out.print("Enter the name of the field to delete: ");
		String fieldName = getReaderValue();
		reader.removeCompleter(fieldCompleter);
		boolean success = umlDiagram.deleteField(className, fieldName);
		if (success) {
			System.out.println("Field " + fieldName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println(
					"Failed to delete field from class " + className + ". Class or field may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteField(String className, String fieldName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter fieldCompleter = setFieldCompleter(cls);
		reader.addCompleter(fieldCompleter);

		reader.removeCompleter(fieldCompleter);
		boolean success = umlDiagram.deleteField(className, fieldName);
		if (success) {
			System.out.println("Field " + fieldName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println(
					"Failed to delete field from class " + className + ". Class or field may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	// Method to rename an attribute in a class
	protected static void renameField() throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the class containing the field: ");

		String className = getReaderValue();
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter fieldCompleter = setFieldCompleter(cls);
		reader.addCompleter(fieldCompleter);

		System.out.print("Enter the current name of the field: ");
		String oldFieldName = getReaderValue();
		reader.removeCompleter(fieldCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void renameField(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter fieldCompleter = setFieldCompleter(cls);
		reader.addCompleter(fieldCompleter);

		System.out.print("Enter the current name of the field: ");
		String oldFieldName = getReaderValue();
		reader.removeCompleter(fieldCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void renameField(String className, String oldFieldName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter fieldCompleter = setFieldCompleter(cls);
		reader.addCompleter(fieldCompleter);

		reader.removeCompleter(fieldCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void renameField(String className, String oldFieldName, String newFieldName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter fieldCompleter = setFieldCompleter(cls);
		reader.addCompleter(fieldCompleter);

		reader.removeCompleter(fieldCompleter);
		boolean success = umlDiagram.renameField(className, oldFieldName, newFieldName);
		if (success) {
			System.out.println("Field " + oldFieldName + " renamed to " + newFieldName + " in class "
					+ className + " successfully.");
		} else {
			System.out.println("Failed to rename field in class " + className
					+ ". Class or field may not exist, or new name may already be in use.");
		}
		reader.addCompleter(generalCompleter);
	}
	
	
/**************************************************************************************************************************************/
    /**   METHODS   **/
/** @throws IOException ************************************************************************************************************************************/


	// Method to add a method to a class
	protected static void addMethod() throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the class to add method to: ");

		String className = getReaderValue();
		reader.removeCompleter(classCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void addMethod(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void addMethod(String className, String methodName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		System.out.print("Enter the return type of the method: ");
		String returnType = getReaderValue();
		boolean success = umlDiagram.addMethod(className, methodName, returnType);
		if (success) {
			System.out.println("Method " + methodName + " added to class " + className + " successfully.");
		} else {
			System.out.println("Failed to add method to class " + className + ". Class may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void addMethod(String className, String methodName, String returnType) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		boolean success = umlDiagram.addMethod(className, methodName, returnType);
		if (success) {
			System.out.println("Method " + methodName + " added to class " + className + " successfully.");
		} else {
			System.out.println("Failed to add method to class " + className + ". Class may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	// Method to delete a method from a class
	protected static void deleteMethod() throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the class to delete method from: ");

		String className = getReaderValue();
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);
		
		System.out.print("Enter the name of the method to delete: ");
		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
		boolean success = umlDiagram.deleteMethod(className, methodName);
		if (success) {
			System.out.println("Method " + methodName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println("Failed to delete method from class " + className + ". Class or method may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteMethod(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);
		
		System.out.print("Enter the name of the method to delete: ");
		String methodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
		boolean success = umlDiagram.deleteMethod(className, methodName);
		if (success) {
			System.out.println("Method " + methodName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println("Failed to delete method from class " + className + ". Class or method may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteMethod(String className, String methodName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);
		
		reader.removeCompleter(methodCompleter);
		boolean success = umlDiagram.deleteMethod(className, methodName);
		if (success) {
			System.out.println("Method " + methodName + " deleted from class " + className + " successfully.");
		} else {
			System.out.println("Failed to delete method from class " + className + ". Class or method may not exist.");
		}
		reader.addCompleter(generalCompleter);
	}

	// Method to rename a method in a class
	protected static void renameMethod() throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the class containing the method: ");

		String className = getReaderValue();
		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);

		System.out.print("Enter the current name of the method: ");
		String oldMethodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
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
		reader.addCompleter(generalCompleter);
	}
	
	protected static void renameMethod(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);

		System.out.print("Enter the current name of the method: ");
		String oldMethodName = getReaderValue();
		reader.removeCompleter(methodCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void renameMethod(String className, String oldMethodName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void renameMethod(String className, String oldMethodName, String newMethodName) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		UMLClass cls = umlDiagram.getClassByName(className);
		UMLCompleter methodCompleter = setMethodCompleter(cls);
		reader.removeCompleter(classCompleter);
		reader.addCompleter(methodCompleter);

		reader.removeCompleter(methodCompleter);
		boolean success = umlDiagram.renameMethod(className, oldMethodName, newMethodName);
		if (success) {
			System.out.println("Method " + oldMethodName + " renamed to " + newMethodName + " in class " + className
					+ " successfully.");
		} else {
			System.out.println("Failed to rename method in class " + className
					+ ". Class or method may not exist, or new name may already be in use.");
		}
		reader.addCompleter(generalCompleter);
	}

/**************************************************************************************************************************************/
    /**   RELATIONSHIPS   **/
/** @throws IOException ************************************************************************************************************************************/

	// Method to add a relationship to a class
	protected static void addRelationship() throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the source class: ");
		String sourceClass = getReaderValue();
		System.out.println("Enter the name of the destination class: ");
		String destinationClass = getReaderValue();
		reader.removeCompleter(classCompleter);
		System.out.println(
				"Choose a relationship type (Enter a number): \n1. Aggregation\n2. Composition\n3. Inheritance\n4. Realization\n");
		int type = Integer.parseInt(getReaderValue());

		boolean success = umlDiagram.addRelationship(sourceClass, destinationClass, type);
		if (success) {
			System.out.println("Relationship added successfully.");
		} else {
			System.out.println("Failed to add relationship.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void addRelationship(String sourceClass) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the destination class: ");
		String destinationClass = getReaderValue();
		reader.removeCompleter(classCompleter);
		System.out.println(
				"Choose a relationship type (Enter a number): \n1. Aggregation\n2. Composition\n3. Inheritance\n4. Realization\n");
		int type = Integer.parseInt(getReaderValue());

		boolean success = umlDiagram.addRelationship(sourceClass, destinationClass, type);
		if (success) {
			System.out.println("Relationship added successfully.");
		} else {
			System.out.println("Failed to add relationship.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void addRelationship(String sourceClass, String destinationClass) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		System.out.println(
				"Choose a relationship type (Enter a number): \n1. Aggregation\n2. Composition\n3. Inheritance\n4. Realization\n");
		int type = Integer.parseInt(getReaderValue());

		boolean success = umlDiagram.addRelationship(sourceClass, destinationClass, type);
		if (success) {
			System.out.println("Relationship added successfully.");
		} else {
			System.out.println("Failed to add relationship.");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void addRelationship(String sourceClass, String destinationClass, String relType) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		int type = Integer.parseInt(relType);

		boolean success = umlDiagram.addRelationship(sourceClass, destinationClass, type);
		if (success) {
			System.out.println("Relationship added successfully.");
		} else {
			System.out.println("Failed to add relationship.");
		}
		reader.addCompleter(generalCompleter);
	}

	// Method to delete a relationship between classes
	protected static void deleteRelationship() throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the first class in the relationship: ");
		String class1 = getReaderValue();
		System.out.print("Enter the name of the second class in the relationship: ");
		String class2 = getReaderValue();
		reader.removeCompleter(classCompleter);

		boolean success = umlDiagram.deleteRelationship(class1, class2);
		if (success) {
			System.out.println("Relationship deleted successfully between " + class1 + " and " + class2 + ".");
		} else {
			System.out.println("Failed to delete relationship between " + class1 + " and " + class2 + ".");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteRelationship(String class1) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.print("Enter the name of the second class in the relationship: ");
		String class2 = getReaderValue();
		reader.removeCompleter(classCompleter);

		boolean success = umlDiagram.deleteRelationship(class1, class2);
		if (success) {
			System.out.println("Relationship deleted successfully between " + class1 + " and " + class2 + ".");
		} else {
			System.out.println("Failed to delete relationship between " + class1 + " and " + class2 + ".");
		}
		reader.addCompleter(generalCompleter);
	}

	protected static void deleteRelationship(String class1, String class2) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		boolean success = umlDiagram.deleteRelationship(class1, class2);
		if (success) {
			System.out.println("Relationship deleted successfully between " + class1 + " and " + class2 + ".");
		} else {
			System.out.println("Failed to delete relationship between " + class1 + " and " + class2 + ".");
		}
		reader.addCompleter(generalCompleter);
	}

	// Method to change the type of an existing relationship
	protected static void changeType() throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.println("Enter the name of the first class in the relationship: ");
		String class1 = getReaderValue();
		System.out.print("Enter the name of the second class in the relationship: ");
		String class2 = getReaderValue();
		reader.removeCompleter(classCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void changeType(String class1) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		System.out.print("Enter the name of the second class in the relationship: ");
		String class2 = getReaderValue();
		reader.removeCompleter(classCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void changeType(String class1, String class2) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
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
		reader.addCompleter(generalCompleter);
	}

	protected static void changeType(String class1, String class2, String relType) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

		reader.removeCompleter(classCompleter);
		int type = Integer.parseInt(relType);

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
		reader.addCompleter(generalCompleter);
	}

/**************************************************************************************************************************************/
    /**   SAVE DIAGRAM   **/
/*** @throws IOException ************************************************************************************************************************************/

	
	// Method to save the UML diagram to a JSON file
	protected static void saveDiagram() throws IOException {
		reader.removeCompleter(generalCompleter);
		System.out.print("Enter the file name to save the diagram (JSON format): ");
		String filePath = getReaderValue().trim();
		if (!filePath.contains(".json")) {
			filePath += ".json";
		}
		try (FileWriter writer = new FileWriter(filePath)) {
			JsonObject jsonDiagram = new JsonObject();

			// Convert classes to JSON array
			JsonArray jsonClasses = new JsonArray();
			for (UMLClass umlClass : umlDiagram.getClasses()) {
				JsonObject jsonClass = new JsonObject();
				jsonClass.addProperty("name", umlClass.getName());

				// Add the position property
				Point pos = umlClass.position;
				JsonObject position = new JsonObject();
				position.addProperty("x", pos.x);
				position.addProperty("y", pos.y);
				jsonClass.add("position", position);

				// Convert fields to JSON array
				JsonArray jsonFields = new JsonArray();
				for (Field field : umlClass.getFields()) {
					JsonObject jsonField = new JsonObject();
					jsonField.addProperty("name", field.getName());
					jsonField.addProperty("type", field.getType());
					jsonFields.add(jsonField);
				}
				jsonClass.add("attributes", jsonFields);

				// Convert methods to JSON array
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
				String t = "";
				if (relationship.getType() == 1){
					t = "Aggregation";
				}
				if (relationship.getType() == 2){
					t = "Composition";
				}
				if (relationship.getType() == 3){
					t = "Inheritance";
				}
				if (relationship.getType() == 4){
					t = "Realization";
				}
				jsonRelationship.addProperty("type", t);
				jsonRelationships.add(jsonRelationship);
			}
			jsonDiagram.add("relationships", jsonRelationships);

			gson.toJson(jsonDiagram, writer);
			System.out.println("Diagram saved successfully to " + filePath + ".");
		} catch (IOException e) {
			System.out.println("Failed to save diagram to " + filePath + ": " + e.getMessage());
		}
		reader.addCompleter(generalCompleter);
	}

	
/**************************************************************************************************************************************/
    /**   LOAD DIAGRAM   **/
/*** @throws IOException ************************************************************************************************************************************/

	
	// Method to load a UML diagram from a JSON file
	protected static void loadDiagram() throws IOException {
		reader.removeCompleter(generalCompleter);
		System.out.print("Enter the file name to load the diagram (JSON format): ");
		String filePath = getReaderValue().trim();
		if (!filePath.contains(".json")) {
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
				// Load the position
				JsonObject jsonPosition = jsonClass.get("position").getAsJsonObject();
				int x = jsonPosition.get("x").getAsInt();
				int y = jsonPosition.get("y").getAsInt();
				Point position = new Point(x, y);
											
				// Create a Point object
				umlDiagram.setPosition(className, position);
	
				// Load attributes
				JsonArray jsonFields = jsonClass.getAsJsonArray("attributes");
				for (JsonElement fieldElement : jsonFields) {
					JsonObject jsonField = fieldElement.getAsJsonObject();
					String fieldName = jsonField.get("name").getAsString();
					String fieldType = jsonField.get("type").getAsString();
					umlDiagram.getClassByName(className).addField(fieldName, fieldType);
				}
	
				// Load methods
				JsonArray jsonMethods = jsonClass.getAsJsonArray("methods");
				for (JsonElement methodElement : jsonMethods) {
					JsonObject jsonMethod = methodElement.getAsJsonObject();
					String methodName = jsonMethod.get("name").getAsString();
					String methodType = jsonMethod.get("returnType").getAsString();
					umlDiagram.getClassByName(className).addMethod(methodName, methodType);
				}
			}
				
			// Load relationships
			JsonArray jsonRelationships = jsonDiagram.getAsJsonArray("relationships");
			for (JsonElement element : jsonRelationships) {
				JsonObject jsonRelationship = element.getAsJsonObject();
				String source = jsonRelationship.get("source").getAsString();
				String destination = jsonRelationship.get("destination").getAsString();
				String tempType = jsonRelationship.get("type").getAsString();
				int type = 0;
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
		reader.addCompleter(generalCompleter);
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
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

	    System.out.print("Enter the name of the class to list its contents: ");
	    String className = getReaderValue();
	    UMLClass umlClass = umlDiagram.getClassNameMapToName().get(className);
		reader.removeCompleter(classCompleter);

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
		reader.addCompleter(generalCompleter);
	}

	protected static void listClassContents(String className) throws IOException {
		UMLCompleter classCompleter = setClassCompleter();
		reader.addCompleter(classCompleter);
		reader.removeCompleter(generalCompleter);

	    UMLClass umlClass = umlDiagram.getClassNameMapToName().get(className);
		reader.removeCompleter(classCompleter);

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
		reader.addCompleter(generalCompleter);
	}

	// Method to list all relationships in the UML diagram
	protected static void listRelationships() {
		ArrayList<Relationship> relationships = umlDiagram.getRelationships();
		System.out.println("Relationships:");
		for (Relationship relationship : relationships) {
			System.out.println("  " + relationship.getSource() + " --> " + relationship.getDestination());
		}
	}

	/**
	 * I will edit this Friday such that it shows the graphical representation of this CLI diagram.
	 * If I fail to do that pull request by Friday evening, then at least we will get partial credit.
	 */
	/*protected static void CLIOutputAsImage() {
        // Create a StringBuilder to hold the content
        StringBuilder stringToRasterize = new StringBuilder();
        
		// Listing the classes and their fields and methods.
		ArrayList<UMLClass> classes = umlDiagram.getClasses();
        stringToRasterize.append("Classes: ");
        for (UMLClass umlClass : classes) 
		{
			stringToRasterize.append("\n" + umlClass.getName() + ":");
	        for (Field field : umlClass.getFields()) {
	            stringToRasterize.append("\n  --" + field.getName() + ": " + field.getType());
	        }
	        for (Method method : umlClass.getMethods()) {
	            stringToRasterize.append("\n  +" + method.getName() + "(");
	            // Print method parameters
	            List<Parameter> params = method.getParameters();
	            for (int i = 0; i < params.size(); i++) {
	                Parameter param = params.get(i);
	                stringToRasterize.append(param.getName() + ": " + param.getType());
	                if (i < params.size() - 1) {
	                    stringToRasterize.append(", ");
	                }
	            }
	            stringToRasterize.append("): " + method.getReturnType());
	        }
        }

		// Listing the relationships
		stringToRasterize.append("\n\nRelationships:");
		for (Relationship relationship : umlDiagram.getRelationships()) {
			stringToRasterize.append("\n  " + relationship.getSource() + " --> " + relationship.getDestination() +
				"    " + relationship.getTypeAsString(relationship.getType()));
		}

        // Generate an image from the string content
        BufferedImage image = createImageFromString(stringToRasterize.toString());
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // Save the image to a file
        File outputFile = new File(timestamp + "-CLIOutput.jpg");
        try {
            ImageIO.write(image, "jpg", outputFile);
            System.out.println("Image saved to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

	/**
	 * Creates a Buffered image from a String, which is important 
	 * for the Export as Image user story.
	 * @param content The giant string to put into the picture, ideally in
	 * 	the future being an ArrayList<String> of all cli output.
	 * @return A buffered image that we write to a JPEG file in CLIOutputAsImage().
	 */
    /*private static BufferedImage createImageFromString(String content) {
		// Set font and color
		Font font = new Font("Arial", Font.PLAIN, 16);
		int lineHeight = font.getSize(); // Get the height of a single line
	
		// Split the content into separate lines
		String[] lines = content.split("\n");
	
		// Calculate the total height required for all lines
		int totalHeight = lines.length * lineHeight;
	
		// Set the image dimensions
		int width = 480; // Arbitrary width.
		int height = totalHeight + 40; // Add some padding at the top
	
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
	
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
	
		// Draw each line
		int y = 30;
		for (String line : lines) {
			g2d.drawString(line, 20, y);
			y += lineHeight; // Move to the next line
		}
	
		g2d.dispose(); // Clean up graphics resources
	
		return image;
	}*/
	
	protected static void CLIOutputAsImage() {
		// Setting the scene dimensions
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		if (umlDiagram.getClasses().size() > 0) {
			for (UMLClass c : umlDiagram.getClasses()) {
				int x = c.position.x;
				int y = c.position.y;
				if (x < minX) minX = x;
				if (x > maxX) maxX = x;
				if (y < minY) minY = y;
				if (y > maxY) maxY = y;
			}
		}
		else
		{
			System.out.println("No content to snapshot.");
			return;
		}
	
		// Making the JPanel that will fill the scene.
		int width = maxX - minX + 100; // +100 for the 50px offset on both sides
		int height = maxY - minY + 100; // +100 for the 50px offset on both sides
		// Creating the buffered image to write the GUIView output.
		BufferedImage bImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D cg = bImg.createGraphics();
		classPanelContainer.paintAll(cg);
		// Save the image to a file
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File outputFile = new File(timestamp + "-CLIOutput.jpg");
        try {
            ImageIO.write(bImg, "jpg", outputFile);
            System.out.println("Image saved to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
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
		System.out.println("4. AddField - Add an field to a class");
		System.out.println("   Arguments: Name of the class, Name of the field, Type of the field\n");
		System.out.println("5. DeleteField - Delete an field from a class");
		System.out.println("   Arguments: Name of the class, Name of the field to delete\n");
		System.out.println("6. RenameField - Rename an field in a class");
		System.out.println("   Arguments: Name of the class, Current name of the field, New name for the field\n");
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
		System.out.println("    Arguments: Name of the first class, Name of the second class, New selection for attribute\n");
		System.out.println("13. ListClasses - List all classes\n");
		System.out.println("14. ListRelationships - List all relationships\n");
		System.out.println("15. ListClass - List contents of a specified class");
		System.out.println("    Arguments: Name of the class to list its contents\n");
		System.out.println("16. SaveAsImage - Outputs listing of classes, fields, methods, and relationships as a .jpg.");
		System.out.println("17. Save - Save diagram to JSON file");
		System.out.println("    Arguments: File path to save the diagram (JSON format)\n");
		System.out.println("18. Load - Load diagram from JSON file");
		System.out.println("    Arguments: File path to load the diagram (JSON format)\n");
		System.out.println("19. Menu - Displays Main menu\n");
		System.out.println("20. Help - Displays detailed information of program\n");
		System.out.println("21. Exit - Exits program\n");

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
				SaveAsImage -         Outputs listing of classes, fields, methods, and relationships as a .jpg.

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