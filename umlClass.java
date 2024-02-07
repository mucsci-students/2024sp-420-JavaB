

public class umlClass {

    public String className;
    public int relationNum;
    public int attributeCount;
    public String [] attributes;
    public int classId;
    public int [] relationShips;
    
    /*
    *   Default constructor - creates a new umlClass object with empty fields and counters set to zero.
    *   @params none
    *   ~classId is set to -1 since it would be invalid until className is updated.
    */

    public umlClass(){                                              
        className = "";
        relationNum = relationShips.length;
        attributeCount = attributes.length;
        classId = -1;
    }

    /*
    *   Constructor that creates a new umlClass object using only a valid string as a parameter.
    *   String cannot be null nor have empty spaces.
    *   @param  String - a name that will be associated with the object
    *   ~Counts and relations/attributes will remain the same, 
    *   ~classId will be updated with the hash code of the className.
    */

    public umlClass(String name){                              
        if (name != null && !(name.contains(" "))){
        className = name;
        relationNum = relationShips.length;
        attributeCount = attributes.length;
        classId = name.hashCode();
        }
    }

    /*
    *   Constructor that creates a new umlClass object using a valid string for the name 
    *   and an array of ints for relation.
    *   String cannot be null nor have empty spaces.
    *   Int array length cannot be less than or equal to zero.
    *   @param String - a name that will be associated with the object.
    *   @param int [] - an array of ints that will hold the classId's of other objects.
    *   ~Counts and relations/attributes will remain the same, 
    *   ~classId will be updated with the hash code of the className.
    *   ~relation count and relationShips are updated using relation.
    */

    public umlClass(String name, int [] relation){                 
        if (name != null && !(name.contains(" ")) && (relation.length > 0)){
        className = name;
        relationNum = relation.length;
        attributeCount =  attributes.length;
        classId = name.hashCode();
        relationShips = relation;
        }
    }

    /*
    *   Constructor that creates a new umlClass object using a valid string for the name
    *   and an array of strings for attributes.
    *   String cannot be null nor have empty spaces.
    *   String array length cannot be less than or equal to zero.
    *   @param String - a name that will be associated with the object.
    *   @param String [] - an array of attributes.
    *   ~Counts and relations/attributes will remain the same, 
    *   ~classId will be updated with the hash code of the className.
    *   ~relation count and relationShips will remain the same.
    *   ~attributes and their count will be updated using attributesL.
    */

    public umlClass(String name, String [] attributesL){    
        if (name != null && !(name.contains(" ")) && (attributesL.length > 0)){
        className = name;
        relationNum = this.relationShips.length;
        attributeCount =  attributesL.length;
        classId = name.hashCode();
        attributes = attributesL;
        }
    }

    /*
    *   Constructor that creates a new umlClass object using a valid string for the name, 
    *   an array of ints for relation, and an array of strings for attributes.
    *   String cannot be null nor have empty spaces.
    *   Int array length cannot be less than or equal to zero.
    *   String array length cannot be less than or equal to zero.
    *   @param String - a name that will be associated with the object.
    *   @param int [] - an array of ints that will hold the classId's of other objects.
    *   @param String [] - an array of attributes.
    *   ~Counts and relations/attributes will remain the same, 
    *   ~classId will be updated with the hash code of the className.
    *   ~relation count and relationShips are updated using relation.
    *   ~attributes and their count will be updated using attributesL.
    */

    public umlClass(String name, int [] relation, String [] attributesL){
        if (name != null && !(name.contains(" ")) && (relation.length > 0) && (attributesL.length > 0)){
        className = name;
        relationNum = relation.length;
        attributeCount =  attributesL.length;
        classId = name.hashCode();
        relationShips = relation;
        attributes = attributesL;
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

    public int [] getRelations(){
        return this.relationShips;
    }

    /*
    *   returns attributes array of current object.
    *   No @params
    */

    public String [] getAttrib(){
        return this.attributes;
    }

    
    //Renaming portion 

    /*
    *   Rename method - renames a umlClass while maintaining its data.
    *   Both parameters must be a non empty nor null string.
    *   @param String - current name of object.
    *   @param String - new name for object.
    *   ~Creates a new object of umlClass with the new name/classId and maintains all other fields.
    */

    public umlClass renameClass (String oldName,String newName){       //Needs rework when list is done and relationships
      
        umlClass newClass = new umlClass(newName, this.relationShips, this.attributes);
        return newClass;


    }


    //Delete class portion

    /*
    *   Delete method - changes all values of umlClass object to default;
    *   Both parameters must be a non empty nor null string.
    *   @param String - name of object.
    *   ~Sets counts to 0, array to empty arrays, name to null, and classId tp -1.
    */

    public void deleteClass (String name){                         //Needs rework when list and relationships are done
        String [] empt = {};
        int [] empt2 = {};
        this.className = " ";
        this.classId = -1;
        this.attributeCount = 0;
        this.attributes = empt;
        this.relationNum = 0;
        this.relationShips = empt2;
        name = null;
    }

}