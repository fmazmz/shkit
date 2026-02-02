package com.shkit.util;

import java.io.File;
import java.io.IOException;

public class ShellExecutor {
    private File currentDirectory;

    public ShellExecutor(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public void execute(String command) {
        // Check if cd
        String trimmed = command.trim();
        if (trimmed.startsWith("cd ")) {
            handleCd(trimmed.substring(3).trim());
            return;
        } else if (trimmed.equals("cd")) {
            handleCd(""); // cd with no args goes to homedir
            return;
        }

        try {
            ProcessBuilder pb = new ProcessBuilder();

            // windows
            if (isWindows()) pb.command("cmd.exe", "/c", command);

            // linux / mac (unix)
            else pb.command("sh", "-c", command);

            pb.directory(currentDirectory);
            pb.inheritIO();

            Process process = pb.start();
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error executing command");
        }
    }

    private void handleCd(String targetDir) {
        String dir = targetDir.isEmpty() ? System.getProperty("user.home") : targetDir;

        File newDir;

        if (dir.equals("~")) {
            newDir = new File(System.getProperty("user.home"));
        } else if (dir.startsWith("~")) {
            newDir = new File(System.getProperty("user.home"), dir.substring(1));
        } else {
            newDir = new File(currentDirectory, dir).getAbsoluteFile();
        }

        if (!newDir.exists() || !newDir.isDirectory()) {
            System.err.println("cd: " + dir + ": No such file or directory");
            return;
        }

        currentDirectory = newDir;
        System.setProperty("user.dir", newDir.getAbsolutePath());
    }
}
