This application is designed to automatically create sub-tasks in JIRA stories using JIRA REST API.
To execute the app:
1. Edit ./bin/config/jira.properties file to set JIRA server, auth token, project key, sub-task issue type name & path to folder with stories files (./bin/data by default)
2. Put stories .csv files into ./bin/data folder. File names should be the JIRA story key to add tasks to. See example file format in ./bin/data/template.csv
3. Run ./bin/create-subtasks.bat
4. See sub-tasks creation. Note that only files with <story key>.csv names will be processed.