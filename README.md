panalyzer
=========

panalyzer is a small command line JAR program to analyze a project or in general a directory and its content. It gives a short summary of the file types, lines of code, directory size, latest modified files and a couple of other values.

![overview](http://i.imgur.com/MzXnvwk.jpg)
![folders](http://i.imgur.com/ksoChhS.jpg)
![files](http://i.imgur.com/QeLvVZb.jpg)

Usage
-----
1. Download the latest JAR file from the [`releases`](/releases) folder and put it into the folder which you want to analyze
2. Open a terminal, navigate to the JAR and execute: `java -jar panalyzer.jar`
3. Go into the created folder `panalyzer-report` and open the file `index.html` in a browser

Build
-----
To build the JAR package from the sources execute the following command in command line:  
`mvn clean compile assembly:single`
