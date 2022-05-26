package org.doctordrue.jira.clients.create_subtasks.client.auth;

import com.atlassian.httpclient.api.Request;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class Base64AuthenticationHandler implements AuthenticationHandler {

   private static final String AUTHORIZATION_HEADER = "Authorization";

   private final String encodedCredentials;

   public Base64AuthenticationHandler(String encodedCredentials) {
      this.encodedCredentials = encodedCredentials;
   }

   @Override
   public void configure(Request.Builder builder) {
      builder.setHeader(AUTHORIZATION_HEADER, "Basic " + encodedCredentials);
   }
}
