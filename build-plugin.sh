cd poc-sources
javac com/nestorurquiza/jcl/sample/plugin/*.java
jar cvf plugin.jar com/nestorurquiza/jcl/sample/plugin/*.class
mv plugin.jar ../
cd ..
