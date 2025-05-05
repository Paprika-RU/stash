import java.util.*;
import java.util.stream.*;

public class GeneticAlgorithmOptimization {
    
    static class Individual {
        double x, y;
        double fitness;
        
        Individual(double x, double y) {
            this.x = x;
            this.y = y;
            calculateFitness();
        }
        
        void calculateFitness() {
            this.fitness = 1.0 / (1 + x*x + y*y);
        }
    }

    public static void main(String[] args) {
        // Параметры алгоритма
        int populationSize = 100;
        int generations = 100;
        double mutationRate = 0.1;
        double crossoverRate = 0.9;
        double minXY = -5.0;
        double maxXY = 5.0;
        
        // Инициализация популяции
        List<Individual> population = initializePopulation(populationSize, minXY, maxXY);
        
        for (int gen = 0; gen < generations; gen++) {
            // Селекция (турнирная)
            List<Individual> selected = tournamentSelection(population, populationSize/2);
            
            // Скрещивание и мутация
            List<Individual> offspring = crossoverAndMutate(selected, crossoverRate, mutationRate, minXY, maxXY);
            
            // Формирование новой популяции
            population = Stream.concat(selected.stream(), offspring.stream())
                             .sorted(Comparator.comparingDouble(ind -> -ind.fitness))
                             .limit(populationSize)
                             .collect(Collectors.toList());
            
            // Вывод лучшей особи
            Individual best = population.get(0);
            System.out.printf("Поколение %d: Лучшее решение (x=%.4f, y=%.4f) f=%.6f%n", 
                            gen, best.x, best.y, best.fitness);
        }
    }
    
    static List<Individual> initializePopulation(int size, double min, double max) {
        Random rand = new Random();
        return IntStream.range(0, size)
                      .mapToObj(i -> new Individual(
                          min + (max - min) * rand.nextDouble(),
                          min + (max - min) * rand.nextDouble()))
                      .collect(Collectors.toList());
    }
    
    static List<Individual> tournamentSelection(List<Individual> population, int size) {
        Random rand = new Random();
        return IntStream.range(0, size)
                      .mapToObj(i -> {
                          Individual a = population.get(rand.nextInt(population.size()));
                          Individual b = population.get(rand.nextInt(population.size()));
                          return a.fitness > b.fitness ? a : b;
                      })
                      .collect(Collectors.toList());
    }
    
    static List<Individual> crossoverAndMutate(List<Individual> parents, double crossoverRate, 
                                             double mutationRate, double min, double max) {
        Random rand = new Random();
        List<Individual> offspring = new ArrayList<>();
        
        for (int i = 0; i < parents.size(); i++) {
            Individual parent1 = parents.get(i);
            Individual parent2 = parents.get(rand.nextInt(parents.size()));
            
            // Скрещивание
            double childX = rand.nextDouble() < crossoverRate ? 
                           (parent1.x + parent2.x) / 2 : parent1.x;
            double childY = rand.nextDouble() < crossoverRate ? 
                           (parent1.y + parent2.y) / 2 : parent1.y;
            
            // Мутация
            if (rand.nextDouble() < mutationRate) {
                childX += (rand.nextDouble() - 0.5) * 0.5;
                childY += (rand.nextDouble() - 0.5) * 0.5;
                
                // Проверка границ
                childX = Math.max(min, Math.min(max, childX));
                childY = Math.max(min, Math.min(max, childY));
            }
            
            offspring.add(new Individual(childX, childY));
        }
        
        return offspring;
    }
}

// Сравнение производительности отбора методом элит и методом рулетки:
// Метод элит - имеет более высокую скорость сходимости, может привести к преждевременной сходимости
// Метод рулетки - из-за случайности может дольше идти к оптимальному решению, меньше риск застрять в локальном оптимуме, но может сохранять слабые особи
