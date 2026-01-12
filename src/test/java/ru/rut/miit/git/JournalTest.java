// JournalTest.java
package ru.rut.miit.git;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class JournalTest {
    private static final String JOURNAL_FILE = "journal.txt";
    private final Path journalPath = Paths.get(JOURNAL_FILE);

    @BeforeEach
    void setUp() throws IOException {
        // Создаем тестовый файл с известным содержимым
        Files.deleteIfExists(journalPath);
        Files.write(journalPath, List.of("Первая строка", "Вторая строка"));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(journalPath);
    }

    @Test
    void addEntry_shouldAddLineToFile() {
        assertDoesNotThrow(() -> Journal.addEntry("Новая тестовая запись"),
                "Метод addEntry не должен выбрасывать исключение");

        assertDoesNotThrow(() -> {
            long lineCount = Files.lines(journalPath).count();
            assertEquals(3, lineCount, "После добавления в файле должно быть 3 строки");
        });
    }

    @Test
    void listEntries_shouldReturnAllLines() throws IOException {
        List<String> lines = Journal.listEntries();
        assertEquals(2, lines.size(), "Метод listEntries должен возвращать все строки из файла");
    }
}