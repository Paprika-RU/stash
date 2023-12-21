import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Calculator {
    private JFrame frame;
    private JTextField display;
    private JButton[] numberButtons; // Массив кнопок для цифр
    private JButton[] operatorButtons; // Массив кнопок для операторов (+, -, *, /)
    private JButton equalsButton; // Кнопка равно 
    private JButton clearButton; // Кнопка очистки 
    private JButton backButton; // Кнопка удаления последнего символа
    private JPanel panel;

    private String currentNumber; // Текущее введенное число
    private double result; // Результат вычислений
    private String currentOperator; // Текущий оператор (+, -, *, /)

    public Calculator() {
        frame = new JFrame("Калькулятор"); // Создание окна с названием "Калькулятор"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH); // Добавление поля ввода (JTextField) в верхнюю часть окна

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4)); // Размещение кнопок в сетке 4x4

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i)); 
            panel.add(numberButtons[i]); 
        }

        operatorButtons = new JButton[4];
        operatorButtons[0] = new JButton("+"); // Кнопка сложения
        operatorButtons[1] = new JButton("-"); // Кнопка вычитания
        operatorButtons[2] = new JButton("*"); // Кнопка умножения
        operatorButtons[3] = new JButton("/"); // Кнопка деления

        equalsButton = new JButton("="); // Кнопка равно
        backButton = new JButton("\u2190"); // Символ стрелки для кнопки "backspace"
        clearButton = new JButton("C"); // Кнопка очистки

        panel.add(operatorButtons[0]);
        panel.add(operatorButtons[1]);
        panel.add(operatorButtons[2]);
        panel.add(operatorButtons[3]);
        panel.add(equalsButton);
        panel.add(clearButton);
        panel.add(backButton);

        panel.setBackground(Color.white);

        // Изменение цвета кнопок
        Color buttonColor = new Color(204, 204, 255);
        for (JButton button : numberButtons) {
            button.setBackground(buttonColor);
        }
        for (JButton button : operatorButtons) {
            button.setBackground(buttonColor);
        }
        equalsButton.setBackground(buttonColor);
        clearButton.setBackground(buttonColor);
        backButton.setBackground(buttonColor);

        frame.add(panel, BorderLayout.CENTER); // Добавление панели с кнопками в центр окна

        currentNumber = "";
        result = 0;
        currentOperator = "";

        ActionListener numberListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                currentNumber += source.getText(); // Добавление цифры к текущему числу
                display.setText(currentNumber); // Отображение текущего числа в поле ввода
            }
        };

        for (JButton button : numberButtons) {
            button.addActionListener(numberListener); 
        }

        ActionListener operatorListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                String operator = source.getText(); 

                if (!currentNumber.isEmpty()) {
                    double number = Double.parseDouble(currentNumber);

                    if (currentOperator.equals("+")) {
                        result += number;
                    } else if (currentOperator.equals("-")) {
                        result -= number;
                    } else if (currentOperator.equals("*")) {
                        result *= number;
                    } else if (currentOperator.equals("/")) {
                        result /= number;
                    } else {
                        result = number;
                    }

                    currentNumber = "";
                    display.setText(Double.toString(result)); // Отображение результата вычислений в поле ввода
                }

                currentOperator = operator; 
            }
        };

        for (JButton button : operatorButtons) {
            button.addActionListener(operatorListener); 
        }

        equalsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!currentNumber.isEmpty()) {
                    double number = Double.parseDouble(currentNumber);

                    if (currentOperator.equals("+")) {
                        result += number;
                    } else if (currentOperator.equals("-")) {
                        result -= number;
                    } else if (currentOperator.equals("*")) {
                        result *= number;
                    } else if (currentOperator.equals("/")) {
                        result /= number;
                    } else {
                        result = number;
                    }

                    currentNumber = "";
                    display.setText(Double.toString(result)); // Отображение результата вычислений в поле ввода
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentNumber = "";
                result = 0;
                currentOperator = "";
                display.setText(""); // Очистка поля ввода
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!currentNumber.isEmpty()) {
                    currentNumber = currentNumber.substring(0, currentNumber.length() - 1); // Удаление последнего символа из текущего числа
                    display.setText(currentNumber); // Отображение обновленного текущего числа в поле ввода
                }
            }
        });

        // Увеличение размеров окна ввода
        display.setPreferredSize(new Dimension(300, 50));

        frame.setSize(300, 400); // Задание размеров окна

        frame.setVisible(true); // Отображение окна
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator();
            }
        });
    }
}
