# Simple web application
# How the application works
1. Database schema and test data are loaded with Liquibase
2. The application connects to the database
3. Executes a simple query that returns a list of projects that are placed in a multi-select drop-down list
4. The user can search for keywords in the project submission history (supports any number of keywords)
5. Returns a list of searched results in an html file that is saved (in root directory) and opened in the browser (MS Edge is required to open the file automatically)

# Screenshots
1. Application window
![img.png](src/main/resources/static/app_window.png)
2. Search results example
![img.png](src/main/resources/static/search_results_example.png)

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