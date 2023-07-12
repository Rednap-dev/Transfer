package com.krasn.lab.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FilesController {

    private static String sourceDirectoryPath;

    public static Map<String, String> loadFiles(final String directoryPath) {
        sourceDirectoryPath = directoryPath;

        return Arrays.stream(loadFilesFromSourceDirectory())
                .collect(Collectors.toMap(f -> f.getName(), f -> readFileContent(f).getValue()));
    }

    public static void writeToFile(final String path, final String content) {
        try {
            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Cannot write to output file: " + path);
        }
    }

    public static Map.Entry<String, String> readFileContent(final File file) {
        try {
            return Map.entry(file.getName(), new String(Files.readAllBytes(file.toPath())));
        } catch (IOException e) {
            return Map.entry("", "");
        }
    }

    private static File[] loadFilesFromSourceDirectory() {
        if (Objects.isNull(sourceDirectoryPath)) {
            return new File[]{};
        }

        final File directory = new File(sourceDirectoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            return new File[]{};
        }

        return Arrays.stream(directory.listFiles())
                .filter(f -> f.isFile() && f.getName().endsWith(".json"))
                .toArray(File[]::new);
    }

}
