package com.classuml.Controller;

import java.io.IOException;

/**
 * The {@code UMLEditor} class serves as the entry point to the UML diagram editor application.
 * It decides between launching the application in GUI (Graphical User Interface) mode or CLI
 * (Command Line Interface) mode based on the command-line arguments provided.
 * 
 * <p>
 * If the "--cli" argument is provided, the application starts in CLI mode, offering a text-based interface
 * for interacting with the UML diagram functionalities. Otherwise, it defaults to GUI mode, providing a graphical
 * interface for the same purpose.
 * </p>
 */
public class UMLEditor {

    /**
     * The main entry point of the UML diagram editor application.
     * @param args 
     * 
     * @param args The command-line arguments passed to the application. The presence of "--cli"
     *             argument switches the application to CLI mode, otherwise, GUI mode is launched.
     */
    public static void main(String[] args) throws IOException{
        // Check if the --cli argument is provided
        boolean cliMode = false;
        for (String arg : args) {
            if (arg.equals("cli")) {
                cliMode = true;
                break;
            }
        }

        if (cliMode) {
            try {
        	    UMLCli.launch();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            // Launch CLI mode
  
        } else {
            // Launch GUI mode
            javax.swing.SwingUtilities.invokeLater(UMLGui::new);
        }
    }
}