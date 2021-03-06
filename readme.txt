/*
 * $Author: florentina.ardean $
 * $Revision: 0.0.1 $
 * $Date: 201616/04/14 $
 *
 * ====================================================================
 *
 */

//////////////////////////////////////////////////////////////////////////////////
//      SUDOKU ONLINE  -  $Date: 201616/04/14 $
//////////////////////////////////////////////////////////////////////////////////



0. Table of Contents
==================================================================================

0. Table of Contents
1. Sudoku Introduction
2. Build prerequisites
3. Build and deploy instructions
4. Run instructions

1. Sudoku Introduction
==================================================================================
RESTful web service that validates successive moves on a Sudoku board. 
To check your moves press "Validates moves" button.
If the numbers are valid they are colored blue.
If the numbers are not valid they are colored in RED.

You can find a very detailed explanation of the Sudoku rules at
https://en.wikipedia.org/wiki/Sudoku (first paragraph).

2. Build prerequisites
==================================================================================
You need to have/install: 
-git	- tested with git 2.7.1.windows.2
-maven 	- tested with apache-maven-3.0.5
-tomcat - tested with apache-tomcat-8.0.30 (or any other web server)


3. Build and deploy instructions
==================================================================================
Get the project from the repository to your <project_location>:
$git clone https://florentina_ardean@bitbucket.org/florentina_ardean/sudoku.git

Go to <project_location>/sudoku (where the pom.xml file is located)
Run command: mvn install
If everything goes ok you should have the war file: <project_location>/sudoku/target/sudoku-0.0.1-SNAPSHOT.war

Deploy the war file: sudoku-0.0.1-SNAPSHOT.war to your web server.
For Tomcat: copy war file to <server_location>/webapps.

Start your web server.
For tomcat: 
- you can setup up the http port in <server_location>/config/server.xml file to 8080 (<Connector port="8080" protocol="HTTP/1.1" ...) [optional step] 
- go to <server_location>/bin
- run startup.bat 
- to check that the server is up and running, go to: http://<host>:<port_number>/ (for localhost: http://localhost:8080)


4. Run instructions
==================================================================================
Start playing sudoku:

http://<host>:<port_number>/sudoku-0.0.1-SNAPSHOT/showBoard.jsp 
(http://localhost:8080/sudoku-0.0.1-SNAPSHOT/showBoard.jsp - for localhost)
