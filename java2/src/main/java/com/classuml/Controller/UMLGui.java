package com.classuml.Controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.classuml.Model.*;
import com.classuml.View.guiView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;


// Assuming UMLDiagram and related classes are in the Model package

/**
 * Represents the main window of a UML diagram editor, providing a graphical user interface
 * for creating and manipulating UML diagrams. It supports actions such as adding, renaming,
 * and deleting classes, fields, methods, and relationships, as well as saving and loading diagrams.
 */
public class UMLGui extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static UMLDiagram diagram = new UMLDiagram();
	private JPanel classPanelContainer;
	private JScrollPane scrollPane;
	
	
    /**
     * Constructs the UMLGui frame and initializes the GUI components, including
     * setting up the menu bar, class panel container, and scroll pane. It also
     * maximizes the window and sets the diagram GUI linkage.
     */
	public UMLGui() {
		super("UML Diagram Editor");
		initializeGUI();
		diagram.setGui(this);
	}  

/**************************************************************************************************************************************/
    /**   GUI FRAME SET-UPS   **/
/**************************************************************************************************************************************/

    /** 
     * Initializes the graphical user interface components and layout. It sets up
     * the menu bar, class panel container for displaying classes, and a scroll pane
     * for navigation. It also calls methods to update the diagram view and populate
     * class components based on the current state of the diagram.
     */
	public void initializeGUI() {        
	    setSize(800, 1000);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());

	    // Initialize and set up the menu bar
	    setJMenuBar(createMenuBar());	   

	    classPanelContainer = new JPanel();
	    classPanelContainer.setLayout(new BoxLayout(classPanelContainer, BoxLayout.Y_AXIS));
	    scrollPane = new JScrollPane(classPanelContainer);
	    add(scrollPane, BorderLayout.CENTER);
	    
	    // Now it's safe to call updateDiagramView
	    updateDiagramView();
	    populateClassComponents();
	    
	    // Maximize the window
	    setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

/**************************************************************************************************************************************/
    /**   MENUBARS   **/
/**************************************************************************************************************************************/

	
    /**
     * Creates the menu bar with all the menus and menu items including file,
     * class, attribute, relationship, and interface management options. Each
     * menu item is associated with an action command to be handled by the action
     * listener.
     * @return the fully constructed JMenuBar for the frame.
     */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		// File Menu
		JMenu fileMenu = new JMenu("File");
		addMenuItem(fileMenu, "Save", "save");
		addMenuItem(fileMenu, "Load", "load");
		addMenuItem(fileMenu, "Help", "help");

		// Class Menu
		JMenu classMenu = new JMenu("Class");
		addMenuItem(classMenu, "Add Class", "addClass");
		addMenuItem(classMenu, "Rename Class", "renameClass");
		addMenuItem(classMenu, "Delete Class", "deleteClass");

		// Attribute Menu
		JMenu attributeMenu = new JMenu("Attribute");
		addMenuItem(attributeMenu, "Add Field", "addField");
		addMenuItem(attributeMenu, "Rename Field", "renameField");
		addMenuItem(attributeMenu, "Delete Field", "deleteField");
		
		addMenuItem(attributeMenu, "Add Method", "addMethod");		
		addMenuItem(attributeMenu, "Rename Method", "renameMethod");
		addMenuItem(attributeMenu, "Delete Method", "deleteMethod");
		
		addMenuItem(attributeMenu, "Add Parameter", "addParameter");
		addMenuItem(attributeMenu, "Rename Parameter", "renameParameter");
		addMenuItem(attributeMenu, "Delete Parameter", "deleteParameter");



		// Relationship Menu
		JMenu relationshipMenu = new JMenu("Relationship");
		addMenuItem(relationshipMenu, "Add Relationship", "addRelationship");
		addMenuItem(relationshipMenu, "Delete Relationship", "deleteRelationship");
		addMenuItem(relationshipMenu, "Change Type", "changeType");

		// Interface Menu
		JMenu interfaceMenu = new JMenu("Interface");
		addMenuItem(interfaceMenu, "List Class", "listClass");
		addMenuItem(interfaceMenu, "List Relationships", "listRelationships");
		addMenuItem(interfaceMenu, "List Classes", "listClasses");

		// Adding menus to menu bar
		menuBar.add(fileMenu);
		menuBar.add(classMenu);
		menuBar.add(attributeMenu);
		menuBar.add(relationshipMenu);
		menuBar.add(interfaceMenu);

		return menuBar;
	}

    /**
     * Adds a menu item to a specified menu with the given title and action command.
     * The menu item is configured to listen for actions which are handled by the
     * UMLGui action listener.
     * @param menu The menu to which the menu item will be added.
     * @param title The text of the menu item.
     * @param actionCommand The command that identifies the action associated with the menu item.
     */
	private void addMenuItem(JMenu menu, String title, String actionCommand) {
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.setActionCommand(actionCommand);
		menuItem.addActionListener(this);
		menu.add(menuItem);
	}

    /**
     * Updates the display of the UML diagram to reflect the current state. This
     * method can be called after any modification to the diagram to refresh the
     * visual representation shown in the GUI.
     */
	private void updateDiagramView() {
		scrollPane.setToolTipText(diagram.toString());
	}

    /**
     * Handles action events generated by menu item selections. Based on the action
     * command of the event, this method delegates to specific methods for handling
     * each possible action, such as adding or deleting classes, fields, and methods,
     * as well as saving and loading diagrams.
     * @param e The action event triggered by selecting a menu item.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Simplified switch structure for brevity
		switch (e.getActionCommand()) {
		case "addClass":
			addClass();
			break;
		case "renameClass":
			renameClass();
			break;
		case "deleteClass":
			deleteClass();
			break;
		case "addField":
			addField();
			break;
		case "renameField":
			renameField();
			break;
		case "deleteField":
			deleteField();
			break;
		case "addMethod":
			addMethod();
			break;
		case "renameMethod":
			renameMethod();
			break;
		case "deleteMethod":
			deleteMethod();
			break;
		case "addParameter":
			addParameter();
			break;
		case "renameParameter":
			renameParameter();
			break;
		case "deleteParameter":
			deleteParameter();
			break;
		case "addRelationship":
			addRelationship();
			break;
		case "deleteRelationship":
			deleteRelationship();
			break;
		case "changeType":
			changeType();
			break;
		case "listClass":
			listClass();
			break;
		case "listClasses":
			listClasses();
			break;
		case "listRelationships":
			listRelationships();
			break;
		case "save":
			saveDiagram();
			break;
		case "load":
			loadDiagram();
			break;
		case "help":
			showHelp();
			break;
		}
		updateDiagramView();
		populateClassComponents();
	}


/**************************************************************************************************************************************/
    /**   CLASSES   **/
/**************************************************************************************************************************************/

    /**
     * Adds a new class to the UML diagram.
     * Prompts the user to enter the name of the class to add.
     * Updates the diagram view and displays a confirmation message if the class is added successfully,
     * otherwise shows an error message.
     */
	private void addClass() {
		// Prompt the user for the class name using JOptionPane for graphical input
		// dialog
		String className = JOptionPane.showInputDialog(this, "Enter class name:", "Add Class",
				JOptionPane.PLAIN_MESSAGE);

		// Check if the user provided a class name and didn't cancel the dialog
		if (className != null && !className.trim().isEmpty()) {
			try {
				// Attempt to add the class to the diagram. Assuming diagram.addClass throws
				// IllegalArgumentException if class exists
				boolean added = diagram.addClass(className);
				if (added) {
					// Update the diagram view to reflect the new class
					updateDiagramView();

					// Display a confirmation message
					JOptionPane.showMessageDialog(this, "Class '" + className + "' added successfully.", "Class Added",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// Class already exists
					JOptionPane.showMessageDialog(this, "Class '" + className + "' already exists.",
							"Error Adding Class", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				// Handle any other unexpected exceptions
				JOptionPane.showMessageDialog(this, "An error occurred while adding the class: " + ex.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (className != null) {
			// Handle the case where the user entered a blank name
			JOptionPane.showMessageDialog(this, "Class name cannot be blank.", "Invalid Class Name",
					JOptionPane.WARNING_MESSAGE);
		}
		// If className is null, the user cancelled the dialog, so do nothing
	}

    /**
     * Notifies the user interface that a new class has been added to the diagram.
     * Updates the class panel container with the new class information including fields, methods, and relationships.
     * @param className The name of the class that has been added.
     */
	public void notifyClassAdded(String className) {
	    UMLClass newClass = diagram.getClassByName(className);
	    if (newClass != null) {
	        List<String> fieldDescriptions = newClass.getFields().stream()
	                .map(Field::toString)
	                .collect(Collectors.toList());

	        // Using Method's toString method directly
	        List<String> methodDescriptions = newClass.getMethods().stream()
	                .map(Method::toString)
	                .collect(Collectors.toList());

	        List<String> relationshipDescriptions = diagram.getRelationshipsForClass(className).stream()
	                .map(Relationship::toString)
	                .collect(Collectors.toList());

	        guiView classComp = new guiView(className, fieldDescriptions, methodDescriptions, relationshipDescriptions);
	        classPanelContainer.add(classComp);
	        classPanelContainer.revalidate();
	        classPanelContainer.repaint();
	    }
	}

    /**
     * Renames an existing class in the UML diagram.
     * Prompts the user for the current class name and the new class name.
     * Updates the diagram view and displays a success message if the class is renamed successfully,
     * otherwise shows an error message.
     */
	private void renameClass() {
		String[] classNames = new String[diagram.getClasses().size()];
		int index = 0;
		for(UMLClass classes: diagram.getClasses()){
			classNames[index] = classes.getName();
			index++;
		}

		
		Object oldName = JOptionPane.showInputDialog(this, "Enter the current class name:", "Rename Class",
				JOptionPane.PLAIN_MESSAGE, null, classNames, classNames[0]);
		if (oldName != null) {
			String newName = JOptionPane.showInputDialog(this, "Enter the new class name: ",
					"Rename Class", JOptionPane.PLAIN_MESSAGE);
				try {
					boolean renamed = diagram.renameClass(oldName.getClass().getName(), newName);
					if (renamed) {
						updateDiagramView();
						JOptionPane.showMessageDialog(this, "Class renamed successfully.", "Class Renamed",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this,
								"Failed to rename class. Class may not exist or new name may already be in use.",
								"Error Renaming Class", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this,
							"An error occurred while renaming the class: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
		}
	}

    /**
     * Deletes an existing class from the UML diagram.
     * Prompts the user for the name of the class to delete and confirms the action.
     * Updates the diagram view and displays a success message if the class is deleted successfully,
     * otherwise shows an error message.
     */
	private void deleteClass() {
		String className = JOptionPane.showInputDialog(this, "Enter the class name to delete:", "Delete Class",
				JOptionPane.PLAIN_MESSAGE);
		if (className != null && !className.trim().isEmpty()) {
			int confirmation = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete '" + className + "'?", "Delete Class", JOptionPane.YES_NO_OPTION);
			if (confirmation == JOptionPane.YES_OPTION) {
				boolean deleted = diagram.deleteClass(className);
				if (deleted) {
					updateDiagramView();
					JOptionPane.showMessageDialog(this, "Class deleted successfully.", "Class Deleted",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Failed to delete class. Class may not exist.",
							"Error Deleting Class", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

/**************************************************************************************************************************************/
    /**   FIELDS   **/
/**************************************************************************************************************************************/

    /**
     * Adds a new field to a specified class.
     * Prompts the user for the class name, field name, and field type.
     * Updates the diagram view and displays a success message if the field is added successfully,
     * otherwise shows an error message.
     */
	private void addField() {
		String className = JOptionPane.showInputDialog(this, "Enter the class name to add an field to:", "Add Field",
				JOptionPane.PLAIN_MESSAGE);
		String fieldName = JOptionPane.showInputDialog(this, "Enter the field name:", "Add Field",
				JOptionPane.PLAIN_MESSAGE);
		String fieldType = JOptionPane.showInputDialog(this, "Enter the field type:", "Add Field",
				JOptionPane.PLAIN_MESSAGE);
		if (className != null && !className.trim().isEmpty() && fieldName != null && !fieldName.trim().isEmpty()
				&& fieldType != null && !fieldType.trim().isEmpty()) {
			try {
				boolean added = diagram.addField(className, fieldName, fieldType);
				if (added) {
					updateDiagramView();
					JOptionPane.showMessageDialog(this, "Field added successfully.", "Field Added",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Failed to add field.", "Error Adding Field",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

    /**
     * Renames an existing field within a specified class.
     * Prompts the user for the class name, existing field name, and new field name.
     * Updates the diagram view and displays a success message if the field is renamed successfully,
     * otherwise shows an error message.
     */
	private void renameField() {
		String className = JOptionPane.showInputDialog(this, "Enter the class name containing the field:",
				"Rename Field", JOptionPane.PLAIN_MESSAGE);
		String oldFieldName = JOptionPane.showInputDialog(this, "Enter the current field name:", "Rename Field",
				JOptionPane.PLAIN_MESSAGE);
		String newFieldName = JOptionPane.showInputDialog(this, "Enter the new field name:", "Rename Field",
				JOptionPane.PLAIN_MESSAGE);
		if (className != null && !className.trim().isEmpty() && oldFieldName != null && !oldFieldName.trim().isEmpty()
				&& newFieldName != null && !newFieldName.trim().isEmpty()) {
			try {
				boolean renamed = diagram.renameField(className, oldFieldName, newFieldName);
				if (renamed) {
					updateDiagramView();
					JOptionPane.showMessageDialog(this,
							"Field '" + oldFieldName + "' renamed to '" + newFieldName + "' successfully.",
							"Field Renamed", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Failed to rename field.", "Error Renaming Field",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

    /**
     * Deletes an existing field from a specified class.
     * Prompts the user for the class name and the field name to delete.
     * Updates the diagram view and displays a success message if the field is deleted successfully,
     * otherwise shows an error message.
     */
	private void deleteField() {
		String className = JOptionPane.showInputDialog(this, "Enter the class name from which to delete an field:",
				"Delete Field", JOptionPane.PLAIN_MESSAGE);
		String fieldName = JOptionPane.showInputDialog(this, "Enter the field name to delete:", "Delete Field",
				JOptionPane.PLAIN_MESSAGE);
		if (className != null && !className.trim().isEmpty() && fieldName != null && !fieldName.trim().isEmpty()) {
			try {
				boolean deleted = diagram.deleteField(className, fieldName);
				if (deleted) {
					updateDiagramView();
					JOptionPane.showMessageDialog(this, "Attribute deleted successfully.", "Attribute Deleted",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Failed to delete attribute.", "Error Deleting Attribute",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

/**************************************************************************************************************************************/
    /**   METHODS   **/
/**************************************************************************************************************************************/

    /**
     * Adds a new method to a specified class.
     * Prompts the user for the class name, method name, and return type.
     * Updates the diagram view and displays a success message if the method is added successfully,
     * otherwise shows an error message.
     */
	private void addMethod() {
		String className = JOptionPane.showInputDialog(this, "Enter the class name to add a method to:", "Add Method",
				JOptionPane.PLAIN_MESSAGE);
		if (className != null && !className.trim().isEmpty()) {
			String methodName = JOptionPane.showInputDialog(this, "Enter the method name:", "Add Method",
					JOptionPane.PLAIN_MESSAGE);
			if (methodName != null && !methodName.trim().isEmpty()) {
				String returnType = JOptionPane.showInputDialog(this, "Enter the return type of the method:",
						"Add Method", JOptionPane.PLAIN_MESSAGE);
				if (returnType != null && !returnType.trim().isEmpty()) {
					try {
						boolean added = diagram.addMethod(className, methodName, returnType);
						if (added) {
							updateDiagramView();
							JOptionPane.showMessageDialog(this, "Method added successfully.", "Method Added",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(this, "Failed to add method to class. Class may not exist.",
									"Error Adding Method", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this,
								"An error occurred while adding the method: " + ex.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

    /**
     * Renames an existing method within a specified class.
     * Prompts the user for the class name, existing method name, and new method name.
     * Updates the diagram view and displays a success message if the method is renamed successfully,
     * otherwise shows an error message.
     */
	private void renameMethod() {
		String className = JOptionPane.showInputDialog(this, "Enter the class name containing the method:",
				"Rename Method", JOptionPane.PLAIN_MESSAGE);
		String oldMethodName = JOptionPane.showInputDialog(this, "Enter the current method name:", "Rename Method",
				JOptionPane.PLAIN_MESSAGE);
		String newMethodName = JOptionPane.showInputDialog(this, "Enter the new method name:", "Rename Method",
				JOptionPane.PLAIN_MESSAGE);
		if (className != null && !className.trim().isEmpty() && oldMethodName != null && !oldMethodName.trim().isEmpty()
				&& newMethodName != null && !newMethodName.trim().isEmpty()) {
			try {
				boolean renamed = diagram.renameMethod(className, oldMethodName, newMethodName);
				if (renamed) {
					updateDiagramView();
					JOptionPane.showMessageDialog(this, "Method renamed successfully.", "Method Renamed",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Failed to rename method.", "Error Renaming Method",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

    /**
     * Deletes an existing method from a specified class.
     * Prompts the user for the class name and the method name to delete.
     * Updates the diagram view and displays a success message if the method is deleted successfully,
     * otherwise shows an error message.
     */
	private void deleteMethod() {
		String className = JOptionPane.showInputDialog(this, "Enter the class name from which to delete a method:",
				"Delete Method", JOptionPane.PLAIN_MESSAGE);
		String methodName = JOptionPane.showInputDialog(this, "Enter the method name to delete:", "Delete Method",
				JOptionPane.PLAIN_MESSAGE);
		if (className != null && !className.trim().isEmpty() && methodName != null && !methodName.trim().isEmpty()) {
			try {
				boolean deleted = diagram.deleteMethod(className, methodName);
				if (deleted) {
					updateDiagramView();
					JOptionPane.showMessageDialog(this, "Method deleted successfully.", "Method Deleted",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Failed to delete method.", "Error Deleting Method",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

/**************************************************************************************************************************************/
    /**   PARAMETERS   **/
/**************************************************************************************************************************************/

    /**
     * Adds a new parameter to a specified method within a specified class.
     * Prompts the user for the class name, method name, parameter name, and parameter type.
     * Displays a success message if the parameter is added successfully, otherwise shows an error message.
     */
	private void addParameter() {
	    String className = JOptionPane.showInputDialog(this, "Enter the class name of the method:", "Add Parameter", JOptionPane.PLAIN_MESSAGE);
	    String methodName = JOptionPane.showInputDialog(this, "Enter the method name to add a parameter to:", "Add Parameter", JOptionPane.PLAIN_MESSAGE);
	    String parameterName = JOptionPane.showInputDialog(this, "Enter the parameter name:", "Add Parameter", JOptionPane.PLAIN_MESSAGE);
	    String parameterType = JOptionPane.showInputDialog(this, "Enter the parameter type:", "Add Parameter", JOptionPane.PLAIN_MESSAGE);

	    if (className != null && methodName != null && parameterName != null && parameterType != null &&
	        !className.isEmpty() && !methodName.isEmpty() && !parameterName.isEmpty() && !parameterType.isEmpty()) {
	        try {
	            boolean success = diagram.addParameter(className, methodName, parameterName, parameterType);
	            if (success) {
	                JOptionPane.showMessageDialog(this, "Parameter added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	                updateDiagramView();
	            } else {
	                JOptionPane.showMessageDialog(this, "Failed to add parameter. Check if class and method exist, and parameter doesn't already exist.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

    /**
     * Renames an existing parameter within a specified method of a specified class.
     * Prompts the user for the class name, method name, existing parameter name, and the new parameter name.
     * Displays a success message if the parameter is renamed successfully, otherwise shows an error message.
     */
	private void renameParameter() {
	    String className = JOptionPane.showInputDialog(this, "Enter the class name:", "Rename Parameter", JOptionPane.PLAIN_MESSAGE);
	    String methodName = JOptionPane.showInputDialog(this, "Enter the method name:", "Rename Parameter", JOptionPane.PLAIN_MESSAGE);
	    String oldParameterName = JOptionPane.showInputDialog(this, "Enter the current parameter name:", "Rename Parameter", JOptionPane.PLAIN_MESSAGE);
	    String newParameterName = JOptionPane.showInputDialog(this, "Enter the new parameter name:", "Rename Parameter", JOptionPane.PLAIN_MESSAGE);

	    if (className != null && oldParameterName != null && newParameterName != null &&
	        !className.isEmpty() && !oldParameterName.isEmpty() && !newParameterName.isEmpty()) {
	        boolean success = diagram.renameParameter(className, methodName, oldParameterName, newParameterName);
	        if (success) {
	            JOptionPane.showMessageDialog(this, "Parameter renamed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	            updateDiagramView();
	        } else {
	            JOptionPane.showMessageDialog(this, "Failed to rename parameter. Ensure the old parameter exists and the new name is unique within its method.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	

    /**
     * Deletes an existing parameter from a specified method within a specified class.
     * Prompts the user for the class name, method name, and the parameter name to delete.
     * Displays a success message if the parameter is deleted successfully, otherwise shows an error message.
     */
	private void deleteParameter() {
	    String className = JOptionPane.showInputDialog(this, "Enter the class name of the method:", "Delete Parameter", JOptionPane.PLAIN_MESSAGE);
	    String methodName = JOptionPane.showInputDialog(this, "Enter the method name of the parameter:", "Delete Parameter", JOptionPane.PLAIN_MESSAGE);
	    String parameterName = JOptionPane.showInputDialog(this, "Enter the parameter name to delete:", "Delete Parameter", JOptionPane.PLAIN_MESSAGE);

	    if (className != null && methodName != null && parameterName != null &&
	        !className.isEmpty() && !methodName.isEmpty() && !parameterName.isEmpty()) {
	        boolean success = diagram.deleteParameter(className, methodName, parameterName);
	        if (success) {
	            JOptionPane.showMessageDialog(this, "Parameter deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	            updateDiagramView();
	        } else {
	            JOptionPane.showMessageDialog(this, "Failed to delete parameter. Ensure the method and parameter exist.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

	
/**************************************************************************************************************************************/
    /**   RELATIONSHIPS   **/
/**************************************************************************************************************************************/

    /**
     * Adds a new relationship between two specified classes.
     * Prompts the user for the source class name, destination class name, and relationship type.
     * Updates the diagram view and displays a success message if the relationship is added successfully,
     * otherwise shows an error message.
     */
	private void addRelationship() {
		String sourceClass = JOptionPane.showInputDialog(this, "Enter the source class name:", "Add Relationship",
				JOptionPane.PLAIN_MESSAGE);
		String destinationClass = JOptionPane.showInputDialog(this, "Enter the destination class name:",
				"Add Relationship", JOptionPane.PLAIN_MESSAGE);
		String typeAsString = JOptionPane.showInputDialog(this,
				"Enter the new relationship type (as a number):\n" 
						+ "1: Aggregation\n"
						+ "2: Composition\n"
						+ "3: Inheritance\n"
						+ "4: Realization\n",
						"Change Relationship Type", JOptionPane.PLAIN_MESSAGE);

		if (sourceClass != null && !sourceClass.isEmpty() && destinationClass != null && !destinationClass.isEmpty()
				&& typeAsString != null && !typeAsString.isEmpty()) {
			try {
				int type = Integer.parseInt(typeAsString);
				if (type >= 1 && type <= 5) {
					boolean added = diagram.addRelationship(sourceClass, destinationClass, type);
					if (added) {
						updateDiagramView();
						JOptionPane.showMessageDialog(this, "Relationship added successfully.", "Relationship Added",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Failed to add relationship. Ensure both classes exist.",
								"Error Adding Relationship", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this,
							"Invalid relationship type. Please enter a number between 1 and 5.", "Invalid Type",
							JOptionPane.WARNING_MESSAGE);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "The relationship type must be a valid number.", "Invalid Type",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Source class, destination class, and relationship type are required.",
					"Invalid Input", JOptionPane.WARNING_MESSAGE);
		}
	}

    /**
     * Deletes an existing relationship between two specified classes.
     * Prompts the user for the source and destination class names of the relationship to delete.
     * Updates the diagram view and displays a success message if the relationship is deleted successfully,
     * otherwise shows an error message.
     */
	private void deleteRelationship() {
		// Prompt the user for the source class name of the relationship to delete
		String sourceClass = JOptionPane.showInputDialog(this,
				"Enter the source class name of the relationship to delete:", "Delete Relationship",
				JOptionPane.PLAIN_MESSAGE);

		// Prompt for the destination class name
		String destinationClass = JOptionPane.showInputDialog(this,
				"Enter the destination class name of the relationship to delete:", "Delete Relationship",
				JOptionPane.PLAIN_MESSAGE);

		if (sourceClass != null && !sourceClass.trim().isEmpty() && destinationClass != null
				&& !destinationClass.trim().isEmpty()) {
			// Confirm the deletion
			int confirm = JOptionPane
					.showConfirmDialog(this,
							"Are you sure you want to delete the relationship from '" + sourceClass + "' to '"
									+ destinationClass + "'?",
							"Confirm Delete Relationship", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				try {
					// Attempt to delete the relationship
					boolean deleted = diagram.deleteRelationship(sourceClass, destinationClass); // Assuming such a
																									// method exists in
																									// UMLDiagram
					if (deleted) {
						// Update the diagram view to reflect the change
						updateDiagramView();
						// Inform the user of success
						JOptionPane.showMessageDialog(this, "Relationship deleted successfully.",
								"Relationship Deleted", JOptionPane.INFORMATION_MESSAGE);
					} else {
						// Inform the user if the relationship couldn't be deleted (e.g., because it
						// doesn't exist)
						JOptionPane.showMessageDialog(this, "Failed to delete relationship. It may not exist.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					// Handle any other exceptions and inform the user
					JOptionPane.showMessageDialog(this,
							"An error occurred while deleting the relationship: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

    /**
     * Changes the type of an existing relationship between two specified classes.
     * Prompts the user for the source and destination class names and the new relationship type.
     * Updates the diagram view and displays a success message if the relationship type is changed successfully,
     * otherwise shows an error message.
     */
	private void changeType() {
		String sourceClass = JOptionPane.showInputDialog(this, "Enter the source class name of the relationship:",
				"Change Relationship Type", JOptionPane.PLAIN_MESSAGE);
		String destinationClass = JOptionPane.showInputDialog(this,
				"Enter the destination class name of the relationship:", "Change Relationship Type",
				JOptionPane.PLAIN_MESSAGE);

		if (sourceClass == null || destinationClass == null || sourceClass.isEmpty() || destinationClass.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Source or destination class cannot be empty.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Assuming a predefined set of relationship types
		String typeAsString = JOptionPane.showInputDialog(this,
				"Enter the new relationship type (as a number):\n" 
						+ "1: Aggregation\n"
						+ "2: Composition\n"
						+ "3: Inheritance\n"
						+ "4: Realization\n",
						"Change Relationship Type", JOptionPane.PLAIN_MESSAGE);

		try {
			int newType = Integer.parseInt(typeAsString);
			if (newType >= 1 && newType <= 5) { // Validate the input range
				boolean typeChanged = diagram.changeRelType(sourceClass, destinationClass, newType);
				if (typeChanged) {
					updateDiagramView();
					JOptionPane.showMessageDialog(this, "Relationship type changed successfully.", "Type Changed",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Failed to change relationship type.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Invalid relationship type selected.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "The relationship type must be a valid number between 1 and 5.",
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	
/**************************************************************************************************************************************/
    /**   INTERFACES   **/
/**************************************************************************************************************************************/

	
    /**
     * Lists the contents of a specified class.
     * Prompts the user for the class name and displays its contents including fields, methods, and relationships.
     * Shows an error message if the class is not found.
     */
	private void listClass() {
	    String className = JOptionPane.showInputDialog(this, "Enter the class name to list its contents:", "List Class", JOptionPane.PLAIN_MESSAGE);
	    if (className != null && !className.trim().isEmpty()) {
	        UMLClass umlClass = diagram.getClassByName(className);
	        if (umlClass != null) {
	            JOptionPane.showMessageDialog(this, umlClass.toString(), "Class Information", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(this, "Class '" + className + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

    /**
     * Lists all classes in the UML diagram.
     * Displays the names of all classes currently in the diagram.
     */
	private void listClasses() {
	    // Assuming diagram.getClasses() method exists and returns a list of UMLClass objects
	    ArrayList<UMLClass> classes = diagram.getClasses();
	    StringBuilder classList = new StringBuilder("Classes:\n");
	    for (UMLClass umlClass : classes) {
	        classList.append(umlClass.getName()).append("\n");
	    }
	    JOptionPane.showMessageDialog(this, classList.toString(), "List of Classes", JOptionPane.INFORMATION_MESSAGE);
	}

    /**
     * Lists all relationships in the UML diagram.
     * Displays information about all relationships currently in the diagram.
     */
	private void listRelationships() {
	    // Assuming diagram.getRelationships() method exists and returns a list of Relationship objects
	    ArrayList<Relationship> relationships = diagram.getRelationships();
	    StringBuilder relationshipList = new StringBuilder("Relationships:\n");
	    for (Relationship relationship : relationships) {
	        relationshipList.append(relationship.toString()).append("\n");
	    }
	    JOptionPane.showMessageDialog(this, relationshipList.toString(), "List of Relationships", JOptionPane.INFORMATION_MESSAGE);
	}

	
/**************************************************************************************************************************************/
    /**   SAVE DIAGRAM   **/
/**************************************************************************************************************************************/

    /**
     * Saves the current state of the UML diagram to a JSON file. This method prompts the user to choose a file
     * location and name through a file chooser dialog. It then serializes the UML diagram data into JSON format
     * and writes it to the selected file. The JSON structure includes details for classes, their fields, methods,
     * parameters, and relationships among classes.
     */
	private static void saveDiagram() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files", "json");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(filePath)) {
                JsonObject jsonDiagram = new JsonObject();

                // Convert classes to JSON array
                JsonArray jsonClasses = new JsonArray();
                for (UMLClass umlClass : diagram.getClasses()) {
                    JsonObject jsonClass = new JsonObject();
                    jsonClass.addProperty("name", umlClass.getName());

                    // Convert fields to JSON array
                    JsonArray jsonFields = new JsonArray();
                    for (Field attribute : umlClass.getFields()) {
                        JsonObject jsonField = new JsonObject();
                        jsonField.addProperty("name", attribute.getName());
                        jsonField.addProperty("type", attribute.getType());
                        jsonFields.add(jsonField);
                    }
                    jsonClass.add("fields", jsonFields);

                    // Convert methods to JSON array
                    JsonArray jsonMethods = new JsonArray();
                    for (Method method : umlClass.getMethods()) {
                        JsonObject jsonMethod = new JsonObject();
                        jsonMethod.addProperty("name", method.getName());
                        jsonMethod.addProperty("return_type", method.getType());

                        // Convert parameters to JSON array
                        JsonArray jsonParams = new JsonArray();
                        for (Parameter param : method.getParameters()) {
                            JsonObject jsonParam = new JsonObject();
                            jsonParam.addProperty("name", param.getName());
                            jsonParam.addProperty("type", param.getType());
                            jsonParams.add(jsonParam);
                        }
                        jsonMethod.add("params", jsonParams);

                        jsonMethods.add(jsonMethod);
                    }
                    jsonClass.add("methods", jsonMethods);

                    jsonClasses.add(jsonClass);
                }
                jsonDiagram.add("classes", jsonClasses);

                // Convert relationships to JSON array
                JsonArray jsonRelationships = new JsonArray();
                for (Relationship relationship : diagram.getRelationships()) {
                    JsonObject jsonRelationship = new JsonObject();
                    jsonRelationship.addProperty("source", relationship.getSource());
                    jsonRelationship.addProperty("destination", relationship.getDestination());
                    jsonRelationship.addProperty("type", relationship.getType());
                    jsonRelationships.add(jsonRelationship);
                }
                jsonDiagram.add("relationships", jsonRelationships);

                gson.toJson(jsonDiagram, writer);
            } catch (IOException e) {
            }
        }
    }

	
/**************************************************************************************************************************************/
    /**   LOAD DIAGRAM   **/
/**************************************************************************************************************************************/

	
    /**
     * Loads a UML diagram from a JSON file. This method prompts the user to select a file through a file chooser
     * dialog. It then reads the JSON data from the file, deserializes it into the UML diagram structure, and updates
     * the current diagram state. This includes reconstructing the classes, fields, methods, parameters, and
     * relationships as defined in the JSON file.
     */
	private static void loadDiagram() {
	    JFileChooser fileChooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files", "json");
	    fileChooser.setFileFilter(filter);
	    int result = fileChooser.showOpenDialog(null);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File file = fileChooser.getSelectedFile();
	        String filePath = file.getAbsolutePath();
	        if (!file.canRead()) {
	            JOptionPane.showMessageDialog(null, "Cannot read the selected file.", "Load Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        Gson gson = new Gson();
	        try (FileReader reader = new FileReader(filePath)) {
	            JsonObject jsonDiagram = gson.fromJson(reader, JsonObject.class);
	            diagram.clear(); // Clear existing diagram first

	            // Load classes
	            if (jsonDiagram.has("classes")) {
	                JsonArray jsonClasses = jsonDiagram.getAsJsonArray("classes");
	                for (JsonElement classElement : jsonClasses) {
	                    JsonObject jsonClass = classElement.getAsJsonObject();
	                    if (jsonClass.has("name")) {
	                        String className = jsonClass.get("name").getAsString();
	                        diagram.addClass(className);

	                        // Load fields
	                        if (jsonClass.has("fields")) {
	                            JsonArray jsonFields = jsonClass.getAsJsonArray("fields");
	                            for (JsonElement fieldElement : jsonFields) {
	                                JsonObject jsonField = fieldElement.getAsJsonObject();
	                                String fieldName = jsonField.get("name").getAsString();
	                                String fieldType = jsonField.get("type").getAsString();
	                                diagram.addField(className, fieldName, fieldType);
	                            }
	                        }

	                        // Load methods
	                        if (jsonClass.has("methods")) {
	                            JsonArray jsonMethods = jsonClass.getAsJsonArray("methods");
	                            for (JsonElement methodElement : jsonMethods) {
	                                JsonObject jsonMethod = methodElement.getAsJsonObject();
	                                String methodName = jsonMethod.get("name").getAsString();
	                                String returnType = jsonMethod.get("return_type").getAsString();
	                                diagram.addMethod(className, methodName, returnType);

	                                // Load parameters
	                                if (jsonMethod.has("params")) {
	                                    JsonArray jsonParams = jsonMethod.getAsJsonArray("params");
	                                    for (JsonElement paramElement : jsonParams) {
	                                        JsonObject jsonParam = paramElement.getAsJsonObject();
	                                        String paramName = jsonParam.get("name").getAsString();
	                                        String paramType = jsonParam.get("type").getAsString();
	                                        diagram.addParameter(className, methodName, paramName, paramType);
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
	            }

	            // Load relationships
	            if (jsonDiagram.has("relationships")) {
	                JsonArray jsonRelationships = jsonDiagram.getAsJsonArray("relationships");
	                for (JsonElement relationElement : jsonRelationships) {
	                    JsonObject jsonRelationship = relationElement.getAsJsonObject();
	                    String source = jsonRelationship.get("source").getAsString();
	                    String destination = jsonRelationship.get("destination").getAsString();
	                    int type = jsonRelationship.get("type").getAsInt();
	                    diagram.addRelationship(source, destination, type);
	                }
	            }

	        } catch (FileNotFoundException e) {
	            JOptionPane.showMessageDialog(null, "File not found: " + filePath, "Load Error", JOptionPane.ERROR_MESSAGE);
	            e.printStackTrace(); // Log stack trace for debugging
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "An error occurred while reading the file.", "Load Error", JOptionPane.ERROR_MESSAGE);
	            e.printStackTrace(); // Log stack trace for debugging
	        } catch (JsonSyntaxException e) {
	            JOptionPane.showMessageDialog(null, "An error occurred while parsing the JSON data. Please check the file format.", "Load Error", JOptionPane.ERROR_MESSAGE);
	            e.printStackTrace(); // Specifically catch JSON parsing errors
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "An unexpected error occurred.", "Load Error", JOptionPane.ERROR_MESSAGE);
	            e.printStackTrace(); // Catch-all for any other exceptions
	        }

	    }
	}

	
/**************************************************************************************************************************************/
    /**   HELP METHOD   **/
/**************************************************************************************************************************************/

    /**
     * Displays help information related to using the UML diagram editor. This method retrieves help information
     * from the UMLDiagram instance and displays it in a dialog. The help information includes instructions on how
     * to use the editor, add or modify diagram components, and save or load diagrams.
     */
	private void showHelp() {
        // Use the helper method from UMLDiagram to get help information
        String helpInformation = diagram.helperMethodForHelp();
        JOptionPane.showMessageDialog(this, helpInformation, "Help", JOptionPane.INFORMATION_MESSAGE);
    }
	
    /**
     * Populates the graphical user interface with components representing the classes and their relationships
     * currently in the UML diagram. This method clears any existing components and creates new ones based on the
     * current state of the UML diagram. Each class is represented with its name, fields, methods, and relationships
     * to other classes.
     */
	private void populateClassComponents() {
	    ArrayList<UMLClass> classes = diagram.getClasses();
	    ArrayList<Relationship> relationships = diagram.getRelationships();
	    classPanelContainer.removeAll(); // Clear existing components.
	    
	    for (UMLClass umlClass : classes) {
	        List<String> fieldDescriptions = umlClass.getFields().stream()
	            .map(Field::toString)
	            .collect(Collectors.toList());

	        List<String> relationshipDescriptions = relationships.stream()
	            .filter(r -> r.getSource().equals(umlClass.getName()) || r.getDestination().equals(umlClass.getName()))
	            .map(Relationship::toString)
	            .collect(Collectors.toList());

	        // Use the Method's toString method which should now include parameters.
	        List<String> methodDescriptions = umlClass.getMethods().stream()
	            .map(Method::toString)
	            .collect(Collectors.toList());

	        guiView classComp = new guiView(umlClass.getName(), fieldDescriptions, methodDescriptions, relationshipDescriptions);
	        classPanelContainer.add(classComp);
	    }

	    classPanelContainer.revalidate();
	    classPanelContainer.repaint();
	}

	
/**************************************************************************************************************************************/
    /**   MAIN METHOD   **/
/**************************************************************************************************************************************/

    /**
     * The main entry point for the UML diagram editor application. This method sets up the user interface and
     * makes the main window visible to the user. It is responsible for initializing the application and starting
     * the event dispatch thread for Swing components.
     * @param args Command line arguments passed to the program (not used).
     */
	public static void main(String[] args) {
		boolean cliMode = false;
		for (String arg : args) {
            if (arg.equals("cli")) {
                cliMode = true;
                break;
            }
        }
		if (cliMode){
			UMLCli.launch();
		}
		SwingUtilities.invokeLater(() -> new UMLGui().setVisible(true));
	}

}
