export JAVA_HOME=$JAVA8
mvn exec:java -Dexec.mainClass=org.cellang.corpsviewer.CorpsViewer -Dcellang.data.dir=/c/d/data
