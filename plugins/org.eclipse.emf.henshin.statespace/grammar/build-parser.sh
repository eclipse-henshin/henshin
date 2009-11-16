#!/bin/bash

GRAMMAR="StateSpace.g"
OUTPUT=../src/org/eclipse/emf/henshin/statespace/parser

CLASSPATH=$(echo ${HOME}/.org.antlr.gen/*.jar | sed s/\ /:/g)

java -cp $CLASSPATH org.antlr.Tool -o $OUTPUT $GRAMMAR
