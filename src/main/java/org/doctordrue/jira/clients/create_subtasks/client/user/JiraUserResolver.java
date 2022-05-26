package org.doctordrue.jira.clients.create_subtasks.client.user;

import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicUser;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class JiraUserResolver {

   private final UserRestClient client;

   public JiraUserResolver(UserRestClient client) {
      this.client = client;
   }

   public BasicUser resolve(String name) {
      return client.getUser(name).fail(e -> {
                 throw new IllegalArgumentException("No user found for username = " + name, e);
              }).done(e -> System.out.println("JIRA user found: " + e.getDisplayName()))
              .claim();
   }
}
