#!/bin/bash
#
# xalan-install.sh
#
# 1. Copy this recipe to a directory where you wish to install xalan. 
# 2. Run it and it will install XALAN_JAVA executable inside INSTAL_DIR
# 
#
# @author: Nestor Urquiza
# @date: 20110211
#
#

echo "--- Install Xalan XSLT Standalone processor ---"
 
set -e

XALAN_JAVA=xalan
INSTAL_DIR=xalan-processor
 
mkdir -p $INSTAL_DIR
cd $INSTAL_DIR
curl -O http://mirrors.ibiblio.org/pub/mirrors/maven2/xalan/xalan/2.7.1/xalan-2.7.1.jar
curl -O http://mirrors.ibiblio.org/pub/mirrors/maven2/xalan/serializer/2.7.1/serializer-2.7.1.jar
curl -O http://mirrors.ibiblio.org/pub/mirrors/maven2/xerces/xercesImpl/2.9.0/xercesImpl-2.9.0.jar
echo "#!/bin/bash" > $XALAN_JAVA
echo "set -e" >> $XALAN_JAVA
echo "USAGE=\"Usage: $XALAN_JAVA <path to xml> <path to xsl>\"" >> $XALAN_JAVA
echo "if [ \$# -ne \"2\" ]; then echo \$USAGE; exit 1; fi" >> $XALAN_JAVA
echo "CP=\"`find \".\" -name '*.jar' | xargs echo | tr ' ' ':'`\"" >> $XALAN_JAVA
echo "CLASSPATH=\$CP:\$CLASSPATH" >> $XALAN_JAVA
echo "export CLASSPATH" >> $XALAN_JAVA
echo "java org.apache.xalan.xslt.Process -IN \$1 -XSL \$2" >> $XALAN_JAVA
chmod +x $XALAN_JAVA
echo "$XALAN_JAVA was installed in $INSTAL_DIR directory. Run this to start using xalan: cd $INSTAL_DIR; ./$XALAN_JAVA"






