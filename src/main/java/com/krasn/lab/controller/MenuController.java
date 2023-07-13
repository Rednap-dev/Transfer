package com.krasn.lab.controller;

import com.krasn.lab.factory.TransactionFactory;
import com.krasn.lab.holder.CardHolder;
import com.krasn.lab.model.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStreamReader;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuController {

    private static final Scanner SCANNER = new Scanner(new InputStreamReader(System.in));

    public static void run() {
        int option = -1;
        while (true) {
            showMenu();
            option = select();
            if (option > 0 && option <= 3) {
                perform(option);
            }
        }
    }

    @SneakyThrows
    private static void showMenu() {
        System.out.println("1) Load data and perform transactions");
        System.out.println("2) Show report file content");
        System.out.println("3) Exit");
    }

    private static int select() {
        final String input = SCANNER.nextLine();
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void perform(final int option) {
        switch (option) {
            case 1:
                OutputController.clear();
                CardHolder.load("src/main/resources");
                TransactionFactory.load("src/main/resources/input").stream()
                        .forEach(e -> e.forEach(Transaction::perform));
                OutputController.saveReport();
                break;
            case 2:
                System.out.println(OutputController.getFullReport());
                break;
            case 3:
                System.exit(0);
                break;
        }
    }

}
