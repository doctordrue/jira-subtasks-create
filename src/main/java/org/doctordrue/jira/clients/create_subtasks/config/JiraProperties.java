package org.doctordrue.jira.clients.create_subtasks.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Andrey_Barantsev
 * 12/14/2021
 **/
public class JiraProperties {

   private final URI server;
   private final String credentials;
   private final String username;
   private final String password;
   private final String issueTypeName;
   private final String projectKey;
   private final Path csvFolderPath;

   private static JiraProperties instance;

   private JiraProperties() throws URISyntaxException, IOException {
      Properties properties = new Properties();
      Path jiraPropertiesPath = AppProperties.getInstance().getJiraPropertiesPath();
      if (!Files.exists(jiraPropertiesPath) || Files.isDirectory(jiraPropertiesPath)) {
         throw new RuntimeException("File not found: " + jiraPropertiesPath);
      }
      properties.load(Files.newInputStream(jiraPropertiesPath));
      this.server = new URI(properties.getProperty("jira.server"));
      this.credentials = properties.getProperty("jira.credentials");
      this.username = properties.getProperty("jira.username");
      this.password = properties.getProperty("jira.password");
      this.issueTypeName = properties.getProperty("jira.issuetype.name");
      this.projectKey = properties.getProperty("jira.project.key");
      this.csvFolderPath = Paths.get(properties.getProperty("jira.stories.csv.path")).toAbsolutePath();
   }

   public static JiraProperties getInstance() throws URISyntaxException, IOException {
      if (instance == null) {
         instance = new JiraProperties();
      }
      return instance;
   }

   public String getCredentials() {
      return credentials;
   }

   public String getUsername() {
      return username;
   }

   public String getPassword() {
      return password;
   }

   public URI getServer() {
      return server;
   }

   public String getIssueTypeName() {
      return issueTypeName;
   }

   public String getProjectKey() {
      return projectKey;
   }

   public Path getCsvFolderPath() {
      return csvFolderPath;
   }
}
