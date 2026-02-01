package com.org.shkit;

import java.io.IOException;

public class ShellExecutor {

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public void execute(String command) {
        try {
            ProcessBuilder pb = new ProcessBuilder();

            // windows
            if (isWindows()) pb.command("cmd.exe", "/c", command);

            // linux / mac (unix)
            else pb.command("sh", "-c", command);

            pb.inheritIO();

            Process process = pb.start();
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error executing command");
        }
    }
}
