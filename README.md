# SPRINGER NATURE CONSOLE PAINT
Simple Java Console application realizing a textual paint with simple command line operations :

`Command        ` - `Description`

`C w h          ` - Create a new canvas of width `w` and height `h`.

`L x1 y1 x2 y2  ` - Create a new line from `(x1,y1)` to `(x2,y2)`. Currently only horizontal or vertical lines are supported.

`R x1 y1 x2 y2  ` - Create a new rectangle, whose upper left corner is `(x1,y1)` and lower right corner is `(x2,y2)`. Currently only horizontal or vertical lines are supported.

`B x y c        ` - Fill the entire area connected to `(x,y)` with colour `c` represented by a letter as the `bucket fill` operation do. 

`Q              ` - Quits the console



This project provide the features :

1- Business abstract API features library

2- Business implementation features library

3- Console Paint Application

4- Maven enterprise application environment


(** UNDER DEVELOPMENT **)


## Pre-Requisites

This project requires `Java 1.7 sdk`, `Maven 3.x` and the system variable JAVA_HOME referred to the JDK 1.7 base directory.

Please run the maven install on the profiles `install-platform` and the `build` process to setup the environment and install the environment. After the installation process you can run the application with the process `run-console`

## Build & Export

Run `mvn -U clean install shade:shade cobertura:cobertura javadoc:jar -P build` for building.
Tests are required before to compile and distribute the app in the maven local repo. A junit and a cobertura reports are provided in the same folder inside the container folder `reports` in each project build path.


## Build & Deployment

A maven POM procedure runs new features :

install the required platform software:
`mvn -U install -P install-platform`

(nothing to install in this project)


build:
`mvn -U clean install shade:shade cobertura:cobertura javadoc:jar -P build` to make the extended build or simply `mvn -U clean install shade:shade` to make the base build

(NOTE: In the platform directory will be installed the executables.)


run the Cobertura Code Coverage reports generation:
`mvn -U cobertura:clean cobertura:cobertura`


run the stand-alone JavaDoc generation for any of the jar libraries:
`mvn -U javadoc:jar`


run the the console application by the command line:
`mvn -U exec:exec -P run-console`

(Note: You need have setted the environment variable JAVA_PATH to the jdk 1.7 base directory before run it. It works only after the full build of the maven enterprise application)

## Testing

Running `mvn -U test` will run the unit tests with junit test. Test cases are running before the build as pre-requisites.
You can run separately the tests a junit (`mvn -U test`) and necessary the cobertura reports (`mvn -U cobertura:clean cobertura:cobertura`) have been created  in the `target/reports` folder for the applications.
Testing frameworks : `junit, mockito`
