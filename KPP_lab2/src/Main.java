import Entities.*;
public class Main {
    public static void main(String[] args) {
        String fileName = "randomWords.txt";

        // Генеруємо файл з 100 випадковими словами
        EmailGenerator.generateRandomFile(fileName, 20);

        // Фільтруємо валідні електронні адреси
        System.out.println("Фільтрація валідних електронних адрес:");
        EmailFilter.filterValidEmails(fileName);
    }
}
