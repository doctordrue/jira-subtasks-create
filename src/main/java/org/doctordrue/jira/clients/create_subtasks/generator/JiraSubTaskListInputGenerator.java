package org.doctordrue.jira.clients.create_subtasks.generator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.doctordrue.jira.clients.create_subtasks.model.JiraSubTaskInput;
import org.doctordrue.jira.clients.create_subtasks.model.JiraSubTaskListInput;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * @author Andrey_Barantsev
 * 12/15/2021
 **/
public class JiraSubTaskListInputGenerator {

   private final Path storyFilesPath;
   private final String projectKey;

   public JiraSubTaskListInputGenerator(Path storyFilesPath, String projectKey) {
      this.storyFilesPath = storyFilesPath;
      this.projectKey = projectKey;
   }

   public List<JiraSubTaskListInput> generate() throws IOException {
      if (!Files.exists(storyFilesPath)) {
         throw new FileNotFoundException("Directory not found: " + storyFilesPath);
      }
      List<Path> files = Files.list(storyFilesPath).filter(f -> !Files.isDirectory(f)).collect(Collectors.toList());
      List<JiraSubTaskListInput> stories = new ArrayList<>();
      for (Path filePath : files) {
         System.out.println("Reading file " + filePath.toString());
         String fileName = filePath.getFileName().toFile().getName();
         String storyKeyRegex = "^" + projectKey + "-\\d+\\.csv$";
         if (fileName.matches(storyKeyRegex)) {
            System.out.println("File is a story. Reading & adding to batch...");
            String storyKey = fileName.replaceAll("\\.csv", "");
            Reader reader = Files.newBufferedReader(filePath);
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .build();
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .build();
            CsvToBeanBuilder<JiraSubTaskInput> beanBuilder = new CsvToBeanBuilder<>(csvReader);
            List<JiraSubTaskInput> subtasks = beanBuilder.withType(JiraSubTaskInput.class)
                    .build()
                    .parse();

            JiraSubTaskListInput subtasksInput = JiraSubTaskListInput.of(storyKey, subtasks);
            stories.add(subtasksInput);
         } else {
            System.out.println("File is not a story file. Skipping...");
         }
      }
      return stories;
   }

}
