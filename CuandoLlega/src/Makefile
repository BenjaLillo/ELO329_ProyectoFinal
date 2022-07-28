JFLAGS = -g
JC = javac
JVM = java
JAR = "	opencsv-5.6.jar"
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $(JAR) $*.java

CLASSES=	Main.java\
	ControlCenter.java\
	Bus.java\
	BusStop.java\

MAIN = Main

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE)

clean:
	$(RM) *.class
