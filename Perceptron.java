import java.util.Arrays;

public class Perceptron {
    private double[] weights;
    private double learningRate;

    public Perceptron(int inputSize, double learningRate) {
        this.learningRate = learningRate;
        this.weights = new double[inputSize + 1]; // +1 для смещения
        Arrays.fill(weights, 0); // Инициализация нулями
    }

    // Активационная функция ступенчатая
    private int step(double x) {
        return x >= 0 ? 1 : 0;
    }

    // Предсказание
    public int predict(double[] inputs) {
        double sum = weights[0]; // Смещение
        for (int i = 0; i < inputs.length; i++) {
            sum += weights[i + 1] * inputs[i];
        }
        return step(sum);
    }

    // Обучение на одном примере
    public void train(double[] inputs, int target) {
        int prediction = predict(inputs);
        int error = target - prediction;

        // Корректировка весов
        weights[0] += learningRate * error; // Обновление смещения
        for (int i = 0; i < inputs.length; i++) {
            weights[i + 1] += learningRate * error * inputs[i];
        }
    }

    public static void main(String[] args) {
        // Данные для обучения ("и", "или", "не")
        double[][] andInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        int[] andTargets = {0, 0, 0, 1};

        double[][] orInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        int[] orTargets = {0, 1, 1, 1};

        double[][] notInputs = {{0}, {1}};
        int[] notTargets = {1, 0};

        // Обучаем перцептрон для "и"
        Perceptron andPerceptron = new Perceptron(2, 0.1);
        for (int epoch = 0; epoch < 10; epoch++) {
            for (int i = 0; i < andInputs.length; i++) {
                andPerceptron.train(andInputs[i], andTargets[i]);
            }
        }

        // Обучаем перцептрон для "или"
        Perceptron orPerceptron = new Perceptron(2, 0.1);
        for (int epoch = 0; epoch < 10; epoch++) {
            for (int i = 0; i < orInputs.length; i++) {
                orPerceptron.train(orInputs[i], orTargets[i]);
            }
        }

        // Обучаем перцептрон для "не"
        Perceptron notPerceptron = new Perceptron(1, 0.1);
        for (int epoch = 0; epoch < 10; epoch++) {
            for (int i = 0; i < notInputs.length; i++) {
                notPerceptron.train(notInputs[i], notTargets[i]);
            }
        }

        // Тестирование "и"
        System.out.println("AND:");
        for (double[] input : andInputs) {
            System.out.println(Arrays.toString(input) + " -> " + andPerceptron.predict(input));
        }

        // Тестирование "или"
        System.out.println("\nOR:");
        for (double[] input : orInputs) {
            System.out.println(Arrays.toString(input) + " -> " + orPerceptron.predict(input));
        }

        // Тестирование "не"
        System.out.println("\nNOT:");
        for (double[] input : notInputs) {
            System.out.println(Arrays.toString(input) + " -> " + notPerceptron.predict(input));
        }
    }
}
