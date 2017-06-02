# html_analyzer

A solution for the ImmobilienScout 24 Technical Challenge.

### Running the project

There is a build file "service.sh" on the root folder that allows to build and run the project. To build the project just execute
`service.sh dev_build`. To start the project, the command is `service.sh dev_run`. It starts a tomcat server in `locahost:8080`. 
Both the frontend and the api are served by the same project.
You can access the index page in `locahost:8080`
_Note:_ You might have to make `service.sh` executable using `chmod`.

### Technology Stack

Java 8 with Spring Boot for the ultra-simplified project setting. Maven to build the project. Jsoup to parse HTML in the server, as suggested
in the challenge description.

 The frontend is done in vanilla JavaScript and CSS.
 
 ### Description of the solution
 
 
 
 ### Assumptions and Design Decisions