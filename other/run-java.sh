wsk action create helloJava hello.jar --main org.apache.openwhisk.example.maven.Hello
wsk action invoke --result helloJava