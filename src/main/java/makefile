SRCS := $(wildcard ./*.java)
CLS := $(SRCS:%.java=%.class)

JC := javac
JCFLAGS := -g
.SUFFIXES: .java .class

default: compile

compile:
	$(JC) $(JCFLAGS) $(SRCS)

clean:
	$(RM) *.class
