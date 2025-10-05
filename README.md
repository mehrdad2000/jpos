# recompile custom channel jpos

`java -jar /tmp/cfr.jar org/jpos/iso/channel/JMSChannel.class --outputdir src`

`javac   -cp "../jpos-2.1.8.jar:/opt/apache-jmeter-5.6.2/lib/*"   -d .   src/org/jpos/iso/channel/FixedHeaderASCIIChannel.java &&  jar uf ../jpos-2.1.8.jar org/jpos/iso/channel/FixedHeaderASCIIChannel.class`

JMSChannel.java (mq)
FixedHeaderASCIIChannel.java (tcp)
