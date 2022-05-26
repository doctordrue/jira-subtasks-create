package org.doctordrue.jira.clients.create_subtasks.model;

import com.opencsv.bean.CsvBindByName;

/**
 * @author Andrey_Barantsev
 * 12/14/2021
 **/
public class JiraSubTaskInput {

   @CsvBindByName(required = true, column = "task")
   private String summary;

   @CsvBindByName()
   private String description;

   @CsvBindByName(required = true, column = "assignee")
   private String assignee;

   @CsvBindByName(required = true, column = "estimate")
   private String estimation;

   public String getSummary() {
      return summary;
   }

   public String getDescription() {
      return description;
   }

   public String getAssignee() {
      return assignee;
   }

   public String getEstimation() {
      return estimation;
   }
}
