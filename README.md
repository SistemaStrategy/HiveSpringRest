# HiveSpringRest
HiveSpringRest is a Spring web application exposing a REST interface for querying Hive.

## Prerequisite
* Maven >= 3.3.3
* Java 8
* Tomcat 9
* HiveServer2 (CDH 5.5.1, Hive jar 1.1.0)

## Usage
This web application must be compiled using maven and then deployed on tomcat.
* Compile it

  ```
  mvn clean install
  ```
* Deploy the war on tomcat, using the manager-gui or a script. If you're using the manager-gui, edit the web.xml
file for increasing the war upload limit size of 50 MB.
* Test the application by accessing **/tables** resource.

## Configuration
The HiveServer2 URL can be configured by editing the src/main/resources/app.properties file.

## Logging
By default the application is writing to {catalina_home}/logs/HiveSpringRest.log, but this output path can be modified 
by editing the src/main/resources/log4j.properties file.