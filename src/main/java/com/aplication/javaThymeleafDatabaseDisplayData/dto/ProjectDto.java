package com.aplication.javaThymeleafDatabaseDisplayData.dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProjectDto {
    // the class is created based on the previously executed query
    // is used to add a values to a multi-select drop-down list
    @Id
    private Long id;
    private String projectName;

    @Override
    public String toString() {
        return id + "=" + projectName;
    }
}
