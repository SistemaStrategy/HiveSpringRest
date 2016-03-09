# HiveSpringRest
HiveSpringRest is a Spring web application exposing a REST interface for querying Hive.

## Prerequisite
* Maven >= 3.3.3
* Java 8
* Tomcat 9
* HiveServer2 (CDH 5.5.1, Hive jar 1.1.0)

## Usage
This web application must be compiled using maven and then deployed on tomcat.
* Customize app.properties with your settings
* Compile the project

  ```
  mvn clean install
  ```
* Deploy the war generated on tomcat, using the manager-gui or a script. If you're using the manager-gui, edit the web.xml
file for increasing the war upload limit size of 50 MB.
* Test the application by accessing */getAll/client* resource.

## Configuration
**TODO : Externalize properties file**
The HiveServer2 settings can be configured by editing the *src/main/resources/app.properties* file. By default the nosasl 
authentication mode is used and the table creation is enabled. You're free to modify this according your HiveServer2 
configuration and need. 
* app.hive.url : HiveServer2 URL
* app.hive.auth : Authentication mode, **noSasl** and **sasl** values supported
* app.hive.user : Hive username, optional if noSasl authentication used
* app.hive.password : Hive password, optional if noSasl authentication used
* app.hive.schema : Schema to be used or created
* app.hive.createDB : Boolean indicating if the database creation is enabled
  **WARNING** The database creation is going to insert data and overwrite actual data in tables
* app.hive.datafile.client.location : Specify the location of csv files, **local** or **hdfs** values supported
* app.hive.datafile.client.path : Specify the location of *client.csv* file on **local** or **hdfs** filesystem 

## Logging
By default the application is writing to *{catalina_home}/logs/HiveSpringRest.log*, but this output path can be modified 
by editing the *src/main/resources/log4j.properties* file.