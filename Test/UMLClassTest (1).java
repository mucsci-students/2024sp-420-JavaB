import static org.junit.Assert.*;
import org.junit.*;

public class UMLClassTest {
    
    private UMLClass umlClass;
    
    @Before
    public void setUp() {
        umlClass = new UMLClass("TestClass");
    }

    @Test
    public void testGetName() {
        assertEquals("TestClass", umlClass.getName());
    }
    

    @Test
    public void testAddMultipleAttributes() {
        assertTrue(umlClass.addAttribute("attr1", "String", "TestClass"));
        assertTrue(umlClass.addAttribute("attr2", "int", "TestClass"));
        assertEquals(2, umlClass.getAttributes().size());
    }

    @Test
    public void testAddDuplicateAttributes() {
        assertTrue(umlClass.addAttribute("attr1", "String", "TestClass"));
        assertFalse(umlClass.addAttribute("attr1", "int", "TestClass"));
        assertEquals(1, umlClass.getAttributes().size());
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
        umlClass.addAttribute("attr1", "String", "TestClass");
        umlClass.addAttribute("attr2", "int", "TestClass");
        assertTrue(umlClass.deleteAttributes());
        assertTrue(umlClass.getAttributes().isEmpty());
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
        assertFalse(umlClass.deleteAttribute("nonexistent_attr"));
    }

    @Test
    public void testDeleteNonExistingMethod() {
        assertFalse(umlClass.deleteMethod("nonexistent_method"));
    }

    @Test
    public void testRenameNonExistingAttribute() {
        assertFalse(umlClass.renameAttribute("nonexistent_attr", "newAttrName"));
    }

    @Test
    public void testRenameNonExistingMethod() {
        assertFalse(umlClass.renameMethod("nonexistent_method", "newMethodName"));
    }

    @Test
    public void testChangeNonExistingAttributeType() {
        assertFalse(umlClass.changeAttributeType("nonexistent_attr", "int"));
    }

    @Test
    public void testChangeNonExistingMethodType() {
        assertFalse(umlClass.changeMethodType("nonexistent_method", "int"));
    }

    @Test
    public void testGetNonExistingAttribute() {
        assertNull(umlClass.getAttribute("nonexistent_attr"));
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
    public void testAddAttribute() {
        assertTrue(umlClass.addAttribute("attr1", "String", "TestClass"));
        assertEquals(1, umlClass.getAttributes().size());
    }

    @Test
    public void testContainsAttribute() {
        umlClass.addAttribute("attr1", "String", "TestClass");
        assertTrue(umlClass.containsAttribute("attr1"));
        assertFalse(umlClass.containsAttribute("nonexistent_attr"));
    }

    @Test
    public void testDeleteAttribute() {
        umlClass.addAttribute("attr1", "String", "TestClass");
        assertTrue(umlClass.deleteAttribute("attr1"));
        assertFalse(umlClass.deleteAttribute("nonexistent_attr"));
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
        umlClass.addAttribute("attr1", "String", "TestClass");
        assertTrue(umlClass.renameAttribute("attr1", "newAttrName"));
        assertFalse(umlClass.renameAttribute("nonexistent_attr", "newAttrName"));
    }

    @Test
    public void testRenameMethod() {
        umlClass.addMethod("method1", "void");
        assertTrue(umlClass.renameMethod("method1", "newMethodName"));
        assertFalse(umlClass.renameMethod("nonexistent_method", "newMethodName"));
    }

    @Test
    public void testChangeAttributeType() {
        umlClass.addAttribute("attr1", "String", "TestClass");
        assertTrue(umlClass.changeAttributeType("attr1", "int"));
        assertFalse(umlClass.changeAttributeType("nonexistent_attr", "int"));
    }

    @Test
    public void testChangeMethodType() {
        umlClass.addMethod("method1", "void");
        assertTrue(umlClass.changeMethodType("method1", "int"));
        assertFalse(umlClass.changeMethodType("nonexistent_method", "int"));
    }

    @Test
    public void testDeleteAttributes() {
        umlClass.addAttribute("attr1", "String", "TestClass");
        assertTrue(umlClass.deleteAttributes());
    }

    @Test
    public void testDeleteMethods() {
        umlClass.addMethod("method1", "void");
        assertTrue(umlClass.deleteMethods());
    }

}