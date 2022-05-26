package org.doctordrue.jira.clients.create_subtasks.client;

import java.net.URI;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.doctordrue.jira.clients.create_subtasks.client.auth.Base64AuthenticationHandler;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class JiraClientWrapper {

   private final JiraRestClient client;

   public JiraClientWrapper(URI server, String base64Credentials) {
      this.client = new AsynchronousJiraRestClientFactory().createWithAuthenticationHandler(server, new Base64AuthenticationHandler(base64Credentials));
   }

   public JiraClientWrapper(URI server, String username, String password) {
      this.client = new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(server, username, password);
   }

   public JiraRestClient getClient() {
      return client;
   }
}
