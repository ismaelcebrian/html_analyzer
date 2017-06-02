# html_analyzer

A solution for the ImmobilienScout 24 Technical Challenge.

### Running the project

There is a build file "service.sh" on the root folder to build and run the project. To build the project just execute `service.sh dev_build`. To start the project, the command is `service.sh dev_run`. It starts a tomcat server in `locahost:8080`. 
Both the frontend and the api are served by the same project.
You can access the index page in `locahost:8080`  
_Note:_ You might have to make `service.sh` executable using `chmod`.

### Technology Stack

Java 8 with Spring Boot for the ultra-simplified project setting. Maven to build the project. Jsoup to parse HTML in the server, as suggested
in the challenge description.

 The frontend is done in vanilla JavaScript and CSS.
 
 ### Description of the solution
 
 The backend part consist of a RESTful api with a single endpoint, available under `localhost:8080/api/analyze?url=...` through a GET request. The endpoint **requires** only one parameter (url), whose value must be a well formed encoded URL (including protocol). When this remote method is called, the server connects to the given URL, parses the HTML using Jsoup and executes a series of analytics as specified in the description of the challenge. Then it returns a JSON object like the following example:
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
 This json is used by the frontend to create a table to display the results.
 
 ### Assumptions and Design Decisions
 
 **Counting Links**: Only `<a>` elements with a href attribute are considered, and only if the value of href is well formed url (or can be parsed to a well formed, in the case of relative links) with the protocols "http" or "https". A link is considered a internal link if the domain is the same as the one in the provided url. Subdomains are taken into account, both if the domain is of the form "google.com" or "google.co.uk".
 
 **Login Form**: One of the things to analyze if the url contains a login form. I wanted to find a solution that was not human language dependent, that recogniced the login form in the url listed (github and spiegel.de), and that didn't result in a false positive for the registration form of the same two websites. I decided for a quite simple solution. A page is considered to have a login form if contains a `<form>` element that:
1. Contains exactly one input with type=password
2. And contains exactly one text field or exactly one email field
This way I discard all password confirmation fields etc. I think this is ok for this prototype, but the study of more cases would be convenient to see if it is an acceptable solution.

**Security Considerations**: The provided url is verified (by Spring MVC) so only well-formed urls are accepted, this provides some protection. Also, the links found in the web page are also verified, and only well-formed url are taken into consideration, so even in the case of returning the list, no harmful javascript code should arrive to the frontend. For a production environment this should be tested more thoroughly.



### Limitations of the solution, and things to improve:
**The frontend**: The "frontend" is quite rudimentary. The form should be much more user friendly: Provide a dropdown with the values "http" and "https", so the user does not have to type the protocol, only submit if the url is not empty... while the request is running (since just parsing the html with Jsoup takes several seconds sometimes), there should be an indication in the frontend that is waiting for an answer. And so on.

**HTML Version**: The structure is prepared for the server to recognize and name all the html versions in [this page](https://www.w3.org/QA/2002/04/valid-dtd-list.html), but alas only HTML5 and HTML 4.1 are actually recognized. It's a matter of adding values to a map though.

**Error Handling**: The error handling is mostly the default provided by Spring MVC. In case of an error while processing the request, a json with all this information is provided:

```json
{
    "timestamp":1496437253356,
    "status":500,
    "error":"Internal Server Error",
    "exception":"java.lang.IllegalArgumentException",
    "message":"Malformed URL: wrong.com",
    "path":"/api/analyze"
}
```
Some of that info is displayed on the frontend. This is ok for a prototype but not good enough for a final product.

### Optional Part:
The optional part of the challenge is alas not implemented. To check if all the links are accesible via https, I would use the URIs that are already parsed while counting the links. From that list I would use a multihreaded stream to produce a series of CollectableFuture, so the links can be checked in parallel.
