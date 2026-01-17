// Journal.java
package ru.rut.miit.git;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Journal {
    private static final String JOURNAL_FILE = "journal.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: java -jar journal.jar [add|list] ['текст записи']");
            return;
        }

        String command = args[0];
        try {
            switch (command) {
                case "add":
                    if (args.length < 2) {
                        System.out.println("Ошибка: для команды 'add' нужен текст записи.");
                        return;
                    }
                    String text = Stream.of(args).skip(1).collect(Collectors.joining(" "));
                    addEntry(text);
                    break;
                case "list":
                    listEntries();
                    break;
                default:
                    System.out.println("Неизвестная команда: " + command);
            }
        } catch (IOException e) {
            System.err.println("Произошла ошибка ввода-вывода: " + e.getMessage());
        }
    }

    public static void addEntry(String text) throws IOException {
        String now = LocalDateTime.now().format(FORMATTER);
        String entry = String.format("[%s] %s%n", now, text);
        Files.write(Paths.get(JOURNAL_FILE), entry.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("Запись успешно добавлена.");
    }

    public static List<String> listEntries() throws IOException {
        Path path = Paths.get(JOURNAL_FILE);
        if (!Files.exists(path)) {
            System.out.println("Дневник пуст.");
            return Collections.emptyList();
        }

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        System.out.println("--- Результаты поиска ---"); // Изменим заголовок для наглядности
        for (String line : lines) {
            // Имитируем простой поиск по содержимому.
            // Например, ищем записи, содержащие слово "тест".
            if (line.contains("тест")) {
                System.out.println(line);
            }
        }
        System.out.println("-------------------------");
        return lines;
    }
}