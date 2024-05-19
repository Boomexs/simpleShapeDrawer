# simpleShapeDrawer
a simple java program using java21 and javafx to draw 3 basic shapes. Lab homework

**You need to have java 21 or higher installed!**

The JAVA_HOME environment variable needs to be set to java21 or higher otherwise maven will not work

```sh
JAVA_HOME = <Path to java21>
```
It will not work if the path is not set to java 21
### Windows
```
java -jar jarfile/ShapesEditorWindows.jar
```
### Linux
```
java -jar jarfile/ShapesEditorLinux.jar
```

## How to build and run
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

## How is the program structured ?
Entity is the base shape
EntityList is a list of the entites
EntityListFile entityList with the file connected to it
FileManger manages loading and saving EntityListFile
TabManager manages tabs which are conteinarized within TabStructure
PaneEditor a member of TabStructure that handles tools, and editing
