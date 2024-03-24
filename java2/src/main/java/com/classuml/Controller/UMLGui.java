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
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
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
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		JTextField newName = new JTextField();
		
		JPanel cName = new JPanel();
		cName.setLayout(new BoxLayout(cName, BoxLayout.Y_AXIS));

		cName.add(new JLabel("Select a class: "));
		cName.add(namesBox);
		cName.add(new JLabel("Enter the new class name: "));
		cName.add(newName);
		
		int entered = -1;
		if(classNames.length > 0){
			entered = JOptionPane.showConfirmDialog(this, cName, "Rename Class",
				JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this,"There are no classes to select, please load or add classes");
		}
		if (namesBox.getSelectedItem() != null && newName != null && entered == 0) {
				try {
					boolean renamed = diagram.renameClass(namesBox.getSelectedItem().toString(), newName.getText());
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
		String[] classNames = getClassNames();

		Object className = JOptionPane.showInputDialog(this, "Please select the class to delete: ", "Delete Class",
				JOptionPane.PLAIN_MESSAGE,null, classNames, classNames[0]);
		if (className != null) {
			int confirmation = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete '" + (String)className + "'?", "Delete Class", JOptionPane.YES_NO_OPTION);
			if (confirmation == JOptionPane.YES_OPTION) {
				boolean deleted = diagram.deleteClass((String)className);
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

	private String[] getClassNames(){
		String[] classNames = new String[diagram.getClasses().size()];
		int index = 0;
		for(UMLClass classes: diagram.getClasses()){
			classNames[index] = classes.getName();
			index++;
		}
		return classNames;
	}

	private String[] returnTypes(){
		return new String[] {"null", "int", "double", "boolean", "string", "object"};
	}

	private String[] attributeTypes(){
		return new String[] {"int", "double", "boolean", "string"};
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
		String[] classNames = getClassNames();
		JComboBox<String>namesBox = new JComboBox<String>(classNames);
		JTextField fieldName = new JTextField();
		JComboBox<String> fieldType = new JComboBox<String>(attributeTypes());

		JPanel oPanel = new JPanel();
		oPanel.setLayout(new BoxLayout(oPanel, BoxLayout.Y_AXIS));

		oPanel.add(new JLabel("Select a class: "));
		oPanel.add(namesBox);
		oPanel.add(new JLabel("Enter the field name: "));
		oPanel.add(fieldName);
		oPanel.add(new JLabel("Select the field type: "));
		oPanel.add(fieldType);

		int entered = -1;

		if(classNames.length > 0){
			entered = JOptionPane.showConfirmDialog(this, oPanel, "Add Field",
				JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "there are no classes to add fields to");
		}

		if (namesBox.getSelectedItem() != null && fieldName != null && fieldType != null && entered == 0) {
			try {
				boolean added = diagram.addField(namesBox.getSelectedItem().toString(), fieldName.getText(), fieldType.getSelectedItem().toString());
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
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames); 
		JTextField newName = new JTextField();
		
		JPanel rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));

		rPanel.add(new JLabel("Select the class: "));
		rPanel.add(namesBox);
		
		int entered = -1;
		if(classNames.length == 0){
			JOptionPane.showMessageDialog(this, "There are no classes");
		}else{
			entered = JOptionPane.showConfirmDialog(this, rPanel,
				"Rename Field", JOptionPane.OK_CANCEL_OPTION);
		}
		
		rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));

		String[] fieldNames = getClassFields(namesBox.getSelectedItem().toString());
		JComboBox<String> fieldsBox = new JComboBox<String>(fieldNames);
		rPanel.add(new JLabel("Select the field: "));
		rPanel.add(fieldsBox);
		rPanel.add(new JLabel("Enter the new field name: "));
		rPanel.add(newName);
		
		entered = -1;
		if(classNames.length == 0){
			JOptionPane.showMessageDialog(this, "There are no fields in this class");
		}else{
			entered = JOptionPane.showConfirmDialog(this, rPanel,
				"Rename Field", JOptionPane.OK_CANCEL_OPTION);
		}
		
		if (namesBox.getSelectedItem() != null && fieldsBox.getSelectedItem() != null && newName != null && entered == 0) {
			try {
				boolean renamed = diagram.renameField(namesBox.getSelectedItem().toString(), fieldsBox.getSelectedItem().toString(), newName.getText());
				if (renamed) {
					updateDiagramView();
					JOptionPane.showMessageDialog(this,
							"Field '" + fieldsBox.getSelectedItem().toString() + "' renamed to '" + newName.getText(),
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
		
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames); 
		
		JPanel dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		
		dPanel.add(new JLabel("Select the Class: "));
		dPanel.add(namesBox);
		
		int entered = -1;
		if(classNames.length == 0){
			JOptionPane.showMessageDialog(this, "there are no classes");
		}else{
			entered = JOptionPane.showConfirmDialog(this, dPanel,
				"Delete Field", JOptionPane.OK_CANCEL_OPTION);
		}
		
		dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		
		String[] fieldNames = getClassFields(namesBox.getSelectedItem().toString());
		JComboBox<String> fieldsBox = new JComboBox<String>(fieldNames);
		dPanel.add(new JLabel("Select the field to delete: "));
		dPanel.add(fieldsBox);
		
		entered = -1;
		if(classNames.length == 0){
			JOptionPane.showMessageDialog(this, "there are no fields in this class");
		}else{
			entered = JOptionPane.showConfirmDialog(this, dPanel,
				"Delete Field", JOptionPane.OK_CANCEL_OPTION);
		}


		if (namesBox.getSelectedItem() != null && fieldsBox.getSelectedItem() != null && entered == 0) {
			try {
				boolean deleted = diagram.deleteField(namesBox.getSelectedItem().toString(), fieldsBox.getSelectedItem().toString());
				if (deleted) {
					updateDiagramView();
					JOptionPane.showMessageDialog(this, "Field deleted successfully.", "Field Deleted",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Failed to delete field.", "Error Deleting Field",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private String[] getClassFields(String className){
		String[] classFields = new String[diagram.getClassByName(className).getFields().size()];
		int index = 0;
		for(Field fields: diagram.getClassByName(className).getFields()){
			classFields[index] = fields.getName();
			index++;
		}
		return classFields;
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
		String [] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		JTextField methName = new JTextField();
		JComboBox<String> methType = new JComboBox<String>(returnTypes());

		JPanel mPanel = new JPanel();
		mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
		
		mPanel.add(new JLabel("Select a class: "));
		mPanel.add(namesBox);
		mPanel.add(new JLabel("Enter the name: "));
		mPanel.add(methName);
		mPanel.add(new JLabel("Select the return type: "));
		mPanel.add(methType);

		int entered = -1;
		if(classNames.length > 0){
			entered = JOptionPane.showConfirmDialog(this, mPanel, "Add Method", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no classes");
		}

		if (namesBox.getSelectedItem() != null && methName != null && methType != null && entered == 0) {
			if (methName != null && methType != null) {
					try {
						boolean added = diagram.addMethod(namesBox.getSelectedItem().toString(), methName.getText(), methType.getSelectedItem().toString());
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

    /**
     * Renames an existing method within a specified class.
     * Prompts the user for the class name, existing method name, and new method name.
     * Updates the diagram view and displays a success message if the method is renamed successfully,
     * otherwise shows an error message.
     */
	private void renameMethod() {
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		JTextField newName = new JTextField();

		JPanel rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		
		rPanel.add(new JLabel("Select the class: "));
		rPanel.add(namesBox);

		

		if(classNames.length > 0){
			JOptionPane.showConfirmDialog(this, rPanel, "Rename Method", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no classes");
		}

		rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		
		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);
		rPanel.add(new JLabel("Select the method: "));
		rPanel.add(methBox);
		rPanel.add(new JLabel("Enter the new name: "));
		rPanel.add(newName);
		
		int entered = -1;
		if(methNames.length > 0){
			entered = JOptionPane.showConfirmDialog(this, rPanel, "Rename Method", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no methods in this class");
		}


		if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null
				&& newName != null && entered == 0) {
			try {
				boolean renamed = diagram.renameMethod(namesBox.getSelectedItem().toString(), methBox.getSelectedItem().toString(), newName.getText());
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
		
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);

		JPanel dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		
		dPanel.add(new JLabel("Select the class: "));
		dPanel.add(namesBox);

		if(classNames.length > 0){
		JOptionPane.showConfirmDialog(this, dPanel, "Delete Method", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no classes");
		}

		dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));

		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);

		dPanel.add(new JLabel("Select a method to delete: "));
		dPanel.add(methBox);

		int entered = -1;
		if(methNames.length > 0){
			entered = JOptionPane.showConfirmDialog(this, dPanel, "Delete Method", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no methods in this class");
		}

		if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null && entered == 0) {
			try {
				boolean deleted = diagram.deleteMethod(namesBox.getSelectedItem().toString(), methBox.getSelectedItem().toString());
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

	private String[] getClassMethods(String className){
		ArrayList<Method> meths= diagram.getClassByName(className).getMethods();
		String[] methNames = new String[meths.size()];
		int index = 0;
		for(Method meth: meths){
			methNames[index] = meth.getName();
			index++;
		}
		return methNames;
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
	    
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		

		JPanel pPanel = new JPanel();
		pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));

		pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));

		pPanel.add(new JLabel("Select a class"));
		pPanel.add(namesBox);

		if(classNames.length > 0){
			JOptionPane.showConfirmDialog(this, pPanel, "Add Parameter", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no classes");
		}

		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);
		JTextField newName = new JTextField();
		JComboBox<String> attType = new JComboBox<String>(attributeTypes()); 

		pPanel = new JPanel();
		pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));

		pPanel.add(new JLabel("Select a method: "));
		pPanel.add(methBox);
		pPanel.add(new JLabel("Enter the name: "));
		pPanel.add(newName);
		pPanel.add(new JLabel("Select the type: "));
		pPanel.add(attType);

		int entered = -1;
		if(methNames.length > 0){
			entered = JOptionPane.showConfirmDialog(this, pPanel, "Add Parameter", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no methods in this class");
		}

	    if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null && newName.toString() != null && attType.getSelectedItem() != null && entered == 0) {
	        try {
	            boolean success = diagram.addParameter(namesBox.getSelectedItem().toString(), methBox.getSelectedItem().toString(), newName.getText(), attType.getSelectedItem().toString());
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
	    
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);

		JPanel rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		
		rPanel.add(new JLabel("Select the class"));
		rPanel.add(namesBox);

		if(classNames.length > 0){
			JOptionPane.showConfirmDialog(this, rPanel, "Rename Parameter", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no classes");
		}

		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);
		rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		
		rPanel.add(new JLabel("Select the method"));
		rPanel.add(methBox);

		if(methNames.length > 0){
			JOptionPane.showConfirmDialog(this, rPanel, "Rename Parameter", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no methods in this class");
		}

		String[] paramNames = getMethodParams(namesBox.getSelectedItem().toString(),methBox.getSelectedItem().toString());
		JComboBox<String> paramBox = new JComboBox<String>(paramNames);
		JTextField newName = new JTextField();
		rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		
		rPanel.add(new JLabel("Select the parameter: "));
		rPanel.add(paramBox);
		rPanel.add(new JLabel("Enter the new name: "));
		rPanel.add(newName);

		int entered = -1;
		if(paramNames.length > 0){
			entered = JOptionPane.showConfirmDialog(this, rPanel, "Rename Parameter", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no parameters in this method");
		}

	    if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null && paramBox.getSelectedItem() != null && newName != null && entered == 0) {
	        boolean success = diagram.renameParameter(namesBox.getSelectedItem().toString(), methBox.getSelectedItem().toString()
					, paramBox.getSelectedItem().toString(), newName.getText());
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
	    
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);

		JPanel dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		
		dPanel.add(new JLabel("Select the class: "));
		dPanel.add(namesBox);

		if(classNames.length > 0){
		JOptionPane.showConfirmDialog(this, dPanel, "Delete Parameter", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no classes");
		}

		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);

		dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		
		dPanel.add(new JLabel("Select the method: "));
		dPanel.add(methBox);

		if(methNames.length > 0){
		JOptionPane.showConfirmDialog(this, dPanel, "Delete Parameter", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no methods in this class");
		}

		String[] paramNames = getMethodParams(namesBox.getSelectedItem().toString(), methBox.getSelectedItem().toString());
		JComboBox<String> paramBox = new JComboBox<String>(paramNames);
		dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		
		dPanel.add(new JLabel("Select the parameter to delete: "));
		dPanel.add(paramBox);

		int entered = -1;
		if(paramNames.length > 0){
		entered = JOptionPane.showConfirmDialog(this, dPanel, "Delete Parameter", JOptionPane.OK_CANCEL_OPTION);
		}else{
			JOptionPane.showMessageDialog(this, "There are no parameters in this method");
		}

	    if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null && paramBox.getSelectedItem() != null && entered == 0) {
	        boolean success = diagram.deleteParameter(namesBox.getSelectedItem().toString(), methBox.getSelectedItem().toString(), paramBox.getSelectedItem().toString());
	        if (success) {
	            JOptionPane.showMessageDialog(this, "Parameter deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	            updateDiagramView();
	        } else {
	            JOptionPane.showMessageDialog(this, "Failed to delete parameter. Ensure the method and parameter exist.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

	
	private String[] getMethodParams(String className, String methodName){
		List<Parameter> params = diagram.getClassByName(className).getMethod(methodName).getParameters();
		String[] paramNames = new String[params.size()];
		int index = 0;
		for(Parameter param: params){
			paramNames[index] = param.getName();
			index++;
		}
		return paramNames;
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
					JsonArray jsonPoss = new JsonArray();
					JsonObject jsonPos = new JsonObject();
					//jsonPos.addProperty("x", clspos.getX());
					//jsonPos.addProperty("y", clspos.getY());
					
					jsonClass.add("position", jsonPoss);
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

							//int posX = jsonClass.get("x").getAsInt(); 
							//int posY = jsonClass.get("y").getAsInt();
							//Setup set functions for guiView

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
