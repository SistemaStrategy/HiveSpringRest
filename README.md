# HiveSpringRest
HiveSpringRest is a Spring web application exposing a REST interface for querying Hive.

## Prerequisite
* Maven >= 3.3.3
* Java 8
* Tomcat 9
* HiveServer2 (CDH 5.5.1, Hive jar 1.1.0)

## Usage
This web application must be compiled using maven and then deployed on tomcat.
* Configure the *src/main/resources/HiveSpringRest.properties* file and copy it to *{catalina_home}/conf/* folder
* Provide your *{catalina_home}* in the pom.xml file (**<argLine>-Dcatalina.home=/usr/share/tomcat9</argLine>**)
* Compile the project

  ```
  mvn clean install
  ```
* Deploy the war generated on tomcat, using the manager-gui or a script. If you're using the manager-gui, edit the web.xml
file for increasing the war upload limit size of 50 MB.

## Rest API 
* /client/all : get all clients
* /client/{id:int} : get client by id

## Configuration
The HiveServer2 and Log4J settings can be configured by editing the *{catalina_home}/conf/HiveSpringRest.properties* 
file. By default the nosasl authentication mode is used and the table creation is enabled. 
You're free to modify this according your HiveServer2 configuration and need. 
* app.hive.url : HiveServer2 URL
* app.hive.port : HiveServer2 port
* app.hive.auth : Authentication mode, **noSasl** and **sasl** values supported
* app.hive.user : Hive username
* app.hive.password : Hive password
* app.db.schema : Schema to be used or created
* app.db.createDB : Boolean indicating if the database creation is enabled

  **WARNING** The database creation is going to insert data and overwrite actual data in tables
* app.db.datafile.client.location : Specify the location of csv files, **local** or **hdfs** values supported

  **Mandatory if createDB is *true*, otherwise not used**
* app.db.datafile.client.path : Specify the location of *client.csv* file on **local** or **hdfs** filesystem 

   **Mandatory if createDB is *true*, otherwise not used**
   
## Database creation
If you're setting *app.db.createDB* to true, you'll need to copy the *client.csv* file to your **HiveServer2** machine. 
Once it's done, provide the filesystem (if it's on **hdfs** or on the **local** filesystem) and the path 
in order to use this file to insert data in the application database. 

## Logging
By default the application is writing to *{catalina_home}/logs/HiveSpringRest.log*, but this output path can be modified 
by editing the *{catalina_home}/conf/HiveSpringRest.properties* file.