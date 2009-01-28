I have been playing recently with JCL (http://sourceforge.net/projects/jcloader) to load classes on demand from a jar file.

The inmediate use of this is to build a plugin engine. A jar file might contain some code that can change without the need to restart the application using it.

Suppose you are told there is a specific functionality that should be changing regularly without affecting your server uptime. If you are behind a simple server container using WAR deployments you could use this method to ensure no WAR redeployment is needed when your functionality is in fact improved/changed.

The attached zip contains a running sample (JDK 5 needed, if you need to support a previous version just download jcl and compile it yourself). 

To see it in action just run run.sh (using linux this time). If you are running Windows just get the *.sh files and provide similar *.bat files. Yes I know Ant is cross platform and I should have used it for this post. Next time I will use Ant and even Maven ...

You will get full traces of what is happenning every two minutes. Change constant 'version' in PluginImpl.java and run build-plugin.sh to see how the trace message is updated with the new version.


