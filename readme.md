## Contents
- [Local Environment](#local-environment)
- [WSK CLI](#wsk-cli)
- [Deploy Python Action](#python-action)
- [Deploy Java Action](#java-action)
- [Deploy JS API](#deploy-js-api)
- [Deploy Python API](#deploy-python-api)
- [Deploy Java API](#deploy-java-api)


## Local Environment
Download open whisk for local environment:
This program runs on the local environment as docker. `Docker, NPM and java` should be installed to run this environment.
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
## WSK CLI

### CLI-Documentation
[openwhisk cli official documentation](https://openwhisk.apache.org/documentation.html#wsk-cli)

### CLI Setup
i) Download the cli from [Official Website](https://github.com/apache/openwhisk-cli/releases).  
ii) Unzip the folder  
iii) Move the wsk file to /usr/local/bin/  
iv) check if it is working with  'wsk --help' command


## Python Action.
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

## Java Action
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
### Pom File With Dependency

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
This will create a fat jar with name mentioned in pom file.
```
original-thumbnail-1.0.jar
```
&

```
thumbnail-1.0.jar
```
The fat jar is
```
thumbnail-1.0.jar
```
This file will be in target folder. Select `thumbnail-1.0.jar` Copy and paste the jar from where you want to use it to deploy in openwhisk.  Rename the jar to `hello.jar` (not necessary but is easier to remember the name of jar).

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




# Action As API

## Deploy JS API
* create a file hello-web.js:

```
function main(params) { 
    return {
        statusCode: 200,
        headers: { 'Content-Type': 'application/json' },
        body: params
    };
}
```

* create action in openwhisk:
```
wsk action create redirect hello-web.js --web true
```

* View the api address of the deployed action:
```
wsk action get redirect --url 
```

`redirect` is the name of the action, don't confuse it as keyword.

* Reply from query of api.
```
http://172.17.0.1:3233/api/v1/web/guest/default/redirect
```
* Getting the result from API
  If you are expecting .json, .html, .text or .http this must be append in the end.
  suppose you are expecting json. The url will be:
```
http://172.17.0.1:3233/api/v1/web/guest/default/redirect.json
```

Query this from postman or curl. You will get the deisred result.

* To view all the listed api
```
wsk api list
```
if the above command do not work then.
```
wsk action list
```

### passing argument to the openwhisk function
* Change the same above code hello-web.js code to :
```
function main({name}) {
  var msg = 'you did not tell me who you are.';
  if (name) {
    msg = `hello ${name}!`
  }
  return {body: msg}
}
```

* update the function in open whisk:
```
wsk action update redirect hello-web.js --web true
```
* check the url
```
wsk action get redirect --url
```

* call the api from postman or curl
```
http://172.17.0.1:3233/api/v1/web/guest/default/redirect.json?name=Himal
```

## Deploy Python API
* Take the same above code with name hello-python.py for deploying as api:
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

* Deploy the action in openwhisk
```
wsk action create hellopy hello-web.py --web true
```
* View the api address
```
wsk action get hellopy --url

```
* most likely the address will be:
```
http://172.17.0.1:3233/api/v1/web/guest/default/hellopy

```
* use postman or curl to call the api
```
http://172.17.0.1:3233/api/v1/web/guest/default/hellopy.json?name=Himal
```
* reason for `.json?name=Himal` at the end is:`.json` because the expected result is in json
  `name?Himal` the parameter.

## Deploy Java API
* create java like above.
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

        JsonObject headers = new JsonObject();
        headers.addProperty("content-type", "application/json");

        JsonObject response = new JsonObject();
        response.add("headers", headers);
        response.addProperty("body", name);
        return response;
    }
}

```

* use same pom file as above.
  [pom file](#pom-file-with-dependency)
* create action
 ```java
wsk action create thumbnail thumbnail-1.0.jar --main org.apache.openwhisk.example.maven.Hello --web true
```
* Get the url
```
wsk action get thumbnail --url
```

Then invoke the action via the url from the result.

