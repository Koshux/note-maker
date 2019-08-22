# note-maker
Quick full-stack solution to create and delete notes.  This tool
also features sorting and pagination. This tool will be seeing new improvements and additional features shortly like Redux, entire CRUD support, search and so on.

#### Requirements to run
 - Latest version of MySQL (haven't tested on older versions, likely compatible)
 - JDK/JRE 8

#### Additional Requirements to compile
 - Maven
 - Execute database schema and data scripts

#### Installation and start-up
##### `Step 1`
Ensure you have a MySQL server with an IDE (e.g. sqlyog, workbench etc...) or a
simple command-line would do.

##### `Step 2`
Ensure your credentials to access the MySQL database are as follows:
  - username: root
  - password: root

##### `Step 3`
Execute the following scripts in the order as described in your MySQL database
instance you have preconfigured or recently installed:
  - schema.sql
  - data.sql

##### `Step 4`
Once you have satisfied each step above, you may start up the API by executing
the jar file:

    java -jar target/notes-keeper-0.0.1-SNAPSHOT.jar

If you do not have the jar file available, run the following command first:
    mvn clean package -P mysql

Note: This will start up the API on port 8081.

##### `Step 5`
Install the web-app dependencies before starting up the server.  Note that this
requires the user to have installed the latest NodeJS (>= v8.x.x) and NPM
(>= v6.x.x) version:

    npm install

##### `Step 6`
Given that the dependencies installed successfully, start up the react web-app
(in dev-mode) by running the following command:

    npm start
Note: This will start a local dev-server on port 3000.  It will proxy requests
      from localhost:3000 to localhost:8081 to perform JSON:API requests
      and also goes by the CORS origin issue (see
      `todo: react-link-proxy-requests`).

#### `Success!`
Should webpack failed to open your browser at http://localhost:3000,
simply visit that URL and you should see the web-app with the dummy data notes
loaded from your MySQL instance!


<br/>

## Backend
 - Application will start on port 8081.
    - `https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#using-boot-running-as-a-packaged-application`

#### Assumptions
 - Since I opted for the `crnk` framework which has limited support for Jackson
   annotations, I decided to perform validation of the length of each
   field/column before performing the insert operation.
  - This could be alleviated by creating a custom length annotation, a new class
    which each resource will extend and a utility class whereby each of these
    resources will be parsed and validated before resuming execution to the CRUD
    methods in the implemented repository.
  - Due to the restricted time:
    - I hadn't the chance to setup a docker image which could be run
      (prepackaged mysql, jar and web-app).
    - The MySQL instance must use a preset selection of credentials for the
      `user-name` and `password` (see Step 2 in `Installation Steps`).
  - Due to the point above - in order to use this web-app successfully, the user
    must follow the short installation steps.

#### Starting up the API
##### `Standard mode`
    java -jar target/notes-keeper-0.0.1-SNAPSHOT.jar

##### `Debug mode`
    java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8082 -jar target/notes-keeper-0.0.1-SNAPSHOT.jar

#### Tools
##### `SpringBoot`
 - Great solution to create a micro-service
 - Lots of support by other frameworks/modules
 - `crnk` & `jOOQ` provide spring-boot modules

##### `crnk`
 - Creation of Model and Repository (similar to the MVC pattern)
 - Out-of-the-box support filtering, paging & sorting abilities
 - Built to respect the JSON-API specification

##### `jOOQ`
 - ORM mapper and code-generator
 - Connection pooling abilities
 - Provides an easy-to-use API to query the database and perform CRUD operations


<br/>

## Frontend
  The frontend web-app will be available at `http://localhost:3000`

#### Assumptions
 - 14 unit tests checkpoints in have been setup for coverage of any testable functionality:
    - Since this was the first time I developed a web-app in react, I was not familiar with `Jest` or `react-testing-library` so I hadn't enough time to complete them.
    - Instead I  have provided full pseudocode length for each test case to be covered in order to show the effort required.  Once I read further into the new syntax and understand it better, I can convert the pseudocode to actual test cases.
 - Once a new note is sent, the text-field is not cleared.
    - Unfortunately hadn't enough time to fix this.  Instead I opted to prioritise the required features to be implemented first (which I can confirm have all been ticked except complete frontend unit tests).
    - This feature was additional but required since it pertains to improved usability.
 - No notifications are displayed when a note is successful or fails to send.
    - This feature was additional but required since it pertains to improved usability.

#### Commands
      npm start
      npm test

#### Tools
##### `ReactJS`
 - Scaffolding command-line feature to bootstrap a reactjs web-app including:
   - unit-tests configuration
   - webpack-dev-server with auto-reload on save
   - large open-source support

##### `react-testing-library`
 - Well known library which is used for testing and is typically preferred over enzyme (AirBnb testing library).
 - Provides rich and easy-to-use API for unit testing React components and functionality.
 - Other sibling libraries are included to test for e.g. React hooks like useState, useContext and so on.

##### `material-ui`
 - Material Design spec implemented in ReactJS
 - Like `bootstrap` but using ReactJS virtual DOM and components

##### `material-table` (built-on `material-ui`)
 - Ready-made Datatable open-source package supporting remote-data fetching
