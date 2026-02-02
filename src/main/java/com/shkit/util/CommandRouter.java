package com.shkit.util;

import com.shkit.commands.Command;
import com.shkit.commands.HelpCommand;
import com.shkit.commands.InitCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandRouter {
    private final Map<String, Command> customCommands;
    private final ShellExecutor shellExecutor;

    public CommandRouter() {
        this.customCommands = new HashMap<>();
        this.shellExecutor = new ShellExecutor();

        registerCommand("help", new HelpCommand(this));
        registerCommand("init", new InitCommand());
    }

    private void registerCommand(String name, Command command) {
        customCommands.put(name, command);
    }

    public Map<String, Command> getCustomCommands() {
        return customCommands;
    }

    public void route(String input) {
        // Split command and argument
        String[] parts = input.trim().split("\\s+", 2);
        String commandName = parts[0];
        String arg = parts.length > 1 ? parts[1] : "";

        if (customCommands.containsKey(commandName)) {
            String[] args = arg.isEmpty() ? new String[0] : arg.split("\\s+");
            customCommands.get(commandName).execute(args);
        }
        else {
            shellExecutor.execute(input);
        }
    }
}