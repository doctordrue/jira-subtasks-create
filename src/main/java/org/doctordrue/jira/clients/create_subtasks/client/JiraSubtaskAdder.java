package org.doctordrue.jira.clients.create_subtasks.client;

import java.util.List;

import org.doctordrue.jira.clients.create_subtasks.client.project.ProjectResolver;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.BulkOperationResult;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import org.doctordrue.jira.clients.create_subtasks.client.issuetype.IssueTypeResolver;
import org.doctordrue.jira.clients.create_subtasks.model.JiraSubTaskListInput;
import org.doctordrue.jira.clients.create_subtasks.transformer.JiraInputListTransformer;

/**
 * @author Andrey_Barantsev
 * 12/14/2021
 **/
public class JiraSubtaskAdder {

   private final JiraRestClient client;
   private final String projectKey;
   private final String subtaskTypeName;

   public JiraSubtaskAdder(JiraRestClient client, String projectKey, String subtaskTypeName) {
      this.client = client;
      this.projectKey = projectKey;
      this.subtaskTypeName = subtaskTypeName;
   }

   public BulkOperationResult<BasicIssue> add(JiraSubTaskListInput input) {
      Project project = new ProjectResolver(client.getProjectClient()).resolve(projectKey);
      IssueType issueType = new IssueTypeResolver(project).resolve(subtaskTypeName);
      List<IssueInput> subtasksInput = new JiraInputListTransformer(client, project, issueType).transform(input);
      return client.getIssueClient().createIssues(subtasksInput).claim();
   }

}
