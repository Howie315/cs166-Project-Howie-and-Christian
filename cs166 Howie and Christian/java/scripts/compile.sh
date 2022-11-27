#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

export JAVA_HOME=/extra/hnguy431/cs166/jdk1.8.0_352.jdk
export PATH=$JAVA_HOME/bin:$PATH
-Djava.awt.headless=true


# compile the java program
javac -d $DIR/../classes $DIR/../src/Retail.java



#run the java program
#Use your database name, port number and login
java -cp $DIR/../classes:$DIR/../lib/pg73jdbc3.jar Retail $USER"_DB" $PGPORT $USER

