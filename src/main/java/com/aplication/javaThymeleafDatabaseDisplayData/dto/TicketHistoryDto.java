package com.aplication.javaThymeleafDatabaseDisplayData.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TicketHistoryDto {
    // class with attributes of query results
    private String ticket;
    private String status;
    private String company;
    private String statusChangeDate;
    private String previousStatus;
    private String previousUser;
    private String nextStatus;
    private String nextUser;
    private String content;
}
