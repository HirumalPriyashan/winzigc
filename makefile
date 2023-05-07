SRC_DIR := src/main/java
OUT_DIR := out

SRCS := $(wildcard $(SRC_DIR)/*.java)
CLS := $(SRCS:$(SRC_DIR)/%.java=$(OUT_DIR)/%.class)

JC := javac
JCFLAGS := -d $(OUT_DIR)/ -cp $(SRC_DIR)/
.SUFFIXES: .java .class

default: compile

compile:
	$(JC) $(JCFLAGS) $(SRCS)

clean:
	rm $(OUT_DIR)/*.class
