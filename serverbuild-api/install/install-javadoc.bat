@echo off
mvn install:install-file -DgroupId=me.xiaoying.serverbuild -DartifactId=serverbuild-api -Dversion=1.0.1 -Dpackaging=jar -Dclassifier=javadoc -Dfile=../target/serverbuild-api-V1.0.1-javadoc.jar
exit