Реализация сети встречного распространения

import java.util.Arrays;
import java.util.Random;

public class CounterpropagationNetwork {
    private double[][] kohonenWeights; // Веса слоя Кохонена
    private double[][] grossbergWeights; // Веса слоя Гроссберга
    private int inputSize; // 100 (10x10)
    private int kohonenSize; // Число нейронов Кохонена
    private double learningRateKohonen;
    private double learningRateGrossberg;
    private Random random = new Random();

    public CounterpropagationNetwork(int inputSize, int kohonenSize, 
                                   double lrKohonen, double lrGrossberg) {
        this.inputSize = inputSize;
        this.kohonenSize = kohonenSize;
        this.learningRateKohonen = lrKohonen;
        this.learningRateGrossberg = lrGrossberg;

        // Инициализация весов случайными значениями
        kohonenWeights = new double[kohonenSize][inputSize];
        grossbergWeights = new double[kohonenSize][inputSize];
        
        for (int i = 0; i < kohonenSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                kohonenWeights[i][j] = random.nextDouble();
                grossbergWeights[i][j] = random.nextDouble();
            }
        }
    }

    // Обучение сети встречного распространения
    public void train(double[] input, int targetClass, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            // 1. Фаза Кохонена: находим победителя
            int winner = findWinner(input);

            // 2. Корректировка весов Кохонена (обучение без учителя)
            for (int i = 0; i < inputSize; i++) {
                kohonenWeights[winner][i] += 
                    learningRateKohonen * (input[i] - kohonenWeights[winner][i]);
            }

            // 3. Корректировка весов Гроссберга (обучение с учителем)
            for (int i = 0; i < inputSize; i++) {
                grossbergWeights[winner][i] += 
                    learningRateGrossberg * (input[i] - grossbergWeights[winner][i]);
            }
        }
    }

    // Нахождение нейрона-победителя в слое Кохонена
    private int findWinner(double[] input) {
        int winner = 0;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < kohonenSize; i++) {
            double distance = 0;
            for (int j = 0; j < inputSize; j++) {
                distance += Math.pow(input[j] - kohonenWeights[i][j], 2);
            }
            if (distance < minDistance) {
                minDistance = distance;
                winner = i;
            }
        }
        return winner;
    }

    // Предсказание цифры
    public int predict(double[] input) {
        return findWinner(input);
    }

    // Восстановление идеального образа
    public double[] reconstruct(double[] input) {
        int winner = findWinner(input);
        return grossbergWeights[winner];
    }
}



Обучение на цифрах

public class Main {
    public static void main(String[] args) {
        // Параметры сети
        int inputSize = 100; // 10x10
        int kohonenSize = 10; // 10 цифр (0-9)
        double lrKohonen = 0.1;
        double lrGrossberg = 0.01;
        int epochs = 1000;

        // Создаем сеть
        CounterpropagationNetwork cpn = 
            new CounterpropagationNetwork(inputSize, kohonenSize, lrKohonen, lrGrossberg);

        // Загружаем данные
        double[][] digits = loadDigits(); // Метод загрузки данных
        int[] labels = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}; // Метки классов

        // Обучение
        for (int i = 0; i < digits.length; i++) {
            cpn.train(digits[i], labels[i], epochs);
        }

        // Тестирование
        double[] testDigit = digits[0]; // Тестовая цифра
        int predicted = cpn.predict(testDigit);
        System.out.println("Распознанная цифра: " + predicted);

        // Восстановление образа
        double[] reconstructed = cpn.reconstruct(testDigit);
        System.out.println("Восстановленный образ: " + Arrays.toString(reconstructed));
    }

    // Загрузка данных (заглушка)
    private static double[][] loadDigits() {
        // В действительности здесь должна быть загрузка из файла или генерация
        return new double[][] {
            new double[100], // Цифра 0
            new double[100], // Цифра 1
            // ...
        };
    }
}

// Сеть встречного распространения — мощнее, обучается быстрее, устойчива к шуму, но сложнее в реализации
// Перцептрон — проще, но годится только для элементарных задач
