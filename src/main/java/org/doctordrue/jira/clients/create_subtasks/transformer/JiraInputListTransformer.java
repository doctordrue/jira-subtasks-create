package org.doctordrue.jira.clients.create_subtasks.transformer;

import java.util.List;
import java.util.stream.Collectors;

import org.doctordrue.jira.clients.create_subtasks.client.issue.IssueResolver;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;

import org.doctordrue.jira.clients.create_subtasks.model.JiraSubTaskListInput;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class JiraInputListTransformer {

   private final Project project;
   private final IssueType type;
   private final JiraRestClient client;

   public JiraInputListTransformer(JiraRestClient client, Project project, IssueType type) {
      this.client = client;
      this.project = project;
      this.type = type;
   }

   public List<IssueInput> transform(JiraSubTaskListInput input) {
      BasicIssue story = new IssueResolver(client.getIssueClient()).resolve(input.getStoryKey());
      return input.getSubTasks()
              .stream()
              .map(s -> new JiraInputTransformer(client, project, story, type).transform(s))
              .collect(Collectors.toList());
   }

}
