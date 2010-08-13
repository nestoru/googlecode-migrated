#!/bin/bash
#
# Downloads a WAR file from a URL and deploys it into tomcat (moves it to tomcat webapp folder)
#
# @author Nestor Urquiza
#

#
# Configuration
#
#TOMCAT_WEBAPP=/home/liferay/liferay-portal-5.2.3/tomcat-6.0.18/webapps
TOMCAT_WEBAPP=~/apache-tomcat-6.0.28/webapps/

#
# Functions
#
function usage {
  echo "Usage - $0 url type [projectName]"
  echo "        url is the WAR archive URL if type is 'war', the SVN project URL if type is 'svn'"
  echo "        type is any of 'warRepo, warSvn, jar'. Jar type is just deployed in the local repository so WAR projects have their snapshot dependencies available"
  echo "        projectName is needed only in case of type == 'warSvn'"
  exit 1
}

function deployWarRepo {
	#getting rid of the numbers
	URL=$1
	LOCAL_FILE=${URL##*/}
	NAME=${LOCAL_FILE%-*}
	EXT=${LOCAL_FILE##*.}
	DEST_FILE=$TOMCAT_WEBAPP/$NAME.$EXT

	#delete local file
	if [ -e "$LOCAL_FILE" ]
	then
  		rm $LOCAL_FILE
	fi

	#download the file
	wget $1
	
	#touch the file to update timestamp
	touch $LOCAL_FILE

	#tomcat deploy
	echo "mv $LOCAL_FILE $DEST_FILE"
	mv $LOCAL_FILE $DEST_FILE
}

function deployWarSvn {
	#delete local folder
	if [ -e "$3" ]
	then
  		rm -fR $3
	fi
	
	#checkout from svn
	svn co "$1" "$3"
	
	#build
	cd "$3"
	mvn clean install
	
	#deploy
	cp "target/$3.war" "$TOMCAT_WEBAPP"
}

function deployJarSvn {
	#delete local folder
	if [ -e "$3" ]
	then
  		rm -fR $3
	fi
	
	#checkout from svn
	svn co "$1" "$3"
	
	#build
	cd "$3"
	mvn clean install
}

#
# Main program
#
cd ~/deployments

if [ $# -lt 2 ]
then
        usage
fi
if [ $# -gt 1 ] && [ "$2" != "warSvn" ] && [ "$2" != "warRepo" ] && [ "$2" != "jarSvn" ]
then
        usage
fi

if [ $# -eq 2 ] && ( [ $2 == "warSvn" ] || [ $2 == "jarSvn" ] )
then
        usage
fi

if [ $2 == "warRepo" ]
then
        deployWarRepo $1 $2
fi

if [ $2 == "warSvn" ]
then
        deployWarSvn $1 $2 $3
fi

if [ $2 == "jarSvn" ]
then
        deployJarSvn $1 $2 $3
fi
