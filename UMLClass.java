import java.util.ArrayList;

public class UMLClass {

    public String className;
    public int relationNum;
    public int attributeCount;
    public ArrayList<String> attributes = new ArrayList<String>();
    public int classId;
    public ArrayList<String> relationShips = new ArrayList<String>();
    
    /*
    *   Default constructor - creates a new UMLClass object with empty fields and counters set to zero.
    *   @params none
    *   ~classId is set to -1 since it would be invalid until className is updated.
    */

    public UMLClass(){                                              
        className = "";
        relationNum = relationShips.size();
        attributeCount = attributes.size();
        classId = -1;
    }

    /*
    *   Constructor that creates a new UMLClass object using only a valid string as a parameter.
    *   String cannot be null nor have empty spaces.
    *   @param  String - a name that will be associated with the object
    *   ~Counts and relations/attributes will remain the same, 
    *   ~classId will be updated with the hash code of the className.
    */

    public UMLClass(String name){                              
        if (name != null || !(name.contains(" "))){
            className = name;
            relationNum = relationShips.size();
            attributeCount = attributes.size();
            classId = name.hashCode();
        }
        else {
            System.out.println("Name is not valid.");
            }
    }

    /*
    *   Constructor that creates a new UMLClass object using a name and a valid array list of type 
    *   string for either the relationships or the attributes.
    *   String name cannot be null nor have empty spaces.
    *   ArrayList<String> newList cannot be empty
    *   
    *   String decision cannot be empty, must be either "r","relation",or "Relation" to update relationships
    *   or 'a',"attribute", or "Attribute" to update attributes. *****                            <-----
    *   
    *   @param String - a name that will be associated with the object.
    *   @param ArrayList<String> - an array list of strings that will hold the names of other objects.
    *
    *   ~Counts of relations or attributes will be updated depending on decision.
    *   ~classId will be updated with the hash code of the className.
    *   
    *   Error messages will be displayed if any param is invalid.
    */

    public UMLClass(String name, ArrayList<String> newList, String decision){                 
        
        if ((name != null) || !(name.contains(" ")) || (newList.size() > 0)){
        
        if (decision.equals("relation") || decision.equals("Relation") || decision.equals("r")){
            className = name;
            relationNum = newList.size();
            attributeCount =  attributes.size();
            classId = name.hashCode();
            relationShips = newList;
        }
        else if (decision.equals("attribute") || decision.equals("Attribute") || decision.equals("a")){
            className = name;
            relationNum = this.relationShips.size();
            attributeCount =  newList.size();
            classId = name.hashCode();
            attributes = newList;
        }

        else {
            System.out.println("Error: Parameter decision was not within expected options. ");
            }

        }

        else {
            System.out.println("Error: Name is not valid and/or ArrayList passed is empty.");
            }
        }
        
    /*
    *   Constructor that creates a new UMLClass object using a name and two valid array list of type 
    *   string for the relationships and the attributes.
    *   String name cannot be null nor have empty spaces.
    *   ArrayList<String> newList cannot be empty
    *
    *   RELATIONSHIPS ARRAYLIST MUST BE SECOND PARAM, AND ATTRIBUTES ARRAYLIST 3RD PARAM*** <---
    *   
    *   @param String - a name that will be associated with the object.
    *   @param ArrayList<String> - an array list of strings that will hold the names of other objects.
    *   @param ArrayList<String> - an array list of strings that will hold the names of other objects.
    *
    *   ~Counts of relations and attributes will be updated depending on passed params.
    *   ~classId will be updated with the hash code of the className.
    *   
    *   Error messages will be displayed if any param is invalid.
    */

    public UMLClass(String name, ArrayList<String> relation, ArrayList<String> attributesL){
        if (name != null || !(name.contains(" ")) || (relation.size() > 0) || (attributesL.size() > 0)){
        className = name;
        relationNum = relation.size();
        attributeCount =  attributesL.size();
        classId = name.hashCode();
        relationShips = relation;
        attributes = attributesL;
        }
        else {
            System.out.println("Error: Name is not valid and/or an ArrayList passed is empty.");
            }
    }

    /*
    *   returns className of current object.
    *   No @params
    */

    public String getName(){
        return this.className;
    }

    /*
    *   returns relationNum of current object.
    *   No @params
    */

    public int getRelationNum(){
        return this.relationNum;
    }
   
    /*
    *   returns attributeCount of current object.
    *   No @params
    */

    public int getattCount(){
        return this.attributeCount;
    }

    /*
    *   Getter that returns classId of current object.
    *   No @params
    */

    public int getclassID(){
        return this.classId;
    }

    /*
    *   returns relationShips array of current object.
    *   No @params
    */

    public ArrayList<String> getRelations(){
        return this.relationShips;
    }

    /*
    *   returns attributes array of current object.
    *   No @params
    */

    public ArrayList<String> getAttrib(){
        return this.attributes;
    }

    
    //Renaming portion 

    /*
    *   Rename method - renames a UMLClass while maintaining its data.
    *   Both parameters must be a non empty nor null string.
    *   @param String - current name of object.
    *   @param String - new name for object.
    *   ~Creates a new object of UMLClass with the new name/classId and maintains all other fields.
    */

    public UMLClass renameClass (String oldName,String newName){       //Needs rework when list is done and relationships
      
        UMLClass newClass = new UMLClass(newName, this.relationShips, this.attributes);
        return newClass;


    }


    //Delete class portion

    /*
    *   Delete method - changes all values of UMLClass object to default;
    *   Both parameters must be a non empty nor null string.
    *   @param String - name of object.
    *   ~Sets counts to 0, array to empty arrays, name to null, and classId tp -1.
    */

    public void deleteClass (String name){                         //Needs rework when list and relationships are done
        ArrayList<String> emptAtt = new ArrayList<String>();
        ArrayList<String> emptRel = new ArrayList<String>();
        this.className = " ";
        this.classId = -1;
        this.attributeCount = 0;
        this.attributes = emptAtt;
        this.relationNum = 0;
        this.relationShips = emptRel;
        name = null;
    }

}