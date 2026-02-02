package com.shkit.commands;

import com.shkit.util.CommandRouter;

import java.util.Map;

public class Help implements Command{
    private final CommandRouter router;

    public Help(CommandRouter router) {
        this.router = router;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("shkit - A shell-wrapper with custom commands for developer automation\n");

        System.out.println("Custom commands:");

        Map<String, Command> commands = router.getCustomCommands();
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            System.out.println("  " + entry.getKey() + " - " + entry.getValue().getDescription());
        }

        System.out.println("\nAll other OS shell commands are passed to your OS shell.\n");
    }

    @Override
    public String getDescription() {
        return "Show available shkit commands";
    }
}
