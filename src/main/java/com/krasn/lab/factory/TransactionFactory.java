package com.krasn.lab.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krasn.lab.controller.FilesController;
import com.krasn.lab.controller.OutputController;
import com.krasn.lab.exception.InvalidTransactionException;
import com.krasn.lab.model.Card;
import com.krasn.lab.model.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionFactory {

    public static List<List<Transaction>> load(final String path) {
        return FilesController.loadFiles(path).entrySet()
                .stream()
                .map(e -> parseTransaction(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private static List<Transaction> parseTransaction(final String file, final String content) {
        try {
            final List<Transaction> result = new LinkedList<>();
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode root = objectMapper.readTree(content);
            root.forEach(e -> {
                result.add(new Transaction(file, e.get("from").asText(),
                        e.get("to").asText(), e.get("amount").asDouble()));
            });
            return result;
        } catch (JsonProcessingException e) {
            OutputController.error(file, "Cannot parse transaction");
        }
        return List.of();
    }

}
