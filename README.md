# NRDS
# Overview:

The UML Editor is a Java-based software tool designed to facilitate the creation and manipulation of Modeling Language [UML EDITOR] diagrams. 
This application provides users with a platform to visually represent the structure and relationships of components within a software system, 
aiding in design, analysis, and documentation.

# Features:

Create, delete, and manipulate classes with fields, methods, and relationships.
Save and load to standar JSON format.

# State to run the program:

# 1.Requirements: 

- Java Development Kit (JDK)
- Gradle
- Gson library (for JSON serialization and deserialization)
- Manually add JSON 2.10.1 into Junit https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1

# 2. Running the Application
To run the UML Editor Command Line Tool,
Run the main class UMLCli.

# 3. Using Gson
[Gson](https://github.com/google/gson) is a Java library that can be used to convert Java Objects into their JSON representation and vice versa. In the context of the UML Editor Command Line Tool, Gson is utilized for saving and loading diagrams in JSON format.

# 4. Adding Gson Dependency
Use Gradle, add the following dependencies to your build.gradle file:
    implementation 'com.google.guava:guava:31.1-jre'
    implementation group: 'com.google.code.gson', name: 'gson', version: '2.10.1'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'

# 5. How to Run
Open terminal, open the file directory where the zip was saved.
Type "gradle build"
Type "gradle run" to run program

# Authors: 
   Natnael Thehaye, David Marquez, Simeon Belayneh, Ram Gurung
