package com.aplication.javaThymeleafDatabaseDisplayData.controller;

import com.aplication.javaThymeleafDatabaseDisplayData.dto.ProjectDto;
import com.aplication.javaThymeleafDatabaseDisplayData.serwice.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {

    @Autowired
    private ProjectService projectService;

    //    http://localhost:8080/api/projects
    @GetMapping("")
    public List<ProjectDto> getAllCompanies() {
        return projectService.getAllProjectNames();
    }
}
