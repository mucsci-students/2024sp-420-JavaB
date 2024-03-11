

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Model.UMLClass;

public class UMLClassTest {
    
    private UMLClass umlClass;
    private UMLClass umlCLass2;
    private UMLClass umlCLass3;
    
    @BeforeAll
    public void setUp() {
        umlClass = new UMLClass("TestClass");
    }
    
    @Test
    public void testAddNullClassName() {
    	String nule = null;
    	this.umlCLass2 = new UMLClass(nule);
    	assertFalse(umlCLass2.getName() == null);
    }
   
    @Test
    public void testAddEmptyClassString() {
    	String nule = "";
    	this.umlCLass3 = new UMLClass(nule);
    	assertFalse(umlCLass3.getName() == "");
    }
    
    
    @Test
    public void testGetName() {
        assertEquals("TestClass", umlClass.getName());
    }
    

    @Test
    public void testAddNullClassAttribute() {
    	String nule = null;
    	this.umlCLass2 = new UMLClass("TestClass2");
    	assertFalse(umlCLass2.addField(nule, "String"));
    }
   
    @Test
    public void testAddEmptyClassAttribute() {
    	this.umlCLass3 = new UMLClass("TestClass3");
    	assertFalse(umlCLass3.addField("", "String"));
    }
   
    @Test
    public void testAddNullClassAttributeType() {
    	String nule = null;
    	this.umlCLass2 = new UMLClass("TestClass2");
    	assertFalse(umlCLass2.addField("attr1", nule));
    }
    
    @Test
    public void testAddEmptyClassAttributeType() {
    	this.umlCLass3 = new UMLClass("TestClass3");
    	assertFalse(umlCLass3.addField("attr1", ""));
    }
    
    @Test
    public void testAddNullClassAttributeClass() {
    	String nule = null;
    	this.umlCLass2 = new UMLClass("TestClass2");
    	assertFalse(umlCLass2.addField("attr1", "String"));
    }
    
    @Test
    public void testAddEmptyClassAttributeClass() {
    	this.umlCLass3 = new UMLClass("TestClass3");
    	assertFalse(umlCLass3.addField("attr1", "String"));
    }
    
    @Test
    public void testAddMultipleAttributes() {
        assertTrue(umlClass.addField("attr1", "String"));
        assertTrue(umlClass.addField("attr2", "int"));
    }

    @Test
    public void testAddDuplicateAttributes() {
        assertTrue(umlClass.addField("attr1", "String"));
        assertFalse(umlClass.addField("attr1", "int"));
    }

    @Test
    public void testAddMultipleMethods() {
        assertTrue(umlClass.addMethod("method1", "void"));
        assertTrue(umlClass.addMethod("method2", "String"));
        assertEquals(2, umlClass.getMethods().size());
    }

    @Test
    public void testAddDuplicateMethods() {
        assertTrue(umlClass.addMethod("method1", "void"));
        assertFalse(umlClass.addMethod("method1", "int"));
        assertEquals(1, umlClass.getMethods().size());
    }

    @Test
    public void testDeleteAllAttributes() {
        umlClass.addField("attr1", "String");
        umlClass.addField("attr2", "int");
        assertTrue(umlClass.deleteField());
    }

    @Test
    public void testDeleteAllMethods() {
        umlClass.addMethod("method1", "void");
        umlClass.addMethod("method2", "String");
        assertTrue(umlClass.deleteMethods());
        assertTrue(umlClass.getMethods().isEmpty());
    }

    @Test
    public void testDeleteNonExistingAttribute() {
        assertFalse(umlClass.deleteField("nonexistent_attr"));
    }

    @Test
    public void testDeleteNonExistingMethod() {
        assertFalse(umlClass.deleteMethod("nonexistent_method"));
    }

    @Test
    public void testRenameNonExistingAttribute() {
        assertFalse(umlClass.renameField("nonexistent_attr", "newAttrName"));
    }

    @Test
    public void testRenameExistingAttributeNull() {
    	String nole = null;
        assertFalse(umlClass.renameField("TestClass", nole));
    }
    
    @Test
    public void testRenameExistingAttributeToSame() {
        assertFalse(umlClass.renameField("TestClass", "TestClass"));
    }
    
    @Test
    public void testRenameExistingAttributeToNull() {
    	String nole = null;
        assertFalse(umlClass.renameField("TestClass", nole));
    }
    
    @Test
    public void testRenameExistingAttributetoEmpty() {
        assertFalse(umlClass.renameField("TestClass", ""));
    }
    
    @Test
    public void testRenameNonExistingMethod() {
        assertFalse(umlClass.renameMethod("nonexistent_method", "newMethodName"));
    }

    @Test
    public void testChangeNonExistingAttributeType() {
        assertFalse(umlClass.changeFieldType("nonexistent_attr", "int"));
    }

    @Test
    public void testChangeNonExistingMethodType() {
        assertFalse(umlClass.changeMethodType("nonexistent_method", "int"));
    }

    @Test
    public void testGetNonExistingMethod() {
        assertNull(umlClass.getMethod("nonexistent_method"));
    }

    @Test
    public void testSetName() {
        umlClass.setName("NewTestClass");
        assertEquals("NewTestClass", umlClass.getName());
    }

    @Test
    public void testContainsAttribute() {
        umlClass.addField("attr1", "String");
        assertTrue(umlClass.containsField("attr1"));
        assertFalse(umlClass.containsField("nonexistent_attr"));
    }

    @Test
    public void testDeleteAttribute() {
        umlClass.addField("attr1", "String");
        assertTrue(umlClass.deleteField("attr1"));
        assertFalse(umlClass.deleteField("nonexistent_attr"));
    }

    @Test
    public void testAddMethod() {
        assertTrue(umlClass.addMethod("method1", "void"));
        assertEquals(1, umlClass.getMethods().size());
    }

    @Test
    public void testContainsMethod() {
        umlClass.addMethod("method1", "void");
        assertTrue(umlClass.containsMethod("method1"));
        assertFalse(umlClass.containsMethod("nonexistent_method"));
    }

    @Test
    public void testDeleteMethod() {
        umlClass.addMethod("method1", "void");
        assertTrue(umlClass.deleteMethod("method1"));
        assertFalse(umlClass.deleteMethod("nonexistent_method"));
    }

    @Test
    public void testRenameAttribute() {
        umlClass.addField("attr1", "String");
        assertTrue(umlClass.renameField("attr1", "newAttrName"));
        assertFalse(umlClass.renameField("nonexistent_attr", "newAttrName"));
    }

    @Test
    public void testRenameMethod() {
        umlClass.addMethod("method1", "void");
        assertTrue(umlClass.renameMethod("method1", "newMethodName"));
        assertFalse(umlClass.renameMethod("nonexistent_method", "newMethodName"));
    }

    @Test
    public void testChangeAttributeType() {
        umlClass.addField("attr1", "String");
        assertTrue(umlClass.changeFieldType("attr1", "int"));
        assertFalse(umlClass.changeFieldType("nonexistent_attr", "int"));
    }

    @Test
    public void testChangeMethodType() {
        umlClass.addMethod("method1", "void");
        assertTrue(umlClass.changeMethodType("method1", "int"));
        assertFalse(umlClass.changeMethodType("nonexistent_method", "int"));
    }

    @Test
    public void testDeleteAttributes() {
        umlClass.addField("attr1", "String");
        assertTrue(umlClass.deleteField());
    }

    @Test
    public void testDeleteMethods() {
        umlClass.addMethod("method1", "void");
        assertTrue(umlClass.deleteMethods());
    }

    
}
