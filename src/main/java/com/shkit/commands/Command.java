package com.shkit.commands;

public interface Command {
    void execute(String[] args);
    String getDescription();
}
