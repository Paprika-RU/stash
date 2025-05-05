import java.util.Random;

public class SimulatedAnnealingOptimization {

    // Целевая функция
    private static double function(double x, double y) {
        return 1.0 / (1.0 + x*x + y*y);
    }

    public static void main(String[] args) {
        // Параметры алгоритма
        double initialTemp = 1000.0;
        double coolingRate = 0.003;
        int iterations = 10000;
        
        Random random = new Random();
        
        // Начальное решение (случайная точка)
        double currentX = random.nextDouble() * 10 - 5; // [-5, 5]
        double currentY = random.nextDouble() * 10 - 5; // [-5, 5]
        double currentEnergy = function(currentX, currentY);
        
        // Лучшее найденное решение
        double bestX = currentX;
        double bestY = currentY;
        double bestEnergy = currentEnergy;
        
        double temp = initialTemp;
        
        for (int i = 0; i < iterations; i++) {
            // Генерация соседнего решения
            double neighborX = currentX + random.nextDouble() * 2 - 1;
            double neighborY = currentY + random.nextDouble() * 2 - 1;
            double neighborEnergy = function(neighborX, neighborY);
            
            // Разница энергий (мы хотим максимизировать функцию)
            double energyDiff = neighborEnergy - currentEnergy;
            
            // Если новое решение лучше, принимаем его
            if (energyDiff > 0) {
                currentX = neighborX;
                currentY = neighborY;
                currentEnergy = neighborEnergy;
                
                // Обновляем лучшее решение
                if (currentEnergy > bestEnergy) {
                    bestX = currentX;
                    bestY = currentY;
                    bestEnergy = currentEnergy;
                }
            }
            // Если решение хуже, принимаем его с некоторой вероятностью
            else if (Math.exp(energyDiff / temp) > random.nextDouble()) {
                currentX = neighborX;
                currentY = neighborY;
                currentEnergy = neighborEnergy;
            }
            
            // Охлаждение
            temp *= 1 - coolingRate;
            
            // Вывод прогресса
            if (i % 1000 == 0) {
                System.out.printf("Итерация %d: temp=%.4f, текущее (%.4f, %.4f)=%.6f, лучшее (%.4f, %.4f)=%.6f%n",
                                 i, temp, currentX, currentY, currentEnergy, 
                                 bestX, bestY, bestEnergy);
            }
        }
        
        System.out.println("\nОптимальное решение:");
        System.out.printf("x = %.6f, y = %.6f%n", bestX, bestY);
        System.out.printf("Максимальное значение функции: %.6f%n", bestEnergy);
    }
}
