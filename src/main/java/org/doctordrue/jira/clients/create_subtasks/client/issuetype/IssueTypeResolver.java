package org.doctordrue.jira.clients.create_subtasks.client.issuetype;

import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Project;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class IssueTypeResolver {

   private final Project project;

   public IssueTypeResolver(Project project) {
      this.project = project;
   }

   public IssueType resolve(String name) {
      for (IssueType type : project.getIssueTypes()) {
         if (type.getName().equals(name)) {
            System.out.println("JIRA type found: " + type.getName());
            return type;
         }
      }
      throw new IllegalArgumentException("No such issue type found: issue type name = " + name + ", project key = " + project.getKey());
   }

}
