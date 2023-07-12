package com.krasn.lab.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OutputController {

    private static final Map<String, List<OutputInfo>> OUTPUT = new HashMap<>();

    public static void success(final String file, final String text) {
        log(file, text, false);
    }

    public static void error(final String file, final String text) {
        log(file, text, true);
    }

    public static void clear() {
        OUTPUT.clear();
    }

    public static void saveReport() {
        FilesController.writeToFile("C:\\Users\\trane\\OneDrive\\Рабочий стол\\Java\\lab\\src\\main\\resources\\report.txt", getFullReport());
    }

    public static String getFullReport() {
        return OUTPUT.keySet().stream()
                .map(OutputController::getRawOutput)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static void log(final String file, final String text, final boolean error) {
        final List<OutputInfo> records = OUTPUT.getOrDefault(file, new LinkedList<>());
        records.add(new OutputInfo(text, error));
        OUTPUT.put(file, records);
    }

    private static String getRawOutput(final String file) {
        if(!OUTPUT.containsKey(file)) {
            return new String();
        }

        return OUTPUT.get(file).stream()
                .map(e -> String.format("(%s) %s", file, e.toString()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private record OutputInfo(String text, boolean error) {

        @Override
        public String toString() {
            return String.format("%s -> %s", error ? "Error" : "Success", text);
        }

    }

}
