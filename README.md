# Java_JUnit
JAVA DOCUMENTATION
Automation Team Proj.



Links used: 
"https://mvnrepository.com/artifact/junit/junit/4.12"
https://maven.apache.org/


Questions: how to articulate "DartifactId" instead of saying "helper" which is just what we named our "DartifactId" when we ran the command.



PWD:
/Users/kyleconnelly/OneDrive - SmartBear Software, Inc/cbtHelper/helper

The point of this is to help the end user/subscriber use our Java Selenium testing.

The download of all things needed for java on your local machine are straight forward and out there on the web via google and youtube. 

The issue in my eyes was having a good order of operation for installing dependencies and code examples that would run the test itself (this test in case was just to check the title of the page). There were imports that we no longer needed still in the old code. I didn't know to run "mvn archetype:generate -DgroupId=com.cbt.helper -DartifactId=helper -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false" in terminal to create the "pom.xml" file, Etc.

Steps taken:
1. Create directory - cbtHelper

2a. Cd into cbtHelper and run "mvn archetype:generate -DgroupId=com.cbt.helper -DartifactId=helper -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false". Keep in mind to change/name your "DgroupId" to what ever you'd like.

2b. Make sure that you import the JUnit library and selenium library into the pom.xml file.

2c. Doing step 2a will create the "helper" directory inside "cbtHelper" directory along with the "pom.xml", "src", and "target" directories/files. Explore each file to see whats inside.

3. Once you've run step 2a, cd into "helper" and open "pom.xml" and once you've added your dependencies run "mvn install" to install your dependencies.

4. Once dependencies are installed cd into "src/"(which was created when you ran step 2a, so it will be inside what ever name you gave the "DarifactId=" until you reach the last directory and create your test file in that directory, our example is "CBTTest.java".

