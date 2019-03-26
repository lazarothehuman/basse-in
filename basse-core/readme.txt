Steps to configure the workspace:
1 - Install subeclipse;
2 - Change JVM on eclipse to JDK (not JRE);
3 - Set envirinment variable MAVEN_OPTS="-Xmx1g -XX:MaxPermSize=300m";
4 - Create 2 Mysql databases: basse and bassetest (see file basse-core/src/main/resources/META-INF/persistence.xml)
	basse is the main application database;
	bassetest is used only for the JUnit tests, we are using DBUnit 
	datasets to prepare our tests and clean database before running the test;
5 - Execute script create-user.sql with root user on Mysql;
6 - Import development database dump into both databases created on step 4. The dump is in the file located at /basse-core/etc/basse.sql;
7 - The project is composed of 2 eclipse projects. 
	basse-core is supposed to contain data access and business logic;
	basse-web is the web layer;
	So basse-core is a dependency of basse-web, wich means every time we change basse-core, 
	we need to build it for the changes to become visible to basse-web;
	To build basse-core run maven goal clean install;
	To run the web application run maven goal clean jetty:run on basse-web, this will download and start jetty maven plugin;
8 - To login on the web application use
	username: irshad
	password: linux
