package com.org.shkit;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("""
                     _     _    _ _  \s
                 ___| |__ | | _(_) |_\s
                / __| '_ \\| |/ / | __|
                \\__ \\ | | |   <| | |_\s
                |___/_| |_|_|\\_\\_|\\__|
                """);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("shkit> ");
            String input = scanner.nextLine();
        }
    }
}
