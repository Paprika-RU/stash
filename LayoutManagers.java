import javax.swing.*;
import java.awt.*;
public class LayoutManagers {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Layout Managers Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Создаем контейнеры для размещения компонентов
        JPanel flowPanel = new JPanel();
        JPanel borderPanel = new JPanel();

        // Устанавливаем менеджеры компоновки
        flowPanel.setLayout(new FlowLayout());
        borderPanel.setLayout(new BorderLayout());

        // Создаем компоненты
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");

        // Добавляем компоненты в панель flowPanel с менеджером компоновки FlowLayout
        flowPanel.add(button1);
        flowPanel.add(button2);
        flowPanel.add(button3);

        // Добавляем компоненты в панель borderPanel с менеджером компоновки BorderLayout
        borderPanel.add(button1, BorderLayout.NORTH);
        borderPanel.add(button2, BorderLayout.CENTER);
        borderPanel.add(button3, BorderLayout.SOUTH);

        // Добавляем панели в главное окно
        frame.add(flowPanel);
        // либо
        frame.add(borderPanel);

        // Отображаем главное окно
        frame.setVisible(true);
    }
}
