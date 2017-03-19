SHELL := /bin/bash # Use bash syntax


##############################################################
# COMMANDS

TMARK   = ^^^
LATEX   = pdflatex
LATEXMK = latexmk -pdf -pdflatex='pdflatex %O -interaction=nonstopmode -synctex=1 $(TMARK)'
BIBTEX  = bibtex


##############################################################
# TARGET

T_DOC = android-studio-main


##############################################################
# CUSTOM FLAG

HANDOUT = \def\handout{true}
WIDE    = \def\widescreen{true}


##############################################################
# COMPILATION

all: presentation

fast: fastpresentation

# PDF compilation

presentation:
	$(LATEX)  $(T_DOC).tex
	$(BIBTEX) $(T_DOC)
	$(LATEX)  $(T_DOC).tex
	$(LATEX)  $(T_DOC).tex

presentation-169:
	$(LATEX)  "$(WIDE) \input{$(T_DOC)}"
	$(BIBTEX) $(T_DOC)
	$(LATEX)  "$(WIDE) \input{$(T_DOC)}"
	$(LATEX)  "$(WIDE) \input{$(T_DOC)}"w

handout:
	$(LATEX)  "$(HANDOUT) \input{$(T_DOC)}"
	$(BIBTEX) $(T_DOC)
	$(LATEX)  "$(HANDOUT) \input{$(T_DOC)}"
	$(LATEX)  "$(HANDOUT) \input{$(T_DOC)}"

fastpresentation:
	$(subst $(TMARK),"\input{$(T_DOC)}",$(LATEXMK)) $(T_DOC)

fastpresentation-169:
	$(subst $(TMARK),"$(WIDE) \input{$(T_DOC)}",$(LATEXMK)) $(T_DOC)

fasthandout:
	$(subst $(TMARK),"$(HANDOUT) \input{$(T_DOC)}",$(LATEXMK)) $(T_DOC)


update:
	pushd .. ; git pull ; popd

up: update

# clean-up
clean:
	rm -f *.bbl *.blg *.log *.aux *.nav *.out *.snm *.synctex.gz *.toc *.pdf *.vrb *.bcf *.run.xml *.xwm
	rm -f *.fls *.fdb_latexmk
	rm -f sections/*.{log,bu}
	rm -f *~
	rm -f sections/*~
	rm -f preamble/*~
