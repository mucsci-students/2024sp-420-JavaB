# Java2
# Overview:

The UML Editor is a Java-based software tool designed to facilitate the creation and manipulation of Modeling Language [UML EDITOR] diagrams. 
This application provides users with a platform to visually represent the structure and relationships of components within a software system, 
aiding in design, analysis, and documentation.

# Features:

Create, delete, and manipulate classes with fields, methods, and relationships.
Save and load to standar JSON format.

# State to run the program:

# Design Patterns Used
- Memento is implemented in our redo/undo.
- Abstract is implemented in our tab completion.
- Oberserver is implemented in our gui to notify the diagram when methods are called.
- MVC is implemented throughout our project.

# 1.Requirements: 

- Java Development Kit (JDK) 17 or higher https://www.oracle.com/java/technologies/downloads/#jdk21-windows
- Maven https://maven.apache.org/download.cgi
- Git Installed https://github.com/git-guides/install-git
- Gson library (for JSON serialization and deserialization)

# 2. Running the Application
- Git clone the repository https://github.com/mucsci-students/2024sp-420-JavaB.git
- Make sure you run all of the following commands from the java2 directory
- mvn clean install
- mvn clean package spring-boot:repackage
# Slashes are backwards on linux machines
- To run the gui use java -jar .\target\java2-1.0-SNAPSHOT.jar
- To run the cli use java -jar .\target\java2-1.0-SNAPSHOT.jar cli
- To run the tests use mvn clean test
- Run specific tests by using "mvn test -Dtest=TestClassName#MethodName" where TestClassName is the name of the file you want to run and Methodname is the name of the test you want to run, you can also use * as the MethodName to run all tests of that class.

# 3. Testing code coverage
- In the java2 director run mvn clean jacoco:prepare-agent install jacoco:report
- As well as mvn jacoco:report
- Then open 2024sp-420-JavaB\java2\target\site\jacoco\index.html in your web browser to find the total test coverage.

# Authors: 
   Eric Almonrode, Joshua Lease, Cullen Kurtz, Vasilis Bougiamas
