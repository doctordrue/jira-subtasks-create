package org.doctordrue.jira.clients.create_subtasks.client.issue;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class IssueResolver {

   private final IssueRestClient client;

   public IssueResolver(IssueRestClient client) {
      this.client = client;
   }

   public Issue resolve(String key) {
      return client.getIssue(key)
              .fail(e -> {
                 throw new IllegalArgumentException("No such issue found: issueKey = " + key, e);
              })
              .done(e -> System.out.println("JIRA story found: " + e.getSummary()))
              .claim();
   }

}
