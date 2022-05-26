package org.doctordrue.jira.clients.create_subtasks.config;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class AppProperties {

   private final Path jiraPropertiesPath;
   private static AppProperties instance;

   private AppProperties() throws IOException {
      Properties properties = new Properties();
      properties.load(getClass().getClassLoader().getResourceAsStream("app.properties"));
      this.jiraPropertiesPath = Paths.get(properties.getProperty("jira.properties.path")).toAbsolutePath();
   }

   public static AppProperties getInstance() throws IOException {
      if (instance == null) {
         instance = new AppProperties();
      }
      return instance;
   }

   public Path getJiraPropertiesPath() {
      return jiraPropertiesPath;
   }
}
