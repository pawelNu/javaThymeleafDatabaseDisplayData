package com.aplication.javaThymeleafDatabaseDisplayData.additonal;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SqlQuery {
    // class with parts of sql query
    // which will be used to query the database
    private String sqlQuery1 = """
            select ath.ticket_id as ticket
            , as3.status_name as status
            , ap.project_name as project
            , ath.status_change_date as statusChangeDate
            , as1.status_name as previousStatus
            , au1.username as previousUser
            , as2.status_name as nextStatus
            , au2.username as nextUser
            , ath.content as content
            from app_tickets_history ath
            left join app_tickets at on at.id = ath.ticket_id
            left join app_project ap on ap.id = at.project_id
            left join app_users au1 on ath.previous_user_id = au1.id
            left join app_users au2 on ath.next_user_id = au2.id
            left join app_status as1 on ath.previous_status_id = as1.id
            left join app_status as2 on ath.next_status_id = as2.id
            left join app_status as3 on as3.id = at.tickets_status_id
            where ath.ticket_id in (
                                    select distinct ath.ticket_id
                                    from app_tickets_history ath
                                    where
            """;
    private String sqlQuery2 = "\n                       )";
    private String sqlQuery3 = "\norder by ath.id asc, ath.status_change_date asc";

}
