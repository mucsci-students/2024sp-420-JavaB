# NRDS
# Overview:

The UML Editor is a Java-based software tool designed to facilitate the creation and manipulation of Modeling Language [UML EDITOR] diagrams. 
This application provides users with a platform to visually represent the structure and relationships of components within a software system, 
aiding in design, analysis, and documentation.

# Features:
The UML Editor Command Line Tool offers the following features:

**Add class**: create new classes.
**Delete class**: Remove existing classes.
**Rename class**: Change the name of classes.
**List Class**: List a class and its attributes and methods.
**List Classes**: List all the classes in the current program.
**Add Relationship**: Establish connections between classes.
**Delete Relationship**: Remove connections between classes.
**List Relationships**: Show all relationship between existing classes.
**Add Attribute**: Attach attributes to classes.
**Rename Attribute**: Change the name of an existing attribute.
**Delete Attribute**: Remove attribute from classes.
**Add Method**: Add a method to an existing class.
**Rename Method**: Change the name of an existing method.
**Delete Method**: Delete a method from an existing class.
**Save**: Current state of the program into a file.
**Load**: A file into the program.
**Help**: List of commands the program understands.
**Exit**: Exit program.

# State to run the program:

# 1.Requirements: 

- Java Development Kit (JDK)
- Gson library (for JSON serialization and deserialization)

# 2. Running the Application
To run the UML Editor Command Line Tool,
Run the main class UMLCli.

Using Gson
[Gson](https://github.com/google/gson) is a Java library that can be used to convert Java Objects into their JSON representation and vice versa. In the context of the UML Editor Command Line Tool, Gson is utilized for saving and loading diagrams in JSON format.

Adding Gson Dependency
If you're using Maven, you can add Gson as a dependency in your `pom.xml` file:
-- xml -- 
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.8</version>
</dependency>
If you're using Gradle, add the following line to your build.gradle file:
-- implementation 'com.google.code.gson:gson:2.8.8' ---


# Authors: 
   David Marquez, Natnael Thehaye, Simeon Belayneh, Ram Gurung
