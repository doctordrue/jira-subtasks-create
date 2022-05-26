package org.doctordrue.jira.clients.create_subtasks.client;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import org.doctordrue.jira.clients.create_subtasks.config.JiraProperties;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class Client {

   private static JiraClientWrapper client;

   public static JiraRestClient getClient() throws URISyntaxException, IOException {
      if (client == null) {
         if (StringUtils.isNotEmpty(JiraProperties.getInstance().getCredentials())) {
            System.out.println("Trying to authenticate using Base64 token...");
            client = new JiraClientWrapper(JiraProperties.getInstance().getServer(), JiraProperties.getInstance().getCredentials());
         } else {
            System.out.println("Trying to authenticate using username & password...");
            client = new JiraClientWrapper(JiraProperties.getInstance().getServer(), JiraProperties.getInstance().getUsername(), JiraProperties.getInstance().getPassword());
         }
      }
      return client.getClient();
   }

}
