import java.io.*;
import java.util.HashSet;
import java.util.Set;
//Класс предоставляет методы для сжатия и распаковки текстового файла путем удаления повторяющихся строк.
public class TextFileCompressor {

    /*Сжимает текстовый файл, удаляя повторяющиеся строки.
     sourceFilePath - путь к исходному файлу.
     destinationFilePath - путь, где будет сохранен сжатый файл.
     */
    public static void compressFile(String sourceFilePath, String destinationFilePath) {
        try {
            int removedDuplicatesCount;
            // Открываем файл назначения для записи
            try ( // Открываем исходный файл для чтения
                    BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath)); // Открываем файл назначения для записи
                    BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath))) {
                // Множество для хранения уникальных строк
                Set<String> uniqueLines = new HashSet<>();
                String line;
                // Читаем каждую строку из исходного файла
                while ((line = reader.readLine()) != null) {
                    // Добавляем каждую строку в множество
                    uniqueLines.add(line);
                }   // Счетчик удаленных дублирующихся строк
                removedDuplicatesCount = 0;
                // Записываем уникальные строки в файл назначения
                for (String uniqueLine : uniqueLines) {
                    writer.write(uniqueLine);
                    writer.newLine();
                    removedDuplicatesCount++;
                }   // Закрываем записыватель и считыватель
                writer.flush();
            }

            // Записываем количество удаленных дублирующихся строк в отдельный файл счетчика
            BufferedWriter countWriter = new BufferedWriter(new FileWriter(destinationFilePath + ".count"));
            countWriter.write("Количество удаленных дублирующихся строк: " + removedDuplicatesCount);
            countWriter.flush();
            countWriter.close();

            System.out.println("Файл успешно сжат и сохранен в " + destinationFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Распаковывает сжатый файл, записывая его содержимое в новый файл.

    public static void decompressFile(String compressedFilePath, String restoredFilePath) {
        try {
            // Открываем сжатый файл для чтения
            BufferedReader reader = new BufferedReader(new FileReader(compressedFilePath));

            // Открываем восстановленный файл для записи
            BufferedWriter writer = new BufferedWriter(new FileWriter(restoredFilePath));

            String line;
            // Читаем каждую строку из сжатого файла
            while ((line = reader.readLine()) != null) {
                // Записываем каждую строку в восстановленный файл
                writer.write(line);
                writer.newLine();
            }

            // Закрываем записыватель и считыватель
            writer.flush();
            writer.close();
            reader.close();

            System.out.println("Файл успешно восстановлен и сохранен в " + restoredFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String sourceFilePath = "file.txt";
        String compressedFilePath = "compressed_file.txt";
        String restoredFilePath = "file2.txt";

        // Сжимаем исходный файл
        compressFile(sourceFilePath, compressedFilePath);

        // Распаковываем сжатый файл
        decompressFile(compressedFilePath, restoredFilePath);
    }
}
