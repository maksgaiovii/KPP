package Entities;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class EmailGenerator {
    private static final String[] RANDOM_WORDS = {
            "hello", "world", "example", "test", "java", "email", "random", "text", "info", "user", "admin"
    };

    private static final String[] DOMAINS = {
            "gmail.com", "yahoo.com", "outlook.com", "example.com"
    };

    public static void generateRandomFile(String fileName, int count) {
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();

            for (int i = 0; i < count; i++) {
                if (random.nextBoolean()) {
                    // Генеруємо валідну електронну адресу
                    String email = RANDOM_WORDS[random.nextInt(RANDOM_WORDS.length)] + "@" + DOMAINS[random.nextInt(DOMAINS.length)];
                    writer.write(email + "\n");
                } else if (random.nextBoolean()) {
                    // Генеруємо випадкове слово з додаванням знака '@' у випадкове місце
                    String randomWord = RANDOM_WORDS[random.nextInt(RANDOM_WORDS.length)] + random.nextInt(100);
                    int insertAt = random.nextInt(randomWord.length());  // Випадкова позиція для '@'
                    String wordWithAt = new StringBuilder(randomWord).insert(insertAt, "@").toString();
                    writer.write(wordWithAt + "\n");
                } else {
                    // Генеруємо випадкове слово без '@'
                    String word = RANDOM_WORDS[random.nextInt(RANDOM_WORDS.length)] + random.nextInt(100);
                    writer.write(word + "\n");
                }
            }

            System.out.println("Файл успішно згенеровано!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
