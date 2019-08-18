# note-maker
Quick full-stack solution to create (modify as well as delete) notes.  This tool also features sorting and pagination.

#Backend

## Commands to startup the API
###Standard mode
java -jar target/notes-keeper-0.0.1-SNAPSHOT.jar

###Debug mode
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8082 -jar target/notes-keeper-0.0.1-SNAPSHOT.jar

###Tools
#####SpringBoot
 - Great solution to create a micro-service
 - Lots of support by other frameworks/modules
 - `crnk` & `jOOQ` provide spring-boot modules

#####crnk
 - Creation of Model and Repository (similar to the MVC pattern)
 - Out-of-the-box support filtering, paging & sorting abilities
 - Built to respect the JSON-API specification

#####jOOQ
 - ORM mapper and code-generator
 - Connection pooling abilities
 - Provides an easy-to-use API to query the database and perform CRUD operations

#Frontend

## Commands to start frontend web-app
npm start

###Tools
####ReactJS
 - Scaffolding command-line feature to bootstrap a reactjs web-app including:
    - unit-tests configuration
    - webpack-dev-server with auto-reload on save
    - large open-source support

####Material-UI for ReactJS
 - Material Design spec implemented in ReactJS
 - Like `bootstrap` but using ReactJS virtual DOM and components
 - Ready-made Datatable open-source packages supporting remote-data fetching


###Assumptions
 - Since I opted for the `crnk` framework which has limited support for Jackson annotations,
   I decided to perform validation of the length of each field/column before performing the
   insert operation.
    - This could be alleviated by creating a custom length annotation, a new class which
      each resource will extend and a utility class whereby each of these resources will be
      parsed and validated before resuming execution to the CRUD methods in the implemented
      repository.
