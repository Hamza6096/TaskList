package com.codurance.training.tasks;
import java.util.HashMap;
import java.util.Map;

public enum Command {
    ADD("add"),
    CHECK("check"),
    HELP("help"),
    QUIT("quit"),
    SHOW("show"),
    UNCHECK("uncheck");

    private final String name;

    Command(String name) {
        this.name = name;
    }

    static final Map<String, Command> commandsByName = new HashMap<>();

    static {
        for (Command command : values()) {
            commandsByName.put(command.getName(), command);
        }
    }

    public static Command fromName(String name) {
        return commandsByName.get(name.toLowerCase());
    }

    public String getName() {
        return name;
    }
}
