package org.doctordrue.jira.clients.create_subtasks.transformer;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.BasicUser;
import com.atlassian.jira.rest.client.api.domain.IssueFieldId;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.google.common.collect.ImmutableMap;
import org.doctordrue.jira.clients.create_subtasks.client.user.JiraUserResolver;
import org.doctordrue.jira.clients.create_subtasks.model.JiraSubTaskInput;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class JiraInputTransformer {

   private final Project project;
   private final IssueType type;
   private final JiraRestClient client;
   private final BasicIssue story;

   public JiraInputTransformer(JiraRestClient client, Project project, BasicIssue story, IssueType type) {
      this.client = client;
      this.project = project;
      this.story = story;
      this.type = type;
   }

   public IssueInput transform(JiraSubTaskInput input) {
      BasicUser assignee = new JiraUserResolver(client.getUserClient()).resolve(input.getAssignee());
      IssueInputBuilder builder = new IssueInputBuilder(project, type)
              .setSummary(input.getSummary())
              .setDescription(input.getDescription())
              .setAssignee(assignee)
              .setFieldValue("parent", new ComplexIssueInputFieldValue(
                      ImmutableMap.of("key", story.getKey())))
              .setFieldValue(IssueFieldId.TIMETRACKING_FIELD.id, new ComplexIssueInputFieldValue(ImmutableMap.of(
                      "originalEstimate", input.getEstimation())));
      return builder.build();
   }

}
