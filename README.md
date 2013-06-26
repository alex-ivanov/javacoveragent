javacoveragent
==============

Java Coverage Agent

Very simple coverage agent to grab information about dead methods.

Right now it supports only method coverage information and allows to dump information into plain file (format is very similar to CSV) or console.

How to install:

-javaagent:agent.jar=coverage.properties

coverage.properties file contains a configuration for your run. Here is an example:
##################################
OPTION.METHODS.INCLUDE=true

EXPORTER.TYPE=simple.file
SIMPLE.EXPORTER.FILE.NAME=coverage-test.data
#EXPORTER.TYPE=simple.console

INCLUDE.0=com/test/
INCLUDE.1=com/test1/
INCLUDE.2=com/test2/

EXCLUDE.0=test/test1/doNotCover
####################################

Right now agent can dump information into your console (EXPORTER.TYPE=simple.console) or text file.
