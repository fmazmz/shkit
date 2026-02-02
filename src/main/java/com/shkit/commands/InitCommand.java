package com.shkit.commands;

import com.shkit.commands.init.JavaMavenInitializer;
import com.shkit.commands.init.ProjectInitializer;

import java.util.HashMap;
import java.util.Map;

public class InitCommand implements Command {
    private final Map<String, ProjectInitializer> initializers;

    public InitCommand() {
        this.initializers = new HashMap<>();

        registerInitializer(new JavaMavenInitializer());
    }

    private void registerInitializer(ProjectInitializer initializer) {
        initializers.put(initializer.getLanguageName(), initializer);
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            showUsage();
            return;
        }

        String language = args[0].toLowerCase();
        String projectName = args[1];

        // Extract additional options if provided
        String[] options = new String[args.length - 2];
        System.arraycopy(args, 2, options, 0, options.length);

        ProjectInitializer initializer = initializers.get(language);

        if (initializer == null) {
            System.out.println("Error: Unsupported language '" + language + "'");
            System.out.println("Supported languages: " + String.join(", ", initializers.keySet()));
            return;
        }

        initializer.initialize(projectName, options);
    }

    private void showUsage() {
        System.out.println("Usage: init <language> <project-name> [options]");
        System.out.println("\nSupported languages:");
        for (String lang : initializers.keySet()) {
            System.out.println("  - " + lang);
        }
        System.out.println("\nExamples:");
        System.out.println("  init java myapp");
    }

    @Override
    public String getDescription() {
        return "Initialize a new project for various languages";
    }
}
