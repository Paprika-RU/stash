import java.util.InputMismatchException;
import java.util.Scanner;

public class LeapYearChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Введите год (для выхода введите отрицательное число): ");
                int year = sc.nextInt();

                if (year < 0) {
                    System.out.println("Выход из программы...");
                    break;
                }

                boolean isLeapYear = isLeapYear(year);
                if (isLeapYear) {
                    System.out.println(year + " - високосный год");
                } else {
                    System.out.println(year + " - не високосный год");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите год в числовом формате.");
                sc.nextLine(); // Очистка буфера ввода
            }
        }

        sc.close();
    }


    //проверяет является ли год высокосным
    public static boolean isLeapYear(int year) {
        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }
}
