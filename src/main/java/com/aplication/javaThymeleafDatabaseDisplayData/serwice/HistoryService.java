package com.aplication.javaThymeleafDatabaseDisplayData.serwice;

import com.aplication.javaThymeleafDatabaseDisplayData.additonal.SqlQuery;
import com.aplication.javaThymeleafDatabaseDisplayData.additonal.WebPageElements;
import com.aplication.javaThymeleafDatabaseDisplayData.dto.TicketHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoryService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> checkIfSelectedCompaniesAreNull(List<String> selectedCompanies) {
        // checks if the list of returned selected items from the multi-select list is empty
        // if is empty, assigns it a new empty list
        // if not, returns this list with the selected values
        if (selectedCompanies == null) {
            selectedCompanies = new ArrayList<>();
            return selectedCompanies;
        } else {
            return selectedCompanies;
        }
    }

    public void searchInTicketHistory(List<String> selectedCompanies, List<String> keywords) {
        Map<String, String> keywordsMap = convertKeywordsListToMap(keywords);
        String sqlKeywordsString = addConditionsToSqlQuery(keywordsMap);
        String selectedCompaniesString = getCompaniesFromMultiSelectComboBox(selectedCompanies);
        String sqlQueryString = createSqlQuery(sqlKeywordsString, selectedCompaniesString);
        Map<String, String> allConditionsMap = joinKeyWordsAndCompanies(keywordsMap, selectedCompanies);
        MapSqlParameterSource sqlParameters = convertToMapSqlParameterSource(allConditionsMap);

        List<TicketHistoryDto> results = executeQuery(sqlQueryString, sqlParameters);
        ticketsHistoryHTML(results, keywords, selectedCompanies);

    }

    public List<TicketHistoryDto> searchInTicketHistory2(List<String> selectedCompanies, List<String> keywords) {
        // a function created additionally for the needs of the HistoryRestController
        Map<String, String> keywordsMap = convertKeywordsListToMap(keywords);
        String sqlKeywordsString = addConditionsToSqlQuery(keywordsMap);
        String selectedCompaniesString = getCompaniesFromMultiSelectComboBox(selectedCompanies);
        String sqlQueryString = createSqlQuery(sqlKeywordsString, selectedCompaniesString);
        Map<String, String> allConditionsMap = joinKeyWordsAndCompanies(keywordsMap, selectedCompanies);
        MapSqlParameterSource sqlParameters = convertToMapSqlParameterSource(allConditionsMap);

//        System.out.println("sqlQueryString");
//        System.out.println(sqlQueryString);
//        System.out.println("allConditionsMap");
//        System.out.println(allConditionsMap);

        List<TicketHistoryDto> results = executeQuery(sqlQueryString, sqlParameters);
        System.out.println("sqlQueryString");
        System.out.println(sqlQueryString);
        System.out.println("sqlParameters");
        System.out.println(sqlParameters);

        return results;
    }

    private List<TicketHistoryDto> executeQuery(String dynamiczneZapytanie, MapSqlParameterSource parametry) {
        // executes the query with the given parameters
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<TicketHistoryDto> results = namedParameterJdbcTemplate.query(dynamiczneZapytanie, parametry, getRowMapper());
        System.out.println("executeQuery");
        System.out.println(results);
        return results;
    }

    private RowMapper<TicketHistoryDto> getRowMapper() {
        // assigns the attributes of the TicketHistoryDto class with the values from the executed query
        return (resultSet, rowNum) -> {
            TicketHistoryDto zgloszenie = new TicketHistoryDto();
            zgloszenie.setTicket(resultSet.getString("ticket"));
            zgloszenie.setStatus(resultSet.getString("status"));
            zgloszenie.setCompany(resultSet.getString("company"));
            zgloszenie.setStatusChangeDate(resultSet.getString("statusChangeDate"));
            zgloszenie.setPreviousStatus(resultSet.getString("previousStatus"));
            zgloszenie.setPreviousUser(resultSet.getString("previousUser"));
            zgloszenie.setNextStatus(resultSet.getString("nextStatus"));
            zgloszenie.setNextUser(resultSet.getString("nextUser"));
            zgloszenie.setContent(resultSet.getString("content"));
            return zgloszenie;
        };
    }

    private Map<String, String> convertKeywordsListToMap(List<String> conditionList) {
        // turns the list of keywords into a map
        Map<String, String> conditionsMap = new HashMap<>();

        for (int i = 0; i < conditionList.size(); i++) {
            String keyWord = "keyWord%d".formatted(i + 1);
            conditionsMap.put(keyWord, "%" + conditionList.get(i) + "%");
        }
        return conditionsMap;
    }

    private String addConditionsToSqlQuery(Map<String, String> conditionsMap) {
        // adds the conditions in the where clause to the query
        List<String> sqlConditionsList = new ArrayList<>();
        conditionsMap.forEach((key, value) -> {
            if (key.startsWith("keyWord")) {
                String sqlCondition = "translate(lower(content), 'ąćęłńóśźż', 'acelnoszz') LIKE translate(lower(:%s), 'ąćęłńóśźż', 'acelnoszz')"
                        .formatted(key);
                sqlConditionsList.add(sqlCondition);
            }
        });

        String sqlConditionsString = String.join("\nOR ", sqlConditionsList);
        return sqlConditionsString;
    }

    private String getCompaniesFromMultiSelectComboBox(List<String> selectedCompanies) {
        // dodaje zmienne wiążące do klauzuli in
        String selectedCompaniesString = "";

        if (selectedCompanies.size() == 1) {
            selectedCompaniesString = "\nand ap.project_name in (:company1)";
        }

        else if (selectedCompanies.size() == 0) {
            selectedCompaniesString = "";
        }

        else if (selectedCompanies.size() > 1) {
            List<String> selectedCompaniesList = new ArrayList<>();

            for (int i = 0; i < selectedCompanies.size(); i++) {
                String companyBindVariable = ":company" + (i + 1);
                selectedCompaniesList.add(companyBindVariable);
            }

            String joinedCompaniesString = String.join(", ", selectedCompaniesList);
            selectedCompaniesString = "\nand ap.project_name in (%s)".formatted(joinedCompaniesString);
        }

        return selectedCompaniesString;
    }

    private Map<String, String> joinKeyWordsAndCompanies(Map<String, String> conditionsMap, List<String> selectedCompanies) {
        // adds a list with selected companies to the conditions map
        int i = 1;
        for (String company : selectedCompanies) {
            String companyString = "company%d".formatted(i);
            conditionsMap.put(companyString, company);
            i++;
        }

        return conditionsMap;
    }

    private String createSqlQuery(String conditions, String companies) {
        // creates a full SQL query using the SqlQuery class and selected conditions and companies
        SqlQuery query = new SqlQuery();

        String sql1 = query.getSqlQuery1();
        String sql2 = query.getSqlQuery2();
        String sql3 = query.getSqlQuery3();
//        System.out.println("sql1");
//        System.out.println(sql1);
//        System.out.println("sql2");
//        System.out.println(sql2);
//        System.out.println("sql3");
//        System.out.println(sql3);

        String sqlQueryString = sql1 + conditions + sql2 + companies + sql3;
        return sqlQueryString;
    }

    private MapSqlParameterSource convertToMapSqlParameterSource(Map<String, String> allConditionsMap) {
        // changes map to parameter map for SQL query
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
        sqlParameters.addValues(allConditionsMap);
        return sqlParameters;
    }

    private void ticketsHistoryHTML(List<TicketHistoryDto> results, List<String> keywords, List<String> selectedCompanies) {
        // creates an HTML file containing the results of the query and saves it
        // and runs it in the browser

        // create html string
        StringBuilder ticketContentHTML = new StringBuilder();

        for (TicketHistoryDto history : results) {
            ticketContentHTML.append("<p>")
                    .append(history.getTicket()).append(" ")
                    .append(history.getStatus()).append(" ")
                    .append(history.getCompany()).append(" ")
                    .append("[").append(history.getStatusChangeDate()).append("]").append("<br>")
                    .append(history.getPreviousStatus() != null ? history.getPreviousStatus() : "")
                    .append(" (").append(history.getPreviousUser()).append(")")
                    .append(" -> ")
                    .append(history.getNextStatus())
                    .append(" (").append(history.getNextUser() != null ? history.getNextUser() : "").append(")")
                    .append("<br><br>")
                    .append(history.getContent() != null ? history.getContent() : "")
                    .append("<br>")
                    .append("---------------------------<br></p>");
        }

        String htmlString = ticketContentHTML.toString();
        htmlString = htmlString.replace("<file:", "file:")
                .replace("\n", "<br>");

        // create web page
        WebPageElements page = new WebPageElements();
        String head1 = page.getHead1();
        String body1 = page.getBody1();
        String body2 = page.getBody2();

        StringBuilder wholePage = new StringBuilder();
        wholePage.append(head1);
        wholePage.append(body1);
        wholePage.append("<h3>Szukano wg: ");
        wholePage.append(keywords);
        wholePage.append("<br> w: ");
        wholePage.append(selectedCompanies);
        wholePage.append("</h3><br><br><br><br>");
        wholePage.append(htmlString);
        wholePage.append(body2);

        String ticketHistoryHTML = wholePage.toString();

        // save file and open
        try {
            // create file name
            String keywordsString = String.join("_", keywords);
            String selectedCompaniesString = String.join("_", selectedCompanies);
            String fileName = "historia_" + keywordsString + "_" + selectedCompaniesString + ".html";

            // write file with encoding UTF-8
            FileWriter fileWriter = new FileWriter(fileName, StandardCharsets.UTF_8);
            fileWriter.write(ticketHistoryHTML);
            fileWriter.close();
            System.out.println("Plik HTML został zapisany.");

            File file = new File(fileName);
            String filePath = file.getAbsolutePath();
            try {
                // open file in browser
                String os = System.getProperty("os.name").toLowerCase();
                ProcessBuilder processBuilder = new ProcessBuilder("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe", filePath);

                processBuilder.directory(new File("."));
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    System.out.println("exitCode: " + exitCode);
                    System.out.println("Obsłuż błąd, jeśli proces uruchamiania przeglądarki nie zakończył się pomyślnie");
                    System.out.println("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Wystąpił bład przy otwieraniu pliku w przeglądarce.");
                System.out.println("Sprawdź czy plik msedge.exe jest w katalogu:");
                System.out.println("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisywania pliku HTML.");
            e.printStackTrace();
        }

    }
}
