import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       
       System.out.println("Введите начальную сумму: ");
       double principal = sc.nextDouble();
       
       System.out.println("Введите процент: ");
       double interestRate = sc.nextDouble();
       
       System.out.println("Введите кол-во периодов: ");
       double periods = sc.nextInt();
       
       // Вычисление сложного процента
        double amount = calculateCompoundInterest(principal, interestRate, (int) periods);
        System.out.println("Сложный процент: " + amount);
        
        // Обратное вычисление процента
        System.out.print("Введите конечную сумму: ");
        double targetAmount = sc.nextDouble();

        double reverseInterestRate = calculateReverseInterestRate(principal, targetAmount, (int) periods);
        System.out.println("Должный быть процент: " + reverseInterestRate);

        sc.close();
    }
    
// Метод для вычисления сложного процента
    public static double calculateCompoundInterest(double principal, double interestRate, int periods) {
        // Формула для вычисления сложного процента: Сумма = Начальная сумма * (1 + Процент/100)^периоды
        double amount = principal * Math.pow(1 + interestRate / 100, periods);
        return amount;
    }
    
    // Метод для обратного вычисления процента
     public static double calculateReverseInterestRate(double principal, double targetAmount, int periods) {
        // Формула для обратного вычисления процента: Процент = ((Конечная сумма / Начальная сумма)^(1/периоды) - 1) * 100
        double reverseInterestRate = Math.pow(targetAmount / principal, 1.0 / periods) - 1;
        reverseInterestRate *= 100;
        return reverseInterestRate;
}
}
