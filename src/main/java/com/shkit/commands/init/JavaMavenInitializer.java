package com.shkit.commands.init;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JavaMavenInitializer implements ProjectInitializer {
    @Override
    public void initialize(String projectName, String[] options) {
        System.out.println("Creating Java (Maven) project: " + projectName);

        try {
            File projectDir = new File(projectName);
            if (projectDir.exists()) {
                System.out.println("Error: Directory '" + projectName + "'already exists");
                return;
            }
            
            createDirectoryStructure(projectDir);
            createPomXml(projectDir, projectName);
            createGitignore(projectDir);
            createReadme(projectDir, projectName);
            createMainClass(projectDir, projectName);

            System.out.println("Java projected created successfully");
            System.out.println("\nNext steps:");
            System.out.println("  cd " + projectName);
            System.out.println("  mvn clean install");
            System.out.println("  mvn exec:java");
            
        } catch (IOException e) {
            System.err.println("Error creating project: " + e.getMessage());
        }

    }

    private void createDirectoryStructure(File projectDir) {
        new File(projectDir, "src/main/java/com/example").mkdirs();
        new File(projectDir, "src/main/resources").mkdirs();
        new File(projectDir, "src/test/java/com/example").mkdirs();
        new File(projectDir, "src/test/resources").mkdirs();
        System.out.println(" Created directory structure");
    }

    private void createPomXml(File projectDir, String projectName) throws IOException {
        File pomFile = new File(projectDir, "pom.xml");
        try (FileWriter writer = new FileWriter(pomFile)) {
            writer.write("""
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0"
                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                         http://maven.apache.org/xsd/maven-4.0.0.xsd">
                    <modelVersion>4.0.0</modelVersion>
                    
                    <groupId>com.example</groupId>
                    <artifactId>%s</artifactId>
                    <version>1.0-SNAPSHOT</version>
                    
                    <properties>
                        <maven.compiler.source>17</maven.compiler.source>
                        <maven.compiler.target>17</maven.compiler.target>
                        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                    </properties>
                    
                    <dependencies>
                        <!-- JUnit 5 for testing -->
                        <dependency>
                            <groupId>org.junit.jupiter</groupId>
                            <artifactId>junit-jupiter</artifactId>
                            <version>5.10.1</version>
                            <scope>test</scope>
                        </dependency>
                    </dependencies>
                    
                    <build>
                        <plugins>
                            <plugin>
                                <groupId>org.codehaus.mojo</groupId>
                                <artifactId>exec-maven-plugin</artifactId>
                                <version>3.1.0</version>
                                <configuration>
                                    <mainClass>com.example.Main</mainClass>
                                </configuration>
                            </plugin>
                        </plugins>
                    </build>
                </project>
                """.formatted(projectName));
        }
        System.out.println("Created pom.xml");
    }

    private void createGitignore(File projectDir) throws IOException {
        File gitignore = new File(projectDir, ".gitignore");
        try (FileWriter writer = new FileWriter(gitignore)) {
            writer.write("""
                # Maven
                target/
                pom.xml.tag
                pom.xml.releaseBackup
                pom.xml.versionsBackup
                
                # IDE
                .idea/
                *.iml
                .vscode/
                .classpath
                .project
                .settings/
                
                # OS
                .DS_Store
                Thumbs.db
                """);
        }
        System.out.println("Created .gitignore");
    }

    private void createReadme(File projectDir, String projectName) throws IOException {
        File readme = new File(projectDir, "README.md");
        try (FileWriter writer = new FileWriter(readme)) {
            writer.write("""
                # %s
                
                A Java project created with shkit.
                
                ## Build
```bash
                mvn clean install
```
                
                ## Run
```bash
                mvn exec:java
```
                """.formatted(projectName));
        }
        System.out.println("Created README.md");
    }

    private void createMainClass(File projectDir, String projectName) throws IOException {
        File mainClass = new File(projectDir, "src/main/java/com/example/Main.java");
        try (FileWriter writer = new FileWriter(mainClass)) {
            writer.write("""
                package com.example;
                
                public class Main {
                    public static void main(String[] args) {
                        System.out.println("Hello from %s!");
                    }
                }
                """.formatted(projectName));
        }
        System.out.println("Created Main.java");
    }

    @Override
    public String getLanguageName() {
        return "java";
    }
}
