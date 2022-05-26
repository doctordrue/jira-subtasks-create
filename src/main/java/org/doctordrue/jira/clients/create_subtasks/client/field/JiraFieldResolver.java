package org.doctordrue.jira.clients.create_subtasks.client.field;

import java.util.stream.Stream;

import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.domain.Field;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class JiraFieldResolver {

   private final MetadataRestClient client;

   public JiraFieldResolver(MetadataRestClient client) {
      this.client = client;
   }

   public Field resolve(String name) {
      Stream.Builder<Field> streamBuilder = Stream.builder();
      client.getFields().claim().forEach(streamBuilder::add);
      return streamBuilder.build().filter(f -> f.getName().equals(name))
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException("Unable to find field name = " + name));
   }

}
