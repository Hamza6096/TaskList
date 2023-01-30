package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.codurance.training.tasks.Command.commandsByName;


public final class TaskList implements Runnable {
    private static final String ADD_PROJECT = "project";
    private static final String ADD_TASK = "task";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final BufferedReader reader;
    private final PrintWriter writer;
    private Integer lastId = 0;

        public static void main(String[] args){
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(System.out);
            new TaskList(in, out).run();
        }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void run() {
        while (true) {
            writer.print("> ");
            writer.flush();
            String commandLine = null;
            try {
                commandLine = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String cmd = commandLine.split(" ")[0];
            Command command = commandsByName.get(cmd);
            if (command != null) {
                switch (command) {
                    case QUIT:
                        return;
                    case SHOW:
                        show();
                        break;
                    case ADD:
                        add(commandLine);
                        break;
                    case CHECK:
                        check(commandLine.split(" ")[1]);
                        break;
                    case UNCHECK:
                        uncheck(commandLine.split(" ")[1]);
                        break;
                    case HELP:
                        help();
                        break;
                }
            }
        }
    }



    private void add(String commandLine) {
        String[] cmdAndRest = commandLine.split(" ", 3);
        String cmd = cmdAndRest[1];
        if (ADD_PROJECT.equals(cmd)) {
            addProject(cmdAndRest[2]);
        }
        if (ADD_TASK.equals(cmd)) {
            String[] projectTask = cmdAndRest[2].split(" ", 2);
            addTask(projectTask[0], projectTask[1]);
        }
    }


        private void addProject(String name) {
            tasks.put(name, new ArrayList<>());
        }


        private void addTask(String project, String description) {
            List<Task> projectTasks = tasks.get(project);
            if (projectTasks == null) {
                writer.printf("Could not find a project with the name \"%s\".", project);
                writer.println();
                return;
            }
            projectTasks.add(new Task(nextId(), description, false));
        }


        private void check(String idString) {
            setDone(idString, Boolean.TRUE);
        }


        private void uncheck(String idString) {
            setDone(idString, false);
        }


        private void setDone(String idString, Boolean done) {
            int id = Integer.parseInt(idString);
            for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
                for (Task task : project.getValue()) {
                    if (task.compareId(id)) {
                        task.completeTaskDone();
                        return;
                    }
                }
            }
            writer.printf("Could not find a task with an ID of %d.", id);
            writer.println();
        }


        private void show() {
            for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
                writer.println(project.getKey());
                for (Task task : project.getValue()) {
                    writer.printf( "    [%c] %d: %s%n", (task.taskDone() ? 'x' : ' '), task.getId(), task.getDescription());
                }
                writer.println();
            }
        }


        private void help() {
            writer.println("Commands:");
            writer.println("  show");
            writer.println("  add project <project name>");
            writer.println("  add task <project name> <task description>");
            writer.println("  check <task ID>");
            writer.println("  uncheck <task ID>");
            writer.println("  help");
            writer.println("  quit");
        }

        private void error(String command) {
            writer.printf("I don't know what the command \\\"%s\\\" is.", command);
            writer.println();
        }

        private Integer nextId() {
            return ++lastId;
        }
    }




