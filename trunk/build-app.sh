cd poc-sources
javac -cp ../lib/jcl.jar com/nestorurquiza/jcl/sample/app/*.java
jar cvf app.jar com/nestorurquiza/jcl/sample/app/*.class
mv app.jar ../
cd ..


