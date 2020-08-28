JAVA=javac
JAVAFLAGS=
SRCDIR=src
BINDIR=bin
JAVAFILES=$(SRCDIR)/*.java

main: $(JAVAFILES)
	$(JAVA) $(JAVAFLAGS) $(JAVAFILES) -d $(BINDIR)

clean:
	rm -f $(BINDIR)/*.*
