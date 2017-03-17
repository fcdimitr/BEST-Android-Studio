SHELL := /bin/bash # Use bash syntax
PDFLATEX = pdflatex
BIBTEX   = bibtex

MAIN = android-studio-main

# "make" target

all: pdf

# PDF compilation

pdf:
	$(PDFLATEX) $(MAIN).tex
	$(BIBTEX)   $(MAIN)
	$(PDFLATEX) $(MAIN).tex
	$(PDFLATEX) $(MAIN).tex

# Fast compilation, where supported

fast:
	latexmk -pdf -pdflatex="pdflatex -interaction=nonstopmode" $(MAIN).tex

# compilation without references

nobib:
	$(PDFLATEX) $(MAIN).tex
	$(PDFLATEX) $(MAIN).tex

up:

	pushd .. ; git pull ; popd

# clean-up
clean:
	rm -f *.bbl *.blg *.log *.aux *.nav *.out *.snm *.synctex.gz *.toc *.pdf *.vrb *.bcf *.run.xml *.xwm
	rm -f sections/*.{log,bu}
	rm -f *~
	rm -f sections/*~
	rm -f preamble/*~
