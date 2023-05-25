package com.aplication.javaThymeleafDatabaseDisplayData.serwice;

import com.aplication.javaThymeleafDatabaseDisplayData.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

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
        String dynamiczneZapytanie = "SELECT distinct project_name as projectName FROM app_project order by project_name asc";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedParameterJdbcTemplate.query(dynamiczneZapytanie, getRowMapper());
    }

    private RowMapper<ProjectDto> getRowMapper() {
        // assigns the attributes of the TicketHistoryDto class with the values from the executed query
        return (resultSet, rowNum) -> {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setProjectName(resultSet.getString("projectName"));
            return projectDto;
        };
    }
}
