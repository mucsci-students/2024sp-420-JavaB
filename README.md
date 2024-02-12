Overview
The UML Editor Command Line Tool is a Java-based application designed to facilitate the creation and manipulation of Unified Modeling Language (UML) diagrams through the command line interface. This tool provides users with functionalities to create, edit, save, and load UML diagrams, aiding in software design and documentation.

Features
The UML Editor Command Line Tool offers the following features:

1. **Add Class**: Create new classes within the UML diagram.
2. **Delete Class**: Remove existing classes from the diagram.
3. **Rename Class**: Change the name of classes already present in the diagram.
4. **Add Relationship**: Establish connections between classes to represent relationships such as associations or inheritances.
5. **Delete Relationship**: Remove connections between classes.
6. **Add Attribute**: Attach attributes to classes to represent their properties.
7. **Delete Attribute**: Remove attributes from classes.
8. **Save/Load**: Store and retrieve diagrams for future reference or sharing.

Usage
Prerequisites
Before using the UML Editor Command Line Tool, ensure that you have the following installed:

- Java Development Kit (JDK)
- Gson library (for JSON serialization and deserialization)

 Running the Application
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
            Authors 
David Marquez
Natnael Thehaye
Simeon Belayneh
Ram Gurung 

