srcpath = ./lab03
src = $(shell find . -type f -name *.java)
outpath = $(srcpath)

flags = -Xlint:deprecation

all: $(src)
	javac $(flags) $^ -d .

run:
	java lab03.Main

.PHONY: clean run
clean:
	rm -f $(srcpath)/*.class
