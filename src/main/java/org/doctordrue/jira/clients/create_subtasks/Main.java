package org.doctordrue.jira.clients.create_subtasks;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.BulkOperationErrorResult;
import com.atlassian.jira.rest.client.api.domain.BulkOperationResult;
import org.doctordrue.jira.clients.create_subtasks.client.Client;
import org.doctordrue.jira.clients.create_subtasks.client.JiraSubtaskAdder;
import org.doctordrue.jira.clients.create_subtasks.config.JiraProperties;
import org.doctordrue.jira.clients.create_subtasks.generator.JiraSubTaskListInputGenerator;
import org.doctordrue.jira.clients.create_subtasks.model.JiraSubTaskListInput;

/**
 * @author Andrey_Barantsev
 * 12/14/2021
 **/
public class Main {

   public static void main(String[] args) {
      try {
         Path filesPath = JiraProperties.getInstance().getCsvFolderPath();
         JiraSubTaskListInputGenerator generator = new JiraSubTaskListInputGenerator(filesPath, JiraProperties.getInstance().getProjectKey());
         List<JiraSubTaskListInput> storiesInput = generator.generate();
         System.out.println("Stories to process: " + storiesInput.size());
         try (JiraRestClient jiraClient = Client.getClient()) {
            for (JiraSubTaskListInput jiraSubTaskListInput : storiesInput) {
               System.out.println("Processing story: " + jiraSubTaskListInput.getStoryKey());
               try {
                  BulkOperationResult<BasicIssue> createdSubtasks = new JiraSubtaskAdder(jiraClient, JiraProperties.getInstance().getProjectKey(), JiraProperties.getInstance().getIssueTypeName())
                          .add(jiraSubTaskListInput);
                  for (BasicIssue subtask : createdSubtasks.getIssues()) {
                     System.out.println("JIRA sub-task created: " + subtask.getKey());
                  }
                  for (BulkOperationErrorResult error : createdSubtasks.getErrors()) {
                     System.out.println("ERROR during sub-task creation: " + error.getElementErrors());
                  }
               } catch (RuntimeException e) {
                  System.err.println("Error while adding tasks for story " + jiraSubTaskListInput.getStoryKey() + ": " + e.getLocalizedMessage());
                  e.printStackTrace();
                  System.out.println("Moving to next story...");
               }
            }
         }
      } catch (Exception e) {
         System.err.println("Error happened: " + e.getLocalizedMessage());
         e.printStackTrace();
      }
      Scanner scanner = new Scanner(System.in);
      System.out.println("Finished. Press enter to close...");
      scanner.nextLine();
   }
}