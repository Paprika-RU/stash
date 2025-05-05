Класс многослойного перцептрона

import java.util.Random;

public class MLP {
    private double[][] hiddenWeights; // веса скрытого слоя
    private double[] outputWeights;   // веса выходного слоя
    private double learningRate;
    private Random random = new Random();

    public MLP(int inputSize, int hiddenSize, double learningRate) {
        this.learningRate = learningRate;
        
        // Инициализация весов случайными значениями
        hiddenWeights = new double[hiddenSize][inputSize + 1]; // +1 для смещения
        outputWeights = new double[hiddenSize + 1];            // +1 для смещения
        
        for (int i = 0; i < hiddenSize; i++) {
            for (int j = 0; j <= inputSize; j++) {
                hiddenWeights[i][j] = random.nextDouble() * 2 - 1; // [-1, 1]
            }
        }
        
        for (int i = 0; i <= hiddenSize; i++) {
            outputWeights[i] = random.nextDouble() * 2 - 1;
        }
    }

    // Сигмоида
    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    // Производная сигмоиды
    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    // Прямое распространение (предсказание)
    public double predict(double[] inputs) {
        // Активация скрытого слоя
        double[] hiddenOutputs = new double[hiddenWeights.length + 1];
        hiddenOutputs[0] = 1.0; // смещение
        
        for (int i = 0; i < hiddenWeights.length; i++) {
            double sum = hiddenWeights[i][0]; // смещение
            for (int j = 0; j < inputs.length; j++) {
                sum += hiddenWeights[i][j + 1] * inputs[j];
            }
            hiddenOutputs[i + 1] = sigmoid(sum);
        }
        
        // Активация выходного слоя
        double outputSum = outputWeights[0]; // смещение
        for (int i = 1; i < outputWeights.length; i++) {
            outputSum += outputWeights[i] * hiddenOutputs[i];
        }
        
        return sigmoid(outputSum);
    }

    // Обучение
    public void train(double[] inputs, double target) {
        // Прямое распространение
        double[] hiddenOutputs = new double[hiddenWeights.length + 1];
        hiddenOutputs[0] = 1.0; // смещение
        
        for (int i = 0; i < hiddenWeights.length; i++) {
            double sum = hiddenWeights[i][0]; // смещение
            for (int j = 0; j < inputs.length; j++) {
                sum += hiddenWeights[i][j + 1] * inputs[j];
            }
            hiddenOutputs[i + 1] = sigmoid(sum);
        }
        
        double outputSum = outputWeights[0]; // смещение
        for (int i = 1; i < outputWeights.length; i++) {
            outputSum += outputWeights[i] * hiddenOutputs[i];
        }
        double output = sigmoid(outputSum);
        
        // Обратное распространение ошибки
        double outputError = target - output;
        double outputDelta = outputError * sigmoidDerivative(output);
        
        // Обновление весов выходного слоя
        outputWeights[0] += learningRate * outputDelta; // смещение
        for (int i = 1; i < outputWeights.length; i++) {
            outputWeights[i] += learningRate * outputDelta * hiddenOutputs[i];
        }
        
        // Ошибка скрытого слоя
        double[] hiddenErrors = new double[hiddenWeights.length];
        for (int i = 0; i < hiddenWeights.length; i++) {
            hiddenErrors[i] = outputDelta * outputWeights[i + 1];
        }
        
        // Обновление весов скрытого слоя
        for (int i = 0; i < hiddenWeights.length; i++) {
            double hiddenDelta = hiddenErrors[i] * sigmoidDerivative(hiddenOutputs[i + 1]);
            hiddenWeights[i][0] += learningRate * hiddenDelta; // смещение
            for (int j = 0; j < inputs.length; j++) {
                hiddenWeights[i][j + 1] += learningRate * hiddenDelta * inputs[j];
            }
        }
    }
}



Обучение "исключающего или" и проверка "и"

public class Main {
    public static void main(String[] args) {
        MLP mlp = new MLP(2, 2, 0.1); // 2 входа, 2 нейрона в скрытом слое
        
        // Данные для "исключающего или"
        double[][] xorInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[] xorTargets = {0, 1, 1, 0};
        
        // Обучение "исключающего или" (5000 эпох)
        for (int epoch = 0; epoch < 5000; epoch++) {
            for (int i = 0; i < xorInputs.length; i++) {
                mlp.train(xorInputs[i], xorTargets[i]);
            }
        }
        
        // Проверка "исключающего или"
        System.out.println("XOR:");
        for (double[] input : xorInputs) {
            double output = mlp.predict(input);
            System.out.println(Arrays.toString(input) + " -> " + Math.round(output));
        }
        
        // Проверка "и" (должно работать, так как это линейно разделимо)
        System.out.println("\nAND:");
        double[][] andInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        for (double[] input : andInputs) {
            double output = mlp.predict(input);
            System.out.println(Arrays.toString(input) + " -> " + (output > 0.5 ? 1 : 0));
        }
    }
}
