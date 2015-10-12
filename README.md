#JumpQA
_Automating ground truth generation._

JumpQA is a projects for generating questions about a corpus. More specifically, it takes a directory of TRECs (in .xml form) and outpus a .csv.

##Requirements
To install this project, you will need:

- [Maven](https://maven.apache.org/download.cgi)

- [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

###Maven

1. Download the binary relevant to your machine.
2. Install maven to the directory of your choice.
3. Edit your `~/.bash_profile` to include the lines
```
    export M2_HOME=/path/to/maven
    export PATH=$PATH:$M2_HOME/bin 
```

###Java 8

1. Download and install the binary relevant to your machine.
2. Edit your `~/.bash_profile` to include the line
```
    export JAVA_HOME=/path/to/java8
```

##Installation
Clone the repository from git. You'll need your IBM ID and password.

    git clone https://github.com/cognitive-catalyst/JumpQA

You will be prompted for your IBM ID and password. Once downloaded, run the install script in the main directory.

    cd JumpQA/
    chmod 755 ./install.sh
    ./install.sh

##Basic Usage
Sample usage of the subprojects within the JumpQA repository. Each subproject contains more thorough documentation and usage instructions.

Before JumpQA can run on a corpus, the corpus must be converted to a .json file. This vastly speeds up how quickly JumpQA can process a corpus. Many of the fields in the TRECs are currently removed. If you wish to keep non-default ones, you will need to edit `corpus2json`. Eventually, it will be possible to specify which fields to keep in the `.properties` file.

Once the corpus is in a JSON, JumpQA can process the corpus and generate ground truth. 



###Converting Corpus to JSON
0. cd into JumpQa/
1. Edit `corpus2json.properties`. The file should look something like:
```
input=sample/
output=sample/output.json
```
`input` is a directory holding a list of `.xml` trecs. Change it to the directory holding your corpus XMLs.
`output` is the output `.json` file. 

2. Run `java -jar target/corpus2json-0.1.0.jar`. If you have a different version, replace `0.1.0` with the one you are using. 

###Using JumpQA
0. cd into JumpQa/
1. CD Edit `jumpqa.properties`. The file should look something like:
```
corpus=health-corpus.json
templates=templates.csv
output=health.csv
```
`templates` is the templates file to process the TRECs with.
`trecs` is the inputs JSON file.
`output` is the output CSV file.
2. Run `java -jar target/jumpqa-0.1.0.jar`


##Projects
Click on the subproject's name to enter its directory.

###[JumpQA](JumpQA/README.md)
This is the main project.

JumpQA takes a corpus in JSON form and creates a set of ground truth. The others are related projects and dependencies.

###[Corpus](Corpus/README.md)
This converts a directory of TRECs to a JSON file that JumpQA can use.

###[Corpus TF-IDF](Corpus TF-IDF/README.md)
Calcaulates TFIDF of terms in a corpus for each document.

###NeuralNet
Library for neural networks; to be used in JumpQA heuristics. This will eventually be moved to be its own project.

###Random
A library which includes an ArrayList which iterates randomly.

##Maintainer
Will Beason, wabeason@us.ibm.com
