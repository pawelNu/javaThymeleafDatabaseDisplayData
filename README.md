# Simple web application
# How the application works
1. Database schema and test data are loaded with Liquibase
2. The application connects to the database
3. Executes a simple query that returns a list of projects that are placed in a multi-select drop-down list
4. The user can search for keywords in the project submission history (supports any number of keywords)
5. Returns a list of searched results in an html file that is saved (in root directory) and opened in the browser (MS Edge is required to open the file automatically)

# Screenshots
1. Application window
![img.png](screenshots/app_window.png)
2. Search results example
![img.png](screenshots/search_results_example.png)

# Used:
Java, Thymeleaf, Spring Boot, H2 database, Liquibase

# How to start:
Pull into IDE.

# REST Controllers
For test purpose:<br><br>
ProjectRestController has endpoint: http://localhost:8080/api/projects <br>
Returns a list of projects. <br><br>

HistoryRestController has endpoint: http://localhost:8080/api/ticket-history <br>
Returns Example search results for the company: Stiedemann, Reilly and Raynor and the keyword: xyz. <br><br>

# Package to .JAR file
1. In pom.xml in build tag add <finalName>SearchInDatabase</finalName>
2. Exclude files: HistoryRestController.java and ProjectRestController.java (they are not needed)
3. Create .JAR package using IntelliJ Community Edition <br>
![img.png](screenshots/package_intelij.png)
4. Create SearchInDatabase.bat file in directory with .JAR file containing: <br>
<code>
@echo off <br>
start "SearchInDatabase" cmd /c "java -jar SearchInDatabase.jar" <br>
timeout /t 10 >nul <br>
start "" http://localhost:8080/ticket-history-app
</code>
5. Run application using .bat file <br>
![app_console.png](screenshots/app_console.png)