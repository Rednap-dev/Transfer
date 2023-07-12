package com.krasn.lab.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krasn.lab.controller.FilesController;
import com.krasn.lab.model.Card;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CardFactory {

    public static List<Card> loadCards(final String folder) {
        final Map.Entry<String, String> content =
                FilesController.readFileContent(new File(String.format("%s\\cards.json", folder)));
        return parseCard(content.getValue());
    }

    private static List<Card> parseCard(final String content) {
        try {
            final List<Card> result = new LinkedList<>();
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode root = objectMapper.readTree(content);
            root.forEach(e -> {
                result.add(new Card(e.get("id").asText(), e.get("amount").asDouble()));
            });
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
