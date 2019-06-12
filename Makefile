ALL:
	
	javac -sourcepath src -d out -classpath lib/gson-2.8.5.jar:lib/javax.mail.jar src/*.java



gui:
	javac -sourcepath src -d out -classpath lib/gson-2.8.5.jar:lib/javax.mail.jar src/Window.java

