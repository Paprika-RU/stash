import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BioSystemSimulation extends JFrame {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PLANT_ENERGY = 50;
    private final int HERBIVORE_ENERGY = 100;
    private final int PREDATOR_ENERGY = 150;
    private final int MAX_ENTITIES = 200;

    private SimulationPanel panel;
    private Timer timer;
    private Random random = new Random();

    private List<Plant> plants = new CopyOnWriteArrayList<>();
    private List<Herbivore> herbivores = new CopyOnWriteArrayList<>();
    private List<Predator> predators = new CopyOnWriteArrayList<>();

    public BioSystemSimulation() {
        setTitle("Модель биоценоза: Растения-Травоядные-Хищники");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new SimulationPanel();
        add(panel);

        JButton startButton = new JButton("Старт");
        startButton.addActionListener(e -> startSimulation());
        add(startButton, BorderLayout.SOUTH);

        initializeEntities();
    }

    private void initializeEntities() {
        for (int i = 0; i < 30; i++) {
            plants.add(new Plant(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }
        for (int i = 0; i < 10; i++) {
            herbivores.add(new Herbivore(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }
        for (int i = 0; i < 3; i++) {
            predators.add(new Predator(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }
    }

    private void startSimulation() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSimulation();
                panel.repaint();
            }
        });
        timer.start();
    }

    private void updateSimulation() {
        // Обновляем всех агентов
        for (Herbivore herbivore : herbivores) {
            herbivore.update(plants, herbivores, predators);
        }
        for (Predator predator : predators) {
            predator.update(herbivores, predators);
        }

        // Размножение и смерть
        handleReproductionAndDeath();

        // Добавляем новые растения
        if (random.nextDouble() < 0.1 && plants.size() < MAX_ENTITIES) {
            plants.add(new Plant(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }
    }

    private void handleReproductionAndDeath() {
        // Растения
        Iterator<Plant> plantIter = plants.iterator();
        while (plantIter.hasNext()) {
            Plant plant = plantIter.next();
            if (plant.getEnergy() <= 0) {
                plantIter.remove();
            }
        }

        // Травоядные
        Iterator<Herbivore> herbIter = herbivores.iterator();
        while (herbIter.hasNext()) {
            Herbivore herb = herbIter.next();
            if (herb.getEnergy() <= 0) {
                herbIter.remove();
            } else if (herb.getEnergy() > HERBIVORE_ENERGY * 1.5) {
                herb.setEnergy(HERBIVORE_ENERGY);
                if (herbivores.size() < MAX_ENTITIES) {
                    herbivores.add(new Herbivore(herb.x + random.nextInt(21) - 10, 
                                              herb.y + random.nextInt(21) - 10));
                }
            }
        }

        // Хищники
        Iterator<Predator> predIter = predators.iterator();
        while (predIter.hasNext()) {
            Predator pred = predIter.next();
            if (pred.getEnergy() <= 0) {
                predIter.remove();
            } else if (pred.getEnergy() > PREDATOR_ENERGY * 1.5) {
                pred.setEnergy(PREDATOR_ENERGY);
                if (predators.size() < MAX_ENTITIES / 5) {
                    predators.add(new Predator(pred.x + random.nextInt(21) - 10, 
                                            pred.y + random.nextInt(21) - 10));
                }
            }
        }
    }

    class SimulationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Рисуем растения
            g.setColor(Color.GREEN);
            for (Plant plant : plants) {
                g.fillOval(plant.x - 2, plant.y - 2, 5, 5);
            }
            
            // Рисуем травоядных
            g.setColor(Color.BLUE);
            for (Herbivore herb : herbivores) {
                g.fillRect(herb.x - 3, herb.y - 3, 7, 7);
            }
            
            // Рисуем хищников
            g.setColor(Color.RED);
            for (Predator pred : predators) {
                g.fillOval(pred.x - 4, pred.y - 4, 9, 9);
            }
            
            // Статистика
            g.setColor(Color.BLACK);
            g.drawString("Растения: " + plants.size(), 10, 20);
            g.drawString("Травоядные: " + herbivores.size(), 10, 40);
            g.drawString("Хищники: " + predators.size(), 10, 60);
        }
    }

    // Базовый класс для всех агентов
    abstract class Entity {
        int x, y;
        double energy;
        
        Entity(int x, int y, double initialEnergy) {
            this.x = x;
            this.y = y;
            this.energy = initialEnergy;
        }
        
        double getEnergy() { return energy; }
        void setEnergy(double energy) { this.energy = energy; }
        
        void moveRandomly() {
            x += random.nextInt(5) - 2;
            y += random.nextInt(5) - 2;
            
            // Ограничиваем движение в пределах панели
            x = Math.max(0, Math.min(WIDTH, x));
            y = Math.max(0, Math.min(HEIGHT, y));
            
            energy -= 0.1;
        }
    }

    // Класс растения
    class Plant extends Entity {
        Plant(int x, int y) {
            super(x, y, PLANT_ENERGY);
        }
    }

    // Класс травоядного с перцептроном
    class Herbivore extends Entity {
        private Perceptron brain;
        
        Herbivore(int x, int y) {
            super(x, y, HERBIVORE_ENERGY);
            this.brain = new Perceptron(5); // 5 входов
        }
        
        void update(List<Plant> plants, List<Herbivore> herbivores, List<Predator> predators) {
            // Находим ближайшие объекты
            Plant closestPlant = findClosestPlant(plants);
            Predator closestPredator = findClosestPredator(predators);
            
            // Входные данные для перцептрона
            double[] inputs = new double[5];
            inputs[0] = closestPlant != null ? (closestPlant.x - x) / WIDTH : 0;
            inputs[1] = closestPlant != null ? (closestPlant.y - y) / HEIGHT : 0;
            inputs[2] = closestPredator != null ? (closestPredator.x - x) / WIDTH : 0;
            inputs[3] = closestPredator != null ? (closestPredator.y - y) / HEIGHT : 0;
            inputs[4] = energy / HERBIVORE_ENERGY;
            
            // Принимаем решение
            double[] output = brain.feedForward(inputs);
            
            // Движение на основе решения
            if (output[0] > 0.7) { // Бежать от хищника
                if (closestPredator != null) {
                    x += (x - closestPredator.x) / 10;
                    y += (y - closestPredator.y) / 10;
                }
            } else if (output[1] > 0.7 && closestPlant != null) { // Идти к растению
                x += (closestPlant.x - x) / 10;
                y += (closestPlant.y - y) / 10;
                
                // Если достигли растения - съесть его
                if (Math.hypot(closestPlant.x - x, closestPlant.y - y) < 5) {
                    energy += closestPlant.getEnergy() * 0.8;
                    plants.remove(closestPlant);
                }
            } else { // Случайное блуждание
                moveRandomly();
            }
            
            // Потеря энергии со временем
            energy -= 0.2;
            
            // Ограничиваем координаты
            x = Math.max(0, Math.min(WIDTH, x));
            y = Math.max(0, Math.min(HEIGHT, y));
        }
        
        private Plant findClosestPlant(List<Plant> plants) {
            Plant closest = null;
            double minDist = Double.MAX_VALUE;
            
            for (Plant plant : plants) {
                double dist = Math.hypot(plant.x - x, plant.y - y);
                if (dist < minDist) {
                    minDist = dist;
                    closest = plant;
                }
            }
            
            return closest;
        }
        
        private Predator findClosestPredator(List<Predator> predators) {
            Predator closest = null;
            double minDist = Double.MAX_VALUE;
            
            for (Predator predator : predators) {
                double dist = Math.hypot(predator.x - x, predator.y - y);
                if (dist < minDist) {
                    minDist = dist;
                    closest = predator;
                }
            }
            
            return closest;
        }
    }

    // Класс хищника с перцептроном
    class Predator extends Entity {
        private Perceptron brain;
        
        Predator(int x, int y) {
            super(x, y, PREDATOR_ENERGY);
            this.brain = new Perceptron(3); // 3 входа
        }
        
        void update(List<Herbivore> herbivores, List<Predator> predators) {
            // Находим ближайшую добычу
            Herbivore closestHerbivore = findClosestHerbivore(herbivores);
            
            // Входные данные для перцептрона
            double[] inputs = new double[3];
            inputs[0] = closestHerbivore != null ? (closestHerbivore.x - x) / WIDTH : 0;
            inputs[1] = closestHerbivore != null ? (closestHerbivore.y - y) / HEIGHT : 0;
            inputs[2] = energy / PREDATOR_ENERGY;
            
            // Принимаем решение
            double[] output = brain.feedForward(inputs);
            
            // Движение на основе решения
            if (output[0] > 0.7 && closestHerbivore != null) { // Охота
                x += (closestHerbivore.x - x) / 10;
                y += (closestHerbivore.y - y) / 10;
                
                // Если поймали добычу
                if (Math.hypot(closestHerbivore.x - x, closestHerbivore.y - y) < 7) {
                    energy += closestHerbivore.getEnergy() * 0.6;
                    herbivores.remove(closestHerbivore);
                }
            } else { // Случайное блуждание
                moveRandomly();
            }
            
            // Потеря энергии со временем
            energy -= 0.3;
            
            // Ограничиваем координаты
            x = Math.max(0, Math.min(WIDTH, x));
            y = Math.max(0, Math.min(HEIGHT, y));
        }
        
        private Herbivore findClosestHerbivore(List<Herbivore> herbivores) {
            Herbivore closest = null;
            double minDist = Double.MAX_VALUE;
            
            for (Herbivore herb : herbivores) {
                double dist = Math.hypot(herb.x - x, herb.y - y);
                if (dist < minDist) {
                    minDist = dist;
                    closest = herb;
                }
            }
            
            return closest;
        }
    }

    // Простой однослойный перцептрон
    class Perceptron {
        private double[] weights;
        
        Perceptron(int inputSize) {
            weights = new double[inputSize];
            for (int i = 0; i < weights.length; i++) {
                weights[i] = random.nextDouble() * 2 - 1; // Инициализация случайными весами
            }
        }
        
        double[] feedForward(double[] inputs) {
            double[] outputs = new double[weights.length];
            for (int i = 0; i < weights.length; i++) {
                outputs[i] = sigmoid(inputs[i] * weights[i]);
            }
            return outputs;
        }
        
        private double sigmoid(double x) {
            return 1 / (1 + Math.exp(-x));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BioSystemSimulation sim = new BioSystemSimulation();
            sim.setVisible(true);
        });
    }
}
