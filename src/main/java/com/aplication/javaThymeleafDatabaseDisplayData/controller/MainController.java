package com.aplication.javaThymeleafDatabaseDisplayData.controller;

import com.aplication.javaThymeleafDatabaseDisplayData.dto.ProjectDto;
import com.aplication.javaThymeleafDatabaseDisplayData.serwice.HistoryService;
import com.aplication.javaThymeleafDatabaseDisplayData.serwice.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ticket-history-app")
public class MainController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private HistoryService historyService;

    @GetMapping("") // http://localhost:8080/ticket-history-app
    public String showMainPage(Model model) {

        List<ProjectDto> projectNames = projectService.getAllProjectNames();
        model.addAttribute("projectNames", projectNames);
        return "index.html";
    }

    @PostMapping("") // http://localhost:8080/ticket-history-app
    public String showMainPageAfterSearch(Model model,
                                          @RequestParam(value = "selectedOptions", required = false) List<Long> selectedProjectsForm,
                                          @RequestParam(value = "hiddenField") List<String> keywords) {
        try {
            List<ProjectDto> projectNames = projectService.getAllProjectNames();

            List<Long> selectedProjects = historyService.checkIfSelectedProjectsAreNull(selectedProjectsForm);
            List<String> selectedProjectsNames = projectService.assignProjectNameToId(selectedProjects, projectNames);

            historyService.searchInTicketHistory(selectedProjectsNames, keywords);

            model.addAttribute("projectNames", projectNames);

        } catch (DataAccessException ex) {
            model.addAttribute("errorMessage1", "An error occurred while downloading data: ");
            model.addAttribute("errorMessage2", "!!! NO KEYWORD WAS PROVIDED !!!");
            model.addAttribute("errorMessage3", ex.getMessage());
            return "error.html";
        }
        return "index.html";
    }
}
