package org.doctordrue.jira.clients.create_subtasks.client.project;

import com.atlassian.jira.rest.client.api.ProjectRestClient;
import com.atlassian.jira.rest.client.api.domain.Project;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class ProjectResolver {

   private final ProjectRestClient client;

   public ProjectResolver(ProjectRestClient client) {
      this.client = client;
   }

   public Project resolve(String projectKey) {
      return client.getProject(projectKey).fail(e -> {
                 throw new IllegalArgumentException("No project found for key " + projectKey, e);
              }).done(p -> System.out.println("JIRA Project found: " + p.getName()))
              .claim();
   }

}
