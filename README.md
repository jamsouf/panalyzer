panalyzer
=========

panalyzer is a small command line JAR program to analyze a project or in general a directory and its content.
It gives a short summary of the file types, lines of code, directory size, latest modified files and a couple of other values.

<img src="http://i.imgur.com/MzXnvwk.jpg" alt="overview" width="230">
<img src="http://i.imgur.com/ksoChhS.jpg" alt="folders" width="230">
<img src="http://i.imgur.com/QeLvVZb.jpg" alt="files" width="230">

Usage
-----
1. Download the latest JAR file from the [`releases`](/releases) folder and put it into the folder which you want to analyze
2. Open a terminal, navigate to the JAR and execute: `java -jar panalyzer.jar`
3. Go into the created folder `panalyzer-report` and open the file `index.html` in a browser

Config
-----
To control the analyzed directories and files you can create a config file which will be included into the analyzing process. 
With the config file you can e.g. define ignores so that these directories and files are not analyzed.

##### panalyzer.json
Crate the file `panalyzer.json` and put it into the same directory where the JAR file is located.

##### example content
``` json
{
    "ignores": [
        "*.iml",
        ".git",
        "*target",
        "src/main/resources/tmp",
        "tmp/data/*.log",
        "config/db/*/*password*.conf"
    ]
}
```

Build
-----
To build the JAR package from the sources execute the following command in command line:  
`mvn clean compile assembly:single`
