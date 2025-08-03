#REPO
https://github.com/galihlasahido/jpos-example

#server pom to create single jar file
https://github.com/galihlasahido/jpos-example/tree/main/jpos-server

1-run with mvn
/opt/apache-maven-3.9.9/bin/mvn exec:java -Dexec.mainClass="com.ads.jpos.MainApp"

2-set custom pom.xml

3-build single jar file
/opt/apache-maven-3.9.9/bin/mvn  clean package
