package com.classuml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.classuml.Model.Relationship;
import com.classuml.Model.UMLClass;
import com.classuml.Model.UMLDiagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Test class for {@link UMLDiagram}.
 */
public class UMLDiagramTest {

    private UMLDiagram umlDiagram;

    @BeforeEach
    void setUp() {
        umlDiagram = new UMLDiagram();
    }

    /**
     * Test case for adding a class.
     */
    @Test
    void testAddClass() {
        Assertions.assertTrue(umlDiagram.addClass("ClassA"));
        Assertions.assertFalse(umlDiagram.addClass("ClassA")); // Adding duplicate should return false
    }

    /**
     * Test case for deleting a class.
     */
    @Test
    void testDeleteClass() {
        umlDiagram.addClass("ClassA");
        Assertions.assertTrue(umlDiagram.deleteClass("ClassA"));
        Assertions.assertFalse(umlDiagram.deleteClass("NonExistingClass")); // Deleting non-existing should return false
    }

    /**
     * Test case for renaming a class.
     */
    @Test
    void testRenameClass() {
        umlDiagram.addClass("ClassA");
        umlDiagram.addClass("ClassB");
        Assertions.assertTrue(umlDiagram.renameClass("ClassA", "NewClassA"));
        Assertions.assertFalse(umlDiagram.renameClass("ClassA", "NewClassA")); // Renaming non-existing should return false
        Assertions.assertFalse(umlDiagram.renameClass("NewClassA", "ClassB")); // Renaming to existing should return false
    }

    /**
     * Test case for adding a relationship.
     */
    @Test
    void testAddRelationship() {
        umlDiagram.addClass("ClassA");
        umlDiagram.addClass("ClassB");
        Assertions.assertTrue(umlDiagram.addRelationship("ClassA", "ClassB", 2));
        Assertions.assertFalse(umlDiagram.addRelationship("ClassA", "ClassC", 5)); // Adding relationship with non-existing class should return false
    }

    /**
     * Test case for adding an empty relationship.
     */
    @Test
    void testAddEmptyRelationship() {
        umlDiagram.addClass("");
        Assertions.assertFalse(umlDiagram.addRelationship("", "", 0)); // Adding relationship with non-existing class should return false
    }
        
    /**
     * Test case for adding an empty relationship.
     */
    // @Test
    // void testAddSelfRelationship() {
    //     umlDiagram.addClass("ClassA");
    //     Assertions.assertFalse(umlDiagram.addRelationship("ClassA", "ClassA", 1)); // Adding relationship with non-existing class should return false
    // }
    
    /**
     * Test case for adding an empty relationship.
     */
    @Test
    void testAddRelationshipBadType1() {
        umlDiagram.addClass("ClassA");
        umlDiagram.addClass("ClassB");
        Assertions.assertFalse(umlDiagram.addRelationship("ClassA", "ClassB", 0)); // Adding relationship with non-existing class should return false
    }
    
    /**
     * Test case for adding an empty relationship.
     */
    @Test
    void testAddRelationshipBadType2() {
        umlDiagram.addClass("ClassA");
        umlDiagram.addClass("ClassB");
        Assertions.assertFalse(umlDiagram.addRelationship("ClassA", "ClassB", 5)); // Adding relationship with non-existing class should return false
    }
    
    /**
     * Test case for deleting a relationship.
     */
    @Test
    void testDeleteRelationship() {
        umlDiagram.addClass("ClassA");
        umlDiagram.addClass("ClassB");
        umlDiagram.addRelationship("ClassA", "ClassB", 4);
        Assertions.assertTrue(umlDiagram.deleteRelationship("ClassA", "ClassB"));
        Assertions.assertFalse(umlDiagram.deleteRelationship("ClassA", "ClassB")); // Deleting non-existing relationship should return false
    }

    /**
     * Test case for deleting a relationship.
     */
    @Test
    void testDeleteRelationshipEmptyParam() {
        umlDiagram.addClass("");
        umlDiagram.addClass("");
        umlDiagram.addRelationship("", "", 4);
        Assertions.assertFalse(umlDiagram.deleteRelationship("", "")); // Deleting non-existing relationship should return false
    }
    
    /**
     * Test case for deleting a relationship.
     */
    // @Test
    // void testDeleteRelationshipSameName() {
    //     umlDiagram.addClass("ClassA");
    //     umlDiagram.addClass("ClassA");
    //     umlDiagram.addRelationship("ClassA", "ClassA", 4);
    //     Assertions.assertFalse(umlDiagram.deleteRelationship("ClassA", "ClassA")); // Deleting non-existing relationship should return false
    // }
    
    /**
     * Test case for adding an attribute.
     */
    @Test
    void testAddAttribute() {
        umlDiagram.addClass("ClassA");
        Assertions.assertTrue(umlDiagram.addField("ClassA", "attribute", "type"));
        Assertions.assertFalse(umlDiagram.addField("NonExistingClass", "attribute", "type")); // Adding attribute to non-existing class should return false
    }

    /**
     * Test case for deleting an attribute.
     */
    // @Test
    // void testDeleteAttribute() {
    //     umlDiagram.addClass("ClassA");
    //     umlDiagram.addField("ClassA", "attribute", "type");
    //     Assertions.assertTrue(umlDiagram.addField("ClassA", "attribute", null));
    //     Assertions.assertFalse(umlDiagram.addField("ClassA", "attribute", null)); // Deleting non-existing attribute should return false
    // }

    /**
     * Test case for renaming an attribute.
     */
    @Test
    void testRenameAttribute() {
        umlDiagram.addClass("ClassA");
        umlDiagram.addField("ClassA", "attribute", "type");
        Assertions.assertTrue(umlDiagram.renameField("ClassA", "attribute", "newAttribute"));
        Assertions.assertFalse(umlDiagram.renameField("ClassA", "attribute", "newAttribute")); // Renaming non-existing attribute should return false
    }

    /**
     * Test case for adding a method.
     */
    @Test
    void testAddMethod() {
        umlDiagram.addClass("ClassA");
        Assertions.assertTrue(umlDiagram.addMethod("ClassA", "method", "type"));
        Assertions.assertFalse(umlDiagram.addMethod("NonExistingClass", "method", "type")); // Adding method to non-existing class should return false
    }

    /**
     * Test case for deleting a method.
     */
    @Test
    void testDeleteMethod() {
        umlDiagram.addClass("ClassA");
        umlDiagram.addMethod("ClassA", "method", "type");
        Assertions.assertTrue(umlDiagram.deleteMethod("ClassA", "method"));
        Assertions.assertFalse(umlDiagram.deleteMethod("ClassA", "method")); // Deleting non-existing method should return false
    }

    /**
     * Test case for renaming a method.
     */
    @Test
    void testRenameMethod() {
        umlDiagram.addClass("ClassA");
        umlDiagram.addMethod("ClassA", "method", "type");
        Assertions.assertTrue(umlDiagram.renameMethod("ClassA", "method", "newMethod"));
        Assertions.assertFalse(umlDiagram.renameMethod("ClassA", "method", "newMethod")); // Renaming non-existing method should return false
    }

    /**
     * Test case for saving and loading to/from JSON.
     */
    @Test
    void testSaveLoadJSON() throws IOException {
        umlDiagram.addClass("ClassA");
        umlDiagram.addClass("ClassB");
        umlDiagram.addRelationship("ClassA", "ClassB", 1);
        String fileName = "test.json";
        Assertions.assertTrue(umlDiagram.saveToJSON(fileName));

        UMLDiagram loadedDiagram = new UMLDiagram();
        Assertions.assertTrue(loadedDiagram.loadFromJSON(fileName));

        Map<String, UMLClass> classes = loadedDiagram.getClassNameMapToName();
        System.out.println(classes.size());
        Assertions.assertEquals(0, classes.size());

        ArrayList<Relationship> relationships = loadedDiagram.getRelationships();
        Assertions.assertEquals(0, relationships.size());
    }

    /**
     * Test case for clearing the diagram.
     */
    @Test
    void testClear() {
        umlDiagram.addClass("ClassA");
        umlDiagram.addClass("ClassB");
        umlDiagram.addRelationship("ClassA", "ClassB", 3);
        umlDiagram.clear();
        Assertions.assertTrue(umlDiagram.getClasses().isEmpty());
        Assertions.assertTrue(umlDiagram.getRelationships().isEmpty());
    }

    /**
     * Test case for checking if a class exists.
     */
    @Test
    void testHasClass() {
        umlDiagram.addClass("ClassA");
        Assertions.assertTrue(umlDiagram.hasClass("ClassA"));
        Assertions.assertFalse(umlDiagram.hasClass("NonExistingClass"));
    }
    
    /**
     * Test case for checking if an empty class exists.
     */
    @Test
    void testHasClassEmpty() {
        umlDiagram.addClass("");
        Assertions.assertFalse(umlDiagram.hasClass(""));
    }
    
}
