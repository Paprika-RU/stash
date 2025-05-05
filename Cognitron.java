import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class Cognitron extends JFrame {

    private static final int INPUT_SIZE = 28; // Размер входного изображения
    private static final int NUM_CLASSES = 10; // Количество распознаваемых классов
    private static final int HIDDEN_LAYERS = 3; // Количество скрытых слоев
    private static final int HIDDEN_NEURONS = 64; // Нейронов в каждом скрытом слое

    private double[][][] weights; // Веса сети [слой][нейрон][вес]
    private double[][] biases; // Смещения [слой][нейрон]
    private DrawingPanel drawingPanel;
    private JLabel resultLabel;

    public Cognitron() {
        setTitle("Когнитрон - распознавание образов");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Инициализация нейронной сети
        initializeNetwork();

        // Панель для рисования
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        // Панель управления
        JPanel controlPanel = new JPanel();
        JButton recognizeButton = new JButton("Распознать");
        JButton clearButton = new JButton("Очистить");
        JButton trainButton = new JButton("Обучить");
        resultLabel = new JLabel("Нарисуйте цифру", SwingConstants.CENTER);

        recognizeButton.addActionListener(e -> recognizeDrawing());
        clearButton.addActionListener(e -> drawingPanel.clear());
        trainButton.addActionListener(e -> trainNetwork());

        controlPanel.add(recognizeButton);
        controlPanel.add(clearButton);
        controlPanel.add(trainButton);
        add(controlPanel, BorderLayout.SOUTH);
        add(resultLabel, BorderLayout.NORTH);
    }

    private void initializeNetwork() {
        Random random = new Random();
        weights = new double[HIDDEN_LAYERS + 1][][];
        biases = new double[HIDDEN_LAYERS + 1][];

        // Инициализация входного слоя
        weights[0] = new double[HIDDEN_NEURONS][INPUT_SIZE * INPUT_SIZE];
        biases[0] = new double[HIDDEN_NEURONS];

        // Инициализация скрытых слоев
        for (int i = 1; i < HIDDEN_LAYERS; i++) {
            weights[i] = new double[HIDDEN_NEURONS][HIDDEN_NEURONS];
            biases[i] = new double[HIDDEN_NEURONS];
        }

        // Инициализация выходного слоя
        weights[HIDDEN_LAYERS] = new double[NUM_CLASSES][HIDDEN_NEURONS];
        biases[HIDDEN_LAYERS] = new double[NUM_CLASSES];

        // Заполнение случайными значениями
        for (int layer = 0; layer < weights.length; layer++) {
            for (int neuron = 0; neuron < weights[layer].length; neuron++) {
                for (int w = 0; w < weights[layer][neuron].length; w++) {
                    weights[layer][neuron][w] = random.nextGaussian() * 0.1;
                }
                biases[layer][neuron] = random.nextGaussian() * 0.1;
            }
        }
    }

    private double[] feedForward(double[] input) {
        double[] currentActivations = Arrays.copyOf(input, input.length);

        for (int layer = 0; layer < weights.length; layer++) {
            double[] nextActivations = new double[weights[layer].length];

            for (int neuron = 0; neuron < weights[layer].length; neuron++) {
                double sum = biases[layer][neuron];

                for (int w = 0; w < weights[layer][neuron].length; w++) {
                    sum += weights[layer][neuron][w] * currentActivations[w];
                }

                nextActivations[neuron] = sigmoid(sum);
            }

            currentActivations = nextActivations;
        }

        return softmax(currentActivations);
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private double[] softmax(double[] x) {
        double[] result = new double[x.length];
        double sum = 0.0;

        // Вычисляем экспоненты и их сумму
        for (int i = 0; i < x.length; i++) {
            result[i] = Math.exp(x[i]);
            sum += result[i];
        }

        // Нормализуем
        for (int i = 0; i < result.length; i++) {
            result[i] /= sum;
        }

        return result;
    }

    private void trainNetwork() {
        // В реальном приложении здесь должна быть загрузка обучающего набора данных
        // Для примера создадим искусственные данные
        double[][][] trainingData = generateTrainingData();
        double[][] trainingLabels = generateTrainingLabels();

        // Параметры обучения
        int epochs = 10;
        double learningRate = 0.1;

        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int sample = 0; sample < trainingData.length; sample++) {
                // Прямое распространение
                double[] activations = trainingData[sample];
                double[][] layerActivations = new double[weights.length + 1][];
                double[][] layerSums = new double[weights.length][];
                layerActivations[0] = activations;

                for (int layer = 0; layer < weights.length; layer++) {
                    layerSums[layer] = new double[weights[layer].length];
                    layerActivations[layer + 1] = new double[weights[layer].length];

                    for (int neuron = 0; neuron < weights[layer].length; neuron++) {
                        double sum = biases[layer][neuron];

                        for (int w = 0; w < weights[layer][neuron].length; w++) {
                            sum += weights[layer][neuron][w] * layerActivations[layer][w];
                        }

                        layerSums[layer][neuron] = sum;
                        layerActivations[layer + 1][neuron] = sigmoid(sum);
                    }
                }

                // Обратное распространение ошибки
                double[] output = layerActivations[layerActivations.length - 1];
                double[] expected = trainingLabels[sample];
                double[] error = new double[output.length];

                for (int i = 0; i < output.length; i++) {
                    error[i] = output[i] - expected[i];
                }

                for (int layer = weights.length - 1; layer >= 0; layer--) {
                    double[] nextError = new double[weights[layer][0].length];
                    double[] gradient = new double[weights[layer].length];

                    // Вычисляем градиенты для текущего слоя
                    for (int neuron = 0; neuron < weights[layer].length; neuron++) {
                        gradient[neuron] = error[neuron] * sigmoidDerivative(layerSums[layer][neuron]);

                        // Обновляем веса и смещения
                        biases[layer][neuron] -= learningRate * gradient[neuron];

                        for (int w = 0; w < weights[layer][neuron].length; w++) {
                            double delta = learningRate * gradient[neuron] * layerActivations[layer][w];
                            weights[layer][neuron][w] -= delta;
                            nextError[w] += weights[layer][neuron][w] * gradient[neuron];
                        }
                    }

                    error = nextError;
                }
            }
            System.out.println("Эпоха " + (epoch + 1) + " завершена");
        }
    }

    private double sigmoidDerivative(double x) {
        double s = sigmoid(x);
        return s * (1 - s);
    }

    private double[][][] generateTrainingData() {
        // Генерация искусственных обучающих данных (в реальном приложении нужно загружать реальные данные)
        double[][][] data = new double[NUM_CLASSES * 10][INPUT_SIZE * INPUT_SIZE];
        Random random = new Random();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = random.nextDouble() > 0.7 ? 1.0 : 0.0;
            }
        }

        return data;
    }

    private double[][] generateTrainingLabels() {
        // Генерация искусственных меток (one-hot encoding)
        double[][] labels = new double[NUM_CLASSES * 10][NUM_CLASSES];
        
        for (int i = 0; i < labels.length; i++) {
            int digit = i % NUM_CLASSES;
            labels[i][digit] = 1.0;
        }
        
        return labels;
    }

    private void recognizeDrawing() {
        // Получаем изображение с панели рисования
        BufferedImage image = drawingPanel.getImage();
        double[] input = new double[INPUT_SIZE * INPUT_SIZE];

        // Преобразуем изображение в входной вектор
        for (int y = 0; y < INPUT_SIZE; y++) {
            for (int x = 0; x < INPUT_SIZE; x++) {
                int rgb = image.getRGB(x * image.getWidth() / INPUT_SIZE, 
                                      y * image.getHeight() / INPUT_SIZE);
                input[y * INPUT_SIZE + x] = (rgb & 0xff) < 128 ? 1.0 : 0.0;
            }
        }

        // Прямое распространение через сеть
        double[] output = feedForward(input);

        // Находим класс с максимальной вероятностью
        int predictedClass = 0;
        double maxProb = output[0];
        for (int i = 1; i < output.length; i++) {
            if (output[i] > maxProb) {
                maxProb = output[i];
                predictedClass = i;
            }
        }

        resultLabel.setText("Распознано: " + predictedClass + " (вероятность: " + String.format("%.2f", maxProb * 100) + "%)");
    }

    class DrawingPanel extends JPanel {
        private BufferedImage image;
        private Graphics2D graphics;

        public DrawingPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(280, 280));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            image = new BufferedImage(280, 280, BufferedImage.TYPE_INT_RGB);
            graphics = image.createGraphics();
            clear();
        }

        public void clear() {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
            graphics.setColor(Color.BLACK);
            graphics.setStroke(new BasicStroke(10));
            repaint();
        }

        public BufferedImage getImage() {
            return image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }

        @Override
        protected void processMouseEvent(MouseEvent e) {
            if (e.getID() == MouseEvent.MOUSE_PRESSED || 
                e.getID() == MouseEvent.MOUSE_DRAGGED) {
                graphics.fillOval(e.getX() - 5, e.getY() - 5, 10, 10);
                repaint();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Cognitron cognitron = new Cognitron();
            cognitron.setVisible(true);
        });
    }
}
