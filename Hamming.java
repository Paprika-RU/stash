public class HammingNetwork {
    private int[][] prototypes; // Эталоны
    private int numPrototypes;  // Количество эталонов
    private int inputSize;      // Размер входа

    public HammingNetwork(int[][] prototypes) {
        this.prototypes = prototypes;
        this.numPrototypes = prototypes.length;
        this.inputSize = prototypes[0].length;
    }

    // Расстояние Хемминга между двумя векторами
    private int hammingDistance(int[] a, int[] b) {
        int distance = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) distance++;
        }
        return distance;
    }

    // Классификация входного вектора
    public int classify(int[] input) {
        int minDistance = Integer.MAX_VALUE;
        int bestIndex = -1;

        for (int i = 0; i < numPrototypes; i++) {
            int distance = hammingDistance(input, prototypes[i]);
            if (distance < minDistance) {
                minDistance = distance;
                bestIndex = i;
            }
        }

        return bestIndex; // Возвращает индекс эталона
    }

    // Пример использования
    public static void main(String[] args) {
        // Эталоны: 0, 1
        int[][] prototypes = {
            { // Цифра 0
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
            },
            { // Цифра 1
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
            }
        };

        HammingNetwork net = new HammingNetwork(prototypes);

        // Тестовый вектор (повреждённая "0")
        int[] testInput = {
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

        int result = net.classify(testInput);
        System.out.println("Распознанная цифра: " + result); // 0
    }
}
