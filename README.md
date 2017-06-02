# html_analyzer

A solution for the ImmobilienScout 24 Technical Challenge.

### Running the project

There is a build file "service.sh" on the root folder that allows to build and run the project. To build the project just execute `service.sh dev_build`. To start the project, the command is `service.sh dev_run`. It starts a tomcat server in `locahost:8080`. 
Both the frontend and the api are served by the same project.
You can access the index page in `locahost:8080`  
_Note:_ You might have to make `service.sh` executable using `chmod`.

### Technology Stack

Java 8 with Spring Boot for the ultra-simplified project setting. Maven to build the project. Jsoup to parse HTML in the server, as suggested
in the challenge description.

 The frontend is done in vanilla JavaScript and CSS.
 
 ### Description of the solution
 
 The backend par consist of an RESTful api of with a single endpoint, available under `localhost:8080/api/analyze?url=...` through a GET request. The endpoint **requires** only one parameter (url), whose value must be a well formed encoded URL (including protocol). When this remote method is called, the server connects to the given URL, parses the HTML using Jsoup and executes a series of analytics as specified in the description of the challenge. Then it returns a JSON object like the followwing example:
 ```json
 {
    "url":"https%3A%2F%2Fgithub.com%2Flogin",
    "htmlVersion":{
        "name":"HTML5",
        "publicId":null
    },
    "title":"Sign in to GitHub · GitHub",
    "headers":{
        "h1":1,
        "h2":0,
        "h3":0,
        "h4":0,
        "h5":0,
        "h6":0
    },
    "linkCount":{
        "internal":10,
        "external":0
    },
    "hasLogin":true
}
```
 
 
 ### Assumptions and Design Decisions
