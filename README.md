# Battleship Game
## Setup Environment
### Version
Java version: 17.0.5
Gradle: 7.3.3
Kotlin: 1.5.31
Groovy: 3.0.9
### Getting Started With Gradle
```ch
cd ~
mkdir battleship
cd battleship
git init
gradle init
```
Then, Gradle would ask some questions, please answer them as follow:
- type of project  2 (application)
- implementation language  3 (Java)
- Split Functionality across multiple subprojects  1 (No)
- build script DSL  1 (Groovy)
- Generate build using new APIs and behavior  no
- test framework  4 (JUnit Jupiter)
- project name enter  (default is fine)
- source package  edu.duke.ece651.example

If you do “ls” you will see some gradle configuration files, and an “app” directory. There is not a top-level build.gradle file here, but we will need one to work with our test coverage tool. In the ece651-dev-setup git repository there is a file that will work, so
```sh
cp ~/ece651-dev-setup/top-level-build.gradle build.gradle
```
The build.gradle file is analagous to (though very different from) a Makefile. Newer gradle versions require that the project be setup with subdirectories for each subproject (even if the project doesnt have subprojects, and is just one big project). Our “app” directory is the subproject, which is where most stuff happens. Next, cd into it
```sh
cd app
```
The first of these is needed to make clover (the code coverage tool) work properly with JUnit. The second will make it so that during testing, you will see any output you create on stdout and/or stderr, and fully detailed exception traces.
```sh
gradle build
```
Copy in the top-level build.gradle, and edit app/build.gradle as in the dev-setup walkthrough.
Before you proceed, run
```sh
./gradlew  dependencies
```
to make sure your build.gradle file is valid
Some command lines you may find them useful
```
gradle cloverAggregateReports
```
Then look at the HTML file in the dir build/reports/clover/html/index.html
Open it in the browser, then you could view test coverage
