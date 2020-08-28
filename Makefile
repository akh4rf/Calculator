JAVA=javac
JAVAFLAGS=-verbose
SRCDIR=src
CLASSES=class
RELEASE=release
JAVAFILES=$(SRCDIR)/*.java

main: $(JAVAFILES)
	$(JAVA) $(JAVAFLAGS) $(JAVAFILES) -d $(CLASSES)

jar: main
	cd $(CLASSES) && jar cfve ../$(RELEASE)/Calculator,jar Calculator *.class

clean:
	rm -f $(CLASSES)/*.*
	rm -f $(RELEASE)/*.*
