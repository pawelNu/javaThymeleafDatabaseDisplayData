package com.aplication.javaThymeleafDatabaseDisplayData.controller;

import com.aplication.javaThymeleafDatabaseDisplayData.dto.TicketHistoryDto;
import com.aplication.javaThymeleafDatabaseDisplayData.serwice.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ticket-history")
public class HistoryRestController {
    @Autowired
    private HistoryService historyService;

    //    http://localhost:8080/api/ticket-history
    @GetMapping("")
    public List<TicketHistoryDto> getTicketHistory(
            @RequestParam(value = "selectedOptions", required = false) List<String> selectedCompanies,
            @RequestParam(value = "hiddenField", required = false) List<String> keywords
    ) {
        selectedCompanies = new ArrayList<>();
        selectedCompanies.add("Stiedemann, Reilly and Raynor");

        keywords = new ArrayList<>();
        keywords.add("xyz");
//        System.out.println(selectedCompanies);
//        System.out.println(keywords);

        List<TicketHistoryDto> list = historyService.searchInTicketHistory2(selectedCompanies, keywords);
//        System.out.println("list");
//        System.out.println(list);

        return list;
    }
}
