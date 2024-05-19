# simpleShapeDrawer
a simple java program using java21 and javafx to draw 3 basic shapes. Lab homework
## How to build ?
**You need to have java 21 or higher installed!**

The JAVA_HOME environment variable needs to be set to java21 or higher otherwise maven will not work
```sh
JAVA_HOME = <Path to java21>
```
It will not work if the path is not set to java 21

First cd into the directory where mvnw and mvnw.cmd is located
Then run the build scripts with the javafx:run arguments

### Run the app
```
.\/mvnw.cmd javafx:run
```

### Linux
```
sudo chmod +x mvnw
sudo ./mvnw javafx:run
```
## Everything else failed?
You can just execute the jar file
### Windows
```
java -jar jarfile/ShapesEditor.jar
```


## How is the program structured ?

