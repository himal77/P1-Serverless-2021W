# Step 1: create a local environment for running java.
Download open whisk for local environment:
This program runs on the local environment as docker. Docker, NPM and java should be installed to run this environment.
Set up the runtime environment for running the application. installing the gradle in the local computer will set up all the required environment for the computer.

### a) Install gradle in local computer:
```
sudo apt install gradle
```

### b) download openwhisk app for running locally via git
git clone https://github.com/apache/openwhisk.git

### c) run app in the local environment:
```
sudo ./gradlew core:standalone:bootRun
```
### d) set the wsk property for wsk commands, this line will be available on the same terminal where the app      is running, scroll to the top, you will find it somewhere in different color mostly red:
```
wsk property set --apihost 'http://172.17.0.1:3233' --auth '23bc46b1-71f6-4ed5-8c54-816aa4f8c502:123zO3xZCLrMN6v2BKK1dXYFpXlPkccOFqm12CdAsMgRU4VrNZ9lyGVCGuMDGIwP'
```
# Step 2: use open whisk command line interface (wsk) for creating, deploying functions in openwhisk.

### a) For documentation visit:
openwhisk cli official documentation

i) Download the cli from github.  
ii) Unzip the folder  
iii) Move the wsk file to /usr/local/bin/  
iv) check if it is working with  'wsk --help' command  


# Step 3: Create & invoke action in python.
### Create a python file hello.py:
```
def main(dict):
    if 'name' in dict:
        name = dict['name']
    else:
        name = "stranger"
    greeting = "Hello " + name + "!"
    print(greeting)
    return {"greeting": greeting}
```
### Create a action name helloPy:
```
wsk action create helloPy hello.py
```
### Invoke a action name helloPy:
```
wsk action invoke helloPy --result
```
### Delete a action name helloPy:
```
wsk action delete helloPy
```
### View list of actions:
```
wsk action list
```
action-creation&invocation

# Step 4: Create & invoke action in java
### Create a program Hello.java:
Hello.java
```
package org.apache.openwhisk.example.maven;

import com.google.gson.JsonObject;

public class Hello {
public static JsonObject main(JsonObject args){
String name;

        try {
            name = args.getAsJsonPrimitive("name").getAsString();
        } catch(Exception e) {
            name = "stranger";
        }

        JsonObject response = new JsonObject();
        response.addProperty("greeting", "Hello " + name + "!");
        return response;
    }
}
```
### Create a pom file, import dependency of google gson.
pom.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.serverless</groupId>
    <artifactId>thumbnail</artifactId>
    <version>1.0</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <gson.version>2.8.5</gson.version>
        <shade.version>3.1.0</shade.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>${gson.version}</version>
        </dependency>
    </dependencies>

    <build>
        <finalName>maven-java</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>${shade.version}</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
Having maven-shade-plugin is important for serverless computing in java.

### Create a fat jar(jar including all dependency) of this program:

If mvn is already installed on your computer:
```
mvn package
```
else
```
./mvnw package
```
This will create a fat jar with name mentioned in pom file. i.e: thumbnail-1.0-shaded.jar  but sometimes the file name will be 'original-maven-java.jar'
This file will be in target folder. Copy and paste the jar from where you want to use it to deploy in openwhisk. rename the jar to hello.jar(not necessary but is easier to remember the name of jar).

### Create action in openwhisk:
```
wsk action create helloJava hello.jar --main org.apache.openwhisk.example.maven.Hello
```
The name of main class should start with the package. Only mentioning Hello won't work. Package can be found on top of Hello.java file. In my case: package org.apache.openwhisk.example.maven;

### Invoking action:
```
wsk action invoke --result helloJava --param name World
wsk action invoke --result helloJava
```
### Updating action:
```
wsk action update helloJava hello.jar --main org.apache.openwhisk.example.maven.Hello
```
### Deleting action:
```
wsk action delete helloJava
```
[Github](https://github.com/himal77/P1-Serverless-2021W/tree/a7ede37c0bd8264cf644ee035742179752c7f39e)
