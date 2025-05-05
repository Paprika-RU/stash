Класс сети Хопфилда

import java.util.Arrays;

public class HopfieldNetwork {
    private int size; // Количество нейронов
    private int[][] weights; // Матрица весов

    public HopfieldNetwork(int size) {
        this.size = size;
        this.weights = new int[size][size];
    }

    // Обучение на одном образе (бинарные пиксели 0/1)
    public void train(int[] pattern) {
        int[] binaryPattern = new int[size];
        for (int i = 0; i < size; i++) {
            binaryPattern[i] = 2 * pattern[i] - 1; // Преобразуем в {-1, 1}
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    weights[i][j] = 0; // Нет самовоздействия
                } else {
                    weights[i][j] += binaryPattern[i] * binaryPattern[j];
                }
            }
        }
    }

    // Восстановление повреждённого образа
    public int[] recall(int[] damagedPattern, int maxIterations) {
        int[] currentPattern = Arrays.copyOf(damagedPattern, size);
        boolean stable;

        for (int iter = 0; iter < maxIterations; iter++) {
            stable = true;
            int[] newPattern = new int[size];

            for (int i = 0; i < size; i++) {
                int sum = 0;
                for (int j = 0; j < size; j++) {
                    sum += weights[i][j] * (2 * currentPattern[j] - 1);
                }
                newPattern[i] = (sum >= 0) ? 1 : 0;

                if (newPattern[i] != currentPattern[i]) {
                    stable = false;
                }
            }

            if (stable) {
                System.out.println("Стабилизировалась за " + iter + " итераций");
                return newPattern;
            }
            currentPattern = newPattern;
        }

        return currentPattern;
    }

    // Печать изображения
    public static void printImage(int[] pattern) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(pattern[i * 10 + j] == 1 ? "█" : " ");
            }
            System.out.println();
        }
    }
}



Пример использования

public class Main {
    public static void main(String[] args) {
        HopfieldNetwork network = new HopfieldNetwork(100);

        // Эталонные цифры (0 и 1 в виде 10x10)
        int[] zero = {
            0, 1, 1, 1, 1, 1, 1, 1, 1, 0,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            0, 1, 1, 1, 1, 1, 1, 1, 1, 0
        };

        int[] one = {
            0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 1, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 0, 0, 0
        };

        // Обучение сети
        network.train(zero);
        network.train(one);

        // Повреждённый "0" (часть пикселей удалена)
        int[] damagedZero = {
            0, 1, 1, 1, 0, 0, 1, 1, 1, 0,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            0, 1, 1, 1, 1, 1, 1, 1, 1, 0
        };

        System.out.println("Повреждённый '0':");
        HopfieldNetwork.printImage(damagedZero);

        // Восстановление
        int[] recovered = network.recall(damagedZero, 100);

        System.out.println("\nВосстановленный '0':");
        HopfieldNetwork.printImage(recovered);
    }
}
