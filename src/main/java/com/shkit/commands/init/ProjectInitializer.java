package com.shkit.commands.init;

public interface ProjectInitializer {
    void initialize(String projectName, String[] options);
    String getLanguageName();
}
