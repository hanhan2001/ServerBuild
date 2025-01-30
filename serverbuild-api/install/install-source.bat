@echo off
mvn install:install-file -DgroupId=me.xiaoying.serverbuild -DartifactId=serverbuild-api -Dversion=1.0.1 -Dpackaging=jar -Dclassifier=sources -Dfile=../target/serverbuild-api-V1.0.1-sources.jar
exit