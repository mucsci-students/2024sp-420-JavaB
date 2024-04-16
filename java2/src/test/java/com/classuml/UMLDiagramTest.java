package com.classuml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import com.classuml.Model.*;
import java.util.List;

public class UMLDiagramTest {

    private UMLDiagram umlDiagram;

    @BeforeEach
    void setUp() {
        umlDiagram = new UMLDiagram();
        // Add some classes and relationships to the diagram
        umlDiagram.addClass("ClassA");
        umlDiagram.addClass("ClassB");
        umlDiagram.addClass("ClassC");
        umlDiagram.addRelationship("ClassA", "ClassB", 1);
        umlDiagram.addRelationship("ClassB", "ClassC", 1);
    }

    @Test
    public void testClear() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addClass("Class2");
        diagram.addRelationship("Class1", "Class2", 1);

        // Clear the diagram
        diagram.clear();

        // Check that the diagram is empty
        assertTrue(diagram.getClasses().isEmpty());
        assertTrue(diagram.getClassMapToRelation().isEmpty());
    }

    @Test
    public void testAddClass() {
        UMLDiagram diagram = new UMLDiagram();
        assertTrue(diagram.addClass("Class1"));
        assertTrue(diagram.hasClass("Class1"));
        assertFalse(diagram.addClass("Class1"));
        assertTrue(diagram.addClass("Class2"));
        assertTrue(diagram.getClasses().size() == 2);
    }

    @Test
    public void testDeleteClass() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addClass("Class2");
        diagram.deleteClass("Class1");
        assertFalse(diagram.hasClass("Class1"));
        assertTrue(diagram.getClasses().size() == 1);
        assertFalse(diagram.deleteClass("Class1"));
    }

    @Test
    public void testRenameClass() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addClass("Class2");
        assertTrue(diagram.renameClass("Class1", "Class3"));
        assertTrue(diagram.hasClass("Class3"));
        assertFalse(diagram.hasClass("Class1"));
        assertFalse(diagram.renameClass("Class4", "Class3"));
        assertTrue(diagram.renameClass("Class3", "Class4"));
    }

    @Test
    public void testAddRelationship() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addClass("Class2");
        assertTrue(diagram.addRelationship("Class1", "Class2", 1));
        assertTrue(diagram.getRelationships().size() == 1);
        assertFalse(diagram.addRelationship("Class1", "Class2", 1));
        assertTrue(diagram.deleteRelationship("Class1", "Class2"));
    }
    @Test
    public void testDeleteRelationship() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addClass("Class2");
        diagram.addRelationship("Class1", "Class2", 1);
        assertTrue(diagram.getRelationships().size() == 1);
        assertTrue(diagram.deleteRelationship("Class1", "Class2"));
        assertFalse(diagram.getRelationships().size() == 1);
        assertFalse(diagram.deleteRelationship("Class1", "Class2"));
    }
    @Test
    public void testAddField() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addField("Class1", "field1", "int");
        assertTrue(diagram.getClassByName("Class1").getFields().size() == 1);
        assertFalse(diagram.addField("Class1", "field1", "int"));
        assertFalse(diagram.addField("Class1", null, "int"));
        assertFalse(diagram.addField("Class1", "field1", null));
    }
    @Test
    public void testDeleteField() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addField("Class1", "field1", "int");
        diagram.deleteField("Class1", "field1");
        assertFalse(diagram.getClassByName("Class1").getFields().size() == 1);
        assertFalse(diagram.deleteField("Class1", "field1"));
        assertFalse(diagram.deleteField("Class1", null));
        assertFalse(diagram.deleteField(null, "field1"));
    }
    @Test
    public void testRenameField() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addField("Class1", "field1", "int");
        assertTrue(diagram.renameField("Class1", "field1", "field2"));
        assertTrue(diagram.getClassByName("Class1").getFields().get(0).getName().equals("field2"));
        assertTrue(diagram.renameField("Class1", "field2", "field1"));
        assertFalse(diagram.renameField("Class1", "field3", "field1"));
        assertFalse(diagram.renameField(null, "field1", "field2"));
        assertFalse(diagram.renameField("Class1", null, "field2"));
        assertFalse(diagram.renameField("Class1", "field1", null));
    }

    @Test
    void testChangeRelType() {
        // Test changing the type of a relationship
        assertTrue(umlDiagram.changeRelType("ClassA", "ClassB", 2));
        assertTrue(umlDiagram.getClassMapToRelation().get("ClassA-ClassB").getType() == 2);

        // Test changing the type of a relationship that doesn't exist
        assertFalse(umlDiagram.changeRelType("ClassA", "ClassC", 2));
        assertEquals(1, umlDiagram.getClassMapToRelation().get("ClassB-ClassC").getType());

        // Test changing the type of a relationship that has already been changed
        assertTrue(umlDiagram.changeRelType("ClassA", "ClassB", 1));
        assertTrue(umlDiagram.getClassMapToRelation().get("ClassA-ClassB").getType() == 1);
    }
    @Test
    public void testReplaceParameters() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addMethod("Class1", "method1", "void");
        diagram.addParameter("Class1", "method1", "param1", "int");
        diagram.addParameter("Class1", "method1", "param2", "String");

        assertTrue(diagram.replaceParameters("Class1", "method1", new String[]{"param1", "param3"}, new String[]{"float", "boolean"}));
        assertEquals(2, diagram.getClassByName("Class1").getMethodByName("method1").getParameters().size());
        assertEquals("float", diagram.getClassByName("Class1").getMethodByName("method1").getParameters().get(0).getType());
        assertEquals("boolean", diagram.getClassByName("Class1").getMethodByName("method1").getParameters().get(1).getType());

        assertFalse(diagram.replaceParameters("Class1", "method2", new String[]{"param1", "param3"}, new String[]{"float", "boolean"}));
    }
    @Test
    public void testUndo() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addClass("Class2");
        diagram.addRelationship("Class1", "Class2", 1);
        // Perform an action that can be undone
        diagram.deleteClass("Class1");
        assertTrue(diagram.getClasses().size() == 1);

        // Undo the action
        diagram.undo();
        assertEquals(2, diagram.getClasses().size());
        assertNotNull(diagram.getClassByName("Class1"));

        // Try to undo again, but there's nothing to undo
        diagram.undo();
        assertEquals(2, diagram.getClasses().size());
        assertNotNull(diagram.getClassByName("Class1"));
        assertFalse(diagram.getClassMapToRelation().containsKey("Class1-Class2"));
    }

    @Test
    public void testRedo() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addClass("Class2");
        diagram.addRelationship("Class1", "Class2", 1);
        // Perform an action that can be undone
        diagram.deleteClass("Class1");
        assertTrue(diagram.getClasses().size() == 1);

        // Undo the action
        diagram.undo();
        assertEquals(2, diagram.getClasses().size());
        assertNotNull(diagram.getClassByName("Class1"));

        // Redo the action
        diagram.redo();
        assertTrue(diagram.getClasses().size() == 1);
        assertNull(diagram.getClassByName("Class1"));

        // Try to redo again, but there's nothing to redo
        diagram.redo();
        assertTrue(diagram.getClasses().size() == 1);
        assertNull(diagram.getClassByName("Class1"));
    }
    @Test
    public void testRenameMethod() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addMethod("Class1", "method1", "void");
        // Rename the method
        assertTrue(diagram.renameMethod("Class1", "method1", "method2"));
        assertEquals("method2", diagram.getClassByName("Class1").getMethodByName("method2").getName());
    
        // Try to rename the method again with the same name
        assertTrue(diagram.renameMethod("Class1", "method2", "method2"));
        assertEquals("method2", diagram.getClassByName("Class1").getMethodByName("method2").getName());
    
        // Try to rename the method again with a different name
        assertTrue(diagram.renameMethod("Class1", "method2", "method3"));
        assertEquals("method3", diagram.getClassByName("Class1").getMethodByName("method3").getName());
    
        // Try to rename the method with a null name
        assertFalse(diagram.renameMethod("Class1", "method3", null));
        assertEquals("method3", diagram.getClassByName("Class1").getMethodByName("method3").getName());
    
        // Try to rename the method with an empty name
        assertFalse(diagram.renameMethod("Class1", "method3", ""));
        assertEquals("method3", diagram.getClassByName("Class1").getMethodByName("method3").getName());
    
        // Try to rename the method with a non-existent class
        assertFalse(diagram.renameMethod("Class2", "method3", "method4"));
        assertNull(diagram.getClassByName("Class2"));
    }
    @Test
    public void testRenameParameter() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        UMLClass clazz = diagram.getClassByName("Class1");
        clazz.addMethod("method1", "void");
        clazz.addParameter("method1", "param1", "int");
        // Rename the parameter
        assertTrue(diagram.renameParameter("Class1", "method1", "param1", "p1"));
        Parameter p1 = new Parameter("p1", "int");
        Parameter p2 = new Parameter("param2", "int");
        assertEquals("p1", p1.getName());
    
        // Try to rename the parameter again with the same name
        assertFalse(diagram.renameParameter("Class1", "method1", "p1", "p1"));
        assertEquals("p1", p1.getName());
    
        // Try to rename the parameter again with a different name
        assertTrue(diagram.renameParameter("Class1", "method1", "p1", "param2"));
        assertEquals("param2", p2.getName());
    
        // Try to rename the parameter with a null name
        assertFalse(diagram.renameParameter("Class1", "method1", "param2", null));
        assertEquals("param2", p2.getName());
    
        // Try to rename the parameter with an empty name
        assertFalse(diagram.renameParameter("Class1", "method1", "param2", ""));
        assertEquals("param2", p2.getName());
    
        // Try to rename the parameter with a non-existent method
        assertFalse(diagram.renameParameter("Class1", "method2", "param2", "p3"));
        assertNull(diagram.getClassByName("Class1").getMethodByName("method2"));
    
        // Try to rename the parameter with a non-existent class
        assertFalse(diagram.renameParameter("Class2", "method1", "param2", "p3"));
        assertNull(diagram.getClassByName("Class2"));
    }
    @Test
    public void testGetRelationship() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        diagram.addClass("Class2");
        diagram.addClass("Class3");
        diagram.addRelationship("Class1", "Class2", 1);
        diagram.addRelationship("Class2", "Class3", 2);
    // Get the relationships for Class1
    List<Relationship> rels1 = diagram.getRelationshipsForClass("Class1");
    assertNotNull(rels1);
    assertEquals(1, rels1.size());
    assertEquals(1, rels1.get(0).getType());

    // Get the relationships for Class2
    List<Relationship> rels2 = diagram.getRelationshipsForClass("Class2");
    assertNotNull(rels2);
    assertEquals(2, rels2.size());
    assertEquals(1, rels2.get(0).getType());
    assertEquals(2, rels2.get(1).getType());
    }
    @Test
    public void testDeleteMethod() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        UMLClass clazz = diagram.getClassByName("Class1");
        clazz.addMethod("method1", "void");
        clazz.addParameter("method1", "param1", "int");
        // Delete the method
        assertTrue(diagram.deleteMethod("Class1", "method1"));
        assertNull(diagram.getClassByName("Class1").getMethodByName("method1"));
    
        // Try to delete the method again
        assertFalse(diagram.deleteMethod("Class1", "method1"));
    
        // Try to delete a non-existent method
        assertFalse(diagram.deleteMethod("Class1", "method2"));
    
        // Try to delete a method with a non-existent class
        assertFalse(diagram.deleteMethod("Class2", "method1"));
    }
    
    @Test
    public void testDeleteParameter() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        UMLClass clazz = diagram.getClassByName("Class1");
        clazz.addMethod("method1", "void");
        clazz.addParameter("method1", "param1", "int");
        // Delete the parameter
        assertTrue(diagram.deleteParameter("Class1", "method1", "param1"));
        List<Parameter> ps = diagram.getClassByName("Class1").getMethodByName("method1").getParameters();
        Parameter temp = null;
        for(Parameter p : ps){
            if (p.getName().equals("Class1")){
                temp = p;
            }
        }
        assertNull(temp);
    
        // Try to delete the parameter again
        assertFalse(diagram.deleteParameter("Class1", "method1", "param1"));
    
        // Try to delete a non-existent parameter
        assertFalse(diagram.deleteParameter("Class1", "method1", "param2"));
    
        // Try to delete a parameter with a non-existent method
        assertFalse(diagram.deleteParameter("Class1", "method2", "param1"));
    
        // Try to delete a parameter with a non-existent class
        assertFalse(diagram.deleteParameter("Class2", "method1", "param1"));
    }
    @Test
    public void testSetPosition() {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Class1");
        UMLClass clazz = diagram.getClassByName("Class1");
        clazz.position = new Point(100, 100);
        // Set the position
        diagram.setPosition("Class1", new Point(200, 200));
        Point expected = new Point(200, 200);
        assertEquals(expected, diagram.getClassByName("Class1").position);

        // Try to set the position again
        diagram.setPosition("Class1", new Point(200, 200));
        assertEquals(expected, diagram.getClassByName("Class1").position);

        // Try to set the position of a non-existent class
        assertFalse(diagram.setPosition("Class2", new Point(200, 200)));
    }
    @Test
    public void testUndoEmpty()
    {
        UMLDiagram diagram = new UMLDiagram();
        diagram.helperMethodForHelp();
        diagram.addClass("Class1");
        diagram.undo();
        assertFalse(diagram.undo());

    }
    @Test
    public void testRenameClassNull()
    {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("Aa");
        diagram.addClass("bb");
        diagram.addRelationship("Aa", "bb", 1);
        assertFalse(diagram.renameClass(null, null));
        assertTrue(diagram.renameClass("Aa", "ee"));
        assertTrue(diagram.renameClass("bb", "ea"));
    }
    @Test 
    public void testRelNull()
    {
        UMLDiagram diagram = new UMLDiagram();
        assertFalse(diagram.addRelationship(null, null, 0));
        assertFalse(diagram.changeRelType(null, null, 0));
    }
    @Test 
    public void testAddNull()
    {
        UMLDiagram diagram = new UMLDiagram();
        assertFalse(diagram.addField(null, "a", "b"));

    }
    @Test 
    public void testRenameField2()
    {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("a");
        diagram.addField("a", "b", "c");
        diagram.addField("a", "c", "c");
        assertFalse(diagram.renameField("a", "b", "c"));
    }
    @Test
    public void testAddMethod()
    {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("a");
        diagram.addMethod("a", "b", "c");
        assertFalse(diagram.addMethod("a", "b", "c"));
        assertFalse(diagram.addMethod("c", "a", "c"));
    }
    @Test
    public void testAddParamBad()
    {
        UMLDiagram diagram = new UMLDiagram();
        assertFalse(diagram.addParameter(null, null, null, null));
    }
    @Test
    public void testClearParams()
    {
        UMLDiagram diagram = new UMLDiagram();
        assertFalse(diagram.clearParameters(null, null));
    }
    @Test
    public void testDeleteClass2()
    {
        UMLDiagram diagram = new UMLDiagram();
        diagram.addClass("a");
        diagram.addClass("b");
        diagram.addRelationship("a", "b", 1);
        assertTrue(diagram.deleteClass("b"));
        diagram.addClass("c");
        diagram.addRelationship("c", "a", 2);
        assertTrue(diagram.deleteClass("c"));
        diagram.addClass("d");
        diagram.addRelationship("a", "d", 1);
        diagram.addClass("e");
        assertTrue(diagram.deleteClass("e"));
        diagram.deleteClass("a");
    }
}