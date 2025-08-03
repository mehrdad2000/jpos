# Here is jPOS example repository to run jpos as server or client

https://github.com/galihlasahido/jpos-example

# server pom to create single jar file
https://github.com/galihlasahido/jpos-example/tree/main/jpos-server

1-run with mvn
```
/opt/apache-maven-3.9.9/bin/mvn exec:java -Dexec.mainClass="com.ads.jpos.MainApp"
```

2-set custom [pom.xml](https://github.com/mehrdad2000/jpos/blob/main/jpos-server/pom.xml) in jpos-server directory

3-build single jar file
/opt/apache-maven-3.9.9/bin/mvn clean package

4-run jar
java -jar ./target/jpos-server-1.0.jar
