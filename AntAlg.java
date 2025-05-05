import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class AntColonyTSP extends JFrame {

    private static final int NUM_CITIES = 20;
    private static final int NUM_ANTS = 30;
    private static final int MAX_ITERATIONS = 100;
    private static final double ALPHA = 1.0; // Вес феромона
    private static final double BETA = 2.0; // Вес видимости (обратное расстояние)
    private static final double RHO = 0.1; // Коэффициент испарения
    private static final double Q = 500.0; // Константа для феромона

    private City[] cities;
    private double[][] distances;
    private double[][] pheromones;
    private Ant[] ants;
    private int[] bestTour;
    private double bestTourLength = Double.MAX_VALUE;

    public AntColonyTSP() {
        setTitle("Муравьиный алгоритм для задачи коммивояжера");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Инициализация городов
        initializeCities();
        
        // Вычисление матрицы расстояний
        calculateDistances();
        
        // Инициализация феромонов
        initializePheromones();
        
        // Создание муравьев
        initializeAnts();
        
        // Запуск алгоритма
        runACO();
        
        // Добавление панели для отображения
        add(new TSPPanel());
    }

    private void initializeCities() {
        cities = new City[NUM_CITIES];
        Random rand = new Random();
        
        for (int i = 0; i < NUM_CITIES; i++) {
            int x = 50 + rand.nextInt(700);
            int y = 50 + rand.nextInt(500);
            cities[i] = new City(x, y, "City " + i);
        }
    }

    private void calculateDistances() {
        distances = new double[NUM_CITIES][NUM_CITIES];
        for (int i = 0; i < NUM_CITIES; i++) {
            for (int j = 0; j < NUM_CITIES; j++) {
                if (i != j) {
                    double dx = cities[i].x - cities[j].x;
                    double dy = cities[i].y - cities[j].y;
                    distances[i][j] = Math.sqrt(dx*dx + dy*dy);
                } else {
                    distances[i][j] = 0.0;
                }
            }
        }
    }

    private void initializePheromones() {
        pheromones = new double[NUM_CITIES][NUM_CITIES];
        double initialPheromone = 1.0 / NUM_CITIES;
        
        for (int i = 0; i < NUM_CITIES; i++) {
            for (int j = 0; j < NUM_CITIES; j++) {
                if (i != j) {
                    pheromones[i][j] = initialPheromone;
                }
            }
        }
    }

    private void initializeAnts() {
        ants = new Ant[NUM_ANTS];
        for (int i = 0; i < NUM_ANTS; i++) {
            ants[i] = new Ant(NUM_CITIES);
        }
    }

    private void runACO() {
        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            // Размещаем муравьев в случайных городах
            placeAntsRandomly();
            
            // Построение маршрутов
            constructSolutions();
            
            // Обновление феромонов
            updatePheromones();
            
            // Сохранение лучшего маршрута
            updateBestTour();
            
            System.out.println("Итерация " + iter + ", лучшая длина: " + bestTourLength);
        }
    }

    private void placeAntsRandomly() {
        Random rand = new Random();
        for (Ant ant : ants) {
            ant.clear();
            int startCity = rand.nextInt(NUM_CITIES);
            ant.visitCity(startCity);
        }
    }

    private void constructSolutions() {
        for (int step = 1; step < NUM_CITIES; step++) {
            for (Ant ant : ants) {
                int nextCity = selectNextCity(ant);
                ant.visitCity(nextCity);
            }
        }
    }

    private int selectNextCity(Ant ant) {
        int currentCity = ant.getCurrentCity();
        
        // Вычисление вероятностей для всех непосещенных городов
        double[] probabilities = new double[NUM_CITIES];
        double total = 0.0;
        
        for (int i = 0; i < NUM_CITIES; i++) {
            if (!ant.visited(i)) {
                double pheromone = Math.pow(pheromones[currentCity][i], ALPHA);
                double visibility = Math.pow(1.0 / distances[currentCity][i], BETA);
                probabilities[i] = pheromone * visibility;
                total += probabilities[i];
            }
        }
        
        // Рулеточный выбор следующего города
        double rand = Math.random() * total;
        double sum = 0.0;
        
        for (int i = 0; i < NUM_CITIES; i++) {
            if (!ant.visited(i)) {
                sum += probabilities[i];
                if (sum >= rand) {
                    return i;
                }
            }
        }
        
        // Если что-то пошло не так (не должно происходить)
        for (int i = 0; i < NUM_CITIES; i++) {
            if (!ant.visited(i)) {
                return i;
            }
        }
        return -1;
    }

    private void updatePheromones() {
        // Испарение феромонов
        for (int i = 0; i < NUM_CITIES; i++) {
            for (int j = 0; j < NUM_CITIES; j++) {
                if (i != j) {
                    pheromones[i][j] *= (1.0 - RHO);
                }
            }
        }
        
        // Добавление нового феромона
        for (Ant ant : ants) {
            double contribution = Q / ant.getTourLength(distances);
            int[] tour = ant.getTour();
            
            for (int i = 0; i < NUM_CITIES - 1; i++) {
                int from = tour[i];
                int to = tour[i + 1];
                pheromones[from][to] += contribution;
                pheromones[to][from] += contribution; // Симметричный феромон
            }
        }
    }

    private void updateBestTour() {
        for (Ant ant : ants) {
            double tourLength = ant.getTourLength(distances);
            if (tourLength < bestTourLength) {
                bestTourLength = tourLength;
                bestTour = ant.getTour().clone();
            }
        }
    }

    class City {
        int x, y;
        String name;
        
        City(int x, int y, String name) {
            this.x = x;
            this.y = y;
            this.name = name;
        }
    }

    class Ant {
        private int[] tour;
        private boolean[] visited;
        private int tourIndex;
        
        Ant(int numCities) {
            tour = new int[numCities];
            visited = new boolean[numCities];
            tourIndex = 0;
        }
        
        void clear() {
            Arrays.fill(visited, false);
            tourIndex = 0;
        }
        
        void visitCity(int city) {
            tour[tourIndex++] = city;
            visited[city] = true;
        }
        
        boolean visited(int city) {
            return visited[city];
        }
        
        int getCurrentCity() {
            return tour[tourIndex - 1];
        }
        
        int[] getTour() {
            return tour;
        }
        
        double getTourLength(double[][] distances) {
            double length = 0.0;
            for (int i = 0; i < tour.length - 1; i++) {
                length += distances[tour[i]][tour[i + 1]];
            }
            // Возвращаемся в начальный город
            length += distances[tour[tour.length - 1]][tour[0]];
            return length;
        }
    }

    class TSPPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Рисуем города
            g2d.setColor(Color.BLUE);
            for (City city : cities) {
                g2d.fillOval(city.x - 5, city.y - 5, 10, 10);
                g2d.drawString(city.name, city.x + 10, city.y + 5);
            }
            
            // Рисуем лучший маршрут
            if (bestTour != null) {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(2));
                
                for (int i = 0; i < bestTour.length - 1; i++) {
                    City from = cities[bestTour[i]];
                    City to = cities[bestTour[i + 1]];
                    g2d.drawLine(from.x, from.y, to.x, to.y);
                }
                // Замыкаем маршрут
                City first = cities[bestTour[0]];
                City last = cities[bestTour[bestTour.length - 1]];
                g2d.drawLine(last.x, last.y, first.x, first.y);
                
                // Отображаем длину маршрута
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.format("Лучший маршрут: %.2f", bestTourLength), 20, 20);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AntColonyTSP tsp = new AntColonyTSP();
            tsp.setVisible(true);
        });
    }
}
