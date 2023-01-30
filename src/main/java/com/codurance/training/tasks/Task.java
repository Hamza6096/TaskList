
package com.codurance.training.tasks;


import java.util.List;
import java.util.Map;

public final class Task {
    private final int id;
    private final String description;
    private Boolean done;

    public Task(Integer id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public boolean compareId(int id){
        if(this.id == id){
            return true;
        }
        return false;
    }


public int getId(){
        return id;
}

    @Override
    public String toString() {
        return String.format("    [%c] %d: %s%n", (done ? 'x' : ' '), id, description);
    }

    public String getDescription() {
        return description;
    }

    public boolean taskDone() {
        return done;
    }

    public void completeTaskDone() {
        this.done = true;
    }

    private void show(Map<String, List<Task>> tasks) {
        tasks.forEach((project, projectTasks) -> {
            System.out.println(project);
            projectTasks.forEach(task ->
                    System.out.printf("    [%c] %d: %s%n",
                            (task.taskDone() ? 'x' : ' '), task.getId(), task.getDescription()));
            System.out.println();
        });
    }
}
