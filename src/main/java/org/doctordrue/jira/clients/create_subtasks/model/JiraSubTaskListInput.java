package org.doctordrue.jira.clients.create_subtasks.model;

import java.util.List;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class JiraSubTaskListInput {

   private final String storyKey;
   private final List<JiraSubTaskInput> subTasks;

   private JiraSubTaskListInput(String storyKey, List<JiraSubTaskInput> subTasks) {
      this.storyKey = storyKey;
      this.subTasks = subTasks;
   }

   public static JiraSubTaskListInput of(String parentStoryKey, List<JiraSubTaskInput> subTasks) {
      return new JiraSubTaskListInput(parentStoryKey, subTasks);
   }

   public String getStoryKey() {
      return storyKey;
   }

   public List<JiraSubTaskInput> getSubTasks() {
      return subTasks;
   }
}
