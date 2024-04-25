package com.classuml.Controller;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.IllegalComponentStateException;
import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.swing.KeyStroke;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.*;

import com.classuml.Model.*;
import com.classuml.View.guiView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

import java.awt.Point;
import java.awt.Rectangle;


// Assuming UMLDiagram and related classes are in the Model package

/**
 * Represents the main window of a UML diagram editor, providing a graphical user interface
 * for creating and manipulating UML diagrams. It supports actions such as adding, renaming,
 * and deleting classes, fields, methods, and relationships, as well as saving and loading diagrams.
 */
public class UMLGui extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static UMLDiagram diagram = new UMLDiagram();
	private guiView view = new guiView(diagram.getClasses(), diagram.getRelationships());
	private JPanel classPanelContainer;
	private JScrollPane scrollPane;
	private Rectangle windowDimensions;
	public static int prefMaxWidth = 800;
	private static int prefMaxHeight = 800;
	private static boolean isSaved = false;
	Timer timer;

	JMenu fileMenu = new JMenu("File");
	JMenu classMenu = new JMenu("Class");
	JMenu attributeMenu = new JMenu("Attribute");
	JMenu parameterMenu = new JMenu("Parameters");
	JMenu relationshipMenu = new JMenu("Relationship");
	JMenu interfaceMenu = new JMenu("Interface");

	//JMenu Item
	JMenuItem saveItem = addMenuItem(fileMenu, "Save", "save", 'S');
	JMenuItem loadItem = addMenuItem(fileMenu, "Load", "load", 'O');
	JMenuItem clearItem = addMenuItem(fileMenu, "Clear", "clear", 'Q');
	JMenuItem helpItem = addMenuItem(fileMenu, "Help", "help", 'H');
	JMenuItem quitItem = addMenuItem(fileMenu, "Quit", "quit");
	JMenuItem addClassItem = addMenuItem(classMenu, "Add Class", "addClass", 'C');
	JMenuItem renClassItem = addMenuItem(classMenu, "Rename Class", "renameClass");
	JMenuItem delClassItem = addMenuItem(classMenu, "Delete Class", "deleteClass");
	JMenuItem addAttItem = addMenuItem(attributeMenu, "Add Field", "addField", 'F');
	JMenuItem renAttItem = addMenuItem(attributeMenu, "Rename Field", "renameField");
	JMenuItem delAttItem = addMenuItem(attributeMenu, "Delete Field", "deleteField");
	JMenuItem addMethItem = addMenuItem(attributeMenu, "Add Method", "addMethod", 'M');
	JMenuItem renMethItem = addMenuItem(attributeMenu, "Rename Method", "renameMethod");
	JMenuItem delMethItem = addMenuItem(attributeMenu, "Delete Method", "deleteMethod");
	JMenuItem addParItem = addMenuItem(parameterMenu, "Add Parameter", "addParameter", 'P');
	JMenuItem renParItem = addMenuItem(parameterMenu, "Rename Parameter", "renameParameter");
	JMenuItem delParItem = addMenuItem(parameterMenu, "Delete Parameter", "deleteParameter");
	JMenuItem repParItem = addMenuItem(parameterMenu, "Replace Parameters", "replaceParameters");
	JMenuItem addRelItem = addMenuItem(relationshipMenu, "Add Relationship", "addRelationship", 'R');
	JMenuItem delRelItem = addMenuItem(relationshipMenu, "Delete Relationship", "deleteRelationship");
	JMenuItem chgRelItem = addMenuItem(relationshipMenu, "Change Type", "changeType");
	
	
    /**
     * Constructs the UMLGui frame and initializes the GUI components, including
     * setting up the menu bar, class panel container, and scroll pane. It also
     * maximizes the window and sets the diagram GUI linkage.
     * @throws AWTException 
     */
	public UMLGui() throws AWTException {
		super("UML Diagram Editor");
		initializeGUI();
		diagram.setGui(this);
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty( "apple.awt.application.appearance", "system" );
		System.setProperty("apple.awt.application.name", "UML Editor");
		timer = new Timer(150, timerActionListener);
        timer.start();
	}  

	private ActionListener timerActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Restart the timer
			timer.restart();
			// Call the updatePreferredDimensions method
			updatePreferredDimensions();
		}
	};

/**************************************************************************************************************************************/
    /**   GUI FRAME SET-UPS   **/
/**************************************************************************************************************************************/

    /** 
     * Initializes the graphical user interface components and layout. It sets up
     * the menu bar, class panel container for displaying classes, and a scroll pane
     * for navigation. It also calls methods to update the diagram view and populate
     * class components based on the current state of the diagram.
     * @throws AWTException 
     */
	public void initializeGUI() throws AWTException {
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());

	    // Initialize and set up the menu bar
	    setJMenuBar(createMenuBar());	   

	    classPanelContainer = new JPanel();
		classPanelContainer.setPreferredSize(new Dimension(prefMaxWidth, prefMaxHeight));
	    scrollPane = new JScrollPane(classPanelContainer);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		classPanelContainer.setLayout(new BorderLayout());
	    add(scrollPane, BorderLayout.CENTER);
		setSize(300,300);
		setVisible(true);
	    
	    
	    // Maximize the window
	    setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	public void updatePreferredDimensions() {
		int maxWidth = 50;
		int maxHeight = 50;
	
		for (UMLClass umlClass : diagram.getClasses()) {
			Point position = umlClass.position;
			maxWidth = Math.max(maxWidth, position.x + 50);
			maxHeight = Math.max(maxHeight, position.y + 50);
		}
	
		prefMaxWidth = maxWidth;
		prefMaxHeight = maxHeight;
	
		classPanelContainer.setPreferredSize(new Dimension(prefMaxWidth, prefMaxHeight));
		classPanelContainer.revalidate();
		classPanelContainer.repaint();
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
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		fileMenu.add(clearItem);
		fileMenu.add(helpItem);
		fileMenu.add(quitItem);
		

		// Class Menu
		classMenu.add(addClassItem);
		classMenu.add(renClassItem);
		classMenu.add(delClassItem);

		// Attribute Menu
		attributeMenu.add(addAttItem);
		attributeMenu.add(renAttItem);
		attributeMenu.add(delAttItem);
		
		attributeMenu.add(addMethItem);
		attributeMenu.add(renMethItem);
		attributeMenu.add(delMethItem);
		
		parameterMenu.add(addParItem);
		parameterMenu.add(renParItem);
		parameterMenu.add(delParItem);
		parameterMenu.add(repParItem);

		// Relationship Menu
		relationshipMenu.add(addRelItem);
		relationshipMenu.add(delRelItem);
		relationshipMenu.add(chgRelItem);
		attributeMenu.setEnabled(false);
		relationshipMenu.setEnabled(false);
		parameterMenu.setEnabled(false);
		delClassItem.setEnabled(false);
		renClassItem.setEnabled(false);
		delAttItem.setEnabled(false);
		renAttItem.setEnabled(false);
		renMethItem.setEnabled(false);
		delMethItem.setEnabled(false);
		delParItem.setEnabled(false);
		renParItem.setEnabled(false);
		repParItem.setEnabled(false);
		delRelItem.setEnabled(false);
		chgRelItem.setEnabled(false);

		// Interface Menu
		JMenuItem u = addMenuItem(interfaceMenu, "Undo", "undo", 'Z');
        JMenuItem r = addMenuItem(interfaceMenu, "Redo", "redo", 'Y');
		JMenuItem s = addMenuItem(interfaceMenu, "Snapshot Diagram", "snapshot");
		interfaceMenu.add(u);
		interfaceMenu.add(r);
		interfaceMenu.add(s);

		// Adding menus to menu bar
		menuBar.add(fileMenu);
		menuBar.add(classMenu);
		menuBar.add(attributeMenu);
		menuBar.add(parameterMenu);
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
	private JMenuItem addMenuItem(JMenu menu, String title, String actionCommand) {
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.setActionCommand(actionCommand);
		menuItem.addActionListener(this);
		return menuItem;
	}

	private JMenuItem addMenuItem(JMenu menu, String title, String actionCommand, int controlChar) {
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.setActionCommand(actionCommand);
		menuItem.addActionListener(this);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(controlChar, java.awt.event.InputEvent.CTRL_DOWN_MASK));
		return menuItem;
	}

	/**
	 * Finds the window dimensions to snapshot the screen. This does not
	 * capture the internal canvas, only what the monitor sees, so I can't 
	 * unfortunately take a snapshot of the entire UML canvas if the window
	 * showing it is smaller and needs to display a scroll bar to see the
	 * whole thing.
	 */
	public void recalculateWindowDimForSnapshot()
	{
		Point frameLocation;
		try
		{
			frameLocation = this.getLocationOnScreen();
		}
		catch (IllegalComponentStateException e)
		{
			frameLocation = new Point();
		}
		int frameX = frameLocation.x;
		int frameY = frameLocation.y;
		windowDimensions = classPanelContainer.getBounds();
		windowDimensions.setLocation(frameX, frameY);
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
			isSaved = false;
			break;
		case "renameClass":
			renameClass();
			isSaved = false;
			break;
		case "deleteClass":
			deleteClass();
			isSaved = false;
			break;
		case "addField":
			addField();
			isSaved = false;
			break;
		case "renameField":
			renameField();
			isSaved = false;
			break;
		case "deleteField":
			deleteField();
			isSaved = false;
			break;
		case "addMethod":
			addMethod();
			isSaved = false;
			break;
		case "renameMethod":
			renameMethod();
			isSaved = false;
			break;
		case "deleteMethod":
			deleteMethod();
			isSaved = false;
			break;
		case "addParameter":
			addParameter();
			isSaved = false;
			break;
		case "renameParameter":
			renameParameter();
			isSaved = false;
			break;
		case "deleteParameter":
			deleteParameter();
			isSaved = false;
			break;
		case "replaceParameters":
			replaceParameters();
			isSaved = false;
			break;
		case "addRelationship":
			addRelationship();
			isSaved = false;
			break;
		case "deleteRelationship":
			deleteRelationship();
			isSaved = false;
			break;
		case "changeType":
			changeType();
			isSaved = false;
			break;
		case "save":
			saveDiagram();
			isSaved = true;
			break;
		case "load":
			loadDiagram();
			isSaved = false;
			break;
		case "help":
			showHelp();
			break;
		case "undo":
            undo();
			isSaved = false;
            break;
        case "redo":
            redo();
			isSaved = false;
            break;
		case "clear":
			clearGui();
			isSaved = false;
			break;
		case "quit":
			quitGui();
		case "snapshot":
			getSnapshotImage();
			break; 
        }
		if (diagram.getClasses().size() > 0){
			attributeMenu.setEnabled(true);
			delClassItem.setEnabled(true);
			renClassItem.setEnabled(true);
		}
		else {
			attributeMenu.setEnabled(false);
		}
		if (diagram.getClasses().size() > 1){
			relationshipMenu.setEnabled(true);
		}
		else{
			relationshipMenu.setEnabled(false);
		}
		for( UMLClass c : diagram.getClasses()){
			if (c.getMethods().size() > 0){
				parameterMenu.setEnabled(true);
				renMethItem.setEnabled(true);
				delMethItem.setEnabled(true);
				break;
			}
			else{
				parameterMenu.setEnabled(false);
				delMethItem.setEnabled(false);
				renMethItem.setEnabled(false);
			}
		}
		for(UMLClass c : diagram.getClasses()){
			if(c.getFields().size() > 0){
				delAttItem.setEnabled(true);
				renAttItem.setEnabled(true);
				break;
			}
			else{
				delAttItem.setEnabled(false);
				renAttItem.setEnabled(false);
			}
		}
		for (UMLClass c : diagram.getClasses()){
			for(Method m : c.getMethods()){
				if (m.getParameters().size() > 0){
					delParItem.setEnabled(true);
					renParItem.setEnabled(true);
					repParItem.setEnabled(true);
					break;
				}
				else{
					delParItem.setEnabled(false);
					renParItem.setEnabled(false);
					repParItem.setEnabled(false);
				}
			}
		}
		if (diagram.getRelationships().size() > 0){
			delRelItem.setEnabled(true);
			chgRelItem.setEnabled(true);
		}
		else{
			delRelItem.setEnabled(false);
			chgRelItem.setEnabled(false);
		}
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
		String className = JOptionPane.showInputDialog(this, "Enter class name: ", "Add Class",
				JOptionPane.PLAIN_MESSAGE);

		// Check if the user provided a class name and didn't cancel the dialog
		if (className != null && !className.trim().isEmpty()) {
			try {
				// Attempt to add the class to the diagram. Assuming diagram.addClass throws
				// IllegalArgumentException if class exists
				boolean added = diagram.addClass(className);
				if (added) {
					// Update the diagram view to reflect the new class
					changeComponent();

					
				} else {
					// Class already exists
					JOptionPane.showMessageDialog(this, "Class '" + className + "' is invalid.",
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
		entered = JOptionPane.showConfirmDialog(this, cName, "Rename Class", JOptionPane.OK_CANCEL_OPTION);

		if (namesBox.getSelectedItem() != null && newName != null && entered == 0) {
				try {
					boolean renamed = diagram.renameClass(namesBox.getSelectedItem().toString(), newName.getText());
					if (renamed) {
						changeComponent();

						
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

		Object className = JOptionPane.showInputDialog(this, "Select the class to delete: ", "Delete Class",
				JOptionPane.PLAIN_MESSAGE,null, classNames, classNames[0]);
		if (className != null) {
			int confirmation = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete '" + (String)className + "'?", "Delete Class", JOptionPane.YES_NO_OPTION);
			if (confirmation == JOptionPane.YES_OPTION) {
				boolean deleted = diagram.deleteClass((String)className);
				if (deleted) {
					changeComponent();

					
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

	// private String[] returnTypes(){
	// 	return new String[] {"void", "int", "double", "boolean", "string", "object"};
	// }

	// private String[] attributeTypes(){
	// 	return new String[] {"int", "double", "boolean", "string"};
	// }

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
		JTextField fieldType = new JTextField();

		JPanel oPanel = new JPanel();
		oPanel.setLayout(new BoxLayout(oPanel, BoxLayout.Y_AXIS));

		oPanel.add(new JLabel("Select a class: "));
		oPanel.add(namesBox);
		oPanel.add(new JLabel("Enter the field name: "));
		oPanel.add(fieldName);
		oPanel.add(new JLabel("Select the field type: "));
		oPanel.add(fieldType);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, oPanel, "Add Field", JOptionPane.OK_CANCEL_OPTION);

		if (namesBox.getSelectedItem() != null && fieldName != null && fieldType != null && entered == 0) {
			try {
				String objName = namesBox.getSelectedItem().toString();
				boolean added = diagram.addField(objName, fieldName.getText(), fieldType.getText());
				if (added) {
					changeComponent();

					
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

		JPanel rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames); 
		JTextField newName = new JTextField();
		String[] fieldNames = getClassFields(namesBox.getSelectedItem().toString());
		JComboBox<String> fieldsBox = new JComboBox<String>(fieldNames);
		
		namesBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				fieldsBox.removeAllItems();
				String[] newFNames = getClassFields(namesBox.getSelectedItem().toString());
				for(String fName: newFNames){
					fieldsBox.addItem(fName);
				}
			}
		});

		rPanel.add(new JLabel("Select the class: "));
		rPanel.add(namesBox);
		rPanel.add(new JLabel("Select the field: "));
		rPanel.add(fieldsBox);
		rPanel.add(new JLabel("Enter the new field name: "));
		rPanel.add(newName);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, rPanel,"Rename Field", JOptionPane.OK_CANCEL_OPTION);
		
		if (namesBox.getSelectedItem() != null && fieldsBox.getSelectedItem() != null && newName != null && entered == 0) {
			try {
				String objName = namesBox.getSelectedItem().toString();
				boolean renamed = diagram.renameField(objName , fieldsBox.getSelectedItem().toString(), newName.getText());
				if (renamed) {
					changeComponent();

					
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
		
		JPanel dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames); 
		String[] fieldNames = getClassFields(namesBox.getSelectedItem().toString());
		JComboBox<String> fieldsBox = new JComboBox<String>(fieldNames);
		
		namesBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedClassName = (String) namesBox.getSelectedItem();
				String[] fieldNames = getClassFields(selectedClassName);
				fieldsBox.removeAllItems();
				for (String fieldName : fieldNames) {
					fieldsBox.addItem(fieldName);
				}
			}
		});

		dPanel.add(new JLabel("Select the Class: "));
		dPanel.add(namesBox);
		dPanel.add(new JLabel("Select the field to delete: "));
		dPanel.add(fieldsBox);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, dPanel,"Delete Field", JOptionPane.OK_CANCEL_OPTION);

		if (namesBox.getSelectedItem() != null && fieldsBox.getSelectedItem() != null && entered == 0) {
			try {
				String objName = namesBox.getSelectedItem().toString();
				boolean deleted = diagram.deleteField(objName , fieldsBox.getSelectedItem().toString());
				if (deleted) {
					changeComponent();

					
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
		JTextField methType = new JTextField();

		JPanel mPanel = new JPanel();
		mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
		
		mPanel.add(new JLabel("Select a class: "));
		mPanel.add(namesBox);
		mPanel.add(new JLabel("Enter the name: "));
		mPanel.add(methName);
		mPanel.add(new JLabel("Select the return type: "));
		mPanel.add(methType);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, mPanel, "Add Method", JOptionPane.OK_CANCEL_OPTION);

		if (namesBox.getSelectedItem() != null && methName != null && methType != null && entered == 0) {
			if (methName != null && methType != null) {
					try {
						String objName = namesBox.getSelectedItem().toString();
						boolean added = diagram.addMethod(objName, methName.getText(), methType.getText());
						if (added) {
							changeComponent();

							
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

		JPanel rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		JTextField newName = new JTextField();
		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);
		
		namesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String[] newMNames = getClassMethods(namesBox.getSelectedItem().toString());
				methBox.removeAllItems();
				for(String methName: newMNames){
					methBox.addItem(methName);
				}
			}
		});
		
		rPanel.add(new JLabel("Select the class: "));
		rPanel.add(namesBox);
		rPanel.add(new JLabel("Select the method: "));
		rPanel.add(methBox);
		rPanel.add(new JLabel("Enter the new name: "));
		rPanel.add(newName);
		
		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, rPanel, "Rename Method", JOptionPane.OK_CANCEL_OPTION);


		if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null
				&& newName != null && entered == 0) {
			try {
				String objName = namesBox.getSelectedItem().toString();
				boolean renamed = diagram.renameMethod(objName, methBox.getSelectedItem().toString(), newName.getText());
				if (renamed) {
					changeComponent();

					
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
		
		JPanel dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);
		
		namesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String[] newMNames = getClassMethods(namesBox.getSelectedItem().toString());
				methBox.removeAllItems();
				for(String methName: newMNames){
					methBox.addItem(methName);
				}
			}
		});

		dPanel.add(new JLabel("Select the class: "));
		dPanel.add(namesBox);
		dPanel.add(new JLabel("Select a method to delete: "));
		dPanel.add(methBox);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, dPanel, "Delete Method", JOptionPane.OK_CANCEL_OPTION);

		if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null && entered == 0) {
			try {
				String objName = namesBox.getSelectedItem().toString();
				boolean deleted = diagram.deleteMethod(objName, methBox.getSelectedItem().toString());
				if (deleted) {
					changeComponent();

					
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
	    JPanel pPanel = new JPanel();
		pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);
		JTextField newName = new JTextField();
		JTextField attType = new JTextField(); 

		namesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String[] newMNames = getClassMethods(namesBox.getSelectedItem().toString());
				methBox.removeAllItems();
				for(String methName: newMNames){
					methBox.addItem(methName);
				}
			}
		});

		
		pPanel.add(new JLabel("Select a class: "));
		pPanel.add(namesBox);
		pPanel.add(new JLabel("Select a method: "));
		pPanel.add(methBox);
		pPanel.add(new JLabel("Enter the name: "));
		pPanel.add(newName);
		pPanel.add(new JLabel("Select the type: "));
		pPanel.add(attType);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, pPanel, "Add Parameter", JOptionPane.OK_CANCEL_OPTION);
		

	    if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null && newName.toString() != null && attType != null && entered == 0) {
	        try {
				String objName = namesBox.getSelectedItem().toString();
	            boolean success = diagram.addParameter(objName, methBox.getSelectedItem().toString(), newName.getText(), attType.getText());
	            if (success) {
	                changeComponent();

	                
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

	    JPanel rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);
		String[] paramNames = getMethodParams(namesBox.getSelectedItem().toString(),methBox.getSelectedItem().toString());
		JComboBox<String> paramBox = new JComboBox<String>(paramNames);
		JTextField newName = new JTextField();

		namesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				methBox.removeAllItems();
				String[] newMNames = getClassMethods(namesBox.getSelectedItem().toString());
				for(String methName: newMNames){
					methBox.addItem(methName);
				}
				if(newMNames.length > 0) {
					methBox.setSelectedItem(newMNames[0]);
				}
			}
		});
		
		methBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(methBox.getSelectedItem()!= null){
					paramBox.removeAllItems();
					String[] newPnames = getMethodParams(namesBox.getSelectedItem().toString(),methBox.getSelectedItem().toString());
					for(String newParam: newPnames){
						paramBox.addItem(newParam);
					}
				}
			}
		});
		
		rPanel.add(new JLabel("Select the class: "));
		rPanel.add(namesBox);
		rPanel.add(new JLabel("Select the method: "));
		rPanel.add(methBox);
		rPanel.add(new JLabel("Select the parameter: "));
		rPanel.add(paramBox);
		rPanel.add(new JLabel("Enter the new name: "));
		rPanel.add(newName);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, rPanel, "Rename Parameter", JOptionPane.OK_CANCEL_OPTION);

	    if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null && paramBox.getSelectedItem() != null && newName != null && entered == 0) {
			String objName = namesBox.getSelectedItem().toString();
	        boolean success = diagram.renameParameter(objName, methBox.getSelectedItem().toString()
					, paramBox.getSelectedItem().toString(), newName.getText());
	        if (success) {
	            changeComponent();

	            
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
	    
		JPanel dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		String[] methNames = getClassMethods(namesBox.getSelectedItem().toString());
		JComboBox<String> methBox = new JComboBox<String>(methNames);
		String[] paramNames = getMethodParams(namesBox.getSelectedItem().toString(), methBox.getSelectedItem().toString());
		JComboBox<String> paramBox = new JComboBox<String>(paramNames);

		namesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				methBox.removeAllItems();
				String[] newMNames = getClassMethods(namesBox.getSelectedItem().toString());
				for(String methName: newMNames){
					methBox.addItem(methName);
				}
			}
		});
		
		methBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				paramBox.removeAllItems();
				String[] newPNames = getMethodParams(namesBox.getSelectedItem().toString(), methBox.getSelectedItem().toString());
				for(String paramName: newPNames){
					paramBox.addItem(paramName);
				}
			}
		});
		
		dPanel.add(new JLabel("Select the class: "));
		dPanel.add(namesBox);
		dPanel.add(new JLabel("Select the method: "));
		dPanel.add(methBox);
		dPanel.add(new JLabel("Select the parameter to delete: "));
		dPanel.add(paramBox);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, dPanel, "Delete Parameter", JOptionPane.OK_CANCEL_OPTION);

	    if (namesBox.getSelectedItem() != null && methBox.getSelectedItem() != null && paramBox.getSelectedItem() != null && entered == 0) {
			String objName = namesBox.getSelectedItem().toString();
	        boolean success = diagram.deleteParameter(objName, methBox.getSelectedItem().toString(), paramBox.getSelectedItem().toString());
	        if (success) {
	            changeComponent();

	            
	        } else {
	            JOptionPane.showMessageDialog(this, "Failed to delete parameter. Ensure the method and parameter exist.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	private void replaceParameters() {
		// Prompt the user for class name, method name, and number of parameters
		String[] classNames = getClassNames();
		JComboBox<String> namesBox = new JComboBox<String>(classNames);
		JTextField methName = new JTextField();
		JTextField numParams = new JTextField();
		JPanel pPanel = new JPanel();
		pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));
		pPanel.add(new JLabel("Select class: "));
		pPanel.add(namesBox);
		pPanel.add(new JLabel("Enter method name: "));
		pPanel.add(methName);
		pPanel.add(new JLabel("Enter number of parameters: "));
		pPanel.add(numParams);
	
		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, pPanel, "Replace Parameters", JOptionPane.OK_CANCEL_OPTION);
	
		if (entered == 0 && namesBox.getSelectedItem() != null && methName.getText() != null && numParams.getText() != null) {
			int num = Integer.parseInt(numParams.getText());
			String[] paramNames = new String[num];
			String[] paramTypes = new String[num];
	
			for (int i = 0; i < num; i++) {
				JPanel pPanelInner = new JPanel();
				pPanelInner.setLayout(new BoxLayout(pPanelInner, BoxLayout.Y_AXIS));
				JTextField paramName = new JTextField();
				JTextField attType = new JTextField();
				pPanelInner.add(new JLabel("Enter parameter name: "));
				pPanelInner.add(paramName);
				pPanelInner.add(new JLabel("Select parameter type: "));
				pPanelInner.add(attType);
	
				entered = JOptionPane.showConfirmDialog(this, pPanelInner, "Parameter " + (i + 1), JOptionPane.OK_CANCEL_OPTION);
	
				if (entered == 0 && paramName.getText() != null) {
					paramNames[i] = paramName.getText();
					paramTypes[i] = attType.toString();
				} else {
					return;
				}
			}
	
			diagram.replaceParameters(namesBox.getSelectedItem().toString(), methName.getText(), paramNames, paramTypes);
			changeComponent();
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

		JPanel aPanel = new JPanel();
		aPanel.setLayout(new BoxLayout(aPanel, BoxLayout.Y_AXIS));
		String[] classNames = getClassNames();
		JComboBox<String> namesBox1 = new JComboBox<String>(classNames);
		JComboBox<String> namesBox2 = new JComboBox<String>(classNames);
		JComboBox<String> typesBox = new JComboBox<String>();

		typesBox.addItem("Aggregation");
		typesBox.addItem("Composition");
		typesBox.addItem("Inheritance");
		typesBox.addItem("Realization");
		namesBox2.removeItemAt(0);

		namesBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String[] cNames = getClassNames();
				namesBox2.removeAllItems();
				for(String name: cNames){
					if(!namesBox1.getSelectedItem().toString().equals(name)){
						namesBox2.addItem(name);
					}
				}
			}
		});

		aPanel.add(new JLabel("Select source class: "));
		aPanel.add(namesBox1);
		aPanel.add(new JLabel("Select destination class: "));
		aPanel.add(namesBox2);
		aPanel.add(new JLabel("Select relationship type: "));
		aPanel.add(typesBox);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, aPanel, "Add Relationship", JOptionPane.OK_CANCEL_OPTION);

		String sourceClass = namesBox1.getSelectedItem().toString();
		String destinationClass = namesBox2.getSelectedItem().toString();

		if (namesBox1.getSelectedItem() != null && namesBox2.getSelectedItem() != null && typesBox.getSelectedItem() != null && entered == 0) {
			try {
				if (typesBox.getSelectedIndex() >= 0 && typesBox.getSelectedIndex() <= 3) {
					boolean added = diagram.addRelationship(sourceClass, destinationClass, (typesBox.getSelectedIndex() + 1));
					if (added) {
						changeComponent();
						
					} else {
						JOptionPane.showMessageDialog(this, "Failed to add relationship. Ensure both classes exist.",
								"Error Adding Relationship", JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "The relationship type must be a valid number.", "Invalid Type",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

    /**
     * Deletes an existing relationship between two specified classes.
     * Prompts the user for the source and destination class names of the relationship to delete.
     * Updates the diagram view and displays a success message if the relationship is deleted successfully,
     * otherwise shows an error message.
     */
	private void deleteRelationship() {

		JPanel dPanel = new JPanel();
		dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
		ArrayList<Relationship> relates = diagram.getRelationships();
		JComboBox<Relationship> relateBox = new JComboBox<Relationship>();

		for(Relationship r: relates){
			relateBox.addItem(r);
		}

		dPanel.add(new JLabel("Select the relationship to delete: "));
		dPanel.add(relateBox);

		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, dPanel, "Delete Relationship", JOptionPane.OK_CANCEL_OPTION);

		Relationship selected = (Relationship) relateBox.getSelectedItem();
		String sourceClass = selected.getSource();
		String destinationClass = selected.getDestination();

			if (relateBox.getSelectedItem() != null && entered == 0) {
				try {
					// Attempt to delete the relationship
					boolean deleted = diagram.deleteRelationship(sourceClass, destinationClass); // Assuming such a
																									// method exists in
																									// UMLDiagram
					if (deleted) {
						// Update the diagram view to reflect the change
						changeComponent();
						
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

    /**
     * Changes the type of an existing relationship between two specified classes.
     * Prompts the user for the source and destination class names and the new relationship type.
     * Updates the diagram view and displays a success message if the relationship type is changed successfully,
     * otherwise shows an error message.
     */
	private void changeType() {

		JPanel cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));

		ArrayList<Relationship> relates = diagram.getRelationships();
		JComboBox<Relationship> relateBox = new JComboBox<Relationship>();
		JComboBox<String> typesBox = new JComboBox<String>();

		typesBox.addItem("Aggregation");
		typesBox.addItem("Composition");
		typesBox.addItem("Inheritance");
		typesBox.addItem("Realization");

		for(Relationship r: relates){
			relateBox.addItem(r);
		}
		Relationship selected = (Relationship) relateBox.getSelectedItem();
		typesBox.setSelectedIndex((selected.getType() - 1));

		relateBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Relationship select = (Relationship) relateBox.getSelectedItem();
				typesBox.setSelectedIndex((select.getType() - 1));
			}
		});

		cPanel.add(new JLabel("Select the relationship to change: "));
		cPanel.add(relateBox);
		cPanel.add(new JLabel("Select the new type: "));
		cPanel.add(typesBox);



		int entered = -1;
		entered = JOptionPane.showConfirmDialog(this, cPanel, "Change Relationship Type", JOptionPane.OK_CANCEL_OPTION);

		
		String sourceClass = selected.getSource();
		String destinationClass = selected.getDestination();

		
			if (typesBox.getSelectedIndex() >= 0 && typesBox.getSelectedIndex() <= 3 && entered == 0 && relateBox.getSelectedItem() != null && entered == 0) { // Validate the input range
				try {
					boolean typeChanged = diagram.changeRelType(sourceClass, destinationClass, (typesBox.getSelectedIndex() + 1));
					if (typeChanged) {
						changeComponent();
					
					} else {
						JOptionPane.showMessageDialog(this, "Failed to change relationship type.", "Error",
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
	}


/**************************************************************************************************************************************/
    /**   INTERFACES   **/
/**************************************************************************************************************************************/

	private void clearGui(){
		if (!isSaved) {
			int response = JOptionPane.showConfirmDialog(
				null,
				"The diagram has unsaved changes. Do you want to save before quitting?",
				"Unsaved Changes",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
	
			if (response == JOptionPane.YES_OPTION) {
				saveDiagram();
			} else if (response == JOptionPane.NO_OPTION) {
				diagram.clear();
				changeComponent();
			} else if (response == JOptionPane.CANCEL_OPTION){

			}
		}
		else{
			diagram.clear();
			changeComponent();
		}
	}

	private void undo(){
		diagram.undo();
		changeComponent();
		
	}

	private void quitGui() {
		if (!isSaved) {
			int response = JOptionPane.showConfirmDialog(
				null,
				"The diagram has unsaved changes. Do you want to save before clearing?",
				"Unsaved Changes",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
	
			if (response == JOptionPane.YES_OPTION) {
				saveDiagram();
			} else if (response == JOptionPane.CANCEL_OPTION) {
				// Do nothing, just exit the method
				return;
			}
		}
		System.exit(0);
	}

	private void redo(){
		diagram.redo();
		changeComponent();
		
	}

	public void getSnapshotImage() {
		BufferedImage bImg = new BufferedImage(classPanelContainer.getWidth(), classPanelContainer.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D cg = bImg.createGraphics();
		classPanelContainer.paintAll(cg);
	
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save snapshot");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setSelectedFile(new File("GUIOutput.jpg"));
	
		int userSelection = fileChooser.showSaveDialog(null);
	
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			try {
				ImageIO.write(bImg, "jpg", new File(fileToSave.getParent() + "/" + timestamp + "-" + fileToSave.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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

					// Add the position property
					Point pos = umlClass.position;
					JsonObject position = new JsonObject();
					position.addProperty("x", pos.x);
					position.addProperty("y", pos.y);
					jsonClass.add("position", position);

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
            } catch (IOException e) {
            }
			isSaved = true;
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
	private void loadDiagram() {
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

							// Load the position
							JsonObject jsonPosition = jsonClass.get("position").getAsJsonObject();
							int x = jsonPosition.get("x").getAsInt();
    						int y = jsonPosition.get("y").getAsInt();
							Point position = new Point(x, y);
							
							// Create a Point object
							diagram.setPosition(className, position);

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
	                    String type = jsonRelationship.get("type").getAsString();
						int type2 = 0;
						if (type.equals("Aggregation")) {
							type2 = 1;
						}
						else if (type.equals("Composition")){
							type2 = 2;
						}
						else if (type.equals("Inheritance")) {
							type2 = 3;
						}
						else if (type.equals("Realization")) {
							type2 = 4;
						}
	                    diagram.addRelationship(source, destination, type2);
	                }
	            }
				changeComponent();

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
	private void changeComponent() {
		classPanelContainer.remove(view);
		view.updateContents(diagram.getClasses(), diagram.getRelationships());
		classPanelContainer.add(view);
		

	    classPanelContainer.revalidate();
	    classPanelContainer.repaint();
		
		recalculateWindowDimForSnapshot();
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
	public static void main(String[] args) throws IOException{
		boolean cliMode = false;
		for (String arg : args) {
            if (arg.equals("cli")) {
                cliMode = true;
                break;
            }
        }
		if (cliMode){
			try {
				UMLCli.launch();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		SwingUtilities.invokeLater(() -> {
			try {
				new UMLGui().setVisible(true);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		});
	}

}
