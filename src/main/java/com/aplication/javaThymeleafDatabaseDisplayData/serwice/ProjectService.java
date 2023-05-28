package com.aplication.javaThymeleafDatabaseDisplayData.serwice;

import com.aplication.javaThymeleafDatabaseDisplayData.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProjectDto> getAllProjectNames() {
        // executes the query with the given parameters
        String dynamiczneZapytanie = "SELECT id, project_name as projectName FROM app_project order by project_name asc";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedParameterJdbcTemplate.query(dynamiczneZapytanie, getRowMapper());
    }

    private RowMapper<ProjectDto> getRowMapper() {
        // assigns the attributes of the TicketHistoryDto class with the values from the executed query
        return (resultSet, rowNum) -> {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setId(resultSet.getLong("id"));
            projectDto.setProjectName(resultSet.getString("projectName"));
            return projectDto;
        };
    }

    public List<String> assignProjectNameToId(List<Long> selectedProjects, List<ProjectDto> projectDtos) {
        List<String> projectNames = new ArrayList<>();

        for (Long projectId : selectedProjects) {
            // Find the project name from the project ID
            String projectName = findProjectNameById(projectId, projectDtos);
            projectNames.add(projectName);
        }
        return projectNames;
    }

    private String findProjectNameById(Long projectId, List<ProjectDto> projectDtos) {
        for (ProjectDto projectDto : projectDtos) {
            if (projectId.equals(projectDto.getId())) {
                return projectDto.getProjectName();
            }
        }
        // If no project name was found for the given project ID
        return "Project name not found for given ID";
    }
}
