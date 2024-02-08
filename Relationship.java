import java.util.*;

/**
 * A class to manage relationships in a UML diagram.
 */
public class Relationship {

    /**
     * A class to edit UML diagrams.
     */
    public static class UMLDiagramEditor {
        private final Map<String, Set<String>> relationships;

        /**
         * Constructs a UMLDiagramEditor object.
         */
        public UMLDiagramEditor() {

            relationships = new HashMap<>();
        }

        /**
         * Adds a class to the UML diagram.
         *
         * @param className The name of the class to be added.
         */
        public void addClass(String className) {
            // Method implementation...
        }

        /**
         * Adds a relationship between two classes in the UML diagram.
         *
         * @param sourceClass      The source class of the relationship.
         * @param destinationClass The destination class of the relationship.
         * @param ID               The ID of the relationship.
         * @param newType          The type of the relationship.
         * @return true if the relationship is added successfully, false otherwise.
         */
        public boolean addRelationship(String sourceClass, String destinationClass, String ID, String newType) {
            // Check if both classes exist
            if (!relationships.containsKey(sourceClass) || !relationships.containsKey(destinationClass)) {
                System.out.println("Error: One or both of the specified classes do not exist in the diagram.");
                return false;
            }

            // Check if a relationship already exists between the specified classes
            if (relationshipExists(sourceClass, destinationClass)) {
                System.out.println("Error: A relationship between the specified classes already exists.");
                return false;
            }

            // Add relationship with ID and newType
            String relationshipInfo = String.format("ID: %s, Type: %s", ID, newType);
            relationships.get(sourceClass).add(destinationClass + " - " + relationshipInfo);
            relationships.get(destinationClass).add(sourceClass + " - " + relationshipInfo); // Assuming relationship is bi-directional
            System.out.println("Relationship added between " + sourceClass + " and " + destinationClass + " with ID: " + ID + " and Type: " + newType);
            return true;
        }

        /**
         * Checks if a relationship already exists between two classes.
         *
         * @param sourceClass      The source class of the relationship.
         * @param destinationClass The destination class of the relationship.
         * @return true if a relationship exists, false otherwise.
         */
        private boolean relationshipExists(String sourceClass, String destinationClass) {
            Set<String> sourceRelationships = relationships.get(sourceClass);
            for (String relationship : sourceRelationships) {
                String[] parts = relationship.split(" - ");
                String destClass = parts[0];
                if (destClass.equals(destinationClass)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Deletes a relationship between two classes in the UML diagram.
         *
         * @param sourceClass      The source class of the relationship.
         * @param destinationClass The destination class of the relationship.
         * @param ID               The ID of the relationship to be deleted.
         * @return true if the relationship is deleted successfully, false otherwise.
         */
        public boolean deleteRelationship(String sourceClass, String destinationClass, String ID) {
            // Check if both classes exist
            if (!relationships.containsKey(sourceClass) || !relationships.containsKey(destinationClass)) {
                System.out.println("Error: One or both of the specified classes do not exist in the diagram.");
                return false;
            }

            // Remove relationship
            String relationshipInfo = "ID: " + ID;
            String sourceRelationshipInfo = destinationClass + " - " + relationshipInfo;
            String destinationRelationshipInfo = sourceClass + " - " + relationshipInfo;

            if (relationships.get(sourceClass).remove(sourceRelationshipInfo) ||
                    relationships.get(destinationClass).remove(destinationRelationshipInfo)) {
                // If the relationship is removed from either source or destination class, remove it from the other class.
                relationships.get(destinationClass).remove(sourceClass + " - " + relationshipInfo);
                relationships.get(sourceClass).remove(destinationClass + " - " + relationshipInfo);
                System.out.println("Relationship between " + sourceClass + " and " + destinationClass + " with ID " + ID + " deleted.");
                return true;
            } else {
                System.out.println("Error: No relationship exists between the specified classes with the given ID.");
                return false;
            }
        }
    }
}
